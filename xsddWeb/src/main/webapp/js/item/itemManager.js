/**
 * Created by Administrator on 2015/1/21.
 */

//----------------------------------------
$(document).ready(function(){
    initSearchInput()
    var url=path+"/ajax/selfFolder.do?folderType=modelFolder";
    $().invoke(url,{},
        [function(m,r) {
            var htmlstr = '<dt name="allProduct" class=new_tab_1 onclick="setTab(this)">所有范本</dt>'
                + '<dt name="listingProduct" class=new_tab_2 onclick="setTab(this)">有在线刊登</dt>'
                + '<dt name="product" class=new_tab_2 onclick="setTab(this)">无在线刊登</dt>'
                + '<dt name="timeProduct" class=new_tab_2 onclick="setTab(this)">定时</dt>';
            if (r != null) {
                for (var i = 0; i < r.length; i++) {
                    htmlstr += "<dt scop=\"tabRemark\" name='myFolder_" + i + "' val='" + r[i].id + "' class=new_tab_2 onclick='setTab(this)'>" + r[i].configName + "</dt>";
                }
            }
            $("#tab").html(htmlstr);
        },
            function(m,r){
                alert(r);
            }]
    );
    onloadTable(loadurl);
    if(localStorage.getItem("siteListStr")!=null){
        $("#li_countyselect").html(localStorage.getItem("siteListStr"));
    }
});
/**初始化select2框*/
function initSearchInput(bs){
    var _url="";
    var _map={};
    if(bs=='sku'){
        _url=path+"/informationType/ajax/loadOrgIdItemInformationList.do";
        _map={id:"sku",text:"sku"};
    }

    mySelect2I([{url:_url,doitAfterSelect:selectData,
        data:{currInputName:"content"},bs:"#_selectvalue",multiple:false,maping:_map}]);
}
/**点击后下原来的select也做出相应的变化*/
    function chageOldDom(obj){
    /*$(obj).parent().find("a[name='t_a']").each(function(i,d){
        $(d).find("span").attr("class","newusa_ici_1");
    });
    $(obj).find("span").attr("class","newusa_ici");
    $("select[name='selecttype']").val($(obj).attr("value"));*/

    initSearchInput($(obj).val());
    $("input[name='_selectvalue']").val('');
}


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
        if(name=="timeProduct"){
            $("#selectMoreType").append("<option value='delTimer'>&nbsp;&nbsp;取消定时</option>");
        }else{
            $("#selectMoreType option[value='delTimer']").remove();
        }
        loadurl=map.get(name);
    }else{
        $("#selectMoreType option[value='delTimer']").remove();
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
    if(selectType==null || selectType==''){alert("请选择查询条件!");return;}
    if($("#_selectvalue").select2("data")==null){return;}
    var selectValue=$("#_selectvalue").select2("data").text;

    //$("input[name='selectvalue']").val(_searchV);
   // var selectValue = $("input[name='selectvalue']").val();
    if(selectType==""){
        alert("请选择查询类型！");
        return;
    }
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
    initSearchInput();
    var urls = loadurl;
    if(urls.indexOf(selectQuery)>0) {
        urls = urls.replace(selectQuery,"");
    }
    $("select[name='selecttype']").val("");
    $("input[name='selectvalue']").val("");
    loadurl = urls;
    onloadTable(loadurl);
    $("select[name='selecttype']").val('');
    $(obj).parent().find("a[name='t_a']").each(function(i,d){
        $(d).find("span").attr("class","newusa_ici_1");
    });
    $(obj).find("span").attr("class","newusa_ici");
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
var OrderGetOrders;
function addTabRemark(){
    var url=path+"/order/selectTabRemark.do?folderType=modelFolder";
    OrderGetOrders=openMyDialog({title: '选择文件夹',
        content: 'url:'+url,
        icon: 'tips.gif',
        width:800
    });
}
//--------------------------
function refleshTabRemark(folderType){
    var url=path+"/order/refleshTabRemark.do?folderType="+folderType;
    $().invoke(url,null,
        [function(m,r){
            var div=document.getElementById("tab");
            var remarks=$(div).find("dt[scop=tabRemark]");
            for(var i=0;i<remarks.length;i++){
                $(remarks[i]).remove();
            }
            var htm="";
            for(var i=0;i< r.length;i++){
                htm+="<dt scop=\"tabRemark\" name='myFolder_" + i + "' val='" + r[i].id + "' class=new_tab_2 onclick='setTab(this)'>" + r[i].configName + "</dt>";
            }
            $(div).append(htm);
            Base.token;
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );
}

var returnItem ="";
function addItem(){
    location = path+"/addItem.do";
}

function editItem(id){
    document.location = path+"/editItem.do?id="+id;
}
function getSiteImg(json){
    var html='<img title="'+json.siteName+'" src="'+path+json.siteImg+'"/>';
    return html;
}
function getDuration(json){

    var html="";
    if(json.itemId!=null&&json.itemId!="") {
        if(json.listingduration.indexOf("Days_")!=-1){
            html = "<a target='_blank' href='" + getSiteUrl("2",json.site) + json.itemId + "'>" + json.listingduration.substr(json.listingduration.indexOf("_")+1,json.listingduration.length) + "</a>"
        }else{
            html = "<a target='_blank' href='" + getSiteUrl("2",json.site) + json.itemId + "'>" + json.listingduration + "</a>"
        }
    }else{
        if(json.listingduration.indexOf("Days_")!=-1){
            html=json.listingduration.substr(json.listingduration.indexOf("_")+1,json.listingduration.length);
        }else{
            html=json.listingduration;
        }

    }
    return html;
}

var descStatic
function orderList(obj){
    var des = "";
    if($(obj).attr("val")=="0"){//默认状态为降序，之前为升序
        $(obj).attr("val","1");
        des="desc";
    }else{
        $(obj).attr("val","0");
        des="asc";
    }
    descStatic=$(obj).attr("val");
    var desc = $(obj).attr("colu");
    onloadTable(loadurl+"&descStr="+desc+"&desStr="+des);
    $("#itemTable").find("span[colu='"+desc+"']").attr("val",descStatic);

}
function getDefaultTimer(json){
    return defaultTimer.replace(" ","<br>");
}
function onloadTable(urls){
    var clod=null;
    if(urls.indexOf("flag=3")!=-1){
        clod = [
            {title:"选择",name:"selectName",width:"3%",align:"left",format:makeOption0},
            {title:"图片",name:"pucURLS",width:"6%",align:"center",format:picUrl},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='item_name' val='0'>名称/SKU</span>",name:"itemName",width:"26%",align:"left",format:itemName},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='Site' val='0'>站点</span>",name:"siteName",width:"5%",align:"center",format:getSiteImg},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='ListingType' val='0'>刊登类型</span>",name:"listingtype",width:"6%",align:"center",format:listingType},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='ebay_account' val='0'>eBay账户</span>",name:"ebayaccountname",width:"12%",align:"left"},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='ListingDuration' val='0'>刊登天数</span>",name:"listingduration",width:"8%",align:"center",format:getDuration},
            {title:"预设时间",name:"defaultTimer",width:"8%",align:"center"},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='is_flag' val='0'>在线</span>",name:"isFlag",width:"5%",align:"center",format:itemstatus},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"option1",width:"4%",align:"left",format:makeOption1}
        ];
    }else{
        clod = [
            {title:"选择",name:"selectName",width:"3%",align:"left",format:makeOption0},
            {title:"图片",name:"pucURLS",width:"6%",align:"center",format:picUrl},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='item_name' val='0'>名称/SKU</span>",name:"itemName",width:"26%",align:"left",format:itemName},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='Site' val='0'>站点</span>",name:"siteName",width:"5%",align:"center",format:getSiteImg},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='ListingType' val='0'>刊登类型</span>",name:"listingtype",width:"6%",align:"center",format:listingType},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='ebay_account' val='0'>eBay账户</span>",name:"ebayaccountname",width:"12%",align:"left"},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='ListingDuration' val='0'>刊登天数</span>",name:"listingduration",width:"8%",align:"center",format:getDuration},
            {title:"<span onclick='orderList(this)' style='cursor: pointer;' colu='is_flag' val='0'>在线</span>",name:"isFlag",width:"5%",align:"center",format:itemstatus},
            {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"option1",width:"4%",align:"left",format:makeOption1}
        ];
    }
    $("#itemTable").initTable({
        url:urls,
        columnData:clod,
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        isrowClick: true,
        rowClickMethod: function (obj,o){

            if($(event.target).prop("type")=="checkbox"){
                return;
            }
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
    if(json.listingWay=="1"&&(json.itemId==null||json.itemId=="")){
        htm="<img title='定时刊登' name='statusimg' src='"+path+"/img/timer.png'>";
    }else{
        if(json.isFlag=="Success"){
            htm="<img name='statusimg' src='"+path+"/img/new_yes.png'>";
        }else{
            htm="<img name='statusimg' src='"+path+"/img/new_no.png'>";
        }
    }
    if(json.sl!=null){
        htm+="<img src='"+path+"/img/Tip.ico' title='"+json.sl.eventdesc+"'/>"
    }
    return htm;
}
function listingType(json){
    var htm="";
    var titlestr= "";
    if(json.listingtype=="2"){
        htm="lx.png";
        titlestr="多属性";
    }else if(json.listingtype=="Chinese"||json.listingtype=="chinese"){
        htm="bids.png";
        titlestr="拍卖";
    }else if(json.listingtype=="FixedPriceItem"||json.listingtype=="fixedpriceitem"){
        htm="buyit.png";
        titlestr="固价";
    }

    return "<img width='16' title='"+titlestr+"' height='16' src='"+path+"/img/"+htm+"'>";
}
function itemName(json){
    var htm="<span style='color:#5F93D7;word-break:break-all;'><a style='line-height: 19px;' onclick='editItem("+json.id+")' href='javascript:void(0)'>"+json.itemName+"</a></span></br><span style='color:#8BB51B;'>"+json.sku+"</span>";
    if(json.itemId!=null&&json.itemId!=""){
        htm="<span style='color:#5F93D7;word-break:break-all;'><a style='line-height: 19px;' onclick='editItem("+json.id+")' href='javascript:void(0)'>"+json.itemName+"<a></span></br><a target='_blank' href='"+getSiteUrl("2",json.site)+json.itemId+"'><span style='color:#8BB51B;'>"+json.sku+"</span></a>";
    }
    var remark = "";
    if(json.remark!=null&&json.remark!=""){
        if(json.remark.length>25){
            remark = json.remark.substr(0,25)+"......";
        }else{
            remark = json.remark;
        }
        htm+="</br><span class='newdf' style='margin-top: -6px;' title='"+json.remark+"'>备注："+remark+"</span>";
    }else{
        htm+="</br><span class='newdf' title='' style='display: none;margin-top: -6px;'></span>";
    }
    return htm;
}
function picUrl(json){
    var htm="<img width='50px' height='50px' src='"+chuLiPotoUrl(json.galleryURL)+"'>";
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
    if(json.isFlag=="Success"){
        hs+="<li style='height:25px' onclick=editItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
        hs+="<li style='height:25px' onclick=delItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
        hs+="<li style='height:25px' onclick=copyItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >复制</li>";
        hs+="<li style='height:25px' onclick=renameItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >重命名</li>";
        hs+="<li style='height:25px' onclick=toFolder('"+json.id+"') value='"+json.id+"' doaction=\"look\" >移动</li>";
    }else{
        if(json.listingWay=="1"){
            hs+="<li style='height:25px' onclick=delTradingTimer('"+json.id+"') value='"+json.id+"' doaction=\"look\" >取消定时</li>";
        }else{
            hs+="<li style='height:25px' onclick=editItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
            hs+="<li style='height:25px' onclick=delItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
            hs+="<li style='height:25px' onclick=copyItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >复制</li>";
            hs+="<li style='height:25px' onclick=renameItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >重命名</li>";
            hs+="<li style='height:25px' onclick=toFolder('"+json.id+"') value='"+json.id+"' doaction=\"look\" >移动</li>";
            hs+="<li style='height:25px' onclick=listingItem('"+json.id+"','"+json.isFlag+"') value='"+json.id+"' doaction=\"look\" >立即刊登</li>";
            hs+="<li style='height:25px' onclick=selectTimers('"+json.id+"') value='"+json.id+"' doaction=\"look\" >定时刊登</li>";
        }
    }
    /*if(json.isFlag!="Success"&&json.listingWay=="0"){
        hs+="<li style='height:25px' onclick=editItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
        hs+="<li style='height:25px' onclick=delItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
        hs+="<li style='height:25px' onclick=copyItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >复制</li>";
        hs+="<li style='height:25px' onclick=renameItem('"+json.id+"') value='"+json.id+"' doaction=\"look\" >重命名</li>";
        hs+="<li style='height:25px' onclick=toFolder('"+json.id+"') value='"+json.id+"' doaction=\"look\" >移动</li>";
        hs+="<li style='height:25px' onclick=selectTimers('"+json.id+"') value='"+json.id+"' doaction=\"look\" >定时刊登</li>";
        hs+="<li style='height:25px' onclick=listingItem('"+json.id+"','"+json.isFlag+"') value='"+json.id+"' doaction=\"look\" >立即刊登</li>";
    }
    if(json.listingWay=="1"){
        hs+="<li style='height:25px' onclick=delTradingTimer('"+json.id+"') value='"+json.id+"' doaction=\"look\" >取消定时</li>";
    }*/
    hs+="<li style='height:25px' onclick=addRemark('"+json.id+"','"+json.remark+"') value='"+json.id+"' doaction=\"look\" >备注</li>";
    var pp={"liString":hs,"setDivStyle":"padding-top:2px;"};
    return getULSelect(pp);
}

//备注
function addRemark(id,remark){
    if(remark==undefined){
        remark="";
    }else{
        remark = $("input[type='checkbox'][name='modelid'][val='" + id + "']").parent().parent().find("td").eq(2).find(".newdf").text().substr(3);
    }
    var tent = "<div class='textarea'>备注：<textarea cols='30' rows='5' id='centents' >"+remark+"</textarea></div>";
    var editPage = openMyDialog({title: '备注',
        content: tent,
        icon: 'tips.gif',
        width: 400,
        button: [
            {
                name: '确定',
                callback: function (iwins, enter) {
                    var reason = "";
                    /*if (iwins.parent.document.getElementById("centents").value == "") {
                     alert("备注必填！");
                     return false;
                     } else {*/
                    //alert(iwins.parent.document.getElementById("centents").selectedIndex);
                    reason = iwins.parent.document.getElementById("centents").value;
                    //alert(reason);
                    var url = path + "/ajax/addItemRemark.do?id=" + id+"&remark="+reason;
                    $().invoke(url, {},
                        [function (m, r) {
                            for(var i=0;i< r.length;i++){
                                var tld  = r[i];
                                var remark = "";
                                if(tld.remark!=null&&tld.remark!=""){
                                    if(tld.remark.length>25){
                                        remark = tld.remark.substr(0,25)+"......";
                                    }else{
                                        remark = tld.remark;
                                    }
                                    if(remark==null||remark==""||remark.length==0){
                                        $("input[type='checkbox'][name='modelid'][val='"+tld.id+"']").parent().parent().find("td").eq(2).find(".newdf").hide();
                                    }else {
                                        $("input[type='checkbox'][name='modelid'][val='" + tld.id + "']").parent().parent().find("td").eq(2).find(".newdf").show();
                                        $("input[type='checkbox'][name='modelid'][val='" + tld.id + "']").parent().parent().find("td").eq(2).find(".newdf").html("备注：" + remark);
                                        $("input[type='checkbox'][name='modelid'][val='" + tld.id + "']").parent().parent().find("td").eq(2).find(".newdf").prop("title", remark);
                                    }
                                }else{
                                    $("input[type='checkbox'][name='modelid'][val='"+tld.id+"']").parent().parent().find("td").eq(2).find(".newdf").hide();
                                }
                            }
                        },
                            function (m, r) {
                                alert(r);
                                Base.token();
                            }]
                    );
                }
                //}
            }
        ]
    });
}
function  refreshTable(){
    var selectValue = $("input[type='text'][name='selectvalue']").val();
    var param={};
    if(selectValue!=null&&selectValue!=""){
        param={"queryValue":selectValue};
    }
    $("#itemTable").selectDataAfterSetParm(param);
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
            var editPage = openMyDialog({title: '选择移动到的文件夹',
                content: htmlstr,
                icon: 'tips.gif',
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
                    var su = r.su;
                    var er = r.er;
                    var tent = "<div>";
                    if(su!=null){
                        for(var i = 0;i<su.length;i++){
                            tent+="<div>"+su[i]+"</div>";
                        }
                    }
                    if(er!=null){
                        for(var i = 0;i<er.length;i++){
                            tent+="<div>"+er[i]+"</div>";
                        }
                    }
                    tent+="</div>";
                    var editPage = openMyDialog({title: '刊登明细',
                        content: tent,
                        icon: 'tips.gif',
                        width: 400
                    });
                    onloadTable(loadurl);
                },
                    function (m, r) {
                        alert(r);
                    }],{isConverPage:true}
            );
        },null);
    }else{
        var url = path+"/ajax/listingItem.do?id="+idStr;
        $().invoke(url, {},
            [function (m, r) {
                var su = r.su;
                var er = r.er;
                var tent = "<div>";
                if(su!=null){
                    for(var i = 0;i<su.length;i++){
                        tent+="<div>"+su[i]+"</div>";
                    }
                }
                if(er!=null){
                    for(var i = 0;i<er.length;i++){
                        tent+="<div>"+er[i]+"</div>";
                    }
                }
                tent+="</div>";
                var editPage = openMyDialog({title: '刊登明细',
                    content: tent,
                    icon: 'tips.gif',
                    width: 400
                });
                onloadTable(loadurl);
            },
                function (m, r) {
                    alert(r);
                }],{isConverPage:true}
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
        alert("请选择商品！");
        $(obj).val("");
        return ;
    }
    if($(obj).val()=="del"){//删除
        delItem(idStr);
    }else if($(obj).val()=="copy"){//复制
        copyItem(idStr);
    }else if($(obj).val()=="rename"){//重命名
        renameItem(idStr);
    }else if($(obj).val()=="editMoreItem"){
        editMoreItem(idStr);
    }else if($(obj).val()=="delTimer"){
        delTradingTimer(idStr);
    }else if($(obj).val()=="remark"){
        addRemark(idStr);
    }else if($(obj).val()=="timerListingItem"){
        selectTimers(idStr);
    }
    $(obj).val("");
}
var editMorepage
function editMoreItem(idStr){
    var urls = path+'/editMoreItem.do?ids='+idStr;
    editMorepage = openMyDialog({title: '批量修改范本',
        content: 'url:'+urls,
        icon: 'tips.gif',
        width:1000,
        height:600,
        lock:true
    });
}
//复制
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
            var editPage = openMyDialog({title: '选择Ebay账号',
                content: htmlstr,
                icon: 'tips.gif',
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
                //alert(r);
                Base.token();
                //onloadTable(loadurl);
                for(var i=0;i<r.length;i++){
                    var id = r[i];
                    $("input[type='checkbox'][name='modelid'][val='"+id+"']").parent().parent().prop("id",id);
                    $("#"+id).hide(1500);
                    $("#"+id).slideUp(1500);
                }
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
    var name = "";
    if(idStr.indexOf(",")==-1){
        name = $("input[type='checkbox'][name='modelid'][val='" + idStr + "']").parent().parent().find("td").eq(2).find("span").eq(0).text();
    }
    var htmlstr = "<div>请输入名称：<input type='text' name='fileName' size='40' id='fileName' value='"+name+"' style='border: 1px solid #cccccc;border-radius: 4px;'/></div>";
    openMyDialog({title: '请输入名称',
        content: htmlstr,
        icon: 'tips.gif',
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
                            var id = null;
                            if(idStr.indexOf(",")!=-1){
                                id = idStr.substr(0,idStr.length-1).split(",");
                            }else{
                                id = idStr.substr(0,idStr.length).split(",");
                            }
                            for(var i=0;i<id.length;i++){
                                $("input[type='checkbox'][name='modelid'][val='"+id[i]+"']").parent().parent().prop("id",id[i]);
                                $("#"+id[i]+" td:eq(2) span:eq(0)").html('<a style="line-height: 19px;" onclick="editItem('+id[i]+')" href="javascript:void(0)">'+fileName+'</a>')
                            }
                            /*Base.token();
                             onloadTable(loadurl);*/
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
var siteListPage
function selectSiteList(obj){
    var siteid = "";
    $(obj).parent().find("a").each(function(i,d){
        if($(d).attr("value")!=null&&$(d).attr("value")!=""){
            siteid+=$(d).attr("value")+",";
        }
    });
    var urls = path+'/selectSiteList.do?siteidStr='+siteid;
    siteListPage = openMyDialog({title: '选择站点',
        content: 'url:'+urls,
        icon: 'tips.gif',
        width:500,
        lock:true
    });
}

function delTradingTimer(itemids){
    var url = path + "/ajax/delTradingTimer.do?itemids=" + itemids;
    $().invoke(url, {},
        [function (m, r) {
            alert(r);
            Base.token();
            onloadTable(loadurl);
        },
            function (m, r) {
                alert(r);
                /*Base.token();*/
            }]
    );
}

var timerPage
function selectTimers(obj){
    $("#idStr").val(obj)
    var urls = path+'/selectTimer.do';
    timerPage = openMyDialog({title: '选择定时时间',
        content: 'url:'+urls,
        icon: 'tips.gif',
        width:500,
        lock:true
    });
    //saveData(this,'timeSave')
}
//重写方法，不可改变方法名称
function saveDatas(a,b){
    var url = path+"/ajax/timerListingItem.do?id="+$("#idStr").val()+"&timerStr="+$("#timerListing").val();
    $().invoke(url, {},
        [function (m, r) {
            var id=null;
            if($("#idStr").val().indexOf(",")==-1){
                id = $("#idStr").val().split(",");
            }else{
                id = $("#idStr").val().substr(0,$("#idStr").val().length-1).split(",");
            }
            if(a=="timeSave"){
                var str = "";
                for(var i=0;i<id.length;i++){
                    for(var j=0;j< r.length;j++){
                        if(id[i]==r[j].id){
                            $("input[type='checkbox'][name='modelid'][val='"+id[i]+"']").parent().parent().prop("id",id[i]);
                            $("#"+id[i]).find("img[name='statusimg']").attr("src",path+"/img/timer.png");
                            $("input[type='checkbox'][name='modelid'][val='" + id[i] + "']").parent().parent().find("td").last().find("ul").append('<li><a href="javascript:void(0)" onclick="delTradingTimer('+id[i]+')" value="'+id[i]+'" doaction="look">取消定时</a></li>');
                            $("input[type='checkbox'][name='modelid'][val='" + id[i] + "']").parent().parent().find("td").last().find("ul").find("li").eq(4).remove();
                            str+=r[j].sku+"定时设置成功！</br>"
                        }
                    }
                }
                if(str!=""){
                    alert(str);
                }
            }
        },
            function (m, r) {
                alert(r);
            }],{isConverPage:true}
    );
}