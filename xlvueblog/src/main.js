import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui';  //引入Element ui
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';

Vue.prototype.axios = axios;
// axios.defaults.baseURL = "/api"
axios.defaults.withCredentials = true
Vue.use(axios);


Vue.use(ElementUI); //使用 ElementUI

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
