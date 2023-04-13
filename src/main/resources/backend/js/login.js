function loginApi(data) {
    return $axios({
        'url': '/administrators/login',
        'method': 'post',
        data
    })
}

function logoutApi(){
    return $axios({
        'url': '/administrators/logout',
        'method': 'post',
    })
}

