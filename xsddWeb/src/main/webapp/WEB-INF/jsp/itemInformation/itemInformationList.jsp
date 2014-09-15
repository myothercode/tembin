<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/4
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var itemInformation;
        $(document).ready(function(){
            $("#ItemInformationListTable").initTable({
                url:path + "/information/ajax/loadItemInformationList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"8%",align:"left",format:makeOption4},
                    {title:"图片",name:"pictureUrl",width:"8%",align:"left",format:makeOption2},
                    {title:"商品SKU",name:"sku",width:"8%",align:"left"},
                    {title:"商品名称",name:"name",width:"8%",align:"left"},
                    {title:"标签",name:"remark",width:"8%",align:"left"},
                    {title:"分类",name:"typeName",width:"8%",align:"left"},
                    {title:"状态",name:"pictureUrl",width:"8%",align:"left",format:makeOption3},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#ItemInformationListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"printSku('"+json.id+"');\">打印SKU</a>";
            return htm;
        }
        function makeOption2(json){
            var htm="<img src="+json.pictureUrl+" style=\" width: 50px;height: 50px; \">";
            return htm;
        }
        function makeOption3(json){
            if(json.pictureUrl){
                return "ok";
            }else{
                return "none";
            }
        }
        function makeOption4(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function submitCommit(){
            var remark=$("#remarkid").val();
            var information=$("#information").val();
            var itemType=$("#itemTypeid").val();
            var content=$("#content").val();
            var userparms={"remark":remark,"information":information,"itemType":itemType}
            $("#ItemInformationListTable").initTable({
                url:path + "/information/ajax/loadItemInformationList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"8%",align:"left",format:makeOption4},
                    {title:"图片",name:"pictureUrl",width:"8%",align:"left",format:makeOption2},
                    {title:"商品SKU",name:"sku",width:"8%",align:"left"},
                    {title:"商品名称",name:"name",width:"8%",align:"left"},
                    {title:"标签",name:"remark",width:"8%",align:"left"},
                    {title:"分类",name:"typeName",width:"8%",align:"left"},
                    {title:"状态",name:"pictureUrl",width:"8%",align:"left",format:makeOption3},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#ItemInformationListTable").selectDataAfterSetParm();
            refreshTable1(remark,information,itemType,content);
        }
        function refreshTable1(remark,information,itemType,content){
            $("#ItemInformationListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0,"remark":remark,"information":information,"itemType":itemType,"content":content});
        }
        function addItemInformation(){
            var url=path+"/information/addItemInformation.do";
            itemInformation=$.dialog({title: '添加或修改商品信息',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050
            });
        }
        function removeItemInformation(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length>0){
                var str="";

                for(var i=0;i<id.length;i++){
                    if(i!=id.length-1){
                        str+="\"id["+i+"]\":"+$(id[i]).val()+",";
                    }else{
                        str+="\"id["+i+"]\":"+$(id[i]).val();
                    }
                }
                var data1= "{"+str+"}";
                var data= eval('(' + data1 + ')');
                console.debug(data);
                var url=path+"/information/ajax/removeItemInformation.do";
                $().invoke(url,data,
                        [function(m,r){
                            alert(r);
                            refreshTable();
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }else{
                alert("请选择要删除的数据");
            }
        }
        function updateItemInformation(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length==1){
                var url=path+"/information/addItemInformation.do?id="+id;
                itemInformation=$.dialog({title: '添加或修改商品信息',
                    content: 'url:'+url,
                    icon: 'succeed',
                    width:1050
                });
            }else if(id.length>1){
                alert("请选择单个需要修改的数据");
            }else{
                alert("请选择需要修改的数据");
            }
        }
        function addRemark(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length==1){
                var url=path+"/information/addRemark.do?id="+$(id).val();
                itemInformation=$.dialog({title: '添加标签',
                    content: 'url:'+url,
                    icon: 'succeed',
                    width:1050
                });
            }else if(id.length>1){
                alert("请选择单个需要添加标签的商品");
            }else{
                alert("请选择需要添加标签的商品");
            }
        }
        function exportItemInformation(){
            var id=$("input[type='checkbox'][name='templateId']:checked");
            if(id.length>0){
                var str="";
                for(var i=0;i<id.length;i++){
                    if(i!=id.length-1){
                        str+="\"id["+i+"]\":"+$(id[i]).val()+",";
                    }else{
                        str+="\"id["+i+"]\":"+$(id[i]).val();
                    }
                }
                var data1= "{"+str+"}";
                var data= eval('(' + data1 + ')');
                var url=path+"/information/exportItemInformation1.do";
                for(var i=0;i<id.length;i++){
                    if(i==0){
                        url=url+"?id["+i+"]="+$(id[i]).val();
                    }else{
                        url=url+"&id["+i+"]="+$(id[i]).val();
                    }
                }
                window.open(url);
            }else{
                alert("请选择要导出的数据");
            }
        }
        function importItemInformation(){
            var url=path+"/information/importItemInformation.do";
            itemInformation=$.dialog({title: '请选择导入的excel文件',
                content: 'url:'+url,
                icon: 'succeed',
                width:1050
            });
        }
    </script>
</head>
<body>
    按标签查看:<select id="remarkid" name="remark">
        <option value="all">全部</option>
        <option value="null">无标签</option>
        <c:forEach items="${remarks}" var="remark">
            <option value="${remark.id}">${remark.configName}</option>
        </c:forEach>
    </select><br/>
    信息状态:<select id="information" name="information">
        <option value="all">全部</option>
        <option value="custom">无报关信息</option>
        <option value="supplier">无供应商</option>
        <option value="inventory">无库存</option>
    </select><br/>
    搜索内容:<select id="itemTypeid" name="itemType">
        <option value="all">选择类型</option>
        <c:forEach items="${types}" var="type">
            <option value="${type.id}">${type.configName}</option>
        </c:forEach>
    </select>
    &nbsp;<input id="content" name="content"/>&nbsp;<input type="button" value="查询" onclick="submitCommit();"/><br/>
<input type="button" value="添加商品" onclick="addItemInformation();"/>&nbsp;<input type="button" value="导入商品" onclick="importItemInformation();"/>&nbsp;<input type="button" value="导出商品" onclick="exportItemInformation();"/>
&nbsp;<input type="button" value="添加标签" onclick="addRemark();"/>&nbsp;<input type="button" value="删除商品" onclick="removeItemInformation();"/>&nbsp;<input type="button" value="修改商品分类" onclick="updateItemInformation();"/>
<div id="ItemInformationListTable"></div>
<a id="download" ></a>
</body>

</html>
