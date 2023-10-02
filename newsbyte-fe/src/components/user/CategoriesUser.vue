<template>
    <div>
   <nav class="navbar navbar-expand-lg navbar-light">
       <div class="container-fluid" style="margin-top: 60px;">
  
  
         <a class="navbar-brand" href="#">Navbar</a>
         <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
           <span class="navbar-toggler-icon"></span>
         </button>
          <img src="../../assets/logo.png" alt="Logo" class="logo-image" style="width:2.5cm; padding-right:1cm;  " />
         <div class="collapse navbar-collapse" id="navbarSupportedContent">
           <ul class="navbar-nav me-auto mb-2 mb-lg-0">
           
            <li class="nav-item"  style="margin-right: 15px;">
                <router-link to="/user" class="custom-link">Latest</router-link>
            </li>
          
            <div v-for="(item, index) in tags.slice(0, tags.length)" :key="index">
                <li class="nav-item"  style="margin-right: 15px;">
                <router-link :to="{ name: 'CategoriesUser', params: { category: item }}" class="custom-link" style="text-transform: capitalize;">{{item}}</router-link>
              </li>
            </div> 

           </ul>
          </div>
       </div>
     </nav>
</div>
    <div>
      <NewsCard :newsItems="newsItems" :rowsCount="rowsCount" :displayInCol="displayInCol" :category="category" :channel="channel"/>
    </div>
  </template>
  
  <script>
import NewsService from "../../services/NewsService";
import NewsCard from "./NewsCard";
  
  export default {
    name: 'CategoriesUser',
    data() {
      return {
        newsItems: [],
        rowsCount:0,
        displayInCol:0,
        category:'',
      };
    },
    components: {
        NewsCard,
    },
    mounted() {
         this.category = this.$route.params.category;
       },

      watch: {
    '$route.params.category': {
      handler(newCategory) {
        if (newCategory !== this.category) {
          this.category = newCategory;
          this.getNewsByCategoryUser(newCategory);
        }
      },
      immediate: true, // Trigger on initial load
    },
  },
  
    methods: {
        getNewsByCategoryUser(category) {
        NewsService.getNewsByCategoryUser(category)
          .then((response) => {
            console.log("i am generic card after response: "+this.category);
            this.newsItems = response.data;
            this.firstHalfItems();
          })
          .catch((error) => {
            console.error("Error fetching data in categories:", error);
          });
      },
      firstHalfItems() {
      this.displayInCol = this.newsItems.length / 3;
      this.rowsCount = Math.floor((this.newsItems.length - this.displayInCol)/ 3);
  
      if ((this.newsItems.length - this.displayInColumn) % 3 != 0) {
        this.displayInCol += (this.newsItems.length - this.displayInCol) % 3;
        
      }
  
    },
    },
  };
  </script>
  <style>
  .navbar{
  margin-top: -1.5cm;
  background-color:black;
  
  }

   .custom-link {
  text-decoration: none; /* Remove underline */
  color: white; /* Set text color to white */
}

  </style>