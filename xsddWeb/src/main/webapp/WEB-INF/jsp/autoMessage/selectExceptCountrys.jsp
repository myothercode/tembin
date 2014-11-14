<%--
  Created by IntelliJ IDEA.
  User: Administrtor
  Date: 2014/10/15
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
        var api = frameElement.api, W = api.opener;
        function selectCountry(id,name,obj,count){
            count++;
            var url=path+"/autoMessage/ajax/selectCountry.do?countryId="+id;
            $().invoke(url,null,
                    [function(m,r){
                        var td=$(obj).parent().parent();
                        $(obj).parent().remove();
                        var htm1="<div>&nbsp; &nbsp; &nbsp; &nbsp;<input type=\"checkbox\" name=\"location\" onclick='addtext(this);' value=\""+id+"\" value1=\""+name+"\">"+name    +"[<a href=\"javascript:void(0)\" onclick=\"selectCountry('"+id+"','"+name+"',this,"+count+")\">显示所有国家</a>]<br/>";
                        var htm="";
                        for(var i= 0;i< r.length;i++){
                            console.debug(r[i]);
                            htm+="&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<input type=\"checkbox\" name=\"location\" onclick='addtext(this);' value=\""+ r[i].id+"\" value1=\""+ r[i].name+"\">"+ r[i].name+"<br/>";
                        }
                        console.debug(td);
                        var div=htm1+htm+"</div>";
                        if(count%2==0){
                            $(td).append($(htm1+"</div>"));
                        }else{
                            $(td).append($(div));
                        }
                        Base.token();
                    },
                        function(m,r){
                            Base.token();
                        }]
            );
        }
        function closedialog(){
            W.selectCountry1.close();
        }
        function addtext(obj){

            var id=$(obj).val();
            var name=$(obj).attr("value1");
            var value1=$(obj).attr("value1");
            var td=$(obj).parent().parent();
            var htm1="<div>&nbsp; &nbsp; &nbsp; &nbsp;<input type=\"checkbox\" name=\"location\" onclick='addtext(this);' value=\""+id+"\" value1=\""+name+"\">"+name    +"[<a href=\"javascript:void(0)\" onclick=\"selectCountry('"+id+"','"+name+"',this,"+0+")\">显示所有国家</a>]<br/></div>";
            var htm="<div>&nbsp; &nbsp; &nbsp; &nbsp;<input type=\"checkbox\" checked name=\"location\" onclick='addtext(this);' value=\""+id+"\" value1=\""+name+"\">"+name    +"<br/></div>";
            var cccs="${names}";
            cccs=cccs.substring(1,cccs.length-1);
            var ccs=cccs.split(",");
            for(var i=0;i<ccs.length;i++){
                var c=ccs[i].trim();
                if(name==c){
                    if(obj.checked){
                        $(obj).parent().remove();
                        $(td).append($(htm));

                    }else{
                        $(obj).parent().remove();
                        $(td).append($(htm1));
                    }
                }
            }
            var checkboxs=$("input[type=checkbox][name=location]:checked");
            $("#aountryText").val("");
            var aountryText="";
            var countryIds="";
            for(var i=0;i<checkboxs.length;i++){
                var checkbox=checkboxs[i];
                if(i==(checkboxs.length-1)){
                    aountryText+=$(checkbox).attr("value1");
                    countryIds+=$(checkbox).attr("value");
                }else{
                    aountryText+=$(checkbox).attr("value1")+",";
                    countryIds+=$(checkbox).attr("value")+",";
                }
            }
            $("#aountryText").val(aountryText);
            $("#countryID").val(countryIds);
        }
        function submitCommit(){
            if(!$("#countryForm").validationEngine("validate")){
                return;
            }
            var aountryText=$("#aountryText").val();
            var countryIds=$("#countryID").val();
            var countrys=W.document.getElementById("selectExceptCountrys");
            var countryIds1=W.document.getElementById("exceptCountryIds");
            var autoMessageId= W.document.getElementById("id");
            var flag="false";
            if($(countrys).val()!="指定国家之外"){
                flag="true";
            }
            if(aountryText!=""){
                var url=path+"/autoMessage/ajax/saveExceptCountry.do?autoMessageId="+$(autoMessageId).val()+"&flag="+flag;
                var date=$("#countryForm").serialize();
                $().invoke(url,date,
                        [function(m,r){
                            var coutryid="";
                            for(var i=0;i< r.length;i++){
                                if(i==(r.length-1)) {
                                    coutryid+=r[i];
                                }else{
                                    coutryid+=r[i]+",";
                                }
                            }
                            countrys.innerHTML=aountryText+"之外";
                            $(countryIds1).val(coutryid);
                            W.selectCountry1.close();
                            Base.token();
                        },
                            function(m,r){
                                alert(r);
                                Base.token();
                            }]
                );
            }
        }
        $(document).ready(function(){
            $("#countryForm").validationEngine();
        });
    </script>
</head>
<body>

        <div id="legend" class="">
            <legend class="">选择国家</legend>
        </div>
        <div class="control-group">
            <label class="control-label" style="width: 360px;" >选取一个或者多个选项,您可以选择一个国家或者整个地区</label>
            <div class="controls">
            </div>
        </div><br/>
        <div class="control-group">
            <label class="control-label" style="width: 100px;" ></label>
            <div class="controls" style="width: 600px;"><br/><br/>
                <table>
                    <tr>
                    <c:forEach var="data" items="${countrys}" varStatus="da">
                        <c:if test="${da.index%3==2}">
                            <td valign="top"><div>&nbsp; &nbsp; &nbsp; &nbsp;<input type="checkbox" name="location" onclick="addtext(this);" value="${data.id}" value1="${data.name}">${data.name}
                            [<a href="javascript:void(0)" onclick="selectCountry('${data.id}','${data.name}',this,0)">
                            显示所有国家
                            </a>]</div></td></tr><tr>
                        </c:if>
                        <c:if test="${da.index%3!=2}">
                            <td valign="top">
                                <div>&nbsp; &nbsp; &nbsp; &nbsp;<input type="checkbox" name="location" onclick="addtext(this);" value="${data.id}" value1="${data.name}">${data.name}
                                [<a href="javascript:void(0)" onclick="selectCountry('${data.id}','${data.name}',this,0)">
                                显示所有国家
                                </a>]</div>
                            </td>
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </div><br/><br/>
        <div class="control-group">
            <label class="control-label" style="width: 110px;" >有效的国家:</label>
            <div class="controls">
                <form class="form-horizontal" id="countryForm">

                    <fieldset>
                        <input type="hidden" name="countryIds" id="countryID">
                        <textarea name="aountryText" class="validate[required]"  id="aountryText" style="width: 600px;background:#e0e6ef"></textarea>
                    </fieldset>
                </form>

            </div>
        </div>
        <div class="modal-footer" style="text-align: right;width: 700px;">
            <button type="button" class="net_put" onclick="submitCommit();">保存</button>
            <button type="button" class="net_put_1" data-dismiss="modal" onclick="closedialog();">关闭</button>
      <%--      <button type="button" class="btn btn-primary" onclick="submitCommit();">保存</button>
            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closedialog();">关闭</button>--%>
        </div>

</body>
</html>
