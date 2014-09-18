<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var payPal;
        function addPayPal(){
            payPal=$.dialog({title: '新增付款选项',
                content: 'url:/xsddWeb/addPayPal.do',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        function editPayPal(id){
            payPal=$.dialog({title: '编辑付款选项',
                content: 'url:/xsddWeb/editPayPal.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function editPayPalselect(id){
            payPal=$.dialog({title: '查看付款选项',
                content: 'url:/xsddWeb/editPayPal.do?id='+id+'&type=01',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function delPayPal(id){
            var url=path+"/ajax/delPayPal.do?id="+id;
            $().invoke(url,{},
                    [function(m,r){
                        alert(r);
                        Base.token();
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }

        $(document).ready(function(){
            $("#paypallisttable").initTable({
                url:path + "/ajax/loadPayPalList.do",
                columnData:[
                    {title:"名称",name:"payName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"paypal账号",name:"payPalName",width:"8%",align:"left"},
                    {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2},
                    {title:"动作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function  refreshTable(){
            $("#paypallisttable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        //数据状态
        function makeOption2(json){
            var htm=''
            if(json.checkFlag=="0"){
                htm='已启用';
            }else{
                htm='已禁用';
            }
            return htm;
        }
/**组装操作选项*/
function makeOption1(json){
var htm='<div class="ui-select" style="margin-top:9px; width:10px">'
    htm+='<select onchange="selectDo(this)">'
    htm+='<option value="">请选择</option>'
    htm+='<option value="'+json.id+'">查看</option>'
    if(json.checkFlag=="0") {
        htm += '<option value="' + json.id + '">禁用</option>'
    }else{
        htm += '<option value="' + json.id + '">启用</option>'
    }
    htm+='<option value="'+json.id+'">编辑</option>'

    htm+='</select>'
    htm+='</div>';
    return htm;
}
function selectDo(obj){
    if($(obj).find(":selected").text()=="编辑"){
        editPayPal($(obj).find(":selected").val());
    }else if($(obj).find(":selected").text()=="禁用"||$(obj).find(":selected").text()=="启用"){
        delPayPal($(obj).find(":selected").val());
    }else if($(obj).find(":selected").text()=="查看"){
        editPayPalselect($(obj).find(":selected").val());
    }
    $(obj).val("");
}
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="addPayPal" value="新增" onclick="addPayPal();">
</div>
<div id="paypallisttable">

</div>
</body>
</html>
