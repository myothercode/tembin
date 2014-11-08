<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/24
  Time: 18:20
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
        function submitCommit(){
            var redios=$("input[name=redio]:checked");
            var folderId=redios.val();
            var date="";
            <c:forEach items="${orderids}" begin="0" varStatus="status">
                date=date+"&orderid[${status.index}]=${orderids[status.index]}";
            </c:forEach>
            if(folderId){
                var data=null;
                var url=path+"/order/saveFolder.do?folderId="+folderId+date;
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                            var divs= W.document.getElementsByName("newbut");
                            for(var v=5;v<divs.length;v++){
                                var vv="#OrderGetOrdersListTable"+v;
                               /* var table= W.document.getElementById(vv);*/
                                var me= W.document.getElementById("con_menu_"+v);
                                if(me){
                                    me.style.display="block";
                                    W.refreshTable7(vv);
                                    me.style.display="none";
                                }
                            }
                            W.setTab("menu",${table},${table1});
                            W.OrderGetOrders.close();
                            Base.token;
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }else{
                alert("请选择文件夹");
            }
        }
        /**查看消息*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewOrder('"+json.orderid+"');\">查看详情</a>";
            var htm1="|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"modifyOrderStatus('"+json.transactionid+"');\">修改发货状态</a>";
            return htm+htm1;
        }
        function makeOption2(json){
            var imgurl=path+"/img/error.png";
            var htm1="<img src=\""+imgurl+"\"> <font class=\"red_1\"><strong>"+json.orderid+"</strong></font><br>";
            var htm="<img src='"+json.pictrue+"' style='width: 50px;hidden=50px;' />";
            return htm1+htm;
        }
        function makeOption3(json){
            var imgurl=path+"/img/";
            var imgurl1=path+"/img/";
            if(json.message==null||json.message==""){
                imgurl1=imgurl1+"f.png";
            }else{
                imgurl1=imgurl1+"add.png";
            }
            /*var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"ebayurl('"+json.itemUrl+"');\">"+json.title+"</a>";*/
            var htm="<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">"+json.buyeruserid+"  </font> ( "+json.buyeremail+" )</span>" +
                    "<span class=\"newbgspan_3\">"+json.shipmenttrackingnumber+"</span>&nbsp;<span class=\"newbgspan_3\">"+json.shippingcarrierused+"</span>" +
                    "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">"+json.title+"</font><br>("+json.itemid+")</span>" +
                    "<span style=\"width:100%; float:left; color:#8BB51B\">"+json.sku+"</span>" +
                    "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">Color：</font>Black;</span>" +
                    "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">B：</font><img src=\""+imgurl+"f.png\" width=\"14\" height=\"14\"></span>"+
                    "<span style=\"width:100%; float:left\"><font color=\"#5F93D7\">S：" +
                    "</font><img src=\""+imgurl1+"\" width=\"12\" height=\"12\">"+json.message+"</span>" +
                    "<span class=\"newdf\">I need black color</span>" +
                    "<span style=\"width:100%; float:left\">08/25/2014 17:56</span>" +
                    "<span style=\"width:100%; float:left; color:#999\">PayPal payment Status: [Completed], Type: [instant], Amount: [USD21.96] received on UTC 8/28/2014 1:55:59 AM, PayPal transaction ID: 5TW</span>";
            return htm;
        }

        function makeOption4(json){
            var imgurl1=path+"/img/";
            var htm="";
            if(json.status=='Incomplete'){
                htm=htm+"<img src=\""+imgurl1+"money.gif \" onmousemove='showInformation();'>"/*"<img src=\""+imgurl1+"money.gif\">"*/;
                /*"<img onmousemove='showInformation();'>"*/
            }
            if(json.status=='Complete'){
                htm=htm+"<img src=\""+imgurl1+"money.png\" >";
            }
            if(json.shipmenttrackingnumbe!=""&&json.shippingcarrierused!=""){
                htm=htm+"<img src=\""+imgurl1+"car.png\" >"
            }
            return htm;
        }
        function makeOption5(json){
            var htm="<input type='checkbox' id='checkbox' name='checkbox' value='"+json.orderid+"' />";
            return htm;
        }
        function makeOption6(json){
            var htm=json.transactionprice+"USD";
            return htm;
        }
        function closedialog(){
            W.OrderGetOrders.close();
        }
    </script>
</head>
<body>
    <form class="form-horizontal" role="form">
        <table width="100%" border="0">

        <tbody><tr>
            <td width="13%" height="33" align="right"></td>
            <td width="87%" height="33" style="padding-top:8px;">
                <table width="100%" border="0">
                    <tbody><tr>
                        <td width="10%" height="30">选择文件夹</td>
                        <td width="15%" height="30">文件夹名称</td>
                    </tr>
                    <c:forEach items="${folders}" var="folder">
                    <tr>
                        <td height="24" valign="middle"><input type="hidden" name="remarkFlag" value="${folder.configName}"/><input type="radio" name="redio" value="${folder.id}"/></td>
                        <td height="24" valign="middle">${folder.configName}</td>
                        <td height="24" valign="middle"></td>
                    </tr>
                    </c:forEach>
                    </tbody></table>
            </td>
        </tr>
        </tbody></table>

    </form>
<div class="modal-footer">
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>
</body>
</html>
