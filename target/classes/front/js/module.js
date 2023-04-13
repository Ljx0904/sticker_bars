function getModuleList(){
    return $axios({
        url: `/module/list`,
        method: 'get'
    })

}

