<template>
  <div id="app">
    <router-view/>
  </div>
</template>


<script>
  export default{
    provide(){
      return {
        reload: this.reload
      }
    },
    data() {
      return {
        isRouterAlive:true
      }
    },
    methods: {
      reload(){
        this.isRouterAlive = false
        this.$nextTick(function(){
          this.isRouterAlive = true
        })
      }
    },
  }
</script>

<style>
  #app{
    background-color: #f9f9f9;
    height: 100%;
  }
  * {
  text-decoration: none;
}
  .router-link-active {
    text-decoration: none;
    /* font-size: 20px; */
    /* font-style: italic;  */
  }
</style>
