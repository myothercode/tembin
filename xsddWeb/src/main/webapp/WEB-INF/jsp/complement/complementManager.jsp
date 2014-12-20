<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src=<c:url value ="/js/complement/complementManager.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/complement/setEbayComplement.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/complement/inventoryComplement.js" /> ></script>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>自动补数</b></div>
    <div class="a_bal"></div>
    <div class="tbbay" id="showAddButton" style="background-image:url(null);position: absolute;top: 60px;right: 40px;z-index: 10000;">
        <img id="addimg" onclick="addAll()">
        <%--<a data-toggle="modal" href="javascript:void(0)" class=""  onclick="addModel()">新增</a>--%>
    </div>
    <div class="new">
        <div class="new_tab_ls" id="selectModel">
            <dt id=menu1 name="setEbayComplement" class=new_tab_1 onclick="setAssessTab(this)">自动补数设置</dt>
            <dt id=menu2 name="complementManager" class=new_tab_2 onclick="setAssessTab(this)">自动补数规则</dt>
            <dt id=menu3 name="otherComplementManager" class=new_tab_2 onclick="setAssessTab(this)">库存补数规则</dt>
            <dt id=menu4 name="complementLog" class=new_tab_2 onclick="setAssessTab(this)">补数记录</dt>
        </div>
        <div class=Contentbox id="Contentbox" style="float:none;">
            <div class="new_usa" style="margin-top:20px;">
                <div id="setEbayComplement"></div>
                <div id="complementManager" style="display: none;"></div>
                <div id="otherComplementManager" style="display: none;"></div>
                <div id="complementLog" style="display: none;"></div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
