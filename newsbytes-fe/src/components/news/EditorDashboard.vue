<template>

    <div class="container-fluid text-start">
        <div class="row">
            <div class="col-2 bg-dark text-light ps-5">
                <a class="" href="/logout">
                    <button class="btn btn-danger m-2 mb-4 mt-4" type="button">Logout</button>
                </a>
                <h4 class="ms-2">MENU</h4>
                <div>
                    <button class="btn btn-outline-light m-1" type="button" @click="enableFetchNews">National News</button>
                </div>
                <div>
                    <button class="btn btn-outline-light m-1" type="button">International News</button>
                </div>
                <div>
                     <button class="btn btn-outline-light m-1" type="button" @click="showAllTags">Manage Tags</button>
                </div>
                <div>
                     <button class="btn btn-outline-light m-1" type="button" @click="enableShowTrends">View Trends</button>
                 </div>
            </div>

            <div class="col bg-light">
                <div class="p-5">
                    <h1>Welcome to Editor Panel!</h1>
                    <hr />
                </div>
                <div v-if="showNews" id="news-div" class="p-5 pt-0">
                    <h3 v-if="newsIrrespectiveOfTag">National News</h3>
                    <div class="row mb-2" v-if="newsIrrespectiveOfTag">
                        <div class="col-4">
                            <button @click="fetchNews('Dawn')" class="btn btn-success w-100">Dawn</button>
                        </div>
                        <div class="col-4">
                            <button @click="fetchNews('The News International')" class="btn btn-success w-100">The News
                                International</button>
                        </div>
                        <div class="col-4">
                            <button @click="fetchNews('The Express Tribune')" class="btn btn-success w-100">The Express
                                Tribune</button>
                        </div>
                    </div>
                    <div class="" role="status" id="news-loading-spinner">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                    <table class="table table-hover table-secondary text-start" v-if="news.length > 0">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>News</th>
                                <th>NewsPaper</th>
                                <th>Tag</th>
                                <th v-if="newsIrrespectiveOfTag">Assign New Tag</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="newsItem in news" :key="newsItem.detailsUrl"
                                :class="{ 'table-danger': newsItem.tag.name === 'None', 'table-warning': newsItem.tag.name !== 'None' }">
                                <td>{{newsItem.id}}</td>
                                <td>
                                    <div class="card mb-3">
                                        <div class="row">
                                            <div class="col-md-2">
                                                <a :href="newsItem.detailsUrl" target="_blank" :title="newsItem.title">
                                                    <img :src="newsItem.imageSource" class="img-fluid rounded-start"
                                                        :alt="newsItem.title"
                                                        style="max-width: 150px; max-height: 100px;" />
                                                </a>
                                            </div>
                                            <div class="col">
                                                <div class="card-body">
                                                    <h6 class="card-title"><a :href=newsItem.detailsUrl target="_blank"
                                                            :title="newsItem.description" class="text-black">{{
                                                            newsItem.title }}</a></h6>
                                                    <p class="card-text"><small class="text-muted">{{
                                                            newsItem.publishedAt }}</small></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <a :href="newsItem.newspaper.siteUrl" target="_blank"
                                        :title="newsItem.newspaper.name">
                                        <img :src="newsItem.newspaper.logoUrl" class="img-fluid rounded-start"
                                            :alt="newsItem.title" style="max-width: 50px; max-height: 50px;" />
                                    </a>
                                </td>
                                <td>{{ newsItem.tag.name }}</td>
                                <td v-if="newsIrrespectiveOfTag">
                                    <select class="form-select" aria-label="Default select example">
                                        <option disabled value="" selected>Select a Tag</option>
                                        <option v-for="tag in tags" :key="tag.id" :value="tag.name"
                                            @click="assignTag(newsItem.id, tag)">{{ tag.name }}</option>
                                    </select>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div v-if="showTags" id="tags-div" class="p-5 pt-0 text-start">
                    <div class="row">
                        <div class="col">
                            <h2>Tags</h2>
                        </div>
                        <div class="col text-end">
                            <button class="btn btn-success" @click="enableTagCreation">+ Create Tag</button>
                        </div>
                    </div>
                    <table v-if="tags.length > 0" class="table table-hover table-secondary text-center ">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="tagItem in tags" :key="tagItem.id">
                                <td>{{ tagItem.id }}</td>
                                <td>{{ tagItem.name }}</td>
                                <td>{{ tagItem.description }}</td>
                                <td>
                                    <button class="btn btn-warning me-1" @click="fetchTag(tagItem.id)">Update</button>
                                    <button class="btn btn-danger me-1" @click="deleteTag(tagItem.id)">Delete</button>
                                    <button class="btn btn-info me-1" @click="getNews(tagItem.name)">View Associated News</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div v-if="showCreateTagForm" id="create-tag-div" class="p-5 pt-0">
                    <div class="" role="alert" id="tag-created-alert">
                    </div>
                    <form @submit.prevent="createTag">
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <h4>Create Tag</h4>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Name</label>
                            <div class="col-sm-5">
                                <input class="form-control" type="text" placeholder="Name" required v-model="tag.name"
                                    autofocus />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Description</label>
                            <div class="col-sm-5">
                                <textarea class="form-control" rows="5" placeholder="Description"
                                    v-model="tag.description" required> </textarea>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <button class="btn btn-dark" type="submit">Create</button>
                            </div>
                        </div>
                    </form>
                </div>

                <div v-if="showUpdateTagForm" id="update-tag-div" class="p-5 pt-0">
                    <div class="" role="alert" id="tag-updated-alert"></div>
                    <form @submit.prevent="updateTag">
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <h4>Update Tag</h4>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Id</label>
                            <div class="col-sm-5">
                                <input class="form-control" type="text" required v-model="tag.id" disabled />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Name</label>
                            <div class="col-sm-5">
                                <input class="form-control" type="text" required v-model="tag.name" autofocus />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Description</label>
                            <div class="col-sm-5">
                                <textarea class="form-control" rows="5" v-model="tag.description" required> </textarea>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <button class="btn btn-dark" type="submit">Update</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>

</template>



<script>
    import axios from 'axios';

    export default {
        name: 'EditorDashboardComponent',

        data() {

            return {
                news: [],
                tags: [],
                tag: {},
                showNews: false,
                showTags: false,
                showCreateTagForm: false,
                showUpdateTagForm: false,
                newsIrrespectiveOfTag: false,
                showTrends: false,
            };
        },
        mounted() {
            console.log('editor dash');
        },

        methods: {
            async enableShowTrends() {

            },
            showAllTags() {
                this.fetchTags();
                this.showCreateTagForm = false;
                this.showUpdateTagForm = false;
                this.newsIrrespectiveOfTag= false;
                this.showTrends = false;
                this.showNews = false;
                this.showTags = true;
            },
            async fetchTags() {
                try {
                    const response = await axios.get('/newsbyte/api/v1/tags');
                    this.tags = response.data.tags.slice(1);

                } catch (error) {
                    console.error('Error fetching tags:', error);
                }
            },
            async deleteTag(id) {
                try {
                    await axios.delete(`/newsbyte/api/v1/tags/${id}`);
                    this.fetchTags();
                } catch (error) {
                    console.error('Error del tag:', error);
                }
            },
            enableTagCreation() {
                this.tag = {};
                this.showTags = false;
                this.showUpdateTagForm = false;
                this.showTrends = false;
                this.showNews = false;
                this.showCreateTagForm = true;
                this.newsIrrespectiveOfTag= false;
            },
            async createTag() {
                try {
                    this.tag.id = 0;
                    const response = await axios.post('/newsbyte/api/v1/tags', this.tag);
                    console.log('tag created successfully', response);
                    document.getElementById('tag-created-alert').setAttribute("class", "alert alert-success");
                    document.getElementById('tag-created-alert').innerHTML = 'Tag Created Successfully!';
                    this.tag = {};
                } catch (error) {
                    console.error('Error in creating tag', error);
                }
            },
            async fetchTag(id) {
                this.showTags = false;
                this.showNews = false;
                this.showTrends = false;
                this.showCreateTagForm = false;
                this.newsIrrespectiveOfTag= false;
                this.showUpdateTagForm = true;
                try {
                    const response = await axios.get(`/newsbyte/api/v1/tags/${id}`);
                    this.tag = response.data.tag;

                } catch (error) {
                    console.error('Error fetching tag options:', error);
                }
            },
            async updateTag() {
                try {
                    const response = await axios.post(`/newsbyte/api/v1/tags/${this.tag.id}`, this.tag);
                    console.log('tag updated successfully', response);
                    document.getElementById('tag-updated-alert').setAttribute("class", "alert alert-success");
                    document.getElementById('tag-updated-alert').innerHTML = 'Tag Updated Successfully!';
                } catch (error) {
                    console.error('Error in updated tag', error);
                }
            },

            enableFetchNews() {
                this.showTags = false;
                this.showCreateTagForm = false;
                this.showUpdateTagForm = false;
                this.showTrends = false;
                this.showNews = true;
                this.newsIrrespectiveOfTag= true;
            },
            async fetchNews(newspaper) {
                try {
                    document.getElementById('news-loading-spinner').setAttribute("class", "spinner-border");
                    const response = await axios.get(`/newsbyte/api/v1/news/latest?newspaper=${newspaper}`);
                    this.news = response.data.news;
                    this.fetchTags();
                    document.getElementById('news-loading-spinner').setAttribute("class", "");

                } catch (error) {
                    console.error('Error fetching Dawn News:', error);
                }
            },
            async assignTag(id, tag) {
                try {
                    const response = await axios.post(`/newsbyte/api/v1/news/assign-tag/${id}`, tag);
                    const index = this.news.findIndex(item => item.id === response.data.news.id);
                    if (index !== -1) {
                        this.news[index].tag = response.data.news.tag;
                    }
                } catch (error) {
                    console.error('Error in updating tag', error);
                }
            },
            async getNews(type) {
            this.showTags = false;
                this.showTrends = false;
                            this.showCreateTagForm = false;
                            this.showUpdateTagForm = false;
                this.newsIrrespectiveOfTag= false;
                            this.showNews = true;
                            try {
                                //document.getElementById('news-loading-spinner').setAttribute("class", "spinner-border");
                                const response = await axios.get(`/newsbyte/api/v1/news?type=${type}`);
                                this.news = response.data.news;
                                //document.getElementById('news-loading-spinner').setAttribute("class", "");
            
                            } catch (error) {
                                console.error('Error fetching Dawn News:', error);
                            }
                        },
        },
    };
</script>