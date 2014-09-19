/**
 * Created by Administrator on 2014/9/18.
 * 主框架页的js
 */

/**获取菜单的方法*/
function getMenu(){
    var url=path+"/menu/getUserMenuList.do";
    $().invoke(
            url,
            {},
        [function(m,r){
            for(var i in r){
                if(r[i]['permissionLevel']=='1' || r[i]['permissionLevel']==1 ){
                    addMenuLevel1(r[i]);
                }else if(r[i]['permissionLevel']=='2' || r[i]['permissionLevel']==2){
                    addMenuLevel2(r[i]);
                }

            }
            initLeftMenuBar();
        },
        function(m,r){alert(r)}]

    );
}

/**组装一级菜单*/
function addMenuLevel1(json){
    var h="<li id='menu_1_"+json.permissionID+"'> " ;
    if(json.permissionID==1 || json.permissionID=='1'){
        h+=" <a flag='m1' class=\"dropdown-toggle1\" href="+json.permissionURL+">";
    }else{
        h+="<a flag='m1' class=\"dropdown-toggle\" href=\"javascript:void(0)\">" ;
    }
        h+="<i class=\""+json.menuClass+"\"></i>"+
        "<span>"+json.permissionName+"</span>"+
        "<i class=\"icon-chevron-down\"></i>"+
        "</a><ul class=\"submenu\"></ul>" +
        "</li>";
    $('#dashboard-menu').append(h);
}

/**添加二级菜单*/
function addMenuLevel2(json){
    var h="<li><a target='contentMain' " +
        "onclick='addArrow(this)' href="+json.permissionURL+">"+json.permissionName+"</a></li>";
    var parID="menu_1_"+json['parentID'];
    $("#"+parID+" .submenu").append(h);
}

/**为选中的菜单的大菜单增加箭头*/
function addArrow(obj){
    $(".pointer[flag='po']").remove();
   var arrow="<div flag='po' class=\"pointer\">"+
            "<div class=\"arrow\"></div>"+
                "<div class=\"arrow_border\"></div>"+
            "</div>";
    if(obj.className=='dropdown-toggle' && $(obj).attr('flag')=='m1'){
        $(obj).prepend(arrow);
    }else{
        var o=obj.parentNode.parentNode.parentNode;
        var parentA=$(o).find("a");
        $(parentA[0]).prepend(arrow);
    }

}

/**注销登录*/
function logout(){
    top.location=path+'/logout.do';
}
