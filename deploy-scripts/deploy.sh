#!/bin/bash

# 人力资源管理系统服务器部署脚本
# 适用于 Ubuntu 20.04+ 系统

set -e

echo "开始部署人力资源管理系统..."

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 日志函数
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查是否为root用户
check_root() {
    if [[ $EUID -eq 0 ]]; then
        log_error "请不要使用root用户运行此脚本"
        exit 1
    fi
}

# 更新系统
update_system() {
    log_info "更新系统包..."
    sudo apt update && sudo apt upgrade -y
}

# 安装Java
install_java() {
    log_info "安装Java 17..."
    if ! command -v java &> /dev/null; then
        sudo apt install openjdk-17-jdk -y
    else
        log_warn "Java已安装: $(java -version 2>&1 | head -n 1)"
    fi
}

# 安装MySQL
install_mysql() {
    log_info "安装MySQL..."
    if ! command -v mysql &> /dev/null; then
        sudo apt install mysql-server -y
        sudo systemctl start mysql
        sudo systemctl enable mysql
        
        # 配置MySQL安全设置
        log_info "配置MySQL安全设置..."
        sudo mysql_secure_installation
    else
        log_warn "MySQL已安装"
    fi
}

# 安装Redis
install_redis() {
    log_info "安装Redis..."
    if ! command -v redis-server &> /dev/null; then
        sudo apt install redis-server -y
        sudo systemctl start redis-server
        sudo systemctl enable redis-server
    else
        log_warn "Redis已安装"
    fi
}

# 安装Nginx
install_nginx() {
    log_info "安装Nginx..."
    if ! command -v nginx &> /dev/null; then
        sudo apt install nginx -y
        sudo systemctl start nginx
        sudo systemctl enable nginx
    else
        log_warn "Nginx已安装"
    fi
}

# 安装Maven
install_maven() {
    log_info "安装Maven..."
    if ! command -v mvn &> /dev/null; then
        sudo apt install maven -y
    else
        log_warn "Maven已安装: $(mvn -version 2>&1 | head -n 1)"
    fi
}

# 配置防火墙
configure_firewall() {
    log_info "配置防火墙..."
    sudo ufw allow 22/tcp
    sudo ufw allow 80/tcp
    sudo ufw allow 443/tcp
    sudo ufw allow 8080/tcp
    sudo ufw --force enable
}

# 创建应用目录
create_app_dirs() {
    log_info "创建应用目录..."
    sudo mkdir -p /opt/hr-system
    sudo mkdir -p /opt/hr-system/backend
    sudo mkdir -p /opt/hr-system/frontend
    sudo mkdir -p /opt/hr-system/logs
    sudo chown -R $USER:$USER /opt/hr-system
}

# 配置MySQL数据库
setup_database() {
    log_info "配置MySQL数据库..."
    
    # 创建数据库和用户
    sudo mysql -e "
    CREATE DATABASE IF NOT EXISTS gl_1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    CREATE USER IF NOT EXISTS 'hruser'@'%' IDENTIFIED BY 'HrSystem2024!';
    GRANT ALL PRIVILEGES ON gl_1.* TO 'hruser'@'%';
    FLUSH PRIVILEGES;
    "
    
    log_info "数据库配置完成"
}

# 主函数
main() {
    log_info "开始部署人力资源管理系统..."
    
    check_root
    update_system
    install_java
    install_mysql
    install_redis
    install_nginx
    install_maven
    configure_firewall
    create_app_dirs
    setup_database
    
    log_info "基础环境部署完成！"
    log_info "接下来需要："
    log_info "1. 上传项目文件到服务器"
    log_info "2. 配置后端应用"
    log_info "3. 构建并部署前端"
    log_info "4. 配置Nginx反向代理"
}

# 运行主函数
main "$@"
