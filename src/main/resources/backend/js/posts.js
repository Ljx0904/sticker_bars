
//分页查询帖子数据
function getPostslPage(params){
    return $axios({
        url: '/posts/page',
        method: 'get',
        params
    })
}

function setmealStatusByStatus(params){
    return $axios({
        url: `/posts/status/${params.status}`,
        method: 'post',
        params:{ids:params.ids}
    })
}

function setmealStatusByFine(params){
    return $axios({
        url: `/posts/fine/${params.fine}`,
        method: 'post',
        params:{ids:params.ids}
    })
}