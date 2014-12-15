<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/15
  Time: 9:41
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
            W.autoMessage.close();
        }
        var selectCountry1;
        function selectCountrys(){
            var url=path+'/autoMessage/selectCountrys.do?id='+$("#id").val();
            selectCountry1 = openMyDialog({title: '有效国家',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:350,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function selectExceptCountrys(){
            var url=path+'/autoMessage/selectExceptCountrys.do?id='+$("#id").val();
            selectCountry1 = openMyDialog({title: '指定国家之外',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:350,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function selectItems(){
            var url=path+'/autoMessage/selectItems.do?id='+$("#id").val();
            selectCountry1 = openMyDialog({title: '指定商品',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:250,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function selectAmounts(){
            var url=path+'/autoMessage/selectAmounts.do?';
            selectCountry1 = openMyDialog({title: '指定账号',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:350,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function selectShippingServices(){
            var url=path+'/autoMessage/selectShippingServices.do?';
            selectCountry1 = openMyDialog({title: '指定物流',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:400,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function selectMessageTemplate(){

            var url=path+'/autoMessage/selectMessageTemplate.do?';
            selectCountry1 = openMyDialog({title: '选择模块',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:350,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function addcountry(){
            var country=$("input[type=checkbox][name=country]");
            if(country[0].checked){
                var htm="<div id='selectCountry'>订单目的地:<a id='selectCountrys' href='javascript:void(0);' onclick='selectCountrys();'><font style=\"color: #0000ff\">指定国家</font></a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectCountry").remove();
                $("#countryIds").val("");
            }
        }
        function addExceptCountry(){
            var country=$("input[type=checkbox][name=exceptCountry]");
            if(country[0].checked){
                var htm="<div id='selectExceptCountry'>订单目的地:<a id='selectExceptCountrys' href='javascript:void(0)' onclick='selectExceptCountrys();'><font style=\"color: #0000ff\">指定国家之外</font></a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectExceptCountry").remove();
                $("#exceptCountryIds").val("");
            }
        }
        function addAllOrder(){
            var country=$("input[type=checkbox][name=allOrder]");
            if(country[0].checked){
                var htm="<div id='selectAllOrder'>所有订单:<font style=\"color: #0000ff\">所有的订单</font><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectAllOrder").remove();
                $("#allOrder").val("");
            }
        }
        function addItem(){
            var item=$("input[type=checkbox][name=item]");
            if(item[0].checked){
                var htm="<div id='selectItem'>订单商品:<a id='selectItems' href='javascript:void(0)' onclick='selectItems();'><font style=\"color: #0000ff\">指定商品</font></a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectItem").remove();
                $("#orderItems").val("");
            }
        }
        function addAmount(){
            var amount=$("input[type=checkbox][name=amount]");
            if(amount[0].checked){
                var htm="<div id='selectAmount'>订单来源:<a id='selectAmounts' href='javascript:void(0)' onclick='selectAmounts();'><font style=\"color: #0000ff\">指定账号</font></a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectAmount").remove();
                $("#amounts").val("");
            }
        }
        function addShippingService(){
            var amount=$("input[type=checkbox][name=shippingService]");
            if(amount[0].checked){
                var htm="<div id='selectShippingService'>买家选择的物流:<a id='selectShippingServices' href='javascript:void(0)' onclick='selectShippingServices();'><font style=\"color: #0000ff\">指定物流方式</font></a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectShippingService").remove();
                $("#service").val("");
            }
        }
        function submitCommit(){
            if(!$("#autoMessageForm").validationEngine("validate")){
                return;
            }
            var checkboxs=$("#regulation").find("input[type=checkbox]:checked");
            var allOrder1=$("#regulation").find("input[type=checkbox][name=allOrder]:checked");
            if(checkboxs.length==0){
                alert("选择规则至少选择一个");
                return;
            }
            var countryIds=$("#countryIds").val();
            var orderItems=$("#orderItems").val();
            var amounts=$("#amounts").val();
            var service=$("#service").val();
            var exceptCountryIds=$("#exceptCountryIds").val();
            if(!countryIds&&!orderItems&&!amounts&&!service&&!exceptCountryIds&&allOrder1.length==0){
                alert("选择规则至少一个有效值");
                return;
            }
            var selectAllOrder=document.getElementById("selectAllOrder");
            if(selectAllOrder){
                $("#allOrder").val("true");
            }
            var url=path+"/autoMessage/ajax/saveAutoMessage.do?";
            var date=$("#autoMessageForm").serialize();
            $().invoke(url,date,
                    [function(m,r){
                        alert(r);
                        var url=path + "/autoMessage/ajax/loadAutoMessageList.do?status=1";
                        var checkboxStatus= W.document.getElementById("checkboxStatus");
                        if(checkboxStatus.checked){
                            url=path + "/autoMessage/ajax/loadAutoMessageList.do?status=0";
                        }
                        W.refreshTable1(url);
                        W.autoMessage.close();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function(){
            $("#autoMessageForm").validationEngine();
        });
    </script>
</head>
<body>
<form class="form-horizontal" id="autoMessageForm">
        <input type="hidden" name="id" id="id" value="${autoMessage.id}">
        <input type="hidden" name="countryIds" id="countryIds"/>
        <input type="hidden" name="orderItems" id="orderItems"/>
        <input type="hidden" name="amounts" id="amounts"/>
        <input type="hidden" name="service" id="service"/>
        <input type="hidden" name="exceptCountryIds" id="exceptCountryIds"/>
        <input type="hidden" name="allOrder" id="allOrder"/>
        <input type="hidden" name="starUse value="${autoMessage.startuse}">
        <div>
            <br/><br/><br/>
        <div  style="border: 1px #acd0f0 solid;width: 930px;height:150px;margin-left:5px;background-color:whitesmoke">
            <div class="control-group" style="padding: 30px;">
                <!-- Text input-->
                <label class="control-label" style="width: 100px;" >消息模块&nbsp;</label>
                <div class="controls">
                    <input type="hidden" id="messageId" name="messageId"/>
                    <input type="text" placeholder="" onclick="selectMessageTemplate();" style="width: 250px;" class="form-controlsd validate[required]" value="${autoMessage.subject}" name="subject" id="subject">&nbsp;<a href="#" style="line-height: 35px;" onclick="selectMessageTemplate();">选择</a>
                    <p class="help-block"></p>
                </div>
                    <div>
                        <label class="control-label" style="width: 100px;" >在&nbsp;</label>
                        <div class="controls">
                            <select class="input-xlarge" name="type" style="float: left">
                                <option value="null">--请选择--</option>
                                <c:if test="${autoMessage.type=='收到买家付款'}">
                                    <option selected>收到买家付款</option>
                                </c:if>
                                <c:if test="${autoMessage.type!='收到买家付款'}">
                                    <option>收到买家付款</option>
                                </c:if>
                                <c:if test="${autoMessage.type=='标记已发货'}">
                                    <option selected>标记已发货</option>
                                </c:if>
                                <c:if test="${autoMessage.type!='标记已发货'}">
                                    <option>标记已发货</option>
                                </c:if>
                                <c:if test="${autoMessage.type=='收到买家的正评'}">
                                   <option selected>收到买家的正评</option>
                                </c:if>
                                <c:if test="${autoMessage.type!='收到买家的正评'}">
                                    <option>收到买家的正评</option>
                                </c:if>
                                 <c:if test="${autoMessage.type=='收到买家的中评'}">
                                   <option selected>收到买家的中评</option>
                                 </c:if>
                                <c:if test="${autoMessage.type!='收到买家的中评'}">
                                    <option>收到买家的中评</option>
                                </c:if>
                                <c:if test="${autoMessage.type=='收到买家的负评'}">
                                    <option selected>收到买家的负评</option>
                                </c:if>
                                <c:if test="${autoMessage.type!='收到买家的负评'}">
                                    <option>收到买家的负评</option>
                                </c:if>
                            </select>&nbsp;后
                            <select name="day">
                                <c:forEach var="i"  begin="0" end="30" step="1">
                                    <c:if test="${autoMessage.day==i}">
                                        <option selected>${i}</option>
                                    </c:if>
                                    <c:if test="${autoMessage.day!=i}">
                                        <option>${i}</option>
                                    </c:if>
                                </c:forEach>
                            </select>&nbsp;天
                            <select name="hour">
                                <c:forEach var="i"  begin="0" end="23" step="1">
                                    <c:if test="${autoMessage.hour==i}">
                                        <option selected>${i}</option>
                                    </c:if>
                                    <c:if test="${autoMessage.hour!=i}">
                                        <option>${i}</option>
                                    </c:if>
                                </c:forEach>
                            </select>小时发送
                    </div>
                </div>
              <%--  <label class="control-label" style="width: 100px;" >发送方式&nbsp;</label>--%>
                <!-- Multiple Checkboxes -->
                <div class="controls">
                    <label class="checkbox inline" style="width: 150px;">
                        <%--<input type="checkbox" name="ebay" value="1" checked disabled>EBAY MESSAGE</label>--%>
                    <%--<label class="checkbox inline">
                        <c:if test="${autoMessage.ebayemail==1}">
                            <input type="checkbox" name="ebay" value="1" checked>eBay消息
                        </c:if>
                        <c:if test="${autoMessage.ebayemail!=1}">
                            <input type="checkbox" name="ebay" value="1" >eBay消息
                        </c:if>
                    </label>
                    <label class="checkbox inline">
                        <c:if test="${autoMessage.email==1}">
                             <input type="checkbox" name="email" checked value="1">电邮
                        </c:if>
                        <c:if test="${autoMessage.email!=1}">
                            <input type="checkbox" name="email" value="1">电邮
                        </c:if>
                    </label>--%>
                </div>
            </div>
        </div>
</form>
<br/>
        <%--<div class="control-group">
            <label class="control-label" style="width: 300px;" >对以下eBay账号产生的交易有效&nbsp;</label>
            <div class="controls">
                <select multiple >
                    <option>所有账户</option>
                    <c:forEach items="${ebays}" var="ebay">
                        <option>${ebay.ebayName}</option>
                    </c:forEach>
                </select></div><br/>
            </div>
            <label class="control-label" style="width: 300px;" >对来自以下国家的买家有效&nbsp;</label>
            <div class="controls">
                 <input type="text" id="countrys" placeholder="" class="input-xlarge">&nbsp;<a href="#" onclick="selectCountrys();">选择</a>
            </div>
            <label class="control-label" style="width: 300px;" >对以下运输方式有效&nbsp;</label>
            <div class="controls">
                <select></select>
            </div>
        </div>--%>
        <div style="padding: 10px;">
            <div style="width: 560px;height:300px;border: 1px #acd0f0 solid;float: left">
                <div class="new_tab">
                    <div class="new_tab_left"></div>
                    <div class="new_tab_right"></div>
                    <dt id="svt1" >条件描述</dt>
                </div>
                <div style="padding: 20px;margin-top: 40px;" id="addMarket">
                    <c:if test="${items[0]!=null}">
                        <div id='selectItem'>订单商品:<a id='selectItems' href='javascript:void(0)' onclick='selectItems();'>
                            <c:forEach items="${items}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                <font style="color: #0000ff">${item.value}</font>
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                <font style="color: #0000ff">${item.value},</font>
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${countrys[0]!=null}">
                        <div id='selectCountry'>订单目的地:<a id='selectCountrys' href='javascript:void(0)' onclick='selectCountrys();'>
                            <c:forEach items="${countrys}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                <font style="color: #0000ff">${item.value}</font>
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                <font style="color: #0000ff">${item.value},</font>
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${amounts[0]!=null}">
                        <div id='selectAmount'>订单来源:<a id='selectAmounts' href='javascript:void(0)' onclick='selectAmounts();'>
                            <c:forEach items="${amounts}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                <font style="color: #0000ff">${item.value}</font>
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                <font style="color: #0000ff">${item.value},</font>
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${services[0]!=null||internationalServices[0]!=null}">
                        <div id='selectShippingService'>买家选择的物流:<a id='selectShippingServices' href='javascript:void(0)' onclick='selectShippingServices();'>

                            <c:forEach items="${services}" var="item" varStatus="status">
                                <c:if test="${status.index==0}">国内运输方式:</c:if>
                                <c:if test="${status.index==(status.count-1)}">
                                    <font style="color: #0000ff">${item.value};</font>
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                            <font style="color: #0000ff">${item.value},</font>
                                </c:if>
                            </c:forEach>

                            <c:forEach items="${internationalServices}" var="item" varStatus="status">
                                <c:if test="${status.index==0}">国际运输方式:</c:if>
                                <c:if test="${status.index==(status.count-1)}">
                            <font style="color: #0000ff">${item.value};</font>
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                            <font style="color: #0000ff">${item.value},</font>
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${order!=null}">
                        <div id='selectAllOrder'>所有订单:<font style="color: #0000ff">所有的订单</font><br/></div>
                    </c:if>
                    <c:if test="${exceptCountrys[0]!=null}">
                        <div id='selectExceptCountry'>订单目的地:<a id='selectExceptCountrys' href='javascript:void(0)' onclick='selectExceptCountrys();'>
                            <c:forEach items="${exceptCountrys}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                    <font style="color: #0000ff">${item.value}之外</font>
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                <font style="color: #0000ff">${item.value},</font>
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                </div>
            </div>
            <div style="width: 350px;height:300px;border: 1px #acd0f0 solid;float: right">
                <div class="new_tab">
                    <div class="new_tab_left"></div>
                    <div class="new_tab_right"></div>
                    <dt id="svt1" >选择规则</dt>
                </div>
                <div id="regulation" style="padding: 20px;margin-top: 40px;">
                    <c:if test="${items[0]!=null}">
                        <input type="checkbox" name="item" checked onclick="addItem();">&nbsp;订单商品包含指定商品<br/><br/>
                    </c:if>
                    <c:if test="${items[0]==null}">
                        <input type="checkbox" name="item"  onclick="addItem();">&nbsp;订单商品包含指定商品<br/><br/>
                    </c:if>
                    <c:if test="${countrys[0]!=null}">
                        <input type="checkbox" name="country" checked onclick="addcountry();">&nbsp;订单目的地为指定国家<br/><br/>
                    </c:if>
                    <c:if test="${countrys[0]==null}">
                        <input type="checkbox" name="country" onclick="addcountry();">&nbsp;订单目的地为指定国家<br/><br/>
                    </c:if>
                    <c:if test="${amounts[0]!=null}">
                        <input type="checkbox" name="amount" checked onclick="addAmount();">&nbsp;订单来源为指定账号<br/><br/>
                    </c:if>
                    <c:if test="${amounts[0]==null}">
                        <input type="checkbox" name="amount"  onclick="addAmount();">&nbsp;订单来源为指定账号<br/><br/>
                    </c:if>
                    <c:if test="${services[0]!=null||internationalServices[0]!=null}">
                        <input type="checkbox" name="shippingService" checked onclick="addShippingService();">&nbsp;买家选择的物流为指定物流方式<br/><br/>
                    </c:if>
                    <c:if test="${services[0]==null&&internationalServices[0]==null}">
                        <input type="checkbox" name="shippingService"  onclick="addShippingService();">&nbsp;买家选择的物流为指定物流方式<br/><br/>
                    </c:if>
                    <c:if test="${exceptCountrys[0]!=null}">
                        <input type="checkbox" checked name="exceptCountry" onclick="addExceptCountry();">&nbsp;订单目的地为指定国家之外<br/><br/>
                    </c:if>
                    <c:if test="${exceptCountrys[0]==null}">
                        <input type="checkbox" name="exceptCountry" onclick="addExceptCountry();">&nbsp;订单目的地为指定国家之外<br/><br/>
                    </c:if>
                    <c:if test="${order!=null}">
                        <input type="checkbox" checked name="allOrder" onclick="addAllOrder();">&nbsp;所有订单<br/><br/>
                    </c:if>
                    <c:if test="${order==null}">
                        <input type="checkbox" id="allOrder1" name="allOrder" onclick="addAllOrder();">&nbsp;所有订单<br/><br/>
                    </c:if>
                </div>
            </div>
            <div  style="text-align: right;width: 800px;float: left">
                <br/>
                <button type="button" class="net_put" onclick="submitCommit();">保存</button>
                <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
            </div>

        </div>





</body>
</html>
