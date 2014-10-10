<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/29
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var returnPolicy;
        function addReturnpolicy(){
            returnPolicy=$.dialog({title: '新增退款选项',
                content: 'url:/xsddWeb/addReturnpolicy.do',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function editReturnpolicy(id){
            returnPolicy=$.dialog({title: '编辑退款选项',
                content: 'url:/xsddWeb/editReturnpolicy.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function makeOption3(json){
            var htm='接受退货</br>'
            if(json.returnsWithinOptionName!=null&&json.returnsWithinOptionName!=""){
                htm+='退货天数：'+json.returnsWithinOptionName+'</br>';
            }
            if(json.returnsAcceptedOptionName!=null&&json.returnsAcceptedOptionName!=""){
                htm+='退货政策：'+json.returnsAcceptedOptionName+'</br>';
            }
            if(json.shippingCostPaidByOptionName!=null&&json.shippingCostPaidByOptionName!=""){
                var period = json.unpaidPeriod;
                htm+='退货运费由谁承担:'+json.shippingCostPaidByOptionName+'</br>';
            }
            if(json.policyCount!=null&&json.policyCount!=""){
                var period = json.policyPeriod;
                htm+='退货方式：'+json.refundOptionName+'</br>';
            }
            return htm;
        }
        $(document).ready(function(){
            $("#returnPolicyListTable").initTable({
                url:path + "/ajax/loadReturnpolicyList.do?",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"退货明细",name:"option1",width:"8%",align:"left",format:makeOption3},
                    {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#returnPolicyListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
                editReturnpolicy($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="禁用"||$(obj).find(":selected").text()=="启用"){
                delReturnPolicy($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="查看"){
                editReturnPolicyselect($(obj).find(":selected").val());
            }
            $(obj).val("");
        }
        function editReturnPolicyselect(id){
            returnPolicy=$.dialog({title: '编辑退款选项',
                content: 'url:/xsddWeb/editReturnpolicy.do?id='+id+'&type=01',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function delReturnPolicy(id){
            var url=path+"/ajax/delReturnPolicy.do?id="+id;
            $().invoke(url,{},
                    [function(m,r){
                        alert("操作成功！");
                        Base.token();
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
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
    </script>
</head>
<body>
<div class="newds">
    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addReturnpolicy()">新增</a></div>
</div>
<div id="returnPolicyListTable"></div>
</body>
</html>
