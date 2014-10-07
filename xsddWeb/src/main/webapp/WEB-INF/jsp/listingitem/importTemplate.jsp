<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/8/2
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>

<html>
<head>
<title></title>
    <script type="text/javascript" src=<c:url value ="/js/ajaxFileUpload/ajaxfileupload.js" /> ></script>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/ajaxFileUpload/ajaxfileupload.css" />"/>
    <script>
        var apis = frameElement.api, W = apis.opener;
        function importTemplateSave(){
            if($('#templatename').val()==null || $('#templatename').val()==''){
                alert('请选择文件！');
                return;
            }
            $.ajaxFileUpload({
                //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
                url:path+'/importTemplateSave.do',
                secureuri:false,                       //是否启用安全提交,默认为false
                fileElementId:'templatename',           //文件选择框的id属性
                files:[$('#templatename')],
                dataType:'text',                       //服务器返回的格式,可以是json或xml等
                data:{},
                fileFilter:".xls",
                fileSize:1000000,//1000是1k
                success:function(data, status){        //服务器响应成功时的处理函数
                    if(data=="success"){
                        alert("导入成功！");
                        W.refreshTable();
                        W.addTablePrice.close();
                    }else if(data=='nofile'){
                        alert('没有选择文件');
                    }else{
                        var jsonFormat = /^\s*\{[\s\S]*\}\s*$/m;
                        if(jsonFormat.test(data)){
                            var jsons = eval("("+data+")");
                            if(!jsons.bool){
                                alert("上传失败："+jsons.result);
                            }
                        }else{
                            alert(data);
                        }

                    }


                },
                error:function(data, status, e){ //服务器响应失败时的处理函数
                    alert('上传失败:'+e);
                }

            });
        }
    </script>

</head>
<body>
<div class="new_all">
<form name='uploadform' method='POST' action='/xsddWeb/importTemplateSave.do' ENCTYPE='multipart/form-data'>
<div class="a_bal"></div>
<div class="new">
    <li>
        <input type="file"  id="templatename" name="templatename"/>
    </li>

</div>
    <div class="a_bal"></div>
    <div>
        <input type="button" onclick="importTemplateSave()" value="确定">
    </div>
</form>
</div>

</body>
</html>
