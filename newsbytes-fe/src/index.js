import { createRouter, createWebHistory } from 'vue-router';

import About from './components/About.vue';
import Home from './components/Home.vue';

import Login from './components/signing/Login.vue';
import Logout from './components/signing/Logout.vue';

import EditorDashboard from './components/news/EditorDashboard.vue';
import News from './components/news/News.vue';


const routes = [
{
     path: '/',
     redirect: { path: "/home" },
   },
   {
        path: '/home',
        name: 'homePage',
        component: Home,
      },
{
     path: '/about',
     name: 'aboutPage',
     component: About,
   },
{
     path: '/login',
     name: 'loginPage',
     component: Login,
   },
   {
        path: '/logout',
        name: 'logoutPage',
        component: Logout,
      },
            {
                    path: '/editor-dashboard',
                    name: 'editorDashboardPage',
                    component: EditorDashboard,
                  },
{
        path: '/:type',
        name: 'newsPage',
        component: News,
      },



];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
