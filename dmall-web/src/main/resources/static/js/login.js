/*系统登录业务处理js文件*/

//页面加载
window.onload=function () {
    addListener();
    addAttr();
    showAccount();
};
/*加密*/
function encryptToMD5() {
    let before = document.getElementById('pwd').value;
    if (before.length > 0){
        document.getElementById('pwd').value = md5(before);
    }
}
//登陆按钮效果
function addListener() {
    let element = document.getElementById("login");
    element.addEventListener("mouseover",mouseOver);
    element.addEventListener("mouseout",mouseOut);
}
function mouseOver() {
    let element = document.getElementById("login");
    element.style.backgroundColor = "limegreen";
    element.style.color = "black";
}
function mouseOut() {
    let element = document.getElementById("login");
    element.style.backgroundColor = "lightgreen";
    element.style.color = "white";
}
function addAttr() {
    let element = document.getElementById("test-account");
    element.setAttribute("data-toggle","popover");
    element.setAttribute("title","体验账号密码：");
    element.setAttribute("data-placement","bottom");
    element.setAttribute("data-container","body");
    element.setAttribute("data-content","guest/guest1");
    let forget = document.getElementById("forget");
    forget.setAttribute("data-toggle","tooltip");
    forget.setAttribute("data-placement","bottom");
    forget.setAttribute("title","请联系后台人员");
}
function showAccount() {
    $('#test-account').popover();
}
function hideAccount() {
    let element = document.getElementById("test-account");
    element.removeAttribute("data-toggle");
    element.removeAttribute("title");
    element.removeAttribute("data-content");
    element.removeEventListener("click",hideAccount);
}