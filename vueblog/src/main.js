import Vue from 'vue'
import App from './App.vue'
import router from './router'

import ElementUI from 'element-ui';  //引入Element ui
import { Message } from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';

import {postKeyValueRequest} from "./utils/api";
import {postPicRequest} from "./utils/api";
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";


import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// 使用 mavonEditor
Vue.use(mavonEditor)

Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.postPicRequest = postPicRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;


Vue.config.productionTip = false

Vue.use(ElementUI); //使用 ElementUI

router.beforeEach((to,from,next)=>{
  //to: 将要去哪里  from: 从哪里来的  next: 下一步，放行
  //如果要去的页面是Login页，直接放行
  if(to.path=='/' || to.path=='/admin'){
    next();
  }else{
    //如果有user，说明已经登录了
    if(window.sessionStorage.getItem("user")){
        next();
    }else{
      //没有登录就去登录页并记录你刚才要去的路由
      //to.path是你刚要去的路由，但因未登录被拦截了，在登录之后就会直接去到你刚访问的路由
      Message.error({message: "尚未登录，请先登录"})
      next('/?redirect=' + to.path);
    }
   
  }
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
