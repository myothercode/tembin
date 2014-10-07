<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/4
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title>系统通知</title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript" src=<c:url value ="/js/sitemessage/siteMessage.js" /> ></script>
</head>
<body>

<div class="">
    <div class="new_all">
        <div class="here">当前位置：首页 > 系统设置 > <b>系统消息</b></div>
        <div class="a_bal"></div>
        <div class="new">
            <div class="new_tab_ls">
                <dt id=menu1 class=new_tab_1>系统消息</dt>
            </div>
            <div class="newds">
                <div class="newsj_left"><br>

                    <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" /></span>

                    <div class="numlist">
                        <div class="ui-select" style="margin-top:1px; width:10px">
                            <select>
                                <c:forEach items="${mtype}" var = "ty">
                                    <option value="${ty.key}">${ty.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                        </div>
                    <span class="newusa_ici_del">标记为已读</span>
                    <span class="newusa_ici_del">全部为已读</span>
                    <%--<span class="newusa_ici_del">新建文件夹</span>--%>
                </div>
                <div>
                </div>
               <%-- <div class="tbbay"><a data-toggle="modal" href="#myModal" class="">同步eBay</a></div>--%>
            </div>
        </div>
<div>&nbsp;</div>
        <div  id="siteMessageDiv">

        </div>

            </div>
        </div>



    </div>






</body>
</html>
