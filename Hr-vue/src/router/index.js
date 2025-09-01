import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import ArticleCategoryVue from '@/views/article/ArticleCategory.vue'
import ArticleManageVue from '@/views/article/ArticleManage.vue'
import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
import SalaryVue from '@/views/article/Salary.vue'
import RepunVue from '@/views/article/Repun.vue'
import DiaoduVue from '@/views/article/Diaodu.vue'
import DepartVue from '@/views/article/Depart.vue'
import JiuzhiVue from '@/views/article/Jiuzhi.vue'
import PostVue from '@/views/article/Post.vue'
//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: LayoutVue,redirect:'/article/manage', children: [
            { path: '/article/category', component: ArticleCategoryVue },
            { path: '/article/manage', component: ArticleManageVue },
            { path: '/user/info', component: UserInfoVue },
            { path: '/user/avatar', component: UserAvatarVue },
            { path: '/user/resetPassword', component: UserResetPasswordVue },
            { path: '/article/salary', component: SalaryVue },
            { path: '/article/repun', component: RepunVue },
            { path: '/article/diaodu', component: DiaoduVue },
            { path: '/article/depart', component: DepartVue },
            { path: '/article/jiuzhi', component: JiuzhiVue },
            { path: '/article/post', component: PostVue },


        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router
