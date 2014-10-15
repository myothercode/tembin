<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 2014/7/27j
  Time: 18:09
  To change this template use File | Settings | File Templates.
  <html xmlns="http://www.w3.org/1999/xhtml">
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<link rel="stylesheet" type="text/css" href="<c:url value ="/css/basecss/base.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/js/validation/validationEngine.jquery.css" />"/>
<script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/base.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/util.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/lhgdialog/lhgdialog.min.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/My97DatePicker/WdatePicker.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/jquery-blockui/jquery.blockUI.min.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/table/jquery.table.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/validation/jquery.validationEngine.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/validation/jquery.validationEngine-zh_CN.js" /> ></script>
<link rel="stylesheet" type="text/css" href="<c:url value ="/css/bootstrap2/bootstrap.min.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/css/bootstrap2/bootstrap-responsive.min.css" />"/>
<script type="text/javascript" src=<c:url value ="/css/bootstrap2/bootstrap.min.js" /> ></script>

<%
    String rootPath = request.getContextPath();
    String _token = (String) request.getSession().getAttribute("_token")==null?"":(String) request.getSession().getAttribute("_token");
%>
<script type="text/javascript">
    var path = window["path"] = '<%=rootPath%>';
    var nowDateTime="<fmt:formatDate value="${nowDateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>";
    var _token="<%=_token%>";

    var _sku=null;
    var _jscacheVersion="${jscacheVersion}";

    function cleanLocalStorageFunction() {
        try {
            var __v = __getjv();
            if(_jscacheVersion==""){return;}
            if (__v == null) {
                __setjv();
            } else {
                if (__v != _jscacheVersion ) {
                    localStorage.clear();
                    __setjv();
                }
            }

        }catch (e){alert(e+"处理本地缓存失败！")}
    }

        function __setjv(){
            localStorage.setItem("tiancheng_jscacheVersion",_jscacheVersion);
        }
        function __getjv(){
            return localStorage.getItem("tiancheng_jscacheVersion");
        }
        cleanLocalStorageFunction();




</script>