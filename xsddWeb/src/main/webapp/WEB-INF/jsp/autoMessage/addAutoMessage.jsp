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
            var url=path+'/autoMessage/selectCountrys.do?';
            selectCountry1 = $.dialog({title: '有效国家',
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
            var url=path+'/autoMessage/selectExceptCountrys.do?';
            selectCountry1 = $.dialog({title: '指定国家之外',
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
            var url=path+'/autoMessage/selectItems.do?';
            selectCountry1 = $.dialog({title: '指定商品',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:500,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function selectAmounts(){
            var url=path+'/autoMessage/selectAmounts.do?';
            selectCountry1 = $.dialog({title: '指定账号',
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
            selectCountry1 = $.dialog({title: '指定物流',
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
            selectCountry1 = $.dialog({title: '选择模块',
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
                var htm="<div id='selectCountry'>订单目的地:<a id='selectCountrys' href='#' onclick='selectCountrys();'>指定国家</a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectCountry").remove();
                $("#countryIds").val("");
            }
        }
        function addExceptCountry(){
            var country=$("input[type=checkbox][name=exceptCountry]");
            if(country[0].checked){
                var htm="<div id='selectExceptCountry'>订单目的地:<a id='selectExceptCountrys' href='#' onclick='selectExceptCountrys();'>指定国家之外</a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectExceptCountry").remove();
                $("#exceptCountryIds").val("");
            }
        }
        function addAllOrder(){
            var country=$("input[type=checkbox][name=allOrder]");
            if(country[0].checked){
                var htm="<div id='selectAllOrder'>所有订单:所有的订单<br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectAllOrder").remove();
                $("#allOrder").val("");
            }
        }
        function addItem(){
            var item=$("input[type=checkbox][name=item]");
            if(item[0].checked){
                var htm="<div id='selectItem'>订单商品:<a id='selectItems' href='#' onclick='selectItems();'>指定商品</a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectItem").remove();
                $("#orderItems").val("");
            }
        }
        function addAmount(){
            var amount=$("input[type=checkbox][name=amount]");
            if(amount[0].checked){
                var htm="<div id='selectAmount'>订单来源:<a id='selectAmounts' href='#' onclick='selectAmounts();'>指定账号</a><br/></div>"
                $("#addMarket").append($(htm));
            }else{
                $("#selectAmount").remove();
                $("#amounts").val("");
            }
        }
        function addShippingService(){
            var amount=$("input[type=checkbox][name=shippingService]");
            if(amount[0].checked){
                var htm="<div id='selectShippingService'>买家选择的物流:<a id='selectShippingServices' href='#' onclick='selectShippingServices();'>指定物流方式</a><br/></div>"
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
            if(checkboxs.length==0){
                alert("选择规则至少选择一个");
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
                        W.refreshTable();
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
        <div>
            <br/><br/><br/>
        <div  style="border: 1px #acd0f0 solid;width: 930px;height:150px;margin-left:5px;background-color:whitesmoke">
            <div class="control-group" style="padding: 30px;">
                <!-- Text input-->
                <label class="control-label" style="width: 100px;" >消息模块&nbsp;</label>
                <div class="controls">
                    <input type="hidden" id="messageId" name="messageId"/>
                    <input type="text" placeholder="" onclick="selectMessageTemplate();" class="input-xlarge validate[required]" value="${autoMessage.subject}" name="subject" id="subject">&nbsp;<a href="#" onclick="selectMessageTemplate();">选择</a>
                    <p class="help-block"></p>
                </div>
                <label class="control-label" style="width: 100px;" >在&nbsp;</label>
                <div class="controls">
                    <select class="input-xlarge" name="type">
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
                <label class="control-label" style="width: 100px;" >发送方式&nbsp;</label>
                <!-- Multiple Checkboxes -->
                <div class="controls">
                    <label class="checkbox inline">
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
                    </label>
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
                    <c:if test="${items!=null}">
                        <div id='selectItem'>订单商品:<a id='selectItems' href='#' onclick='selectItems();'>
                            <c:forEach items="${items}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                    ${item.value}
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                    ${item.value},
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${countrys!=null}">
                        <div id='selectCountry'>订单目的地:<a id='selectCountrys' href='#' onclick='selectCountrys();'>
                            <c:forEach items="${countrys}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                    ${item.value}
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                    ${item.value},
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${amounts!=null}">
                        <div id='selectAmount'>订单来源:<a id='selectAmounts' href='#' onclick='selectAmounts();'>
                            <c:forEach items="${amounts}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                    ${item.value}
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                    ${item.value},
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${services!=null||internationalServices!=null}">
                        <div id='selectShippingService'>买家选择的物流:<a id='selectShippingServices' href='#' onclick='selectShippingServices();'>
                            国内运输方式:
                            <c:forEach items="${services}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                    ${item.value}
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                    ${item.value},
                                </c:if>
                            </c:forEach>
                            ;国际运输方式:
                            <c:forEach items="${internationalServices}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                    ${item.value}
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                    ${item.value},
                                </c:if>
                            </c:forEach>
                        </a><br/></div>
                    </c:if>
                    <c:if test="${order!=null}">
                        <div id='selectAllOrder'>所有订单:所有的订单<br/></div>
                    </c:if>
                    <c:if test="${exceptCountrys!=null}">
                        <div id='selectExceptCountry'>订单目的地:<a id='selectExceptCountrys' href='#' onclick='selectExceptCountrys();'>
                            <c:forEach items="${exceptCountrys}" var="item" varStatus="status">
                                <c:if test="${status.index==(status.count-1)}">
                                    ${item.value}之外
                                </c:if>
                                <c:if test="${status.index!=(status.count-1)}">
                                    ${item.value},
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
                    <c:if test="${items!=null}">
                        <input type="checkbox" name="item" checked onclick="addItem();">&nbsp;订单商品包含指定商品<br/><br/>
                    </c:if>
                    <c:if test="${items==null}">
                        <input type="checkbox" name="item"  onclick="addItem();">&nbsp;订单商品包含指定商品<br/><br/>
                    </c:if>
                    <c:if test="${countrys!=null}">
                        <input type="checkbox" name="country" checked onclick="addcountry();">&nbsp;订单目的地为指定国家<br/><br/>
                    </c:if>
                    <c:if test="${countrys==null}">
                        <input type="checkbox" name="country" onclick="addcountry();">&nbsp;订单目的地为指定国家<br/><br/>
                    </c:if>
                    <c:if test="${amounts!=null}">
                        <input type="checkbox" name="amount" checked onclick="addAmount();">&nbsp;订单来源为指定账号<br/><br/>
                    </c:if>
                    <c:if test="${amounts==null}">
                        <input type="checkbox" name="amount"  onclick="addAmount();">&nbsp;订单来源为指定账号<br/><br/>
                    </c:if>
                    <c:if test="${services!=null||internationalServices!=null}">
                        <input type="checkbox" name="shippingService" checked onclick="addShippingService();">&nbsp;买家选择的物流为指定物流方式<br/><br/>
                    </c:if>
                    <c:if test="${services==null&&internationalServices==null}">
                        <input type="checkbox" name="shippingService"  onclick="addShippingService();">&nbsp;买家选择的物流为指定物流方式<br/><br/>
                    </c:if>
                    <c:if test="${exceptCountrys!=null}">
                        <input type="checkbox" checked name="exceptCountry" onclick="addExceptCountry();">&nbsp;订单目的地为指定国家之外<br/><br/>
                    </c:if>
                    <c:if test="${exceptCountrys==null}">
                        <input type="checkbox" name="exceptCountry" onclick="addExceptCountry();">&nbsp;订单目的地为指定国家之外<br/><br/>
                    </c:if>
                    <c:if test="${order!=null}">
                        <input type="checkbox" checked name="allOrder" onclick="addAllOrder();">&nbsp;所有订单<br/><br/>
                    </c:if>
                    <c:if test="${order==null}">
                        <input type="checkbox" name="allOrder" onclick="addAllOrder();">&nbsp;所有订单<br/><br/>
                    </c:if>
                </div>
            </div>
            <div  style="text-align: right;width: 800px;float: left">
                <br/>
                <button type="button" class="btn btn-primary" onclick="submitCommit();">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>
            </div>

        </div>





</body>
</html>
