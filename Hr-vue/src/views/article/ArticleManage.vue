<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'

// const dialogVisible = ref(false)

// //用户搜索时选中的分类id
// const categoryId = ref('')

// //用户搜索时选中的发布状态
// const state = ref('')

//文章列表数据模型
const articles = ref([
    {
        "id": "1",
        "staffName":"11",
        "email": "美食",
        "phone": "my",
        "username": "2023-09-02 12:06:59",
        "sex": "2023-09-02 12:06:59",
        "password": "2023-09-02 12:06:59",
        "picture": "2023-09-02 12:06:59",
        "postId": "2023-09-02 12:06:59",
        "degree": "2023-09-02 12:06:59",
        "marriage": "2023-09-02 12:06:59",
        "work": "2023-09-02 12:06:59",
    },
    {
        "id": "1",
        "staffName":"11",
        "email": "美食",
        "phone": "my",
        "username": "2023-09-02 12:06:59",
        "sex": "2023-09-02 12:06:59",
        "password": "2023-09-02 12:06:59",
        "picture": "2023-09-02 12:06:59",
        "postId": "2023-09-02 12:06:59",
        "degree": "2023-09-02 12:06:59",
        "marriage": "2023-09-02 12:06:59",
        "work": "2023-09-02 12:06:59",
    },
    {
        "id": "1",
        "staffName":"11",
        "email": "美食",
        "phone": "my",
        "username": "2023-09-02 12:06:59",
        "sex": "2023-09-02 12:06:59",
        "password": "2023-09-02 12:06:59",
        "picture": "2023-09-02 12:06:59",
        "postId": "2023-09-02 12:06:59",
        "degree": "2023-09-02 12:06:59",
        "marriage": "2023-09-02 12:06:59",
        "work": "2023-09-02 12:06:59",
    },
])

const uid=ref('')
//声明一个异步的函数
import { articleCategoryListService1, articleCategoryAddService1, articleCategoryUpdateService1, articleCategoryDeleteService1,articleAddService2 } from '@/api/article1.js'
const articleCategoryList1 = async () => {
    let result = await articleCategoryListService1();
    articles.value = result.data;
}
const articleList=async ()=>{

    console.log(uid.value)
    let result=await articleAddService2(uid.value);
    articles.value=result.data;
}

//articleList();
articleCategoryList1();

//控制添加分类弹窗
const dialogVisible = ref(false)
const dialogVisible1 = ref(false)
//添加分类数据模型
const articleModel = ref({
    id: '',
    staffName:'',
    email: '',
    phone: '',
    username: '',
    sex: '',
    password: '',
    picture: '',
    postId: '',
    degree: '',
    marriage: '',
    work: '',
})


const articleModel1 = ref({
    id: '',
    staffName:'',
    email: '',
    phone: '',
    username: '',
    sex: '',
    password: '',
    picture: '',
    postId: '',
    degree: '',
    marriage: '',
    work: '',
})
//添加分类表单校验
const rules = {
    postId: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    work: [
        { required: true, message: '请输入', trigger: 'blur' },
    ]
}

const rules1 = {
    postId: [
        { required: true, message: '请输入正确的岗位', trigger: 'blur' },
    ]
    
}



//调用接口,添加表单
import { ElMessage } from 'element-plus'
const addCategory = async () => {
    //调用接口
    let result = await articleCategoryAddService1(articleModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功')

    //调用获取所有文章分类的函数
    articleCategoryList1();
    dialogVisible.value = false;
}

//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
    dialogVisible1.value = true; title.value = '编辑用户信息'
    articleModel1.value.staffName = row.staffName;
    articleModel1.value.password = row.password;
    articleModel1.value.email = row.email;
    articleModel1.value.phone = row.phone;
    articleModel1.value.username = row.username;
    articleModel1.value.sex = row.sex;
    articleModel1.value.postId = row.postId;
    articleModel1.value.degree = row.degree;
    articleModel1.value.marriage = row.marriage;
    articleModel1.value.work = row.work;
    articleModel1.value.id = row.id
}

//编辑分类
const updateCategory = async () => {
    //调用接口
    console.log(articleModel1)
    let result = await articleCategoryUpdateService1(articleModel1.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')

    //调用获取所有分类的函数
    articleCategoryList1();

    //隐藏弹窗
    dialogVisible1.value = false;
}

//清空模型的数据
const clearData = () => {
    articleModel.value.email = '';
    articleModel.value.phone = '';
    articleModel.value.username = '';
    articleModel.value.sex = '';
    articleModel.value.postId = '';
    articleModel.value.degree = '';
    articleModel.value.marriage = '';
    articleModel.value.staffName = '';
    articleModel.value.password = '';
    articleModel.value.work = '';
}

// const clearData1 = () => {
//     articleModel1.value.email = '';
//     articleModel1.value.phone = '';
//     articleModel1.value.username = '';
//     articleModel1.value.sex = '';
//     articleModel1.value.postId = '';
//     articleModel1.value.degree = '';
//     articleModel1.value.marriage = '';
//     articleModel1.value.staffName = '';
//     articleModel1.value.password = '';
//     articleModel1.value.work = '';
// }

//删除分类
import { ElMessageBox } from 'element-plus'
const deleteCategory = (row) => {
    //提示用户  确认框

    ElMessageBox.confirm(
        '你确认要删除该用户信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await articleCategoryDeleteService1(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            articleCategoryList1();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })
}

// const articleList=async()=>{

//     let result=await articleAddService2(uid);
//     articles.value= result.data.items;
// }
// articleList();
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>用户信息</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加用户'; clearData()">添加用户</el-button>
                </div>
            </div>
        </template>
        <el-form inline>
            <el-form>
                <el-input v-model="uid" placeholder="请输入员工id" ></el-input>
                
                <el-button type="primary" @click="articleList()">搜索</el-button>
                <el-button type="primary" @click="articleCategoryList1()">返回</el-button>
            </el-form>
        </el-form>
    
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="员工id" prop="id"></el-table-column>
            <el-table-column label="员工姓名" prop="staffName"></el-table-column>
            <el-table-column label="邮箱" prop="email"></el-table-column>
            <el-table-column label="电话" prop="phone"> </el-table-column>
            <el-table-column label="用户名" prop="username"></el-table-column>
            <el-table-column label="性别" prop="sex"></el-table-column>
            <el-table-column label="密码" prop="password"></el-table-column>
            <!-- <el-table-column label="照片" prop="picture"></el-table-column> -->
            <el-table-column label="岗位id" prop="postId"></el-table-column>
            <!-- <el-table-column label="级别" prop="degree"></el-table-column> -->
            <el-table-column label="婚姻状况" prop="marriage"></el-table-column>
            <el-table-column label="工作经历" prop="work"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row); clearData1()"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="articleModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="邮箱">
                    <el-input v-model="articleModel.email" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="articleModel.phone" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="articleModel.sex" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="articleModel.username" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="级别">
                    <el-input v-model="articleModel.degree" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="婚姻状况">
                    <el-input v-model="articleModel.marriage" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="岗位id">
                    <el-input v-model="articleModel.postId" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="员工姓名">
                    <el-input v-model="articleModel.staffName" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="articleModel.password" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="工作经历">
                    <el-input v-model="articleModel.work" placeholder="请输入"></el-input>
                </el-form-item>
                <!-- <el-form-item>
                        <el-button type="primary" @click="addArticle('已发布')">添加</el-button>
                        <el-button type="info" @click="addArticle('草稿')">草稿</el-button>
                    </el-form-item> -->
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addCategory()"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="dialogVisible1" :title="title" width="30%">
            <el-form :model="articleModel1" :rules="rules1" label-width="100px" style="padding-right: 30px">
                <el-form-item label="邮箱">
                    <el-input v-model="articleModel1.email" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="articleModel1.phone" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="articleModel1.sex" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="articleModel1.username" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="级别">
                    <el-input v-model="articleModel1.degree" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="婚姻状况">
                    <el-input v-model="articleModel1.marriage" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="岗位id">
                    <el-input v-model="articleModel1.postId" minlength="1" maxlength="15" placeholder="请输入正确的岗位号"></el-input>
                    
                </el-form-item>
                <el-form-item label="员工姓名">
                    <el-input v-model="articleModel1.staffName" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="articleModel1.password" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="工作经历">
                    <el-input v-model="articleModel1.work" placeholder="请输入"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="updateCategory()"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>



