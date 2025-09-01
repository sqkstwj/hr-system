# 人力资源管理系统部署指南

## 项目概述
这是一个前后端分离的人力资源管理系统，包含：
- 后端：Spring Boot + MySQL + Redis + MyBatis Plus
- 前端：Vue 3 + Element Plus
- 文件存储：阿里云OSS

## 快速部署步骤

### 1. 服务器准备
```bash
# 连接到你的云服务器
ssh root@你的服务器IP

# 下载部署脚本
wget https://raw.githubusercontent.com/your-repo/deploy-scripts/main/deploy.sh
chmod +x deploy.sh

# 运行环境部署脚本
./deploy.sh
```

### 2. 后端部署
```bash
# 在本地构建项目
cd spring_01
mvn clean package -Dmaven.test.skip=true

# 上传JAR文件到服务器
scp target/spring_01-0.0.1-SNAPSHOT.jar root@你的服务器IP:/tmp/

# 在服务器上部署
ssh root@你的服务器IP
cd /tmp
chmod +x backend-deploy.sh
./backend-deploy.sh
```

### 3. 前端部署
```bash
# 在本地构建前端
cd Hr-vue
npm run build

# 上传构建文件到服务器
scp -r dist/* root@你的服务器IP:/tmp/dist/

# 在服务器上部署
ssh root@你的服务器IP
cd /tmp
chmod +x frontend-deploy.sh
./frontend-deploy.sh
```

## 在云服务器上继续开发

### 方案1：直接在服务器上开发（推荐）
```bash
# 安装开发工具
sudo apt install git vim nodejs npm -y

# 克隆项目到服务器
git clone https://github.com/your-repo/hr-system.git
cd hr-system

# 安装前端依赖
cd Hr-vue
npm install

# 启动开发服务器（需要端口转发）
npm run dev -- --host 0.0.0.0

# 在本地使用SSH端口转发访问
ssh -L 5173:localhost:5173 root@你的服务器IP
# 然后访问 http://localhost:5173
```

### 方案2：使用VS Code远程开发
1. 安装VS Code Remote-SSH扩展
2. 连接到服务器
3. 在服务器上直接编辑代码
4. 实时同步到本地

### 方案3：使用Git + 自动部署
```bash
# 在服务器上设置Git钩子
cd /opt/hr-system
git init
git remote add origin https://github.com/your-repo/hr-system.git

# 创建自动部署脚本
cat > .git/hooks/post-receive << 'EOF'
#!/bin/bash
git --work-tree=/opt/hr-system --git-dir=/opt/hr-system/.git checkout -f
cd /opt/hr-system/spring_01 && mvn clean package -Dmaven.test.skip=true
sudo systemctl restart hr-system.service
cd /opt/hr-system/Hr-vue && npm run build
sudo systemctl reload nginx
EOF

chmod +x .git/hooks/post-receive
```

## 开发环境配置

### 后端开发
```bash
# 在服务器上启动开发模式
cd /opt/hr-system/spring_01
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 或者使用热重载
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.devtools.restart.enabled=true"
```

### 前端开发
```bash
# 在服务器上启动开发服务器
cd /opt/hr-system/Hr-vue
npm run dev -- --host 0.0.0.0 --port 5173

# 配置防火墙开放端口
sudo ufw allow 5173
```

## 数据库管理
```bash
# 连接到MySQL
mysql -u hruser -p gl_1

# 导入数据（如果有SQL文件）
mysql -u hruser -p gl_1 < /path/to/your/database.sql

# 备份数据库
mysqldump -u hruser -p gl_1 > backup_$(date +%Y%m%d).sql
```

## 监控和日志
```bash
# 查看应用状态
sudo systemctl status hr-system.service

# 查看应用日志
sudo journalctl -u hr-system.service -f

# 查看Nginx日志
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log

# 查看应用日志文件
tail -f /opt/hr-system/logs/hr-system.log
```

## 常见问题

### 1. 端口被占用
```bash
# 查看端口占用
sudo netstat -tlnp | grep :8080

# 杀死进程
sudo kill -9 <PID>
```

### 2. 权限问题
```bash
# 修复文件权限
sudo chown -R www-data:www-data /opt/hr-system
sudo chmod -R 755 /opt/hr-system
```

### 3. 内存不足
```bash
# 调整JVM参数
sudo vim /etc/systemd/system/hr-system.service
# 修改 -Xmx 参数为合适的内存大小
```

## 安全建议
1. 修改默认密码
2. 配置防火墙只开放必要端口
3. 定期更新系统和依赖
4. 配置SSL证书（推荐使用Let's Encrypt）
5. 定期备份数据库

## 性能优化
1. 配置Redis持久化
2. 调整MySQL配置参数
3. 配置Nginx缓存策略
4. 使用CDN加速静态资源
5. 配置数据库连接池参数
