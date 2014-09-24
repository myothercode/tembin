<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
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

            var map=new Map();
            map.put("buyerId",path+"/BuyerRequirementDetailsList.do");
            map.put("itemAddr",path+"/ItemAddressList.do");
            map.put("payPal",path+"/PayPalList.do");
            map.put("returnpolicy",path+"/ReturnpolicyList.do");
            map.put("discountPriceInfo",path+"/discountPriceInfoList.do");
            map.put("descriptionDetails",path+"/DescriptionDetailsList.do");
            map.put("shipping",path+"/shippingDetailsList.do");
            $("#module_frame").attr("src",map.get(name)) ;
        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>模块管理</b></div>
    <div class="a_bal"></div>
    <h3>模块管理</h3>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls">
            <dt id=menu1 name="shipping" class=new_tab_1 onclick="setTab(this)">运输选项</dt>
            <dt id=menu2 name="itemAddr" class=new_tab_2 onclick="setTab(this)">物品所在地</dt>
            <dt id=menu3 name="payPal" class=new_tab_2 onclick="setTab(this)">付款方式</dt>
            <dt id=menu4 name="buyerId" class=new_tab_2 onclick="setTab(this)">买家要求</dt>
            <dt id=menu5 name="returnpolicy" class=new_tab_2 onclick="setTab(this)">退货政策</dt>
            <dt id=menu6 name="descriptionDetails" class=new_tab_2 onclick="setTab(this)">卖家描述</dt>
            <dt id=menu7 name="discountPriceInfo" class=new_tab_2 onclick="setTab(this)">折扣信息</dt>
        </div>
    </div>
    <div>
        <iframe src="/xsddWeb/shippingDetailsList.do" id="module_frame" height="600px;" frameborder="0" width="100%">

        </iframe>
    </div>
</div>

</body>
</html>
