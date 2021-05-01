import axios from 'axios'
/* 单独引入 此时调用方法为 Message(options)。我们也为每个 type 定义了各自的方法，如 Message.success(options)。
   并且可以调用 Message.closeAll() 手动关闭所有实例。*/
import { Message } from 'element-ui'
//导入路由
import router from '../router'

//处理响应信息的响应拦截器

/*上面已经导入了axios的对象了，这个对象里面有个响应的拦截器axios.interceptors.response.use(),use里面的data是服务端响应给你的数据，
该拦截器有两个回调函数，一个是success，一个是error,和jQuery里面的ajax一样请求数据的时候也有两个回调函数，一个success，一个error,
可以简单的理解为http的响应码是200的，它会进入到success方法中来，400以上的会进入到error来
*/
axios.interceptors.response.use(success => {
    //success.status: http的响应码   
    //success.data.status == 500: 返回json的status
    if(success.status && success.status == 200 && success.data.status == 500 ){
        //把后台的出错消息写出来
        Message.error({message: success.data.msg})
        return;
    }
    if(success.data.msg){
        //把操作成功的信息也弹出来
        Message.success({message: success.data.msg})
    }
    return success.data
},error =>{ //失败的处理
    if(error.response.status == 504 || error.response.status == 404){
        Message.error({message: "服务器被吃了哦！"})
    }else if(error.response.status == 403){
        Message.error({message: "权限不足，请联系管理员"})
    }else if(error.response.status == 401){
        Message.error({message: "尚未登录"})
        router.replace('/')
    }else{
        //服务器返回的错误信息
        if(error.response.data.msg){
            Message.error({message: error.response.data.msg})
        }else{
            Message.error({message: "未知错误"})
        }
    }
    return;
})

//封装请求
let base = ''; //避免前缀要一个一个去改，太麻烦
//
export const postKeyValueRequest=(url,params)=>{

    return axios({
        method: 'post',  
        url: `${base}${url}`, //特别注意这里是``引用变量的方式,而不是''
        data: params, //直接这样以json的形式传给服务器，这是不支持的,所有还需要定义下面的transformRequest
        transformRequest: [function (data){
            let ret = '';
            for(let i in data){
               //往ret上面追加变量 
                ret+=encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&'
            }
            // console.log(ret);
            return ret;
        }],
        //定义header
        headers:{
            'Content-Type':'application/x-www-form-urlencoded '
        }
    })

}

export const postPicRequest=(url,params)=>{
    return axios({
        method: 'post',  
        url: `${base}${url}`, //特别注意这里是``引用变量的方式,而不是''
        data: params,
        //定义header
        headers:{
            'Content-Type':'multipart/form-data'
        }
    })

}

export const postRequest=(url, params)=>{
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params  //这是用json来传递的，所以不用加transformRequest转换了
    })
}

export const putRequest=(url, params)=>{
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params //这是用json来传递的，所以不用加transformRequest转换了
    })
}

export const getRequest=(url, params)=>{
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params //这是用json来传递的，所以不用加transformRequest转换了
    })
}

export const deleteRequest=(url, params)=>{
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params //这是用json来传递的，所以不用加transformRequest转换了
    })
}
