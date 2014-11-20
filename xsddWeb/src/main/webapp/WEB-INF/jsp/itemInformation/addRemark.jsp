<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/9/10
  Time: 11:56
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
    <link rel="stylesheet" type="text/css" href="<c:url value ="/js/toolTip/css/toolTip.css"/> "/>
    <script type="text/javascript" src=<c:url value ="/js/toolTip/js/toolTip.js" /> ></script>
    <style type="text/css">
        *{margin:0;padding:0;list-style-type:none;}
        a,img{border:0;}
        body{font:12px/180% Arial, Helvetica, sans-serif, "新宋体";}

        .label_box{display:none;width:430px;margin:20px auto;}
        .label{height:30px;margin-left:142px;padding:0 10px;}
        .label li{float:left;line-height:30px;margin-right:15px;}
    </style>

    <script type="text/javascript">
        var api = frameElement.api, W = api.opener;
        function closedialog(){
            W.itemInformation.close();
        }
        function submitCommit(){
            var id=$("#id").val();
            var remarks=$("input[name=label]");
            var remark="";
            if(remarks.length==0){
                alert("请先添加标签");
                return;
            }else{
                for(var i=0;i<remarks.length;i++){
                    if(i==(remarks.length-1)){
                        remark+=$(remarks[i]).val();
                    }else{
                        remark+=$(remarks[i]).val()+",";
                    }
                }
            }
            var url=path+"/information/ajax/saveremark.do?id="+id+"&remark="+remark;
            var data=$("#remarkForm").serialize();
            $().invoke(url,data,
                    [function(m,r){
                        alert(r);
                        W.refreshTable();
                        W.loadRemarks();
                        W.itemInformation.close();
                        Base.token();

                    },
                        function(m,r){
                            alert(r);
                            Base.token();
                        }]
            );
        }
      /*  $(document).ready(function(){
            $("#remarkForm").validationEngine();
        });*/
        function addLength(obj){
            this.value='';
            $(obj).attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
        }
        function subLength(obj){
            if(obj.value==''){obj.value='';obj.style.color='#999';}
            $(obj).attr("style","margin-left:10px;width: 40px;background-color: #fff;border-radius: 5px;");
            $(obj).val("");
        }

    </script>
</head>
<body>
    <%--<form id="remarkForm">--%>
        <br/><br/>
        <input type="hidden" id="id" name="id" value="${id}"/>
       <%-- &nbsp;&nbsp;父类标签:<select name="parentid">
        <option value="0">--请选择--</option>
        <c:forEach items="${parents}" var="parent">
            <option value="${parent.id}">${parent.configName}</option>
        </c:forEach>

    </select>--%><br/>
        <%--&nbsp;&nbsp;标签:&nbsp;<input type="text" class="validate[required]" name="remark" style="width: 800px;"/>--%>
        <table style="margin-left: 50px;">
            <tr>
                <td>标签:</td>
                <%--<td><div id="addRemark" class="form-controlsd" ><input &lt;%&ndash;onblur="subLength(this);"&ndash;%&gt; onclick="addLength(this);" type="text" class="form-controlsd" style="width: 40px;height: 20px;">&lt;%&ndash;<input type="text" class="form-controlsd" style="width: 260px;height: 20px;">&ndash;%&gt;</div></td>--%>
                <td><ul id="addRemark" style="padding: 3px 5px 3px 5px;border: 1px solid lightgray;width: 360px;background-color: #ffffff;margin-top: 3px;"><input style="margin-left:10px;width:40px;background-color: #fff;border-radius: 5px;" id="kk"type="text" value="" onfocus="addLength(this)" onblur="subLength(this)" onkeyup="this.style.color='#333';" onclick="addLength(this)" /></ul></td>
            </tr>
            <tr>
                <td></td>
                <td><span style="color: #0000ff">请使用【enter】键添加标签</span></td>
            </tr>
            <tr></tr>
            <tr>
                <td></td>
              <%--  <td>您可能需要的标签:<br/>--%>

                </td>
            </tr>
        </table>


   <%-- </form>
--%>

    <div class="modal-footer">
        <button type="button" class="net_put" onclick="submitCommit();">保存</button>
        <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
    </div>

    <div style="height:50px;"></div>
    <script type="text/javascript">
        var lablId = -1;

        $(function() {
            $("#kk").blur(function() {
                if (isNan(this.value) != false) {
                    this.value = '';
                    this.style.color = '#999';
                }
            });
        });
        $(document).ready(function() {
            $("#kk").keydown(function(event) {
                if (event.keyCode == 13) {
                    var str = $("#kk").val();
                    if (isNan(str) != true) {
                        var li_id = $(".label li:last-child").attr('id');
                        if (li_id != undefined) {
                            li_id = li_id.split('_');
                            li_id = parseInt(li_id[1]) + 1;
                        } else {
                            li_id = 0;
                        }
                        $(".label_box").css("display", "block");
                        var text = "<a href='javascript:#' style='padding: 3px 5px 3px 5px;margin-left: 5px;margin-top:3px;border: 1px solid #aaaaaa;border-radius: 3px;position: relative;line-height: 30px;' onclick='deletes(this);' ><i class=\"icon-remove-sign\" style='margin-right: 2px;'></i><span >" + str + "</span><input type='hidden' name='label' value='" + str + "'></a>";
                        var spans=$("#addRemark").find("span");
                        for(var i=0;i<spans.length;i++){
                            var span=spans[i].innerHTML;
                            if(str==span){
                                return;
                            }
                        }
                        $("#kk").before(text);
                    }
                    $("#kk").val("");
                    $("#kk").attr("style","margin-left:10px;width: auto;background-color: #fff;border-radius: 5px;");
                }
            })
        });
        function isNan(obj) {
            try {
                return obj == 0 ? true: !obj
            } catch(e) {
                return true;
            }
        }


        function deletes(obj) {
            $(obj).remove();
        }

        function addlabl(id) {
            if (lablId == id) {
                return;
            }
            lablId = id;
            var str = $("#add_" + id).text();
            var li_id = $(".label li:last-child").attr('id');
            if (li_id != undefined) {
                li_id = li_id.split('_');
                li_id = parseInt(li_id[1]) + 1;
            } else {
                li_id = 0;
            }
            $(".label_box").css("display", "block");
            var text = "<li id='li_" + li_id + "'><a href='javascript:;' onclick='deletes(" + li_id + ");' ><img src='images/label_03.png' class='label-pic'>" + str + "</a><input type='hidden' name='label[" + li_id + "].name' value='" + str + "'></li>";
            $(".label").append(text);
        }
    </script>
    <div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
    </div>

</body>
</html>
