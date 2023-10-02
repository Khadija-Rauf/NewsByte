import axios from 'axios';

const GET_NEWS_USER = '/newsbyte/news';

class NewsService{

    getNewsUser()
    {
        return axios.get(GET_NEWS_USER+'/latestAll');
    }
    getNewsByCategoryUser(category)
    {
        return axios.get(`${GET_NEWS_USER}/${category}`);
    }
    getTags()
    {
        return axios.get(GET_NEWS_USER+'/tags');
    }

}

export default new NewsService();