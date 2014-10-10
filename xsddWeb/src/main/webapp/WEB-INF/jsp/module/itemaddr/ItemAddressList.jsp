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
    <script>
        var itemAddressList;
        function addItemAddress(){
            itemAddressList=$.dialog({title: '新增物品所在地',
                content: 'url:/xsddWeb/addItemAddress.do',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        function editItemAddress(id){
            itemAddressList=$.dialog({title: '编辑物品所在地',
                content: 'url:/xsddWeb/editItemAddress.do?id='+id,
                icon: 'succeed',
                width:500,
                lock:true
            });
        }

        $(document).ready(function(){
            $("#ItemAddressListTable").initTable({
                url:path + "/ajax/loadItemAddressList.do",
                columnData:[
                    {title:"名称",name:"name",width:"8%",align:"left"},
                    {title:"地址",name:"address",width:"8%",align:"left"},
                    {title:"国家",name:"countryName",width:"8%",align:"left"},
                    {title:"邮编",name:"postalcode",width:"8%",align:"left"},
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
            $("#ItemAddressListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
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
                editItemAddress($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="禁用"||$(obj).find(":selected").text()=="启用"){
                delItemAddress($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="查看"){
                editItemAddressselect($(obj).find(":selected").val());
            }
            $(obj).val("");
        }
        function editItemAddressselect(id){
            payPal=$.dialog({title: '查看付款选项',
                content: 'url:/xsddWeb/editItemAddress.do?id='+id+'&type=01',
                icon: 'succeed',
                width:500,
                lock:true
            });
        }
        function delItemAddress(id){
            var url=path+"/ajax/delItemAddress.do?id="+id;
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

    </script>
</head>
<body>
<div class="newds">
    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addItemAddress()">新增</a></div>
</div>
<div id="ItemAddressListTable"></div>

<%--<div>
    <table width="100%">
        <tr>
            <td>名称</td>
            <td>地址</td>
            <td>国家</td>
            <td>邮编</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${li}" var="li">
            <tr>
                <td>${li.name}</td>
                <td>
                    ${li.address}
                </td>
                <td>${li.countryName}</td>
                <td>${li.postalcode}</td>
                <td>
                    <a target="_blank" href="javascript:void(0)" onclick="editItemAddress('${li.id}')">编辑</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>--%>
</body>
</html>
