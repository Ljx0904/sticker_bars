
/*
*分页查询数据
* */
function getMemberList (params) {
    return $axios({
        url: '/administrators/page',
        method: 'get',
        params
    })
}


/*
*
*修改页面回显详细数据
*
* */
function queryEmployeeById(id){
    return $axios({
        url:`/administrators/${id}`,
        method: 'get'
    })
}

/*
*
* 添加员工
* */

function addEmployee(params){
    return $axios({
        url:'/administrators/add',
        method:'post',
        data:{...params}
    })
}
/*
* 修改员工信息
* */

function edit(params){
    return $axios({
        url:'/administrators/update',
        method:'put',
        data: {...params}
    })
}

function enableOrDisable(params){
    return $axios({
        url:'/administrators/update',
        method:'put',
        data: {...params}
    })
}




