<template>
<div class="container container-fluid" style="margin-top:20px;">

  <div class="row">
    <h3 style="text-align: left; color:#000; font-family: Georgia, serif;">{{category}}</h3>
    <hr style="border: none;
            border-top: 4px solid black;
            margin-bottom: 10px 0; 
            color:#000">
  </div>
  <div class="row">

   <div class="col-8">
        <div class="row" v-for="i in rowsCount" :key="i" style="flex:1">
          <div class="col" v-for="j in 3" :key="j">
              <a :href="newsItems[((i-1)*3)+(j-1)].url" style="text-decoration: none;">
                  <div class="card text-center card-container" style="border: none;">
                     <div v-if="newsItems[((i-1)*3)+(j-1)].imageSource !== null && newsItems[((i-1)*3)+(j-1)].imageSource !== ''">
                      <img class="card-img-top" :src="newsItems[((i-1)*3)+(j-1)].imageSource" alt="Card image cap" style="width: 250px; height: 150px; object-fit: cover;">
                    </div>
                    <div v-else>
                      <img class="card-img-top" :src="getCardImage(((i-1)*3)+(j-1))" alt="Card image cap" style="width: 250px; height: 150px; object-fit: cover;">
                    </div>
                      <div class="card-body">
                          <p class="text-md">{{ newsItems[((i-1)*3)+(j-1)].title }}</p>
                      </div> 
                  </div>
                  
              </a>
          </div>
        </div>
    </div>

  <div class="col-3" style="margin-left: 40px; border-left: 1px solid #000;">
  <div v-for="i in displayInCol" :key="i">
    <a :href="newsItems[(rowsCount*3)+(i-1)].url" style="text-decoration: none;">
      <div class="row" style="border: none; margin-bottom: 20px;">

        <div class="col-6">
          <div v-if="newsItems[(rowsCount*3)+(i-1)].imageSource !== null && newsItems[(rowsCount*3)+(i-1)].imageSource !== ''">
            <img :src="newsItems[(rowsCount*3)+(i-1)].imageSource" alt="Card image cap" style="width:150px; height:150px object-fit: cover;">
          </div>
          <div v-else>
            <img :src="getCardImage((rowsCount*3)+(i-1))" alt="Card image cap" style="width: 150px; height:150px object-fit: cover;">
          </div>
          
        </div>
        <div class="col-6">
          <div>
            <h6 style="color: black;">{{ newsItems[(rowsCount*3)+(i-1)].title }}</h6>
          </div>
        </div>
        <hr style="border: none;
            border-top: 1px solid black;
            margin-bottom: 10px 0; 
            margin-top: 10px 0; 
            color:#000">
      </div>
    
    </a>
  </div>
  </div>

  </div>
</div>
</template>

<script>

export default {
  name: 'NewsCard',
  created() {
  },
  components: {
    // NavFooter92,
},
  props: {
    newsItems: Array,
    rowsCount: Number,
    displayInCol: Number,
    category: String,
    channel: String,
  
  },
  data() {
        return {

      cardImagesNY: [
        "1.jpg",
        "2.jpg",
        "3.jpg",
        "4.jpg",
        "5.jpg",
        "6.jpg",
        "7.jpg",
        "8.jpg",
        "9.jpg",
        "10.jpg",
        "11.jpg",
        "12.jpg",
        "13.jpg",
        "14.jpg",
        "15.jpg",
        "16.jpg",
        "17.jpg",
        "18.jpg",
        "19.jpg",
        "20.jpg",
        "21.jpg",
        "22.jpg",
        "23.jpg",
        "24.jpg",
        "25.jpg",
        "26.jpg",
        "27.jpg",
        "28.jpg",
        "29.jpg",
        "30.jpg"

      ],
      cardImagesGuardian: [
        "1.jpg",
        "2.jpg",
        "3.jpg",
        "4.jpg",
        "5.jpg",
        "6.jpg",
        "7.jpg",
        "8.jpg",
        "9.jpg",
        "10.jpg",
        "11.jpg",
        "12.jpg",
        "13.jpg",
        "14.jpg",
        "15.jpg",
        "16.jpg",
        "17.jpg",
        "18.jpg",
        "19.jpg",
        "20.jpg",
        "21.jpg",
        "22.jpg",
        "23.jpg",
        "24.jpg",
        "25.jpg",
        "26.jpg",
        "27.jpg",
        "28.jpg",
        "29.jpg",
        "30.jpg"

      ],
    
    };
    },


    methods: {
   getCardImage(index) {

        const effIndex = index % this.cardImagesNY.length;
        const image = `/${this.channel}/${this.category}/${this.cardImagesNY[effIndex]}`;
        console.log("image: ", image);
        return image;

      },

    }

};
</script>
<style>

#img{
height:7cm;

}
</style>
