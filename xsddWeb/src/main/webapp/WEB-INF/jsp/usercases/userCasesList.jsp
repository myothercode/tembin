<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/28
  Time: 17:48
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
        var userCases;
        function getBindParm(){
            var url=path+"/userCases/getUserCases.do";
            userCases=$.dialog({title: '同步订单',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        $(document).ready(function(){
            $("#UserCasesListTable").initTable({
                url:path + "/userCases/ajax/loadUserCasesList.do?",
                columnData:[
                    {title:"Item",name:"casetype",width:"15%",align:"left",format:makeOption2,click:rowClickMethod},
                    {title:"Problem",name:"itemid",width:"12%",align:"left",format:makeOption3,click:rowClickMethod},
                    {title:"Amount",name:"caseamount",width:"8%",align:"left",click:rowClickMethod},
                    {title:"Opended on",name:"lastmodifieddate",width:"12%",align:"left",click:rowClickMethod},
                    {title:"Trading Partner",name:"buyerid",width:"8%",align:"left",click:rowClickMethod},
                    {title:"Status",name:"status",width:"8%",align:"left",click:rowClickMethod},
                    {title:"Open requests/cases",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function rowClickMethod(json){
            handleDispute(json.transactionid,json.sellerid);
        }
        function refreshTable(){
            $("#UserCasesListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function refreshTable1(account,type,status,days,name,content,starttime,endtime){
            $("#UserCasesListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"account":account,"type":type,"status":status,"days":days,"name":name,"content":content,"starttime1":starttime,"endtime1":endtime});
        }
        /**组装操作选项*/
        function makeOption1(json){
            /*var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"synDetails('"+json.id+"');\">同步详情</a>";
            var htm1="|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewDetails('"+json.transactionid+"');\">查看详情</a>";
            var htm2="";
            var flag=json.casetype;
            var flag1=flag.substring(0,3);
            if(flag1=="EBP"){
                htm2="|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"handleDispute('"+json.transactionid+"');\">处理纠纷</a>";
            }
            var h=htm+htm1+htm2;
            return h;*/
           /* var htm="<div class=\"ui-select\" style=\"width:106px\" >" +
                    "<select onchange=\"selectOperation('"+json.transactionid+"','"+json.id+"','"+json.sellerid+"',this); \" name=\"ui-select\" style=\"margin-left:-3px;\">" +
                    "<option value=\"0\"><a href=\"javascript:#\">--请选择--</a></option>" +
                  *//*  "<option value=\"1\"><a href=\"javascript:#\">同步详情</a></option>" +
                    "<option value=\"2\"><a href=\"javascript:#\">查看详情</a></option>" +*//*
                    "<option value=\"3\"><a href=\"javascript:#\">处理纠纷</a></option>" +
                    "</select>" +
                    "</div>";
            return htm;*/
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=selectOperation('"+json.transactionid+"','"+json.id+"','"+json.sellerid+"',this); value='3' doaction=\"readed\" >处理纠纷</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function selectOperation(transactionid,id,sellerid,obj){
            var value=$(obj).val();
            if(value=="1"){
                synDetails(id);
            }
            if(value=="2"){
                viewDetails(transactionid,sellerid);
            }
            if(value=="3"){
                handleDispute(transactionid,sellerid);
            }
        }
        function makeOption2(json){
            var detail=json.itemtitle;
            if(detail.length>25){
                detail=detail.substring(0,26)+"...<br/>";
            }
            var htm="<a target=\"_blank\" href=\"http://www.sandbox.ebay.com/itm/\""+json.itemid+">"+detail+"("+json.itemid+")</a>";
            return htm;
        }
        function makeOption3(json){
            var type=json.casetype;
            var type1=type.substring(0,3);
            if(type1=="EBP"){
                return json.ebpReason;
            }else{
                return json.disputeReason;
            }

        }
        function synDetails(id){
            var url=path+"/userCases/synDetails.do?id="+id;
            userCases=$.dialog({title: '同步纠纷详情',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function viewDetails(id,sellerid){
            var url=path+"/userCases/viewDetails.do?transactionid="+id+"&sellerid="+sellerid;
            userCases=$.dialog({title: '查看纠纷详情',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function handleDispute(id,sellerid){
            /*var url=path+"/userCases/handleDispute.do?transactionid="+id;
            userCases=$.dialog({title: '处理纠纷',
                content: 'url:'+url,
                icon: 'succeed',
                width:600,
                height:350
            });*/
            var url=path+"/userCases/responseDispute.do?transactionid="+id+"&sellerid="+sellerid;
            CaseDetails=$.dialog({title: '响应纠纷',
                content: 'url:'+url,
                icon: 'succeed',
                width:1100,
                height:700,
                lock:true
            });
        }
        function submitCommit(){
            var account=$("#caseAccount").val();
            var type=$("#caseType").val();
            var status=$("#caseStatus").val();
            var days=$("#caseDays").val();
            var name=$("#selectName").val();
            var content=$("#qeuryContent").val();
            var starttime=$("#starttime").val();
            var endtime=$("#endtime").val();
            $("#UserCasesListTable").initTable({
                url:path + "/userCases/ajax/loadUserCasesList.do?type="+type,
                columnData:[
                    {title:"Item",name:"casetype",width:"15%",align:"left",format:makeOption2,click:rowClickMethod},
                    {title:"Problem",name:"itemid",width:"12%",align:"left",format:makeOption3,click:rowClickMethod},
                    {title:"Amount",name:"caseamount",width:"8%",align:"left",click:rowClickMethod},
                    {title:"Opended on",name:"lastmodifieddate",width:"12%",align:"left",click:rowClickMethod},
                    {title:"Trading Partner",name:"buyerid",width:"8%",align:"left",click:rowClickMethod},
                    {title:"Status",name:"status",width:"8%",align:"left",click:rowClickMethod},
                    {title:"Open requests/cases",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:true,
                showIndex:false,
                rowClickMethod:rowClickMethod
            });
            refreshTable1(account,type,status,days,name,content,starttime,endtime)
            alert("查询完成");
        }
        function selectAccount(name,count){
            var accounts=$("span[scop=account]");
            for(var i=0;i<accounts.length;i++){
                if(count==i){
                    $(accounts[i]).attr("class","newusa_ici");
                    $("#caseAccount").val(name);
                }else{
                    $(accounts[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function selectType(name,count){
            var types=$("span[scop=type]");
            for(var i=0;i<types.length;i++){
                if(count==i){
                    $(types[i]).attr("class","newusa_ici");
                    $("#caseType").val(name);
                }else{
                    $(types[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function selectStatus(name,count){
            var status=$("span[scop=status]");
            for(var i=0;i<status.length;i++){
                if(count==i){
                    $(status[i]).attr("class","newusa_ici");
                    $("#caseStatus").val(name);
                }else{
                    $(status[i]).attr("class","newusa_ici_1");
                }
            }
        }
        function selectDays(name,count){
            var days=$("span[scop=days]");
            for(var i=0;i<days.length;i++){
                if(count==i){
                    $(days[i]).attr("class","newusa_ici");
                    $("#caseDays").val(name);
                    $("#starttime").val("");
                    $("#endtime").val("");
                }else{
                    $(days[i]).attr("class","newusa_ici_1");
                }
            }
        }
    </script>
</head>
<body>
<%--纠纷类型:<select id="type" name="type">
    <option value="all">all</option>
    <option value="EBP">EBP</option>
    <option value="dispute">dispute</option>
    <option value="CANCEL">CANCEL</option>
</select>
<input type="button" value="查询" onclick="submitCommit();"/>
<div id="UserCasesListTable"></div>
<input type="button" value="同步纠纷" onclick="getBindParm();">--%>
<div class="new_all">
<div class="here">当前位置：首页 &gt; 客服管理 &gt; <b>CASE管理</b></div>
<div class="a_bal"></div>
<div class="new">
<h2>eBay官方调整E邮宝接口，截至九月底将停止使用原E邮宝，需要您重新授权E邮宝</h2>
<div class="Contentbox">
    <script type="text/javascript">
        function menuFix() {
            var sfEls = document.getElementById("newtipi").getElementsByTagName("li");
            for (var i = 0; i < sfEls.length; i++) {
                sfEls[i].onmouseover = function() {
                    this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                }
                sfEls[i].onMouseDown = function() {
                    this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                }
                sfEls[i].onMouseUp = function() {
                    this.className += (this.className.length > 0 ? " " : "") + "sfhover";
                }
                sfEls[i].onmouseout = function() {
                    this.className = this.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
                }
            }
        }
        window.onload = menuFix;
        //--><!]]>
    </script>
    <!--综合开始 -->
    <form id="UserCaseForm">
        <input id="caseAccount" type="hidden" name="account">
        <input id="caseType" type="hidden" name="type">
        <input id="caseStatus" type="hidden" name="status">
        <input id="caseDays" type="hidden" name="days">
    </form>
    <div class="new_usa">

        <li class="new_usa_list"><span class="newusa_i">选择账号：</span><a href="#"><span scop="account" onclick="selectAccount(null,0);" class="newusa_ici_1">&nbsp;全部&nbsp;&nbsp;</span></a><a href="#"><span scop="account" onclick="selectAccount('ebay',1);" class="newusa_ici_1">eBay账号&nbsp</span></a><a href="#"><span scop="account" onclick="selectAccount(null,2);" class="newusa_ici_1">亚马逊账号</span></a><a href="#"><span scop="account" onclick="selectAccount(null,3);" class="newusa_ici_1">亚马逊账号</span></a><a href="#"><span scop="account" onclick="selectAccount(null,4);" class="newusa_ici_1">亚马逊账号</span></a><a href="#"><span scop="account" onclick="selectAccount(null,5);" class="newusa_ici_1">亚马逊账号</span></a></li>
        <li class="new_usa_list"><span class="newusa_i">纠纷类型：</span><a href="#"><span scop="type" onclick="selectType(null,0);" class="newusa_ici_1">&nbsp;全部&nbsp;&nbsp;</span></a><a href="#"><span scop="type" onclick="selectType('BuyerHasNotPaid',1);" class="newusa_ici_1">未付款&nbsp;</span></a><a href="#"><span scop="type" onclick="selectType('ItemNotReceived',2);" class="newusa_ici_1">未收到物品</span></a><a href="#"><span scop="type" onclick="selectType('SignificantlyNotAsDescribed',3);" class="newusa_ici_1">描述不符&nbsp;</span></a></li>
        <li class="new_usa_list"><span class="newusa_i">处理状态：</span><a href="#"><span scop="status" onclick="selectStatus(null,0);" class="newusa_ici_1">&nbsp;全部&nbsp;&nbsp;</span></a><a href="#"><span scop="status" onclick="selectStatus('1',1);" class="newusa_ici_1">已处理&nbsp;</span></a><a href="#"><span scop="status" onclick="selectStatus('0',2);" class="newusa_ici_1">未处理&nbsp;</span></a></li>
        <div class="newsearch">
            <span class="newusa_i">搜索内容：</span><a href="#"><span scop="days" onclick="selectDays(null,0);" class="newusa_ici_1">&nbsp;全部&nbsp;&nbsp;</span></a><a href="#"><span scop="days" onclick="selectDays('1',1);" class="newusa_ici_1">&nbsp;今天&nbsp;&nbsp;</span></a><a href="#"><span scop="days" onclick="selectDays('2',2);" class="newusa_ici_1">&nbsp;昨天&nbsp;&nbsp;</span></a><a href="#"><span scop="days" onclick="selectDays('7',3);" class="newusa_ici_1">7天以内&nbsp;</span></a><a href="#"><span scop="days" onclick="selectDays('30',4);" class="newusa_ici_1">30天以内&nbsp;</span></a>
            <span style="float: left;color: #5F93D7;">从</span><input style="float: left;color: #5F93D7;width: 90px;height: 26px;" id="starttime"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
            <span style="float: left;color: #5F93D7;">到</span><input style="float: left;color: #5F93D7;width: 90px;height: 26px;margin-right: 20px;" id="endtime"  type="text" onfocus="WdatePicker({isShowWeek:true,dateFmt:'yyyy-MM-dd'})"/>
<span id="sleBG">
<span id="sleHid">
<select id="selectName" name="type" class="select">
    <option selected="selected" value="">选择类型</option>
    <option value="1">EBP纠纷</option>
    <option value="2">一般纠纷</option>
    <option value="3">买家</option>
    <option value="4">卖家</option>
</select>
</span>
</span>
            <div class="vsearch">
                <input id="qeuryContent" name="" type="text" class="key_1"><input onclick="submitCommit();" name="newbut" type="button" class="key_2"></div>
        </div>
        <div class="newds">
            <div class="newsj_left">
            </div><div>
            <div id="newtipi">
                <%--<li class=""><a href="#">显示20条</a>
                    <ul>
                        <li><a href="#">自定义显示</a></li>
                        <li><a href="#">自定义显示</a></li>
                        <li><a href="#">自定义显示</a></li>
                    </ul>
                </li>--%>
            </div></div>
            <div class="tbbay"><a data-toggle="modal" onclick="getBindParm();">同步纠纷</a></div>
            <%--<div class="tbbay"><a data-toggle="modal" href="#userall" class="">展开管理</a></div>--%>
        </div>
    </div>
    <!--综合结束 -->
    <div style="width: 100%;float: left;height: 5px"></div>
    <div id="UserCasesListTable"></div>
    <%--<table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
        <tbody><tr>
            <td height="30" bgcolor="#F7F7F7"><strong>Item</strong></td>
            <td height="30" bgcolor="#F7F7F7"><strong>Problem</strong></td>
            <td width="8%" align="center" bgcolor="#F7F7F7"><strong>Amount</strong></td>
            <td width="8%" align="center" bgcolor="#F7F7F7"><strong>Opended on</strong></td>
            <td width="15%" align="center" bgcolor="#F7F7F7"><strong>Trading Partner</strong></td>
            <td width="12%" align="center" bgcolor="#F7F7F7"><strong>Status</strong></td>
            <td width="15%" align="center" bgcolor="#F7F7F7"><strong>Open requests/cases</strong></td>
        </tr>
        <tr>
            <td valign="top">

                <font color="#5E93D5">7 Pcs 60cm Linght Bron...</font>
                <br>
                ( 2135135421 )
            </td>
            <td align="center">You want You want You want You want</td>
            <td align="center">$14.09</td>
            <td align="center">Aug 07,2014</td>
            <td align="center">rfasdgasd</td>
            <td align="center">Buyer didn’t reads</td>
            <td align="center"><a data-toggle="modal" href="#userall" class=""><span class="newusa_ici_2" style="margin-left:30px;">Take action</span></a></td>
        </tr>
        </tbody></table>--%>
    <div class="page_newlist">
        <%--<div>
            <div id="newtipi">
                <li><a href="#">显示20条</a>
                    <ul>
                        <li><a href="#">自定义显示</a></li>
                        <li><a href="#">自定义显示</a></li>
                        <li><a href="#">自定义显示</a></li>
                    </ul>
                </li>
            </div></div> 共 <span style="color:#F00">3000</span> 条记录 <span style="color:#F00">300</span> 页
    </div>--%>
    <%--<div class="maage_page">
        <li>&lt;</li>
        <li class="page_cl">1</li>
        <li>2</li>
        <li>3</li>
        <li>4</li>
        <dt>&gt;</dt>
    </div>--%>
</div>

<!-- userall -->
<div class="modal fade" id="userall" tabindex="-1" role="dialog" aria-labelledby="userallLabel" aria-hidden="true">
<div class="modal-dialog" style="width:1200px;">
<div class="modal-content">

<div class="modal-body">
<script src="../../js/jquery.min.js"></script>
<script>
    $(function(){
        var _listBox=$('.jq_new');
        var _navItem=$('.jq_new>h4');
        var _boxItem=null, _icoItem=null, _parents=null, _index=null;
        _listBox.each(function(i){
            $(this).find('div.box').eq(0).show();
            $(this).find('h4>span').eq(0).text('-');
        });

        _navItem.each(function(i){
            $(this).click(function(){
                _parents=$(this).parents('.listbox_new');
                _navItem=_parents.find('h4');
                _icoItem=_parents.find('span');
                _boxItem=_parents.find('div.box');
                _index=_navItem.index(this);
                if(_boxItem.eq(_index).is(':visible')){
                    _boxItem.eq(_index).hide().end().not(':eq('+_index+')').first().show();
                    _icoItem.eq(_index).text('+').end().not(':eq('+_index+')').first().text('-');
                }else{
                    _boxItem.eq(_index).show().end().not(':eq('+_index+')').hide();
                    _icoItem.eq(_index).text('-').end().not(':eq('+_index+')').text('+');
                }
            });
        });
    });
</script>
<div id="demo">
<table width="100%" border="0">
<tbody><tr>
    <td width="772">            <div class="modal-header">
        <h4 class="modal-title"><span>Please respond to your buyer by sep 07,2014.</span>Choose one of the following</h4>
    </div></td>
    <td width="9" rowspan="3" valign="top">&nbsp;</td>
    <td rowspan="3" valign="top" style="margin-left:20px; padding-left:15px; padding-top:20px; padding-right:20px; line-height:25px;background:#F4F4F4"><strong>订单号</strong><br>
        CO-9798-R1(已配货)<br>
        <strong>物流跟踪号</strong><br>
        RI123885807CN<br>
        <strong>发货时间：</strong><br>
        2017-07-22<br>
        <strong>付款时间：</strong><br>
        2017-07-22<br>
        <strong>运输方式：</strong><br>
        海运
        <span class="voknet"></span>
        <table width="100%" border="0">
            <tbody><tr>
                <td width="56"><strong>商品2</strong></td>
                <td style="color:#63B300">CNDL</td>
            </tr>
            <tr>
                <td><img src="../../img/no_pic_1.png" width="46" height="46"></td>
                <td style=" color:#5F93D7">标题标题标题标题标题标题标题标题标题标题标题<br>
                    标题标题标题标题标题...</td>
            </tr>
            </tbody></table>
        <span class="voknet"></span>

        <table width="100%" border="0">
            <tbody><tr>
                <td width="56"><strong>商品2</strong></td>
                <td style="color:#63B300">CNDL</td>
            </tr>
            <tr>
                <td><img src="../../img/no_pic_1.png" width="46" height="46"></td>
                <td style=" color:#5F93D7">标题标题标题标题标题标题标题标题标题标题标题<br>
                    标题标题标题标题标题...</td>
            </tr>
            </tbody></table>
        <div style="font-family:Arial, Helvetica, sans-serif; font-size:12px;">
            <span class="voknet"></span>
            <strong><font color="#5F93D7">The buyer opended a case:</font><font color="#F35F23">Item not receiver</font></strong><br>
            Aug 30,,2014 at 5:42 AM<br>
            <strong style="color:#000000">The case details:</strong><br>
            The buyer has not receiver the item yet.<br>
            The buyer paid on May 26,2014.<br>
            The buyer can be contacted at “+7894312424”in order to <br>
            resolve the problem. <br>
            <strong style="color:#000000">Additional information:</strong><br>
            The buyer paid on May 26,2014. <br>
            <strong style="color:#000000">The case details:</strong><br>
            Item tracking information<br></div></td>
</tr>
<tr>
    <td width="772"><div class="listbox_new jq_new">
        <h4><span>-</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>Verify tracking information ( Buyer’s preference )</strong></td></tr></tbody></table>
        </h4>
        <div class="box" style="display: block;">
            <table width="100%" border="0">
                <tbody><tr>
                    <td height="32" align="left">Please verity that this tracking information is correct and click Submit to continue.If it is incorrect,please re-enter your </td>
                </tr>
                <tr>
                    <td height="32" align="left">What is your tracking number?</td>
                </tr>
                <tr>
                    <td height="32" align="left"><input type="text" class="form-controlsd" value="RD2751354132CN"></td>
                </tr>
                <tr>
                    <td height="32" align="left">Which carrier did you use?</td>
                </tr>
                <tr>
                    <td height="32" align="left"><input type="text" class="form-controlsd" value="China Post"></td>
                </tr>
                <tr>
                    <td height="32" align="left">[ <font color="#5F93D7">View your tracking information</font> ]
                        <span class="voknet"></span></td>
                </tr>
                <tr>
                    <td height="32" align="left"><table width="100%" border="0">
                        <tbody><tr>
                            <td><strong>Tracking #:</strong></td>
                            <td><strong>Carrier:</strong></td>
                            <td width="118"><strong>Status:</strong></td>
                            <td width="118">&nbsp;</td>
                            <td width="118">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>CO-9798-R1</td>
                            <td>China Post</td>
                            <td width="118"><img src="../../img/co_1.jpg"></td>
                            <td width="118"><img src="../../img/co_2.jpg"></td>
                            <td width="118"><img src="../../img/co_3.jpg"></td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td width="116" style="color:#8DB81F">ACCEPTED</td>
                            <td width="116" style="color:C3C3C3">IN TRANST</td>
                            <td width="116" style="color:C3C3C3">Delivered</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3" style="color:#8DB81F">Departure from outward office of exchange</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td colspan="3" style="color:#BFBFBF">2014-05-29,09:59:00,Guangdong</td>
                        </tr>
                        </tbody></table></td>
                </tr>
                <tr>
                    <td height="32" align="left">
                        <span class="voknet"></span>
                        <label for="textarea"></label>
                        <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco">Additional comments:(optional)</textarea>
                    </td>
                </tr>
                <tr>
                    <td height="22" align="right" style="color:#C6C6C6">1000 chartacters left. No HTML.</td>
                </tr>
                </tbody></table>

        </div>
        <h4><span>+</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>I shipped the item without tracking information</strong></td></tr></tbody></table>
        </h4>
        <div class="box"><table width="100%" border="0">
            <tbody><tr>
                <td height="32" align="left"><strong><img src="../../img/new_yes.png" width="22" height="22"> Tracking number entered successfully</strong></td>
            </tr>
            <tr>
                <td height="32" align="left">Please check to see if this tracking information is correct.</td>
            </tr>
            <tr>
                <td height="32" align="left"><textarea name="textarea2" id="textarea2" cols="100%" rows="5" class="newco">Additional comments:(optional)</textarea></td>
            </tr>
            <tr>
                <td height="32" align="left" style="color:#F00">Or octer the correct tracking delivery conmation number</td>
            </tr>
            <tr>
                <td height="32" align="left">Additonal Commects</td>
            </tr>
            <tr>
                <td height="32" align="left">
                    <label for="textarea"></label>
                    <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco">Additional comments:(optional)</textarea>
                </td>
            </tr>
            <tr>
                <td height="22" align="left" style="color:#C6C6C6">2000 No HTML.</td>
            </tr>
            </tbody></table></div>
        <h4><span>+</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>Issue a full refund</strong></td></tr></tbody></table>
        </h4>
        <div class="box"><table width="100%" border="0">
            <tbody><tr>
                <td height="32" align="left">
                    <input name="" type="checkbox" value=""> Issue a full refund
                    <input name="" type="checkbox" value="">
                    Issue a partial refund
                </td>
            </tr>
            <tr>
                <td height="32" align="left"><input type="text" class="form-controlsd" value="RD2751354132CN"> 填写退款金额</td>
            </tr>
            <tr>
                <td height="32" align="left">You agree to issue your customer a $17.56 refund , which is the purchase price plus original shipping. After the buyer receives the buyer receives the refund, the case will be closed</td>
            </tr>
            <tr>
                <td height="32" align="left" style="color:#5F93D7">The buyer must receive your refund by sep 05,2014.</td>
            </tr>
            <tr>
                <td height="32" align="left">
                    <span class="voknet"></span>
                    <label for="textarea"></label>
                    <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco">Additional comments:(optional)</textarea>
                </td>
            </tr>
            <tr>
                <td height="22" align="right" style="color:#C6C6C6">2000 No HTML.</td>
            </tr>
            </tbody></table></div>
        <h4><span>+</span>
            <table width="90%" border="0"><tbody><tr>
                <td><strong>send a message</strong></td></tr></tbody></table>
        </h4>
        <div class="box"><table width="100%" border="0">
            <tbody><tr>
                <td height="32" align="left" style="color:#5F93D7">Please provide detailed information in your response. All responses may be Viewed by the buyer and eBay Customer Support</td>
            </tr>
            <tr>
                <td height="32" align="left">
                    <label for="textarea"></label>
                    <textarea name="textarea" id="textarea" cols="100%" rows="5" class="newco">Additional comments:(optional)</textarea>
                </td>
            </tr>
            <tr>
                <td height="22" align="right" style="color:#C6C6C6">2000 No HTML.</td>
            </tr>
            </tbody></table></div>
    </div>


    </td>
</tr>
<tr>
    <td width="772"><div class="modal-footer">
        <button type="button" class="btn btn-primary">保存</button>
        <button type="button" class="btn btn-newco">回复</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    </div> </td>
</tr>
</tbody></table>

</div>


<script src="../../js/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/theme.js"></script>

</div></div></div></div></div></div>
</body>
</html>
