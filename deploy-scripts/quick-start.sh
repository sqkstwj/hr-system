#!/bin/bash

# 一键部署脚本 - 适用于已有云服务器的快速部署

set -e

# 颜色定义
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查参数
if [ $# -eq 0 ]; then
    log_error "请提供服务器IP地址"
    echo "用法: ./quick-start.sh <服务器IP> [用户名]"
    echo "示例: ./quick-start.sh 123.456.789.123 root"
    exit 1
fi

SERVER_IP=$1
USER=${2:-root}

log_info "开始部署到服务器: $SERVER_IP (用户: $USER)"

# 检查本地文件
if [ ! -f "spring_01/target/spring_01-0.0.1-SNAPSHOT.jar" ]; then
    log_info "构建后端项目..."
    cd spring_01
    mvn clean package -Dmaven.test.skip=true
    cd ..
fi

if [ ! -d "Hr-vue/dist" ]; then
    log_info "构建前端项目..."
    cd Hr-vue
    npm run build
    cd ..
fi

# 创建临时目录
TEMP_DIR="/tmp/hr-deploy-$(date +%s)"
log_info "创建临时目录: $TEMP_DIR"

# 上传文件到服务器
log_info "上传文件到服务器..."
ssh $USER@$SERVER_IP "mkdir -p $TEMP_DIR"

# 上传后端JAR
scp spring_01/target/spring_01-0.0.1-SNAPSHOT.jar $USER@$SERVER_IP:$TEMP_DIR/

# 上传前端构建文件
scp -r Hr-vue/dist $USER@$SERVER_IP:$TEMP_DIR/

# 上传部署脚本
scp deploy-scripts/backend-deploy.sh $USER@$SERVER_IP:$TEMP_DIR/
scp deploy-scripts/frontend-deploy.sh $USER@$SERVER_IP:$TEMP_DIR/
scp deploy-scripts/production-config.yml $USER@$SERVER_IP:$TEMP_DIR/

# 在服务器上执行部署
log_info "在服务器上执行部署..."
ssh $USER@$SERVER_IP << EOF
    set -e
    
    echo "开始服务器端部署..."
    
    # 创建应用目录
    sudo mkdir -p /opt/hr-system/{backend,frontend,logs}
    sudo chown -R $USER:$USER /opt/hr-system
    
    # 部署后端
    echo "部署后端..."
    cd $TEMP_DIR
    chmod +x backend-deploy.sh
    ./backend-deploy.sh
    
    # 部署前端
    echo "部署前端..."
    chmod +x frontend-deploy.sh
    ./frontend-deploy.sh
    
    # 清理临时文件
    rm -rf $TEMP_DIR
    
    echo "部署完成！"
EOF

log_info "部署完成！"
log_info "访问地址: http://$SERVER_IP"
log_info "后端API: http://$SERVER_IP:8080"
log_info ""
log_info "常用命令："
log_info "查看后端状态: ssh $USER@$SERVER_IP 'sudo systemctl status hr-system.service'"
log_info "查看后端日志: ssh $USER@$SERVER_IP 'sudo journalctl -u hr-system.service -f'"
log_info "重启后端: ssh $USER@$SERVER_IP 'sudo systemctl restart hr-system.service'"
log_info "重启前端: ssh $USER@$SERVER_IP 'sudo systemctl reload nginx'"
