<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/9/19
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title>系统账户管理</title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript" src=<c:url value ="/js/systemabout/user/sysusermanager.js" /> ></script>
</head>
<body>
<div class="new_all">
<div class="here">当前位置：首页 > 系统设置 > <b>系统账户</b></div>
<div class="a_bal"></div>
<div class="new">

<div class="new_tab_ls">
    <dt style="display: none" id=menu1 class=new_tab_2 onmouseover="setTab('menu',1,5)">账户信息yy</dt>
    <dt id=menu3 class=new_tab_1 onclick="setTab('menu',3,5)">账户管理</dt>
    <dt id=menu4 class=new_tab_2 onclick="setTab('menu',4,5)">角色设置</dt>
    <dt id=menu5 class=new_tab_2 onclick="setTab('menu',5,5)">系统初始化</dt>
    <dt id=menu2 class=new_tab_2 onclick="setTab('menu',2,5)">修改密码</dt>
</div>
<div class=Contentbox>
<div>
<div id=con_menu_1 class=hover>

</div>



<div style="DISPLAY: none" id=con_menu_2>
    <form action="" method="get">
        <table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
            <tr>
                <td width="10%" height="40" align="right">原始密码</td>
                <td width="82%" height="40"><input name="id" type="text" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right" >新密码</td>
                <td height="40"><input name="newps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right">确认密码</td>
                <td height="40"><input name="ps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td height="40" align="right">&nbsp;</td>
                <td height="40"><input name="提交" type="submit" class="newps_put" value="保 存"></td>
            </tr>
        </table></form>
</div>
<div style="DISPLAY: none" id=con_menu_3>
    <div class="new_usa" style="margin-top:20px;">
        <div class="newds">
            <div class="newsj_left"><span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" /></span>
                <span class="newusa_ici_open">显示已禁用的成员</span><div id="newtipi">
                    <li><a href="#">显示20条</a>
                        <ul>
                            <li><a href="#">自定义显示</a></li>
                            <li><a href="#">自定义显示</a></li>
                            <li><a href="#">自定义显示</a></li>
                        </ul>
                    </li>
                </div>
            </div><div class="tbbay"><a data-toggle="modal" href="#myModal" class="">添加成员</a></div>
        </div>
    </div>
    <div id="accountManager">

    </div>
    <table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
        <tr>
            <td width="20" height="30" bgcolor="#F7F7F7"><strong>姓名</strong></td>
            <td width="20" height="30" bgcolor="#F7F7F7"><strong>角色<b style="color:#8BB51B"></b></strong></td>
            <td width="20" align="center" bgcolor="#F7F7F7">邮箱</td>
            <td width="20" align="center" bgcolor="#F7F7F7">电话</td>
            <td width="10%" align="center" bgcolor="#F7F7F7">状态</td>
            <td width="10%" align="center" bgcolor="#F7F7F7">操作</td>
        </tr>
        <tr>
            <td width="20" style="color:#5E93D5">Fred</td>
            <td width="20">系统管理员</td>
            <td width="20" align="center">1111111111.com</td>
            <td width="20" align="center">15999999999</td>
            <td align="center"><img src="../../img/new_no.png" ></td>
            <td align="center">  <div class="ui-select" style="width:8px">
                <select>
                    <option value="AK">删除</option>
                    <option value="AK">动作</option>
                </select>
            </div></td>
        </tr>
        <tr>
            <td width="20" style="color:#5E93D5">Fred</td>
            <td width="20">系统管理员</td>
            <td width="20" align="center">1111111111.com</td>
            <td width="20" align="center">15999999999</td>
            <td align="center"><img src="../../img/new_yes.png" ></td>
            <td align="center">  <div class="ui-select" style="width:8px">
                <select>
                    <option value="AK">删除</option>
                    <option value="AK">动作</option>
                </select>
            </div></td>
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

<div style="DISPLAY: none" id=con_menu_4>
    <div class="new_usa" style="margin-top:20px;">
        <div class="newds">
            <div class="newsj_left">
            </div><div class="tbbay"><a data-toggle="modal" href="#myModal" class="">添加角色</a></div>
        </div>
    </div>
    <table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
        <tr>
            <td width="45%" height="30" bgcolor="#F7F7F7"><strong>角色名称</strong></td>
            <td width="45%" height="30" bgcolor="#F7F7F7"><strong>描述<b style="color:#8BB51B"></b></strong></td>
            <td width="10%" align="center" bgcolor="#F7F7F7">操作</td>
        </tr>
        <tr>
            <td width="45%" style="color:#5E93D5">Fred</td>
            <td width="45%">系统管理员</td>
            <td align="center">  <div class="ui-select" style="width:8px">
                <select>
                    <option value="AK">删除</option>
                    <option value="AK">动作</option>
                </select>
            </div></td>
        </tr>
        <tr>
            <td width="45%" style="color:#5E93D5">Fred</td>
            <td width="45%">系统管理员</td>
            <td align="center">  <div class="ui-select" style="width:8px">
                <select>
                    <option value="AK">删除</option>
                    <option value="AK">动作</option>
                </select>
            </div></td>
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

<div style="DISPLAY: none" id=con_menu_5>
    <h2 style="margin-top:20px;">系统初始化功能将会把您账号下的所有信息全部清空，请谨慎操作。</h2>

    <form action="" method="get">
        <table width="100%" border="0" align="left" cellspacing="0" >
            <tr>
                <td width="10%" height="40" align="right" >请输入密码</td>
                <td height="40"><input name="newps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right">再次输入密码</td>
                <td height="40"><input name="ps" type="password" class="newps"></td>
            </tr>
            <tr>
                <td width="10%" height="40" align="right"></td>
                <td height="40"><input name="" type="checkbox" value="" style="margin-left:12px;"> 我已了解风险</td>
            </tr>
            <tr>
                <td height="40" align="right">&nbsp;</td>
                <td height="40"><input name="提交" type="submit" class="newps_put" value="确认初始化"></td>
            </tr>
        </table></form>
</div>
<!--结束 -->
</div>
</div>
</div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">退货政策</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="input1" class="newdt control-label">名称</label>
                        <div class="col-lg-10">
                            <div class="ui-select span5">
                                <select>
                                    <option value="AK">美国</option>
                                    <option value="HI">Hawaii</option>
                                    <option value="CA">California</option>
                                    <option value="NV">Nevada</option>
                                    <option value="OR">Oregon</option>
                                    <option value="WA">Washington</option>
                                    <option value="AZ">Arizona</option>
                                </select>
                            </div>
                        </div>

                        <label for="input1" class="newdt control-label" style="margin-top:9px;">站点</label>
                        <div class="col-lg-10">
                            <div class="ui-select span5" style="margin-top:9px;">
                                <select>
                                    <option value="AK">美国</option>
                                    <option value="HI">Hawaii</option>
                                    <option value="CA">California</option>
                                    <option value="NV">Nevada</option>
                                    <option value="OR">Oregon</option>
                                    <option value="WA">Washington</option>
                                    <option value="AZ">Arizona</option>
                                </select>
                            </div>
                        </div>

                        <label for="input1" class="newdt control-label" style="margin-top:9px;">退货政策</label>
                        <div class="col-lg-10">
                            <div class="ui-select span5" style="margin-top:9px;">
                                <select>
                                    <option value="AK">美国</option>
                                    <option value="HI">Hawaii</option>
                                    <option value="CA">California</option>
                                    <option value="NV">Nevada</option>
                                    <option value="OR">Oregon</option>
                                    <option value="WA">Washington</option>
                                    <option value="AZ">Arizona</option>
                                </select>
                            </div>
                        </div>

                        <label for="input1" class="newdt control-label" style="margin-top:9px;">退货天数</label>
                        <div class="col-lg-10">
                            <div class="ui-select span5" style="margin-top:9px;">
                                <select>
                                    <option value="AK">美国</option>
                                    <option value="HI">Hawaii</option>
                                    <option value="CA">California</option>
                                    <option value="NV">Nevada</option>
                                    <option value="OR">Oregon</option>
                                    <option value="WA">Washington</option>
                                    <option value="AZ">Arizona</option>
                                </select>
                            </div>
                        </div>

                        <label for="input1" class="newdt control-label" style="margin-top:9px;">退款方式</label>
                        <div class="col-lg-10">
                            <div class="ui-select span5" style="margin-top:9px;">
                                <select>
                                    <option value="AK">美国</option>
                                    <option value="HI">Hawaii</option>
                                    <option value="CA">California</option>
                                    <option value="NV">Nevada</option>
                                    <option value="OR">Oregon</option>
                                    <option value="WA">Washington</option>
                                    <option value="AZ">Arizona</option>
                                </select>
                            </div>
                        </div>

                        <label for="input1" class="newdt control-label" style="margin-top:9px;">退货费用由谁承担</label>
                        <div class="col-lg-10">
                            <div class="ui-select" style="margin-top:9px;">
                                <select>
                                    <option value="AK">美国</option>
                                    <option value="HI">Hawaii</option>
                                    <option value="CA">California</option>
                                    <option value="NV">Nevada</option>
                                    <option value="OR">Oregon</option>
                                    <option value="WA">Washington</option>
                                    <option value="AZ">Arizona</option>
                                </select>
                            </div>
                        </div>


                        <label for="input1" class="col-lg-2 control-label"  style="margin-top:9px;">退货政策详情</label>
                        <div class="col-lg-10" style="margin-top:9px;">
                            <textarea class="form-control" cols="" rows="2" style="width:660px;"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary">保存</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    </div>
</body>
</html>
