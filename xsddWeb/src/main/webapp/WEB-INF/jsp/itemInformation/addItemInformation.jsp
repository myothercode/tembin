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
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/dialogs/image/imageextend.js" /> ></script>
 <%--   <script type="text/javascript" src=<c:url value="/js/item/addItem.js"/>></script>
    <script type="text/javascript" src=<c:url value="/js/item/addItem2.js"/>></script>--%>
    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        var add=0;
        function addAttrabute() {
            var names=$("input[name=attrName]");
            var values=$("input[name=attrValue]");
            if(names){
                add=names.length+1;
            }else{
                add = add + 1;
            }
            var addAttr1 = " <tr><td>名称</td><td>值</td></tr><tr><td><input type=\"text\" name=\"attrName\" /></td><td><input type=\"text\" name=\"attrValue\" /><a href=\"javascript:void()\" onclick=\"removeAttrabute(this)\" >移除</a></td></tr>"
            var addAttr2 = "<tr><td><input type=\"text\" name=\"attrName\" /></td><td><input type=\"text\" name=\"attrValue\" /><a href=\"javascript:void()\" onclick=\"removeAttrabute(this)\" >移除</a></td></tr>";
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
            var tr="<tr><td>ID:</td><td><input name=\"supplierId\"/></td></tr>" +
            "<tr><td>供应商:</td><td>" +
            "<select name=\"supplierName\">" +
            "<option>爱酷客</option>" +
            "</select>" +
            "</td></tr>" +
            "<tr><td>价格:</td><td><input name=\"supplierPrice\"/></td><td>" +
            "<select name=\"supplierCurrency\">" +
            "<option>AUD</option>" +
            "</select></td></tr>" +
            "<tr><td>厂商SKU</td><td><input name=\"supperSku\"/></td></tr>" +
            "<tr><td>备注</td><td><textarea name=\"supplierRemark\"></textarea></td></tr>";
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
            var url=path+"/information/ajax/saveItemInformation.do";
            var data=$("#informationForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
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
            adddiscription=$.dialog({title: '添加描述',
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
            adddiscription=$.dialog({title: '编辑描述',
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
           /* if (sss.indexOf("apicUrls")!=-1) {//商品图片*/
                var str = '';
                str += "<ul class='gbin1-list'>";
                for (var i = 0; i < urls.length; i++) {
                    str += '<li><div style="position:relative"><input type="hidden" name="Picture" value="' + urls[i].src.replace("@", ":") + '">' +
                            '<img src=' + urls[i].src.replace("@", ":") + ' style="width: 50px;height: 50px;" />' +
                            '<a href=\'javascritp:void(0)\' onclick=\'removeThis(this)\'>移除</a></span></div>';
                    str += "</li>";
                }
                str += "</ul>";
                $("#picture").append(str);
                str = "";
        }
        function removeThis(a){
            $(a).parent().parent().remove();
        }
        $(document).ready(function(){
            $("#informationForm").validationEngine();
        });
    </script>
</head>
<body>
<form id="informationForm">
<input type="hidden" name="id" value="${itemInformation.id}"/>
<input type="hidden" name="inventoryid" value="${inventory.id}"/>
<input type="hidden" name="customid" value="${custom.id}"/>
<input type="hidden" name="supplierid" value="${supplier.id}"/>
    <br/><br/>
<table style="width: 1000px;">
    <tr>
        <td></td><td>名称:</td><td><input type="text" class="validate[required]" id="informationName" name="name" value="${itemInformation.name}"/></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td><b>产品明细</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>SKU</td><td><input type="text" class="validate[required]" id="sku" name="sku" value="${itemInformation.sku}"/></td>
    </tr>
    <tr>
        <td></td><td></td><td>
            <select>
                <option>UPC</option>
                <option>EAN</option>
                <option>ISBN</option>
                <option>eBay ReferenceID</option>
            </select>
            <input type="text"/>
       </td>
    </tr>
    <tr>
        <td></td><td>商品分类</td><td>
            <select name="itemType">
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
            <%--<input type="hidden" name="descriptionName" value="${itemInformation.description}"/>--%>
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
        </div>
        <br/><a href="javascript:void();" onclick="addDiscription();">添加描述</a></td>
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
                            <td><input type="text" name="attrName" value="${attr.attrname}"/></td>
                            <td><input type="text" name="attrValue" value="${attr.attrvalue}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </div>
        <a href="javascript:void()" onclick="addAttrabute();">添加</a></td>
    </tr>
    <tr>
        <td><b>ebay</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>相关范本</td><td>此处仅计算通过PA刊登的物品。</td>
    </tr>
    <tr>
        <td><b>供应商</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <c:if test="${supplier==null}">
            <td></td><td></td><td><a href="javascript:void()" onclick="addSupplier();">添加</a><br/>
        </c:if>
        <c:if test="${supplier!=null}">
            <td></td><td></td><td><br/>
        </c:if>
        <table id="supper">
            <c:if test="${supplier!=null}">
                <tr><td>ID:</td><td><input name="supplierId" value="${supplier.supplierid}"/></td></tr>
                <tr><td>供应商:</td><td>
                <select name="supplierName">
                <option>爱酷客</option>
                </select>
                </td></tr>
                <tr><td>价格:</td><td><input name="supplierPrice" value="${supplier.price}"/></td><td>
                <select name="supplierCurrency">
                <option>AUD</option>
                </select></td></tr>
                <tr><td>厂商SKU</td><td><input name="supperSku" value="${supplier.suppersku}"/></td></tr>
                <tr><td>备注</td><td><textarea name="supplierRemark" ${supplier.remark}></textarea></td></tr>
            </c:if>
        </table>
    </td>
    </tr>
    <tr>
        <td><b>库存</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>库存</td><td>总数量:</td>
    </tr>
    <tr>
        <td></td><td>库存预警</td><td><input type="text" name="warning" value="${inventory.warningnumber}"/></td>
    </tr>
    <tr>
        <td></td><td>均价</td><td>0.00  USD</td>
    </tr>
    <tr>
        <td></td><td>长</td><td><input type="text" name="length" value="${inventory.length}"/>&nbsp;宽:&nbsp;<input type="text" name="width" value="${inventory.width}"/>&nbsp;高:&nbsp;<input type="text" name="height" value="${inventory.height}"/></td>
    </tr>
    <tr>
        <td><b>报关信息</b></td><td></td><td></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
    <tr>
        <td></td><td>申报名1</td><td><input type="text" name="customEnglishName" value="${custom.englishname}"/></td>
    </tr>
    <tr>
        <td></td><td>申报名2</td><td><input type="text" name="customName" value="${custom.name}"/></td>
    </tr>
    <tr>
        <td></td><td>重量(kg)</td><td><input type="text" name="weight" value="${custom.weight}"/></td>
    </tr>
    <tr>
        <td></td><td>申报价值</td><td><input type="text" name="declaredValue" value="${custom.declaredvalue}"/>&nbsp;
        <select name="currencyType">

        </select>
    </tr>
    <tr>
        <td></td><td>原产地</td><td><input type="text" name="place" value="${custom.productionplace}"/></td>
    </tr>
    <tr><td colspan="3"><hr/></td></tr>
</table>
</form>
<div class="modal-footer">
    <button type="button" class="btn btn-newco" onclick="submitCommit();">保存</button>
    <button type="button" class="btn btn-default" onclick="closedialog();" data-dismiss="modal">关闭</button>
</div>


</body>
</html>
