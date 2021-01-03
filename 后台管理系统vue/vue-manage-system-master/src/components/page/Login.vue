<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">后台管理系统</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="param.username" placeholder="username">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="param.password"
                              @keyup.enter.native="submitForm()">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div ref="codeContainer" @click="changeCode()">
                    <img :src="imgHtml">
                </div>
                <el-input
                    placeholder="请输入验证码"
                    v-model="param.verifyCode"
                    clearable>
                </el-input>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>

            </el-form>
        </div>
    </div>
</template>

<script>

import {
    login
} from '../../api/index';

export default {
    inject: ['reload'],
    data: function() {
        return {
            imgHtml: 'http://127.0.0.1:8081/user/generateverifycode',
            param: {
                username: '',
                password: '',
                verifyCode: ''
            },
            rules: {
                username: [{
                    required: true,
                    message: '请输入用户名',
                    trigger: 'blur'
                }],
                password: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }]
            }
        };
    },
    methods: {
        changeCode:function(){
            let num=Math.ceil(Math.random()*10);
            this.imgHtml = "http://127.0.0.1:8081/user/generateverifycode?" + num;
        },
        submitForm() {
            this.$refs.login.validate(valid => {
                if (valid) {
                    if (this.param['username']==""||this.param['password']==""||this.param['verifyCode'].length!=4){
                        this.$message.info("请输入正确信息");
                        return;
                    }
                    login({
                        username: this.param['username'],
                        password: this.param['password'],
                        verifyCode: this.param['verifyCode']
                    }).then(response => {
                        if (response.status === 'success') {
                            this.$message.success('登录成功');
                            localStorage.setItem('user_name', response.data['userName']);
                            localStorage.setItem('user_role', response.data['role_name']);
                            localStorage.setItem('user_id', response.data['id']);
                            localStorage.setItem('token', response.data['token']);
                            this.$router.push('/dashboard');
                        } else {
                            console.log(response);
                            this.$message.error(response.data['errMessage']);
                            let num=Math.ceil(Math.random()*10);
                            this.imgHtml = "http://127.0.0.1:8081/user/generateverifycode?" + num;
                            return true;
                        }
                    }).catch(error => {
                        this.$message.error('请输入正确的账号和密码和验证码');
                        console.log('error submit!!');
                        return false;
                    });
                } else {

                }
            });
        }
    }
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}

.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}

.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}

.ms-content {
    padding: 30px 30px;
}

.login-btn {
    margin-top: 30px;
    text-align: center;
}

.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}

.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>