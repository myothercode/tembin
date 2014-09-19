/**
 * Created by Administrator on 2014/9/19.
 */


/**账户管理获取数据*/
function accountManagerTab(){
    var url
    $().invoke(

    );
}

/**tab切换方法*/
function setTab(name,cursel,n){
    for(i=1;i<=n;i++){
        var menu=document.getElementById(name+i);
        var con=document.getElementById("con_"+name+"_"+i);
        menu.className=i==cursel?"new_tab_1":"new_tab_2";
        con.style.display=i==cursel?"block":"none";
    }
}