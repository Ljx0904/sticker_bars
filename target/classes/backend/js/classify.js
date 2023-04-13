
/*
* 分页查询分类数据
* */
function getClassifyPage(params){
    return $axios({
        url:'classify/page',
        method:'get',
        params
    })
}

function deleClassify(id){
    return $axios({
        url:`classify/delete/${id}`,
        method:'delete',
    })
}

function addClassify(params){
    return $axios({
        url:'classify/add',
        method: 'post',
        data:{...params}
    })
}

function editClassify(params){
    return $axios({
        url:'classify/update',
        method: 'put',
        data:{...params}
    })
}