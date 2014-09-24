/**
 * Created by Administrator on 2014/9/24.
 */

function getPermissionList(){
    var url=path+"/menu/getAllMenuList.do";
    var data={};
    $().invoke(
        url,
        data,
        [function(m,r){
            if(r==null || r.length==0){return;}
            var shtml="<select class=\"input-xlarge\" multiple=\"multiple\">";
        }]
    );
}