<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'

// const dialogVisible = ref(false)

// //用户搜索时选中的分类id
// const categoryId = ref('')

// //用户搜索时选中的发布状态
// const state = ref('')

//文章列表数据模型
const articles = ref([
    {
        "id": "1",
        "email": "美食",
        "mobilePhone": "my",
        "person_type": "2023-09-02 12:06:59",
        "sex": "2023-09-02 12:06:59",
        "empNo": "2023-09-02 12:06:59",
        "leftreason": "2023-09-02 12:06:59",
        "empStatus": "2023-09-02 12:06:59",
        "bossname": "2023-09-02 12:06:59",
        "bossid": "2023-09-02 12:06:59",
        "postid": "2023-09-02 12:06:59",
        "status": "2023-09-02 12:06:59",
        "userpostcode": "2023-09-02 12:06:59",
        "fullName": "2023-09-02 12:06:59",
        "departname": "2023-09-02 12:06:59"
    },
    {
        "id": "1",
        "email": "美食",
        "mobilePhone": "my",
        "person_type": "2023-09-02 12:06:59",
        "sex": "2023-09-02 12:06:59",
        "empNo": "2023-09-02 12:06:59",
        "leftreason": "2023-09-02 12:06:59",
        "empStatus": "2023-09-02 12:06:59",
        "bossname": "2023-09-02 12:06:59",
        "bossid": "2023-09-02 12:06:59",
        "postid": "2023-09-02 12:06:59",
        "status": "2023-09-02 12:06:59",
        "userpostcode": "2023-09-02 12:06:59",
        "fullName": "2023-09-02 12:06:59",
        "departname": "2023-09-02 12:06:59"
    },
    {
        "id": "1",
        "email": "美食",
        "mobilePhone": "my",
        "person_type": "2023-09-02 12:06:59",
        "sex": "2023-09-02 12:06:59",
        "empNo": "2023-09-02 12:06:59",
        "leftreason": "2023-09-02 12:06:59",
        "empStatus": "2023-09-02 12:06:59",
        "bossname": "2023-09-02 12:06:59",
        "bossid": "2023-09-02 12:06:59",
        "postid": "2023-09-02 12:06:59",
        "status": "2023-09-02 12:06:59",
        "userpostcode": "2023-09-02 12:06:59",
        "fullName": "2023-09-02 12:06:59",
        "departname": "2023-09-02 12:06:59"
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
    email: '',
    mobilePhone: '',
    person_type: '',
    sex: '',
    empNo: '',
    leftreason: '',
    empStatus: '',
    bossname: '',
    bossid: '',
    postid: '',
    status: '',
    userpostcode: '',
    fullName: '',
    departname: ''
})


const articleModel1 = ref({
    email: '',
    mobilePhone: '',
    person_type: '',
    sex: '',
    empStatus: '',
    bossname: '',
    bossid: '',
    leftreason: ''
})
//添加分类表单校验
const rules = {
    postid: [
        { required: true, message: '请输入岗位id', trigger: 'blur' },
    ],
    status: [
        { required: true, message: '请输入状态', trigger: 'blur' },
    ]
}

const rules1 = {
    email: [
        { required: true, message: '请输入邮箱', trigger: 'blur' },
    ],
    sex: [
        { required: true, message: '请输入性别', trigger: 'blur' },
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
    //数据拷贝
    articleModel1.value.email = row.email;
    articleModel1.value.mobilePhone = row.mobilePhone;
    articleModel1.value.person_type = row.person_type;
    articleModel1.value.sex = row.sex;
    articleModel1.value.empStatus = row.empStatus;
    articleModel1.value.bossname = row.bossname;
    articleModel1.value.bossid = row.bossid;
    articleModel1.value.leftreason = row.leftreason;
    //扩展id属性,将来需要传递给后台,完成分类的修改
    articleModel1.value.id = row.id
}

//编辑分类
const updateCategory = async () => {
    //调用接口
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
    articleModel.value.mobilePhone = '';
    articleModel.value.person_type = '';
    articleModel.value.sex = '';
    articleModel.value.empStatus = '';
    articleModel.value.bossname = '';
    articleModel.value.bossid = '';
    articleModel.value.leftreason = '';
}

const clearData1 = () => {
    articleModel1.value.email = '';
    articleModel1.value.mobilePhone = '';
    articleModel1.value.person_type = '';
    articleModel1.value.sex = '';
    articleModel1.value.empStatus = '';
    articleModel1.value.bossname = '';
    articleModel1.value.bossid = '';
    articleModel1.value.leftreason = '';
}

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
                <el-input v-model="uid" placeholder="请输入工号"></el-input>
                <el-button type="primary" @click="articleList()">搜索</el-button>
                <el-button type="primary" @click="articleCategoryList1()">返回</el-button>
            </el-form>
        </el-form>
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="用户id" prop="id"></el-table-column>
            <el-table-column label="邮箱" prop="email"></el-table-column>
            <el-table-column label="电话" prop="mobilePhone"> </el-table-column>
            <el-table-column label="级别" prop="person_type"></el-table-column>
            <el-table-column label="性别" prop="sex"></el-table-column>
            <el-table-column label="工号" prop="empNo"></el-table-column>
            <el-table-column label="辞职原因" prop="leftreason"></el-table-column>
            <el-table-column label="任岗状态" prop="empStatus"></el-table-column>
            <el-table-column label="上级名称" prop="bossname"></el-table-column>
            <el-table-column label="上级id" prop="bossid"></el-table-column>
            <el-table-column label="状态" prop="status"></el-table-column>
            <el-table-column label="岗位id" prop="postid"></el-table-column>
            <el-table-column label="任岗编码" prop="userpostcode"></el-table-column>
            <el-table-column label="职称" prop="fullName"></el-table-column>
            <el-table-column label="部门" prop="departname"></el-table-column>
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
                    <el-input v-model="articleModel.mobilePhone" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="articleModel.sex" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="级别">
                    <el-input v-model="articleModel.person_type" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="上级名称">
                    <el-input v-model="articleModel.bossname" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="上级id">
                    <el-input v-model="articleModel.bossid" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="任岗情况">
                    <el-input v-model="articleModel.empStatus" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="辞职原因">
                    <el-input v-model="articleModel.leftreason" placeholder="请输入"></el-input>
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
                <el-form-item label="邮箱" >
                    <el-input v-model="articleModel1.email"></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="articleModel1.mobilePhone" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="articleModel1.sex" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="级别">
                    <el-input v-model="articleModel1.person_type" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="上级名称">
                    <el-input v-model="articleModel1.bossname" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="上级id">
                    <el-input v-model="articleModel1.bossid" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="任岗情况">
                    <el-input v-model="articleModel1.empStatus" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="辞职原因">
                    <el-input v-model="articleModel1.leftreason" placeholder="请输入"></el-input>
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



