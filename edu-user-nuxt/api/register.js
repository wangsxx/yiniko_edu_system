import request from '@/utils/request'

export default {
    sendCode(phone){
        return request({
            url: `/edumsm/msm/sendMsm/${phone}`,
            method: "get"
        })
    },
    register(formItem){
        return request({
            url: `/eduuser/user/register`,
            method: "post",
            data: formItem
        })
    }
}