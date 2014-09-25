/**
 * Created by Administrator on 2014/9/24.
 */

$(document).ready(function(){
    getPermissionList();
});
/**获取全部可选的权限*/
function getPermissionList(){
    var url=path+"/menu/getAllMenuList.do";
    var data={};
    $().invoke(
        url,
        data,
        [function(m,r){
            if(r==null || r.length==0){return;}
            //这一步先过滤出一级菜单
            var shtml="<select id='persselect' size='10' class=\"input-xlarge\" multiple=\"multiple\">";
               for(var i in r){
                   if((r[i]['permissionLevel'])=="1"){
                       shtml+="<optgroup id='levelone"+(r[i]['permissionID'])+"' label="+(r[i]['permissionName'])+">";
                       shtml+="</optgroup>";
                   }
               }
            shtml+="</select>";

            $('#canSelectPer').html(shtml);
            jxMenuTwo(r);
            shtml="";
        }]
    );
}
/**开始解析二级菜单*/
function jxMenuTwo(json){
    for(var i in json){
        if((json[i]['permissionLevel'])=="1"){continue;}
        var op="<option onclick='chosePermission(this)' value='"+(json[i]['permissionID'])+"'>"+(json[i]['permissionName'])+"</option>";
        $("#levelone"+(json[i]['parentID'])).append(op);
    }
    getRoleInfoWhenEdit();
}
/**点击要选择的菜单后执行的操作*/
function chosePermission(obj){
    var perID=$(obj).val();
    var t = $('#alreadChecked').find("input[value='"+perID+"']").length
    if(t>0){return;}
    var perName=$(obj).html();
    var parentPerName=$(obj.parentNode).attr("label");
    var html="<label class=\"checkbox\">" +
        "<input onclick='canclechecked(this)' checked=true name='rolePermissions[0].permissionId' type=\"checkbox\" value='"+perID+"'>" + //class='validate[required] checkbox'
        (parentPerName+"--"+perName)+"</label>";
    $('#alreadChecked').append(html);
}
/*取消选中的权限*/
function canclechecked(obj){
    if(!$(obj).prop("checked")){
        $(obj.parentNode).remove();
    }

}

/**编辑页面获取角色信息*/
function getRoleInfoWhenEdit(){
    var localhref=document.location.href;
    if(localhref.indexOf("editRolePageInit.do")==-1 || roleId==null || roleId==''){return;}
    var data={"roleId":roleId};
    var url=path+"/role/getRoleInfoById.do";
    $().invoke(
        url,
        data,
        [function(m,r){
            if(r==null){alert("没有获取到角色数据");return;}
            $('#roleName').val(r.role.roleName);
            $('#roleDesc').val(r.role.roleDesc);
            var pers= r.rolePermissions;
            for(var i in pers){
                var v_=pers[i]['permissionId'];
                var op=$('#persselect').find("option[value='"+v_+"']").eq(0);
                chosePermission(op[0]);
            }
        }]
    );
}


/**提交新增角色*/
function submitRole(){
if(roleName==null || roleName==''){alert('请输入角色名!');return;}

    if($('#alreadChecked').find("input").length==0){alert("请至少选择一个权限！");return;}

    domReIndex("alreadChecked","rolePermissions");
    var localhref=document.location.href;
    var url=path+"/role/addOrEditRole.do";
    if(localhref.indexOf("editRolePageInit.do")>-1 && roleId!=null){
        url+="?role.roleId="+roleId;
    }
    var data=$('#roleForm').serialize();
    $().invoke(
        url,
        data,
        [function(m,r){
            alert(r);
            var api = frameElement.api, W = api.opener;
            W.refreshRoleTable({});
            W.openAddroleWindowD.close();
        },
        function(m,r){
            alert(r);
            Base.token();
        }]
    );
}