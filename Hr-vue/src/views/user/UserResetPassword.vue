<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore();
const userInfo = ref({ ...userInfoStore.info })
const rules = {
    oldPwd: [
        { required: true, message: '请输入原密码', trigger: 'blur' },
        {
            pattern: /^\S{5,17}$/,
            message: '昵称必须是5-17位的非空字符串',
            trigger: 'blur'
        }
    ],
    newPwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,17}$/,
            message: '密码必须是5-17位的非空字符串',
            trigger: 'blur'
        }
    ],
    rePwd: [
        { required: true, message: '请再次入新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,17}$/,
            message: '密码必须是5-17位的非空字符串',
            trigger: 'blur'
        }
    ]
}


//修改个人信息
import { userAvatarUpdateService1 } from '@/api/user.js'
import { ElMessage } from 'element-plus'


const updateUserInfo1 = async () => {
        //调用接口
        let result = await userAvatarUpdateService1(userInfo.value);
        ElMessage.success(result.msg ? result.msg : '修改成功');


}

const clearRegisterData1 = () => {
    userInfo.value = {
        oldPwd: '',
        newPwd: '',
        rePwd: ''
    }
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="旧密码" prop="oldPwd">
                        <el-input v-model="userInfo.oldPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPwd">
                        <el-input v-model="userInfo.newPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="rePwd">
                        <el-input v-model="userInfo.rePwd"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo1();clearRegisterData1()">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>