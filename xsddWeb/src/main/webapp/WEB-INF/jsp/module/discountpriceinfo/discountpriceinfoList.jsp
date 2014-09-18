<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/24
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var discountPriceInfo;
        function adddiscountpriceinfo(){
            discountPriceInfo= $.dialog({title: '新增折扣选项',
                content: 'url:/xsddWeb/addDiscountPriceInfo.do',
                icon: 'succeed',
                width:400,
                lock:true
            });
        }

        function editdiscountpriceinfo(id){
            discountPriceInfo= $.dialog({title: '编辑折扣选项',
                content: 'url:/xsddWeb/editDiscountPriceInfo.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        $(document).ready(function(){
            $("#discountPriceInfoListTable").initTable({
                url:path + "/ajax/loadDiscountPriceInfoList.do",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"账户名称",name:"ebayName",width:"8%",align:"left"},
                    {title:"开始时间",name:"disStarttime",width:"8%",align:"left"},
                    {title:"结束时间",name:"disEndtime",width:"8%",align:"left"},
                    {title:"折扣",name:"madeforoutletcomparisonprice",width:"8%",align:"left"},
                    {title:"降价",name:"minimumadvertisedprice",width:"8%",align:"left"},
                    {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable() {
            $("#discountPriceInfoListTable").selectDataAfterSetParm({"bedDetailVO.deptId": "", "isTrue": 0});
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
                editdiscountpriceinfo($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="禁用"||$(obj).find(":selected").text()=="启用"){
                delDiscountprice($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="查看"){
                editdiscountpriceinfoSelect($(obj).find(":selected").val());
            }
            $(obj).val("");
        }
        function delDiscountprice(id){
            var url=path+"/ajax/delDiscountprice.do?id="+id;
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
        function editdiscountpriceinfoSelect(id){
            discountPriceInfo= $.dialog({title: '编辑折扣选项',
                content: 'url:/xsddWeb/editDiscountPriceInfo.do?id='+id+'&&type=01',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
    </script>
</head>
<body>
<div style="text-align: right;">
    <input type="button" name="add" value="新增" onclick="adddiscountpriceinfo()">
</div>
<div id="discountPriceInfoListTable"></div>
<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>账户名称</td>
            <td>开始时间</td>
            <td>结束时间</td>
            <td>折扣</td>
            <td>降价</td>
            <td>是否免运费</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${disli}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.ebayName}
                </td>
                <td>
                    <fmt:formatDate value="${li.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                    <fmt:formatDate value="${li.disStarttime}" pattern="yyyy-MM-dd HH:mm"/>
                </td>
                <td>
                        ${li.madeforoutletcomparisonprice}
                </td>
                <td>${li.minimumadvertisedprice}</td>
                <td>
                    <c:if test="${li.isShippingfee=='1'}">
                        <c:out value="是"/>
                    </c:if>
                    <c:if test="${li.isShippingfee!='1'}">
                        <c:out value="否"/>
                    </c:if>
                </td>
                <td>
                    <a target="_blank" href="javascript:void(0)" onclick="editdiscountpriceinfo('${li.id}')">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
