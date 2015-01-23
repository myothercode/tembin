<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
<title></title>
<script>
    var api = frameElement.api, W = api.opener;
    $(document).ready(function () {
        var ebayAccount = '${ttp.ebayAccount}';
        $("input[name='ebayAccounts'][value='" + ebayAccount + "']").attr("checked", true);
        var type = '${type}';
        if(type=="01"){
            $("input").each(function(i,d){
                $(d).attr("disabled",true);
            });
        }
        $("#form").validationEngine();
    });
    function closeWindow(){
        W.addTablePrice.close();
    }
    function saveTablePrice(objs){
        if(!$("#form").validationEngine("validate")){
            return;
        }
        var data = $('#form').serialize();
        var urll = path+"/ajax/saveTablePrice.do";
        $(objs).attr("disabled",true);
        var api = frameElement.api, W = api.opener;
        $().invoke(
                urll,
                data,
                [function (m, r) {
                    //Base.token();
                    alert(r);
                    $(objs).attr("disabled",false);
                    W.reshTable();
                    W.addTablePrice.close();
                },
                    function (m, r) {
                        //Base.token();
                        alert(r)
                        $(objs).attr("disabled",false);
                        W.addTablePrice.close();
                    }]
        )
    }
</script>
</head>
<body>
<div class="new_all">
<form id="form" class="new_user_form">
<div class="a_bal"></div>
<div class="new">
    <input type="hidden" name="id" id="id" value="${ttp.id}">
    <li>
        <dt>ebay账户</dt>
        <c:forEach items="${ebayList}" var="ebay">
            <em style="color:#48a5f3"><input type="checkbox" name="ebayAccounts" value="${ebay.ebayName}"
                                             shortName="${ebay.ebayName}" >${ebay.ebayNameCode}</em>
        </c:forEach>
    </li>
    <li>
        <dt>SKU</dt>
        <div class="new_left">
            <input type="text" name="sku" id="sku" style="width:300px;" class="validate[required] form-control"
                    value="${ttp.sku}">
<%--            <b class="new_button"><a data-toggle="modal" href="javascript:void(0)"
                                     onclick="selectProduct()">选择产品</a></b>--%>
        </div>
    </li>
    <li>
        <dt>价格</dt>
        <div class="new_left">
            <input type="text" name="price" style="width:300px;"
                   class="validate[required] form-control"
                   value="${ttp.price}"/>
        </div>
    </li>
    <br/>
</div>
    <div class="a_bal"></div>
    <div>
        <input type="button" value="确定" onclick="saveTablePrice(this)">
        <input type="button" value="关闭" onclick="closeWindow()">
    </div>
</form>
</div>

</body>
</html>
