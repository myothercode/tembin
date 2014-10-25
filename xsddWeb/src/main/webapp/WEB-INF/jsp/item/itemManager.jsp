<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/23
  Time: 16:48
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
        var loadurl=path+"/ajax/loadItemList.do?1=1";
        function setTab(obj){
            var name=$(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if($(d).attr("name")==name){
                    $(d).attr("class","new_tab_1");
                }else{
                    $(d).attr("class","new_tab_2");
                }
            });
            if("listingProduct"==name){
                $("#listing").hide();
            }else{
                $("#listing").show();
            }

            var map=new Map();
            map.put("allProduct",path+"/ajax/loadItemList.do?1=1");
            map.put("listingProduct",path+"/ajax/loadItemList.do?flag=1");
            map.put("product",path+"/ajax/loadItemList.do?flag=2");
            map.put("timeProduct",path+"/ajax/loadItemList.do?flag=3");
            $("a").each(function(i,d){
                if($(d).find("span").text()=="全部"){
                    $(d).find("span").attr("class","newusa_ici");
                }else if($(d).find("span").text()!=""){
                    $(d).find("span").attr("class","newusa_ici_1");
                }
            });
            if(name.indexOf("myFolder_")==-1){
                loadurl=map.get(name);
            }else{
                loadurl=path + "/ajax/loadItemList.do?1=1&folderid="+$(obj).attr("val");
            }
            onloadTable(loadurl);
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
            onloadTable(loadurl);
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
            onloadTable(loadurl);
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
            onloadTable(loadurl);
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
            onloadTable(loadurl);
        }

        function selectAllData(obj){
            var urls = loadurl;
            if(urls.indexOf(selectQuery)>0) {
                urls = urls.replace(selectQuery,"");
            }
            $(obj).parent().find("select[name='selecttype']").val("");
            $(obj).parent().find("input[name='selectvalue']").val("");
            loadurl = urls;
            onloadTable(loadurl);
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
            onloadTable(loadurl);
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
            onloadTable(loadurl);
        }
        function addTabRemark(){
            var url=path+"/order/selectTabRemark.do?folderType=modelFolder";
            OrderGetOrders=$.dialog({title: '选择文件夹',
                content: 'url:'+url,
                icon: 'succeed',
                width:800
            });
        }

        $(document).ready(function(){
            var url=path+"/ajax/selfFolder.do?folderType=modelFolder";
            $().invoke(url,{},
                    [function(m,r) {
                        var htmlstr = '<dt name="allProduct" class=new_tab_1 onclick="setTab(this)">所有范本</dt>'
                                + '<dt name="listingProduct" class=new_tab_2 onclick="setTab(this)">有在线刊登</dt>'
                                + '<dt name="product" class=new_tab_2 onclick="setTab(this)">无在线刊登</dt>'
                                + '<dt name="timeProduct" class=new_tab_2 onclick="setTab(this)">定时</dt>';
                        if (r != null) {
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
            onloadTable(loadurl);
        });
        var returnItem ="";
        function addItem(){
            location = path+"/addItem.do";
        }

        function editItem(id){
            document.location = path+"/editItem.do?id="+id;
        }
        function onloadTable(urls){
            $("#itemTable").initTable({
                url:urls,
                columnData:[
                    {title:"选择",name:"selectName",width:"4%",align:"left",format:makeOption0},
                    {title:"图片",name:"pucURLS",width:"8%",align:"left",format:picUrl},
                    {title:"名称/SKU",name:"itemName",width:"8%",align:"left",format:itemName},
                    {title:"站点",name:"siteName",width:"8%",align:"left"},
                    {title:"刊登类型",name:"listingtype",width:"8%",align:"left",format:listingType},
                    {title:"eBay账户",name:"ebayaccountname",width:"8%",align:"left"},
                    {title:"刊登天数",name:"listingduration",width:"8%",align:"left"},
                    {title:"在线",name:"isFlag",width:"8%",align:"left",format:itemstatus},
                    {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false,
                isrowClick: true,
                rowClickMethod: function (obj,o){
                    if($("input[type='checkbox'][name='modelid'][val='"+obj.id+"']").prop("checked")){
                        $("input[type='checkbox'][name='modelid'][val='" + obj.id + "']").prop("checked", false);
                    }else {
                        $("input[type='checkbox'][name='modelid'][val='" + obj.id + "']").prop("checked", true);
                    }
                }
            });
            refreshTable();
        }
        function itemstatus(json){
            var htm = "";
            if(json.isFlag=="Success"){
                htm="<img src='"+path+"/img/new_yes.png'>";
            }else{
                htm="<img src='"+path+"/img/new_no.png'>";
            }

            return htm;
        }
        function listingType(json){
            var htm="";
            if(json.listingtype=="2"){
                htm="lx.png";
            }else if(json.listingtype=="Chinese"){
                htm="bids.png";
            }else if(json.listingtype=="FixedPriceItem"){
                htm="buyit.jpg";
            }

            return "<img width='24' height='17' src='"+path+"/img/"+htm+"'>";
        }
        function itemName(json){
            var htm="<span style='color:#5F93D7;'>"+json.itemName+"</span></br>"+json.sku;
            return htm;
        }
        function picUrl(json){
            var htm="<img width='50px' height='50px' src='"+json.galleryURL+"'>";
            return htm;
        }
        /**组装操作选项*/
        function makeOption0(json){
            var htm="<input type=checkbox name='modelid' value='"+json.itemId+"' isFlag='"+json.isFlag+"' val='"+json.id+"' />";
            return htm;
        }
        /**组装操作选项*/
        function makeOption1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=editItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
            hs+="<li style='height:25px' onclick=delItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
            hs+="<li style='height:25px' onclick=copyItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >复制</li>";
            hs+="<li style='height:25px' onclick=renameItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >重命名</li>";
            hs+="<li style='height:25px' onclick=toFolder('"+json.id+"') value='"+json.id+"' doaction=\"look\" >移动</li>";
            hs+="<li style='height:25px' onclick=listingItem('"+json.id+"','"+json.isFlag+"') value='"+json.id+"' doaction=\"look\" >立即刊登</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }

        function  refreshTable(){
            $("#itemTable").selectDataAfterSetParm({});
        }

        function toFolder(idStr){
            var url=path+"/ajax/selfFolder.do?folderType=modelFolder";
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
                                        for(var i =0;i<iwins.parent.document.getElementsByName("folderid").length;i++){
                                            if(iwins.parent.document.getElementsByName("folderid")[i].checked){
                                                folderid = iwins.parent.document.getElementsByName("folderid")[i].value;
                                            }
                                        }
                                        if(folderid==""){
                                            alert("请选择文件夹！");
                                            return false;
                                        }
                                        var url = path + "/ajax/shiftModelToFolder.do?idStr=" + idStr+"&folderid="+folderid;
                                        $().invoke(url, {},
                                                [function (m, r) {
                                                    alert(r);
                                                    Base.token();
                                                    onloadTable(loadurl);
                                                },
                                                    function (m, r) {
                                                        alert(r);
                                                        Base.token();
                                                    }]
                                        );

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
        //移动到文件夹
        function shiftToFolder(obj) {
            var item = $("input[name='modelid']");
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
            toFolder(idStr);
        }
    function listing(obj){
        var item = $("input[name='modelid']");
        var idStr = "";
        var isab = false;
        for (var i = 0; i < item.length; i++) {
            if ($(item[i])[0].checked) {
                idStr += $($(item[i])[0]).attr("val") + ",";
                if($($(item[i])[0]).attr("isFlag")=="Success"){
                    isab=true;
                    break;
                }
            }
        }
        if(isab){
            alert("您选择的商品有已成功刊登，请检查！");
            return;
        }
        if(idStr==""){
            alert("请选择立即刊登的商品！");
            return ;
        }

        listingItem(idStr,"");
    }

    function listingItem(idStr,isFlag){
        if(isFlag=="Success"){
            $.dialog.confirm("该商品已成功刊登，你确定要再次刊登吗？",function(){
                var url = path+"/ajax/listingItem.do?id="+idStr;
                $().invoke(url, {},
                        [function (m, r) {
                            alert(r);
                            onloadTable(loadurl);
                        },
                            function (m, r) {
                                alert(r);
                            }]
                );
            },null);
        }else{
            var url = path+"/ajax/listingItem.do?id="+idStr;
            $().invoke(url, {},
                    [function (m, r) {
                        alert(r);
                        onloadTable(loadurl);
                    },
                        function (m, r) {
                            alert(r);
                        }]
            );
        }


    }

    function onselectAlls(obj){
        var item = $("input[name='modelid']");
        if($(obj).prop("checked")){
            for (var i = 0; i < item.length; i++) {
                $($(item[i])[0]).prop("checked",true);
            }
        }else{
            for (var i = 0; i < item.length; i++) {
                $($(item[i])[0]).prop("checked",false);
            }
        }
    }
    function changeSelect(obj){
        var item = $("input[name='modelid']");
        var idStr = "";
        var isab = false;
        for (var i = 0; i < item.length; i++) {
            if ($(item[i])[0].checked) {
                idStr += $($(item[i])[0]).attr("val") + ",";
            }
        }
        if(idStr==""){
            alert("请选择立即刊登的商品！");
            return ;
        }
        if($(obj).val()=="del"){//删除
            delItem(idStr);
        }else if($(obj).val()=="copy"){//复制
            copyItem(idStr);
        }else if($(obj).val()=="rename"){//重命名
            renameItem(idStr)
        }
    }
    function copyItem(idStr){
        var url=path+"/ajax/selfEbayAccount.do";
        $().invoke(url,{},
                [function(m,r){
                    var htmlstr = "<div>选择Ebay账号：</div>";
                    htmlstr += "<div>";
                    for(var i = 0;i < r.length;i++){
                        htmlstr+="<div><input type='radio' name='ebayAccount' value='"+r[i].id+"'/>"+r[i].ebayName+"</div>";
                    }
                    htmlstr += "</div>";
                    var editPage = $.dialog({title: '选择Ebay账号',
                        content: htmlstr,
                        icon: 'succeed',
                        width: 400,
                        button: [
                            {
                                name: '确定',
                                callback: function (iwins, enter) {
                                    var ebayaccount = "";
                                    for(var i =0;i<iwins.parent.document.getElementsByName("ebayAccount").length;i++){
                                        if(iwins.parent.document.getElementsByName("ebayAccount")[i].checked){
                                            ebayaccount = iwins.parent.document.getElementsByName("ebayAccount")[i].value;
                                        }
                                    }
                                    if(ebayaccount==""){
                                        alert("请选择Ebay账号！");
                                        return false;
                                    }
                                    var url = path + "/ajax/copyItem.do?ids=" + idStr+"&ebayaccount="+ebayaccount;
                                    $().invoke(url, {},
                                            [function (m, r) {
                                                alert(r);
                                                Base.token();
                                                onloadTable(loadurl);
                                            },
                                                function (m, r) {
                                                    alert(r);
                                                    Base.token();
                                                }]
                                    );

                                }
                            }
                        ]
                    });
                },
                    function(m,r){
                        alert(r);
                    }]
        )
    }
    //删除范本
    function delItem(idStr){
        $.dialog.confirm("你确定要删除选择的范本？",function(){
            var url = path+"/ajax/delItem.do?ids="+idStr;
            $().invoke(url, {},
                    [function (m, r) {
                        alert(r);
                        Base.token();
                        onloadTable(loadurl);
                    },
                        function (m, r) {
                            Base.token();
                            alert(r);
                        }]
            );
        },null);
    }
    //重命名
    function renameItem(idStr){
        var htmlstr = "<div>请输入名称：<input type='text' name='fileName' id='fileName'/></div>";
        $.dialog({title: '请输入名称',
            content: htmlstr,
            icon: 'succeed',
            width: 400,
            button: [
                {
                    name: '确定',
                    callback: function (iwins, enter) {
                        var fileName = iwins.parent.document.getElementsByName("fileName")[0].value;
                        if(fileName==""){
                            alert("请输入名称！");
                            return false;
                        }
                        var url = path + "/ajax/rename.do?ids=" + idStr+"&fileName="+fileName;
                        $().invoke(url, {},
                                [function (m, r) {
                                    alert(r);
                                    Base.token();
                                    onloadTable(loadurl);
                                },
                                    function (m, r) {
                                        alert(r);
                                        Base.token();
                                    }]
                        );

                    }
                }
            ]
        });
    }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>范本</b></div>
    <div class="a_bal"></div>
    <div class="new">
        <div class="new_tab_ls" id="tab">

        </div>
        <div class=Contentbox id="Contentbox">
            <div class="new_usa" style="margin-top:20px;">
                <li class="new_usa_list"><span class="newusa_i">选择国家：</span>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="311"><span class="newusa_ici_1"><img src="<c:url value ='/img/usa_1.png'/> ">美国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="310"><span class="newusa_ici_1"><img src="<c:url value ='/img/UK.jpg'/> ">英国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="298"><span class="newusa_ici_1"><img src="<c:url value ='/img/DE.png'/> ">德国</span></a>
                    <a href="javascript:void(0)" onclick="selectCounty(this)" value="291"><span class="newusa_ici_1"><img src="<c:url value ='/img/AU.jpg'/> ">澳大利亚</span></a>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">选择账号：</span>
                    <a href="javascript:void(0)" onclick="selectEbayAccount(this)"  value=""><span class="newusa_ici">全部</span></a>
                    <c:forEach items="${ebayList}" var="ebay">
                        <a href="javascript:void(0)" onclick="selectEbayAccount(this)" value="${ebay.id}"><span class="newusa_ici_1">${ebay.ebayNameCode}</span></a>
                    </c:forEach>
                </li>
                <li class="new_usa_list">
                    <span class="newusa_i">刊登类型：</span>

                    <a href="javascript:void(0)" onclick="selectListType(this)" value=""><span class="newusa_ici">全部</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="FixedPriceItem"><span class="newusa_ici_1">固价</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="2"><span class="newusa_ici_1">多属性</span></a>
                    <a href="javascript:void(0)" onclick="selectListType(this)" value="Chinese"><span class="newusa_ici_1">拍卖</span></a>
                </li>
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

                        <span class="newusa_ici_del_in">
                            <input type="checkbox" onclick="onselectAlls(this);" name="checkbox" id="checkbox"/>
                        </span>

                        <div class="numlist">
                            <div class="ui-select" style="margin-top:1px; width:10px">
                                <select onchange="changeSelect(this)">
                                    <option value="">请选择</option>
                                    <option value="del">删除</option>
                                    <option value="copy">复制</option>
                                    <option value="rename">重命名</option>
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
                        <span class="newusa_ici_del" onclick="listing(this)" id="listing">立即刊登</span>
                        <span class="newusa_ici_del"  onclick="addTabRemark();">管理文件夹</span></div>
                    <div class="tbbay"><a data-toggle="modal" href="#myModal" class=""  onclick="addItem()">新增范本</a></div>
                </div>
            <%--<iframe src="/xsddWeb/itemList.do?1=1" id="listing_frame" height="1000px;" frameborder="0" width="100%">

            </iframe>--%>
                <div style="width: 100%;float: left;height: 5px"></div>
                <div id="itemTable"></div>


        </div>

    </div>
</div>
</div>
</body>
</html>
