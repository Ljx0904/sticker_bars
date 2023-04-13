function enrollApi(data) {
    return $axios({
        'url':'/employee/enroll',
        'method':'post',
        data
    })

}
//
function getById(id){
    return $axios({
        url: `/employee/${id}`,
        method: 'get',
    })
}