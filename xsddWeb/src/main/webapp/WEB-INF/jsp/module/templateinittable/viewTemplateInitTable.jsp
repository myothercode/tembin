<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/5
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-1.9.0.min.js" /> ></script>
  <script type="text/javascript" src=<c:url value ="/js/jquery/jquery-migrate-1.2.1.min.js" /> ></script>
  <script type="text/javascript" src=<c:url value ="/js/drawWindow/drawWindow.js" /> ></script>
  <script type="text/javascript">
    /*function xxx(){
      html2canvas(document.body, {
        onrendered: function(canvas) {
          document.body.appendChild(canvas);
        }
      });
    }*/

    function xxx(){
      window.drawWindow(document, function(canvas) {
        var dataURL = canvas.toDataURL("image/png");
        //alert(dataURL)
        //alert(dataURL.replace(/^data:image\/(png|jpg);base64,/, ""))
        var imsBase61Str=dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
       // window.open(canvas.toDataURL("image/png"));
      });
    }
  </script>
</head>
<body>
<%--<button id="hg" onclick="xxx()">生成缩略图</button>--%>
${TemplateInitTable.templateHtml}
</body>
</html>
