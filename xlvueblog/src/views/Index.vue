<template>
  
  <div class="bg">
    <!--  时钟画布  -->
      <!-- <canvas id="clock"  class="canvas-1" width="500" height="500"></canvas> -->
      <div class="text" >
          <h2>XL-Blog</h2>
          <router-link to="/home">
            <!-- <el-button type="primary">Enter Blog</el-button> -->
            Enter Blog
          </router-link>
      </div>
      <div>
        
      </div>
  </div>
</template>

<script>
// import aff from '../assets/js/clock.js'
export default {
  inject:['reload'],
  name: 'Index',
  data () {
   return {
    timer: '',
    value: 0
   }
  },
  mounted() {
  },

  methods:{
    init(){
      //定义一个函数绘制时钟
    var context=document.getElementById("clock").getContext("2d");
    //每次绘制新图形之前清空当前区域
    context.clearRect(0,0,500,500);
    // var hs = [" I ", "I I", "III", "I V", " V ", "V I", "VII", "VIII", "I X", " X ", "X I", "XII"];
    // var hs = [" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 ", " 10 ", " 11 ", " 12 "];
    //画表盘
    context.beginPath();
    context.arc(250,250,200,0,2*Math.PI);
    // context.strokeStyle="#A9A9A9";
    context.strokeStyle="#bcbff7";
    context.lineWidth=15;
    context.stroke();
    context.closePath();
    //循环绘制分钟刻度
    //重新定义画布的原点
    context.translate(250,250);
    //绘制分钟刻度
    for (var i=0;i<60;i++) {
        //每次旋转6度
        context.rotate(6*Math.PI/180);
        context.beginPath();
        context.moveTo(0,-185);
        context.lineTo(0,-190);
        context.lineWidth=4;
        context.strokeStyle="lightgreen";
        context.stroke();
        context.closePath();
    }
    //绘制小时刻度
    for(var i = 0; i < 12; i++) {
        //旋转30度
        context.rotate(30 * Math.PI / 180);
        context.beginPath();
        context.moveTo(0, -180);
        context.lineTo(0, -190);
        context.strokeStyle = "#008000";
        context.stroke();
        context.closePath();

        context.beginPath();
        context.font="12px  微软雅黑";
        //文字轮廓
        context.fillStyle = "red";
        // context.fillText(hs[i], -8, -160);
        context.closePath();
    }
    context.textAlign = 'center';
    context.textBaseline = 'middle';
    context.fillStyle = 'red';
    context.font = '20px 微软雅黑';
    for (var i = 1; i < 13; i++) {
        context.fillText(i,Math.sin(2*Math.PI/12*i)*160,Math.cos(2*Math.PI/12*i)*-160);
    }

    //获取现在的系统时间
    var date = new Date();
    var hour = date.getHours();
    var minute = date.getMinutes();
    //获得的小时是一个整数,需要加上分钟代表的小数
    hour = hour + minute / 60;
    //如果是下午减去12
    hour = hour > 12 ? hour - 12 : hour;
    var second = date.getSeconds();
    //画时针
    context.rotate(hour * 30 * Math.PI / 180);
    context.beginPath();
    //设置线段开始点
    context.moveTo(0, 20);
    //设置线段结束点
    context.lineTo(0, -80);
    context.lineWidth = 5;
    context.strokeStyle = "#008000";
    context.stroke();
    context.closePath();
    //将画布角度还原到初始状态
    context.rotate(-hour * 30 * Math.PI / 180);

    //画分针
    context.rotate(minute * 6 * Math.PI / 180);
    context.beginPath();
    //设置线段开始点
    context.moveTo(0, 20);
    //设置线段结束点
    context.lineTo(0, -140);
    context.lineWidth = 4;
    context.strokeStyle = "lightgreen";
    context.stroke();
    context.closePath();
    //将画布角度还原到初始状态
    context.rotate(-minute * 6 * Math.PI / 180);

    //画秒针
    context.rotate(second * 6 * Math.PI / 180);
    context.beginPath();
    //设置线段开始点
    context.moveTo(0, 20);
    //设置线段结束点
    context.lineTo(0, -175);
    context.lineWidth = 3;
    context.strokeStyle = "crimson";
    context.stroke();
    context.closePath();
    //在圆形的中心点绘制一个圆形
    context.beginPath();
    context.arc(0,0,10,0,2*Math.PI);
    context.fillStyle="gold";
    context.fill();
    context.closePath();
    //在秒针上端绘制一个圆形
    context.beginPath();
    context.arc(0,-155,8,0,2*Math.PI);
    context.fillStyle="gold";
    context.fill();
    context.closePath();
    //将画布角度还原到初始状态
    context.rotate(-second * 6 * Math.PI / 180);
    //将圆心的位置重置到左上角
    context.translate(-250, -250);

    this.reload()
    // this.$router.go(0)
    // setInterval(this.init(),1000);
    }
  }
}
</script>

<style scoped>
    /* @import url(~@/assets/css/mycss.css); */
   /* 背景 */
    .bg{
      /* background-image: url("../assets/image/bg.jpg");  */
      background: linear-gradient(to right, #00c3ff, #ffff1c);
      
      height: 100%;
      width: 100%;
      background-size: cover;
      position: fixed;  
    }
    /* 文本 */
    .text{
      text-align: center;
      margin-top: 20%;
    }
    
  a {
    position: relative;
    width: 100px;
    height: 60px;
    text-align: center;
    line-height: 60px;
    margin: 45px;
    text-decoration: none;
    color: aqua;
    font-size: 20px;
    padding: 10px 30px;
    text-transform: uppercase;
    transition: 0.5s;
    overflow: hidden;
    -webkit-box-reflect: below 1px linear-gradient(transparent, #1113);
  }
  a::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 20px;
    height: 20px;
    border-top: 2px solid aqua;
    border-left: 2px solid aqua;
    transition: 0.5s;
    transition-delay: 0.5s;
  }
  a::after {
    content: '';
    position: absolute;
    bottom: 0;
    right: 0;
    width: 20px;
    height: 20px;
    border-bottom: 2px solid aqua;
    border-right: 2px solid aqua;
    transition: 0.5s;
    transition-delay: 0.5s;
  }
  a:hover::before, a:hover::after {
    width: 100%;
    height: 100%;
    transition-delay: 0s;
  }
  a:hover {
    background-color: aqua;
    color: #000;
    box-shadow: 0 0 50px #21ebff;
    transition-delay: 0.3s;
  }
  /* a:nth-child(1) {
    filter: hue-rotate(150deg);
  } */
  /* a:nth-child(3) {
    filter: hue-rotate(270deg);
  }
  a:nth-child(4) {
    filter: hue-rotate(400deg);
  }
  a:nth-child(5) {
    filter: hue-rotate(70deg);
  } */
</style>