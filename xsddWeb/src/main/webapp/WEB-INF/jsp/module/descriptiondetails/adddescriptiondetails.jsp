<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/7/30
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/default/easyui.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/jquery-easyui/themes/icon.css" />"/>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.config.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/ueditor.all.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/ueditor/lang/zh-cn/zh-cn.js" /> ></script>
    <script type="text/javascript" src=<c:url value ="/js/jquery-easyui/jquery.easyui.min.js" /> ></script>
    <link href=
          <c:url value="/css/basecss/conter.css"/> type="text/css" rel="stylesheet"/>
    <style type="text/css">
        div{
            width:100%;
        }
    </style>
</head>
<body>
<br/>
<div>
   <%-- <h1>卖家描述</h1>--%>
<form  id="descriptionDetailsForm" >
    <input type="hidden" name="id"  value="${DescriptionDetails.id}" />
    <input type="hidden" name="payInfo" id="Payment"/>
    <input type="hidden" name="shippingInfo" id="Shipping"/>
    <input type="hidden" name="contactInfo" id="Contact"/>
    <input type="hidden" name="guaranteeInfo" id="Guarantee"/>
    <input type="hidden" name="feedbackInfo" id="Feedback"/>

    <input type="hidden" name="payTitle" id="payTitle" value="${empty DescriptionDetails.payTitle?"Payment":DescriptionDetails.payTitle}" />
    <input type="hidden" name="shippingTitle" id="shippingTitle" value="${empty DescriptionDetails.shippingTitle?"Shipping & Handing":DescriptionDetails.shippingTitle}"/>
    <input type="hidden" name="contactTitle" id="contactTitle" value="${empty DescriptionDetails.contactTitle?"Contact us":DescriptionDetails.contactTitle}" />
    <input type="hidden" name="guaranteeTitle" id="guaranteeTitle" value="${empty DescriptionDetails.guaranteeTitle?"Guarantee":DescriptionDetails.guaranteeTitle}" />
    <input type="hidden" name="feedbackTitle" id="feedbackTitle" value="${empty DescriptionDetails.feedbackTitle?"Feedback":DescriptionDetails.feedbackTitle}"/>
    名称：<input type="text" name="name" value="${DescriptionDetails.name}" style="border: 1px solid #cccccc;border-radius: 4px;"/>
</form>

<br>
       <div class="easyui-tabs" style="width:1000px">
                <div title="${empty DescriptionDetails.payTitle?"Payment":DescriptionDetails.payTitle}" style="padding:10px" id="PaymentTab" data-options="tools:'#Payment-tools'">
                    <script id="myEditor" type="text/plain" style="width:975px;height:400px;">${DescriptionDetails.payInfo}</script>
                </div>
                 <div title="${empty DescriptionDetails.shippingTitle?"Shipping & Handing":DescriptionDetails.shippingTitle} " style="padding:10px" id="ShippingTab" data-options="tools:'#Shipping-tools'">
                      <script type="text/plain" id="myEditor1" style="width:975px;height:400px;">${DescriptionDetails.shippingInfo}</script>
                  </div>
                  <div title="${empty DescriptionDetails.contactTitle?"Contact us":DescriptionDetails.contactTitle} " style="padding:10px" id="ContactTab" data-options="tools:'#Contact-tools'">
                      <script type="text/plain" id="myEditor2" style="width:975px;height:400px;">${DescriptionDetails.contactInfo}</script>
                  </div>
                  <div title="${empty DescriptionDetails.guaranteeTitle?"Guarantee":DescriptionDetails.guaranteeTitle}" style="padding:10px" id="GuaranteeTab" data-options="tools:'#Guarantee-tools'">
                      <script type="text/plain" id="myEditor3" style="width:975px;height:400px;">${DescriptionDetails.guaranteeInfo}</script>
                  </div>
                  <div title="${empty DescriptionDetails.feedbackTitle?"Feedback":DescriptionDetails.feedbackTitle}" style="padding:10px" id="FeedbackTab" data-options="tools:'#Feedback-tools'">
                      <script type="text/plain" id="myEditor4" style="width:975px;height:400px;">${DescriptionDetails.feedbackInfo}</script>
                  </div>
            </div>
    <div id="Payment-tools">
        <a href="javascript:void(0)" class="icon-mini-edit" onclick="modifyTabTitle(0)"></a>
    </div>
    <div id="Shipping-tools">
        <a href="javascript:void(0)" class="icon-mini-edit" onclick="modifyTabTitle(1)"></a>
    </div>
    <div id="Contact-tools">
        <a href="javascript:void(0)" class="icon-mini-edit" onclick="modifyTabTitle(2)"></a>
    </div>
    <div id="Guarantee-tools">
        <a href="javascript:void(0)" class="icon-mini-edit" onclick="modifyTabTitle(3)"></a>
    </div>
    <div id="Feedback-tools">
        <a href="javascript:void(0)" class="icon-mini-edit" onclick="modifyTabTitle(4)"></a>
    </div>

    <input type="button" value="保存" onclick="submitCommit();"/>
</div>
<script type="text/javascript">

    /**修改tab的title*/
    function modifyTabTitle(divid){
        var str=prompt('输入别名',$(".tabs .tabs-title").eq(divid).html());
       if(str==null || $.trim(str)==''){return;}

        var inputid="";
        switch (divid){
            case 0:
                inputid="payTitle";
                break;
            case 1:
                inputid="shippingTitle";
                break;
            case 2:
                inputid="contactTitle";
                break;
            case 3:
                inputid="guaranteeTitle";
                break;
            case 4:
                inputid="feedbackTitle";
                break;
            default :
                "";
        }

        $(".tabs .tabs-title").eq(divid).html($.trim(str));
        $("#"+inputid).val($.trim(str));
    }

    /**编辑器的工具栏*/
    var ueditorToolBar={
        toolbars:[['source','FullScreen',  'Undo', 'Redo','Bold','fontfamily', 'fontsize','underline', 'fontborder', 'strikethrough','|',
            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|',
            'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows',
            'splittocols','|','insertimage','imagenone', 'imageleft', 'imageright', 'imagecenter', '|','horizontal','drafts','preview','|'
        ]],
//关闭字数统计
        wordCount:false,
        //关闭elementPath
        elementPathEnabled:false
        //默认的编辑区域高度
        //initialFrameHeight:500 ,
        //initialFrameWidth:'98%'
    };

    var um =UE.getEditor('myEditor',ueditorToolBar);
    var um1 = UE.getEditor('myEditor1',ueditorToolBar);
    var um2 = UE.getEditor('myEditor2',ueditorToolBar);
    var um3 = UE.getEditor('myEditor3',ueditorToolBar);
    var um4 =UE.getEditor('myEditor4',ueditorToolBar);

    _sku="systemImage";
    var type = '${type}';
    if(type=="01"){
        $("input[type='text']").each(function(i,d){
            $(d).attr("disabled",true);
        });
        $("input[type='button']").each(function(i,d){
            $(d).hide();
        });
        $("select").each(function(i,d){
            $(d).attr("disabled",true);
        });
        /*$("p").each(function(i,d){
           $(d).attr("disabled",true);
        });*/
        $("textarea").attr("disabled",true);
    }


</script>
<script type="text/javascript">
    var api = frameElement.api, W = api.opener;
    function submitCommit(){
        $("#Payment").val(um.getContent());
        $("#Shipping").val(um1.getContent());
        $("#Contact").val(um2.getContent());
        $("#Guarantee").val(um3.getContent());
        $("#Feedback").val(um4.getContent());
        var url=path+"/ajax/saveDescriptionDetails.do";
        var data=$("#descriptionDetailsForm").serialize();
        $().invoke(url,data,
                [function(m,r){
                    alert(r);
                    W.descDiag.close();
                    Base.token();
                    W.refreshTableDesciption();
                },
                    function(m,r){
                        alert(r);
                        Base.token();
                    }]
        );
    }



</script>
</body>
</html>