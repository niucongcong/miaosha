<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>修改密码</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box" style="width: 300px;">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="姓名">
                        <el-input v-model="form.username" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                        <el-input type='password' v-model="form.password"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码">
                        <el-input type='password' v-model="form.newPassword"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">表单提交</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    import {
        changePassword
    } from '../../api/index';
    export default {
        name: 'baseform',
        data() {
            return {
                form: {
                    username: localStorage.getItem('user_name'),
                    password: '',
                    newPassword: ''
                }
            };
        },
        methods: {
            onSubmit() {
                changePassword({
                    username: this.form['username'],
                    password: this.form['password'],
                    newPassword: this.form['newPassword']
                }).then(response => {
                    if (response.status === 'success') {
                        this.$message.success('修改密码成功！');
                        this.$router.push('/');
                    } else {
                        this.$message.error('修改密码失败');
                        return true;
                    }
                }).catch(error => {
                    reject(error)
                })
            }
        }
    };
</script>