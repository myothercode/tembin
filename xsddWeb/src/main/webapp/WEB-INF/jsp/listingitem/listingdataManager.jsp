<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/23
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <title></title>
    <script>
        var loadurl=path+"/ajax/ListingItemList.do?1=1";
        var maplisting = new Map();
        maplisting.put("listing", path + "/ajax/ListingItemList.do?1=1");
        maplisting.put("sold", path + "/ajax/ListingItemList.do?flag=1");
        maplisting.put("unsold", path + "/ajax/ListingItemList.do?flag=2");
        maplisting.put("updatelog", path + "/ajax/getListItemDataAmend.do?1=1");
        var nameFolder = "listing";
        function setTab(obj) {
            nameFolder = $(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if ($(d).attr("name") == nameFolder) {
                    $(d).attr("class", "new_tab_1");
                } else {
                    $(d).attr("class", "new_tab_2");
                }
            });
            if(nameFolder=="updatelog"){
                $("#amendlog").show();
                $("[name='attrshow']").each(function(i,d){
                    $(d).hide();
                });
            }else{
                $("#amendlog").hide();
                $("[name='attrshow']").each(function(i,d){
                    $(d).show();
                });
            }
            $("a").each(function(i,d){
                if($(d).find("span").text()=="全部"){
                    $(d).find("span").attr("class","newusa_ici");
                }else if($(d).find("span").text()!=""){
                    $(d).find("span").attr("class","newusa_ici_1");
                }
            });
            if(nameFolder.indexOf("myFolder_")==-1){
                loadurl=maplisting.get(nameFolder);
            }else{
                loadurl=path + "/ajax/ListingItemList.do?1=1&folderid="+$(obj).attr("val");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }

        }

        function itemstatus(json){
            var htm = "";
            if(json.endisflag=="1"){
                htm="<img src='"+path+"/img/new_yes.png'>";
            }else{
                htm="<img src='"+path+"/img/new_no.png'>";
            }
            return htm;
        }
        function amendIsFlag(json){
            var htm="";
            if(json.endisflag=="1"){
                htm="";
            }else{
                if(json.endid!=null&&json.endid!=""){
                    htm="<a onclick=continueWork('"+json.id+"','"+json.endid+"') >继续处理</a>";
                }
            }
            return htm;
        }
        function continueWork(id,endid){
            var url = path + "/ajax/continueWork.do?id=" + id+"&&endid="+endid;
            $().invoke(url, {},
                    [function (m, r) {
                        alert(r);
                        Base.token();
                        onloadTableamend(loadurl);
                    },
                        function (m, r) {
                            alert(r);
                            Base.token();
                            onloadTableamend(loadurl);
                        }],{isConverPage:true}
            );
        }
        function onloadTableamend(urls){
            $("#itemTable").initTable({
                url:urls,
                columnData:[
                    {title:"图片",name:"Option1",width:"6%",align:"left",format:picUrl},
                    {title:"物品标题",name:"title",width:"8%",align:"left"},
                    {title:"SKU",name:"sku",width:"8%",align:"left"},
                    {title:"ebay账户",name:"ebayAccount",width:"8%",align:"left"},
                    {title:"站点",name:"site",width:"8%",align:"left"},
                    {title:"刊登类型",name:"listingType",width:"8%",align:"left",format:listingType},
                    {title:"价格",name:"price",width:"8%",align:"left"},
                    {title:"数量/已售",name:"price",width:"8%",align:"left"},
                    {title:"商品数量",name:"Option1",width:"8%",align:"left",format:tjCount},
                    {title:"修改时间",name:"amendTime",width:"8%",align:"left"},
                    {title:"操作内容",name:"content",width:"8%",align:"left"},
                    {title:"状态",name:"content",width:"4%",align:"left",format:itemstatus},
                    {title:"操作",name:"Option1",width:"8%",align:"left",format:amendIsFlag}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick: true,
                rowClickMethod: function (obj,o){
                    if($("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked")){
                        $("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked",false);
                    }else{
                        $("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked",true);
                    }
                }
            });
            refreshTable();
        }

        function selectCounty(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");

            if(loadurl.indexOf("&county=")!=-1){
                if(loadurl.substr(loadurl.indexOf("county=")).indexOf("&")!=-1){
                    var str = loadurl.substr(loadurl.indexOf("county="));
                    loadurl = loadurl.substr(0,loadurl.indexOf("&county="))+str.substr(str.indexOf("&"))+"&county="+$(obj).attr("value");
                }else{
                    loadurl = loadurl.substr(0,loadurl.indexOf("&county="))+"&county="+$(obj).attr("value");
                }
            }else{
                loadurl = loadurl+"&county="+$(obj).attr("value");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        function selectListType(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");

            if(loadurl.indexOf("&listingtype=")!=-1){
                if(loadurl.substr(loadurl.indexOf("listingtype=")).indexOf("&")!=-1){
                    var str = loadurl.substr(loadurl.indexOf("listingtype="));
                    loadurl = loadurl.substr(0,loadurl.indexOf("&listingtype="))+str.substr(str.indexOf("&"))+"&listingtype="+$(obj).attr("value");
                }else{
                    loadurl = loadurl.substr(0,loadurl.indexOf("&listingtype="))+"&listingtype="+$(obj).attr("value");
                }
            }else{
                loadurl = loadurl+"&listingtype="+$(obj).attr("value");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        function selectEbayAccount(obj){
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");

            if(loadurl.indexOf("&ebayaccount=")!=-1){
                if(loadurl.substr(loadurl.indexOf("ebayaccount=")).indexOf("&")!=-1){
                    var str = loadurl.substr(loadurl.indexOf("ebayaccount="));
                    loadurl = loadurl.substr(0,loadurl.indexOf("&ebayaccount="))+str.substr(str.indexOf("&"))+"&ebayaccount="+$(obj).attr("value");
                }else{
                    loadurl = loadurl.substr(0,loadurl.indexOf("&ebayaccount="))+"&ebayaccount="+$(obj).attr("value");
                }
            }else{
                loadurl = loadurl+"&ebayaccount="+$(obj).attr("value");
            }
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        var selectStr = "";
        var selectQuery="";
        function selectData(){
            var urls = loadurl;
            var selectType = $("select[name='selecttype']").find("option:selected").val();
            var selectValue = $("input[name='selectvalue']").val();
            if(urls.indexOf(selectQuery)>0){
                urls = urls.replace(selectQuery,"");
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;
            }else{
                urls=urls+"&selectType="+selectType+"&selectValue="+selectValue;
            }
            if(selectType!=null&&selectType!=""){
                selectQuery="&selectType="+selectType+"&selectValue="+selectValue;
            }
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }

        function selectAllData(obj){
            var urls = loadurl;
            if(urls.indexOf(selectQuery)>0) {
                urls = urls.replace(selectQuery,"");
            }
            $(obj).parent().find("select[name='selecttype']").val("");
            $(obj).parent().find("input[name='selectvalue']").val("");
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        var amendType = "";
        var amendFlag = "";
        function selectAmendType(obj){
            var urls = loadurl;
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if(urls.indexOf(amendType)>0) {
                urls = urls.replace(amendType,"");
                urls = urls+"&amendType="+$(obj).attr("value");
            }else{
                urls = urls+"&amendType="+$(obj).attr("value");
            }
            amendType="&amendType="+$(obj).attr("value");
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }

        function selectAmendFlag(obj){
            var urls = loadurl;
            $(obj).parent().find("a").each(function(i,d){
                $(d).find("span").attr("class","newusa_ici_1");
            });
            $(obj).find("span").attr("class","newusa_ici");
            if(urls.indexOf(amendFlag)>0) {
                urls = urls.replace(amendFlag,"");
                urls = urls+"&amendFlag="+$(obj).attr("value");
            }else{
                urls = urls+"&amendFlag="+$(obj).attr("value");
            }
            amendFlag="&amendFlag="+$(obj).attr("value");
            loadurl = urls;
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        }
        function getCentents(){
            alert($("#centents").val());
        }
        function listingType(json){
            var htm="";
            if(json.listingType=="2"){
                htm="lx.png";
            }else if(json.listingType=="Chinese"){
                htm="bids.png";
            }else if(json.listingType=="FixedPriceItem"){
                htm="buyit.jpg";
            }

            return "<img width='24' height='17' src='"+path+"/img/"+htm+"'>";
        }
        function endItemByid(itemidStr){
            var tent = "<div>原因<select id='centents' name='centents'><option value='Incorrect'>Incorrect</option>" +
                    "<option value='LostOrBroken'>LostOrBroken</option>" +
                    "<option value='NotAvailable'>NotAvailable</option>" +
                    "<option value='OtherListingError'>OtherListingError</option>" +
                    "<option value='SellToHighBidder'>SellToHighBidder</option>" +
                    "<option value='Sold'>Sold</option></select></div>";
            var editPage = $.dialog({title: '提前结束原因',
                content: tent,
                icon: 'succeed',
                width: 400,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            if (iwins.parent.document.getElementById("centents").value == "") {
                                alert("结束原因必填！");
                                return false;
                            } else {
                                //alert(iwins.parent.document.getElementById("centents").selectedIndex);
                                reason = iwins.parent.document.getElementById("centents").options[iwins.parent.document.getElementById("centents").selectedIndex].value;
                                //alert(reason);
                                var url = path + "/ajax/endItem.do?itemidStr=" + itemidStr+"&reason="+reason;
                                $().invoke(url, {},
                                        [function (m, r) {
                                            alert(r);
                                            Base.token();
                                            if("updatelog"==nameFolder){
                                                onloadTableamend(loadurl);
                                            }else{
                                                onloadTable(loadurl);
                                            }
                                        },
                                            function (m, r) {
                                                alert(r);
                                                Base.token();
                                            }]
                                );
                            }
                        }
                    }
                ]
            });
        }
        function endItem(obj) {
            $("obj").attr({style:"color:red"});
            var item = $("input[name='listingitemid']");
            var itemidStr = "";
            for (var i = 0; i < item.length; i++) {
                if ($(item[i])[0].checked) {
                    itemidStr += $(item[i])[0].value + ",";
                }
            }
            if(itemidStr==""){
                alert("请选择需结束的商品！");
                return ;
            }
            endItemByid(itemidStr);

        }
        function addTabRemark(){
            var url=path+"/order/selectTabRemark.do?folderType=listingFolder";
            OrderGetOrders=$.dialog({title: '选择文件夹',
                content: 'url:'+url,
                icon: 'succeed',
                width:800
            });
        }

        /**
        *表格调 价
         */
        var tablePricewin=null;
        function tablePrice(obj){
            var url=path+"/getTablePriceList.do";
            tablePricewin=$.dialog({title: '表格调价列表',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                lock: true,
                height:400
            });
        }
        $(document).ready(function(){
            var url=path+"/ajax/selfFolder.do?folderType=listingFolder";
            $().invoke(url,{},
                    [function(m,r){
                        var htmlstr='<dt name="listing" class=new_tab_1 onclick="setTab(this)">在线</dt>'
                                +'<dt name="sold" class=new_tab_2 onclick="setTab(this)">已售</dt>'
                                +'<dt name="unsold" class=new_tab_2 onclick="setTab(this)">未卖出</dt>'
                                +'<dt name="updatelog" class=new_tab_2 onclick="setTab(this)">在线修改日志</dt>';
                        if(r!=null) {
                            for (var i = 0; i < r.length; i++) {
                                htmlstr += "<dt name='myFolder_" + i + "' val='" + r[i].id + "' class=new_tab_2 onclick='setTab(this)'>" + r[i].configName + "</dt>";
                            }
                        }
                        $("#tab").html(htmlstr);
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
            if("updatelog"==nameFolder){
                onloadTableamend(loadurl);
            }else{
                onloadTable(loadurl);
            }
        });

        function toFolder(idStr){
            var url=path+"/ajax/selfFolder.do?folderType=listingFolder";
            $().invoke(url,{},
                    [function(m,r){
                        var htmlstr = "<div>选择文件夹：</div>";
                        htmlstr += "<div>";
                        for(var i = 0;i < r.length;i++){
                            htmlstr+="<div><input type='radio' name='folderid' value='"+r[i].id+"'/>"+r[i].configName+"</div>";
                        }
                        htmlstr += "</div>";
                        var editPage = $.dialog({title: '选择移动到的文件夹',
                            content: htmlstr,
                            icon: 'succeed',
                            width: 400,
                            button: [
                                {
                                    name: '确定',
                                    callback: function (iwins, enter) {
                                        var folderid = "";
                                        if (iwins.parent.document.getElementsByName("folderid").value == "") {
                                            alert("请选择文件夹！");
                                            return false;
                                        } else {
                                            for(var i =0;i<iwins.parent.document.getElementsByName("folderid").length;i++){
                                                if(iwins.parent.document.getElementsByName("folderid")[i].checked){
                                                    folderid = iwins.parent.document.getElementsByName("folderid")[i].value;
                                                }
                                            }
                                            var url = path + "/ajax/shiftListingToFolder.do?idStr=" + idStr+"&folderid="+folderid;
                                            $().invoke(url, {},
                                                    [function (m, r) {
                                                        alert(r);
                                                        Base.token();
                                                        if("updatelog"==nameFolder){
                                                            onloadTableamend(loadurl);
                                                        }else{
                                                            onloadTable(loadurl);
                                                        }
                                                    },
                                                        function (m, r) {
                                                            alert(r);
                                                            Base.token();
                                                        }]
                                            );
                                        }
                                    }
                                }
                            ]
                        });
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }
        function shiftToFolder(obj) {
            var item = $("input[name='listingitemid']");
            var idStr = "";

            for (var i = 0; i < item.length; i++) {
                if ($(item[i])[0].checked) {
                    idStr += $($(item[i])[0]).attr("val") + ",";
                }
            }
            if(idStr==""){
                alert("请选择需要移动的商品！");
                return ;
            }
        }
        function getTitle(json){
            var html="";
            if(json.title.length>20){
                html = "<span title='"+json.title+"'>"+json.title.substr(0,20)+".....</span>";
            }else{
                html = json.title;
            }
            var remark = "";
            if(json.remark!=null&&json.remark!=""){
                if(json.remark.length>25){
                    remark = json.remark.substr(0,25)+"......";
                }else{
                    remark = json.remark;
                }
                html+="<span class='newdf' title='"+json.remark+"'>备注："+remark+"</span>";
            }
            return html;
        }
        function onloadTable(urls){
            $("#itemTable").initTable({
                url:urls,
                columnData:[
                    {title:"选择",name:"itemName",width:"4%",align:"left",format:makeOption0},
                    {title:"图片",name:"Option1",width:"8%",align:"left",format:picUrl},
                    {title:"物品标题",name:"title",width:"8%",align:"left",format:getTitle},
                    {title:"SKU",name:"sku",width:"4%",align:"left"},
                    {title:"ebay账户",name:"ebayAccount",width:"8%",align:"left"},
                    {title:"站点",name:"site",width:"4%",align:"left"},
                    {title:"刊登类型",name:"listingType",width:"8%",align:"left",format:listingType},
                    {title:"价格",name:"price",width:"10%",align:"left",format:getPriceHtml},
                    {title:"数量/已售",name:"Option1",width:"10%",align:"left",format:tjCount},
                    {title:"结束时间",name:"endtime",width:"8%",align:"left"},
                    {title:"操作",name:"Option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick: true,
                rowClickMethod: function (obj,o){
                    if($("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked")){
                        $("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked",false);
                    }else{
                        $("input[type='checkbox'][name='listingitemid'][val='"+obj.id+"']").prop("checked",true);
                    }
            }
            });
            refreshTable();
        }
        function getPriceHtml(json){
            var htm="";
            if("updatelog"!=nameFolder&&json.listingType=="FixedPriceItem") {
                htm = "<div style='display:inline;'><input type=text name='price' onFocus='showImg(this)' ids='"+json.id+"' itemId='"+json.itemId+"' " +
                        "size='1' style='height:20px;border: 1px solid #AA17A4;' class='form-control' value='"+json.price+"'/></br>" +
                        "<img onclick='updateItemPrice(this)' style='display:none;' src='<c:url value ="/img/save_ion.gif"/>' />&nbsp;&nbsp;&nbsp;&nbsp;<img onclick='hideImg(this)' style='display:none;' src='<c:url value ="/img/undo1_re.gif"/>'/></div>"+"</br>" +
                        "+$";
                if(json.shippingPrice==null){
                    htm+="0.00";
                }else{
                    htm+=json.shippingPrice;
                }
            }else{
                htm = json.price+"</br>+$"+json.shippingPrice;;
            }

            return htm;
        }
        function showImg(obj){
            $(obj).parent().find("img").each(function(i,d){
                $(d).show();
            });
        }
        function hideImg(obj){
            $(obj).parent().find("img").each(function(i,d){
                $(d).hide();
            });
        }
        //修改界面
        function updateItemPrice(obj){
            var price=$(obj).parent().find("[type='text']").val();
            var itemId=$(obj).parent().find("[type='text']").attr("itemId");
            var textname = $(obj).parent().find("[type='text']").attr("name");
            var ids = $(obj).parent().find("[type='text']").attr("ids");
            var type=textname;
            var url = path + "/ajax/updateListingData.do?itemId=" + itemId+"&price="+price+"&type="+type+"&ids="+ids;
            $().invoke(url, {},
                    [function (m, r) {
                        alert(r);
                        Base.token();
                        if("updatelog"==nameFolder){
                            onloadTableamend(loadurl);
                        }else{
                            onloadTable(loadurl);
                        }
                    },
                        function (m, r) {
                            alert(r);
                            Base.token();
                        }]
            );
        }
        /**组装操作选项*/
        function makeOption0(json){
            var htm="<input type=checkbox name='listingitemid' listingType='"+json.listingType+"' value='"+json.itemId+"' val='"+json.id+"' />";
            return htm;
        }
        function picUrl(json){
            var htm="<img width='50px' height='50px' src='"+json.picUrl+"'>";
            return htm;
        }
        function tjCount(json){
            var htm="";
            if("updatelog"!=nameFolder&&json.listingType=="FixedPriceItem") {
                htm="<div style='display:inline;'><input type=text name='quantity' onFocus='showImg(this)' ids='"+json.id+"' itemId='"+json.itemId+"' size='1'  style='height:20px;border: 1px solid #AA17A4;' class='form-control' value='"+json.quantity+"'/></br><img onclick='updateItemPrice(this)' style='display:none;' onkeypress='showImg(this)' src='<c:url value ="/img/save_ion.gif"/>' />&nbsp;&nbsp;&nbsp;&nbsp;<img onclick='hideImg(this)' style='display:none;' src='<c:url value ="/img/undo1_re.gif"/>'/></div></br>"+json.quantity+"/"+json.quantitysold;
            }else{
                htm = json.quantity+"/"+json.quantitysold;
            }
            return htm;
        }

        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=toFolder('"+json.id+"') value='"+json.id+"' doaction=\"look\" >移动</li>";
            if("updatelog"!=nameFolder) {
                hs += "<li style='height:25px' onclick=edit('" + json.itemId + "') value='" + json.id + "' doaction=\"look\" >在线编辑</li>";
                hs += "<li style='height:25px' onclick=endItemByid('" + json.itemId + "') value='" + json.id + "' doaction=\"look\" >提前结束</li>";
                hs += "<li style='height:25px' onclick=quickEdit('" + json.id + "','"+json.listingType+"') value='" + json.id + "' doaction=\"look\" >快速编辑</li>";
                hs += "<li style='height:25px' onclick=remark('" + json.id + "') value='" + json.id + "' doaction=\"look\" >备注</li>";
                hs += "<li style='height:25px' onclick=selectLog('" + json.id + "') value='" + json.id + "' doaction=\"look\" >查看日志</li>";
            }
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        //快速编辑
        var quickEdits;
        function quickEdit(id,listingType){
            var url=path+"/quickEdit.do?id="+id+"&&listingType="+listingType;
            quickEdits=$.dialog({title: '快速编辑',
                content: 'url:'+url,
                icon: 'succeed',
                width:1000,
                lock: true,
                height:400
            });
        }
        //备注
        function remark(id){
            var tent = "<div class='textarea'>备注：<textarea cols='30' rows='5' id='centents' ></textarea></div>";
            var editPage = $.dialog({title: '备注',
                content: tent,
                icon: 'succeed',
                width: 400,
                button: [
                    {
                        name: '确定',
                        callback: function (iwins, enter) {
                            var reason = "";
                            if (iwins.parent.document.getElementById("centents").value == "") {
                                alert("备注必填！");
                                return false;
                            } else {
                                //alert(iwins.parent.document.getElementById("centents").selectedIndex);
                                reason = iwins.parent.document.getElementById("centents").value;
                                //alert(reason);
                                var url = path + "/ajax/addRemark.do?id=" + id+"&remark="+reason;
                                $().invoke(url, {},
                                        [function (m, r) {
                                            //alert(r);
                                            Base.token();
                                            if("updatelog"==nameFolder){
                                                onloadTableamend(loadurl);
                                            }else{
                                                onloadTable(loadurl);
                                            }
                                        },
                                            function (m, r) {
                                                alert(r);
                                                Base.token();
                                            }]
                                );
                            }
                        }
                    }
                ]
            });
        }
        //查看日志
        function selectLog(id){
            var url=path+"/getListItemDataAmend.do?parentId="+id;
            tablePricewin=$.dialog({title: '修改在线商品日志',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                lock: true,
                height:400
            });
        }
        function  refreshTable(){
            $("#itemTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        //在线编辑
        var editPage = "";
        function edit(itemid){
            editPage = $.dialog({title: '编辑在线商品',
                content: 'url:/xsddWeb/editListingItem.do?itemid='+itemid,
                icon: 'succeed',
                width:1000
            });
        }

        function changeSelect(obj){
            var item = $("input[name='listingitemid']");
            var idStr = "";
            var isab = false;
            var listingType="";
            var itemId="";
            for (var i = 0; i < item.length; i++) {
                if ($(item[i])[0].checked) {
                    if(listingType==""){
                        listingType=$($(item[i])[0]).attr("listingType");
                    }else{
                        if(listingType==$($(item[i])[0]).attr("listingType")){
                            listingType=$($(item[i])[0]).attr("listingType");
                        }else{
                            isab=true;
                        }
                    }
                    idStr += $($(item[i])[0]).attr("val") + ",";
                    itemId += $($(item[i])[0]).val() + ",";
                }
            }
            if(idStr==""){
                alert("请选择需要处理的商品！");
                $(obj).val();
                return ;
            }
            if($(obj).val()=="listingEdit"){//在线编辑
                edit(itemId);
            }else if($(obj).val()=="quickEdit"){//快速编辑
                if(isab){
                    alert("不允许不同刊登类型进行编辑！");
                    $(obj).val();
                    return;
                }
                quickEdit(idStr,listingType)
            }else if($(obj).val()=="remark"){//备注
                remark(idStr)
            }
            $(obj).val();
        }
        //同步
    function synListingData(){
        var url = path + "/ajax/myEbayAccount.do";
        $().invoke(url, {},
                [function (m, r) {
                    var ten = "<div>";
                    for(var i=0;i< r.length;i++){
                        ten+="<div><input type='checkbox' name='ebayAccount' id='ebayAccount' value='"+r[i].ebayAccount+"'/>"+r[i].ebayName+"(<font color='blue'>最近同步时间："+r[i].maxDate+"</font>)</div>";
                    }
                    ten+="</div>";
                    $.dialog({title: '选择EBAY账号',
                        content: ten,
                        icon: 'succeed',
                        width: 400,
                        button: [
                            {
                                name: '确定',
                                callback: function (iwins, enter) {
                                    var reason = "";
                                    for(var i=0;i<iwins.parent.document.getElementsByName("ebayAccount").length;i++){
                                        if($(iwins.parent.document.getElementsByName("ebayAccount")[i]).prop("checked")){
                                            reason+=$(iwins.parent.document.getElementsByName("ebayAccount")[i]).val()+",";
                                        }
                                    }
                                    if (reason == "") {
                                        alert("ebay账号必须选择！");
                                        return false;
                                    } else {
                                        var url = path + "/ajax/synListingData.do?ebayAccount="+reason;
                                        $().invoke(url, {},
                                                [function (m, r) {
                                                    alert(r);
                                                    Base.token();
                                                },
                                                    function (m, r) {
                                                        alert(r);
                                                        Base.token();
                                                    }]
                                        );
                                    }
                                }
                            }
                        ]
                    });
                },
                    function (m, r) {
                        alert(r);
                        Base.token();
                    }]
        );
        /**/
    }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>刊登</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="tab">

        </div>

        <div class=Contentbox>
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list"><span class="newusa_i">选择国家：</span>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="US"><span class="newusa_ici_1"><img src="<c:url value ="/img/usa_1.png"/>">美国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="UK"><span class="newusa_ici_1"><img src="<c:url value ="/img/UK.jpg"/>">英国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="Germany"><span class="newusa_ici_1"><img src="<c:url value ="/img/DE.png"/>">德国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="Australia"><span class="newusa_ici_1"><img src="<c:url value ="/img/AU.jpg"/>">澳大利亚</span></a>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">选择账号：</span>
                    <a href="javascript:void(0)" onclick="selectEbayAccount(this)"  value=""><span class="newusa_ici">全部</span></a>
                    <c:forEach items="${ebayList}" var="ebay">
                        <a href="javascript:void(0)" onclick="selectEbayAccount(this)" value="${ebay.ebayAccount}"><span class="newusa_ici_1">${ebay.ebayNameCode}</span></a>
                    </c:forEach>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">刊登类型：</span>

                    <a href="javascript:void(0)" onclick="selectListType(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="FixedPriceItem"><span class="newusa_ici_1">固价</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="2"><span class="newusa_ici_1">多属性</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Chinese"><span class="newusa_ici_1">拍卖</span></a>
                </li>
                <div id="amendlog" style="display: none;">
                    <li class="new_usa_list">
                        <span class="newusa_i">修改类型：</span>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value=""><span class="newusa_ici">全部</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="StartPrice"><span class="newusa_ici_1">价格</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="Title"><span class="newusa_ici_1">标题</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendType(this)" value="Quantity"><span class="newusa_ici_1">数量</span></a>
                    </li>
                    <li class="new_usa_list">
                        <span class="newusa_i">修改状态：</span>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value=""><span class="newusa_ici">全部</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value="1"><span class="newusa_ici_1">成功</span></a>
                        <a href="javascript:void(0)" onclick="selectAmendFlag(this)" value="0"><span class="newusa_ici_1">失败</span></a>
                    </li>
                </div>
                <div class="newsearch">
                    <span class="newusa_i">搜索内容：</span>
                    <a href="javascript:void(0)" onclick="selectAllData(this)" value=""><span class="newusa_ici">全部</span></a>
<span id="sleBG">
<span id="sleHid">
<select name="selecttype" class="select">
    <option selected="selected" value="">选择类型</option>
    <option value="sku">sku</option>
    <option value="title">物品标题</option>
</select>
</span>
</span>

                    <div class="vsearch">
                        <input name="selectvalue" type="text" class="key_1"><input name="newbut" onclick="selectData()" type="button" class="key_2"></div>
                </div>
                <div class="newds">
                    <div class="newsj_left">

                        <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"/></span>

                        <div class="numlist">
                            <div class="ui-select" style="margin-top:1px; width:10px">
                                <select  onchange="changeSelect(this)">
                                    <option value="">请选择</option>
                                    <option value="listingEdit">在线编辑</option>
                                    <option value="quickEdit">快速编辑</option>
                                    <option value="remark">备注</option>
                                </select>
                            </div>
                            <%--<div class="ui-select" style="margin-top:1px; width:10px">
                                <select>
                                    <option value="AK">移动</option>
                                    <option value="AK">动作</option>
                                </select>
                            </div>--%>
                        </div>
                        <span class="newusa_ici_del" onclick="shiftToFolder(this)">移动</span>
                        <span class="newusa_ici_del" name="attrshow" onclick="endItem(this)">提前结束</span>
                        <span class="newusa_ici_del" name="attrshow" onclick="tablePrice(this)">表格调价</span><span
                            class="newusa_ici_del"  onclick="addTabRemark();">管理文件夹</span></div>
                    <div class="tbbay" name="attrshow"><a data-toggle="modal" href="javascript:void(0)" class="" onclick="synListingData()">同步eBay</a></div>
                </div>
            </div>
            <%--<iframe src="/xsddWeb/getListingItemList.do?1=1" id="listing_frame" height="1000px;" frameborder="0"
                    width="100%">
            </iframe>--%>
<div style="width: 100%;float: left;height: 5px"></div>
            <div id="itemTable" >
            </div>
        </div>

    </div>
</div>
</body>
</html>
