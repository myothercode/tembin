<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script>
        $(document).ready(function(){
            $("#addimg").attr("src",path+"/img/adds.png");
            onloadTable();
            onloadLogTable();
        });
        function onloadLogTable(){
            $("#complementLog").initTable({
                url:path+"/ajax/loadComplementLog.do",
                columnData:[
                    {title:"消息类型",name:"eventname",width:"20%",align:"left"},
                    {title:"消息内容",name:"eventdesc",width:"40%",align:"left"},
                    {title:"时间",name:"createdate",width:"20%",align:"left"}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshLogTable();
        }
        function  refreshLogTable(){
            var param={};
            $("#complementLog").selectDataAfterSetParm(param);
        }
        function onloadTable(){
            $("#complementManager").initTable({
                url:path+"/ajax/loadComplementTable.do",
                columnData:[
                    {title:"SKU",name:"skuKey",width:"20%",align:"left",format:getSku},
                    {title:"规则类型",name:"autoType",width:"20%",align:"left",format:getTypeDesc},
                    {title:"规则描述",name:"complementDesc",width:"40%",align:"left"},
                    {title:"EBAY账号",name:"ebayAccount",width:"10%",align:"center"},
                    {title:"&nbsp;&nbsp;&nbsp;&nbsp;操作",name:"Option1",width:"10%",align:"left",format:Option1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
        function getSku(json){
            var html="";
            if(json.autoType=="1"){
                html="SKU = "+json.skuKey;
            }else if(json.autoType=="2"){
                html="SKU以"+json.skuKey+"开头";
            }
            return html;
        }
        function getTypeDesc(json){
            var html="";
            if(json.autoType=="1"){
                html="停止自动补货（按照SKU）";
            }else if(json.autoType=="2"){
                html="停止自动补货（按照SKU起始字符）";
            }
            return html;
        }
        var complement;
        function addcomplement(){
            complement=openMyDialog({title: '新增自动补数规则',
                content: 'url:'+path+'/complement/addComplement.do',
                icon: 'succeed',
                width:800,
                lock:true
            });
        }
        function  refreshTable(){
            var param={};
            $("#complementManager").selectDataAfterSetParm(param);
        }
        function Option1(json){
            var hs="";
            hs+="<li style='height:25px' onclick=delComplement('"+json.id+"') value='"+json.id+"' doaction=\"look\" >删除</li>";
            var pp={"liString":hs,"marginLeft":"-150px"};
            return getULSelect(pp);
        }
        function delComplement(id){
            var url=path+"/ajax/delComplement.do?id="+id;
            $().invoke(url,{},
                    [function(m,r){
                        refreshTable();
                    },
                        function(m,r){
                            alert(r);
                        }]
            );
        }

        function setAssessTab(obj){
            var name=$(obj).attr("name");
            $(obj).parent().find("dt").each(function (i, d) {
                if($(d).attr("name")==name){
                    $(d).attr("class","new_tab_1");
                    $("#"+name).show();
                }else{
                    $(d).attr("class","new_tab_2");
                    $("#"+$(d).attr("name")).hide();
                }
            });
            if(name=="complementManager"){
                $("#addimg").show();
            }else{
                $("#addimg").hide();
            }

        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 > 刊登管理 > <b>自动补数</b></div>
    <div class="a_bal"></div>
    <div class="tbbay" id="showAddButton" style="background-image:url(null);position: absolute;top: 60px;right: 40px;z-index: 10000;">
        <img id="addimg" onclick="addcomplement()">
        <%--<a data-toggle="modal" href="javascript:void(0)" class=""  onclick="addModel()">新增</a>--%>
    </div>
    <div class="new">
        <div class="new_tab_ls" id="selectModel">
            <dt id=menu1 name="complementManager" class=new_tab_1 onclick="setAssessTab(this)">补数规则设置</dt>
            <dt id=menu2 name="complementLog" class=new_tab_2 onclick="setAssessTab(this)">补数记录</dt>
        </div>
        <div class=Contentbox id="Contentbox" style="float:none;">
            <div class="new_usa" style="margin-top:20px;">
                <div id="complementManager"></div>
                <div id="complementLog" style="display: none;"></div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
