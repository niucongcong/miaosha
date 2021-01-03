<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 商品订单日志
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="订单ID" width="150" align="center"></el-table-column>
                <el-table-column prop="userId" label="用户ID"  align="center"></el-table-column>
                <el-table-column prop="userName" label="用户名" w align="center"></el-table-column>
                <el-table-column prop="itemModel.id" label="商品ID"  align="center"></el-table-column>
                <el-table-column prop="itemModel.title" label="商品名"  align="center"></el-table-column>
                <el-table-column prop="itemModel.description" label="商品详细描述"  align="center"></el-table-column>
                <el-table-column prop="amount" label="购买商品数量" width="55" align="center"></el-table-column>
                <el-table-column prop="totalPrice" label="总价" width="55" align="center"></el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import {
    getOrderList
} from '../../api/index';
export default {
    name: 'OrderInfo',
    data() {
        return {
            tableData: [],
        }
    },
    created() {
        this.getData();
    },
    activated() {
        this.getData();
    },
    methods: {
        getData() {
            if (localStorage.getItem("user_id")==''||localStorage.getItem('token')==''){
                this.$router.push('/login');
                return ;
            }
            getOrderList({
                user_id:localStorage.getItem("user_id"),
                token:localStorage.getItem('token')
            }).then(response => {
                if (response.status === 'success') {
                    let data = response.data;
                    this.tableData = data;
                }else {
                    this.$message.error(response.data['errMessage']);
                    return true;
                }
            }).catch(error => {
                console.log(error.message)
                this.$router.push('/login');
                return false;
            });
        }
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}

.blue {
    color: #00e1ff;
}

.mr10 {
    margin-right: 10px;
}

.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}

.demo-table-expand {
    font-size: 0;
}

.demo-table-expand label {
    width: 90px;
    color: #99a9bf;
}

.demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 100%;
}
</style>