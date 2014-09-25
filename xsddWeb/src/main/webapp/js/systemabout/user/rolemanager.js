/**
 * 角色管理标签的js
 * Created by Administrator on 2014/9/24.
 */

/**获取角色列表*/
function queryRoleList(){
    $("#roleManager").initTable({
        url:path + "/role/queryRoleList.do",
        columnData:[
            {title:"角色名",name:"roleName",width:"8%",align:"left"},
            {title:"描述",name:"roleDesc",width:"8%",align:"left"},
            {title:"操作",name:"op1",width:"8%",align:"left",format:makeOperation}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false
    });
    refreshRoleTable({});
}
/**刷新角色列表*/
function refreshRoleTable(p){
    if(p==null){p={}}
    $("#roleManager").selectDataAfterSetParm(p);
}
/**制作操作菜单*/
function makeOperation(json){
    var select1="<div class=\"ui-select\" style=\"width:8px\">" +
        "<select onchange='doRole(this)'>" ;
    select1+= "<option  value='x'>请选择</option>" ;
    select1+= "<option  value='"+json.roleId+"' doaction=\"delete\">删除</option>" ;
    select1+= "<option  value='"+json.roleId+"' doaction=\"edit\">编辑</option>" ;
    select1+= "</select></div>";
    return select1;
}
/**操作*/
function doRole(obj){
    var optionV=$(obj).find("option:selected");
    var v=$(optionV).attr('value');
    if(v=='x'){return;}
    var d=$(optionV).attr('doaction');
    if(d=='delete'){
        deleteRole(v);
    }else if(d=='edit'){
        addRoleFun(v);
    }
}
/**删除角色*/
function deleteRole(roleid){
    if(!confirm("确定删除？")){
        return;
    }
    var url=path+"/role/deleteRole.do"
    var data={"roleId":roleid};
    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r);
            refreshRoleTable({});
            Base.token();
        },
        function(m,r){
            alert(r);
            Base.token();
        }]
    );
}


/**添加角色的弹窗*/
var openAddroleWindowD;
function addRoleFun(roleId){
    var url=path+"/role/";
    if(roleId!=null){
        url+="editRolePageInit.do?roleId="+roleId;
    }else{
        url+="addRolePageInit.do";
    }

    openAddroleWindowD = $.dialog({
        title:'',
        id : "dig" + (new Date()).getTime(),
        content:"url:"+url,
        width : 600,
        height : 600,
        max:false,
        min:false,
        lock : true

    });
    url="";
}
