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
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script>
        var returnShipping;
        function addshippingDetails(){
            returnShipping=$.dialog({title: '新增运送选项',
                content: 'url:/xsddWeb/addshippingDetails.do',
                icon: 'succeed',
                width:1000,
                lock:true
            });
        }

        function editshippingDetails(id){
            returnShipping=$.dialog({title: '编辑运送选项',
                content: 'url:/xsddWeb/editshippingDetails.do?id='+id,
                icon: 'succeed',
                width:1000,
                lock:true
            });
        }
        $(document).ready(function(){
            $("#shippingDetailsList").initTable({
                url:path + "/ajax/loadShippingDetailsList.do",
                columnData:[
                    {title:"名称",name:"shippingName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"ebay账号",name:"option1",width:"8%",align:"left",format:showData},
                    {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });

        function refreshTable(){
            $("#shippingDetailsList").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
                editshippingDetails($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="禁用"||$(obj).find(":selected").text()=="启用"){
                delshippingDetails($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="查看"){
                editshippingDetailsselect($(obj).find(":selected").val());
            }
            $(obj).val("");
        }

        function delshippingDetails(id){
            var url=path+"/ajax/delshippingDetails.do?id="+id;
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
        function editshippingDetailsselect(id){
            returnShipping=$.dialog({title: '编辑运送选项',
                content: 'url:/xsddWeb/editshippingDetails.do?id='+id+'&type=01',
                icon: 'succeed',
                width:1000,
                lock:true
            });
        }
        function showData(json){
            var html='';
            html+='<div>'+json.ebayName+'</div>';
            html+='<div><table style="border:1px solid #dddddd;" width="400px">';
            for(var i=0;i<json.lits.length;i++){
                html+='<tr><td style="border:1px solid #dddddd;" width="220px">'+json.lits[i].shippingservice+'</td><td style="border:1px solid #dddddd;" width="60px">'+json.lits[i].shippingservicecost+'</td><td style="border:1px solid #dddddd;"  width="60px">'+json.lits[i].shippingserviceadditionalcost+'</td><td style="border:1px solid #dddddd;"  width="60px">'+json.lits[i].shippingsurcharge+'</td></tr>'
            }
            html+='</table></div>';
            return html;
        }
    </script>
</head>
<body>
<div class="newds">
    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addshippingDetails()">新增</a></div>
</div>
<div id="shippingDetailsList">

</div>
</body>
</html>
