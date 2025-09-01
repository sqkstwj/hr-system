<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([
    {
        "id": "美食",
        "postName":"张三",
        "grade": "3000",
        "departId": "3000",
        "salary": "7073-09-07 17:06:59",
    },
    {
        "id": "美食",
        "postName":"张三",
        "grade": "3000",
        "departId": "3000",
        "salary": "7073-09-07 17:06:59",
    },
    {
        "id": "美食",
        "postName":"张三",
        "grade": "7000",
        "departId": "7000",
        "salary": "7073-09-07 17:06:59",
    }
])
//声明一个异步的函数
import { articleCategoryListService7, articleCategoryAddService7, articleCategoryUpdateService7, articleCategoryDeleteService7 } from '@/api/post.js'
const articleCategoryList = async () => {
    let result = await articleCategoryListService7();
    categorys.value = result.data;

}
articleCategoryList();
//控制添加分类弹窗
const dialogVisible = ref(false)
const dialogVisible1 = ref(false)


//添加分类数据模型
const categoryModel = ref({
    id: '',
    postName:'',
    grade: '',
    departId: '',
    salary: ''
})
const categoryModel1 = ref({
    id: '',
    postName:'',
    grade: '',
    departId: '',
    salary: ''
})
//添加分类表单校验
const rules = {
    id: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    postName: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    grade: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    departId: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    salary: [
        { required: true, message: '请输入', trigger: 'blur' },
    ]

}

const rules1 = {
    id: [
        { required: true, message: '请输入', trigger: 'blur' },
    ]
}


//调用接口,添加表单
import { ElMessage } from 'element-plus'
const addCategory = async () => {
    //调用接口
    let result = await articleCategoryAddService7(categoryModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功')

    //调用获取所有文章分类的函数
    articleCategoryList();
    dialogVisible.value = false;
}

//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
    dialogVisible1.value = true; title.value = '编辑'
    //数据拷贝
    categoryModel1.value.id = row.id;
    //扩展id属性,将来需要传递给后台,完成分类的修改
    categoryModel1.value.postName = row.postName;
    categoryModel1.value.grade = row.grade;
    categoryModel1.value.departId = row.departId;
    categoryModel1.value.salary = row.salary
}

//编辑分类
const updateCategory = async () => {
    //调用接口
    let result = await articleCategoryUpdateService7(categoryModel1.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')

    //调用获取所有分类的函数
    articleCategoryList();

    //隐藏弹窗
    dialogVisible.value = false;
}

//清空模型的数据
const clearData = () => {
    categoryModel.value.id = '';
    categoryModel.value.postName = '';
    categoryModel.value.grade = '';
    categoryModel.value.departId='';
    categoryModel.value.salary='';


}

//清空模型的数据
// const clearData1 = () => {
//     categoryModel1.value.id = '';
//     categoryModel1.value.postName = '';
//     categoryModel1.value.grade = '';
//     categoryModel1.value.departId='';
//     categoryModel1.value.salary='';
// }

//删除
import { ElMessageBox } from 'element-plus'
const deleteCategory = (row) => {
    //提示用户  确认框

    ElMessageBox.confirm(
        '你确认要删除该调度信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await articleCategoryDeleteService7(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            articleCategoryList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>调度管理</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加'; clearData()">添加</el-button>
                </div>
            </div>
        </template>
        <el-table :data="categorys" style="width: 100%">
            <el-table-column label="岗位id" prop="id"></el-table-column>
            <el-table-column label="岗位名" prop="postName"></el-table-column>
            <el-table-column label="级别" prop="grade"></el-table-column>
            <el-table-column label="所在部门名" prop="departId"></el-table-column>
            <el-table-column label="薪资" prop="salary"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row);clearData1()"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="岗位id" prop="id">
                    <el-input v-model="categoryModel.id" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="岗位名" prop="postName">
                    <el-input v-model="categoryModel.postName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="级别" prop="grade">
                    <el-input v-model="categoryModel.grade" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="所在部门名" prop="departId">
                    <el-input v-model="categoryModel.departId" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="薪资" prop="salary">
                    <el-input v-model="categoryModel.salary" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="addCategory()"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="dialogVisible1" :title="title" width="30%">
            <el-form :model="categoryModel1" :rules="rules1" label-width="100px" style="padding-right: 30px">
                <el-form-item label="岗位名" prop="postName">
                    <el-input v-model="categoryModel1.postName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="级别" prop="grade">
                    <el-input v-model="categoryModel1.grade" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="所在部门名" prop="departId">
                    <el-input v-model="categoryModel1.departId" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="薪资" prop="salary">
                    <el-input v-model="categoryModel1.salary" minlength="1" maxlength="15"></el-input>
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