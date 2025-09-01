# Windows PowerShell 部署脚本
# 适用于阿里云ECS部署

param(
    [Parameter(Mandatory=$true)]
    [string]$ServerIP = "116.62.55.116",
    
    [Parameter(Mandatory=$false)]
    [string]$Username = "root"
)

Write-Host "开始部署人力资源管理系统到阿里云ECS..." -ForegroundColor Green
Write-Host "服务器IP: $ServerIP" -ForegroundColor Yellow

# 检查SSH连接
Write-Host "检查SSH连接..." -ForegroundColor Cyan
try {
    $testConnection = ssh -o ConnectTimeout=10 -o BatchMode=yes $Username@$ServerIP "echo 'SSH连接成功'"
    if ($LASTEXITCODE -eq 0) {
        Write-Host "SSH连接正常" -ForegroundColor Green
    } else {
        Write-Host "SSH连接失败，请检查网络和密钥配置" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "SSH连接失败: $_" -ForegroundColor Red
    exit 1
}

# 在服务器上执行环境部署
Write-Host "在服务器上部署基础环境..." -ForegroundColor Cyan
$deployScript = @"
#!/bin/bash
set -e

echo "开始部署人力资源管理系统..."

# 更新系统
echo "更新系统包..."
apt update && apt upgrade -y

# 安装Java 17
echo "安装Java 17..."
apt install openjdk-17-jdk -y

# 安装MySQL
echo "安装MySQL..."
apt install mysql-server -y
systemctl start mysql
systemctl enable mysql

# 安装Redis
echo "安装Redis..."
apt install redis-server -y
systemctl start redis-server
systemctl enable redis-server

# 安装Nginx
echo "安装Nginx..."
apt install nginx -y
systemctl start nginx
systemctl enable nginx

# 安装Maven
echo "安装Maven..."
apt install maven -y

# 安装Node.js和npm
echo "安装Node.js..."
curl -fsSL https://deb.nodesource.com/setup_18.x | bash -
apt install nodejs -y

# 安装Git
echo "安装Git..."
apt install git -y

# 配置防火墙
echo "配置防火墙..."
ufw allow 22/tcp
ufw allow 80/tcp
ufw allow 443/tcp
ufw allow 8080/tcp
ufw allow 5173/tcp
ufw --force enable

# 创建应用目录
echo "创建应用目录..."
mkdir -p /opt/hr-system
mkdir -p /opt/hr-system/backend
mkdir -p /opt/hr-system/frontend
mkdir -p /opt/hr-system/logs

# 配置MySQL数据库
echo "配置MySQL数据库..."
mysql -e "CREATE DATABASE IF NOT EXISTS gl_1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -e "CREATE USER IF NOT EXISTS 'hruser'@'%' IDENTIFIED BY 'HrSystem2024!';"
mysql -e "GRANT ALL PRIVILEGES ON gl_1.* TO 'hruser'@'%';"
mysql -e "FLUSH PRIVILEGES;"

echo "基础环境部署完成！"
"@

# 上传并执行部署脚本
$deployScript | ssh $Username@$ServerIP "cat > /tmp/deploy.sh && chmod +x /tmp/deploy.sh && /tmp/deploy.sh"

if ($LASTEXITCODE -eq 0) {
    Write-Host "基础环境部署成功！" -ForegroundColor Green
} else {
    Write-Host "基础环境部署失败！" -ForegroundColor Red
    exit 1
}

# 检查本地项目文件
Write-Host "检查本地项目文件..." -ForegroundColor Cyan
if (-not (Test-Path "spring_01/target/spring_01-0.0.1-SNAPSHOT.jar")) {
    Write-Host "构建后端项目..." -ForegroundColor Yellow
    Set-Location "spring_01"
    mvn clean package -Dmaven.test.skip=true
    Set-Location ".."
}

if (-not (Test-Path "Hr-vue/dist")) {
    Write-Host "构建前端项目..." -ForegroundColor Yellow
    Set-Location "Hr-vue"
    npm run build
    Set-Location ".."
}

# 上传项目文件
Write-Host "上传项目文件到服务器..." -ForegroundColor Cyan

# 创建临时目录
ssh $Username@$ServerIP "mkdir -p /tmp/hr-deploy"

# 上传后端JAR
scp "spring_01/target/spring_01-0.0.1-SNAPSHOT.jar" "${Username}@${ServerIP}:/tmp/hr-deploy/"

# 上传前端构建文件
scp -r "Hr-vue/dist" "${Username}@${ServerIP}:/tmp/hr-deploy/"

# 在服务器上部署应用
Write-Host "在服务器上部署应用..." -ForegroundColor Cyan
$appDeployScript = @"
#!/bin/bash
set -e

echo "部署后端应用..."

# 部署后端
cp /tmp/hr-deploy/spring_01-0.0.1-SNAPSHOT.jar /opt/hr-system/backend/
chmod +x /opt/hr-system/backend/spring_01-0.0.1-SNAPSHOT.jar

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
ExecStop=/bin/kill -15 \$MAINPID
Restart=always
RestartSec=10
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
EOF

systemctl daemon-reload
systemctl enable hr-system.service
systemctl start hr-system.service

echo "部署前端应用..."

# 部署前端
cp -r /tmp/hr-deploy/dist/* /opt/hr-system/frontend/

# 配置Nginx
cat > /etc/nginx/sites-available/hr-system << 'EOF'
server {
    listen 80;
    server_name _;
    root /opt/hr-system/frontend;
    index index.html;

    location / {
        try_files \$uri \$uri/ /index.html;
    }

    location ~* \.(js|css|png|jpg|jpeg|gif|ico|svg)$ {
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    location /api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }

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

ln -sf /etc/nginx/sites-available/hr-system /etc/nginx/sites-enabled/
rm -f /etc/nginx/sites-enabled/default
nginx -t
systemctl reload nginx

# 清理临时文件
rm -rf /tmp/hr-deploy

echo "部署完成！"
"@

$appDeployScript | ssh $Username@$ServerIP "cat > /tmp/app-deploy.sh && chmod +x /tmp/app-deploy.sh && /tmp/app-deploy.sh"

if ($LASTEXITCODE -eq 0) {
    Write-Host "应用部署成功！" -ForegroundColor Green
} else {
    Write-Host "应用部署失败！" -ForegroundColor Red
    exit 1
}

# 检查服务状态
Write-Host "检查服务状态..." -ForegroundColor Cyan
ssh $Username@$ServerIP "systemctl status hr-system.service --no-pager"
ssh $Username@$ServerIP "systemctl status nginx --no-pager"

Write-Host "部署完成！" -ForegroundColor Green
Write-Host "访问地址: http://$ServerIP" -ForegroundColor Yellow
Write-Host "后端API: http://$ServerIP:8080" -ForegroundColor Yellow
Write-Host ""
Write-Host "常用命令：" -ForegroundColor Cyan
Write-Host "查看后端状态: ssh $Username@$ServerIP 'systemctl status hr-system.service'" -ForegroundColor White
Write-Host "查看后端日志: ssh $Username@$ServerIP 'journalctl -u hr-system.service -f'" -ForegroundColor White
Write-Host "重启后端: ssh $Username@$ServerIP 'systemctl restart hr-system.service'" -ForegroundColor White
Write-Host "重启前端: ssh $Username@$ServerIP 'systemctl reload nginx'" -ForegroundColor White
