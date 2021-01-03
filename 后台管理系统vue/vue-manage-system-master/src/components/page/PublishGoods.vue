<template>
    <div>
        <BaseTable :data="tableData" :build="build" @handleChange="handleChange"
                   @handleDelete="handleDelete" @handleEdit="handleEdit" @saveEdit="saveEdit"></BaseTable>
    </div>
</template>

<script>
import {
    getGoodsList,
    publishpromo
} from '../../api/index';

import BaseTable from './BaseTable.vue';

export default {
    name: 'basetable',
    data() {
        return {
            tableData: [],
            build_id: 1,
            room_id: 10,
            bed_id: 1,
            idx: -1,
            id: -1,
            build: []
        };
    },
    components: {
        BaseTable
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
            getGoodsList({
                user_id:localStorage.getItem("user_id"),
                token:localStorage.getItem('token')
            }).then(response => {
                if (response.status === 'success') {
                    var data = response.data;
                    console.log('getOrderList->');
                    console.log(data);
                    let datatoTable = [];
                    for (var index = 0; index < data.length; index++) {
                        if (data[index].promoStatus == 2) {
                            data[index]['state'] = 'success';
                        } else {
                            data[index]['state'] = 'info';
                        }
                        datatoTable.push(data[index]);
                    }
                    this.tableData = datatoTable;
                } else {
                    this.$message.error('获取商品列表失败');
                    return true;
                }
            }).catch(error => {
                this.$router.push('/login');
                return false;
            });
        },
        handleDelete(index, row) {

        },
        handleChange(value) {

        },
        handleEdit(index, row) {

        },
        // 保存编辑
        saveEdit(value) {
            console.log(value);

            if (localStorage.getItem("user_id")=='' ||localStorage.getItem('token')==''){
                this.$router.push('/login');
                return ;
            }

            if (value.price<=0 || value.promoName==""){
                this.$message.error('发布信息不能为空,价格必须为正数');
                return;
            }

            publishpromo({
                item_id: value.id,
                promo_name: value.promoName,
                price: value.price,
                user_id:localStorage.getItem("user_id"),
                token:localStorage.getItem('token')
            }).then(response => {
                if (response.status === 'success') {
                    this.$message.success('发布成功');
                } else {
                    this.$message.error(response.data['errMessage']);
                    return true;
                }
                this.getData();
            }).catch(error => {
                this.$message.error('发布失败进入异常');
                return false;
            });
        }
    }
};
</script>

<style scoped>

</style>