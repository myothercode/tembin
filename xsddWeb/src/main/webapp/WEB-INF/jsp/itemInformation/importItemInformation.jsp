<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/12
  Time: 11:39
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
        var api = frameElement.api, W = api.opener;
        function upload(obj)
        {
            var f=$(obj).serialize();
            var ff= f.split("=");
            if(ff[1]==""){
                alert("请先导入Excel");
            }else{
                obj.action =path+"/information/ajax/importInformation.do";
                obj.submit();
            }

        }
        $(document).ready(function(){
            var flag=document.getElementById("flag");
            if("true"==$(flag).val()){
                alert("上传成功,请刷新界面");
                W.itemInformation.close();
            }
        });
    </script>
</head>
<body>
<br/><br/>
<%--<a href="<c:url value ="/upload/upload.xls"/> "><font color="blue">请先下载excel模板</font></a>
<input id="flag" type="hidden" name="flag" value="${flag}">--%>
<%--<form id="improtForm" action="/information/ajax/importInformation.do" method="post" enctype="multipart/form-data" >
    <input id="a1" type="file" name="file" style="display:none"/>
    <input type=button value="导入excel" class="net_put_2" onclick=a1.click()>
    <input type="submit" class="net_put" value="导入" onclick="upload(this.form)">
</form>--%>
<input id="flag" type="hidden" name="flag" value="${flag}">
<div class="modal-body" style="width:480px; ">
    <form id="improtForm" class="form-horizontal"
          method="post" enctype="multipart/form-data" ><%--<form class="form-horizontal" role="form">--%>
        <table width="100%" border="0" style="margin-left:40px;">
            <tbody>
            <tr>
                <td  ><strong>请下载提供的商品导入模板,填写好后上传&nbsp;&nbsp;</strong><a href="<c:url value ="/upload/upload1.xls"/> "><font color="blue">商品导入格式-普通商品.xls 下载</font></a>
                    </td>
            </tr>
            <tr>

                <td align="right">
                    <input id="a1" type="file" name="file" style="display:none"/>
                    <input type=button value="导入excel" class="net_put_2" onclick=a1.click()>
                    <input type="submit" class="net_put" value="导入" onclick="upload(this.form)"></td>

            </tr>
           <%-- <tr>
                <td height="28" align="right">&nbsp;</td>
                <td height="28" style=" padding-top:22px;"><button type="button" class="net_put">保存</button><button type="button" class="net_put_1" data-dismiss="modal">关闭</button></td>
                <td height="28">&nbsp;</td>
            </tr>--%>
            </tbody></table>

    </form></div>
</body>
</html>
