<template>
<Header />
<div class="container text-start">
    <h3>Your briefing</h3>
        <p id="current-date"></p>

        <div class="row text-start" v-if="news.length > 0">
                  <div class="" v-for="newsItem in news" :key="newsItem.detailsUrl">
                    <div class="card mb-3">
                      <div class="row g-0">
                        <div class="col-md-4">
                            <a :href="newsItem.detailsUrl" target="_blank" :title="newsItem.title">
                            <img :src="newsItem.imageSource" class="img-fluid rounded-start" :alt="newsItem.title" style="max-width: 250px; max-height: 200px;"/>
                            </a>
                        </div>
                        <div class="col-md-8">
                          <div class="card-body">
                            <h4 class="card-title"><a :href="newsItem.detailsUrl" target="_blank" class="text-black" :title="newsItem.title">{{ newsItem.title }}</a></h4>
                            <p class="card-text">{{ newsItem.description }}</p>
                            <p class="card-text"><small class="text-muted">{{ newsItem.publishedAt }}</small></p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
          </div>
</div>

  <Footer />
</template>

<script>
import Header from '../Header.vue';
import Footer from '../Footer.vue';
import axios from 'axios';

export default {
  name: 'NewsComponent',
components: {
      Header,
      Footer
    },

  data() {
    return {
      news: [],
      type: '',
    };
  },
  created() {
          this.type = this.$route.params.type;
        },
  mounted() {
    this.setDate();
    this.fetchNewsByType(this.type);
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

    async fetchNewsByType(newsType) {
          try {
            const response = await axios.get(`/newsbyte/api/v1/news?type=${newsType}`);
            this.news = response.data.news;

          } catch (error) {
            console.error('Error fetching news:', error);
          }
        },
  },
};
</script>
