function getPostList (params) {
    return $axios({
        url: '/posts/list',
        method: 'get',
        params
    })
}
function getPostHeatList () {
    return $axios({
        url: '/posts/HeatList',
        method: 'get',
    })
}
function getPostFineList () {
    return $axios({
        url: '/posts/FineList',
        method: 'get',
    })
}


function addPost (params) {
    return $axios({
        url: '/posts/add',
        method: 'post',
        data: { ...params }
        // params
    })
}
function queryPostsById(id){
    return $axios({
        url: `/posts/${id}`,
        method: 'get'
    })

}
function updatePost(params){
    return $axios({
        url: `/posts/update`,
        method: 'put',
        data:{...params}
    })

}
function deletePost(id){
    return $axios({
        url: `/posts/delete/${id}`,
        method: 'delete',
    })

}
function getByEmployeeId(id){
    return $axios({
        url: `/posts/getByEmployeeId/${id}`,
        method: 'get',
    })

}

