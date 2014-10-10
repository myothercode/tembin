<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014/10/7
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/smallFormImport.jsp" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        var messageId="${messageId}";
        var api = frameElement.api, W = api.opener;
        $(document).ready(function(){
            var url=path+"/sitemessage/readSiteMessage.do";
            var data = {"id":api.data.messageId};
            $().invoke(
                    url,
                    data,
                    [function(m,r){
                        $("#legendd").html(r.messageTitle);
                        $("#messageText").val(r.message);
                        W.refreshRoleTable();
                    },
                    function(m,r){
                        alert(r)
                    }]
            );
        });
    </script>
</head>
<body>
    <div style="width: 600px">
        <form class="form-horizontal">
            <fieldset>
                <div id="legend" class="">
                    <legend id="legendd" class="">表单名</legend>
                </div>
                <div class="control-group">

                    <!-- Textarea -->
                    <label class="control-label">消息内容</label>
                    <div class="controls">
                        <div class="textarea">
                            <textarea id="messageText" readonly="true" type="" class="" style="margin: 0px; width: 340px; height: 108px;"> </textarea>
                        </div>
                    </div>
                </div>

            </fieldset>
        </form>
    </div>
</body>
</html>
