import request from '@/utils/request'

export default {
    login(formItem){
        return request({
            url: `/eduuser/user/login`,
            method: "post",
            data: formItem
        })
    },

    getUserInfo(){
        return request({
            url: `/eduuser/user/getUserInfo`,
            method: "get"
        })
    }

}