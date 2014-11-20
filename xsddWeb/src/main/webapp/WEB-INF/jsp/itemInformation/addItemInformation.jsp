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
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/dialogs/image/imageextend.js" /> ></script>
 <%--   <script type="text/javascript" src=<c:url value="/js/item/addItem.js"/>></script>
    <script type="text/javascript" src=<c:url value="/js/item/addItem2.js"/>></script>--%>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        var add=0;
        $(function(){
            var li=W.document.getElementById("loadremarks");
            var spans=$(li).find("span[scop=remark]");
            var td=$("#addremarks");
            var span="<table><tr><td>";
            var date1="";
            for(var i=0;i<spans.length;i++){
                var remarkName=spans[i].innerText;
               if(i==(spans.length-1)){
                    date1+=remarkName;
                }else{
                    date1+=remarkName+",";
                }
            }
            var date={"names":date1};
            var id=$("#id").val();
            var url=path+"/information/ajax/addPictures.do?id="+id;
            $().invoke(url,date,
                    [function(m,r){
                        var div="";
                        var r1= r.list;
                        var r2= r.pic;
                        if(r2.length>0){
                            for(var i=0;i< r2.length;i++){
                                div+=" <td class=\"spic\" style=\"margin-left: 20px;\">" +
                                        "<div id=\"vspic\">" +
                                        "<li><a href=\"javascript:void(0)\"><img src=\"<c:url value ="/img/a1xl.png" />\" width=\"18\" height=\"18\"></a>" +
                                "<ul>" +
                                "<li><a href=\"javascritp:void(0)\" onclick=\"removeThis(this)\">删除</a></li>" +
                                "<li><a href=\"javascritp:void(0)\" onclick=\"connectPicture('"+r2[i].attrvalue+"')\">复制链接</a></li>" +
                                "</ul>" +
                                "</li>" +
                                "</div>" +
                                "<div class=\"a1fd\"><a href=\"javascript:void(0)\" title=\"<img src='"+r2[i].attrvalue+"'/>\" onclick=\"bigfont('"+r2[i].id+"')\"><img  src=\"<c:url value ="/img/a1fd.png" />\"></a></div>" +
                                "<input type=\"hidden\" name=\"Picture\" value=\""+r2[i].attrvalue+"\"><div class=\"jqzoom\" id=\""+r2[i].id+"\"><img src=\""+r2[i].attrvalue+"\" alt=\"shoe\"   jqimg=\""+r2[i].attrvalue+"\" width=\"120\" height=\"110\"></div></td>";
                            }
                            $("#addPictureId").append(div);
                        }
                        if(r1.length>0){
                            for(var i=0;i< r1.length;i++){
                                var remark=r1[i];
                                var name=spans[i+2].innerText;
                                name=name.substring(0,name.length-1);
                                if(i%5==0&&i!=0){
                                    span+="<a href='javascript:void(0);' onclick=\"addToli('"+name+"')\"><span class=\"pipi\">+ "+name+"("+ remark[name]+")</span></a></td></tr><tr><td>";
                                }else{
                                    span+="<a href='javascript:void(0);' onclick=\"addToli('"+name+"')\"><span class=\"pipi\">+ "+name+"("+ remark[name]+")</span></a>";
                                }
                            }
                            span+="</td></tr></table>";
                            $(td).append(span);
                         }
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        /*    $(".jqzoom").jqueryzoom();*/
        });
        function bigfont(id){
           /* $("#"+id).jqueryzoom({
                xzoom: 1050,		//zooming div default width(default width value is 200)
                yzoom: 700,		//zooming div default width(default height value is 200)
                offset: 1,		//zooming div default offset(default offset value is 10)
                position: "right",  //zooming div position(default position value is "right")
                preload: 10     // 1 by default

            });*/
            var url=path+"/information/bigfont.do?pictureId="+id;
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
            var url=path+"/information/ajax/saveItemInformation.do?remark="+remark;
            var data=$("#informationForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
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
        $(document).ready(function () {
            $().image_editor.init("picUrls"); //编辑器的实例id
            $().image_editor.show("apicUrls"); //上传图片的按钮id
        })
        var afterUploadCallback = null;
        var sss;
        function addpicture(a){

            _sku =$("#sku").val();
            afterUploadCallback = {"imgURLS": addPictrueUrl};
            sss = a.id;
        }
        function addPictrueUrl(urls) {
            var str = '';
            for (var i = 0; i < urls.length; i++) {
                str+="<td class=\"spic\" style=\"margin-left: 20px;\">"+
                        "<div id=\"vspic\">" +
                        "<li><a href=\"javascritp:void(0)\"><input type=\"hidden\" name=\"Picture\" value='" + urls[i].src.replace("@", ":") + "'><img src=\"<c:url value ="/img/a1xl.png" />\" width=\"18\" height=\"18\"></a>" +
                        "<ul>" +
                        "<li><a href=\"javascritp:void(0)\" onclick=\"removeThis(this)\">删除</a></li>" +
                        "<li><a href=\"javascritp:void(0)\" onclick=\"connectPicture('"+ urls[i].src.replace("@", ":") +"')\">复制链接</a></li>" +
                        "</ul>" +
                        "</li>" +
                        "</div>" +
                        "<div class=\"a1fd\"><a href=\"javascritp:void(0)\"><img src=\"<c:url value ="/img/a1fd.png" />\"></a></div>" +
                        "<img src=\"" + urls[i].src.replace("@", ":") +"\" width=\"120\" height=\"110\"></td>";
            }
            /*$("#picture").append(str);*/
          /*  alert($("#addPictureId"));*/
            $("#addPictureId").append(str);
            str = "";
        }
        function removeThis(a){
            $(a).parent().parent().parent().parent().parent().remove();
        }
        $(document).ready(function(){
            $("#informationForm").validationEngine();
        });
        function addLength(obj){
            this.value='';
            $(obj).attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
        }
        function subLength(obj){
            if(obj.value==''){obj.value='';obj.style.color='#999';}
            $(obj).attr("style","margin-left:10px;width: 40px;background-color: #fff;border-radius: 5px;");
            $(obj).val("");
        }
       function connectPicture(url){
            window.open(url);
        }
    </script>
</head>
<body>
<%--<form id="informationForm">
<input type="hidden" name="id" value="${itemInformation.id}"/>
<input type="hidden" name="inventoryid" value="${inventory.id}"/>
<input type="hidden" name="customid" value="${custom.id}"/>
<input type="hidden" name="supplierid" value="${supplier.id}"/>
    <br/><br/>

<table style="width: 1000px;" align="center">
    <tr>
        <td></td><td>名称:</td><td><input type="text" class="form-controlsd validate[required]" id="informationName" name="name" value="${itemInformation.name}"/></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td><b>产品明细</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>SKU</td><td><input type="text" class="form-controlsd validate[required]" id="sku" name="sku" value="${itemInformation.sku}"/></td>
    </tr>
    <tr>
        <td></td><td></td><td>
            <select class="select"  style="border: 1px solid lightgray;float: left;margin-left: 3px;">
                <option>UPC</option>
                <option>EAN</option>
                <option>ISBN</option>
                <option>eBay ReferenceID</option>
            </select>
            <input type="text" class="form-controlsd"/>
       </td>
    </tr>
    <tr>
        <td></td><td>商品分类</td><td>
            <select name="itemType" class="select" style="border: 1px solid lightgray;margin-left: 3px;">
                <c:forEach items="${types}" var="type">
                    <option value="${type.id}">${type.configName}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td></td><td>图片</td><td>
        <div class="panel" style="display: block">
            <section class='example'>
                <div id="picture" class="gridly">
                    <c:if test="${pictures!=null}">
                    <ul>
                        <c:forEach items="${pictures}" var="picture">
                            <li>
                                <div>
                                    <input type="hidden" name="Picture" value="${picture.attrvalue}">
                                    <img src="${picture.attrvalue}" style="width: 50px;"/>
                                    <a href="javascritp:void(0)" onclick="removeThis(this)">移除</a>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                    </c:if>
                </div>
            </section>
            <script type=text/plain id='picUrls'></script>
            <div style="padding-left: 60px;"><a href="javascript:void(0)" id="apicUrls" onclick="addpicture(this)">选择图片</a></div>
        </div>
        <br/>
        </td>
    </tr>
    <tr>
        <td></td><td>描述</td><td>
        <div id="addDiscription">
            &lt;%&ndash;<input type="hidden" name="descriptionName" value="${itemInformation.description}"/>&ndash;%&gt;
            <c:if test="${itemInformation!=null}">
                <c:if test="${itemInformation.description!=null}">
                    <table id="discriptionTable" border="1" cellpadding="0" cellspacing="0" style="width: 400px;">
                        <tr>
                            <td width="70%">描述名称</td>
                            <td width="30%">动作</td>
                        </tr>
                        <tr scop="tr">
                            <td>${itemInformation.name}
                                <input type="hidden" name="discription" value="${itemInformation.description}" />
                            </td>
                            <td>
                                <a href="javascript:void(0);" onclick="editDiscription(this);">编辑</a>&nbsp;
                                <a href="javascript:void(0);" onclick="removeDiscription(this);">移除</a>
                            </td>
                        </tr>
                     </table>
                </c:if>
            </c:if>
        </div>
        <br/><a href="javascript:void(0);" onclick="addDiscription();">添加描述</a></td>
    </tr>
    <tr>
        <td></td><td>自定义物品属性</td><td>
        <div>
            <table id="attrabute1" >
                <c:if test="${attrs!=null}">
                    <tr>
                        <td>名称</td><td>值</td>
                    </tr>
                    <c:forEach items="${attrs}" var="attr">
                        <tr>
                            <td><input type="text" name="attrName" class="form-controlsd" value="${attr.attrname}"/></td>
                            <td><input type="text" name="attrValue" class="form-controlsd" value="${attr.attrvalue}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>
        <a href="javascript:void(0);" onclick="addAttrabute();">添加</a></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td><b>ebay</b></td><td></td><td></td>
    </tr>
    <tr>
        <td></td><td>相关范本</td><td>此处仅计算通过PA刊登的物品。</td>
    </tr>

    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td><b>供应商</b></td><td></td><td></td>
    </tr>
    <tr>
        <c:if test="${supplier==null}">
            <td></td><td></td><td><a href="javascript:void(0);" onclick="addSupplier();">添加</a><br/>
        </c:if>
        <c:if test="${supplier!=null}">
            <td></td><td></td><td><br/>
        </c:if>
        <table id="supper">
            <c:if test="${supplier!=null}">
                <tr><td>ID:</td><td><input name="supplierId" class="form-controlsd" value="${supplier.supplierid}"/></td></tr>
                <tr><td>供应商:</td><td>
                <select name="supplierName">
                <option>爱酷客</option>
                </select>
                </td></tr>
                <tr><td>价格:</td><td><input class="form-controlsd" name="supplierPrice" value="${supplier.price}"/></td><td>
                <select name="supplierCurrency">
                <option>AUD</option>
                </select></td></tr>
                <tr><td>厂商SKU</td><td><input class="form-controlsd" name="supperSku" value="${supplier.suppersku}"/></td></tr>
                <tr><td>备注</td><td><textarea class="form-controlsd" style="height: 100px;" name="supplierRemark" ${supplier.remark}></textarea></td></tr>
            </c:if>
        </table>
    </td>
    </tr>

    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td><b>库存</b></td><td></td><td></td>
    </tr>
    <tr>
        <td></td><td>库存</td><td>总数量:</td>
    </tr>
    <tr>
        <td></td><td>库存预警</td><td><input class="form-controlsd" type="text" name="warning" value="${inventory.warningnumber}"/></td>
    </tr>
    <tr>
        <td></td><td>均价</td><td>0.00  USD</td>
    </tr>
    <tr>
        <td></td><td>长</td><td><input class="form-controlsd" type="text" name="length" value="${inventory.length}" style="width: 200px;"/><span class="a_left">宽:</span>&nbsp;<input class="form-controlsd" style="width: 200px;" type="text" name="width" value="${inventory.width}"/>&nbsp;<span class="a_left">高:</span>&nbsp;<input style="width: 200px;" class="form-controlsd" type="text" name="height" value="${inventory.height}"/></td>
    </tr>

    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td><b>报关信息</b></td><td></td><td></td>
    </tr>
    <tr>
        <td></td><td>申报名1</td><td><input class="form-controlsd" type="text" name="customEnglishName" value="${custom.englishname}"/></td>
    </tr>
    <tr>
        <td></td><td>申报名2</td><td><input class="form-controlsd" type="text" name="customName" value="${custom.name}"/></td>
    </tr>
    <tr>
        <td></td><td>重量(kg)</td><td><input class="form-controlsd" type="text" name="weight" value="${custom.weight}"/></td>
    </tr>
    <tr>
        <td></td><td>申报价值</td><td><input class="form-controlsd" type="text" name="declaredValue" value="${custom.declaredvalue}"/>&nbsp;
        <select name="currencyType">

        </select>
    </tr>
    <tr>
        <td></td><td>原产地</td><td><input class="form-controlsd" type="text" name="place" value="${custom.productionplace}"/></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
</table>
<table style="margin-left: 50px;">
    <tr>
        <td>标签:</td>
        &lt;%&ndash;<td><div id="addRemark" class="form-controlsd" ><input &lt;%&ndash;onblur="subLength(this);"&ndash;%&gt; onclick="addLength(this);" type="text" class="form-controlsd" style="width: 40px;height: 20px;">&lt;%&ndash;<input type="text" class="form-controlsd" style="width: 260px;height: 20px;">&ndash;%&gt;</div></td>&ndash;%&gt;
        <td><ul id="addRemark" style="padding: 3px 5px 3px 5px;border: 1px solid lightgray;width: 360px;background-color: #ffffff;margin-top: 3px;">
            <c:forEach items="${configs}" var="config">
                <a href='javascript:void(0)' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class="icon-remove-sign" style='margin-right: 2px;'></i><span >${config.configName}</span><input type='hidden' name='label' value='${config.configName}'></a>
            </c:forEach>
            <input style="width:40px;background-color: #fff;border-radius: 5px;" id="kk"type="text" value="" onfocus="addLength(this)" onblur="subLength(this)" onkeyup="this.style.color='#333';" onclick="addLength(this)" /></ul></td>
    </tr>
    <tr>
        <td></td>
        <td><span style="color: #0000ff">请使用【enter】键添加标签</span></td>
    </tr>
    <tr></tr>
    <tr>
        <td></td>
        &lt;%&ndash;  <td>您可能需要的标签:<br/>&ndash;%&gt;

        </td>
    </tr>
</table>
</form>--%>
<%---------------------------------------------------------------------------------------------------------------%>

    <div class="modal-body">
        <script type="text/javascript">
            function setvTab(name,cursel,n){
                for(i=1;i<=n;i++){
                    var svt=document.getElementById(name+i);
                    var con=document.getElementById("new_"+name+"_"+i);
                    svt.className=i==cursel?"new_ic_1":"";
                    con.style.display=i==cursel?"block":"none";
                }
            }
        </script>
<br/><br/>
<form id="informationForm">
    <input type="hidden" name="id" id="id" value="${itemInformation.id}"/>
    <input type="hidden" name="inventoryid" value="${inventory.id}"/>
    <input type="hidden" name="customid" value="${custom.id}"/>
    <input type="hidden" name="supplierid" value="${supplier.id}"/>
            <table width="100%" border="0" style="margin-top:-20px;">
                <tbody><tr>
                    <td style="line-height:16px;"><%--<p><span style="color: #2395F3; font-size: 16px; font-family: '微软雅黑', '宋体', Arial">编辑商品</span><span style="float:right; "><button type="button" class="net_put_clo" data-dismiss="modal"></button></span></p>--%>
                        <p>你也可以选择导入商品，<a href="#" style=" color:#2395F3; font-size:12px; font-family: '微软雅黑', '宋体', Arial">+去导入</a></p></td>
                </tr>
                <tr>
                    <td><div class="new_tab">
                        <div class="new_tab_left"></div>
                        <div class="new_tab_right"></div>
                        <dt id="svt1" class="new_ic_1" onclick="setvTab('svt',1,4)">基本信息</dt>
                        <dt id="svt2" onclick="setvTab('svt',2,4)" class="">相关信息</dt>
                        <span style="float:right; margin-top:8px; margin-right:10px;">
                            <script type=text/plain id='picUrls'></script>
                            <div><a href="javascript:void(0)" id="apicUrls" onclick="addpicture(this)"><img src="<c:url value ="/img/apic_dr.png" />" width="75" height="15"></a></div></span>
                    </div></td>
                </tr>

                </tbody></table>
            <div id="new_svt_1" class="hover" style="width:800px;display: block;background-color: #ffffff;">
                <link href="css/compiled/layout.css" rel="stylesheet" type="text/css">
                <table width="100%" border="0" style="margin-left:40px;">

                    <tbody><tr>
                        <td height="46" align="right">商品名称：</td>
                        <td height="46" width="86%"><div class="newselect">
                                <input  class="form-controlsd validate[required]" type="text" id="informationName" name="name" value="${itemInformation.name}">
                        </div></td>
                    </tr>

                    <tr>
                        <td width="14%" height="46" align="right">商品SKU：</td>
                        <td height="46" width="86%"><div class="newselect">
                            <input  class="form-controlsd validate[required]" type="text" id="sku" name="sku" value="${itemInformation.sku}">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">长：</td>
                        <td height="46" width="86%"><div class="newselect">
                            <input name="length" value="${inventory.length}" class="form-controlsd" type="text">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">宽：</td>
                        <td height="46" width="86%"><div class="newselect">
                            <input name="width" value="${inventory.width}" class="form-controlsd" type="text">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">高：</td>
                        <td height="46" width="86%"><div class="newselect">
                            <input name="height" value="${inventory.height}" class="form-controlsd" type="text">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">重量：</td>
                        <td height="46" width="86%"><div class="newselect">
                            <input name="weight" value="${custom.weight}" class="form-controlsd" type="text">
                        </div></td>
                    </tr>
                    <tr>
                        <td width="14%" height="46" align="right">采购价：</td>
                        <td height="46" width="86%"><div class="newselect">
                            <input name="supplierPrice" value="${supplier.price}" class="form-controlsd" type="text">
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
                            </td>
                    </tr>
                 <%--   <tr>
                        <td height="28" align="right"></td>
                        <td style=" padding-top:22px;" height="28"><button type="button" class="net_put">下一步</button> <button type="button" class="net_put_1" style="font-size: 5px;">相关信息</button></td>
                    </tr>--%>
                    </tbody></table>
            </div>
            <div style="display: none;" id="new_svt_2">
                <link href="css/compiled/layout.css" rel="stylesheet" type="text/css">
                <table width="100%" border="0">
                    <tbody><tr id="addPictureId">
                    <%--<c:forEach items="${pictures}" var="picture" begin="0" varStatus="status">
                        <td class="spic" style="margin-left: 20px;">
                            <div id="vspic">
                                <li><a href="#"><img src="<c:url value ="/img/a1xl.png" />" width="18" height="18"></a>
                                    <ul>
                                        <li><a href="javascritp:void(0)" onclick="removeThis(this)">删除</a></li>
                                        <li><a href="javascritp:void(0)" onclick="connectPicture('${picture.attrvalue}')">复制链接</a></li>
                                    </ul>
                                </li>
                            </div>
                            <div class="a1fd"><a href="#"><img src="<c:url value ="/img/a1fd.png" />"></a></div>
                            <input type="hidden" name="Picture" value="${picture.attrvalue}"><img src="${picture.attrvalue}" width="120" height="110"></td>
                    </c:forEach>--%>
                     </tr>
                    </tbody></table>
            </div>

</form>

<%--//----------------------------------------------------------------------------------------------%>
<div style="bottom: 1px;">
<div class="modal-footer">
  <%--  <button type="button" class="net_put" onclick="submitCommit();">下一步</button>
    <button type="button" class="net_put_1" style="font-size: 5px;" onclick="closedialog();">相关信息</button>--%>
    <button type="button" class="net_put" onclick="submitCommit();">保存</button>
    <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
</div>
</div>
<script type="text/javascript">
    var lablId = -1;

    $(function() {
        $("#kk").blur(function() {
            if (isNan(this.value) != false) {
                this.value = '';
                this.style.color = '#999';
            }
        });
    });
    $(document).ready(function() {
        $("#kk").keydown(function(event) {
            if (event.keyCode == 13) {
                var str = $("#kk").val();
                if (isNan(str) != true) {
                    var li_id = $(".label li:last-child").attr('id');
                    if (li_id != undefined) {
                        li_id = li_id.split('_');
                        li_id = parseInt(li_id[1]) + 1;
                    } else {
                        li_id = 0;
                    }
                    $(".label_box").css("display", "block");
                    var text = "<a href='javascript:#' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class=\"icon-remove-sign\" style='margin-right: 2px;'></i><span >" + str + "</span><input type='hidden' name='label' value='" + str + "'></a>";
                    var spans=$("#addRemark").find("span");
                    for(var i=0;i<spans.length;i++){
                        var span=spans[i].innerHTML;
                        if(str==span){
                            return;
                        }
                    }
                    $("#kk").before(text);
                }
                $("#kk").val("");
                $("#kk").attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
            }
        })
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
<%--//--------------------------------------------------------------------------%>
<%--<div class="modal-content">
    <div class="modal-header">
        <h4 class="modal-title" style="color:#2E98EE">添加或修改商品信息</h4>
        <table width="100%" border="0" style="margin-top:20px;">
            <tbody><tr>
                <td width="13%" height="28" align="right">名称：</td>
                <td width="87%" height="28"><div class="newselect">&lt;%&ndash;<input name="" class="form-controlsd" type="text">&ndash;%&gt;<input type="text" class="form-controlsd validate[required]" id="informationName" name="name" value="${itemInformation.name}"/></div></td>
            </tr>
            </tbody></table>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" role="form">
            <div class="newtitle_1" style="margin-top:-22px;">产品明细</div><table width="100%" border="0">

            <tbody><tr>
                <td width="13%" height="28" align="right">SKU：</td>
                <td width="87%" height="28"><div class="newselect">&lt;%&ndash;<input name="" class="form-controlsd" type="text">&ndash;%&gt;<input type="text" class="form-controlsd validate[required]" id="sku" name="sku" value="${itemInformation.sku}"/></div></td>
            </tr>
            <tr>
                <td width="13%" height="33" align="right"></td>
                <td width="87%" height="33" style="padding-top:8px;">
                    <table width="100%" border="0">
                        <tbody><tr>
                            <td width="14%">
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select">
    <option selected="selected">UPC</option>
    <option value="1">EAN</option>
    <option value="2">ISBN</option>
    <option value="3">eBay ReferenceID</option>
</select>
</span>
</span>
                            </td>
                            <td width="86%"><div class="newselect"><input name="" class="form-controlsd" type="text"></div></td>
                        </tr>
                        </tbody></table>
                </td>
            </tr>
            <tr>
                <td width="13%" height="28" align="right">商品分类：</td>
                <td width="87%" height="28" style="padding-top:8px;"><span id="sleBG">
<span id="sleHid">
<select name="itemType" class="select">
    <c:forEach items="${types}" var="type">
        <option value="${type.id}">${type.configName}</option>
    </c:forEach>
</select>
</span>
</span></td>
            </tr>
            <tr>
                <td width="13%" height="28" align="right" style="padding-top:8px;">图片</td>
                <td width="87%" height="28" style="padding-top:8px;"><div class="newselect">
                    <div class="panel" style="display: block">
                    <section class='example '>
                        <div id="picture" class="gridly">
                            <c:if test="${pictures!=null}">
                                <ul>
                                    <c:forEach items="${pictures}" var="picture">
                                        <li>
                                            <div class="newselect" >
                                                <input type="hidden" name="Picture" value="${picture.attrvalue}">
                                                <img src="${picture.attrvalue}" style="width: 50px;"/>
                                                <a href="javascritp:void(0)" onclick="removeThis(this)">移除</a>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </div>
                    </section>
                    <script type=text/plain id='picUrls'></script>
                    <div style="padding-left: 60px;"><a href="javascript:void(0)" id="apicUrls" onclick="addpicture(this)">选择图片</a></div>
                </div>
                    <br/>
                </td>
            </tr>
            <tr>
                <td width="13%" height="28" align="right" style="padding-top:8px;">描述：</td>
                <td width="87%" height="28" style="padding-top:8px;"><div id="addDiscription">
                    &lt;%&ndash;<input type="hidden" name="descriptionName" value="${itemInformation.description}"/>&ndash;%&gt;
                    <c:if test="${itemInformation!=null}">
                        <c:if test="${itemInformation.description!=null}">
                            <table id="discriptionTable" border="1" cellpadding="0" cellspacing="0" style="width: 400px;">
                                <tr>
                                    <td width="70%">描述名称</td>
                                    <td width="30%">动作</td>
                                </tr>
                                <tr scop="tr">
                                    <td>${itemInformation.name}
                                        <input type="hidden" name="discription" value="${itemInformation.description}" />
                                    </td>
                                    <td>
                                        <a href="javascript:void();" onclick="editDiscription(this);">编辑</a>&nbsp;
                                        <a href="javascript:void();" onclick="removeDiscription(this);">移除</a>
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                    </c:if>

                    <br/><a href="javascript:void();" class="newselect" onclick="addDiscription();">添加描述</a></div></td>
            </tr>
            <tr>
                <td width="13%" height="28" align="right" style="padding-top:8px;">自定义物品属性：</td>
                <td width="87%" height="28" style="padding-top:8px;"><div class="newselect">
                    <table id="attrabute1" >
                        <c:if test="${attrs!=null}">
                            <tr>
                                <td width="13%" height="28" align="right" style="padding-top:8px;">名称</td><td width="13%" height="28" align="right" style="padding-top:8px;">值</td>
                            </tr>
                            <c:forEach items="${attrs}" var="attr">
                                <tr>
                                    <td width="13%" height="28" align="right" style="padding-top:8px;"><input type="text" name="attrName" class="newselect" value="${attr.attrname}"/></td>
                                    <td width="13%" height="28" align="right" style="padding-top:8px;"><input type="text" name="attrValue" class="newselect" value="${attr.attrvalue}"/></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                </div>
                    <a href="javascript:void()" class="newselect" onclick="addAttrabute();">添加</a></td>
            </tr>
            </tbody></table>
            <div class="newtitle_1">ebay</div>
            <div class="newtitle_1" style="margin-top:-18px;">供应商 <span>相关范文文</span><span>此处仅计算通过PA刊登的物品</span></div>
            <div class="newtitle_1" style="margin-top:-18px;">库存 <span>添加</span></div>
            <table width="100%" border="0" style="margin-top:20px;">
                <tbody><tr>
                    <td width="13%" height="28" align="right">库存：</td>
                    <td width="87%" height="28">总数量：</td>
                </tr>
                <tr>
                    <td width="13%" height="28" align="right">库存预警：</td>
                    <td width="87%" height="28"><div class="newselect"><input name="" class="form-controlsd" type="text"></div></td>
                </tr>
                <tr>
                    <td width="13%" height="28" align="right">均价：</td>
                    <td width="87%" height="28">0.00  USD</td>
                </tr>
                <tr>
                    <td width="13%" height="28" align="right"></td>
                    <td width="87%" height="28"><span class="a_left">长</span> <input name="" class="form-control" type="text"><span class="a_left">宽</span> <input name="" class="form-control" type="text"><span class="a_left">高</span> <input name="" class="form-control" type="text"></td>
                </tr>
                </tbody></table>
            <div class="newtitle_1">报关信息</div>
            <table width="100%" border="0" style="margin-top:28px;">
                <tbody><tr>
                    <td width="13%" height="28" align="right">电报名：</td>
                    <td width="87%" height="28"><div class="newselect"><input name="" class="form-controlsd" type="text"></div></td>
                </tr>
                <tr>
                    <td width="13%" height="28" align="right">电报名：</td>
                    <td width="87%" height="28" style="padding-top:8px;"><div class="newselect"><input name="" class="form-controlsd" type="text"></div></td>
                </tr>
                <tr>
                    <td height="28" align="right"></td>
                    <td height="28" style="padding-top:22px;"><button type="button" class="net_put">确定</button> <button type="button" class="net_put_1" data-dismiss="modal">关闭</button></td>
                </tr>
                </tbody></table>
        </form></div><!-- /.modal-content -->
</div>--%>

</body>
</html>
