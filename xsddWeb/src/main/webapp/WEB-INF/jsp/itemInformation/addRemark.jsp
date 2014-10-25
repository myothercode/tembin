<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function closedialog(){
            W.itemInformation.close();
        }
        function submitCommit(){
            if(!$("#remarkForm").validationEngine("validate")){
                return;
            }
            var url=path+"/information/ajax/saveremark.do";
            var data=$("#remarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
                        W.itemInformation.close();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function(){
            $("#remarkForm").validationEngine();
        });
    </script>
</head>
<body>
    <form id="remarkForm">
        <br/><br/>
        <input type="hidden" name="id" value="${id}"/>
        &nbsp;&nbsp;父类标签:<select name="parentid">
        <option value="0">--请选择--</option>
        <c:forEach items="${parents}" var="parent">
            <option value="${parent.id}">${parent.configName}</option>
        </c:forEach>

    </select><br/><br/>
        &nbsp;&nbsp;标签:&nbsp;<input type="text" class="validate[required]" name="remark" style="width: 800px;"/>

    </form>


    <div class="modal-footer">
        <button type="button" class="btn btn-newco" onclick="submitCommit();">保存</button>
        <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>
    </div>
</body>
</html>
