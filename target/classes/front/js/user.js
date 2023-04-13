function editUser(params){
    return $axios({
        url: `/employee/update`,
        method: 'put',
        data:{...params}
    })

}