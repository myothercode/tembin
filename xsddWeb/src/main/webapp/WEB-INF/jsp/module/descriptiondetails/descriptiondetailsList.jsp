<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/30
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var descDiag="";
        function addDescriptionDetails(){
            descDiag=$.dialog({title: '新增卖家描述',
                content: 'url:/xsddWeb/addDescriptionDetails.do',
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function editDescriptionDetails(id){
            descDiag= $.dialog({title: '编辑卖家描述',
                content: 'url:/xsddWeb/editDescriptionDetails.do?id='+id,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        $(document).ready(function(){
            $("#descriptionDetailsListTable").initTable({
                url:path + "/ajax/loadDescriptionDetailsList.do",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:true,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#descriptionDetailsListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
                editDescriptionDetails($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="禁用"||$(obj).find(":selected").text()=="启用"){
                delDescriptionDetails($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="查看"){
                editDescriptionDetailsselect($(obj).find(":selected").val());
            }
            $(obj).val("");
        }
        function editDescriptionDetailsselect(id){
            returnPolicy=$.dialog({title: '查看退货政策',
                content: 'url:/xsddWeb/editDescriptionDetails.do?id='+id+'&type=01',
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function delDescriptionDetails(id){
            var url=path+"/ajax/delDescriptionDetails.do?id="+id;
            $().invoke(url,{},
                    [function(m,r){
                        alert("操作成功！");
                        Base.token();
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
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
    </script>
</head>
<body>
<div class="newds">
    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addDescriptionDetails()">新增</a></div>
</div>
<div id="descriptionDetailsListTable"></div>
<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>pay</td>
            <td>shipping</td>
            <td>contact</td>
            <td>Guarantee</td>
            <td>Feedback</td>
        </tr>
        <c:forEach items="${DescriptionDetailsli}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>${li.payInfo}</td>
                <td>${li.shippingInfo}</td>
                <td>${li.contactInfo}</td>
                <td>${li.guaranteeInfo}</td>
                <td>${li.feedbackInfo}</td>
                <td width="3%"><a target="_blank" href="javascript:void(0)" onclick="editDescriptionDetails('${li.id}');">编辑</a></td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
