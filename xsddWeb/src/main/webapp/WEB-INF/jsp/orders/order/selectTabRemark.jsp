<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/24
  Time: 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        var selectTabRemark;
       /* $(document).ready(function(){
            $("#selectRemark").initTable({
                url:path + "/order/ajax/loadselectRemarkList.do?",
                columnData:[
                    {title:"",name:"ch",width:"2%",align:"top",format:makeOption5},
                    {title:"文件夹名称",name:"configName",width:"8%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
         })
        function makeOption5(json){
            var htm="<input type='redio' id='redio' value='"+json.id+"' />";
            return htm;
        }
        function refreshTable(){
            $("#selectRemark").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }*/
        function addRemark(){
            var url=path+"/order/addTabRemark.do?folderType=${folderType}";
            selectTabRemark=$.dialog({title: '新建文件夹',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                lock:true
            });
        }
        function submitCommit(){
            var redios=$("input[name=redio]:checked");
            var folderId=redios.val();
            if(folderId){
                var data=null;
                var url=path+"/order/saveOrderTabremark.do?id="+folderId;
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                            W.location.reload();
                            Base.token;
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }else{
                W.OrderGetOrders.close();
            }

        }
        function removeRemark(folderId){
            /*var redios=$("input[name=redio]:checked");
            var folderId=redios.val();*/
            /*if(folderId){*/
                var data=null;
                var url=path+"/order/removeOrderTabremark.do?id="+folderId;
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                           /* W.location.reload();*/
                            window.location.reload();
                            /*W.selectTabRemark.close();*/
                            Base.token;
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            /*}else{
                alert("请选择需要删除的文件夹");
            }*/
        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
    </script>
</head>
<body>
<a href="javascript:#" onclick="addRemark();">新增文件夹</a>
<table>
    <tr>
        <td></td>
        <td>文件夹名称</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${folders}" var="folder">
            <tr>
                <td>
                    <input type="radio" name="redio" value="${folder.id}"/>
                </td>
                <td>${folder.configName}</td>
                <td><a href="javascript:#" onclick="removeRemark(${folder.id});">删除文件夹</a></td>
            </tr>
    </c:forEach>
</table>
<div align="right">
    <input type="button" value="保存" onclick="submitCommit();"/>
    <input type="button" value="关闭" onclick="closedialog();"/>
</div>
</body>
</html>
