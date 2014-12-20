/**
 * Created by Administrator on 2014/12/18.
 */
$(document).ready(function(){

    mySelect2I([{url:path+"/orderItem/ajax/loadOrdersList.do",
        data:{currInputName:"content"},bs:".multiSelect",multiple:false,fun:null,maping:{id:"id",text:"sku"}}]);

    $("#ItemInformationListTable").initTable({
        url:path + "/information/ajax/loadItemInformationList.do?",
        columnData:[
            {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4,click:sellectCheckBox},
            {title:"图片",name:"pictureUrl",width:"3%",align:"left",format:makeOption2,click:sellectCheckBox},
            {title:"商品/SKU",name:"sku",width:"4%",align:"left",format:makeOption7,click:sellectCheckBox},
            {title:"产品名称",name:"name",width:"36%",align:"left",format:makeOption8,click:sellectCheckBox},
            {title:"标签",name:"remark",width:"8%",align:"left",format:makeOption6,click:sellectCheckBox},
            /*{title:"分类",name:"typeName",width:"8%",align:"left",click:sellectCheckBox},*/
            {title:"状态",name:"pictureUrl",width:"2%",align:"left",format:makeOption3,click:sellectCheckBox},
            {title:"操作&nbsp;&nbsp;",name:"option1",width:"2%",align:"center",format:makeOption1}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:false,
        showDataNullMsgContext:'没有商品记录!'
    });
    refreshTable();

    $("#ItemInformationTypeListTable").initTable({
        url:path + "/informationType/ajax/loadItemInformationTypeList.do?",
        columnData:[
            {title:"分类名称",name:"typeName",width:"8%",align:"left"},
            {title:"商品数",name:"countNum",width:"8%",align:"left"},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption5}
        ],
        selectDataNow:false,
        isrowClick:false,
        showIndex:true
    });
    // refreshTable1();
});

function refreshTable1(){
    $("#ItemInformationTypeListTable").selectDataAfterSetParm({});
}
function makeOption5(json){
    /* var htm="<a target=\"_blank\" href=\"javascript:void(0)\" onclick=\"addChildType('"+json.typeId+"');\">添加子分类</a>";*/
    /*  var htm="<div class=\"ui-select\" style=\"margin-top:1px; width:120px\">" +
     "<select onclick=\"addChildType('"+json.typeId+"');\">" +
     "<option value=\"1\">添加子分类</option>" +
     "</select>" +
     "</div>";
     return htm;*/
    var hs="";
    hs="<li style=\"height:25px;width:75px; \" onclick=addChildType('"+json.typeId+"'); value='1' doaction=\"readed\" >添加子分类</li>";
    var pp={"liString":hs};
    return getULSelect(pp);

}

function addType(){
    var url=path+"/informationType/addType.do?";
    itemInformationType=openMyDialog({title: '添加商品分类',
        content: 'url:'+url,
        icon: 'succeed',
        width:400,
        lock:true
    });
}
function addChildType(id){
    var url=path+"/informationType/addType.do?id="+id;
    itemInformationType=openMyDialog({title: '添加子分类',
        content: 'url:'+url,
        icon: 'succeed',
        width:400,
        lock:true
    });
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


function sellectCheckBox(json){
    var checkbox=$("input[type=checkbox][name=templateId][value="+json.id+"]");
    if(checkbox[0].checked){
        checkbox[0].checked=false;
    }else{
        checkbox[0].checked=true;
    }
}
function refreshTable(){
    $("#ItemInformationListTable").selectDataAfterSetParm({});
}
function makeOption6(json){
    if(json.remark&&json.remark!=""){
        return json.remark.substring(0,json.remark.length-1);
    }
    return json.remark;
}
function makeOption1(json){
    /*var htm="<div class=\"ui-select\"  style=\"margin-top:1px; width:120px\">" +
     "<select onchange=\"selectOption("+json.id+",this);\">" +
     "<option value=\"0\">--请选择--</option>" +
     "<option value=\"1\">添加标签</option>" +
     "</select>" +
     "</div>";
     return htm;*/
    var hs="";
    hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='1' doaction=\"readed\" >快速刊登</li>";
    hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='2' doaction=\"look\" >编辑</li>";
    hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='3' doaction=\"look\" >删除</li>";
    hs+="<li style=\"height:25px;\" onclick=selectOption("+json.id+",this); value='4' doaction=\"look\" >备注</li>";
    var pp={"liString":hs};
    return getULSelect(pp);
}
function selectOption(id,obj){
    var value=$(obj).attr("value");
    if(value=='1' || value==1){
        editItem(id);
    }
    if(value=='2'){
        updateItemInformation1(id,obj);
    }
    if(value=='3'){
        removeItemInformation1(id,obj);
    }
    if(value=='4'){
        addComment(id,obj);
    }
}
function loadRemarks(){
    var url = path + "/information/ajax/loadRemarks.do?";
    $().invoke(url, null,
        [function (m, r) {
            var liId=$("li[id=loadremarks]");
            var value="";
            for(var i=0;i<liId.length;i++){
                var yuspan=$(liId[i]).find("span[class=newusa_ici]");
                if(yuspan.length>0){
                    value=yuspan[0].innerHTML;
                }
            }
            var li="";
            if(value=='全部&nbsp;'){
                li="<li class=\"new_usa_list\" id=\"loadremarks\"><span class=\"newusa_i\" style=\"width: 75px;\">按标签查看：</span><span id=\"loadremarks1\" class=\"newusa_ici\" scop=\"remark\" onclick=\"onclickremark(null,0)\">全部&nbsp;</span><a href=\"javascript:void(0);\"><span class=\"newusa_ici_1\" scop=\"remark\" onclick=\"onclickremark('null',1)\">无标签&nbsp;</span></a>";
            }else if(value=='无标签&nbsp;'){
                li="<li class=\"new_usa_list\" id=\"loadremarks\"><span class=\"newusa_i\" style=\"width: 75px;\">按标签查看：</span><span id=\"loadremarks1\" class=\"newusa_ici_1\" scop=\"remark\" onclick=\"onclickremark(null,0)\">全部&nbsp;</span><a href=\"javascript:void(0);\"><span class=\"newusa_ici\" scop=\"remark\" onclick=\"onclickremark('null',1)\">无标签&nbsp;</span></a>";
            }else{
                li="<li class=\"new_usa_list\" id=\"loadremarks\"><span class=\"newusa_i\" style=\"width: 75px;\">按标签查看：</span><span id=\"loadremarks1\" class=\"newusa_ici_1\" scop=\"remark\" onclick=\"onclickremark(null,0)\">全部&nbsp;</span><a href=\"javascript:void(0);\"><span class=\"newusa_ici_1\" scop=\"remark\" onclick=\"onclickremark('null',1)\">无标签&nbsp;</span></a>";
            }
            for(var i=0;i< r.length;i++){
                var livalue=r[i].configName;
                if((i+2)%11==0){
                    if(value.indexOf(livalue)>=0){
                        li+="<li class=\"new_usa_list\" id=\"loadremarks\"><span class=\"newusa_i\" style=\"width: 75px;\"></span><a href=\"javascript:void(0);\"><span class=\"newusa_ici\" scop=\"remark\" onclick=\"onclickremark('"+r[i].id+"','"+(i+2)+"')\">"+r[i].configName+"&nbsp;</span></a>";
                    }else{
                        li+="<li class=\"new_usa_list\" id=\"loadremarks\"><span class=\"newusa_i\" style=\"width: 75px;\"></span><a href=\"javascript:void(0);\"><span class=\"newusa_ici_1\" scop=\"remark\" onclick=\"onclickremark('"+r[i].id+"','"+(i+2)+"')\">"+r[i].configName+"&nbsp;</span></a>";
                    }
                }
                if((i+2)%11==10){
                    if(value.indexOf(livalue)>=0){
                        li+="<a href=\"javascript:void(0);\"><span class=\"newusa_ici\" scop=\"remark\" onclick=\"onclickremark('"+r[i].id+"','"+(i+2)+"')\">"+r[i].configName+"&nbsp;</span></a></li>";
                    }else{
                        li+="<a href=\"javascript:void(0);\"><span class=\"newusa_ici_1\" scop=\"remark\" onclick=\"onclickremark('"+r[i].id+"','"+(i+2)+"')\">"+r[i].configName+"&nbsp;</span></a></li>";
                    }
                }
                if((i+2)%11!=0&&(i+2)%11!=10){
                    if(value.indexOf(livalue)>=0){
                        li+="<a href=\"javascript:void(0);\"><span class=\"newusa_ici\" scop=\"remark\" onclick=\"onclickremark('"+r[i].id+"','"+(i+2)+"')\">"+r[i].configName+"&nbsp;</span></a>";
                    }else{
                        li+="<a href=\"javascript:void(0);\"><span class=\"newusa_ici_1\" scop=\"remark\" onclick=\"onclickremark('"+r[i].id+"','"+(i+2)+"')\">"+r[i].configName+"&nbsp;</span></a>";
                    }
                }
            }
            li+="</li>";
            $(liId[0]).before(li);
            for(var i=0;i<liId.length;i++){
                $(liId[i]).remove();
            }
            Base.token();
        },
            function (m, r) {
                alert(r);
                Base.token();
            }]
    );
}
function editItem(id){
    var url = path + "/information/editItem.do?id="+id;
    /*itemInformation=$.dialog({title: '快速刊登',
     content: 'url:'+url,
     icon: 'succeed',
     width:1050,
     height:700,
     lock:true
     });*/
    itemInformation=openMyDialog({title: '快速刊登',
        content: 'url:'+url,
        icon: 'succeed',
        width:1050,
        height:700,
        lock:true,
        zIndex:1000
    });
}
function addComment(id,obj){
    var table=$(obj).parent().parent().parent().parent().parent().parent().parent();
    var inputs=$(table).find("input[scop1=selected]");
    for(var i=0;i<inputs.length;i++){
        $(inputs[i]).removeAttr("scop1");
    }
    var input=$(obj).parent().parent().parent().parent().parent().find("input");
    $(input).attr("scop1","selected");
    var url = path + "/information/addComment.do?id="+id;
    /* itemInformation=$.dialog({title: '备注',
     content: 'url:'+url,
     icon: 'succeed',
     width:600,
     lock:true
     });*/
    itemInformation=openMyDialog({title: '备注',
        content: 'url:'+url,
        icon: 'succeed',
        width:600,
        lock:true
    });
}
function makeOption8(json){
    return "<span  style='color: #1a93c2'>"+json.name+"</span>";
}
function makeOption7(json){
    var htm="";
    htm+="<span style='color: #8BB51B;'>"+json.sku+"</span>";
    if(json.comment||!json.comment==""){
        htm+="<br/><span class=\"newdf\" style='border-radius: 3px;' title=\""+json.comment+"\">备注："+json.comment+"</span>"
    }
    return htm;
}
function makeOption2(json){
    var htm="";

    if(json.pictureUrl){
        htm="<img onerror='nofind();'  src="+chuLiPotoUrl(json.pictureUrl)+" style=\" width: 50px;height: 50px; \">";
    }else{
        htm="<img  src='http://i.ebayimg.sandbox.ebay.com/00/s/NjAwWDgwMA==/$(KGrHqRHJEkFJ2m+ipUVBUSMpPJdmw~~60_1.JPG' style=\" width: 50px;height: 50px; \">";
    }
    return htm;
}
function nofind(){
    var img=event.srcElement;
    img.src="http://img.tembin.com/systemIMG/noimg.jpg";
    img.onerror=null;
}
function makeOption3(json){
    if(json.pictureUrl){
        var htm = "<img src='"+path+"/img/new_yes.png'/>";
        return htm;
    }else{
        var htm = "<img src='"+path+"/img/new_no.png' title=\"该商品没有图片\"   />";
        return htm;
    }
}
function makeOption4(json){
    var htm = "<input type=\"checkbox\"  name=\"templateId\" value=" + json.id + ">";
    return htm;
}
function addItemInformation(){
    var url=path+"/information/addItemInformation.do";
    /*itemInformation=$.dialog({title: '添加或修改商品信息',
     content: 'url:'+url,
     icon: 'succeed',
     width:1050,
     height:700,
     lock:true
     });*/
    itemInformation=openMyDialog({title: '添加或修改商品信息',
        content: 'url:'+url,
        icon: 'succeed',
        width:740,
        height:720,
        lock:true
    });
}
function removeItemInformation1(id,obj) {
    var url = path + "/information/ajax/removeItemInformation.do?id[0]="+id;
    $().invoke(url, null,
        [function (m, r) {
            alert(r);
            var tr=$(obj).parent().parent().parent().parent().parent();
            var table=$(obj).parent().parent().parent().parent().parent().parent().parent();
            var tableparent=$(table).parent();
            $(tr).remove();
            var div1=$(tableparent).find("div[id=showPageCount_ItemInformationListTable]");
            var div=$(div1).parent();
            var spans=$(div[0]).find("span");
            console.debug(div);
            console.debug(spans);
            var totol=spans[0].innerHTML;
            totol=parseInt(totol)-1;
            spans[0].innerHTML=totol;
            loadRemarks();
            /*onclickremark(null,0);*/
            Base.token();
        },
            function (m, r) {
                alert(r);
                Base.token();
            }]
    );
}
function removeItemInformation(){
    var id=$("input[type='checkbox'][name='templateId']:checked");
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
        var url=path+"/information/ajax/removeItemInformation.do";
        $().invoke(url,data,
            [function(m,r){
                alert(r);
                for(var i=0;i<id.length;i++){
                    var tr=$(id[i]).parent().parent();
                    var table=$(tr).parent().parent();
                    var tableparent=$(table).parent();
                    var div1=$(tableparent).find("div[id=showPageCount_ItemInformationListTable]");
                    var div=$(div1).parent();
                    var spans=$(div[0]).find("span");
                    console.debug(div);
                    console.debug(spans);
                    var totol=spans[0].innerHTML;
                    totol=parseInt(totol)-1;
                    spans[0].innerHTML=totol;
                    $(tr).remove();
                }
                loadRemarks();
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
function updateItemInformation(){
    var id=$("input[type='checkbox'][name='templateId']:checked");
    if(id.length==1){
        var url=path+"/information/addItemInformation.do?id="+$(id[0]).val();
        itemInformation=openMyDialog({title: '添加或修改商品信息',
            content: 'url:'+url,
            icon: 'succeed',
            width:725,
            height:700,
            lock:true
        });
    }else if(id.length>1){
        alert("请选择单个需要修改的数据");
    }else{
        alert("请选择需要修改的数据");
    }
}
function updateItemInformation1(id,obj){
    var table=$(obj).parent().parent().parent().parent().parent().parent().parent();
    var inputs=$(table).find("input[scop1=selected]");
    for(var i=0;i<inputs.length;i++){
        $(inputs[i]).removeAttr("scop1");
    }
    var input=$(obj).parent().parent().parent().parent().parent().find("input");
    $(input).attr("scop1","selected");
    var url=path+"/information/addItemInformation.do?id="+id;
    itemInformation=openMyDialog({title: '添加或修改商品信息',
        content: 'url:'+url,
        icon: 'succeed',
        width:725,
        height:700,
        lock:true
    });
}
function addRemark(id1){
    var id=null;
    var id2="";
    if(!id1){
        id=$("input[type='checkbox'][name='templateId']:checked");
        for(var i=0;i<id.length;i++){
            if(i==(id.length-1)){
                id2= id2+$(id[i]).val();
            }else{
                id2= id2+$(id[i]).val()+",";
            }
        }
    }else{
        id=$("input[type=checkbox][name=templateId][value="+id1+"]");
        id2=$(id).val();
    }
    if(id.length>0){
        var url=path+"/information/addRemark.do?id="+id2;
        itemInformation=openMyDialog({title: '添加标签',
            content: 'url:'+url,
            icon: 'succeed',
            width:600,
            lock:true
        });
    }else{
        alert("请选择需要添加标签的商品");
    }
}
function exportItemInformation(){
    var id=$("input[type='checkbox'][name='templateId']:checked");
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
        var url=path+"/information/exportItemInformation1.do";
        for(var i=0;i<id.length;i++){
            if(i==0){
                url=url+"?id["+i+"]="+$(id[i]).val();
            }else{
                url=url+"&id["+i+"]="+$(id[i]).val();
            }
        }
        window.open(url);
    }else{
        alert("请选择要导出的数据");
    }
}
function importItemInformation(){
    var url=path+"/information/importItemInformation.do";
    itemInformation=openMyDialog({title: '请选择导入的excel文件',
        content: 'url:'+url,
        icon: 'succeed',
        width:600,
        lock:true
    });
}
function submitCommit1(remark,information,itemType,content,conmmentForm){
    /*var remark=$("#remarkid").val();
     var information=$("#information").val();
     var itemType=$("#itemTypeid").val();
     var content=$("#content").val();*/
    /*$("#ItemInformationListTable").initTable({
     url:path + "/information/ajax/loadItemInformationList.do?",
     columnData:[
     {title:"",name:"pictureUrl",width:"2%",align:"left",format:makeOption4,click:sellectCheckBox},
     {title:"图片",name:"pictureUrl",width:"3%",align:"left",format:makeOption2,click:sellectCheckBox},
     {title:"商品/SKU",name:"sku",width:"4%",align:"left",format:makeOption7,click:sellectCheckBox},
     {title:"产品名称",name:"name",width:"36%",align:"left",format:makeOption8,click:sellectCheckBox},
     {title:"标签",name:"remark",width:"8%",align:"left",format:makeOption6,click:sellectCheckBox},
     {title:"分类",name:"typeName",width:"8%",align:"left",click:sellectCheckBox},
     {title:"状态",name:"pictureUrl",width:"2%",align:"left",format:makeOption3,click:sellectCheckBox},
     {title:"操作&nbsp;&nbsp;",name:"option1",width:"2%",align:"center",format:makeOption1}
     ],
     selectDataNow:false,
     isrowClick:false,
     showIndex:false
     });
     $("#ItemInformationListTable").selectDataAfterSetParm();*/
    refreshTable2(remark,information,itemType,content,conmmentForm);
}
function onclickremark(remark,n){
    var arr=$("span[scop=remark]");
    for(var i=0;i<arr.length;i++){
        if(i==n){
            $(arr[i]).attr("class","newusa_ici");
            $("#remarkForm").val(remark);
        }else{
            $(arr[i]).attr("class","newusa_ici_1");
        }
    }
    submitCommit();
}
function onclickinformation(information,n){
    var arr=$("span[scop=information]");
    for(var i=0;i<arr.length;i++){
        if(i==n){
            $(arr[i]).attr("class","newusa_ici");
            $("#informationForm").val(information);
        }else{
            $(arr[i]).attr("class","newusa_ici_1");
        }
    }
    submitCommit();
}
function onclickconmment(conmment,n){
    var arr=$("span[scop=conmment]");
    for(var i=0;i<arr.length;i++){
        if(i==n){
            $(arr[i]).attr("class","newusa_ici");
            $("#conmmentForm").val(conmment);
        }else{
            $(arr[i]).attr("class","newusa_ici_1");
        }
    }
    submitCommit();
}
function submitCommit(){

    var itemType=$("#itemTypeid").val();
    var content=$("#content").val();
    var remark=$("#remarkForm").val();
    var information=$("#informationForm").val();
    var conmmentForm=$("#conmmentForm").val();
    submitCommit1(remark,information,itemType,content,conmmentForm);
}
function refreshTable2(remark,information,itemType,content,conmmentForm){
    $("#ItemInformationListTable").selectDataAfterSetParm({"remark":remark,"information":information,"itemType":itemType,"content":content,"comment":conmmentForm});
}


function setTab(name,cursel,n){
    for(i=1;i<=n;i++){
        var menu=document.getElementById(name+i);
        var con=document.getElementById("con_"+name+"_"+i);
        menu.className=i==cursel?"new_tab_1":"new_tab_2";
        con.style.display=i==cursel?"block":"none";

    }
}