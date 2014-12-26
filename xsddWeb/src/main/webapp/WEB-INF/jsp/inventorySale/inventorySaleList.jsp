<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/12/22
  Time: 16:11
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
        $(document).ready(function(){
            $("#itemInventoryTableList").initTable({
                url:path + "/inventorySale/ajax/loadItemInventoryList.do?",
                columnData:[
                    {title:"第三方库存名",name:"dataSource",width:"20%",align:"left",format:makeOption5},
                    {title:"SKU",name:"sku",width:"20%",align:"left"},
                    {title:"仓库号",name:"storageNo",width:"20%",align:"left"},
                    {title:"仓库",name:"warehouse",width:"20%",align:"left"},
                    {title:"利用",name:"availStock",width:"20%",align:"left"},
                    {title:"操作",name:"status",width:"20%",align:"center",format:makeOption4}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            $("#itemInventoryTableList1").initTable({
                url:path + "/inventorySale/ajax/loadItemInventoryList.do?",
                columnData:[
                    {title:"第三方库存名",name:"dataSource",width:"20%",align:"left",format:makeOption1},
                    {title:"SKU",name:"sku",width:"20%",align:"left"},
                    {title:"数量",name:"quantity",width:"20%",align:"left",format:makeOption2},
                    {title:"状态",name:"status",width:"20%",align:"left"},
                  /*  {title:"操作",name:"status",width:"2%",align:"center",format:makeOption3}*/
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable1(1);
        });
        function makeOption1(json){
            return "四海游";
        }
        function makeOption4(json){
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=editChuKouYi('"+json.sku+"') doaction=\"readed\" >编辑</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function makeOption5(json){
            if(json.dataSource=="1"){
                return "出口易";
            }
            if(json.dataSource=="2"){
                return "第四方";
            }
        }
        var inventorySale;
        function editChuKouYi(sku){
            var url=path + "/inventorySale/editChuKouYi.do?sku="+sku;
            inventorySale=openMyDialog({title: '编辑',
                content: 'url:'+url,
                icon: 'succeed',
                width:650,
                lock:true,
                zIndex:1000
            });
        }
        function makeOption3(json){
            var hs="";
            hs+="<li style=\"height:25px;\" onclick=editSiHaiYou("+json.id+") doaction=\"readed\" >编辑</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function editSiHaiYou(id){
            alert(234);
        }
        function makeOption2(json){
            if(json.quantity&&json.quantity>0){
                return json.quantity+"";
            }else{
                return "0";
            }
        }
        function refreshTable1(content){
            $("#itemInventoryTableList").selectDataAfterSetParm({"content":content,"flag":"true"});
        }
        function refreshTable2(){
            $("#itemInventoryTableList1").selectDataAfterSetParm({"flag":"false"});
        }
    </script>
</head>
<body>
<div class="new_all" <%--style="width: "--%>>
    <div class="here">当前位置：首页 &gt; 销售管理 &gt; <b>库存销售</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <script type="text/javascript">
            function setTab(name,cursel,n){
                for(i=1;i<=n;i++){
                    var menu=document.getElementById(name+i);
                    var con=document.getElementById("con_"+name+"_"+i);
                    if(i<4&&i>1){
                        con=document.getElementById("con_"+name+"_"+2);
                    }
                    menu.className=i==cursel?"new_tab_1":"new_tab_2";
                    if(i<4&&i>1){

                        con.style.display=(2==cursel||3==cursel)?"block":"none";
                    }else{
                        con.style.display=i==cursel?"block":"none";
                    }
                }
                if(cursel<4&&cursel>1){
                    refreshTable1(cursel-1);
                }else if(cursel==4){

                    refreshTable2();
                }


            }
        </script>

        <div class="new_tab_ls">
            <dt id="menu1" class="new_tab_1" onclick="setTab('menu',1,4)">全部</dt>
            <dt id="menu2" class="new_tab_2" onclick="setTab('menu',2,4)">出口易</dt>
            <dt id="menu3" class="new_tab_2" onclick="setTab('menu',3,4)">递四方</dt>
            <dt id="menu4" class="new_tab_2" onclick="setTab('menu',4,4)">四海游</dt>
        </div>
        <div class="Contentbox">
            <div>
                <div id="con_menu_1" style="display: block;">
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="itemInventoryTableList2">123</div>
                    <div class="page_newlist">
                    </div>
                    <!--综合结束 -->
                </div>
                <div id="con_menu_2" style="display: none;">
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="itemInventoryTableList"></div>
                    <div class="page_newlist">

                    </div>
                    <!--综合结束 -->
                </div>
                <!--结束 -->
                <div id="con_menu_4" style="display: none;">
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="itemInventoryTableList1"></div>
                    <div class="page_newlist">
                    </div>
                    <!--综合结束 -->
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
