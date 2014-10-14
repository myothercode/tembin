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
        var addTablePrice
        var apis = frameElement.api, W = apis.opener;
        $(document).ready(function(){
            $("#TablePriceList").initTable({
                url:path + "/ajax/ajaxTablePriceList.do",
                columnData:[
                    {title:"选择",name:"itemName",width:"8%",align:"left",format:makeOption0},
                    {title:"SKU",name:"sku",width:"8%",align:"left"},
                    {title:"ebay账户",name:"ebayAccount",width:"8%",align:"left"},
                    {title:"价格",name:"price",width:"8%",align:"left"},
                    {title:"操作",name:"Option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        /**组装操作选项*/
        function makeOption0(json){
            var htm="<input type=checkbox name='tablepriceId' sku='"+json.sku+"' ebayaccount='"+json.ebayAccount+"' price='"+json.price+"' value='"+json.id+"' />";
            return htm;
        }

        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=editTablePriceselect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
            hs+="<li style='height:25px' onclick=delTablePrice('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
            hs+="<li style='height:25px' onclick=editTablePrice('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function selectDo(obj){
            if($(obj).find(":selected").text()=="编辑"){
                editTablePrice($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="删除"){
                delTablePrice($(obj).find(":selected").val());
            }else if($(obj).find(":selected").text()=="查看"){
                editTablePriceselect($(obj).find(":selected").val());
            }
            $(obj).val("");
        }
        function delTablePrice(id){
            var url=path+"/ajax/delTablePrice.do?id="+id;
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
        function editTablePrice(id){
            addTablePrice = $.dialog({title: '编辑调价',
                content: 'url:' + path + '/editTablePrice.do?id='+id,
                icon: 'succeed',
                parent: apis,
                lock: true,
                width: 550,
                zIndex:9999,
                height: 280
            });
        }
        function editTablePriceselect(id){
            addTablePrice = $.dialog({title: '查看调价',
                content: 'url:' + path + '/editTablePrice.do?id='+id+'&type=01',
                icon: 'succeed',
                parent: apis,
                lock: true,
                width: 550,
                zIndex:9999,
                height: 280
            });
        }
        function  refreshTable(){
            $("#TablePriceList").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }

        function addTablePrice(obj){
            $(obj).attr({style:"color:red"});
            addTablePrice = $.dialog({title: '新增调价',
                content: 'url:' + path + '/addTablePrice.do',
                icon: 'succeed',
                parent: apis,
                lock: true,
                width: 550,
                zIndex:9999,
                height: 280
            });
        }

        function downloadTemplate(obj){
            $(obj).attr({style:"color:red"});
            var url=path+"/downloadTemplate.do";
            window.open(url);
        }

        function importTemplate(obj) {
            $(obj).attr({style:"color:red"});
            addTablePrice = $.dialog({title: '选择需要导入的文件',
                content: 'url:' + path + '/importTemplate.do',
                icon: 'succeed',
                width: 400,
                zIndex:9999,
                parent: apis
            });
        }
        function runPrice(){
            var price = "",sku="",ebayaccount="";
            $("input[type='checkbox'][name='tablepriceId']").each(function(i,d){
                if($(d).prop("checked")){
                    price+=$(d).attr("price")+",";
                    sku+=$(d).attr("sku")+",";
                    ebayaccount+=$(d).attr("ebayaccount")+",";;
                }
            });
            if(price==""||sku==""||ebayaccount==""){
                alert("请选择需要调价的信息！");
                return;
            }
            var url=path+"/ajax/runPrice.do?price="+price.substr(0,price.length-1)+"&&sku="+sku.substr(0,sku.length-1)+"&&ebayaccount="+ebayaccount.substr(0,ebayaccount.length-1);
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
<div class="newsj_left">
    <span class="newusa_ici_del" onclick="addTablePrice(this)">新增调价</span>
    <span class="newusa_ici_del" onclick="downloadTemplate(this)">下载模板</span>
    <span class="newusa_ici_del"  onclick="importTemplate(this);">导入模板</span>
    <div class="tbbay"><a data-toggle="modal" href="javascript:void(0)" onclick="runPrice()" class="">执行调价</a></div>
</div>

<div id="TablePriceList">
</div>
</body>
</html>
