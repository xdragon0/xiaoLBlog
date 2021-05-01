//配置请求转发的代理

//定义代理的对象
let proxyObj = {};

//拦截http请求
proxyObj['/'] = {
    ws: false,  //关掉websocket
    target: 'http://localhost:8082', //目标转发的地址
    changeOrigin: true,
    pathRewrite:{  //请求地址重写
        '^/': ''  //拦截到的地址不去修改它
    }
}

//把上面的导出来
module.exports = {
    devServer:{  //配置开发环境
        host: 'localhost', //端口号
        // host: '192.168.49.27', //端口号
        port: 8080,
        proxy: proxyObj  //代理对象
    }
}
