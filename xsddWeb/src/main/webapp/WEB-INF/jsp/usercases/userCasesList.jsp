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
                    {title:"纠纷id",name:"caseid",width:"8%",align:"left"},
                    {title:"纠纷类型",name:"casetype",width:"8%",align:"left"},
                    {title:"Itemid",name:"itemid",width:"8%",align:"left"},
                    {title:"seller",name:"sellerid",width:"8%",align:"left"},
                    {title:"buyer",name:"buyerid",width:"8%",align:"left"},
                    {title:"ebay商品详情",name:"itemUrl",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
        });
        function refreshTable(){
            $("#UserCasesListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"synDetails('"+json.id+"');\">同步详情</a>";
            var htm1="|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"viewDetails('"+json.transactionid+"');\">查看详情</a>";
            var htm2="";
            var flag=json.casetype;
            var flag1=flag.substring(0,3);
            if(flag1=="EBP"){
                htm2="|<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"handleDispute('"+json.transactionid+"');\">处理纠纷</a>";
            }
            var h=htm+htm1+htm2;
            return h;
        }
        function makeOption2(json){
            var htm="<a target=\"_blank\" href=\"http://www.sandbox.ebay.com/itm/\""+json.itemid+">"+json.itemtitle+"</a>";
            return htm;
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
        function viewDetails(id){
            var url=path+"/userCases/viewDetails.do?transactionid="+id;
            userCases=$.dialog({title: '查看纠纷详情',
                content: 'url:'+url,
                icon: 'succeed',
                width:1025,
                lock:true
            });
        }
        function handleDispute(id){
            var url=path+"/userCases/handleDispute.do?transactionid="+id;
            userCases=$.dialog({title: '处理纠纷',
                content: 'url:'+url,
                icon: 'succeed',
                width:600,
                height:350
            });
        }
        function submitCommit(){
            var type=$("#type").val();
            $("#UserCasesListTable").initTable({
                url:path + "/userCases/ajax/loadUserCasesList.do?type="+type,
                columnData:[
                    {title:"纠纷id",name:"caseid",width:"8%",align:"left"},
                    {title:"纠纷类型",name:"casetype",width:"8%",align:"left"},
                    {title:"Itemid",name:"itemid",width:"8%",align:"left"},
                    {title:"seller",name:"sellerid",width:"8%",align:"left"},
                    {title:"buyer",name:"buyerid",width:"8%",align:"left"},
                    {title:"ebay商品详情",name:"itemUrl",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:true
            });
            refreshTable();
            alert("查询完成");
        }
    </script>
</head>
<body>
纠纷类型:<select id="type" name="type">
    <option value="all">all</option>
    <option value="EBP">EBP</option>
    <option value="dispute">dispute</option>
    <option value="CANCEL">CANCEL</option>
</select>
<input type="button" value="查询" onclick="submitCommit();"/>
<div id="UserCasesListTable"></div>
<input type="button" value="同步纠纷" onclick="getBindParm();">
</body>
</html>
