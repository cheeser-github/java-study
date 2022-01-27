/**
 * 封装ajax的post请求
 * @param url 要访问的链接
 * @param data 要提交的数据
 */
function postRequest(url, data) {
    //ajax第一步
    var request=new XMLHttpRequest();

    //ajax第二步,第一个参数GET,第二个是我们要找谁要数据，
    //第三个是异步还是同步，true代表异步，不要同步；
    request.open("post",url,true);

    //post请求需要设置请求头
    request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");  //post方式
    //第三步，post请求添加数据的方法
    request.setRequestHeader("token", localStorage.getItem("mytoken"));
    alert("从localstorage中取到：" + localStorage.getItem("mytoken"));
    request.send("username=" + data);

    request.onreadystatechange=function() {

        //readyState 有这几种状态，我们只需要判断4就行;
        // 0: 请求未初始化
        // 1: 服务器连接已建立
        // 2: 请求已接收
        // 3: 请求处理中
        // 4: 请求已完成，且响应已就绪
        if (request.readyState == 4) {

            //status 状态200 代表 ok

            if (request.status == 200) {
                //request.reponseText代表后台返回的数据
                localStorage.setItem("mytoken", request.responseText);
            }
        }
    }
}
