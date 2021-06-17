import axios from 'axios'
import cookie from 'js-cookie'

const service = axios.create({
    baseURL: "http://localhost:9001",  // Api URL
    contentType: "application/json;charset=UTF-8",
    timeout: 20000 // 超时时间
})

// 拦截器
service.interceptors.request.use(
    config => {
        if(cookie.get('wangs_token')){
            // 如果存在cookie，则将cookie放到请求头中
            config.headers['token'] = cookie.get('wangs_token')
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

export default service