
/*
* 查询所有模块
* */
function getModuleList(){
    return $axios({
        url: `/module/list`,
        method: 'get'
    })

}

/*
* 分类查询模块数据
* */
function getModuleListPage(params){
    return $axios({
        url:'module/page',
        method:'get',
        params
    })
}

/*
* 添加模块
* */
function addModule(params){
    return $axios({
        url:'module/add',
        method: 'post',
        data:{...params}
    })
}


/*
* 修改模块
* */
function editModule(params){
    return $axios({
        url:'module/update',
        method:'put',
        data: {...params}
    })
}

/*
* 删除模块
* */
function deleModule(id){
    return $axios({
        url:`module/delete/${id}`,
        method:'delete',
    })
}