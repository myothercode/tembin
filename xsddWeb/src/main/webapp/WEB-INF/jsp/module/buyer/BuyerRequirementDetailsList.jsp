<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script>
        var buyerRequire;
        function addBuyer(){
            buyerRequire=openMyDialog({title: '新增买家要求',
                content: 'url:/xsddWeb/addBuyer.do',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        function editBuyer(id){
            buyerRequire=openMyDialog({title: '编辑买家要求',
                content: 'url:/xsddWeb/editBuyer.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function editBuyerselect(id){
            buyerRequire=openMyDialog({title: '查看买家要求',
                content: 'url:/xsddWeb/editBuyer.do?id='+id+'&type=01',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        function delBuyer(id){
            var url=path+"/ajax/delBuyer.do?id="+id;
            $().invoke(url,{},
                    [function(m,r){
                        alert(r);
                        Base.token();
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        $(document).ready(function(){
            $("#buyerRequireTable").initTable({
                url:path + "/ajax/loadBuyerRequirementDetailsList.do",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"买家要求",name:"option1",width:"8%",align:"left",format:makeOption3},
                    {title:"状态",name:"option1",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#buyerRequireTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm='<div class="ui-select" style="margin-top:9px; width:10px">'
            htm+='<select onchange="selectDo(this)">'
            htm+='<option value="">请选择</option>'
            htm+='<option value="'+json.id+'">查看</option>'
            if(json.checkFlag=="0") {
                htm += '<option value="' + json.id + '">禁用</option>'
            }else{
                htm += '<option value="' + json.id + '">启用</option>'
            }
            htm+='<option value="'+json.id+'">编辑</option>'

            htm+='</select>'
            htm+='</div>';
            return htm;
        }
        function selectDo(obj){
            if($(obj).find(":selected").text()=="编辑"){
                editBuyer($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="禁用"||$(obj).find(":selected").text()=="启用"){
                delBuyer($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="查看"){
                editBuyerselect($(obj).find(":selected").val());
            }
            $(obj).val("");
        }
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
        /**组装操作选项*/
        function makeOption3(json){

            var htm=''
            if(json.buyerFlag!=null&&json.buyerFlag=="1"){
                htm+='允许所有买家购买我的物品</br>';
            }else{
                if(json.linkedpaypalaccount!=null&&json.linkedpaypalaccount=="0"){
                    htm+='没有PayPal账户</br>';
                }
                if(json.shiptoregistrationcountry!=null&&json.shiptoregistrationcountry=="0"){
                    htm+='主要运送地址在我的运送范围之外</br>';
                }
                if(json.policyCount!=null&&json.policyCount!=""){
                    var period = json.unpaidPeriod;
                    htm+='曾收到'+json.unpaidCount+'个弃标个案，在过去'+period.substr(period.indexOf("_")+1,period.length)+'天</br>';
                }
                if(json.policyCount!=null&&json.policyCount!=""){
                    var period = json.policyPeriod;
                    htm+='曾收到'+json.policyCount+'个违反政策检举，在过去'+period.substr(period.indexOf("_")+1,period.length)+'天</br>';
                }

                if(json.minimumfeedbackscore!=null&&json.minimumfeedbackscore!=""){
                    htm+='信用指标等于或低于'+json.minimumfeedbackscore+"</br>";
                }
                if(json.maximumitemcount!=null&&json.maximumitemcount!=""){
                    htm+='在过去10天内曾出价或购买我的物品，已达到我所设定的限制'+json.maximumitemcount+'</br>';
                }
                if(json.feedbackscore!=null&&json.feedbackscore!=""){
                    htm+='这项限制只适用于买家信用指数等于或低于'+json.feedbackscore+'</br>';
                }
            }
            return htm;
        }
    </script>
</head>
<body>

<div class="newds">
    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addBuyer()">新增</a></div>
</div>
<div id="buyerRequireTable"></div>
<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>站点</td>
            <td>所有买家购买</td>
        </tr>
        <c:forEach items="${li}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.siteName}
                </td>
                <td>${li.buyerFlag}</td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
