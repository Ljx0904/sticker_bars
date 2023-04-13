
function GetCommentsList(params){
    return $axios({
        url:`/comments/list`,
        method:'get',
        params
    })
}
function addComments(params){
    return $axios({
        url: 'comments/add',
        method: 'post',
        data:{...params}
    })
}