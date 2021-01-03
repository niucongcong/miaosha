<template>
    <div id="app">
        <router-view v-if="isReload"></router-view>
    </div>
</template>
<script>
export default {
    name: 'App',
    provide() {
        return {
            reload: this.reload
        };
    },
    data() {
        return {
            isReload: true
        };
    },
    methods: {
        reload() {
            this.isReload = false;
            this.$nextTick(() => {
                this.isReload = true;
            });
        }
    }
};
</script>
<style>
@import './assets/css/main.css';

@import './assets/css/color-dark.css';
/*@import "./assets/css/theme-green/color-green.css";   浅绿色主题*/
</style>