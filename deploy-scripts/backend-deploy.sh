#!/bin/bash

# 后端Spring Boot应用部署脚本

set -e

APP_NAME="hr-system"
APP_JAR="spring_01-0.0.1-SNAPSHOT.jar"
APP_DIR="/opt/hr-system/backend"
SERVICE_NAME="hr-system.service"

log_info() {
    echo -e "\033[0;32m[INFO]\033[0m $1"
}

log_error() {
    echo -e "\033[0;31m[ERROR]\033[0m $1"
}

# 检查JAR文件是否存在
if [ ! -f "$APP_JAR" ]; then
    log_error "JAR文件 $APP_JAR 不存在，请先构建项目"
    exit 1
fi

# 停止现有服务
log_info "停止现有服务..."
sudo systemctl stop $SERVICE_NAME 2>/dev/null || true

# 备份旧版本
if [ -f "$APP_DIR/$APP_JAR" ]; then
    log_info "备份旧版本..."
    cp "$APP_DIR/$APP_JAR" "$APP_DIR/${APP_JAR}.backup.$(date +%Y%m%d_%H%M%S)"
fi

# 部署新版本
log_info "部署新版本..."
cp "$APP_JAR" "$APP_DIR/"
chmod +x "$APP_DIR/$APP_JAR"

# 创建systemd服务文件
log_info "创建systemd服务..."
sudo tee /etc/systemd/system/$SERVICE_NAME > /dev/null <<EOF
[Unit]
Description=HR System Spring Boot Application
After=network.target mysql.service redis-server.service

[Service]
Type=simple
User=www-data
Group=www-data
WorkingDirectory=$APP_DIR
ExecStart=/usr/bin/java -Xms512m -Xmx1024m -jar $APP_JAR
ExecStop=/bin/kill -15 \$MAINPID
Restart=always
RestartSec=10
StandardOutput=journal
StandardError=journal

[Install]
WantedBy=multi-user.target
EOF

# 重新加载systemd配置
sudo systemctl daemon-reload

# 启动服务
log_info "启动服务..."
sudo systemctl enable $SERVICE_NAME
sudo systemctl start $SERVICE_NAME

# 检查服务状态
sleep 5
if sudo systemctl is-active --quiet $SERVICE_NAME; then
    log_info "服务启动成功！"
    log_info "查看服务状态: sudo systemctl status $SERVICE_NAME"
    log_info "查看日志: sudo journalctl -u $SERVICE_NAME -f"
else
    log_error "服务启动失败！"
    sudo systemctl status $SERVICE_NAME
    exit 1
fi
