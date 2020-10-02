/*刷新表格*/
function refresh(){
    table.reload('listTest');
}
//刷新表格当前页
function refresh_curr(url,search) {
    console.log("当前页" + current);
    //修整当前页，如果当前页数据为0了，那么将刷新上一页数据
    $.ajax({
        url: url,
        type: 'post',
        data: {limit:limit,page:current,search:search},
        success: function (result) {
            console.log(result);
            if (result.data == null || result.data.length <= 0) current = current -1;//修整
            else console.log("当前页的数据量：" + result.data.length);
            console.log("修整后的当前页：" + current);
            table.reload('listTest',
                {
                    page: {
                        curr:current
                    }
                })
        },
        error:function(XMLHttpRequest) {
            layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});
        }
    });
}
//获取icon
function icon(result) {
    let icon;
    if (result.type === 'error') icon = 2;//系统错误，请联系系统管理员
    else if (result.type === 'failed') icon = 5;//操作失败
    else if (result.type === 'validate_failed') icon = 5;//参数检验失败
    else if (result.type === 'unauthorized') icon = 5;//暂未登录或token已经过期
    else if (result.type === 'forbidden') icon = 4;//没有相关权限
    else  icon = 1;//操作成功
    return icon;
}