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
    </script>
</head>
<body>
    <div style="width: 600px">
        <form class="form-horizontal">
            <fieldset>
                <div id="legend" class="">
                    <legend class="">表单名</legend>
                </div>
                <div class="control-group">

                    <!-- Textarea -->
                    <label class="control-label">Textarea</label>
                    <div class="controls">
                        <div class="textarea">
                            <textarea type="" class="" style="margin: 0px; width: 340px; height: 108px;"> </textarea>
                        </div>
                    </div>
                </div>

            </fieldset>
        </form>
    </div>
</body>
</html>
