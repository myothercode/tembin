<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/16
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var sendGetmymessage;
        $(document).ready(function(){
            $("#sendMessageTable").initTable({
                url:path + "/sendMessage/ajax/loadSendMessageList.do?",
                columnData:[
                    {title:"标题",name:"subject",width:"8%",align:"left"},
                    {title:"主题",name:"body",width:"8%",align:"left"},
                    {title:"类型",name:"messagetype",width:"8%",align:"left",format:makeOption2},
                    {title:"备注",name:"recipientid",width:"8%",align:"left"},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#sendMessageTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**查看发送消息*/
        function makeOption1(json){
            var htm="<div class=\"ui-select\" style=\"width:8px\"><a href=\"javascript:void(0)\" onclick=\"deleteSendMessage("+json.transactionid+","+json.messagetype+");\">删除</a></div>";
            return htm;
        }
        function deleteSendMessage(transactionid,messagetype){
            var url=path+"/sendMessage/ajax/removesendMessage.do?transactionid="+transactionid+"&messagetype="+messagetype;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        refreshTable();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function makeOption2(json){
            if(json.messagetype==1){
                return "纠纷类主动发送消息";
            }
            if(json.messagetype==2){
                return "自动发送消息";
            }
            if(json.messagetype==3){
                return "评价";
            }
            if(json.messagetype==4){
                return "ebay累发送消息";
            }
        }
    </script>
</head>
<body>

    <div class="new_all">
        <div class="here">当前位置：首页 &gt; 客户管理 &gt; <b>消息模板</b></div>
        <div class="a_bal"></div>
        <div class="new">
            <div class="Contentbox">
                <div>
                    <!--综合开始 -->
                    <div class="new_usa">
                        <div class="newds">
                            <div class="newsj_left">
                                <%--<span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox"></span>--%>
                                <%--<span class="newusa_ici_del">检查eBay费</span><div class="page_num">显示20条</div>--%>
                            </div>
                        </div>
                        <div class="tbbay"><a href="#">添加模板</a></div>
                    </div>
                    <div id="sendMessageTable"></div>
                    <!--综合结束 -->
                    <%--<table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
                        <tbody><tr>
                            <td width="5%" bgcolor="#F7F7F7"></td>
                            <td width="25%" height="30" bgcolor="#F7F7F7"><strong>标题</strong></td>
                            <td width="40%" height="30" bgcolor="#F7F7F7"><strong>主题<b style="color:#8BB51B"></b></strong></td>
                            <td width="15%" align="center" bgcolor="#F7F7F7">备注</td>
                            <td width="15%" align="center" bgcolor="#F7F7F7"><strong>操作</strong></td>
                        </tr>
                        <tr>
                            <td width="5%" valign="top" style="color:#5E93D5"><input type="checkbox" name="checkbox" id="checkbox"></td>
                            <td width="20%" style="color:#5E93D5">退回货物</td>
                            <td width="30%" valign="top">RMA and address information for returning package
                            </td>
                            <td width="15%" align="center">topshoper998</td>

                            <td width="15%" align="center"><div class="ui-select" style="width:8px">
                                <select>
                                    <option value="AK">删除</option>
                                    <option value="AK">动作</option>
                                </select>
                            </div></td>
                        </tr>
                        </tbody></table>--%>
                    <%--<div class="page_newlist">
                        <div class="page_num">显示20条</div>共 <span style="color:#F00">3000</span> 条记录 <span style="color:#F00">300</span> 页
                    </div>--%>
                    <%--<div class="maage_page">
                        <li>&lt;</li>
                        <li class="page_cl">1</li>
                        <li>2</li>
                        <li>3</li>
                        <li>4</li>
                        <dt>&gt;</dt>
                    </div>--%>
                </div>

            </div>
        </div>
    </div>

</body>

</html>
