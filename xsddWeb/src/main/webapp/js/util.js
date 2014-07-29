
//格式化时间
Date.prototype.format = function (format) {
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S": this.getMilliseconds()
	};
	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for(var k in o) {
		if(new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

//获取系统时间
function getSysDate() {
	var date = new Date();
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	if(m < 10) {
		m = "0" + m;
	}
	var d = date.getDate();
	if(d < 10) {
		d = "0" + d;
	}
	return y + "-" + m + "-" + d;
}

//加减乘除
function accAdd(arg1, arg2) {
	var r1, r2, m, c;

	try { r1 = arg1.toString().split(".")[1].length; } catch(e) { r1 = 0; }

	try { r2 = arg2.toString().split(".")[1].length; } catch(e) { r2 = 0; }

	c = Math.abs(r1 - r2);
	m = Math.pow(10, Math.max(r1, r2));
	if(c > 0) {
		var cm = Math.pow(10, c);
		if(r1 > r2) {
			arg1 = Number(arg1.toString().replace(".", ""));
			arg2 = Number(arg2.toString().replace(".", "")) * cm;
		} else {
			arg1 = Number(arg1.toString().replace(".", "")) * cm;
			arg2 = Number(arg2.toString().replace(".", ""));
		}
	} else {
		arg1 = Number(arg1.toString().replace(".", ""));
		arg2 = Number(arg2.toString().replace(".", ""));
	}
	return (arg1 + arg2) / m;
}
function Subtr(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch(e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch(e) {
	}
	with(Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch(e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch(e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

function inputOnlyNUM(e, obj)//添加keypress事件  只允许输入数字
{
	if(obj != null && $(obj).val().length >= 9) {	//长度不能超过9位
		return false;
	}
	var isie = (document.all) ? true : false;//判断是IE内核还是Mozilla 
	var key;
	if(isie) {
		key = window.event.keyCode;//IE使用windows.event事件 
	} else {
		key = e.which;//3个按键函数有一个默认的隐藏变量，这里用e来传递。e.which给出一个索引值给Mo内核（注释1） 
	}
	if(key == 8) {
		return true;
	}
	return /[0-9]/.test(String.fromCharCode(key));
}

function inputNUMAndPoint(e, obj, size)//添加keypress事件  只允许输入数字和size位小数点
{
	if(obj != null && $(obj).val().length >= 12) {	//整数位加小数位的长度不能超过12位
		return false;
	}
	var isie = (document.all) ? true : false;//判断是IE内核还是Mozilla 
	var key;
	if(isie) {
		key = window.event.keyCode;//IE使用windows.event事件 
	} else {
		key = e.which;//3个按键函数有一个默认的隐藏变量，这里用e来传递。e.which给出一个索引值给Mo内核（注释1） 
	}
	if(key == 8) {
		return true;
	}

	var temp = (obj.value + String.fromCharCode(key)).match(/[.]/g);
	if(temp != null) {
		if(temp.length > 1) {
			return false;
		}
		if((obj.value + String.fromCharCode(key)).split(".")[1].length > size) {
			return false;
		}
	}
	return /[0-9.]/.test(String.fromCharCode(key));
}

function inputNoChinese(obj, valid)//valid=false 不允许小数点  valid=true 允许小数点 去除中文
{
	if(valid) {
		return $(obj).val().replace(/([^0-9\.])/g, '');
	} else {
		return $(obj).val().replace(/[^u4E00-u9FA5]/g, '');
	}
}
//重写toFixed四舍五入方法
Number.prototype.toFixed = function (d) {
	var s = this + "";
	if(!d)d = 0;
	if(s.indexOf(".") == -1)s += ".";
	s += new Array(d + 1).join("0");
	if(new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$").test(s)) {
		var s = "0" + RegExp.$2, pm = RegExp.$1, a = RegExp.$3.length, b = true;
		if(a == d + 2) {
			a = s.match(/\d/g);
			if(parseInt(a[a.length - 1]) > 4) {
				for(var i = a.length - 2; i >= 0; i--) {
					a[i] = parseInt(a[i]) + 1;
					if(a[i] == 10) {
						a[i] = 0;
						b = i != 1;
					} else break;
				}
			}
			s = a.join("").replace(new RegExp("(\\d+)(\\d{" + d + "})\\d$"), "$1.$2");
		}
		if(b)s = s.substr(1);
		return (pm + s).replace(/\.$/, "");
	}
	return this + "";
};

//将字符串转为ajax param,并处理其中的转义字符
function _ajaxParam(str) {
	if(!(typeof str == "string"))
		return str;
	var p = {};
	var ss = str.split("&");
	var ssLength = ss.length;
	for(var i = 0; i < ssLength; i++) {
		var s = ss[i];
		if(!s.length)
			continue;
		var kv = s.split("=");
		if(kv.length != 2)
			continue;
		var key = kv[0];
		key = key && $.trim(key);
		var value = kv[1];
		value = value && $.trim(value);
		if(!key)
			continue;

		var sv = p[key];
		if(sv) {
			if(sv.constructor === Array) {//数组
				sv.push(value);
			} else {
				sv = [sv, value];
				p[key] = sv;
			}
		} else {
			p[key] = value;
		}
	}

	return p;
}

//处理数字输入框，如果输入有错，则直接转换为0
function processNumberInput(input) {
	input = $(input);
	var v = input.val();
	if(!v) {
		input.val(0);
		return;
	}
	v = parseFloat(v);
	isNaN(v) && input.val(0);
}





//对指定区域内的分页取相应参数
function pageParam(div) {
	div = div.hasClass("page") ? div : div.find("div.page");
	if(!div.length)
		return {};
	return {"page.pageSize": div.attr("pageSize"), "page.currentPage": div.attr("currentPage")};
}

//全局事件绑定注册
$(function () {
    var document$ = $(document);

    //时间控件
    document$.on("focus", "input.date", function () {
        window["WdatePicker"] && (window["WdatePicker"]());
    });

    //分页链接
    document$.on("click", "a[page='true']", function () {
        if(window["clickPage"]) {
            var self = $(this);
            var pageDiv = $(self.parents("div.page")[0]);
            var currentPage = self.attr("currentPage");
            if(currentPage == 0)
                return;
            var pageSize = pageDiv.attr("pageSize");
            window["clickPage"](pageSize, currentPage, this);
        }
    });
});


