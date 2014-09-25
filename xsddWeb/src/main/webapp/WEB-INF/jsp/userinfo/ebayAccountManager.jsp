<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/25
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>

<html>
<head>
    <title>帐号绑定</title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/bindEbayAccount/ebayAccountManager.js" /> ></script>
</head>
<body>
<div >
<div class="new_all">
    <div class="here">当前位置：首页 > 系统设置 > <b>帐号绑定</b></div>
    <div class="a_bal"></div>
    <div class="new">

        <div class="new_tab_ls">
            <dt id=menu1 class=new_tab_1 onclick="setTab('menu',1,2)">eBay帐号</dt>
            <dt id=menu2 class=new_tab_2 onclick="setTab('menu',2,2)">Paypal帐号</dt>
        </div>
        <div class=Contentbox>
            <div>
                <div id=con_menu_1 class=hover>
                    <div class="new_usa"><div class="tbbay" style="margin-top:20px;"><a data-toggle="modal" href="#myModal" class="">添加ebay帐号</a></div>
                        <div class="tbbay" style="margin-top:20px;"><a data-toggle="modal" href="#myModal" class="">E邮宝授权</a></div>
                    </div>
                    <div id="ebayManager"></div>
                </div>



                <div style="DISPLAY: none" id=con_menu_2>
                    <div class="new_usa">
                        <div class="tbbay" style="margin-top:20px;"><a data-toggle="modal" href="#myModal" class="">添加帐号</a></div>
                    </div>
                    <table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
                        <tr>
                            <td width="40%" height="30" bgcolor="#F7F7F7"><strong>Paypal帐号</strong></td>
                            <td width="40%" align="center" bgcolor="#F7F7F7">状态</td>
                            <td width="20%" align="" bgcolor="#F7F7F7"><strong>操作</strong></td>
                        </tr>
                        <tr>
                            <td style="color:#5E93D5">topenjoyed@gmail.com</td>
                            <td align="center"><img src="../../img/new_yes.png" width="22" height="22"></td>

                            <td align="center"><span class="newusa_ici"><b style="color:#FF6060;font-weight: normal;">停用</b>帐号</span></td>
                        </tr>
                        <tr>
                            <td style="color:#5E93D5">topenjoyed@gmail.com</td>
                            <td align="center"><img src="../../img/new_no.png" width="22" height="22"></td>

                            <td align="center"><span class="newusa_ici"><b style="color:#93B937;font-weight: normal;">启用</b>帐号</span></td>
                        </tr>
                    </table>
                    <div class="page_newlist">
                        <div>
                            <div id="newtipi">
                                <li><a href="#">显示20条</a>
                                    <ul>
                                        <li><a href="#">自定义显示</a></li>
                                        <li><a href="#">自定义显示</a></li>
                                        <li><a href="#">自定义显示</a></li>
                                    </ul>
                                </li>
                            </div></div> 共 <span style="color:#F00">3000</span> 条记录 <span style="color:#F00">300</span> 页
                    </div>
                    <div class="maage_page">
                        <li><</li>
                        <li class="page_cl">1</li>
                        <li>2</li>
                        <li>3</li>
                        <li>4</li>
                        <dt>></dt>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
