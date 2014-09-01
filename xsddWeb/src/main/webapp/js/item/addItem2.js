/**
 * Created by Administrtor on 2014/9/1.
 */

/**将几个加载模块的方法定义为一个全局变量*/
var url = window.location.href;
var loadModelFunctions={
    "buyer":loadDataBuyer,
    "discountpriceinfo":loadDiscountPriceInfo,
    "itemLocation":loadItemLocation,
    "pay":loadPayOption,
    "returnpolicy":loadReturnpolicy,
    "shippingDeails":loadShippingDeails,
    "descriptiondetails":loaddescriptiondetails
};
/**编辑器的工具栏*/
var ueditorToolBar={
    toolbars:[['source','FullScreen',  'Undo', 'Redo','Bold','fontfamily', 'fontsize','cleardoc','|',
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

/**加载几个模块的信息*/
/**加载买家要求*/
var loadDataBuyerV=false;
function loadDataBuyer(){
    if(loadDataBuyerV==true){return;}
    $("#buyer").initTable({
        url: path + "/ajax/loadBuyerRequirementDetailsList.do",
        columnData: [
            {title: "选择", name: "option1", width: "8%", align: "left", format: returnBuyer},
            {title: "名称", name: "name", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "所有买家购买", name: "buyerFlag", width: "8%", align: "left"}
        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var buyerId = getLocalStorage("buyerId");
                $("input[type='radio'][name='buyerId'][value='" + buyerId + "']").attr("checked", true);
            }
        }
    });
    loadDataBuyerV=true;
}

//加载折扣信息
var loadDiscountPriceInfoV=false;
function loadDiscountPriceInfo(){
    if(loadDiscountPriceInfoV==true){return;}
    $("#discountpriceinfo").initTable({
        url: path + "/ajax/loadDiscountPriceInfoList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnDiscountpriceinfo},
            {title: "名称", name: "name", width: "8%", align: "left"},
            {title: "账户名称", name: "ebayName", width: "8%", align: "left"},
            {title: "开始时间", name: "disStarttime", width: "8%", align: "left"},
            {title: "结束时间", name: "disEndtime", width: "8%", align: "left"},
            {title: "降价", name: "madeforoutletcomparisonprice", width: "8%", align: "left"},
            {title: "是否免运费", name: "isShippingfee", width: "8%", align: "left"}
        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var discountpriceinfoId = getLocalStorage("discountpriceinfoId");
                $("input[type='radio'][name='discountpriceinfoId'][value='" + discountpriceinfoId + "']").attr("checked", true);
            }
        }
    });
    loadDiscountPriceInfoV=true;
}

/**物品所在地*/
var loadItemLocationV=false;
 function loadItemLocation(){
     if(loadItemLocationV==true){return;}
     $("#itemLocation").initTable({
         url: path + "/ajax/loadItemAddressList.do",
         columnData: [
             {title: "选项", name: "option1", width: "8%", align: "left", format: returnItemLocation},
             {title: "名称", name: "name", width: "8%", align: "left"},
             {title: "地址", name: "address", width: "8%", align: "left"},
             {title: "国家", name: "countryName", width: "8%", align: "left"},
             {title: "邮编", name: "postalcode", width: "8%", align: "left"}
         ],
         selectDataNow: true,
         isrowClick: false,
         showIndex: false,
         afterLoadTable:function(){
             if (url.indexOf("addItem.do") != -1) {
                 var itemLocationId = getLocalStorage("itemLocationId");
                 $("input[type='radio'][name='itemLocationId'][value='" + itemLocationId + "']").attr("checked", true);
             }
         }
     });
     loadItemLocationV=true;
 }

//付款选项
var loadPayOptionV=false;
function loadPayOption(){
    if(loadPayOptionV==true){return;}
    $("#pay").initTable({
        url: path + "/ajax/loadPayPalList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnPay},
            {title: "名称", name: "payName", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "paypal账号", name: "payPalName", width: "8%", align: "left"},
            {title: "描述", name: "paymentinstructions", width: "8%", align: "left"}

        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var payId = getLocalStorage("payId");
                $("input[type='radio'][name='payId'][value='" + payId + "']").attr("checked", true);
            }
        }
    });
    loadPayOptionV=true;
}

//退货选项
var loadReturnpolicyV=false;
function loadReturnpolicy(){
if(loadReturnpolicyV==true){return;}
    $("#returnpolicy").initTable({
        url: path + "/ajax/loadReturnpolicyList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnReturnpolicy},
            {title: "名称", name: "name", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "退货政策", name: "returnsAcceptedOptionName", width: "8%", align: "left"},
            {title: "退货天数", name: "returnsWithinOptionName", width: "8%", align: "left"},
            {title: "退款方式", name: "refundOptionName", width: "8%", align: "left"},
            {title: "退货运费由谁负担", name: "shippingCostPaidByOptionName", width: "8%", align: "left"}

        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var returnpolicyId = getLocalStorage("returnpolicyId");
                $("input[type='radio'][name='returnpolicyId'][value='" + returnpolicyId + "']").attr("checked", true);
            }
        }
    });
    loadReturnpolicyV=true;
}

/**运输选项*/
var loadShippingDeailsV=false;
function loadShippingDeails(){
    if(loadShippingDeailsV==true){
        return;
    }
    $("#shippingDeails").initTable({
        url: path + "/ajax/loadShippingDetailsList.do",
        columnData: [
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnShippingDeails},
            {title: "名称", name: "shippingName", width: "8%", align: "left"},
            {title: "站点", name: "siteName", width: "8%", align: "left"},
            {title: "ebay账号", name: "ebayName", width: "8%", align: "left"}

        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var shippingDeailsId = getLocalStorage("shippingDeailsId");
                $("input[type='radio'][name='shippingDeailsId'][value='" + shippingDeailsId + "']").attr("checked", true);
            }
        }
    });
    loadShippingDeailsV=true;
}

/**卖家描述*/
var descriptiondetailsV=false;
function loaddescriptiondetails(){
    if(descriptiondetailsV==true){return;}
    $("#descriptiondetails").initTable({
        url:path + "/ajax/loadDescriptionDetailsList.do",
        columnData:[
            {title: "选项", name: "option1", width: "8%", align: "left", format: returnDescriptiondetails},
            {title:"名称",name:"name",width:"8%",align:"left"},
            {title:"pay",name:"payInfo",width:"8%",align:"left"},
            {title:"shipping",name:"shippingInfo",width:"8%",align:"left"},
            {title:"contact",name:"contactInfo",width:"8%",align:"left"},
            {title:"Guarantee",name:"guaranteeInfo",width:"8%",align:"left"},
            {title:"Feedback",name:"feedbackInfo",width:"8%",align:"left"}
        ],
        selectDataNow: true,
        isrowClick: false,
        showIndex: false,
        afterLoadTable:function(){
            if (url.indexOf("addItem.do") != -1) {
                var sellerItemInfoId = getLocalStorage("sellerItemInfoId");
                $("input[type='radio'][name='sellerItemInfoId'][value='" + sellerItemInfoId + "']").attr("checked", true);
            }
        }
    });
    descriptiondetailsV=true;
}



/**返回买家要求单选框*/
function returnBuyer(json) {
    var htm = "<input type=\"radio\" name=\"buyerId\" value=" + json.id + ">";
    return htm;
}

/**返回折扣信息单选框*/
function returnDiscountpriceinfo(json) {
    var htm = "<input type=\"radio\" name=\"discountpriceinfoId\" value=" + json.id + ">";
    return htm;
}

/**返回物品所在地信息单选框*/
function returnItemLocation(json) {
    var htm = "<input type=\"radio\" name=\"itemLocationId\" value=" + json.id + ">";
    return htm;
}
/**返回付款选项信息单选框*/
function returnPay(json) {
    var htm = "<input type=\"radio\" name=\"payId\" value=" + json.id + ">";
    return htm;
}


/**返回退货项信息单选框*/
function returnReturnpolicy(json) {
    var htm = "<input type=\"radio\" name=\"returnpolicyId\" value=" + json.id + ">";
    return htm;
}

/**返回运输选项信息单选框*/
function returnShippingDeails(json) {
    var htm = "<input type=\"radio\" name=\"shippingDeailsId\" value=" + json.id + ">";
    return htm;
}

function returnDescriptiondetails(json) {
    var htm = "<input type=\"radio\" name=\"sellerItemInfoId\" value=" + json.id + ">";
    return htm;
}


function addValueTr(obj1, obj2) {
    var trStr = '<tr height="20px;"><td><input type="text" name="name"  class="validate[required] form-control" value="' + obj1 + '"></td><td><input type="text" name="value" class="validate[required] form-control" value="' + obj2 + '"></td><td><a href="javascript:void(0)" onclick="removeROW(this)">移除</a></td></tr>';
    return trStr;
}
function removeROW(obj) {
    $(obj).parent().parent().remove();
}
function addAttrTr(showName, name, value) {
    var trStr = '<tr><td>' + showName + '</td><td><input type="text" name="' + name + '" value="' + value + '"></td></tr>';
    return trStr;
}
/**
 *添加自定义属性
 */
function addValue() {
    $("#attTable").append(addValueTr('', ''));
}
/**
 *添加定固定属性
 */
function addAttr(showName, name) {
    $("#trValue").after().append(addAttrTr(showName, name, ''));
}

function onShow(obj) {
    _sku = obj.value;
}
//选择刊登 类型，判断显示
function changeRadio(obj) {
    if (obj == "2") {
        $("#oneAttr").hide();
        $("#twoAttr").show();
        $("#Auction").hide();
    } else if (obj == "FixedPriceItem") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").hide();
    } else if (obj == "Chinese") {
        $("#oneAttr").show();
        $("#twoAttr").hide();
        $("#Auction").show();
    }
}
//点击添回SKU输入项
function addInputSKU(obj) {
    var len = $(obj).parent().parent().find("table").find("tr").find("td").length / $(obj).parent().parent().find("table").find("tr").length - 5;
    $(obj).parent().parent().find("table").append(addTr(len));
    $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
    changeBackcolour();
}
function changeBackcolour(){
    $("#moreAttrs tr").hover(function() {
        $(this.cells[0]).addClass('showDragHandle');
    }, function() {
        $(this.cells[0]).removeClass('showDragHandle');
    });
}
//添加一行数据，用于填写
function addTr(len) {
    var str = "";
    str += "<tr>";
    str += "<td class='dragHandle'></td>";
    str += "<td><input type='text' name='SKU'  class='validate[required] form-control'></td>";
    str += "<td><input type='text' name='Quantity' class='validate[required,custom[integer]] form-control'></td>";
    str += "<td><input type='text' name='StartPrice.value' class='validate[required,custom[number]] form-control'></td>";
    for (var i = 0; i < len; i++) {
        str += "<td><input type='text' name='attr_Value' class='validate[required] form-control' onblur='addb(this)' size='10' ></td>";
    }
    str += "<td name='del'><a href='javascript:void(0)' onclick='removeCloums(this)'>删除</a></td>";
    str += "</tr>";
    return str;
}
//添加属性列
function addMoreAttr(obj) {
    $(obj).parent().parent().find("table").find("tr").each(function (i, d) {
        $(d).find("td").each(function (ii, dd) {
            if ($(dd).attr("name") == "del") {
                if (i == 0) {
                    $(dd).before("<td><a href='javascript:void(0)' onclick='removeCols(this)'>移除</a><input type='text' size='8' class='validate[required] form-control' name='attr_Name' onblur='addc(this)'></td>");
                } else {
                    $(dd).before("<td><input type='text' size='10' name='attr_Value' onblur='addb(this)' class='validate[required] form-control'></td>");
                }
            }
        });
    });
    // $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
}
//删除多属性中的SKU输入项
function removeCloums(obj) {
    $(obj).parent().parent().remove();
    var attrValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
        if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
            attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
        }
    });
    var dicMap = new Map()
    for (var i = 0; i < attrValue.keys.length; i++) {
        var url = $("input[name='" + attrValue.get(attrValue.keys[i]) + "']");
        var dics = new Map();
        for (var j = 0; j < url.length; j++) {
            dics.put(j, $(url[j]).val());
        }
        dicMap.put(attrValue.get(attrValue.keys[i]), dics);
    }
    $("#picMore").html("");
    for (var i = 0; i < attrValue.keys.length; i++) {
        var m = dicMap.get(attrValue.get(attrValue.keys[i]));
        for (var j = 0; j < m.keys.length; j++) {
            $('#' + attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='50' width='50' />");
        }
        $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
    }
    $("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
    changeBackcolour()
}
//移除属性值
function removeCols(obj) {
    $("#moreAttrs tr th:eq(" + ($(obj.parentNode)[0].cellIndex + 1) + ")").remove();
    $("#moreAttrs tr td:nth-child(" + ($(obj.parentNode)[0].cellIndex + 1) + ")").remove();

    var attrValue = new Map();
    $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
        if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
            attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
        }
    });
    var dicMap = new Map()
    for (var i = 0; i < attrValue.keys.length; i++) {
        var url = $("input[name='" + attrValue.get(attrValue.keys[i]) + "']");
        var dics = new Map();
        for (var j = 0; j < url.length; j++) {
            dics.put(j, $(url[j]).val());
        }
        dicMap.put(attrValue.get(attrValue.keys[i]), dics);
    }
    $("#picMore").html("");
    for (var i = 0; i < attrValue.keys.length; i++) {

        $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
        var m = dicMap.get(attrValue.get(attrValue.keys[i]));
        for (var j = 0; j < m.keys.length; j++) {
            $('#' + attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='50' width='50' />");
        }
    }
    //$("#moreAttrs").tableDnD({dragHandle: ".dragHandle"});
}
var attrName = new Map();
//当输入属性名称时调用的方法
function addc(obj) {
    if ($(obj.parentNode)[0].cellIndex == 3) {
        attrName = obj.value;
    }

    var attrValue = new Map();
    if ($(obj.parentNode)[0].cellIndex == 3) {
        $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
            if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
                attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
            }
        });
        $("#picMore").html("");
        for (var i = 0; i < attrValue.keys.length; i++) {
            $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
        }
    }
}
//当输入属性值时调用的方法
function addb(obj) {
    var attrValue = new Map();
    if ($(obj.parentNode)[0].cellIndex == 3) {
        $("#moreAttrs tr td:nth-child(5)").each(function (i, d) {
            if ($(d).find("input[name='attr_Value']").val() != undefined && $(d).find("input[name='attr_Value']").val() != "") {
                attrValue.put($(d).find("input[name='attr_Value']").val(), $(d).find("input[name='attr_Value']").val());
            }
        });
        var dicMap = new Map()
        for (var i = 0; i < attrValue.keys.length; i++) {
            var url = $("input[name='" + attrValue.get(attrValue.keys[i]) + "']");
            var dics = new Map();
            for (var j = 0; j < url.length; j++) {
                dics.put(j, $(url[j]).val());
            }
            dicMap.put(attrValue.get(attrValue.keys[i]), dics);
        }
        $("#picMore").html("");
        for (var i = 0; i < attrValue.keys.length; i++) {
            //alert($("input[name='"+attrValue.get(attrValue.keys[i])+"']").length);
            $("#picMore").append(addPic(attrName, attrValue.get(attrValue.keys[i])));
            var m = dicMap.get(attrValue.get(attrValue.keys[i]));
            for (var j = 0; j < m.keys.length; j++) {
                $('#' + attrValue.get(attrValue.keys[i])).before("<input type='hidden' name='" + attrValue.get(attrValue.keys[i]) + "' value='" + m.get(j) + "'><img src='" + m.get(j) + "' height='50' width='50' />");
            }
            //$().image_editor.init(attrName+"."+attrValue.get(attrValue.keys[i])); //编辑器的实例id
            $().image_editor.show(attrValue.get(attrValue.keys[i])); //上传图片的按钮id
        }
    }
}
function addPic(attrName, attrValue) {
    var str = "";
    str += "<div><div>" + attrName + ":" + attrValue + "</div><script type=text/plain id='" + attrName + "." + attrValue + "' />";
    str += "<div><a href='javascript:void(0)' id=" + attrValue + " onClick='selectPic(this)'>选择图片</a></div>";
    str += "</div>";
    return str;
}

var afterUploadCallback = null;
var sss;
//当选择图片后生成图片地址
function selectPic(a) {
    afterUploadCallback = {"imgURLS": addPictrueUrl};
    sss = a.id;
}

function addPictrueUrl(urls) {
    if (sss == "apicUrls") {//商品图片
        var str = '';
        for (var i = 0; i < urls.length; i++) {
            str += "<div class='brick small'>";
            str += '<span><input type="hidden" name="PictureDetails.PictureURL[' + i + ']" value="' + urls[i].src + '"><img src=' + urls[i].src.replace("@", ":") + ' height="100%" width="100%" /></span>'
            str += "<a class='delete' href='#'>&times;</a></div>";
            $("#picture").append(str);
            str = "";
        }
    } else {//多属性图片
        $('#' + sss).before("<input type='hidden' name='VariationSpecificValue_" + sss + "' value='" + sss + "'>");
        for (var i = 0; i < urls.length; i++) {
            $('#' + sss).before("<span><input type='hidden' name='" + sss + "' value='" + urls[i].src + "'><img src='" + urls[i].src.replace("@", ":") + "' height='50' width='50' /> <a href='javascritp:void(0)' onclick='removeThis(this)'>移除</a></span>");
        }
    }
    initDraug();//初始化拖动图片
}
function removeThis(obj) {
    $(obj).parent().remove();
}

//当输入分类，
function addTypeAttr() {
    $('#typeAttrs').html('');
    if(jQuery("#PrimaryCategory").validationEngine("validate"))
    {
        return;
    }
    var values = $("#PrimaryCategory").val();
    getCategorySpecificsData(values, "typeAttrs", "afterClickAttr", "attTable");
}
var CategoryType;
function selectType() {
    var title = $("#Title").val();
    if(title==null||title==""){
        alert("请输入第一标题！");
        return;
    }
    CategoryType = $.dialog({
        id:"itemClass_",
        title: '选择商品分类',
        content: 'url:' + path + '/category/initSelectCategoryPage.do?title='+title,
        icon: 'succeed',
        zIndex:2000,
        width: 650,
        lock: true
    });
}
function incount(obj) {
    $("#incount").text($(obj).val().length);
}

function setTab(obj) {
    $("div[name='showModel']").each(function (i, d) {
        $(d).hide();
    });
    $(obj).parent().find("dt").each(function (i, d) {
        $(d).removeClass("new_ic_1");
    });
    var name = $(obj).attr("name");
    $(obj).addClass("new_ic_1");
    $("#" + name).show();
    loadModelFunctions[name]();
}
function closeWindow(){
    var api = frameElement.api, W = api.opener;
    W.refreshTable();
    W.returnItem.close();
}
var selectTemplates;
function selectTemplate() {
    var apis = frameElement.api, W = apis.opener;
    selectTemplates = $.dialog({title: '选择模板',
        content: 'url:' + path + '/selectTemplate.do',
        icon: 'succeed',
        parent: apis,
        lock: true,
        width: 650,
        height: 600
    });
}