<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/16
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="content">
    <div class="new_all">
        <div class="here">当前位置：首页 &gt; 刊登管理 &gt; <b>虚拟SKU列表</b></div>
        <div class="a_bal"></div>
        <div class="new">
            <h2>eBay官方调整E邮宝接口，截至九月底将停止使用原E邮宝，需要您重新授权E邮宝</h2>
            <div class="Contentbox">
                <div>
                    <!--综合开始 -->
                    <div class="new_usa">
                        <li class="new_usa_list"><span class="newusa_i">按标签看：全部</span><span class="newusa_ici">无标签</span><a href="#"><span class="newusa_ici_1">有电池</span></a><a href="#"><span class="newusa_ici_1">无电池</span></a></li>
                        <li class="new_usa_list"><span class="newusa_i">信息状态：全部</span><span class="newusa_ici">无图片</span><a href="#"><span class="newusa_ici_1">无报关信息</span></a><a href="#"><span class="newusa_ici_1">信息不全</span></a></li>
                        <div class="newsearch">
                            <span class="newusa_i">搜索内容：全部</span>
<span id="sleBG">
<span id="sleHid">
<select name="type" class="select">
    <option selected="selected">选择类型</option>
    <option value="1">图书</option>
    <option value="2">音像</option>
</select>
</span>
</span>
                            <div class="vsearch">
                                <input name="" type="text" class="key_1"><input name="newbut" type="button" class="key_2"></div>
                        </div>
                        <div class="newds">
                            <div class="newsj_left">

                                <span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"></span>


                                <span class="newusa_ici_del">添加商品</span>
                                <span class="newusa_ici_del">导入商品</span>
                                <span class="newusa_ici_del">导出商品</span>
                                <span class="newusa_ici_del">添加标签</span>
                                <span class="newusa_ici_del">删除商品</span>
                                <span class="newusa_ici_del">修改商品分类</span>
                            </div><div class="page_num" style="margin-left:0px;">显示20条</div>
                        </div>
                    </div>
                    <!--综合结束 -->
                    <table width="100%" border="0" align="left" cellspacing="0">
                        <tbody><tr>
                            <td width="5%" bgcolor="#F7F7F7"></td>
                            <td width="10%" height="30" bgcolor="#F7F7F7"><strong>商品 / <b style="color:#8BB51B">SKU</b></strong></td>
                            <td bgcolor="#F7F7F7">商品名称</td>
                            <td width="20%" bgcolor="#F7F7F7"><strong>体积（长*宽*高）</strong></td>
                            <td width="10%" align="center" bgcolor="#F7F7F7"><strong>重量</strong></td>
                            <td width="10%" align="center" bgcolor="#F7F7F7"><strong>操作</strong></td>
                        </tr>
                        <tr>
                            <td width="5%" valign="top" style="color:#5E93D5"><input type="checkbox" name="checkbox" id="checkbox"></td>
                            <td width="10%" valign="top">
                                <span style="color:#8BB51B; width:100%; float:left">ZQ00215</span>
                            </td>
                            <td valign="top">内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容</td>
                            <td>10.00cm*10.00cm*10.00cm</td>
                            <td align="center">10g</td>
                            <td align="center"><div class="ui-select" style="width:8px">
                                <select>
                                    <option value="AK">删除</option>
                                    <option value="AK">删除</option>
                                </select>
                            </div></td>
                        </tr>
                        <tr>
                            <td width="5%" valign="top" style="color:#5E93D5"><input type="checkbox" name="checkbox" id="checkbox"></td>
                            <td width="10%" valign="top">
                                <span style="color:#8BB51B; width:100%; float:left">ZQ00215</span>
                            </td>
                            <td valign="top">内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容内容</td>
                            <td>10.00cm*10.00cm*10.00cm</td>
                            <td align="center">10g</td>
                            <td align="center"><div class="ui-select" style="width:8px">
                                <select>
                                    <option value="AK">删除</option>
                                    <option value="AK">删除</option>
                                </select>
                            </div></td>
                        </tr>
                        </tbody></table>
                    <div class="page_newlist">
                        <div class="page_num">显示20条</div>共 <span style="color:#F00">3000</span> 条记录 <span style="color:#F00">300</span> 页
                    </div>
                    <div class="maage_page">
                        <li>&lt;</li>
                        <li class="page_cl">1</li>
                        <li>2</li>
                        <li>3</li>
                        <li>4</li>
                        <dt>&gt;</dt>
                    </div>

                    <!--结束 -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
