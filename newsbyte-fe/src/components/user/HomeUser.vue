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
             

              <!-- <li class="nav-item"  style="margin-right: 15px;">
                <router-link :to="{ name: 'CategoriesUser', params: { category: 'sports' }}" class="custom-link">Sports</router-link>
              </li>
  
              <li class="nav-item"  style="margin-right: 15px;">
                <router-link :to="{ name: 'CategoriesUser', params: { category: 'health' }}" class="custom-link">Health</router-link>
              </li>

              <li class="nav-item"  style="margin-right: 15px;">
                <router-link :to="{ name: 'CategoriesUser', params: { category: 'business' }}" class="custom-link">Business</router-link>
              </li>
              
              <li class="nav-item"  style="margin-right: 15px;">
                <router-link :to="{ name: 'CategoriesUser', params: { category: 'entertainment' }}" class="custom-link">Entertainment</router-link>
              </li> -->

           </ul>
          </div>
       </div>
     </nav>
</div>
    <br><br>
    <div class="container container-fluid">
    

        <div class="row">
        <h3 style="text-align: left;">Latest News</h3>
        <hr style="border: none;
                border-top: 4px solid black;
                margin-bottom: 10px 0; ">
    </div>

                                                                                                                <!-- THIS IS TOP DIV -->
        <div v-if="newsItems.length > 0" class="top-div row">

            <div class="left-top-div col-6" style="max-height: 90%;">
                <a :href="newsItems[0].url" style="text-decoration: none;">
                    <div class="card text-center" style="border: none;">
                        <img class="card-img-top" :src="newsItems[0].imageSource" alt="Card image cap" style="max-height: 50%;">
                        <div class="card-body" >
                            <p class="text-md">{{ newsItems[0].title }}</p>
                            <!-- <p class="text-sm"><strong>{{ newsItems[0].publishedAt }}</strong></p> -->
                        </div>
                    </div>
                </a>
                
            </div>

            <div class="mid-top-div col-3">
                <div class="up-div" style="max-height: 45%; overflow: hidden;">
                    <a :href="newsItems[1].url" style="text-decoration: none;">
                        <div class="card text-center"  style="border: none;">
                            <img class="card-img-top img-fluid" :src="newsItems[1].imageSource" alt="Card image cap">
                            <div class="card-body">
                                <p class="text-md">{{ newsItems[1].title }}</p>
                                <!-- <p class="text-md"><strong>{{ newsItems[1].publishedAt }}</strong></p> -->
                            </div>
                        </div>
                    </a>
                    
                </div>

                <div class="down-div" style="max-height: 45%; overflow: hidden;">
                    <a :href="newsItems[2].url" style="text-decoration: none;">
                        <div class="card text-center"  style="border: none;">
                            <img class="card-img-top img-fluid" :src="newsItems[2].imageSource" alt="Card image cap">
                            <div class="card-body">
                                <p class="text-md">{{ newsItems[2].title }}</p>
                                <!-- <p class="text-md"><strong>{{ newsItems[2].publishedAt }}</strong></p> -->
                            </div>
                        </div>
                    </a>
                    
                </div>
            </div>

            <div class="right-top-div col-3">
                <div class="up-div" style="max-height: 45%; overflow: hidden;">
                    <a :href="newsItems[3].url" style="text-decoration: none;">
                        <div class="card text-center"  style="border: none;" >
                            <img class="card-img-top" :src="newsItems[3].imageSource" alt="Card image cap">
                            <div class="card-body">
                                <p class="text-md">{{ newsItems[3].title }}</p>
                                <!-- <p class="text-md"><strong>{{ newsItems[3].publishedAt }}</strong></p> -->
                            </div>
                        </div>
                    </a>
                    
                </div>

                <div class="down-div" style="max-height: 45%; overflow: hidden;">
                    <a :href="newsItems[4].url" style="text-decoration: none;">
                        <div class="card text-center"  style="border: none;">
                            <img class="card-img-top" :src="newsItems[4].imageSource" alt="Card image cap">
                            <div class="card-body">
                                <p class="text-md">{{ newsItems[4].title }}</p>
                                <!-- <p class="text-md"><strong>{{ newsItems[4].publishedAt }}</strong></p> -->
                            </div>
                        </div>
                    </a>
                    
                </div>
            </div>
        </div>


                                                                                                                    <!-- THIS IS MIDDLE DIV -->

        <hr style="border: 1px solid #000;">
        <div v-if="newsItems.length >= 5" class="middle-div d-flex align-items-stretch row" style="margin-top: 30px;">
            <div v-for="(item, index) in newsItems.slice(5, 9)" :key="index" class="left-div-middle col-sm-3">
                <a :href="item.url" style="text-decoration: none;">
                    <div class="card text-center"  style="border: none;">
                        <img class="card-img-top" :src="item.imageSource" alt="Card image cap" style="max-height: 50%;">
                        <div class="card-body" >
                            <p class="text-md">{{ item.title }}</p>
                            <!-- <p class="text-md"><strong>{{ item.publishedAt }}</strong></p> -->
                        </div>
                    </div>
                </a>
            </div>
        </div>


                                                                                                                    <!-- THIS IS BOTTOM DIV -->


        <div v-if="newsItems.length >=9" class="bottom-div" style="margin-top: 10px; margin-bottom: 20px;">
            <div class="card text-center"  style="border: none;">
                    <img class="card-img-top" :src="newsItems[9].imageSource" alt="Card image cap" style="max-height: 300px;">
                    <div class="card-body">
                        <p class="text-md">{{ newsItems[9].title }}</p>
                    </div>
                </div>
                
        </div>
                                                                                                                    <!-- THIS IS last DIV -->
         
        <br><br> 
        <hr style="border: 1px solid #000;">                                                                                                    
        <div class="last-div row" style="margin-top: 20px;">
            <div class="last-div-left col-5">
                <div v-for="(item, index) in firstHalfItems" :key="index">
                    <a :href="item.url" style="text-decoration: none;">
                        <h6 style="color:black;">{{ item.title }}</h6>
                    </a>
                    
                    <hr>
                </div>
            </div> 


            <div class="last-div-right col-5"  style="margin-left: 40px; border-left: 1px solid #000;">
                <div v-for="(item, index) in secondHalfItems" :key="index">
                    <a :href="item.url" style="text-decoration: none;">
                        <div class="row" style="max-height: 200px; margin-top: 10px;">
                            <div class="col-5">
                                <div>
                                    <img :src="item.imageSource" alt="Card image cap" style="max-width: 90%; max-height: 100%;">
                                </div>
                            </div>
                            <div class="col-5">
                                <div>
                                    <p style="max-width: 90%; max-height: 100%; color:black;">{{ item.title }}</p> 
                                </div>
                            </div>
                        </div>
                    </a>
                    
                    <hr>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import NewsService from "../../services/NewsService";
import router from '../../index';

export default {
  name: "HomeUser",
  data() {
    return {
      newsItems: [],
      tags: [],
    };
  },
  components: {
},

  mounted(){
    this.getNewsUser();
    this.getTags();
  },
  methods: {
    getNewsUser() {
      NewsService.getNewsUser()
        .then((response) => {
          console.log("data: "+response.data);
          this.newsItems = response.data;
        })
        .catch((error) => {
          console.error("Error fetching data in home:", error);
        });

    },
    getTags() {
      NewsService.getTags()
        .then((response) => {
          console.log("tags: " +response.data);
          this.tags = response.data;
        })
        .catch((error) => {
          console.error("Error fetching tags in home:", error);
        });

    },
    goToFetchNews(category) {
        console.log(category);
        router.push('CategoriesUser',{ category: {category} }) ;
        
    }
   

    },
  
  computed: {
    firstHalfItems() {
      const totalItems = this.newsItems.length;
      const middleIndex = Math.floor((10 + totalItems) / 2)+3;
      return this.newsItems.slice(10, middleIndex);
    },
    secondHalfItems() {
      const totalItems = this.newsItems.length;
      const middleIndex = Math.floor((10 + totalItems) / 2)+3;
      return this.newsItems.slice(middleIndex, totalItems);
    },
  },
};
</script>
<style>
.container {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100vh;
}

.up-div, .down-div {
    height: 50%; 
    padding: 10px; 
}

.custom-link {
  text-decoration: none; /* Remove underline */
  color: white; /* Set text color to white */
  font-family: Georgia, serif;
  font-size:large;
}


</style>