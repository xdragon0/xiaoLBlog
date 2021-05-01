<template>
  <div>
      <Head aIndex="4"></Head>
      <Comment :blogId="-1" style="width: 60%;margin: auto;"></Comment>
      <Foot></Foot>
  </div>
</template>

<script>

import Head from '@/components/Head'
import Foot from '@/components/Foot'
import Comment from '@/components/Comment'

export default {
  name: 'message',
  components:{ Head,Foot,Comment },
  data () {
   return {
   }
  },
  methods:{
  }
}
</script>

<style scoped>

</style>