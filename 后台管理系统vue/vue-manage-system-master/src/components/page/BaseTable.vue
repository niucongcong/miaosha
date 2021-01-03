<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 商品列表页
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-table :data="data" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="title" label="商品名"></el-table-column>
                <el-table-column prop="description" label="商品描述"></el-table-column>
                <el-table-column prop="sales" label="已售"></el-table-column>
                <el-table-column prop="price" label="商品价格"></el-table-column>
                <el-table-column prop="stock" label="库存"></el-table-column>
                <el-table-column label="状态" align="center">
                    <template slot-scope="scope">
                        <el-tag :type="scope.row.state">
                            {{scope.row.promoStatus==2?'已发布':'未发布'}}</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" class="blue"
                            @click="handleEdit(scope.$index, scope.row)">发布
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog title="发布商品" :visible.sync="editVisible" width="30%">
            <el-form :model="form" ref="form" label-width="70px">
                <el-form-item label="商品ID" prop="name">
                    <el-input v-model="form.id" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="发布信息" prop="name">
                    <el-input v-model="form.promoName"></el-input>
                </el-form-item>
                <el-form-item label="商品价格" prop="name">
                    <el-input v-model="form.price"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {
        getHasRoomList,
        studentChangeRoom,
        studentRemoveRoom
    } from '../../api/index';
    export default {
        name: 'basetable',
        props: {
            data: {
                type: Array,
                required: true
            },
            build: {
                type: Array,
                required: true
            }
        },
        data() {
            return {
                editVisible: false,
                form: {}
            }
        },
        methods: {
            handleChange(value) {
                this.$emit('handleChange', value);
            },
            handleDelete(index, row) {

            },
            handleEdit(index, row) {
                if (row.promoStatus==2){
                    this.$message.info('该商品已经发布，不许再发布');
                    return ;
                }
                this.editVisible = true;
                this.form=row;
                this.form['promoName']='';
            },
            // 保存编辑
            saveEdit() {
                this.editVisible = false;
                //添加发布信息为空的处理 以及price必须是数字的处理
                this.$emit('saveEdit',this.form);
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