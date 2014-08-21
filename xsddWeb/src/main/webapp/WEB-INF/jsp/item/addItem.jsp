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
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/dialogs/image/imageextend.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/item/addItem.js" /> ></script>

    <link href=<c:url value ="/js/gridly/css/jquery.gridly.css"/> rel='stylesheet' type='text/css'>
    <link href=<c:url value ="/js/gridly/css/sample.css"/> rel='stylesheet' type='text/css'>
    <script src=<c:url value ="/js/gridly/js/jquery-ui.js"/> type='text/javascript'></script>
    <script src=<c:url value ="/js/gridly/js/jquery.gridly.js"/> type='text/javascript'></script>
    <script src=<c:url value ="/js/gridly/js/sample.js"/> type='text/javascript'></script>
    <script src=<c:url value ="/js/gridly/js/rainbow.js"/> type='text/javascript'></script>

    <script>
        var _sku="ZBQ13212";
        var myDescription=null;


        $(document).ready(function() {
            $().image_editor.init("picUrls"); //编辑器的实例id
            $().image_editor.show("apicUrls"); //上传图片的按钮id

            //加载买家要求
            _invokeGetData_type=null;
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

            var payid = '${item.payId}';
            var buyid = '${item.buyerId}'
            var discountpriceinfoId = '${item.discountpriceinfoId}';
            var itemLocationId = '${item.itemLocationId}';
            var returnpolicyId = '${item.returnpolicyId}';
            var shippingDeailsId = '${item.shippingDeailsId}';
            $("input[name='buyerId'][value='"+buyid+"']").attr("checked","checked");
            $("input[name='payId'][value='"+payid+"']").attr("checked","checked");
            $("input[name='discountpriceinfoId'][value='"+discountpriceinfoId+"']").attr("checked","checked");
            $("input[name='itemLocationId'][value='"+itemLocationId+"']").attr("checked","checked");
            $("input[name='returnpolicyId'][value='"+returnpolicyId+"']").attr("checked","checked");
            $("input[name='shippingDeailsId'][value='"+shippingDeailsId+"']").attr("checked","checked");
            <c:forEach items="${lipa}" var="pa">
                $("#trValue").after().append(addValueTr('${pa.name}','${pa.value}'));
            </c:forEach>


            var site = '${item.site}';
            $("select[name='site']").find("option[value='"+site+"']").attr("selected",true);
            var ebayaccount = '${item.ebayAccount}';
            $("select[name='ebayAccount']").find("option[value='"+ebayaccount+"']").attr("selected",true);
            var ConditionID = '${item.conditionid}';
            $("select[name='ConditionID']").find("option[value='"+ConditionID+"']").attr("selected",true);
            var listingType = '${item.listingtype}';
            $("input[name='listingType'][value='"+listingType+"']").attr("checked",true);
            var title = '${item.title}';
            $("#incount").text(title.length);
            changeRadio(listingType);


            //多属性
            <c:forEach items="${liv}" var="liv" varStatus="status">
                var str ="";
                str +="<tr>";
                str +="<td><input type='text' name='SKU' value='${liv.sku}'></td>";
                str +="<td><input type='text' name='Quantity' value='${liv.quantity}'></td>";
                str +="<td><input type='text' name='StartPrice.value' value=${liv.startprice}></td>";
                <c:forEach items="${liv.tradingPublicLevelAttr}" var="ta">
                    str +="<td><input type='text' name='attr_Value'  onblur='addb(this)' size='10' value='${ta.value}'></td>";
                </c:forEach>
                str +="<td name='del'><a href='javascript:void(0)' onclick='removeCloums(this)'>删除</a></td>";
                str +="</tr>";
                $("#moreAttrs").append(str);
            </c:forEach>
            <c:forEach items="${clso}" var="lis" varStatus="status">
                $("#moreAttrs tr:eq(0)").find("td").each(function (i,d){
                    if($(d).attr("name")=="del"){
                        $(d).before("<td><a href='javascript:void(0)' onclick='removeCols(this)'>移除</a><input type='text' size='8' value='${lis.value}' name='attr_Name' onblur='addc(this)'></td>");
                    }
                });
            </c:forEach>



            var attrValue = new Map();
            $("#moreAttrs tr td:nth-child(4)").each(function (i,d) {
                if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
                    attrValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
                }
            });
            /*var dicMap = new Map()
            for(var i = 0;i<attrValue.keys.length;i++){
                var url = $("input[name='"+attrValue.get(attrValue.keys[i])+"']");
                var dics = new Map();
                for(var j = 0;j<url.length;j++){
                    dics.put(j,$(url[j]).val());
                }
                dicMap.put(attrValue.get(attrValue.keys[i]),dics);
            }*/
            $("#picMore").html("");
            for(var i = 0;i<attrValue.keys.length;i++){
                /*var m = dicMap.get(attrValue.get(attrValue.keys[i]));
                for(var j = 0;j< m.keys.length;j++){
                    $('#'+attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='"+attrValue.get(attrValue.keys[i])+"' value='"+ m.get(j)+"'><img src='"+m.get(j)+"' height='50' width='50' />");
                }*/
                $("#picMore").append(addPic($("#moreAttrs tr:eq(0) td:eq(3)").find("input").val(),attrValue.get(attrValue.keys[i])));
                //$().image_editor.init($("#moreAttrs tr:eq(0) td:eq(3)").find("input").val()+"."+attrValue.get(attrValue.keys[i])); //编辑器的实例id
                $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
            }
            var str='';
            <c:forEach items="${litam}" var="tam" varStatus="status">
                str +="<div class='brick small'>";
                str += '<span><input type="hidden" name="PictureDetails.PictureURL[${status.index}]" value="${tam.value}"><img src="${tam.value}" height="100%" width="100%" /></span>';
                str +="<a class='delete' href='#'>&times;</a></div>";
                $("#picture").append(str);
                str="";
            </c:forEach>

            <c:forEach items="${lipics}" var="pics">
                $("#${pics.tamname}").before("<input type='hidden' name='VariationSpecificValue_${pics.tamname}' value='${pics.tamname}'>");
                <c:forEach items="${pics.litam}" var="pi">
                    $("#${pics.tamname}").before("<span><input type='hidden' name='${pics.tamname}' value='${pi.value}'><img src='${pi.value}' height='50' width='50' /> <a href='javascritp:void(0)' onclick='removeThis(this)'>移除</a></span>");
                </c:forEach>
            </c:forEach>

            var privatelisting = '${tai.privatelisting}';
            $("input[name='PrivateListing'][value='"+privatelisting+"']").attr("checked",true);
            var listingduration = '${tai.listingduration}';
            $("select[name='ListingDuration']").find("option[value='"+listingduration+"']").attr("selected",true);
            //ReservePrice,BuyItNowPrice,ListingFlag,ListingScale,SecondFlag
            var reserveprice = '${tai.reserveprice}';
            var buyitnowprice  = '${tai.buyitnowprice}';
            var listingscale = '${tai.listingscale}';
            var listingflag = '${tai.listingflag}';
            var secondflag = '${tai.secondflag}';
            $("#ReservePrice").val(reserveprice);
            $("#BuyItNowPrice").val(buyitnowprice);
            $("#ListingScale").val(listingscale);
            $("select[name='ListingFlag']").find("option[value='"+listingflag+"']").prop("selected",true);
            $("input[name='SecondFlag'][value='"+secondflag+"']").prop("checked",true);
            initDraug();//初始化拖动图片
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


        function addValueTr(obj1,obj2){
            var trStr='<tr><td><input type="text" name="name"  class="validate[required]" value="'+obj1+'"></td><td><input type="text" name="value" class="validate[required]" value="'+obj2+'"></td><td><a href="javascript:void(0)" onclick="removeROW(this)">移除</a></td></tr>';
            return trStr;
        }
        function removeROW(obj){
            $(obj).parent().parent().remove();
        }
        function addAttrTr(showName,name,value){
            var trStr='<tr><td>'+showName+'</td><td><input type="text" name="'+name+'" value="'+value+'"></td></tr>';
            return trStr;
        }
        /**
        *添加自定义属性
         */
        function addValue(){
            $("#attTable").append(addValueTr('',''));
        }
        /**
        *添加定固定属性
         */
        function addAttr(showName,name){
            $("#trValue").after().append(addAttrTr(showName,name,''));
        }

        function onShow(obj){
            _sku=obj.value;
        }
        //选择刊登 类型，判断显示
        function  changeRadio(obj){
            if(obj=="2"){
                $("#oneAttr").hide();
                $("#twoAttr").show();
                $("#Auction").hide();
            }else if(obj=="FixedPriceItem"){
                $("#oneAttr").show();
                $("#twoAttr").hide();
                $("#Auction").hide();
            }else if(obj=="Chinese"){
                $("#oneAttr").show();
                $("#twoAttr").hide();
                $("#Auction").show();
            }
        }
        //点击添回SKU输入项
        function addInputSKU(obj){
            var len = $(obj).parent().parent().find("table").find("tr").find("td").length/$(obj).parent().parent().find("table").find("tr").length-4;
            $(obj).parent().parent().find("table").append(addTr(len));
        }
        //添加一行数据，用于填写
        function addTr(len){
            var str ="";
            str +="<tr>";
            str +="<td><input type='text' name='SKU'  class='validate[required]'></td>";
            str +="<td><input type='text' name='Quantity' class='validate[required,custom[integer]]'></td>";
            str +="<td><input type='text' name='StartPrice.value' class='validate[required,custom[number]]'></td>";
            for(var i = 0;i < len ;i++){
                str +="<td><input type='text' name='attr_Value'  onblur='addb(this)' size='10' ></td>";
            }
            str +="<td name='del'><a href='javascript:void(0)' onclick='removeCloums(this)'>删除</a></td>";
            str +="</tr>";
            return str;
        }
        //添加属性列
        function addMoreAttr(obj){
            $(obj).parent().parent().find("table").find("tr").each(function(i,d){
                $(d).find("td").each(function(ii,dd){
                    if($(dd).attr("name")=="del"){
                        if(i==0){
                            $(dd).before("<td><a href='javascript:void(0)' onclick='removeCols(this)'>移除</a><input type='text' size='8' name='attr_Name' onblur='addc(this)'></td>");
                        }else{
                            $(dd).before("<td><input type='text' size='10' name='attr_Value' onblur='addb(this)'></td>");
                        }
                    }
                });
            });
        }
        //删除多属性中的SKU输入项
        function removeCloums(obj){
            $(obj).parent().parent().remove();
            var attrValue = new Map();
            $("#moreAttrs tr td:nth-child(4)").each(function (i,d) {
                if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
                    attrValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
                }
            });
            var dicMap = new Map()
            for(var i = 0;i<attrValue.keys.length;i++){
                var url = $("input[name='"+attrValue.get(attrValue.keys[i])+"']");
                var dics = new Map();
                for(var j = 0;j<url.length;j++){
                    dics.put(j,$(url[j]).val());
                }
                dicMap.put(attrValue.get(attrValue.keys[i]),dics);
            }
            $("#picMore").html("");
            for(var i = 0;i<attrValue.keys.length;i++){
                var m = dicMap.get(attrValue.get(attrValue.keys[i]));
                for(var j = 0;j< m.keys.length;j++){
                    $('#'+attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='"+attrValue.get(attrValue.keys[i])+"' value='"+ m.get(j)+"'><img src='"+m.get(j)+"' height='50' width='50' />");
                }
                $("#picMore").append(addPic(attrName,attrValue.get(attrValue.keys[i])));
            }
        }
        //移除属性值
        function removeCols(obj){
            $("#moreAttrs tr th:eq("+($(obj.parentNode)[0].cellIndex+1)+")").remove();
            $("#moreAttrs tr td:nth-child("+($(obj.parentNode)[0].cellIndex+1)+")").remove();

            var attrValue = new Map();
            $("#moreAttrs tr td:nth-child(4)").each(function (i,d) {
                if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
                    attrValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
                }
            });
            var dicMap = new Map()
            for(var i = 0;i<attrValue.keys.length;i++){
                var url = $("input[name='"+attrValue.get(attrValue.keys[i])+"']");
                var dics = new Map();
                for(var j = 0;j<url.length;j++){
                    dics.put(j,$(url[j]).val());
                }
                dicMap.put(attrValue.get(attrValue.keys[i]),dics);
            }
            $("#picMore").html("");
            for(var i = 0;i<attrValue.keys.length;i++){

                $("#picMore").append(addPic(attrName,attrValue.get(attrValue.keys[i])));
                var m = dicMap.get(attrValue.get(attrValue.keys[i]));
                for(var j = 0;j< m.keys.length;j++){
                    $('#'+attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='"+attrValue.get(attrValue.keys[i])+"' value='"+ m.get(j)+"'><img src='"+m.get(j)+"' height='50' width='50' />");
                }
            }

        }
        var attrName = new Map();
        //当输入属性名称时调用的方法
        function addc(obj){
            if($(obj.parentNode)[0].cellIndex==3) {
                attrName = obj.value;
            }

            var attrValue = new Map();
            if($(obj.parentNode)[0].cellIndex==3){
                $("#moreAttrs tr td:nth-child(4)").each(function (i,d) {
                    if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
                        attrValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
                    }
                });
                $("#picMore").html("");
                for(var i = 0;i<attrValue.keys.length;i++){
                    $("#picMore").append(addPic(attrName,attrValue.get(attrValue.keys[i])));
                }
            }
        }
        //当输入属性值时调用的方法
        function addb(obj){
            var attrValue = new Map();
            if($(obj.parentNode)[0].cellIndex==3){
                $("#moreAttrs tr td:nth-child(4)").each(function (i,d) {
                    if($(d).find("input[name='attr_Value']").val()!=undefined&&$(d).find("input[name='attr_Value']").val()!=""){
                        attrValue.put($(d).find("input[name='attr_Value']").val(),$(d).find("input[name='attr_Value']").val());
                    }
                });
                var dicMap = new Map()
                for(var i = 0;i<attrValue.keys.length;i++){
                    var url = $("input[name='"+attrValue.get(attrValue.keys[i])+"']");
                    var dics = new Map();
                    for(var j = 0;j<url.length;j++){
                        dics.put(j,$(url[j]).val());
                    }
                    dicMap.put(attrValue.get(attrValue.keys[i]),dics);
                }
                $("#picMore").html("");
                for(var i = 0;i<attrValue.keys.length;i++){
                    //alert($("input[name='"+attrValue.get(attrValue.keys[i])+"']").length);
                    $("#picMore").append(addPic(attrName,attrValue.get(attrValue.keys[i])));
                    var m = dicMap.get(attrValue.get(attrValue.keys[i]));
                    for(var j = 0;j< m.keys.length;j++){
                        $('#'+attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='"+attrValue.get(attrValue.keys[i])+"' value='"+ m.get(j)+"'><img src='"+m.get(j)+"' height='50' width='50' />");
                    }
                    //$().image_editor.init(attrName+"."+attrValue.get(attrValue.keys[i])); //编辑器的实例id
                    $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
                }
            }
        }
        function addPic(attrName,attrValue){
            var str = "";
            str += "<div><div>"+attrName+":"+attrValue+"</div><script type=text/plain id='"+attrName+"."+attrValue+"' />";
            str += "<div><a href='javascript:void(0)' id="+attrValue+" onClick='selectPic(this)'>选择图片</a></div>";
            str += "</div>";
            return str;
        }

        var afterUploadCallback=null;
        var sss;
        //当选择图片后生成图片地址
        function selectPic(a){
            afterUploadCallback = {"imgURLS":addPictrueUrl};
            sss= a.id;
        }

        function addPictrueUrl(urls){
            if(sss=="apicUrls"){//商品图片
                var str='';
                for(var i=0;i<urls.length;i++){
                    str+="<div class='brick small'>";
                    str+='<span><input type="hidden" name="PictureDetails.PictureURL['+i+']" value="'+urls[i].src+'"><img src=' + urls[i].src.replace("@", ":") + ' height="100%" width="100%" /></span>'
                    str+="<a class='delete' href='#'>&times;</a></div>";
                    $("#picture").append(str);
                    str="";
                }
            }else {//多属性图片
                $('#' + sss).before("<input type='hidden' name='VariationSpecificValue_" + sss + "' value='" + sss + "'>");
                for (var i = 0; i < urls.length; i++) {
                    $('#' + sss).before("<span><input type='hidden' name='" + sss + "' value='" + urls[i].src + "'><img src='" + urls[i].src.replace("@", ":") + "' height='50' width='50' /> <a href='javascritp:void(0)' onclick='removeThis(this)'>移除</a></span>");
                }
            }
            initDraug();//初始化拖动图片
        }
        function removeThis(obj){
            $(obj).parent().remove();
        }

        //当输入分类，
        function addTypeAttr(obj){
            getCategorySpecificsData(obj.value,"typeAttrs","afterClickAttr","attTable");
        }
        var CategoryType;
        function selectType(){
            CategoryType=$.dialog({title: '选择商品分类',
                content: 'url:'+path+'/category/initSelectCategoryPage.do',
                icon: 'succeed',
                width:650,
                lock:true
            });
        }
        function incount(obj){
            $("#incount").text($(obj).val().length);
        }
   </script>
</head>
<c:set var="item" value="${item}"/>
<body>
<form id="form">
    <input type="hidden" name="id" id="id" value="${item.id}">
    <input type="hidden" name="dataMouth" id="dataMouth" value="">

    <table width="100%">
        <tr>
            <td colspan="2">
                一般信息
                <hr/>
            </td>
        </tr>
        <tr>
            <td width="200" align="right">名称</td>
            <td><input type="text" class="validate[required]" name="itemName" id="itemName" value="${item.itemName}"></td>
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
                <input type="radio" name="listingType" value="Chinese" onchange="changeRadio('Chinese')">拍买
                <input type="radio" name="listingType" value="FixedPriceItem" onchange="changeRadio('FixedPriceItem')">固价
                <input type="radio" name="listingType" value="2" onchange="changeRadio('2')">多属性
            </td>
        </tr>
        <tr>
            <td align="right">SKU</td>
            <td><input type="text" name="sku" id="sku"  class="validate[required]" onblur="onShow(this)" value="${item.sku}"></td>
        </tr>
        <tr>
            <td align="right"></td>
            <td><input type="checkbox" name="OutOfStockControl" value="1">是否开启无货在线</td>
        </tr>
        <tr>
            <td align="right" style="vertical-align: top;">物品标题</td>
            <td>
                标题<input type="text" name="Title" id="Title"  class="validate[required,maxSize[80]]" value="${item.title}" size="100" onkeyup="incount(this)"><span id="incount">0</span>/80
                <br/>
                子标题<input type="text" name="SubTitle" id="SubTitle" value="${item.subtitle}" size="100">
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
                <input type="text" id="PrimaryCategory.categoryID" name="PrimaryCategory.categoryID" onblur="addTypeAttr(this)" class="validate[required]" title="PrimaryCategory.categoryID" class="validate[required]" value="${item.categoryid}">
                <a href="javascript:void(0)" onclick="selectType()">请选择</a>
                <br/>
                <div id="PrimaryCategoryshow">
                </div>
            </td>
        </tr>
        <tr>
            <td align="right">第二分类</td>
            <td>
                <input type="text" name="SecondaryCategory.CategoryID" title="SecondaryCategory.CategoryID" value="${item.secondaryCategoryid}">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                商品图片
                <hr/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <div>
                    <section class='example'>
                    <div id="picture" class="gridly">
                    </div>
                    </section>
                    <script type=text/plain id='picUrls'></script>
                    <div><a href="javascript:void(0)" id="apicUrls" onclick="selectPic(this)">选择图片</a></div>
                </div>
            </td>
        </tr>
        <tr id="Auction" style="display: none;">
            <td colspan="2">
                <table width="100%">
                    <tr>
                        <td colspan="2">
                            拍买
                            <hr/>
                        </td>
                    </tr>
                    <tr>
                        <td width="200" align="right">私人拍买：</td>
                        <td><input type="checkbox" name="PrivateListing" value="on">不向公众显示买家的名称</td>
                    </tr>
                    <tr>
                        <td width="200" align="right">刊登天数：</td>
                        <td>
                            <select name="ListingDuration">
                                <option value="Days_1">1</option>
                                <option value="Days_3">3</option>
                                <option value="Days_5">5</option>
                                <option value="Days_7">7</option>
                                <option value="Days_10">10</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="200" align="right">保留价：</td>
                        <td>
                            <input type="text" name="ReservePrice" id="ReservePrice">
                        </td>
                    </tr>
                    <tr>
                        <td width="200" align="right">一口价：</td>
                        <td>
                            <input type="text" name="BuyItNowPrice" id="BuyItNowPrice">
                        </td>
                    </tr>
                    <tr>
                        <td width="200" align="right">

                        </td>
                        <td>
                            <select name="ListingFlag">
                                <option value="0">单独物品</option>
                                <option value="1">批量物品</option>
                            </select>
                            销售比基数<input type="text" name="ListingScale" id="ListingScale">
                            <br/>
                            <input type="checkbox" name="SecondFlag" value="on">二次交易机会
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                物品属性与状况
                <hr/>
            </td>
        </tr>
        <tr id="twoAttr" style="display: none;">
            <td colspan="2">
                <div >
                    <table>
                        <tr>
                            <td width="200" align="right" style="vertical-align: top;">多属性</td>
                            <td>
                                <div>
                                    <table width="100%" id="moreAttrs">
                                        <tr>
                                            <td>Sub SKU</td>
                                            <td>数量</td>
                                            <td>价格</td>
                                            <td name="del">操作</td>
                                        </tr>
                                    </table>
                                </div>
                                <div>
                                    <a href="javascript:void(0)" onclick="addInputSKU(this)">添加SKU项</a> &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"  onclick="addMoreAttr(this)">添加属性</a>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td width="200" align="right" style="vertical-align: top;">多属性图片</td>
                            <td id="picMore">

                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
        <tr>
            <td align="right" style="vertical-align: top;">自定义物品属性</td>
            <td>
                <div id="oneAttr"  style="display: none;">
                    商品价格：<input type="text" name="StartPrice.value" class="validate[required]" value="${item.startprice==null?'0':item.startprice}"/>
                    <br/>
                    商品数量：<input type="text" name="Quantity" value="${item.quantity}" class="validate[required]"/>
                </div>
                <div>
                <table id="attTable">
                    <tr>
                        <td align="center">名称</td>
                        <td align="center">值</td>
                        <td align="center">操作</td>
                    </tr>
                    <tr>
                        <td colspan="3" id="trValue"></td>
                    </tr>
                </table>
                <a href="javascript:void(0);" onclick="addValue();">添加自定义属性</a>
                <div id="typeAttrs"></div>
                <%--<a href="javascript:void(0);" onclick="addAttr('MPN','ProductListingDetails.BrandMPN.MPN');">添加MPN</a>
                <a href="javascript:void(0);" onclick="addAttr('Brand','ProductListingDetails.BrandMPN.Brand');">添加Brand</a>--%>
                <%--<a href="javascript:void(0);" onclick="addValue();">添加Type</a>
                <a href="javascript:void(0);" onclick="addValue();">添加Compatible Brand</a>
                <a href="javascript:void(0);" onclick="addValue();">添加Country/Region of Manufacture</a>--%>
                </div>
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
                <script id="myDescription" type="text/plain" style="width:975px;height:300px;">${item.description}</script>
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
                <input type="button" value="保存数据" onclick="saveData(this,'save')">
                <input type="button" value="保存并刊登" onclick="saveData(this,'all')">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
