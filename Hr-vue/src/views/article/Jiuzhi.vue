<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([
    {
        "id": 3,
        "staffId": "美食",
        "staffName":"张三",
        "status": "3000",
        "startTime": "3000",
        "endTime": "6063-09-06 16:06:59",
    },
    {
        "id": 3,
        "staffId": "美食",
        "staffName":"张三",
        "status": "3000",
        "startTime": "3000",
        "endTime": "6063-09-06 16:06:59",
    },
    {
        "id": 3,
        "staffId": "美食",
        "staffName":"张三",
        "status": "6000",
        "startTime": "6000",
        "endTime": "6063-09-06 16:06:59",
    }
])
//声明一个异步的函数
import { articleCategoryListService6, articleCategoryAddService6, articleCategoryUpdateService6, articleCategoryDeleteService6 } from '@/api/jiuzhi.js'
const articleCategoryList = async () => {
    let result = await articleCategoryListService6();
    categorys.value = result.data;

}
articleCategoryList();
//控制添加分类弹窗
const dialogVisible = ref(false)
const dialogVisible1 = ref(false)


//添加分类数据模型
const categoryModel = ref({
    staffId: '',
    staffName:'',
    status: '',
    startTime: '',
    endTime: ''
})
const categoryModel1 = ref({
    id:'',
    staffId: '',
    staffName:'',
    status: '',
    startTime: '',
    endTime: ''
})
//添加分类表单校验
const rules = {
    staffId: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    staffName: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    status: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    startTime: [
        { required: true, message: '请输入', trigger: 'blur' },
    ],
    endTime: [
        { required: true, message: '请输入', trigger: 'blur' },
    ]

}

const rules1 = {
    staffId: [
        { required: true, message: '请输入', trigger: 'blur' },
    ]
}


//调用接口,添加表单
import { ElMessage } from 'element-plus'
const addCategory = async () => {
    //调用接口
    let result = await articleCategoryAddService6(categoryModel.value);
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
    categoryModel1.value.staffId = row.staffId;
    //扩展id属性,将来需要传递给后台,完成分类的修改
    categoryModel1.value.staffName = row.staffName;
    categoryModel1.value.status = row.status;
    categoryModel1.value.startTime = row.startTime;
    categoryModel1.value.endTime = row.endTime
}

//编辑分类
const updateCategory = async () => {
    //调用接口
    let result = await articleCategoryUpdateService6(categoryModel1.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')

    //调用获取所有分类的函数
    articleCategoryList();

    //隐藏弹窗
    dialogVisible.value = false;
}

//清空模型的数据
const clearData = () => {
    categoryModel.value.staffId = '';
    categoryModel.value.staffName = '';
    categoryModel.value.status = '';
    categoryModel.value.startTime='';
    categoryModel.value.endTime='';


}

//清空模型的数据
// const clearData1 = () => {
//     categoryModel1.value.staffId = '';
//     categoryModel1.value.staffName = '';
//     categoryModel1.value.status = '';
//     categoryModel1.value.startTime='';
//     categoryModel1.value.endTime='';
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
            let result = await articleCategoryDeleteService6(row.id);
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
            <el-table-column label="id" prop="id"> </el-table-column>
            <el-table-column label="员工id" prop="staffId"></el-table-column>
            <el-table-column label="员工姓名" prop="staffName"></el-table-column>
            <el-table-column label="状态" prop="status"></el-table-column>
            <el-table-column label="开始时间" prop="startTime"></el-table-column>
            <el-table-column label="结束时间" prop="endTime"></el-table-column>
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
                <el-form-item label="员工id" prop="staffId">
                    <el-input v-model="categoryModel.staffId" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="员工姓名" prop="staffName">
                    <el-input v-model="categoryModel.staffName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-input v-model="categoryModel.status" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="开始时间" prop="startTime">
                    <el-input v-model="categoryModel.startTime" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="结束时间" prop="endTime">
                    <el-input v-model="categoryModel.endTime" minlength="1" maxlength="15"></el-input>
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
                <el-form-item label="员工姓名" prop="staffName">
                    <el-input v-model="categoryModel1.staffName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-input v-model="categoryModel1.status" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="开始时间" prop="startTime">
                    <el-input v-model="categoryModel1.startTime" minlength="1" maxlength="15"></el-input>
                </el-form-item>
                <el-form-item label="结束时间" prop="endTime">
                    <el-input v-model="categoryModel1.endTime" minlength="1" maxlength="15"></el-input>
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