import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '../components/Login.vue'
import Home from '../views/Home.vue'

import WriteBlog from '../views/blogs/WriteBlog'
import EditBlog from '../views/blogs/EditBlog'
import AllBlogs from '../views/blogs/AllBlogs'
import CategoryBlog from '../views/blogs/CategoryBlog'
import TagBlog from '../views/blogs/TagBlog'
import CommentBlog from '../views/blogs/CommentBlog'
import LinksBlog from '../views/blogs/LinksBlog'


Vue.use(VueRouter)

const routes = [
  { path: '/',redirect: '/admin',name: 'Login', component: Login, hidden: true  },

  { path: '/admin', name: 'Login', component: Login, hidden: true},
    
  {
    path: '/admin/home',
    name: '博客管理',
    component: Home,
    children:[
      { path: '/admin/writeblog', name: '写博客',  component: WriteBlog },
      { path: '/admin/editblog/:id', name: '编辑博客',  component: EditBlog, props: true, hidden: true },
      { path: '/admin/allblogs', name: '文章管理',  component: AllBlogs  },
      { path: '/admin/categoryblog', name: '分类专栏',  component: CategoryBlog  },
      { path: '/admin/tagblog', name: '标签管理',  component: TagBlog  },
      { path: '/admin/commentblog', name: '评论管理',  component: CommentBlog  },
      { path: '/admin/linksblog', name: '友链管理',  component: LinksBlog  },
      
    ]
  },
  
]

const router = new VueRouter({
  routes
})

export default router
