<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/4
  Time: 15:54
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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/css/jqzoom/jqzoom.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jqzoom/jquery.jqzoom.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
    <%--<script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/dialogs/image/imageextend.js" /> ></script>--%>
    <script type="text/javascript" src=<c:url value ="/js/zeroclipboard/dist/ZeroClipboard.min.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/item/addItemInfomation.js" /> ></script>
 <%--   <script type="text/javascript" src=<c:url value="/js/item/addItem.js"/>></script>
    <script type="text/javascript" src=<c:url value="/js/item/addItem2.js"/>></script>--%>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        var add=0;

                 /*       }
                        var addPictureId=document.getElementById("addPictureId");
                        var div=$(addPictureId).find("div[id=vspic]");
                        if(div.length>0) {
                            $("#lianjie").remove();
                        }
                        zeroclipInit();
                        Base.token();

                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        *//*    $(".jqzoom").jqueryzoom();*//*
        });*/

        function bigfont(id){
           /* $("#"+id).jqueryzoom({
                xzoom: 1050,		//zooming div default width(default width value is 200)
                yzoom: 700,		//zooming div default width(default height value is 200)
                offset: 1,		//zooming div default offset(default offset value is 10)
                position: "right",  //zooming div position(default position value is "right")
                preload: 10     // 1 by default

            });*/
            var url=path+"/information/bigfont.do?pictureUrl="+id;
            itemInformation=openMyDialog({title: '',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:600,
                lock:true,
                zIndex:2000
            });
        }
        function addToli(name){
            var spans=$("#addRemark").find("span");
            for(var i=0;i<spans.length;i++){
                var span=spans[i].innerHTML;
                if(name==span){
                    return;
                }
            }
            var lable="<a href='javascript:void(0)' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class=\"icon-remove-sign\" style='margin-right: 2px;'></i><span >"+name+"</span><input type='hidden' name='label' value='"+name+"'></a>"
            $("#kk").before(lable);
        }
        function addAttrabute() {
            var names=$("input[name=attrName]");
            var values=$("input[name=attrValue]");
            if(names){
                add=names.length+1;
            }else{
                add = add + 1;
            }
            var addAttr1 = " <tr><td>名称</td><td>值</td></tr><tr><td><input type=\"text\" name=\"attrName\" class='form-controlsd' /></td><td><input type=\"text\" name=\"attrValue\" class='form-controlsd'/><a href=\"javascript:void()\" onclick=\"removeAttrabute(this)\" >移除</a></td></tr>"
            var addAttr2 = "<tr><td><input type=\"text\" name=\"attrName\" class='form-controlsd' /></td><td><input type=\"text\" name=\"attrValue\" class='form-controlsd' /><a href=\"javascript:void()\" onclick=\"removeAttrabute(this)\" >移除</a></td></tr>";
            var table = document.getElementById("attrabute1");
            if (add == 1) {
                table.innerHTML = addAttr1;
            }
            if (add > 1) {
                console.debug($(addAttr2));
                $(table).append($(addAttr2));
            }
        }
        function removeAttrabute(th){
            add=add-1;
            var tr=$(th).parent().parent();
            var table=document.getElementById("attrabute1");
            if(add<=0){
                add=0;
                table.innerHTML="";
            }else{
                tr.remove();
            }
        }
        var addsupplier=false;
        function addSupplier(){
            addsupplier=!addsupplier;
            var tr="<tr><td>ID:</td><td><input class='form-controlsd' name=\"supplierId\"/></td></tr>" +
            "<tr><td>供应商:</td><td>" +
            "<select name=\"supplierName\">" +
            "<option>爱酷客</option>" +
            "</select>" +
            "</td></tr>" +
            "<tr><td>价格:</td><td><input class='form-controlsd' name=\"supplierPrice\"/></td><td>" +
            "<select name=\"supplierCurrency\">" +
            "<option>AUD</option>" +
            "</select></td></tr>" +
            "<tr><td>厂商SKU</td><td><input class='form-controlsd' name=\"supperSku\"/></td></tr>" +
            "<tr><td>备注</td><td><textarea style='height: 100px;' class='form-controlsd' name=\"supplierRemark\"></textarea></td></tr>";
            var table=document.getElementById("supper");
            if(addsupplier){
                table.innerHTML=tr;
            }else{
                table.innerHTML="";
            }
        }
        function closedialog() {
            /*window.parent.location.reload();*/
            W.itemInformation.close();
        }

        function submitCommit(){
            if(!$("#informationForm").validationEngine("validate")){
                return;
            }
            var names=$("input[name=attrName]");
            var values=$("input[name=attrValue]");
            var pictures=$("input[name=Picture]");
            for(var i=0;i<names.length;i++){
                $(names[i]).attr("name","attrName["+i+"]");
                $(values[i]).attr("name","attrValue["+i+"]");
            }
            for(var i=0;i<pictures.length;i++){
                $(pictures[i]).attr("name","Picture["+i+"]");
            }
            var remarks=$("input[name=label]");
            var remark="";
            if(remarks.length!=0){
                for(var i=0;i<remarks.length;i++){
                    if(i==(remarks.length-1)){
                        remark+=$(remarks[i]).val();
                    }else{
                        remark+=$(remarks[i]).val()+",";
                    }
                }
            }
            $("#remark123").val(remark);
            $("#discription").val(ue.getContent());
            var url=path+"/information/ajax/saveItemInformation.do?";
            var data=$("#informationForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        var idvalue=$("#id").val();
                        if(idvalue&&idvalue!=""){
                            var table=W.document.getElementById("ItemInformationListTable");
                            var inputs=$(table).find("input[scop1=selected]");
                            for(var i=0;i<inputs.length;i++){
                                var tr=$(inputs[i]).parent().parent();
                                var tds=$(tr).find("td");
                                var td1=$("#addPictureId").find("td");
                                var img1=$(td1[0]).find("img[scop=img]")
                                for(var j=0;j<tds.length;j++){
                                    if(j==1){
                                        var img=$(tds[j]).find("img");
                                        if(img1.length>0){
                                            $(img).attr("src",$(img1).attr("src"));
                                        }else{
                                            $(img).attr("src","http://i.ebayimg.sandbox.ebay.com/00/s/NjAwWDgwMA==/$(KGrHqRHJEkFJ2m+ipUVBUSMpPJdmw~~60_1.JPG");
                                        }
                                    }else if(j==5){
                                        var img=$(tds[j]).find("img");
                                        if(img1.length>0){
                                            $(img).attr("src",path+"/img/new_yes.png");
                                        }else{
                                            $(img).attr("src",path+"/img/new_no.png");
                                            $(img).attr("title","该商品没有图片");
                                        }
                                    }else if(j==2){
                                        var value=$("#sku").val();
                                        var comment=$("#comment").val();
                                        if(comment||comment!=""){
                                            tds[j].innerHTML="<span style='color: #8BB51B;'>"+value+"</span><br/><span class=\"newdf\" style='border-radius: 3px;' title=\""+comment+"\">备注："+comment+"</span>";
                                        }else{
                                            tds[j].innerHTML="<span style='color: #8BB51B;'>"+value+"</span>";
                                        }

                                    }
                                    else if(j==3){
                                        var value=$("#informationName").val();
                                        tds[j].innerHTML="<span style='color: #1a93c2'>"+value+"</span>";
                                    }
                                    else if(j==4){
                                        var as=$("#addRemark").find("a");
                                        var value="";
                                        for(var x=0;x<as.length;x++){
                                            var span=$(as[x]).find("span");
                                            value+=span[0].innerHTML+",";
                                        }
                                        tds[j].innerHTML=value.substring(0,value.length-1);
                                    }
                                }
                            }
                        }else{
                            W.refreshTable();
                        }

                        W.loadRemarks();
                        W.itemInformation.close();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        var adddiscription;
        var discription;
        var discriptionFlag=false;

        function addDiscription(){
            var trs=$("tr[scop=tr]");
            var name=$("#informationName").val();
            if(trs.length>0){
                alert("描述已经存在,请编辑");
                return;
            }
            var url=path+"/information/addDiscription.do?name="+name;
            adddiscription=openMyDialog({title: '添加描述',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:700,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
        function removeDiscription(ph){
            var tr=$(ph).parent().parent();
            tr.remove();
        }
        function editDiscription(ph){
            discription=$(ph).parent().parent();
            discriptionFlag=true;
            var name=$("#informationName").val();
            var descriptionName=$("#descriptionName").val();
            var url=path+"/information/addDiscription.do?descriptionName="+descriptionName+"&name="+name;
            adddiscription=openMyDialog({title: '编辑描述',
                content: 'url:'+url,
                icon: 'succeed',
                width:800,
                height:700,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }

        function addpictureName(obj){
            var a=document.getElementById("apicUrls");
            $(a).click();
        }
        var afterUploadCallback = null;
        var sss;
        function addpicture(a){

            _sku =$("#sku").val();
            afterUploadCallback = {"imgURLS": addPictrueUrl};
            sss = a.id;
        }
        function nofind(){
            var img=event.srcElement;
            img.src="http://i.ebayimg.sandbox.ebay.com/00/s/NjAwWDgwMA==/$(KGrHqRHJEkFJ2m+ipUVBUSMpPJdmw~~60_1.JPG";
            img.onerror=null;
        }
        function addPictrueUrl(urls) {
            var divs=$("div[id=vspic]");
            var str = '';
            for (var i = 0; i < urls.length; i++) {
            /*    str+="<td class=\"spic\" style=\"margin-left: 20px;\">"+
                        "<div id=\"vspic\">" +
                        "<li bs="+i+" id='"+"linkbutton"+i+"'><a href=\"javascript:void(0)\"><input type=\"hidden\" name=\"Picture\" value='" + chuLiPotoUrl(urls[i].src.replace("@", ":")) + "'><img src=\"<c:url value ="/img/a1xl.png" />\" width=\"18\" height=\"18\"></a>" +
                        "<ul id='picul"+i+"'>" +
                        "<li><a href=\"javascript:void(0)\" onclick=\"removeThis(this)\">删除</a></li>" +
                        "<li><textarea style='display: none' id='"+"nowimg"+(i+divs.length)+"'>"+ chuLiPotoUrl(urls[i].src.replace("@", ":")) +"</textarea></li>" +
                        "<li><a href=\"javascript:void(0)\" id='"+"doCopy"+(i+divs.length)+"' data='"+(i+divs.length)+"' data-clipboard-target='nowimg"+(i+divs.length)+"' >复制链接</a></li>" +
                        "</ul>" +
                        "</li>" +
                        "</div>" +
                        "<div class=\"a1fd\"><a href=\"javascript:void(0)\" onclick=\"bigfont('"+ urls[i].src.replace("@", ":")+"')\"><img  src=\"<c:url value ="/img/a1fd.png" />\"></a></div>" +
                        "<img scop='img' onerror='nofind();'  src=\"" + chuLiPotoUrl(urls[i].src.replace("@", ":")) +"\" width=\"120\" height=\"110\"></td>";
*/
                var j=i;
                if(divs&&divs.length>0){
                    j=j+divs.length;
                }
                str+="<td class=\"spic\" style=\"margin-left: 20px;\">" +
                        "<div id=\"vspic\">" +
                        "<li bs="+j+" id='"+"linkbutton"+j+"'><a href=\"javascript:void(0)\"><img src=\"<c:url value ="/img/a1xl.png" />\" width=\"18\" height=\"18\"></a>" +
                        "<ul id='picul"+j+"'>" +
                        "<li><a href=\"javascript:void(0)\" onclick=\"removeThis(this)\">删除</a></li>" +

                        "<li><textarea style='display: none' id='"+"nowimg"+j+"'>"+chuLiPotoUrl(urls[i].src.replace("@", ":"))+"</textarea></li>" +
                        "<li><a href=\"javascript:void(0)\" id='"+"doCopy"+5+"' data='"+j+"' data-clipboard-target='nowimg"+j+"' >复制链接</a></li>" +
                        "</ul>" +
                        "</li>" +
                        "</div>" +
                        "<div class=\"a1fd\"><a href=\"javascript:void(0)\" onclick=\"bigfont('"+ urls[i].src.replace("@", ":")+"')\"><img  src=\"<c:url value ="/img/a1fd.png" />\"></a></div>" +
                        "<input type=\"hidden\" name=\"Picture\" value=\""+urls[i].src.replace("@", ":")+"\"><div class=\"jqzoom\" \"><img onerror='nofind();' scop='img' src=\""+chuLiPotoUrl(urls[i].src.replace("@", ":"))+"\" alt=\"shoe\"   jqimg=\""+chuLiPotoUrl(urls[i].src.replace("@", ":"))+"\" width=\"120\" height=\"110\"></div></td>";
            }
            /*$("#picture").append(str);*/
          /*  alert($("#addPictureId"));*/
            $("#addPictureId").append(str);
            str = "";
            var addPictureId=document.getElementById("addPictureId");
            var div=$(addPictureId).find("div[id=vspic]");
            console.debug(div);
            if(div.length>0) {
                $("#lianjie").remove();
            }
            zeroclipInit();
        }
        function removeThis(a){
            $(a).parent().parent().parent().parent().parent().remove();
            var addPictureId=document.getElementById("addPictureId");
            var div=$(addPictureId).find("div[id=vspic]");
            if(div.length==0) {
                $(addPictureId).append("<td align='center' id='lianjie'><br/><a href='javascript:void(0);' onclick='addpictureName(this)'>您还没有上传图片，马上上传</a></td>");
            }
        }

        function addLength(obj){
            this.value='';
            $(obj).attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
        }
        function subLength(obj){
            if(obj.value==''){obj.value='';obj.style.color='#999';}
            $(obj).attr("style","margin-left:10px;width: 40px;background-color: #fff;border-radius: 5px;");
            $(obj).val("");
        }
       function connectPicture(obj){
            console.debug(obj);
           var i=$(obj).attr("data");
           var a=$(obj).parent.find("a[id=doCopy"+i+"]");
           console.debug(a);
          /* a.click();*/
        }
        function importItemInformation(){
            var url=path+"/information/importItemInformation.do";
            itemInformation=openMyDialog({title: '请选择导入的excel文件',
                content: 'url:'+url,
                icon: 'succeed',
                width:600,
                lock:true,
                zIndex:2000
            });
        }
    function changeName(obj){
       var value=$(obj).val();
       var changeName=document.getElementById("changeName");
        var content="系统正在为你匹配到最佳EBAY分类...";
       if(value==""){
           return;
       }else{
           if(!changeName){
               var tr1="<br/><span id=\"changeName\" style=\"float:left;margin-left: 30px;font-size:5px;color: lightgray;line-height: 15px;\">"+content+"</span>";
               $(obj).after(tr1);
           }else{
               changeName.innerHTML=content;
           }

       }
    }

    </script>
</head>
<body>
<%---------------------------------------------------------------------------------------------------------------%>
    <div class="modal-body" style="background-color: #ffffff;">

<br/><br/>
<form id="informationForm">
    <input type="hidden" name="id" id="id" value="${itemInformation.id}"/>
    <input type="hidden" name="comment" id="comment" value="${itemInformation.comment}"/>
    <input type="hidden" name="inventoryid" value="${inventory.id}"/>
    <input type="hidden" name="customid" value="${custom.id}"/>
    <input type="hidden" name="supplierid" value="${supplier.id}"/>
    <input type="hidden" name="remark" id="remark123">
    <input type="hidden" name="discription" id="discription">
            <table width="100%" border="0" style="margin-top:-20px;">
                <tbody><tr>
                    <td style="line-height:16px;"><%--<p><span style="color: #2395F3; font-size: 16px; font-family: '微软雅黑', '宋体', Arial">编辑商品</span><span style="float:right; "><button type="button" class="net_put_clo" data-dismiss="modal"></button></span></p>--%>
                        <p>你也可以选择导入商品，<a href="javascript:void(0);" onclick="importItemInformation();" style=" color:#2395F3; font-size:12px; font-family: '微软雅黑', '宋体', Arial">+去导入</a></p></td>
                </tr>
                <tr>
                    <td><div class="new_tab">
                        <div class="new_tab_left"></div>
                        <div class="new_tab_right"></div>
                        <dt id="svt1" style="width: 60px;border-top-left-radius: 5px;border-top-right-radius: 5px;" class="new_ic_1" onclick="setvTab('svt',1,3)">基本信息</dt>
                        <dt id="svt3" style="width: 60px;border-top-left-radius: 5px;border-top-right-radius: 5px;" onclick="setvTab('svt',3,3)" class="">产品描述</dt>
                        <dt id="svt2" style="width: 60px;border-top-left-radius: 5px;border-top-right-radius: 5px;" onclick="setvTab('svt',2,3)" class="">产品图片</dt>
                        <span style="float:right; margin-top:8px; margin-right:10px;">
                            <script type=text/plain id='picUrls'></script>
                            <div><a href="javascript:void(0)" id="apicUrls" onclick="addpicture(this)"><img src="<c:url value ="/img/apic_dr.png" />" width="75" height="15"></a></div></span>
                    </div></td>
                </tr>

                </tbody></table>
            <div id="new_svt_1" class="hover" style="width:650px;display: block;background-color: #ffffff;">
                <link href="<c:url value ="/css/compiled/layout.css" />" rel="stylesheet" type="text/css">
                <table width="100%" border="0" style="margin-left:40px;">
                    <tbody><tr>
                        <%--<td height="46" align="right">商品名称：</td>--%>
                        <td  height="46" colspan="2" width="95%"><div class="newselect" style="width:600px;">
                            <span style="float: left;line-height: 65px;width: 91px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商品名称：</span><input onchange="changeName(this);" style="margin-top: 15px;" class="form-controlsd validate[required]" type="text" id="informationName" name="name" value="${itemInformation.name}">
                            <c:if test="${itemInformation.typename!=null&&itemInformation.typeflag==1}">
                                <br/><span id="changeName" style="float:left;margin-left: 5px;font-size:5px;color: lightgray;line-height: 15px;">系统匹配Ebay分类:${itemInformation.typename}</span>
                            </c:if>
                            <c:if test="${itemInformation.typeflag==0}">
                                <br/><span id="changeName" style="float:left;margin-left: 5px;font-size:5px;color: lightgray;line-height: 15px;">系统正在在为你匹配到最佳EBAY分类...</span>
                            </c:if>
                            <c:if test="${itemInformation.typeId==null&&itemInformation.typeflag==1}">
                                <br/><span id="changeName" style="float:left;margin-left: 5px;font-size:5px;color: lightgray;line-height: 15px;">系统没有为你匹配到最佳EBAY分类...</span>
                            </c:if>
                        </div></td>
                    </tr>

                    <tr>
                        <td width="14%" height="46" align="right">商品SKU：</td>
                        <td height="46" width="95%"><div class="newselect">
                            <input  class="form-controlsd validate[required]" type="text" id="sku" name="sku" value="${itemInformation.sku}">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">长：</td>
                        <td height="46" width="95%"><div class="newselect" style="width: 400px;">
                            <input name="length" style="width: 225px;margin-right: 0px;border-top-right-radius: 0px;border-bottom-right-radius: 0px;" value="${inventory.length}" class="form-controlsd" type="text"><input name="length" class="form-controlsd" value="cm" disabled  style="color:#000000;background-color:gainsboro; width: 75px;margin-left: 0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;" type="button">
                        </div>
                        </td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">宽：</td>
                        <td height="46" width="95%"><div class="newselect" style="width: 400px;">
                            <input name="width" style="width: 225px;margin-right: 0px;border-top-right-radius: 0px;border-bottom-right-radius: 0px;" value="${inventory.width}" class="form-controlsd" type="text"><input name="length" class="form-controlsd" value="cm" disabled  style="color:#000000;background-color:gainsboro; width: 75px;margin-left: 0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;" type="button">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">高：</td>
                        <td height="46" width="95%"><div class="newselect" style="width: 400px;">
                            <input name="height" style="width: 225px;margin-right: 0px;border-top-right-radius: 0px;border-bottom-right-radius: 0px;" value="${inventory.height}" class="form-controlsd" type="text"><input name="length" class="form-controlsd" value="cm" disabled  style="color:#000000;background-color:gainsboro; width: 75px;margin-left: 0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;" type="button">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">重量：</td>
                        <td height="46" width="95%"><div class="newselect" style="width: 400px;">
                            <input name="weight" style="width: 225px;margin-right: 0px;border-top-right-radius: 0px;border-bottom-right-radius: 0px;" value="${custom.weight}" class="form-controlsd" type="text"><input name="length" class="form-controlsd" value="g" disabled  style="color:#000000;background-color:gainsboro; width: 75px;margin-left: 0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;" type="button">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">销售价：</td>
                        <td height="46" width="95%"><div class="newselect" style="width: 400px;">
                            <input style="width: 225px;margin-right: 0px;border-top-right-radius: 0px;border-bottom-right-radius: 0px;" name="supplierPrice" value="${supplier.price}" class="form-controlsd" type="text"><input name="length" class="form-controlsd" value="USD" disabled  style="color:#000000;background-color:gainsboro; width: 75px;margin-left: 0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;" type="button">
                        </div></td>
                    </tr>
              <%--      <tr>
                        <table style="margin-left: 50px;">
                            <td>标签:</td>
                                <td><ul id="addRemark" style="padding: 3px 5px 3px 5px;border: 1px solid lightgray;width: 360px;background-color: #ffffff;margin-top: 3px;">
                                    <c:forEach items="${configs}" var="config">
                                        <a href='javascript:void(0)' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class="icon-remove-sign" style='margin-right: 2px;'></i><span >${config.configName}</span><input type='hidden' name='label' value='${config.configName}'></a>
                                    </c:forEach>
                                    <input style="width:40px;background-color: #fff;border-radius: 5px;" id="kk"type="text" value="" onfocus="addLength(this)" onblur="subLength(this)" onkeyup="this.style.color='#333';" onclick="addLength(this)" /></ul></td>
                        </table>
                    </tr>--%>
                    <tr>
                        <td width="14%" height="46" align="right">标签：</td>
                        <td height="46" width="86%"><div class="newselect">
                            <%--<input name="" class="form-controlsd" type="text">--%>
                                <ul id="addRemark"  style="padding: 3px 5px 3px 5px;border: 1px solid lightgray;width: 300px;border-radius: 4px;margin-left: 4px;background-color: #ffffff;margin-top: 3px;float: left">
                                    <c:forEach items="${configs}" var="config">
                                        <a href='javascript:void(0)' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class="icon-remove-sign" style='margin-right: 2px;'></i><span >${config.configName}</span><input type='hidden' name='label' value='${config.configName}'></a>
                                    </c:forEach>
                                    <input style="margin-left: 10px;width:40px;background-color: #fff;border-radius: 5px;" id="kk"type="text" value="" onfocus="addLength(this)" onblur="subLength(this)" onkeyup="this.style.color='#333';" onclick="addLength(this)" /></ul>
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="22" align="right"></td>
                        <td height="22" width="86%"><div class="newselect" style=" color:#597791">
                            请使用【enter】键添加标签
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" align="right"></td>
                        <td width="86%">
                            你可能需要的标签：</td>
                    </tr>
                    <tr>
                        <td width="14%" height="0" align="right"></td>
                        <td height="0" width="86%" id="addremarks">
                            <a href='javascript:void(0);' onclick="addToli('电子产品')"><span class="pipi">+电子产品</span></a>
                            <a href='javascript:void(0);' onclick="addToli('服装')"><span class="pipi">+服装</span></a>
                            <a href='javascript:void(0);' onclick="addToli('户外用品')"><span class="pipi">+户外用品</span></a>
                            <a href='javascript:void(0);' onclick="addToli('清仓')"><span class="pipi">+清仓</span></a>
                        </td>
                    </tr>
                 <%--   <tr>
                        <td height="28" align="right"></td>
                        <td style=" padding-top:22px;" height="28"><button type="button" class="net_put">下一步</button> <button type="button" class="net_put_1" style="font-size: 5px;">相关信息</button></td>
                    </tr>--%>
                    </tbody></table>
                <div style="bottom: 1px;">
                    <div class="modal-footer">
                        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
                        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
                        <button type="button" class="net_put" style="margin-left: 20px;" onclick="setvTab('svt',3,3)">下一步</button>
                    </div>
                </div>
            </div>
            <div style="display: none;" id="new_svt_2">
                <link href="<c:url value ="/css/compiled/layout.css" />" rel="stylesheet" type="text/css">
                <table width="100%" border="0">
                    <tbody><tr id="addPictureId"></tr>
                    </tbody></table>
                <div style="bottom: 1px;">
                    <div class="modal-footer">
                        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
                        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>

                    </div>
                </div>
            </div>

            <div style="display: none;height: 500px;" id="new_svt_3">
                <script id="myEditor" type="text/plain" style="width:670px;height:400px;"></script>
                <textarea id="myEditor12" style="display: none">${itemInformation.description}</textarea>
                <div style="bottom: 1px;">
                    <div class="modal-footer">
                        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
                        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
                        <button type="button" class="net_put" style="margin-left: 20px;" onclick="setvTab('svt',2,3)">下一步</button>
                    </div>
                </div>
            </div>

</form>

<%--//----------------------------------------------------------------------------------------------%>

<script type="text/javascript">
    var api = frameElement.api, W = api.opener;

    var lablId = -1;
    $(function() {
        $("#kk").blur(function() {
            if (isNan(this.value) != false) {
                this.value = '';
                this.style.color = '#999';
            }
        });

    });

    function isNan(obj) {
        try {
            return obj == 0 ? true: !obj
        } catch(e) {
            return true;
        }
    }


    function deletes(obj) {
        $(obj).remove();
    }

    function addlabl(id) {
        if (lablId == id) {
            return;
        }
        lablId = id;
        var str = $("#add_" + id).text();
        var li_id = $(".label li:last-child").attr('id');
        if (li_id != undefined) {
            li_id = li_id.split('_');
            li_id = parseInt(li_id[1]) + 1;
        } else {
            li_id = 0;
        }
        $(".label_box").css("display", "block");
        var text = "<li id='li_" + li_id + "'><a href='javascript:;' onclick='deletes(" + li_id + ");' ><img src='images/label_03.png' class='label-pic'>" + str + "</a><input type='hidden' name='label[" + li_id + "].name' value='" + str + "'></li>";
        $(".label").append(text);
    }
</script>

</body>

</html>
