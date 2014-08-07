<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/6
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title>绑定账户</title>
    <script type="text/javascript">
        var tokenPageUrl="${tokenPageUrl}";
        var sessid;

        /**打开*/
        function getBindParm(){
            var devAccountID=1;
            var url=path+"/user/apiGetSessionID.do";
            var data={id:devAccountID};
            $().invoke(
                    url,
                    data,
                    [function(m,r){
                        Base.token();
                        var rr= $.parseJSON(r)
                        sessid=rr.sessionid;
                        var tokenParm="?SignIn&RuName="+rr.runName+"&SessID="+rr.sessionid;
                        window.open(tokenPageUrl+tokenParm);
                    },function(m,r){Base.token();alert(r)}],
                    {async:false}
            );
        }

        function fetchToken(){
            var devAccountID=1;
            var url=path+"/user/apiFetchToken.do";
            var name=$('#bm').val();
            var data={strV1:sessid,id:devAccountID};
            $().invoke(
                    url,
                    data,
                    [function(m,r){
                        Base.token();
                        alert(r)
                    },function(m,r){Base.token();alert(r)}],
                    {async:false}
            );

        }


    </script>
</head>
<body>
<button onclick="getBindParm()">账户授权</button>
帐号别名
<input type="text" id="bm" />
<button onclick="fetchToken()">授权已完成?</button>

</body>
</html>
