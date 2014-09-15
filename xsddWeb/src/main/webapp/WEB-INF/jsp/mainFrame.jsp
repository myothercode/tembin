<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/15
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<html>
<head>
    <%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
    <title></title>
    <script type="text/javascript">
        $(document).ready(function(){
            initLeftMenuBar();
        });
    </script>
</head>
<body>
<!-- navbar -->
<header class="navbar navbar-inverse" role="banner">
    <div class="navbar-header">
        <button class="navbar-toggle" type="button" data-toggle="collapse" id="menu-toggler">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#"><img src="<c:url value ="/img/logo.png"/> "></a>
    </div>
    <ul class="nav navbar-nav pull-right hidden-xs">
        <li class="hidden-xs hidden-sm">
            <input class="search" type="text" />
        </li>
        <li class="notification-dropdown hidden-xs hidden-sm">
            <a href="#" class="trigger">
                <i class="icon-warning-sign"></i>
                <span class="count">8</span>
            </a>
            <div class="pop-dialog">
                <div class="pointer right">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <div class="body">
                    <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                    <div class="notifications">
                        <h3>You have 6 new notifications</h3>
                        <a href="#" class="item">
                            <i class="icon-signin"></i> New user registration
                            <span class="time"><i class="icon-time"></i> 13 min.</span>
                        </a>
                        <a href="#" class="item">
                            <i class="icon-signin"></i> New user registration
                            <span class="time"><i class="icon-time"></i> 18 min.</span>
                        </a>
                        <a href="#" class="item">
                            <i class="icon-envelope-alt"></i> New message from Alejandra
                            <span class="time"><i class="icon-time"></i> 28 min.</span>
                        </a>
                        <a href="#" class="item">
                            <i class="icon-signin"></i> New user registration
                            <span class="time"><i class="icon-time"></i> 49 min.</span>
                        </a>
                        <a href="#" class="item">
                            <i class="icon-download-alt"></i> New order placed
                            <span class="time"><i class="icon-time"></i> 1 day.</span>
                        </a>
                        <div class="footer">
                            <a href="#" class="logout">View all notifications</a>
                        </div>
                    </div>
                </div>
            </div>
        </li>
        <li class="notification-dropdown hidden-xs hidden-sm">
            <a href="#" class="trigger">
                <i class="icon-envelope"></i>
            </a>
            <div class="pop-dialog">
                <div class="pointer right">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <div class="body">
                    <a href="#" class="close-icon"><i class="icon-remove-sign"></i></a>
                    <div class="messages">
                        <a href="#" class="item">
                            <img src="../../img/contact-img.png" class="display" />
                            <div class="name">Alejandra Galván</div>
                            <div class="msg">
                                There are many variations of available, but the majority have suffered alterations.
                            </div>
                            <span class="time"><i class="icon-time"></i> 13 min.</span>
                        </a>
                        <a href="#" class="item">
                            <img src="../../img/contact-img2.png" class="display" />
                            <div class="name">Alejandra Galván</div>
                            <div class="msg">
                                There are many variations of available, have suffered alterations.
                            </div>
                            <span class="time"><i class="icon-time"></i> 26 min.</span>
                        </a>
                        <a href="#" class="item last">
                            <img src="../../img/contact-img.png" class="display" />
                            <div class="name">Alejandra Galván</div>
                            <div class="msg">
                                There are many variations of available, but the majority have suffered alterations.
                            </div>
                            <span class="time"><i class="icon-time"></i> 48 min.</span>
                        </a>
                        <div class="footer">
                            <a href="#" class="logout">View all messages</a>
                        </div>
                    </div>
                </div>
            </div>
        </li>

        <li class="settings hidden-xs hidden-sm">
            <a href="../../personal-info.html" role="button">
                <i class="icon-cog"></i>
            </a>
        </li>
        <li class="settings hidden-xs hidden-sm">
            <a href="../../signin.html" role="button">
                <i class="icon-share-alt"></i>
            </a>
        </li>
    </ul>
</header>
<!-- end navbar -->

<!-- sidebar -->
<div id="sidebar-nav">
    <ul id="dashboard-menu">
        <li>
            <a class="dropdown-toggle" href="#">
                <i class="icon-home"></i>
                <span>首页</span>
                <i class="icon-chevron-down"></i>
            </a>
            <ul class="submenu">
                <li><a href="../../user-list.html">User list</a></li>
                <li><a href="../../new-user.html">New user form</a></li>
                <li><a href="../../user-profile.html">User profile</a></li>
            </ul>
        </li>
        <li class="active">
            <a class="dropdown-toggle" href="#">
                <div class="pointer">
                    <div class="arrow"></div>
                    <div class="arrow_border"></div>
                </div>
                <i class="icon-picture"></i>
                <span>刊登管理</span>
                <i class="icon-chevron-down"></i>
            </a>

            <ul class="active submenu">
                <li><a href="../../form-showcase.html" class="active">刊登</a></li>




                <li><a href="../../form-wizard.html">范本</a></li>
                <li><a href="../../form-wizard.html">定时</a></li>
                <li><a href="../../form-wizard.html">刊登</a></li>
                <li><a href="../../form-wizard.html">重新刊登</a></li>
            </ul>
        </li>

        <li>
            <a class="dropdown-toggle" href="#">
                <i class="icon-group"></i>
                <span>客服管理</span>
                <i class="icon-chevron-down"></i>
            </a>
            <ul class="submenu">
                <li><a href="../../user-list.html">User list</a></li>
                <li><a href="../../new-user.html">New user form</a></li>
                <li><a href="../../user-profile.html">User profile</a></li>
            </ul>
        </li>
        <li>
            <a class="dropdown-toggle" href="#">
                <i class="icon-edit1"></i>
                <span>商品管理</span>
                <i class="icon-chevron-down"></i>
            </a>
            <ul class="submenu">
                <li><a href="../../form-showcase.html">Form showcase</a></li>
                <li><a href="../../form-wizard.html">Form wizard</a></li>
            </ul>
        </li>
        <li>
            <a class="dropdown-toggle" href="#">
                <i class="icon-signal"></i>
                <span>销售管理</span>
                <i class="icon-chevron-down"></i>
            </a>
            <ul class="submenu">
                <li><a href="../../user-list.html">User list</a></li>
                <li><a href="../../new-user.html">New user form</a></li>
                <li><a href="../../user-profile.html">User profile</a></li>
            </ul>
        </li>
    </ul>
</div>

<div class="content"></div>


</body>
</html>
