/**
 * Created by Administrtor on 2014/11/8.
 */
function loadItemReportData(){
    $("#itemReportTable").initTable({
        url:"/xsddWeb/ajax/getItemReportList.do",
        columnData:[
            {title:"名称",name:"datatype",width:"28%",align:"left",format:getName1},
            {title:"今天",name:"day",width:"8%",align:"left",format:getday},
            {title:"昨天",name:"yesterday",width:"8%",align:"left",format:getyesterday},
            {title:"本周",name:"week",width:"8%",align:"left",format:getweek},
            {title:"上周",name:"thatweek",width:"8%",align:"left",format:getthatweek},
            {title:"本月",name:"month",width:"8%",align:"left",format:getmonth},
            {title:"上月",name:"thatmonth",width:"8%",align:"left",format:getthatmonth}
        ],
        selectDataNow:true,
        isrowClick:false,
        showIndex:false,
        onlyFirstPage: true
    });
}
function getthatmonth(json){
    return json.thatmonth;
}
function getmonth(json){
    return json.month;
}
function getthatweek(json){
    return json.thatweek;
}
function getweek(json){
    return json.week;
}
function getyesterday(json){
    return json.yesterday;
}
function getName1(json){
    var html = "";
    if(json.datatype=="1"){
        html="新刊登物品";
    }else if(json.datatype=="2"){
        html="结束的刊登物品";
    }else if(json.datatype=="3"){
        html="结束并卖出的刊登物品";
    }else if(json.datatype=="4"){
        html="销售比";
    }else if(json.datatype=="5"){
        html="刊登费（新刊登）";
    }else if(json.datatype=="6"){
        html="刊登费（结束的刊登）";
    }
    return html;
}
function getday(json){
    return json.day;
}