
;
(function ($) {
	//初始化table
	$.fn.initTable = function (obj) {
		var defaultOption = {
			tableCss: "tab_ZY",//table样式
			url: null,//获得数据url
			columnData: null,//table中每一列的信息[{title:"第一列",name:"columnName",width:"10%",format:function(json){return json;},align:"center",click:function(json){}},{。。。。}，。。。。。]
			pageCountArray: [ 10, 20, 50, 100 ],//每页显示条数
			total: 0,
			allData: null,//当前用户条件查询出的数据集合
			userParm: null,//保存用户当前查询条件参数
			sysParm: {"jsonBean.pageNum": 1, "jsonBean.pageCount": 10},//系统参数,如果需要分页,默认分页为第一页,分页条数为每页10条

			allowClick: true,//是否允许添加单击事件,(注:如果不需要单击事件，请将属性设置为false,减少数据循环)

			isrowClick: false,//是否是行单击(注:如果为true,columnData中的列单击将失效)
			rowClickMethod: null,//行单击事件'
            afterLoadTable:null,//table加载完成以后执行的方法
            isBindDataToTr:false,//是否将行数据绑定到tr，供加载列表后调用
			selectDataNow: false,//是否立刻查询数据

			indexWidth: null,//序号列宽度
			showIndex: false,//是否显示序号

			onlyFirstPage: false,//只有一页数据

			showDataNullMsg:true//当查询数据为空时，显示提示信息
		};
		var SUCCESS = true;// 初始化状态
		if(typeof obj == 'object') {
			try {
				$.extend(defaultOption, obj);
				$(this).data("option", defaultOption);
				if(defaultOption.selectDataNow) {					
					this.createdTable();					
				}
			} catch(e) {				
				SUCCESS = false;
			}
		} else {
			SUCCESS = false;
		}
		if(!SUCCESS) {
			alert("\u521D\u59CB\u5316\u5206\u9875Table\u51FA\u9519,\u8BF7\u68C0\u67E5\u53C2\u6570!");
		}
	};
	//创建table
	$.fn.createdTable = function () {
		var option = this.data("option");
		this.empty();//清空内容
		this.loadDataByAjax();//加载数据
		this.analysis().appendTo(this);//解析数据构造table整体
		if(option.allData!=null && option.allData.length>0){
			var paging = this.createPaging2();
			if(paging != null) {
				this.append(paging);
			}
			if(option.allowClick) {//绑定事件
				this.bindClick();
			}
			this.setRowClass();//设置行样式
		}
        /**设置当table加载完成以后需要执行的方法*/
        if(option.afterLoadTable!=null && $.isFunction(option.afterLoadTable)){
            option['afterLoadTable']();
        }
        this.setCurrPageNumColor();
        try{initULSelect();}catch (e){}
        //alert($("#newtipi").css("left"))
	};

	//加载数据
	$.fn.loadDataByAjax = function () {
		var option = this.data("option");
		var url = null;//为了兼容一些程序设计时候用了url传参数，所以解析下url
		var urlArray = option.url.split("?");
		if(urlArray.length == 2) {
			url = urlArray[0] + "?math=" + Math.random() + "&" + urlArray[1];
		} else {
			url = option.url + "?math=" + Math.random();
		}
		var total = 0;
		var tdata = null;

		$().invoke(url, option.sysParm, function (message, json) {
			tdata = json.list;
			if(json.total != undefined) {//未分页时候的总数未定义
				total = json.total;
			}
		}, {async: false});

		option.total = total;
		option.allData = tdata;
	};

	//解析数据
	$.fn.analysis = function () {
		var option = this.data("option");
		var tableData = option.allData;
		var table = $("<table name = 'myTablePlug'></table>").attr("width", "100%").attr("border", "0").attr("cellspacing",
				"0").attr("cellpadding", "0").attr("class", option.tableCss);
		var headTr = $("<tr></tr>");
		if(option.showIndex) {
			var headThIndex = $("<th></th>").attr("width",
				option.indexWidth == null ? "5%" : option.indexWidth).html("\u5E8F\u53F7");
			headThIndex.appendTo(headTr);
		}
		for(var i in option.columnData) {
			var headThTitle = $("<th></th>").attr("width",
				option.columnData[i]["width"]).html(option.columnData[i]["title"]);
			headThTitle.appendTo(headTr);
		}
		headTr.appendTo(table);//构造列表头结束
		if(tableData != null && tableData.length > 0) {
			for(var i in tableData) {
				var contentTr = $("<tr></tr>");
				contentTr.bind("click",contentTr,function(event){
					$.clickTr(event.data);
				});
                if(option.isBindDataToTr){
                    contentTr.data("trData",tableData[i]); //将行数据绑定到tr
                }

				if(option.showIndex) {
					var pagenum = parseInt(option.sysParm["jsonBean.pageNum"]);
					var pagecount = parseInt(option.sysParm["jsonBean.pageCount"]);
					var no = (pagenum - 1) * pagecount + (parseInt(i) + 1);
					$("<td></td>").attr("style", "text-align:left;cursor: pointer;").html(no).appendTo(contentTr);
				}

				for(var j = 0; j < option.columnData.length; j++) {
					if(option.columnData[j]["format"] == undefined) {
						if(option.columnData[j]["showTips"] == undefined || option.columnData[j]["showTips"] == false) {
							var contentTd = $("<td></td>").attr("style",
									"tword-break:break-all;word-wrap:break-word;cursor: pointer;text-align:" + (option.columnData[j]["align"] == undefined ? "center" : option.columnData[j]["align"])).html((tableData[i][option.columnData[j]["name"]] == null) || (tableData[i][option.columnData[j]["name"]] == '') ? "&nbsp;" : tableData[i][option.columnData[j]["name"]]);
							contentTd.appendTo(contentTr);
						} else {
							var wordWidth = $(this).width() * $.pointToFloatNumber(option.columnData[j]["width"]);
							var tdContentWidth = 0;
							var tdContent = "";
							if(tableData[i][option.columnData[j]["name"]] != null || tableData[i][option.columnData[j]["name"]] != '') {
								tdContentWidth = $.getStringSizeNumber(tableData[i][option.columnData[j]["name"]]);
							}
							//-20是因为样式的左右各有10像素的距离
							if(tdContentWidth != 0 && tdContentWidth > wordWidth - 20) {
								tdContent = ($.getInterceptionReturnString(tableData[i][option.columnData[j]["name"]],
									wordWidth - 40)) + "...";
							} else {
								tdContent = tableData[i][option.columnData[j]["name"]];
							}
							if(tableData[i][option.columnData[j]["name"]] == null || tableData[i][option.columnData[j]["name"]] == '') {
								$("<td></td>").css("tword-break", "break-all").css("word-wrap",
										"break-word").css("cursor", "pointer").css("text-align",
										option.columnData[j]["align"] == undefined ? "center" : option.columnData[j]["align"]).html((tdContent == null || tdContent == '') ? "&nbsp;" : tdContent).appendTo(contentTr);
							} else {
								$("<td></td>").css("tword-break", "break-all").css("word-wrap",
										"break-word").css("cursor", "pointer").css("text-align",
										option.columnData[j]["align"] == undefined ? "center" : option.columnData[j]["align"]).attr("title",
										(tableData[i][option.columnData[j]["name"]] == null) || (tableData[i][option.columnData[j]["name"]] == '') ? "&nbsp;" : tableData[i][option.columnData[j]["name"]]).html((tdContent == null || tdContent == '') ? "&nbsp;" : tdContent).appendTo(contentTr);
							}
						}
					} else {
						$("<td></td>").css("tword-break", "break-all").css("word-wrap", "break-word").css("cursor",
								"pointer").css("text-align",
								option.columnData[j]["align"] == undefined ? "center" : option.columnData[j]["align"]).html(option.columnData[j]["format"](option.allData[i])).appendTo(contentTr);
					}
				}
				contentTr.appendTo(table);
			}
		} else {
			if(option.showDataNullMsg){
				var colLength=(option.showIndex?option.columnData.length+1:option.columnData.length);
				var leftTrSize= parseInt(colLength/2,10);
				var rightTrSize=colLength-leftTrSize;
				table.attr("class", "tab_ZY");
				table.append(
					$("<tr></tr>")
						.append($("<td></td>").attr("colspan",leftTrSize).attr("width","50%").attr("height", 200).attr("align","right")
								.append($("<img src='" + path + "/js/table/100.png'>")))
						.append($("<td></td>").attr("colspan",rightTrSize).attr("width","50%").attr("align", "left").attr("style","font-size:20px;")
							.html("\u6CA1\u6709\u641C\u7D22\u5230\u7ED3\u679C!"))
				);
			}
		}
		return table;
	};

	$.fn.createPaging = function () {
		var option = this.data("option");
		var id = this.attr("id");
		var Previous = this.Previous();
		var Next = this.Next();
		var GetLastPageNum = this.getLastPageNum();
		if(!option.onlyFirstPage && option.allData != null && option.allData.length > 0) {//创建分页
			return $("<div></div>").attr("class", "mtop1 page").append($("<span></span>").attr("class",
						"left").html("\u5171" + option.total + "\u6761\u8BB0\u5F55,\u6BCF\u9875" + option.sysParm["jsonBean.pageCount"] + "\u6761\u8BB0\u5F55,\u5F53\u524D\u7B2C" + option.sysParm["jsonBean.pageNum"] + "\u9875")).append($("<span></span>").attr("class",
						"right").append($("<select></select>").css("width", "55px").css("height", "22px").css("margin-top", "3px").css("margin-right", "3px").bind("change",
						function(){
							$("#" + id).changePageCount(this);
						}).html(this.getPageCount())).append($("<a></a>").html("\u9996\u9875").bind("click",
						function () {
							$("#" + id).toPage(1);
						})).append($("<a></a>").html("\u4E0A\u4E00\u9875").bind("click", function () {
						$("#" + id).toPage(Previous);
					})).append(this.showOtherPage()).append($("<a></a>").html("\u4E0B\u4E00\u9875").bind("click",
						function () {
							$("#" + id).toPage(Next);
						})).append($("<a></a>").html("\u672B\u9875").bind("click", function () {
						$("#" + id).toPage(GetLastPageNum);
					})));
		} else {
			return null;
		}
	};

    //第二种分页条
    $.fn.createPaging2=function(){
        //option.sysParm["jsonBean.pageNum"] 当前第几页
        var option = this.data("option");
        var id = this.attr("id");
        var Previous = this.Previous();
        var Next = this.Next();
        var GetLastPageNum = this.getLastPageNum();
        if(!option.onlyFirstPage && option.allData != null && option.allData.length > 0) {
            var htmlPage="<div class=\"page_newlist\"><div><div id=\"newtipi\"><li>";
                htmlPage+="<a onmouseout=showPageNumList2('"+id+"') onmouseover=showPageNumList2('"+id+"') id='showCount' href=\"javascript:void(0)\">显示"+option.sysParm["jsonBean.pageCount"]+"条</a>";
                htmlPage+="<ul onmouseout=showPageNumList2('"+id+"') onmouseover=showPageNumList2('"+id+"') id='pageListUL"+id+"' style=\"visibility:hidden ;z-index: 9999;left: auto;position:relative;\">";
            var pageNumArr=option.pageCountArray;
            for(var i in pageNumArr){
                htmlPage+="<li style='position: absolute;left: 0px;top: "+(i*30)+"px' ><a onclick=changeMyTablePageCount(\""+id+"\","+pageNumArr[i]+") href=\"javascript:void(0)\">显示"+pageNumArr[i]+"条</a></li>";
            }
            htmlPage+="</ul></li></div></div>";
            htmlPage+="共 <span style=\"color:#F00\">"+option.total+"</span> 条记录 <span style=\"color:#F00\">"+GetLastPageNum+"</span> 页";
            htmlPage+="</div>";

            htmlPage+="<div class=\"maage_page\">";
            htmlPage+="<li onclick=\"$('#" + this.attr("id") + "').toPage(" + 1 + ")\">|<</li>";
            htmlPage+="<li onclick=\"$('#" + this.attr("id") + "').toPage(" + Previous + ")\"><</li>";
            htmlPage+=this.showOtherPage2();
            htmlPage+="<li onclick=\"$('#" + this.attr("id") + "').toPage(" + Next + ")\">></li>" ;
            htmlPage+="<li onclick=\"$('#" + this.attr("id") + "').toPage(" + GetLastPageNum + ")\">>|</li>" ;
            htmlPage+="</div>";
            return htmlPage;
        }else{
            return null;
        }
    };

	//绑定行或者列click事件当设置了行单击事件后，列单击时间将失效
	$.fn.bindClick = function () {
		var option = this.data("option");
		var tableOption = option;
		if(option.rowClickMethod != null && option.isrowClick == true) {
			//添加行单击事件
			var tableData = option.allData;
			$("tr", this).each(function (i) {
				if(i > 0) {
					$(this).unbind("click").click(function () {
						tableOption["rowClickMethod"](tableData[i - 1], this);
					});
				}
			});
		} else {
			//添加列单击事件
			var columnData = option.columnData;
			var tableData = option.allData;
			$("tr", this).each(function (i) {
				if(i > 0) {//排除表头
					$("td", this).each(function (j) {
						if(option.showIndex) {
							if(j != 0) {
                                try{
                                    if(columnData[j - 1]["click"] != undefined) {
                                        $(this).unbind("click").click(function () {
                                            columnData[j - 1]["click"](tableData[i - 1]);
                                        });
                                    }
                                }catch (e){}

							}
						} else {
							if(j != 0) {
                                try{
								if(columnData[j]["click"] != undefined) {
									$(this).unbind("click").click(function () {
										columnData[j]["click"](tableData[i - 1]);
									});
								}
                                }catch (e){}
							}
						}
					});
				}
			});
		}
	};

    //设置当前页数的颜色
    $.fn.setCurrPageNumColor=function(){
        var option = this.data("option");
        var cur=option.sysParm["jsonBean.pageNum"];
        $(".maage_page").find("li").each(function(i,d){

            if(parseInt($(d).html()) ==cur){
                $(d).addClass("page_cl");
            }
        });
    }

	//设置行样式
	$.fn.setRowClass = function () {
		var option = this.data("option");
		$("." + option.tableCss + " tr").mouseover(function () { // 如果鼠标移到class为tabStyle的表格的tr上时，执行函数
			$(this).addClass("over");
		}).mouseout(function () { // 给这行添加class值为over，并且当鼠标一出该行时执行函数
				$(this).removeClass("over");
			}); // 移除该行的class
		$("." + option.tableCss + " tr:odd").addClass("alt"); // 给class为tabStyle的表格的偶数行添加class值为alt
	};
	//获得最大页数
	$.fn.getLastPageNum = function () {
		var option = this.data("option");
		var last = 0;
		if((option.total % option.sysParm["jsonBean.pageCount"]) != 0) {
			last = parseInt(option.total / option.sysParm["jsonBean.pageCount"]) + 1;
		} else {
			last = option.total / option.sysParm["jsonBean.pageCount"];
		}
		return last;
	};
	//构造页列表
	$.fn.showOtherPage = function () {
		var option = this.data("option");
		var showString = "";
		var totalPageNum = this.getLastPageNum();
		if(totalPageNum <= 5) {
			for(var i = 1; i <= totalPageNum; i++) {
				showString += "<a onclick=\"$('#" + this.attr("id") + "').toPage(" + i + ")\">" + i + "</a>";
			}
		} else {
			var show = 0;
			if(option.sysParm["jsonBean.pageNum"] - 2 >= -1 && option.sysParm["jsonBean.pageNum"] + 2 <= 5) {
				for(var i = 1; i <= option.sysParm["jsonBean.pageNum"]; i++) {
					show++;
					showString += "<a onclick=\"$('#" + this.attr("id") + "').toPage(" + i + ")\">" + i + "</a>";
				}
				for(var i = 1; i <= (5 - show); i++) {
					showString += "<a onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] + i) + ")\">" + (option.sysParm["jsonBean.pageNum"] + i) + "</a>";
				}
			} else if(totalPageNum <= option.sysParm["jsonBean.pageNum"] + 2 && option.sysParm["jsonBean.pageNum"] > 3) {
				show = totalPageNum - option.sysParm["jsonBean.pageNum"];
				for(var i = (5 - show); i >= 1; i--) {
					showString += "<a onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] - i + 1) + ")\">" + (option.sysParm["jsonBean.pageNum"] - i + 1) + "</a>";
				}
				for(var i = 1; i <= show; i++) {
					showString += "<a onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] + i) + ")\">" + (option.sysParm["jsonBean.pageNum"] + i) + "</a>";
				}
			} else {
				for(var i = 3; i >= 1; i--) {
					showString += "<a onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] - i + 1) + ")\">" + (option.sysParm["jsonBean.pageNum"] - i + 1) + "</a>";
				}
				for(var i = 1; i <= 2; i++) {
					showString += "<a onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] + i) + ")\">" + (option.sysParm["jsonBean.pageNum"] + i) + "</a>";
				}
			}
		}

		return showString;
	};

    /**第二种页数样式*/
    $.fn.showOtherPage2 = function () {
        var option = this.data("option");
        var showString = "";
        var totalPageNum = this.getLastPageNum();
        if(totalPageNum <= 5) {
            for(var i = 1; i <= totalPageNum; i++) {
                showString += "<li onclick=\"$('#" + this.attr("id") + "').toPage(" + i + ")\">" + i + "</li>";
            }
        } else {
            var show = 0;
            if(option.sysParm["jsonBean.pageNum"] - 2 >= -1 && option.sysParm["jsonBean.pageNum"] + 2 <= 5) {
                for(var i = 1; i <= option.sysParm["jsonBean.pageNum"]; i++) {
                    show++;
                    showString += "<li onclick=\"$('#" + this.attr("id") + "').toPage(" + i + ")\">" + i + "</li>";
                }
                for(var i = 1; i <= (5 - show); i++) {
                    showString += "<li onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] + i) + ")\">" + (option.sysParm["jsonBean.pageNum"] + i) + "</li>";
                }
            } else if(totalPageNum <= option.sysParm["jsonBean.pageNum"] + 2 && option.sysParm["jsonBean.pageNum"] > 3) {
                show = totalPageNum - option.sysParm["jsonBean.pageNum"];
                for(var i = (5 - show); i >= 1; i--) {
                    showString += "<li onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] - i + 1) + ")\">" + (option.sysParm["jsonBean.pageNum"] - i + 1) + "</li>";
                }
                for(var i = 1; i <= show; i++) {
                    showString += "<li onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] + i) + ")\">" + (option.sysParm["jsonBean.pageNum"] + i) + "</li>";
                }
            } else {
                for(var i = 3; i >= 1; i--) {
                    showString += "<li onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] - i + 1) + ")\">" + (option.sysParm["jsonBean.pageNum"] - i + 1) + "</li>";
                }
                for(var i = 1; i <= 2; i++) {
                    showString += "<li onclick=\"$('#" + this.attr("id") + "').toPage(" + (option.sysParm["jsonBean.pageNum"] + i) + ")\">" + (option.sysParm["jsonBean.pageNum"] + i) + "</li>";
                }
            }
        }

        return showString;
    };


	//跳转到上一页
	$.fn.Previous = function () {
		var option = this.data("option");
		if(option.sysParm["jsonBean.pageNum"] > 1) {
			return option.sysParm["jsonBean.pageNum"] - 1;
		} else {
			return 1;
		}
	};
	//跳转到下一页
	$.fn.Next = function () {
		var option = this.data("option");
		if(option.sysParm["jsonBean.pageNum"] < this.getLastPageNum()) {
			return option.sysParm["jsonBean.pageNum"] + 1;
		} else {
			return this.getLastPageNum();
		}
	};
	//通过初始化参数显示每页显示条数选择
	$.fn.getPageCount = function () {
		var option = this.data("option");
		var options = "";

		for(var i = 0; i < option.pageCountArray.length; i++) {
			if(option.sysParm["jsonBean.pageCount"] == option.pageCountArray[i]) {
				options += "<option selected='selected' value='" + option.pageCountArray[i] + "'>" + option.pageCountArray[i] + "</option>";
			} else {
				options += "<option value='" + option.pageCountArray[i] + "'>" + option.pageCountArray[i] + "</option>";
			}

		}
		return options;
	};
	//跳转到制定页面
	$.fn.toPage = function (pageNum) {
		var option = this.data("option");
		if(pageNum == option.sysParm["jsonBean.pageNum"]) {
			return;
		}
		option.sysParm["jsonBean.pageNum"] = pageNum;
		if(option.userParm != null) {
			$.extend(option.sysParm, option.userParm);
		}
		this.createdTable();
	};
	//当改变每页显示条数时候刷新到第一页面
	$.fn.changePageCount = function (obj) {
		var option = this.data("option");
		option.sysParm["jsonBean.pageCount"] = obj.value;
		option.sysParm["jsonBean.pageNum"] = 1;
		this.refresh();
	};
	//刷新当前页面数据
	$.fn.refresh = function () {
		var option = this.data("option");
		if(option.userParm != null) {
			$.extend(option.sysParm, option.userParm);
		}
		this.createdTable();
	};
	//通过构造用户参数查询数据
	$.fn.selectDataAfterSetParm = function (userParm) {
		var option = this.data("option");
		if(option) {
			option.sysParm["jsonBean.pageNum"] = 1;
			if(userParm != null) {
				option.userParm = userParm;
				$.extend(option.sysParm, userParm);
				this.createdTable();
			}
		}
	};
/**清除掉指定的参数*/
    $.fn.deleteSpecUserParm = function(userParms){
        var option = this.data("option");
        for(var i in userParms){
            delete option.userParm[userParms[i]];
            delete option.sysParm[userParms[i]];
        }
    }

	$.clickTr = function (obj) {
		$(obj).parent().find("tr").each(function (i) {
			if(i != 0) {
				if($(obj).text() == $(this).text()) {
					if($(this).attr("class") != "" && $(this).attr("class") != undefined && $(this).attr("class") != null && $(this).attr("class").indexOf("clickCss") != -1) {
						$(obj).removeClass("clickCss");
					} else {
						$(obj).addClass("clickCss");
					}
				} else {
					$(this).removeClass("clickCss");
				}
			}
		});

	};

	$.pointToFloatNumber = function (pointVal) {
		return  parseInt(pointVal.replace("%", "")) / 100;
	};

	$.getStringSizeNumber = function (strVal) {
		if(strVal==null){
			return "";
		}
		var valArray = strVal.split("");
		var totalWidth = 0;
		for(var i in valArray) {
			if(valArray[i].match(/[0-9a-zA-z]+$/g) != null) {
				totalWidth += 8;
			} else if(valArray[i].match(/[.,]+$/g) != null) {
				totalWidth += 3;
			} else {
				totalWidth += 15;
			}
		}
		return totalWidth;
	};

	$.getInterceptionReturnString = function (strVal, backStrLength) {
		if(strVal==null){
			return "";
		}
		var valArray = strVal.split("");
		strVal = "";
		var totalWidth = 0;
		for(var i in valArray) {
			if(totalWidth < backStrLength) {
				if(valArray[i].match(/[0-9a-zA-z]+$/g) != null) {
					totalWidth += 8;
				} else if(valArray[i].match(/[.,]+$/g) != null) {
					totalWidth += 3;
				} else {
					totalWidth += 15;
				}
				strVal += valArray[i];
			}
		}
		return strVal.substr(0, strVal.length - 1);
	};

})(jQuery);


function showPageNumList2(parentId){

    $("#pageListUL"+parentId).css("visibility","visible");
    //alert($("#pageListUL").css("visibility"))
    $("body").one("mouseover",function(){
        var e = event || window.event;
        var elem = e.srcElement||e.target;
        if(elem.tagName=='A' || elem.tagName=="UL" || elem.tagName=="LI" ){
            return;
        }else{
            hidePageNumList2(parentId);
        }
    });
}
function hidePageNumList2(parentId){
    $("#pageListUL"+parentId).css("visibility","hidden");
}

function changeMyTablePageCount (id,pageNum) {
    var option = $("#"+id).data("option");
    option.sysParm["jsonBean.pageCount"] = pageNum;
    option.sysParm["jsonBean.pageNum"] = 1;
    $("#"+id).refresh();
};