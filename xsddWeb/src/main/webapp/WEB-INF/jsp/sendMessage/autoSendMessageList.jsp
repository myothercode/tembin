<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/16
  Time: 14:41
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
        var sendGetmymessage;
        $(document).ready(function(){
            $("#autoSendMessageTable").initTable({
                url:path + "/sendMessage/ajax/loadAutoSendMessageList.do?",
                columnData:[
                    {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption3},
                    {title:"标题",name:"subject",width:"8%",align:"left"},
                    {title:"类型",name:"messagetype",width:"8%",align:"left",format:makeOption2},
                    {title:"ebay账号",name:"recipientid",width:"8%",align:"left"},
                    {title:"买家来自",name:"recipientid",width:"8%",align:"left"},
                    {title:"操作",name:"countNum",width:"8%",align:"left",format:makeOption1}
                ],
                selectDataNow:false,
                isrowClick:false,
                showIndex:false
            });
            refreshTable();
        });
        function refreshTable(){
            $("#autoSendMessageTable").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
        }
        /**查看发送消息*/
        function makeOption1(json){
           /* var htm="<div class=\"ui-select\" style=\"width:8px\"><a href=\"javascript:void(0)\" onclick=\"deleteAutoSendMessage("+json.transactionid+","+json.messagetype+","+json.messageflag+");\">删除</a></div>";
            return htm;*/
            var hs="";
            hs="<li style=\"height:25px;\" onclick=deleteAutoSendMessage("+json.transactionid+","+json.messagetype+","+json.messageflag+"); value='1' doaction=\"readed\" >删除</li>";
            var pp={"liString":hs};
            return getULSelect(pp);
        }
        function makeOption3(json){
            var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";
            return htm;
        }
        function deleteAutoSendMessage(transactionid,messagetype,messageflag){
            var url=path+"/sendMessage/ajax/deleteAutoSendMessage.do?transactionid="+transactionid+"&messagetype="+messagetype+"&messageflag="+messageflag;
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
        function makeOption2(json){
            if(json.messageflag==1){
                return "付款后自动消息";
            }
            if(json.messageflag==2){
                return "发货后自动消息";
            }
            if(json.messageflag==3){
                return "发货后n天自动消息";
            }

        }
        function Allchecked(obj){
            var checkboxs=$("input[type=checkbox][name=templateId]");
            if(obj.checked){
                for(var i=0;i<checkboxs.length;i++){
                    checkboxs[i].checked=true;
                }
            }else{
                for(var i=0;i<checkboxs.length;i++){
                    checkboxs[i].checked=false;
                }
            }
        }
        function removeAutoSendMessage(){
            var id=$("input[type=checkbox][name=templateId]:checked");
            if(id.length>0){
                var str="";
                for(var i=0;i<id.length;i++){
                    if(i!=id.length-1){
                        str+="\"id["+i+"]\":"+$(id[i]).val()+",";
                    }else{
                        str+="\"id["+i+"]\":"+$(id[i]).val();
                    }
                }
                var data1= "{"+str+"}";
                var data= eval('(' + data1 + ')');
                console.debug(data);
                var url=path+"/sendMessage/ajax/removeAutoSendMessage.do";
                $().invoke(url,data,
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
            }else{
                alert("请选择要删除的数据");
            }
        }
    </script>
</head>
<body>

    <div class="new_all">
        <div class="here">当前位置：首页 &gt; 客户管理 &gt; <b>自动消息</b></div>
        <div class="a_bal"></div>
        <div class="new">

            <div class="Contentbox">
                <div>
                    <div id="con_menu_1" >
                        <div class="new_usa" style="margin-top:20px;">
                            <div class="newds">
                                <div class="newsj_left"><span class="newusa_ici_del_in"><input type="checkbox" name="checkbox" id="checkbox" onclick="Allchecked(this);"></span>
                                    <span class="newusa_ici_del" onclick="removeAutoSendMessage();">删除</span><%--<div class="page_num">显示20条</div>--%>
                                </div><div class="tbbay"><a href="javascript:void(0)">添加模板</a></div>
                            </div>
                        </div>
                        <div id="autoSendMessageTable"></div>
                        <%--<table width="100%" border="0" align="left" cellspacing="0" style="margin-top:20px;">
                            <tbody><tr>
                                <td width="5%" bgcolor="#F7F7F7">&nbsp;</td>
                                <td width="15%" height="30" bgcolor="#F7F7F7"><strong>标题</strong></td>
                                <td width="15%" height="30" bgcolor="#F7F7F7"><strong>类型<b style="color:#8BB51B"></b></strong></td>
                                <td width="15%" align="center" bgcolor="#F7F7F7">eBay账号</td>
                                <td bgcolor="#F7F7F7">买家来自</td>
                                <td width="10%" align="center" bgcolor="#F7F7F7">操作</td>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="checkbox" id="checkbox"></td>
                                <td width="15%" style="color:#5E93D5">DL全球_发货通知</td>
                                <td width="15%">在标记已发货立即发送</td>
                                <td width="15%" align="center">lucky</td>
                                <td>阿尔及利亚，安哥拉，贝宁，喀麦隆...</td>
                                <td align="center"><div class="ui-select" style="width:8px">
                                    <select>
                                        <option value="AK">删除</option>
                                        <option value="AK">动作</option>
                                    </select>
                                </div></td>
                            </tr>
                            <tr>
                                <td style="color:#5E93D5"><input type="checkbox" name="checkbox2" id="checkbox2"></td>
                                <td width="15%" style="color:#5E93D5">DL全球_发货通知</td>
                                <td width="15%">在标记已发货立即发送</td>
                                <td width="15%" align="center">lucky</td>
                                <td>美国</td>
                                <td align="center"><div class="ui-select" style="width:8px">
                                    <select>
                                        <option value="AK">删除</option>
                                        <option value="AK">动作</option>
                                    </select>
                                </div></td>
                            </tr>
                            </tbody></table>--%>
                        <%--<div class="page_newlist">
                            <div class="page_num">显示20条</div>共 <span style="color:#F00">3000</span> 条记录 <span style="color:#F00">300</span> 页
                        </div>
                        <div class="maage_page">
                            <li>&lt;</li>
                            <li class="page_cl">1</li>
                            <li>2</li>
                            <li>3</li>
                            <li>4</li>
                            <dt>&gt;</dt>
                        </div>--%>
                    </div>

                    <!--结束 -->
                </div>
            </div>
        </div>
    </div>

</body>
</html>
