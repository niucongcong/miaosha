<template>
    <div>
        <el-row :gutter="20">
            <el-col>
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img src="../../assets/img/wxLogo.jpg" class="user-avator" alt />
                        <div class="user-info-cont">
                            <div class="user-info-name">{{name}}</div>
                            <div>{{role}}</div>
                        </div>
                    </div>
                    <div class="user-info-list">
                        上次登录时间：
                        <span>{{loginTime}}</span>
                    </div>
                    <div class="user-info-list">
                        上次登录地点：
                        <span>北京</span>
                    </div>
                </el-card>
            </el-col>
        </el-row>
        <el-row :gutter="20">
            <el-col>
                <el-card shadow="hover">
                    <schart ref="bar" class="schart" canvasId="bar" :options="options"></schart>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import Schart from 'vue-schart';
    import bus from '../common/bus';
    export default {
        name: 'dashboard',
        data() {
            return {
                name: localStorage.getItem('user_name'),
                hasRoom: 71.4,
                noRoom: 12,
                loginTime: '2020-10-31 00:00:00',
                options: {
                    type: 'bar',
                    title: {
                        text: '商品销售分布'
                    },
                    xRorate: 25,
                    labels: ['第一季度', '第二季度', '第三季度',"第四季度"],
                    datasets: [{
                            label: 'iphone8',
                            data: [50, 70, 89,10]
                        },
                        {
                            label: 'iphone9',
                            data: [164, 178, 190,10]
                        },
                        {
                            label: 'iphone10',
                            data: [144, 198,20,100]
                        },{
                            label: 'iphone11',
                            data: [50, 70, 89,200]
                        },
                    ]
                }
            };
        },
        components: {
            Schart
        },
        computed: {
            role() {
                return this.name === 'admin' ? '超级管理员' : this.name === 'assistant' ? '发布小能手' : '普通用户';
            }
        },
        created() {
            this.changeDate();
            this.getData();
        },
        methods: {
            changeDate() {
                const now = new Date().getTime();
                // console.log(now);
                this.loginTime = this.formatDate(now);
                // console.log(this.loginTime);
            },
            getData() {
            },
            formatDate(date) {
                var date = new Date(date);
                var YY = date.getFullYear() + '-';
                var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                var
                    DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
                var hh = (date.getHours() < 10 ? '0' +
                    date.getHours() : date.getHours()) + ':';
                var mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() :
                    date.getMinutes()) + ':';
                var ss = (date.getSeconds() < 10 ? '0' + date.getSeconds() :
                    date.getSeconds());
                return YY + MM + DD + " " + hh + mm + ss;
            }
        },
        activated() {
            this.getData();
        }
    };
</script>


<style scoped>
    .el-row {
        margin-bottom: 20px;
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }

    .todo-item {
        font-size: 14px;
    }

    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }

    .schart {
        width: 100%;
        height: 300px;
    }
</style>