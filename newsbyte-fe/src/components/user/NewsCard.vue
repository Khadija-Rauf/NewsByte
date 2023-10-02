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
                    <img class="card-img-top" :src="newsItems[((i-1)*3)+(j-1)].imageSource" alt="Card image cap" style="width: 250px; height: 150px; object-fit: cover;">
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
          <img :src="newsItems[(rowsCount*3)+(i-1)].imageSource" alt="Card image cap" style="width:150px; height:150px object-fit: cover;">
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
};
</script>
<style>

#img{
height:7cm;

}
</style>
