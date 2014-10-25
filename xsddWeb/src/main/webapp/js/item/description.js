var descDiag="";
function addDescriptionDetails(){
    descDiag=$.dialog({title: '新增卖家描述',
        content: 'url:/xsddWeb/addDescriptionDetails.do',
        icon: 'succeed',
        width:1025,
        lock:true
    });
}
function editDescriptionDetails(id){
    descDiag= $.dialog({title: '编辑卖家描述',
        content: 'url:/xsddWeb/editDescriptionDetails.do?id='+id,
        icon: 'succeed',
        width:1025,
        lock:true
    });
}

function refreshTableDesciption(){
    $("#descriptiondetails").selectDataAfterSetParm({"bedDetailVO.deptId":"", "isTrue":0});
}
function loadDesciption(){
    $("#descriptionDetailsListTable").initTable({
        url:path + "/ajax/loadDescriptionDetailsList.do",
        columnData:[
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"数据状态",name:"option1",width:"8%",align:"left",format:makeOption2},
            {title:"操作",name:"option1",width:"8%",align:"left",format:makeOption1descript}
        ],
        selectDataNow:false,
        isrowClick:true,
        showIndex:false
    });
    refreshTableDesciption();
}
/**组装操作选项*/
function makeOption1descript(json){
    var hs="";
    hs+="<li style='height:25px' onclick=editDescriptionDetailsselect('"+json.id+"') value='"+json.id+"' doaction=\"look\" >查看</li>";
    hs+="<li style='height:25px' onclick=editDescriptionDetails('"+json.id+"') value='"+json.id+"' doaction=\"look\" >编辑</li>";
    if(json.checkFlag=="0") {
        hs += "<li style='height:25px' onclick=delDescriptionDetails('" + json.id + "') value='" + json.id + "' doaction=\"look\" >禁用</li>";
    }else{
        hs+="<li style='height:25px' onclick=delDescriptionDetails('"+json.id+"') value='"+json.id+"' doaction=\"look\" >启用</li>";
    }
    var pp={"liString":hs};
    return getULSelect(pp);
}

function editDescriptionDetailsselect(id){
    descDiag=$.dialog({title: '查看退货政策',
        content: 'url:/xsddWeb/editDescriptionDetails.do?id='+id+'&type=01',
        icon: 'succeed',
        width:1025,
        lock:true
    });
}
function delDescriptionDetails(id){
    var url=path+"/ajax/delDescriptionDetails.do?id="+id;
    $().invoke(url,{},
        [function(m,r){
            alert("操作成功！");
            Base.token();
            refreshTableDesciption();
        },
            function(m,r){
                alert(r);
                Base.token();
            }]
    );
}
