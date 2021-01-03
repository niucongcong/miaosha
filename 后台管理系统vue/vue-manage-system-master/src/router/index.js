import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const originalPush = Router.prototype.push
//修改原型对象中的push方法
Router.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Index.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/orderList',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/OrderInfoList.vue'),
                    meta: {
                        title: '订单列表',
                        all: true
                    }
                },
                {
                    path: '/logInfo',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/LogInfoList.vue'),
                    meta: {
                        title: '商品订单日志',
                        all: true
                    }
                },
                {
                    path: '/publicGoods',
                    component: () => import( /* webpackChunkName: "table" */ '../components/page/PublishGoods.vue'),
                    meta: {
                        title: '商品列表页',
                        permission: true
                    }
                },
                {
                    path: '/form',
                    component: () => import(/* webpackChunkName: "form" */ '../components/page/ChangePassword.vue'),
                    meta: { title: '修改密码' }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                },
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue'),
            meta: { title: '登录' }
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
