/**
 * Created by Administrtor on 2014/11/8.
 */
function loadFeedBackReportData(){
    $("#feedBackReportTable").initTable({
        url:"/xsddWeb/ajax/getFeedBackReportList.do",
        columnData:[
            {title:"名称",name:"dataType",width:"8%",align:"left",format:getName},
            {title:"今天",name:"day",width:"8%",align:"left"},
            {title:"昨天",name:"yesterday",width:"8%",align:"left"},
            {title:"本周",name:"week",width:"8%",align:"left"},
            {title:"上周",name:"thatweek",width:"8%",align:"left"},
            {title:"本月",name:"month",width:"8%",align:"left"},
            {title:"上月",name:"thatmonth",width:"8%",align:"left"}
        ],
        selectDataNow:true,
        isrowClick:false,
        showIndex:false,
        onlyFirstPage: true
    });
}

function getName(json){
    var html = "";
    if(json.dataType=="Positive"){
        html="<img src='img/new_one_1.gif'>&nbsp;正评";
    }else if(json.dataType=="Neutral"){
        html="<img src='img/new_one_2.gif'>&nbsp;中评";
    }else if(json.dataType=="Negative"){
        html="<img src='img/new_one_3.gif'>&nbsp;负评";
    }
    return html;
}