#!/bin/bash

# Git自动部署脚本
# 适用于从GitHub自动部署到云服务器

set -e

REPO_URL="https://github.com/sqkstwj/hr-system.git"
DEPLOY_DIR="/opt/hr-system"
BRANCH="master"

log_info() {
    echo -e "\033[0;32m[INFO]\033[0m $1"
}

log_error() {
    echo -e "\033[0;31m[ERROR]\033[0m $1"
}

# 检查Git是否安装
if ! command -v git &> /dev/null; then
    log_error "Git未安装，正在安装..."
    apt update && apt install git -y
fi

# 创建部署目录
mkdir -p $DEPLOY_DIR
cd $DEPLOY_DIR

# 克隆或更新代码
if [ -d ".git" ]; then
    log_info "更新代码..."
    git fetch origin
    git reset --hard origin/$BRANCH
else
    log_info "克隆代码..."
    git clone -b $BRANCH $REPO_URL .
fi

# 安装Java环境
if ! command -v java &> /dev/null; then
    log_info "安装Java 17..."
    apt update && apt install openjdk-17-jdk -y
fi

# 安装Maven
if ! command -v mvn &> /dev/null; then
    log_info "安装Maven..."
    apt install maven -y
fi

# 安装Node.js
if ! command -v node &> /dev/null; then
    log_info "安装Node.js..."
    curl -fsSL https://deb.nodesource.com/setup_18.x | bash -
    apt install nodejs -y
fi

# 构建后端
log_info "构建后端项目..."
cd $DEPLOY_DIR/spring_01
mvn clean package -Dmaven.test.skip=true

# 构建前端
log_info "构建前端项目..."
cd $DEPLOY_DIR/Hr-vue
npm install
npm run build

# 部署后端
log_info "部署后端..."
cp target/spring_01-0.0.1-SNAPSHOT.jar $DEPLOY_DIR/backend/
chmod +x $DEPLOY_DIR/backend/spring_01-0.0.1-SNAPSHOT.jar

# 创建systemd服务
cat > /etc/systemd/system/hr-system.service << 'EOF'
[Unit]
Description=HR System Spring Boot Application
After=network.target mysql.service redis-server.service

[Service]
Type=simple
User=root
WorkingDirectory=/opt/hr-system/backend
ExecStart=/usr/bin/java -Xms512m -Xmx1024m -jar spring_01-0.0.1-SNAPSHOT.jar
ExecStop=/bin/kill -15 $MAINPID
Restart=always
RestartSec=10
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
EOF

# 重新加载systemd配置
systemctl daemon-reload

# 重启后端服务
log_info "重启后端服务..."
systemctl enable hr-system.service
systemctl restart hr-system.service

# 部署前端
log_info "部署前端..."
cp -r dist/* $DEPLOY_DIR/frontend/

# 配置Nginx
cat > /etc/nginx/sites-available/hr-system << 'EOF'
server {
    listen 80;
    server_name _;
    root /opt/hr-system/frontend;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    location /upload/ {
        proxy_pass http://localhost:8080/upload/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        client_max_body_size 100M;
    }
}
EOF

# 启用Nginx配置
ln -sf /etc/nginx/sites-available/hr-system /etc/nginx/sites-enabled/
rm -f /etc/nginx/sites-enabled/default

# 测试Nginx配置
nginx -t

# 重启Nginx
log_info "重启Nginx..."
systemctl reload nginx

log_info "部署完成！"
log_info "访问地址: http://$(curl -s ifconfig.me)"
