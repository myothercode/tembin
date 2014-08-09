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
<script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
<script type="text/javascript" src=<c:url value ="/js/table/jquery.tablednd.js" /> ></script>
<script type="text/javascript">
    $(document).ready(function(){
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
    });
    function refreshTable(){
        $("#descriptionDetailsListTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
    }
    function setDruag(){
        $(".tab_ZY").tableDnD();
    }

    /*(function($) {
        var image = {
            editor: null,
            init: function(editorid, keyid) {
                var _editor = this.getEditor(editorid);
                _editor.ready(function() {
                    _editor.setDisabled();
                    _editor.hide();
                    _editor.addListener('beforeInsertImage', function(t, args) {
                        $("#" + keyid).val(args[0].src);
                    });
                });
            },
            getEditor: function(editorid) {
                this.editor = UE.getEditor(editorid);
                return this.editor;
            },
            show: function(id) {
                var _editor = this.editor;
                //注意这里只需要获取编辑器，无需渲染，如果强行渲染，在IE下可能会不兼容（切记）
                //和网上一些朋友的代码不同之处就在这里
                $("#" + id).click(function() {
                    var image = _editor.getDialog("insertimage");
                    image.render();
                    image.open();
                });
            },
            render: function(editorid) {
                var _editor = this.getEditor(editorid);
                _editor.render();
            }
        };
        $(function() {
            image.init("myeditor", "upload");
            image.show("image");
        });
    })(jQuery);*/

   /* var editor = UE.getEditor('myeditor', {
        imageUrl: "{:U(GROUP_NAME.'/Cases/upload/')}",
        imagePath: "__ROOT__/Uploads/",
        imageManagerUrl: "{:U(GROUP_NAME.'/cases/imagesManager/')}",
        imageManagerPath: "__ROOT__/",
    });*/

</script>
<head>
    <title></title>
</head>
<body>

<input id="upload" name='upload' type="text" style='width: 300px' value=""/>
<script type="text/plain" id="myeditor"></script>
<input type="button" id='image' value='上传图片'/>


<div id="descriptionDetailsListTable"></div>
<button onclick="setDruag()">设置拖拽</button>

</body>
</html>
