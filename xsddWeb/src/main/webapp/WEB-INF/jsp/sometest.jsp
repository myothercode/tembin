<%--
  Created by IntelliJ IDEA.
  User: wula
  Date: 2014/8/9
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<script type="text/javascript" src=<c:url value ="/js/item/addItem.js" />  />
<script type="text/javascript">

    $(document).ready(function(){
        getCategorySpecificsData("11555","attList","afterClickAttr","attTable");
    });



</script>
<%--<script type="text/javascript" src=<c:url value ="/js/batchAjaxUtil.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/table/jquery.tablednd.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/ueditor/dialogs/image/imageextend.js" /> ></script>--%>

<%--<script type="text/javascript">
    var _sku="fffff";
    /*$(document).ready(function(){
        $("#descriptionDetailsListTable").initTable({
            url:path + "/ajax/loadDescriptionDetailsList.do",
            columnData:[
                {title:"名称",name:"name",width:"8%",align:"left"},
                {title:"pay",name:"payInfo",width:"8%",align:"left"},
                {title:"shipping",name:"shippingInfo",width:"8%",align:"left"},
                {title:"contact",name:"contactInfo",width:"8%",align:"left"},
                {title:"Guarantee",name:"guaranteeInfo",width:"8%",align:"left"},
                {title:"Feedback",name:"feedbackInfo",width:"8%",align:"left"}
            ],
            selectDataNow:false,
            isrowClick:false,
            showIndex:true
        });
        refreshTable();
    });*/
    function refreshTable(){
        $("#descriptionDetailsListTable").selectDataAfterSetParm({});
    }
    function setDruag(){
       // alert($.fn.returnIMGURLs)
        //$(".tab_ZY").tableDnD();
    }
function addPictrueUrl(opt){
    alert(opt.length)
}


    $(document).ready(function(){
        $().image_editor.init("myeditor");
        $().image_editor.show("image");
    });



var afterUploadCallback={"imgURLS":addPictrueUrl}

</script>--%>
<head>
    <title></title>
</head>
<body>

<div id="attList"></div>
<table id="attTable">
    <tr>
        <th>属性名</th>
        <th>属性值</th>
        <th>操作</th>
    </tr>
</table>
<%--<input id="upload" name='upload' type="text" style='width: 300px' value=""/>
<script type="text/plain" id="myeditor"></script>
<input type="button" id='image' value='上传图片'/>


<div id="descriptionDetailsListTable"></div>
<button onclick="setDruag()">设置拖拽</button>--%>
<button onclick=asyCombox2InputData()>kkkk</button>
</body>
</html>
