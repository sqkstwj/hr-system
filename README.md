# 人力资源管理系统 (HR Management System)

一个基于Spring Boot + Vue 3的前后端分离的人力资源管理系统，提供完整的员工信息管理、部门管理、岗位管理等功能。

## 🚀 项目特性

- **前后端分离架构**：后端使用Spring Boot，前端使用Vue 3 + Element Plus
- **用户管理**：员工信息增删改查、用户注册、权限管理
- **部门管理**：部门信息管理、组织架构维护
- **岗位管理**：岗位信息管理、员工岗位分配
- **文件上传**：支持阿里云OSS文件存储
- **JWT认证**：基于JWT的用户认证和授权
- **Redis缓存**：提升系统性能
- **响应式设计**：适配各种设备屏幕

## 🛠️ 技术栈

### 后端技术
- **框架**：Spring Boot 3.2.1
- **数据库**：MySQL 8.0
- **ORM**：MyBatis Plus 3.5.5
- **缓存**：Redis
- **认证**：JWT (java-jwt 4.3.0)
- **文件存储**：阿里云OSS
- **构建工具**：Maven
- **Java版本**：JDK 17

### 前端技术
- **框架**：Vue 3.3.4
- **UI组件库**：Element Plus 2.4.1
- **状态管理**：Pinia 2.1.7
- **路由**：Vue Router 4.2.5
- **HTTP客户端**：Axios 1.5.1
- **富文本编辑器**：Vue Quill
- **构建工具**：Vite 4.4.11
- **样式预处理**：Sass

## 📁 项目结构

```
twjdemo/
├── spring_01/                 # 后端Spring Boot项目
│   ├── src/main/java/
│   │   └── com/example/spring_01/
│   │       ├── config/        # 配置类
│   │       ├── controller/    # 控制器层
│   │       ├── mapper/        # 数据访问层
│   │       ├── pojo/          # 实体类
│   │       ├── service/       # 业务逻辑层
│   │       ├── utils/         # 工具类
│   │       └── interceptors/  # 拦截器
│   ├── src/main/resources/
│   │   ├── application.yml    # 配置文件
│   │   └── mapper/           # MyBatis映射文件
│   └── pom.xml               # Maven依赖配置
├── Hr-vue/                   # 前端Vue项目
│   ├── src/
│   │   ├── api/              # API接口
│   │   ├── components/       # 组件
│   │   ├── views/            # 页面视图
│   │   ├── router/           # 路由配置
│   │   ├── stores/           # 状态管理
│   │   └── utils/            # 工具函数
│   ├── package.json          # 依赖配置
│   └── vite.config.js        # Vite配置
├── deploy-scripts/           # 部署脚本
│   ├── deploy.sh            # 主部署脚本
│   ├── backend-deploy.sh    # 后端部署脚本
│   ├── frontend-deploy.sh   # 前端部署脚本
│   └── nginx.conf           # Nginx配置
└── README.md                # 项目说明文档
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Node.js 16+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 1. 克隆项目

```bash
git clone https://github.com/your-username/hr-management-system.git
cd hr-management-system
```

### 2. 数据库配置

创建MySQL数据库并导入数据：

```sql
CREATE DATABASE gl_1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 后端配置

1. 修改 `spring_01/src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gl_1?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC&allowMultiQueries=true&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

2. 配置阿里云OSS（可选）：

```yaml
aliyun:
  oss:
    endpoint: https://oss-cn-beijing.aliyuncs.com
    access-key-id: your-access-key-id
    access-key-secret: your-access-key-secret
    bucket-name: your-bucket-name
```

3. 启动后端服务：

```bash
cd spring_01
mvn clean install
mvn spring-boot:run
```

后端服务将在 `http://localhost:8080` 启动。

### 4. 前端配置

1. 安装依赖：

```bash
cd Hr-vue
npm install
```

2. 启动开发服务器：

```bash
npm run dev
```

前端服务将在 `http://localhost:5173` 启动。

## 📖 功能模块

### 用户管理
- 用户注册和登录
- 用户信息管理（增删改查）
- 用户权限管理
- 密码重置

### 部门管理
- 部门信息维护
- 部门层级管理
- 部门人员统计

### 岗位管理
- 岗位信息管理
- 员工岗位分配
- 岗位职责维护

### 文件管理
- 文件上传下载
- 阿里云OSS集成
- 文件类型限制

## 🔧 开发指南

### 后端开发

1. 使用MyBatis Plus进行数据库操作
2. 统一使用Result类封装返回结果
3. 使用JWT进行用户认证
4. 遵循RESTful API设计规范

### 前端开发

1. 使用Vue 3 Composition API
2. 使用Element Plus组件库
3. 使用Pinia进行状态管理
4. 使用Axios进行HTTP请求

## 🚀 部署指南

详细的部署指南请参考 [deploy-scripts/README.md](deploy-scripts/README.md)

### 快速部署

```bash
# 使用部署脚本
chmod +x deploy-scripts/deploy.sh
./deploy-scripts/deploy.sh
```

## 📝 API文档

### 用户相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/user/allUser` | 获取所有用户 |
| POST | `/user/addUser` | 添加用户 |
| GET | `/user/selects/{id}` | 根据ID查询用户 |
| PUT | `/user/users` | 更新用户信息 |
| DELETE | `/user/delete/{id}` | 删除用户 |

### 部门相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/dep/allDep` | 获取所有部门 |
| POST | `/dep/addDep` | 添加部门 |
| PUT | `/dep/updateDep` | 更新部门信息 |
| DELETE | `/dep/delete/{id}` | 删除部门 |

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系方式

- 项目链接：[https://github.com/your-username/hr-management-system](https://github.com/your-username/hr-management-system)
- 问题反馈：[Issues](https://github.com/your-username/hr-management-system/issues)

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者们！

---

⭐ 如果这个项目对你有帮助，请给它一个星标！
