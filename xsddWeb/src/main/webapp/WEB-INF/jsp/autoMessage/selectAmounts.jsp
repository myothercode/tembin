<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/16
  Time: 17:46
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
        function closedialog(){
            W.selectCountry1.close();
        }
        function submitCommit(){
            var checkboxs=$("input[type=checkbox][name=checkbox]:checked")
            var amounts=W.document.getElementById("amounts");
            var selectAmounts= W.document.getElementById("selectAmounts");
            var autoMessageId= W.document.getElementById("id");
            var flag="false";
            if($(selectAmounts).val()!="指定来源"){
                flag="true";
            }
            if(checkboxs.length>0){
                var amountIds="";
                var ebayName="";
                for(var i=0;i<checkboxs.length;i++){
                    if(i==(checkboxs.length-1)){
                        amountIds+= $(checkboxs[i]).val();
                        ebayName+=$(checkboxs[i]).attr("value1");
                    }else{
                        amountIds+=$(checkboxs[i]).val()+",";
                        ebayName+=$(checkboxs[i]).attr("value1")+",";
                    }
                }
                var url=path+"/autoMessage/ajax/saveAmount.do?autoMessageId="+$(autoMessageId).val()+"&flag="+flag+"&amountIds="+amountIds+"&ebayName="+ebayName;
                $().invoke(url,null,
                        [function(m,r){
                            var coutryid="";
                            for(var i=0;i< r.length;i++){
                                if(i==(r.length-1)) {
                                    coutryid+=r[i];
                                }else{
                                    coutryid+=r[i]+",";
                                }
                            }
                            selectAmounts.innerHTML=ebayName;
                            $(amounts).val(coutryid);
                            W.selectCountry1.close();
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }else{
                alert("请选择账号");
            }
        }
    </script>
</head>
<body>
<div style="padding: 10px;height: 240px;"><br/>
    <form id="amountForm">
    <c:forEach items="${ebays}" var="ebay" varStatus="status">
        <c:if test="${status.index%4==0}">
            &nbsp;&nbsp;&nbsp;<input type="checkbox" name="checkbox" value="${ebay.id}" value1="${ebay.ebayName}"/>${ebay.ebayName}<br/>
        </c:if>
        <c:if test="${status.index%4!=0}">
            &nbsp;&nbsp;&nbsp;<input type="checkbox" name="checkbox" value="${ebay.id}" value1="${ebay.ebayName}"/>${ebay.ebayName}
        </c:if>
    </c:forEach>
    </form>
</div>
<div class="modal-footer" style="text-align: right;width: 700px;">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
   <%-- <button type="button" class="btn btn-primary" onclick="submitCommit();">保存</button>
    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>--%>
</div>
</body>
</html>
