<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <script type="text/javascript" src=<c:url value ="/js/module/shipping.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/itemAddress.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/paypal.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/returnpolicy.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/buyer.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/description.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/module/disprice.js" /> ></script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <title></title>
    <script>
        function setTab(obj){
            var name=$(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if($(d).attr("name")==name){
                    $(d).attr("class","new_tab_1");
                }else{
                    $(d).attr("class","new_tab_2");
                }
            });
            $("#cent").find("div").each(function(i,d){
                $(d).hide();
            });
            if(name=="shipping"){
                $("#shippingDetailsList").show();
                loadShipping();
            }else if(name=="itemAddr"){
                $("#ItemAddressListTable").show();
                loadAddress();
            }else if(name=="payPal"){
                $("#paypallisttable").show();
                loadpaypal();
            }else if(name=="returnpolicy"){
                $("#returnPolicyListTable").show();
                loadreturnpolicy();
            }else if(name=="buyerId"){
                $("#buyerRequireTable").show();
                loadbuyer();
            }else if(name=="descriptionDetails"){
                $("#descriptionDetailsListTable").show();
                loadDesciption();
            }else if(name=="discountPriceInfo"){
                $("#discountPriceInfoListTable").show();
                loadDisPrice();
            }
            //$("#showAddButton").css("z-index","10000");
        }
        $(document).ready(function(){
            loadShipping();
        });


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
        function addModel(){
            var modelname = "";
            $("#selectModel").find("dt").each(function(i,d){
                if($(d).prop("class")=="new_tab_1"){
                    modelname = $(d).attr("name");
                }
            });
            if(modelname=="shipping"){
                addshippingDetails();
            }else if(modelname=="itemAddr"){
                addItemAddress();
            }else if(modelname=="payPal"){
                addPayPal();
            }else if(modelname=="returnpolicy"){
                addReturnpolicy();
            }else if(modelname=="buyerId"){
                addBuyer();
            }else if(modelname=="descriptionDetails"){
                addDescriptionDetails();
            }else if(modelname=="discountPriceInfo"){
                adddiscountpriceinfo();
            }

        }

    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>模块管理</b></div>
    <div class="a_bal"></div>
    <%--<div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" class=""  onclick="addModel()">新增</a></div>--%>
<%--    <div class="a_bal"></div>--%>
    <div class="new">
        <div class="new_tab_ls" id="selectModel">
            <dt id=menu1 name="shipping" class=new_tab_1 onclick="setTab(this)">运输选项</dt>
            <dt id=menu2 name="itemAddr" class=new_tab_2 onclick="setTab(this)">物品所在地</dt>
            <dt id=menu3 name="payPal" class=new_tab_2 onclick="setTab(this)">付款方式</dt>
            <dt id=menu4 name="buyerId" class=new_tab_2 onclick="setTab(this)">买家要求</dt>
            <dt id=menu5 name="returnpolicy" class=new_tab_2 onclick="setTab(this)">退货政策</dt>
            <dt id=menu6 name="descriptionDetails" class=new_tab_2 onclick="setTab(this)">卖家描述</dt>
            <dt id=menu7 name="discountPriceInfo" class=new_tab_2 onclick="setTab(this)">折扣信息</dt>
        </div>
    </div>
    <div class="tbbay" id="showAddButton" style="background-image:url(null);position: absolute;top: 60px;right: 40px;z-index: 10000;">
        <img src="img/adds.png" onclick="addModel()">
        <%--<a data-toggle="modal" href="javascript:void(0)" class=""  onclick="addModel()">新增</a>--%>
    </div>
    <div id="cent">
        <%--<iframe src="/xsddWeb/shippingDetailsList.do" id="module_frame" height="600px" frameborder="0" width="100%">

        </iframe>--%>

        <div id="shippingDetailsList"></div>
        <div id="ItemAddressListTable"></div>
        <div id="paypallisttable"></div>
        <div id="returnPolicyListTable"></div>
            <div id="buyerRequireTable"></div>
            <div id="descriptionDetailsListTable"></div>
            <div id="discountPriceInfoListTable"></div>
    </div>
</div>

</body>
</html>
