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

<!-- bootstrap -->
<link href="<c:url value ="/css/bootstrap/bootstrap.css"/>" rel="stylesheet"/>
<link href="<c:url value ="/css/bootstrap/bootstrap-overrides.css"/>" type="text/css" rel="stylesheet"/>
<!-- global styles -->
<link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/layout.css"/> "/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/elements.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/css/compiled/icons.css"/>"/>
<!-- libraries -->
<link href="<c:url value ="/css/lib/font-awesome.css"/>" type="text/css" rel="stylesheet" />
<!-- this page specific styles -->
<link rel="stylesheet" href="<c:url value ="/css/compiled/gallery.css"/>" type="text/css" media="screen" />

<link rel="stylesheet" type="text/css" href="<c:url value ="/css/basecss/base.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/js/validation/validationEngine.jquery.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>

<script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/jquery/jquery-migrate-1.2.1.min.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/jquery/jquery.cookie.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/base.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/util.js" /> ></script>
<%--<script type="text/javascript" src=<c:url value ="/js/lhgdialog/lhgdialog.min.js" /> ></script>--%>
<script type="text/javascript" src=<c:url value ="/js/lhgdialog/lhgdialog.js?skin=idialog" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/My97DatePicker/WdatePicker.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/jquery-blockui/jquery.blockUI.min.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/table/jquery.table.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/validation/jquery.validationEngine.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/validation/jquery.validationEngine-zh_CN.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
<script src="<c:url value ="/js/bootstrap.min.js"/>"></script>
<script src="<c:url value ="/js/theme.js"/>"></script>

<%
    String rootPath = request.getContextPath();
    String _token = (String) request.getSession().getAttribute("_token")==null?"":(String) request.getSession().getAttribute("_token");
%>
<script type="text/javascript">
    var path = window["path"] = '<%=rootPath%>';
    var nowDateTime="<fmt:formatDate value="${nowDateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>";
    var _token="<%=_token%>";
    var itemListIconUrl_="${itemListIconUrl}";
    var serviceItemUrl = "${serviceItemUrl}";

    var _sku=null;
    var _jscacheVersion="${jscacheVersion}";


    /**页面加载完成后执行的方法*/
    $(document).ready(function(){
        showBanner_(false);
        /**滚动条滚动到顶部和底部的时候触发事件显示和隐藏banner栏*/
        $(window).scroll(function() {
            if($(document).scrollTop()>0){//如果不是在顶端，隐藏顶部栏
                hideBanner_();

            };

            if($(document).scrollTop() <= 0){//如果到顶端，显示顶部栏
                showBanner_(true);

            }

        });
    });
    var bbs_=true;
    var bbc_=true;
    function showBanner_(o){
        if(parent.document==document){return}
            $("#navbar",parent.document.body).show();
            $("#contentMaindiv",parent.document.body).css("top","58px");
        if(!o){
            $("#contentMaindiv",parent.document.body).css("height",(parseInt(parent.mainHei_)-60)+"px");

            return;
        }
if(bbs_){
    var parentDivH = $("#contentMaindiv",parent.document.body).css("height").replace("px");
    $("#contentMaindiv",parent.document.body).css("height",(parseInt(parentDivH)-60)+"px");
    bbc_=true;
    bbs_=false;
}
    }
    function hideBanner_(){
        if(parent.document==document){return;}
        $("#navbar",parent.document.body).hide();
        $("#contentMaindiv",parent.document.body).css("top","0px");

        if(bbc_){
            var parentDivH = $("#contentMaindiv",parent.document.body).css("height").replace("px");
            $("#contentMaindiv",parent.document.body).css("height",(parseInt(parentDivH)+60)+"px");
            bbc_=false;
            bbs_=true;
        }

    }
    /*function zjh_(pheight){
        if(parent.document==document){return;}
        var wi=getCurrPageWH();
        if(pheight==null){
            $("#contentMaindiv",parent.document.body).css("height",(parseInt(wi.eHeight)+65)+"px");
            $(parent.document.body).css("height",(parseInt(wi.eHeight)+65)+"px");
        }else{
            var th_= parseInt(wi.eHeight);
            $("#contentMaindiv",parent.document.body).css("height",(th_+pheight+100)+"px");
            var eHeight1=(parent.document.documentElement.scrollHeight > parent.document.documentElement.clientHeight) ? parent.document.documentElement.scrollHeight : parent.document.documentElement.clientHeight;
            $(parent.document.body).css("height",(eHeight1+pheight+10)+"px");
        }

    }
    function jsh_(){
        if(parent.document==document){return}
        var wi=getCurrPageWH();
        $("#contentMaindiv",parent.document.body).css("height",(parseInt(wi.eHeight)-65)+"px");
        $(parent.document.body).css("height",(parseInt(wi.eHeight)-65)+"px");
    }*/

    /**关闭弹窗的时候会统一执行的方法*/
    function doitAfterCloseLhgDiolag(){
        Base.token();
    }

    /**清除本地缓存*/
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