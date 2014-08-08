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
        var tokenParm;

        /**打开*/
        function getBindParm(){
            var devAccountID=$('#devSelect').val();
            if(devAccountID==null || devAccountID ==0){alert('请选择开发帐号');return;}
            var url=path+"/user/apiGetSessionID.do";
            var data={id:devAccountID};
            $().invoke(
                    url,
                    data,
                    [function(m,r){
                        Base.token();
                        var rr= $.parseJSON(r)
                        sessid=rr.sessionid;
                        runName=rr.runName;
                        tokenParm="?SignIn&RuName="+rr.runName+"&SessID="+rr.sessionid;
                        window.open(tokenPageUrl+tokenParm);
                    },function(m,r){Base.token();alert(r)}],
                    {async:false}
            );
        }

        /**获取token*/
        function fetchToken(){
            var devAccountID=$('#devSelect').val();
            if(devAccountID==null || devAccountID ==0){alert('请选择开发帐号');return;}
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

        /**重新打开页面*/
        function reOpenPage(){
            window.open(tokenPageUrl+tokenParm);
        }

        /**组装选择开发帐号的*/
        function getAllDevSelect(){
            var url=path+"/user/queryAllDev.do";
            var data={};
            $().invoke(
                    url,
                    data,
                    [function(m,r){
                        for(var i in r){
                             var op="<option value='"+ r[i].id+"'>"+ r[i].devUser+"</option>";
                            $('#devSelect').append(op);
                        }
                    }]
            );
        }

        $(document).ready(function(){
            getAllDevSelect();
        });


    </script>
</head>
<body>

选择要绑定的开发帐号
<select id="devSelect">
    <option value="">请选择</option>
</select>

<button onclick="getBindParm()">账户授权</button>
帐号别名
<input type="text" id="bm" />
<button onclick="fetchToken()">授权已完成?</button>

</body>
</html>
