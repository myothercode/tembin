<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>

    <script>
        var myDescription=null;
        $(document).ready(function() {
            //加载买家要求
            $("#buyer").initTable({
                url:path + "/ajax/loadBuyerRequirementDetailsList.do",
                columnData:[
                    {title:"选择",name:"option1",width:"8%",align:"left",format:returnBuyer},
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"所有买家购买",name:"buyerFlag",width:"8%",align:"left"}
                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false
            });

            //加载折扣信息
            $("#discountpriceinfo").initTable({
                url:path + "/ajax/loadDiscountPriceInfoList.do",
                columnData:[
                    {title:"选项",name:"option1",width:"8%",align:"left",format:returnDiscountpriceinfo},
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"账户名称",name:"ebayName",width:"8%",align:"left"},
                    {title:"开始时间",name:"disStarttime",width:"8%",align:"left"},
                    {title:"结束时间",name:"disEndtime",width:"8%",align:"left"},
                    {title:"降价",name:"madeforoutletcomparisonprice",width:"8%",align:"left"},
                    {title:"是否免运费",name:"isShippingfee",width:"8%",align:"left"}
                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false
            });

            //物品所在地
            $("#itemLocation").initTable({
                url:path + "/ajax/loadItemAddressList.do",
                columnData:[
                    {title:"选项",name:"option1",width:"8%",align:"left",format:returnItemLocation},
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"地址",name:"address",width:"8%",align:"left"},
                    {title:"国家",name:"countryName",width:"8%",align:"left"},
                    {title:"邮编",name:"postalcode",width:"8%",align:"left"}
                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false
            });
            //付款选项
            $("#pay").initTable({
                url:path + "/ajax/loadPayPalList.do",
                columnData:[
                    {title:"选项",name:"option1",width:"8%",align:"left",format:returnPay},
                    {title:"名称",name:"payName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"paypal账号",name:"payPalName",width:"8%",align:"left"},
                    {title:"描述",name:"paymentinstructions",width:"8%",align:"left"}

                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false
            });

            //退货选项
            $("#returnpolicy").initTable({
                url:path + "/ajax/loadReturnpolicyList.do",
                columnData:[
                    {title:"选项",name:"option1",width:"8%",align:"left",format:returnReturnpolicy},
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"退货政策",name:"returnsAcceptedOptionName",width:"8%",align:"left"},
                    {title:"退货天数",name:"returnsWithinOptionName",width:"8%",align:"left"},
                    {title:"退款方式",name:"refundOptionName",width:"8%",align:"left"},
                    {title:"退货运费由谁负担",name:"shippingCostPaidByOptionName",width:"8%",align:"left"}

                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false
            });
            //运输选项
            $("#shippingDeails").initTable({
                url:path + "/ajax/loadShippingDetailsList.do",
                columnData:[
                    {title:"选项",name:"option1",width:"8%",align:"left",format:returnShippingDeails},
                    {title:"名称",name:"shippingName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"ebay账号",name:"ebayName",width:"8%",align:"left"}

                ],
                selectDataNow:true,
                isrowClick:false,
                showIndex:false
            });

            $("#form").validationEngine();
            myDescription = UE.getEditor('myDescription');
        });

        /**返回买家要求单选框*/
        function returnBuyer(json){
            var htm="<input type=\"radio\" name=\"buyerId\" value="+json.id+">";
            return htm;
        }

        /**返回折扣信息单选框*/
        function returnDiscountpriceinfo(json){
            var htm="<input type=\"radio\" name=\"discountpriceinfoId\" value="+json.id+">";
            return htm;
        }

        /**返回物品所在地信息单选框*/
        function returnItemLocation(json){
            var htm="<input type=\"radio\" name=\"itemLocationId\" value="+json.id+">";
            return htm;
        }
        /**返回付款选项信息单选框*/
        function returnPay(json){
            var htm="<input type=\"radio\" name=\"payId\" value="+json.id+">";
            return htm;
        }


        /**返回退货项信息单选框*/
        function returnReturnpolicy(json){
            var htm="<input type=\"radio\" name=\"returnpolicyId\" value="+json.id+">";
            return htm;
        }

        /**返回运输选项信息单选框*/
        function returnShippingDeails(json){
            var htm="<input type=\"radio\" name=\"shippingDeailsId\" value="+json.id+">";
            return htm;
        }

        function saveData(objs) {
            if(!$("#form").validationEngine("validate")){
                return;
            }
            var nameList = $("input[type='text'][name='name']").each(function(i,d){
                var name_= $(d).prop("name");
                var t="ItemSpecifics.NameValueList["+i+"].";
                $(d).prop("name",t+name_);
            });
            var valueList = $("input[type='text'][name='value']").each(function(i,d){
                var name_= $(d).prop("name");
                var t="ItemSpecifics.NameValueList["+i+"].";
                $(d).prop("name",t+name_);
            });
            $("#Description").val(myDescription.getContent());
            var data = $('#form').serialize();
            var urll = "/xsddWeb/saveItem.do";
            $(objs).attr("disabled",true);
            var api = frameElement.api, W = api.opener;
            $().invoke(
                    urll,
                    data,
                    [function (m, r) {
                        Base.token();
                        alert(r);
                        $(objs).attr("disabled",false);
                        W.refreshTable();
                        W.returnItem.close();
                    },
                        function (m, r) {
                            Base.token();
                            $(objs).attr("disabled",false);
                        }]
            )
        }
        function addValueTr(obj1,obj2){
            var trStr='<tr><td><input type="text" name="name" value="'+obj1+'"></td><td><input type="text" name="value" value="'+obj2+'"></td></tr>';
            return trStr;
        }
        function addAttrTr(name,value){
            var trStr='<tr><td>'+name+'</td><td><input type="text" name="'+name+'" value="'+value+'"></td></tr>';
            return trStr;
        }
        /**
        *添加自定义属性
         */
        function addValue(){
            $("#trValue").after().append(addValueTr('',''));
        }
        /**
        *添加定固定属性
         */
        function addAttr(name){
            $("#trValue").after().append(addAttrTr(name,''));
        }
   </script>
</head>
<c:set var="item" value="${item}"/>
<body>
<form id="form">
    <table width="100%">
        <tr>
            <td colspan="2">
                一般信息
                <hr/>
            </td>
        </tr>
        <tr>
            <td width="200" align="right">名称</td>
            <td><input type="text" class="validate[required]" name="itemName" id="itemName" ></td>
        </tr>
        <tr>
            <td align="right">ebay账户</td>
            <td>
                <select name="ebayAccount" class="validate[required]" >
                    <c:forEach items="${ebayList}" var="ebay">
                        <option value="${ebay.id}">${ebay.configName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right">站点</td>
            <td>
                <select name="site">
                    <c:forEach items="${siteList}" var="sites">
                        <option value="${sites.id}">${sites.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td align="right">刊登类型</td>
            <td>
                <input type="radio" name="listingType" value="0" disabled>拍买
                <input type="radio" name="listingType" value="1" checked>固价
                <input type="radio" name="listingType" value="2">多属性
            </td>
        </tr>
        <tr>
            <td align="right">SKU</td>
            <td><input type="text" name="SKU" id="SKU"  class="validate[required]"></td>
        </tr>
        <tr>
            <td align="right" style="vertical-align: top;">物品标题</td>
            <td>
                标题<input type="text" name="Title" id="Title"  class="validate[required]" value="" >
                <br/>
                子标题<input type="text" name="SubTitle" id="SubTitle" value="">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                分类
                <hr/>
            </td>
        </tr>
        <tr>
            <td align="right">第一分类</td>
            <td>
                <input type="text" name="PrimaryCategory.categoryID" title="PrimaryCategory.categoryID" value="">
            </td>
        </tr>
        <tr>
            <td align="right">第二分类</td>
            <td>
                <input type="text" name="SecondaryCategory.CategoryID" title="SecondaryCategory.CategoryID" value="">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                物品属性与状况
                <hr/>
            </td>
        </tr>
        <tr>
            <td align="right" style="vertical-align: top;">自定义物品属性</td>
            <td>
                <table>
                    <tr>
                        <td align="center">名称</td>
                        <td align="center">值</td>
                    </tr>
                    <tr>
                        <td colspan="2" id="trValue"></td>
                    </tr>
                </table>
                <a href="javascript:void(0);" onclick="addValue();">添加自定义属性</a>
                <a href="javascript:void(0);" onclick="addAttr('ProductListingDetails.BrandMPN.MPN');">添加MPN</a>
                <a href="javascript:void(0);" onclick="addAttr('ProductListingDetails.BrandMPN.Brand');">添加Brand</a>
                <%--<a href="javascript:void(0);" onclick="addValue();">添加Type</a>
                <a href="javascript:void(0);" onclick="addValue();">添加Compatible Brand</a>
                <a href="javascript:void(0);" onclick="addValue();">添加Country/Region of Manufacture</a>--%>
            </td>
        </tr>
        <tr>
            <td align="right">物品状况</td>
            <td>
                <select name="ConditionID">
                    <option selected="selected" value="1000">New</option>
                    <option value="1500">New other (see details)</option>
                    <option value="2000">Manufacturer refurbished</option>
                    <option value="2500">Seller refurbished</option>
                    <option value="3000">Used</option>
                    <option value="7000">For parts or not working</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                描述
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="hidden" name="Description" id="Description">
                <script id="myDescription" type="text/plain" style="width:975px;height:300px;"></script>
            </td>
        </tr>

        <tr>
            <td colspan="2">
                买家要求
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div  id="buyer">

                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                折扣信息
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div  id="discountpriceinfo">

                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                物品所在地
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div  id="itemLocation">

                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                付款选项
                <hr/>
            </td>
        </tr>
        <td colspan="2">
            <div  id="pay">

            </div>
        </td>
        <tr>
            <td colspan="2">
                退货选项
                <hr/>
            </td>
        </tr>
        <td colspan="2">
            <div  id="returnpolicy">

            </div>
        </td>

        <tr>
            <td colspan="2">
                运输选项
                <hr/>
            </td>
        </tr>
        <td colspan="2">
            <div  id="shippingDeails">

            </div>
        </td>

        <tr>
            <td colspan="2">
                <input type="button" value="确定" onclick="saveData(this)">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
