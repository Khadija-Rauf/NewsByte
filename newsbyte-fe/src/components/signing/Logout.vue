<template>
<Header />
  <div class="container text-start">
      <div class="row">
          <div class="col-3 p-5">
              <img src="/logout.jpg" class="img-fluid" alt="login image">
          </div>
          <div class="col p-5 mt-5">
              <form>
                  <div class="row mb-3">
                      <div class="col-sm-2"></div>
                      <div class="col-sm-5">
                          <h4>Logout from NewsByte</h4>
                      </div>
                  </div>
                  <div class="row mb-3">
                      <div class="col-sm-2"></div>
                      <div class="col-sm-5">
                          <button class="btn btn-outline-dark" type="button" @click="logout">Logout</button>
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
  name: 'LogoutComponent',
components: {
      Header,
      Footer
    },

methods: {
    async logout() {
      try {
        const response = await axios.post('/newsbyte/logout');
        console.log('logout success', response.data);

        this.$router.push({ name: 'loginPage' });


      } catch (error) {
        console.log('logout error', error);
      }
    },
  },

};
</script>
