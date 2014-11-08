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
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        /*function submitCommit(){
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

        }*/
        function removeRemark(folderId){
            /*var redios=$("input[name=redio]:checked");
            var folderId=redios.val();*/
            /*if(folderId){*/
                var data=null;
                var url=path+"/order/removeOrderTabremark.do?id="+folderId;
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                            W.refleshTabRemark("${folderType}");
                            $("input[value="+folderId+"]").parent().parent().remove();
                            Base.token;
                            /*window.location.reload();*/
                            /*W.selectTabRemark.close();*/
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
        function addRemark1(folder){
            console.debug(folder);
           /* var tr="<tr><td><input type=\"radio\" name=\"redio\" value=\""+folder.id+"\"/></td><td>"+folder.configName+"</td><td><a href=\"javascript:#\" onclick=\"removeRemark("+folder.id+");\">删除文件夹</a></td></tr>";
            */var tr="<tr><td height=\"24\" valign=\"middle\"><input type=\"radio\" name=\"radio\" id=\"radio\" value=\""+folder.id+"\"></td><td height=\"24\" valign=\"middle\">"+folder.configName+"</td><td height=\"24\" valign=\"middle\"><a href=\"javascript:#\" onclick=\"removeRemark("+folder.id+");\">删除</a></td></tr>";
            $("#addTable").append(tr);
        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
        function refleshTabRemark(folderType){
            W.refleshTabRemark(folderType);
        }
    </script>
</head>
<body>
<%--<a href="javascript:#" onclick="addRemark();">新增文件夹</a>--%>

<%--<table>
    <tr>
        <td width="4%" height="30">&nbsp;</td>
        <td width="15%" height="30">择文件夹名称</td>
        <td width="81%" height="30">操作</td>
    </tr>
    <c:forEach items="${folders}" var="folder">
            <tr>
                <td height="24" valign="middle"><input type="radio" name="radio" id="radio" value="${folder.id}"></td>
                &lt;%&ndash;<td>
                    <input type="radio" name="redio"  value="${folder.id}"/>
                </td>&ndash;%&gt;
                <td height="24" valign="middle">${folder.configName}</td>
                &lt;%&ndash;<td>${folder.configName}</td>&ndash;%&gt;
                <td height="24" valign="middle"><a href="javascript:#" onclick="removeRemark(${folder.id});">删除</a></td>
                &lt;%&ndash;<td><a href="javascript:#" onclick="removeRemark(${folder.id});">删除文件夹</a></td>&ndash;%&gt;
            </tr>
    </c:forEach>
</table>--%>
<br/><br/>
<div class="modal-body">
    <form class="form-horizontal" role="form">
        <div class="newtitle_1" style="margin-top:-22px;">新增文件夹</div><table width="100%" border="0">

        <tbody><tr>
            <td width="13%" height="33" align="right"></td>
            <td width="87%" height="33" style="padding-top:8px;">
                <table id="addTable" width="100%" border="0">
                    <tbody><tr>
                        <td width="4%" height="30">&nbsp;</td>
                        <td width="15%" height="30">择文件夹名称</td>
                        <td width="81%" height="30">操作</td>
                    </tr>
                    <c:forEach items="${folders}" var="folder">
                        <tr>
                            <td height="24" valign="middle"><input type="radio" name="radio" id="radio" value="${folder.id}"></td>
                            <td height="24" valign="middle">${folder.configName}</td>
                            <td height="24" valign="middle"><a href="javascript:#" onclick="removeRemark(${folder.id});">删除</a></td>
                        </tr>
                    </c:forEach>
                    </tbody></table>
            </td>
        </tr>

       <%-- <tr>
            <td height="28" align="right"></td>
            <td height="28" style="padding-top:22px;"><button type="button" class="btn btn-newco" onclick="addRemark();">新增</button>
                <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button></td>
        </tr>--%>
        </tbody></table>

    </form></div>
<div class="modal-footer">
    <%--<button type="button" class="btn btn-newco" onclick="submitCommit();">保存</button>--%>
        <button type="button" class="net_put" onclick="addRemark();">新增</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
<%--    <button type="button" class="btn btn-newco" onclick="addRemark();">新增</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>--%>
</div>
</body>
</html>
