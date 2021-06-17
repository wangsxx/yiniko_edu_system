import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/index',
    component: Layout,
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: '/dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: 'Example', icon: 'example' },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/table/index'),
        meta: { title: 'Table', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/teacherList',
    name: '讲师管理',
    meta: { title: '讲师管理', icon: 'example' },
    children: [
      {
        path: 'teacherList',
        name: '讲师列表',
        component: () => import('@/views/edu/teacher/teacherList'),
        meta: { title: '讲师列表', icon: 'table' }
      },
      {
        path: 'addTeacher',
        name: '添加讲师',
        component: () => import('@/views/edu/teacher/addTeacher'),
        meta: { title: '添加讲师', icon: 'tree' }
      },
        {
            path: 'editTeacher/:id',
            name: '修改讲师',
            component: () => import('@/views/edu/teacher/addTeacher'),
            meta: { title: '修改讲师', icon: 'tree' },
            hidden: true
        },
    ]
  },

    {
        path: '/subject',
        component: Layout,
        redirect: '/subject/subjectList',
        name: '课程分类管理',
        meta: { title: '课程分类管理', icon: 'example' },
        children: [
            {
                path: 'subjectList',
                name: '课程分类列表',
                component: () => import('@/views/edu/subject/subjectList'),
                meta: { title: '课程分类列表', icon: 'table' }
            },
            {
                path: 'addSubject',
                name: '添加课程分类',
                component: () => import('@/views/edu/subject/addSubject'),
                meta: { title: '添加课程分类', icon: 'tree' }
            }
        ]
    },


    {
        path: '/course',
        component: Layout,
        redirect: '/course/list',
        name: '课程管理',
        meta: { title: '课程管理', icon: 'example' },
        children: [
            {
                path: 'list',
                name: '课程列表',
                component: () => import('@/views/edu/course/courseList'),
                meta: { title: '课程列表', icon: 'table' }
            },
            {
                path: 'info',
                name: '添加课程',
                component: () => import('@/views/edu/course/info'),
                meta: { title: '添加课程', icon: 'tree' }
            },
            {
                path: 'info/:id',
                name: '编辑课程基本信息',
                component: () => import('@/views/edu/course/info'),
                meta: { title: '编辑课程基本信息', noCache: 'true' },
                hidden: true
            },
            {
                path: 'chapter/:id',
                name: '编辑课程大纲',
                component: () => import('@/views/edu/course/chapter'),
                meta: { title: '编辑课程大纲', noCache: 'true' },
                hidden: true
            },
            {
                path: 'publish/:id',
                name: '课程发布',
                component: () => import('@/views/edu/course/publish'),
                meta: { title: '课程发布', noCache: 'true' },
                hidden: true
            },
        ]
    },



  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        meta: { title: 'menu2' }
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
