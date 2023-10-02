<template>
    <Header />

  <h1>Home</h1>
<div class="container text-start">
    <h3>Your briefing</h3>
        <p id="current-date"></p>


</div>

    <Footer />
</template>

<script>
import Header from './Header.vue';
import Footer from './Footer.vue';

export default {
  name: 'HomeComponent',
  components: {
        Header,
        Footer
      },
      mounted() {
          this.setDate();
          //this.fetchNewsByType(this.type);
        },
methods: {
  setDate() {
        const monthNames = [
          'January', 'February', 'March', 'April', 'May', 'June', 'July',
          'August', 'September', 'October', 'November', 'December'
        ];
        const dayNames = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        const date = new Date();
          const currentDate = dayNames[date.getDay()] + ', ' + date.getDate() + ' ' + monthNames[date.getMonth()] + ' ' + date.getFullYear();
                 document.getElementById('current-date').innerHTML = currentDate;
               },
               }
};
</script>
