<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/14
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file= "/WEB-INF/jsp/commonImport.jsp" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        body {
            background-color: #ffffff;
        }
    </style>
    <script type="text/javascript">
        var autoMessage;
        $(document).ready(function(){
            $("#autoMessageTable").initTable({
                url:path + "/autoMessage/ajax/loadAutoMessageList.do?status=1",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption3},
                    {title:"标题",name:"subject",width:"8%",align:"left"},
                    {title:"备注",name:"comment",width:"8%",align:"left"},
                    {title:"类型",name:"type",width:"8%",align:"left"},
                    {title:"指定国家",name:"country",width:"8%",align:"left"},
                    {title:"指定账号",name:"amount",width:"8%",align:"left"},
                    {title:"状态",name:"startuse",width:"8%",align:"left",format:makeOption5},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption4}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#autoMessageTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }


        function makeOption3(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function makeOption5(json){
            if(json.startuse==1){
                var htm = "<img title='启用' src='"+path+"/img/new_yes.png' />";
                return htm;
            }else{
                var htm = "<img title='禁用' src='"+path+"/img/new_no.png' />";
                return htm;
            }
        }
        function makeOption4(json){
            var hs="";
            hs="<li style=\"height:25px;\"  onclick=editAutoMessage("+json.id+"); value='1' doaction=\"readed\" >编辑</li>";
            hs+="<li style=\"height:25px;\"  onclick=deleteAutoMessage("+json.id+"); value='1' doaction=\"look\" >删除</li>";
            hs+="<li style=\"height:25px;\"  onclick=addComment("+json.id+"); value='1' doaction=\"look\" >备注</li>";
            if(json.startuse==1){
                hs+="<li style=\"height:25px;\"  onclick=startUsing("+json.id+",0); value='1' doaction=\"look\" >禁用</li>";
            }
            if(json.startuse==0){
                hs+="<li style=\"height:25px;\"  onclick=startUsing("+json.id+",1); value='1' doaction=\"look\" >启用</li>";
            }
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function startUsing(id,status){
            var url=path+"/autoMessage/ajax/startUsing.do?id="+id+"&status="+status;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        refreshTable();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function addAutoMessage(){
            var url=path+'/autoMessage/addAutoMessage.do?';
            autoMessage=openMyDialog({title: '自动消息',
                content: 'url:'+url,
                icon: 'succeed',
                width:950,
                height:600,
                lock:true
            });
        }
        function addComment(id){
            var url=path+'/autoMessage/addComment.do?id='+id;
            autoMessage=openMyDialog({title: '添加备注',
                content: 'url:'+url,
                icon: 'succeed',
                width:600,
                lock:true
            });
        }
        function editAutoMessage(id){
            var url=path+'/autoMessage/addAutoMessage.do?id='+id;
            autoMessage=openMyDialog({title: '自动消息',
                content: 'url:'+url,
                icon: 'succeed',
                width:950,
                height:600,
                lock:true
            });
        }
        function deleteAutoMessage(id){
            var url=path+"/autoMessage/ajax/deleteAutoMessage.do?id="+id;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        refreshTable();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function deleteAutoMessages(){
            var checkboxs=$(document).find("input[type=checkbox][name=templateId]:checked");
            var id="";
            for(var i=0;i<checkboxs.length;i++){
                if(i==(checkboxs.length-1)){
                    id+=$(checkboxs[i]).attr("value");
                }else{
                    id+=$(checkboxs[i]).attr("value")+",";
                }
            }
            var url=path+"/autoMessage/ajax/deleteAutoMessages.do?id="+id;
            $().invoke(url,null,
                    [function(m,r){
                        alert(r);
                        refreshTable();
                        Base.token();
                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
        function selectCheckboxs(obj){
            var checkboxs=$(document).find("input[type=checkbox][name=templateId]");
            for(var i=0;i<checkboxs.length;i++){
                if(obj.checked){
                    checkboxs[i].checked=true;
                }else{
                    checkboxs[i].checked=false;
                }
            }
        }
        function viewForbidden(obj){
            var url=path + "/autoMessage/ajax/loadAutoMessageList.do?status="+status;
            if(obj.checked){
                url=path + "/autoMessage/ajax/loadAutoMessageList.do?status=0";
            }else{
                url=path + "/autoMessage/ajax/loadAutoMessageList.do?status=1";
            }
            $("#autoMessageTable").initTable({
                url:url,
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption3},
                    {title:"标题",name:"subject",width:"8%",align:"left"},
                    {title:"备注",name:"comment",width:"8%",align:"left"},
                    {title:"类型",name:"type",width:"8%",align:"left"},
                    {title:"指定国家",name:"country",width:"8%",align:"left"},
                    {title:"指定账号",name:"amount",width:"8%",align:"left"},
                    {title:"状态",name:"startuse",width:"8%",align:"left",format:makeOption5},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption4}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        }
    </script>
</head>
<body>
<div class="new_all">
    <div class="here">当前位置：首页 &gt; 刊登管理 &gt; <b>自动消息</b></div>
    <div class="a_bal"></div>
    <div class="new_tab_ls">
        <dt id="menu1" class="new_tab_1">自动消息</dt>
    </div><br/><br/><br/>
    <div class="new">

        <div class="Contentbox">
            <div>
                <div id="con_menu_1" >
                    <div class="new_usa" style="margin-top:20px;">
                        <div class="newds">
                            <div class="newsj_left" style="padding: 2px;"><span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" onclick="selectCheckboxs(this);"></span>
                                <span class="newusa_ici_del" onclick="deleteAutoMessages();">删除</span><span class="newusa_ici_del_in"><input id="checkboxStatus" type="checkbox"  onclick="viewForbidden(this);"></span><span style="line-height: 28px;">显示已停用的模板</span>
                                <div id="newtipi" style="width: 200px;">
                                </div>
                            </div><div class="tbbay"><a data-toggle="modal" href="#myModal" class="" onclick="addAutoMessage();">添加模板</a></div>
                        </div>
                    </div>
                    <div style="width: 100%;float: left;height: 5px"></div>
                    <div id="autoMessageTable"></div>
                </div>

                <!--结束 -->
            </div>
        </div>
    </div>
</div>
</body>
</html>
