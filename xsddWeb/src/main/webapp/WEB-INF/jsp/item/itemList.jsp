<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script>
        var returnItem ="";
        function addItem(){
            document.location = path+"/addItem.do";
            //window.open("/xsddWeb/addItem.do");
           /* returnItem=openMyDialog({title: '新增商品',
                content: 'url:/xsddWeb/addItem.do',
                icon: 'succeed',
                width:1200
            });*/


        }

        function editItem(id){
            parent.document.location = path+"/editItem.do?id="+id;
            /*returnItem=openMyDialog({title: '编辑商品',
                content: 'url:/xsddWeb/editItem.do?id='+id,
                icon: 'succeed',
                width:1200
            });*/
        }

        $(document).ready(function(){
            var flag='${flag}';
            var county='${county}';
            var listingtype='${listingtype}';
            var ebayaccount='${ebayaccount}';
            var selectType = '${selectType}';
            var selectValue = '${selectValue}';
            var folderid = '${folderid}';
            var urls="/ajax/loadItemList.do?1=1";
            if(flag!=null&&flag!=""){
                urls="/ajax/loadItemList.do?flag="+flag;
            }
            if(county!=null&&county!=""){
                urls+="&county="+county;
            }
            if(listingtype!=null&&listingtype!=""){
                urls+="&listingtype="+listingtype;
            }
            if(ebayaccount!=null&&ebayaccount!=""){
                urls+="&ebayaccount="+ebayaccount;
            }
            if(selectType!=null&&selectType!=""){
                urls+="&selectType="+selectType;
            }
            if(selectValue!=null&&selectValue!=""){
                urls+="&selectValue="+selectValue;
            }
            if(folderid!=null&&folderid!=""){
                urls+="&folderid="+folderid;
            }
            $("#itemTable").initTable({
                url:path + urls,
                columnData:[
                    {title:"选择",name:"itemName",width:"8%",align:"left",format:makeOption0},
                    {title:"商品名称",name:"itemName",width:"8%",align:"left"},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"标题",name:"title",width:"8%",align:"left"},
                    {title:"数量",name:"quantity",width:"8%",align:"left"},
                    {title:"SKU",name:"sku",width:"8%",align:"left"},
                    {title:"刊登状态",name:"isFlag",width:"8%",align:"left"},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        /**组装操作选项*/
        function makeOption0(json){
            var htm="<input type=checkbox name='modelid' value='"+json.itemId+"' isFlag='"+json.isFlag+"' val='"+json.id+"' />";
            return htm;
        }
        /**组装操作选项*/
        function makeOption1(json){
            var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"editItem('"+json.id+"');\">编辑</a>";
            return htm;
        }

        function  refreshTable(){
            $("#itemTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
    </script>
</head>
<body>
<div id="itemTable">

</div>
</body>
</html>
