#!/bin/bash

# 前端Vue应用部署脚本

set -e

FRONTEND_DIR="/opt/hr-system/frontend"
NGINX_SITE="/etc/nginx/sites-available/hr-system"

log_info() {
    echo -e "\033[0;32m[INFO]\033[0m $1"
}

log_error() {
    echo -e "\033[0;31m[ERROR]\033[0m $1"
}

# 检查dist目录是否存在
if [ ! -d "dist" ]; then
    log_error "dist目录不存在，请先构建前端项目"
    exit 1
fi

# 停止Nginx
log_info "停止Nginx服务..."
sudo systemctl stop nginx

# 备份旧版本
if [ -d "$FRONTEND_DIR" ]; then
    log_info "备份旧版本..."
    sudo mv "$FRONTEND_DIR" "${FRONTEND_DIR}.backup.$(date +%Y%m%d_%H%M%S)"
fi

# 部署新版本
log_info "部署新版本..."
sudo mkdir -p "$FRONTEND_DIR"
sudo cp -r dist/* "$FRONTEND_DIR/"
sudo chown -R www-data:www-data "$FRONTEND_DIR"

# 配置Nginx
log_info "配置Nginx..."
sudo tee "$NGINX_SITE" > /dev/null <<EOF
server {
    listen 80;
    server_name _;
    root $FRONTEND_DIR;
    index index.html;

    # 前端路由支持
    location / {
        try_files \$uri \$uri/ /index.html;
    }

    # 静态资源缓存
    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    # API反向代理到后端
    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }

    # 文件上传代理
    location /upload/ {
        proxy_pass http://localhost:8080/upload/;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
        client_max_body_size 100M;
    }
}
EOF

# 启用站点
sudo ln -sf "$NGINX_SITE" /etc/nginx/sites-enabled/
sudo rm -f /etc/nginx/sites-enabled/default

# 测试Nginx配置
if sudo nginx -t; then
    log_info "Nginx配置测试通过"
else
    log_error "Nginx配置测试失败"
    exit 1
fi

# 启动Nginx
log_info "启动Nginx服务..."
sudo systemctl start nginx
sudo systemctl enable nginx

log_info "前端部署完成！"
log_info "访问地址: http://你的服务器IP"
