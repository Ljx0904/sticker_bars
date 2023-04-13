function getClassifyList (moduleId) {
    return $axios({
        url: `/classify/list/${moduleId}`,
        method: 'get',
    })
}
