<template>
<Header />
<div class="container text-center">
    <div class="row">
        <div class="col-3 p-5">
            <img src="/login.jpg" class="img-fluid" alt="Login">
        </div>
        <div class="col p-5 mt-4">
                    <form @submit.prevent="login">
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <h4>Login</h4>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Username</label>
                            <div class="col-sm-5">
                                <input class="form-control" type="text" id="username" placeholder="Username" required v-model="uname" autofocus />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Password</label>
                            <div class="col-sm-5">
                                <input class="form-control" type="password" id="password" placeholder="Password" required v-model="password" />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <button class="btn btn-dark" type="submit">Login</button>
                            </div>
                        </div>
                    </form>
                </div>
</div>
</div>

<Footer />
</template>

<script>
import axios from 'axios';
import Header from '../Header.vue';
 import Footer from '../Footer.vue';

export default {
  name: 'LoginComponent',

components: {
      Header,
      Footer
    },
  data() {
    return {
      uname: '',
      password: '',
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post(
          '/newsbyte/login',
          `username=${this.uname}&password=${this.password}`,
          {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          }
        );

        console.log('login success', response.data);

        //this.$router.push('/editor-dashboard');
        this.$router.push({ name: 'editorDashboardPage' });


      } catch (error) {
        console.log('login error', error);
      }
    },
  },
};
</script>
