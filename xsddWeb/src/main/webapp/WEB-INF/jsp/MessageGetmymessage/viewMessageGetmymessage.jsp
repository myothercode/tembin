<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/7
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>

</head>
<body>
      <%--<div style="text-align: right">
            <input type="button" value="系统回复" onclick="sendMessage();" />
      </div>
    <c:forEach items="${messages}" var="message">
        ${message.textHtml}
    </c:forEach>--%>
      <div style="padding: 30px;">
          <table width="100%" border="0" style="margin-top:-20px;">
              <tbody><tr>
                  <td style="line-height:30px;"><span style=" color:#2395F3; font-size:24px; font-family:Arial, Helvetica, sans-serif">aasla_karih</span> [来自eBay账号：containyou 的买家]</td>
              </tr>
              </tbody></table>
          <div class="newbook" style="width: 772px;">
              <p style="text-align: right;">aasla_karih 2014-08-20 22:38</p>
              <c:forEach items="${addMessage1}" var="addMessage">
                  <c:if test="${addMessage.sender==sender}">
                      <p class="user">${addMessage.recipientid}，您好！</p>
                      <div class="user_co">
                          <div class="user_co_1"></div>
                          <ul>Hi ${addMessage.recipientid}.: )<br/> ${addMessage.body}
                              <span>发送与:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                          </ul>
                          <div class="user_co_2"></div>
                      </div>
                      <div class="dpan"></div>
                  </c:if>
                  <c:if test="${addMessage.sender==recipient}">

                      <p class="admin">携宇科技</p>

                      <div class="admin_co">
                          <div class="admin_co_1"></div>
                          <ul>Hi ${addMessage.recipientid}.: )<br/>${addMessage.body}
                              <span>发送与:<fmt:formatDate value="${addMessage.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                          </ul>
                          <div class="admin_co_2"></div>
                      </div>
                      <div class="dpan"></div>
                  </c:if>
              </c:forEach>
          </div>
          <div>
              <form id="sendForm">
                  <input type="hidden" name="itemid" value="${message.itemid}">
                  <input type="hidden" name="buyeruserid" value="${message.sender}">
                  <input type="hidden" name="selleruserid" value="${message.recipientuserid}">
                  <input type="hidden" name="transactionid" value="">
                  <input type="hidden" name="subject" value="${message.subject}">
                  <textarea name="body"  id="textarea" style="width:772px;" rows="5"  class="newco_one validate[required]"></textarea><div class="modal-footer" style="margin-top:20px; float:left; width:100%;">
              </form>
              <%--<button type="button" class="btn btn-primary">保存</button>--%>
              <button type="button" class="btn btn-primary" onclick="selectSendMessage();">选择模板</button>
              <button type="button" class="btn btn-newco" onclick="submitCommit1();">回复</button>
              <button type="button" class="btn btn-default" onclick="dialogClose();" data-dismiss="modal">关闭</button>
          </div>
      </div>
    <script type="text/javascript">
        var sentmessage;
        var api = frameElement.api, W = api.opener;
        function sendMessage() {
            var url=path+"/message/sendMessageGetmymessage.do"
            sentmessage = $.dialog({title: '发送消息',
                content: 'url:'+url+'?messageid=${messages[0].messageid}',
                icon: 'succeed',
                width: 800,
                height: 300,
                lock: true
            });
        }
        $(document).ready(function(){
            $("#sendForm").validationEngine();
        });
        function dialogClose(){
            W.MessageGetmymessage.close();
        }
        function submitCommit1(){
            if(!$("#sendForm").validationEngine("validate")){
                return;
            }
            var url=path+"/order/apiGetOrdersSendMessage.do";
            var data=$("#sendForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        window.location.reload();
                        Base.token;
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function selectSendMessage(){
            var url=path+'/message/selectSendMessage.do?';
            sentmessage = $.dialog({title: '选择消息模板',
                content:'url:'+url,
                icon: 'succeed',
                width:800,
                height:400,
                parent:api,
                lock:true,
                zIndex:2000
            });
        }
    </script>
</body>
</html>
