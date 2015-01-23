
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

/**判断是否是日期时间格式返回值为itime,idate,idatetime,none,date*/
function strIsDateOrTime(str){
    if(str==null || str=='' || str=='null'){return "none";}
    if($.type(str)=='date'){return "date"}
    if($.type(str)!='string'){return "none"}

    //首先判断是否是完整的日期时间
    var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
    var r = str.match(reg);
    if(r==null){}else{
    var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);
    if (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]){
        return "idatetime";
    }};

    /**第二判断是否是日期格式*/
    r=null;
    d=null;
    r=str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if(r==null){}else{
        d= new Date(r[1], r[3]-1, r[4]);
        if((d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4])){
            return "idate";
        }
    }
    /**判断是否是时间格式*/
    r=null;
    d=null;
    r=str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
    if(r==null){}else{
        if (r[1]>24 || r[3]>60 || r[4]>60){return "none";}else{
            return "itime";
        }
    }
    return "none";
}

/**时间相减返回秒数如果ms不为空，那么返回毫秒*/
function ComputationSecond(oDate1,oDate2,ms){
	if(oDate2==null){return 10}
	var date3 = parseInt(Math.abs(oDate1 - oDate2));   //时间差的毫秒数
	//计算相差的年数
	var years = Math.floor(date3 / (12 * 30 * 24 * 3600 * 1000));
	//计算相差的月数
	var leave = date3 % (12 * 30 * 24 * 3600 * 1000);
	var months = Math.floor(leave / (30 * 24 * 3600 * 1000));
	//计算出相差天数
	var leave0 = leave % (30 * 24 * 3600 * 1000);
	var days = Math.floor(leave0 / (24 * 3600 * 1000));
	//计算出小时数
	var leave1 = leave0 % (24 * 3600 * 1000);     //计算天数后剩余的毫秒数
	var hours = Math.floor(leave1 / (3600 * 1000));
	//计算相差分钟数
	var leave2 = leave1 % (3600 * 1000);         //计算小时数后剩余的毫秒数
	var minutes = Math.floor(leave2 / (60 * 1000));
	//计算相差秒数
	var leave3 = leave2 % (60 * 1000);       //计算分钟数后剩余的毫秒数

	if(ms!=null){
		return leave3;
	}

	var seconds = Math.round(leave3 / 1000);
	return seconds;
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
		/*if((obj.value + String.fromCharCode(key)).split(".")[1].length > size) {
			return false;
		}*/
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





/**
 * Map类
 * 实现了类似于Java语言中的Map接口的常用方法
 */
function Map(){

    //key集
    this.keys = new Array();

    //value集
    this.values = new Array();

    //添加key-value进Map
    this.put = function(key, value){
        if(key == null || key == undefined)
            return;
        var length = this.size();
        for(var i = 0 ; i < length ; i ++ ) {
            //如果keys数组中有相同的记录，则不保存重复记录
            if(this.keys[i] == key)
                return;
        }
        this.keys.push(key);
        this.values.push(value);
    };

    //获取指定key的value
    this.get = function(key){
        var length = this.size();
        for(var i = 0 ; i < length ; i ++ ) {
            if(this.keys[i] == key) {
                return this.values[i];
            } else {
                continue;
            }
            return null;
        }
    };

    //移除指定key所对应的map
    this.remove = function(key) {
        var length = this.size();
        for(var i = 0 ; i < length ; i ++ ) {
            if(this.keys[i] == key) {
                while(i < length - 1) {
                    this.keys[i] = this.keys[i+1];
                    this.values[i] = this.values[i+1];
                    i ++ ;
                }
                //处理最后一个元素
                this.keys.pop();
                this.values.pop();
                break;
            }
        }
    };

    //是否包含指定的key
    this.containsKey = function(key) {
        var length = this.size();
        for(var i = 0 ; i < length ; i ++ ) {
            if(this.keys[i] == key) {
                return true;
            }
        }
        return false;
    };

    //是否包含指定的value
    this.containsValue = function(value) {
        var length = this.size();
        for(var i = 0 ; i < length ; i ++ ) {
            if(this.values[i] == value) {
                return true;
            }
        }
        return false;
    };

    //包含记录总数
    this.size = function() {
        return this.keys.length;
    };

    //是否为空
    this.isEmpty = function() {
        return this.size() == 0 ? true : false;
    };

    //清空Map
    this.clear = function() {
        this.keys = new Array();
        this.values = new Array();
    };

    //将map转成json字符串，格式：{"key1":"value","key2":"value2"}
    this.toString = function() {
        var length = this.size();
        var str = "{";
        for(var i = 0 ; i < length ; i ++ ) {
            str = str +"\""+ this.keys[i] + "\":\"" + this.values[i]+"\"";
            if(i != length-1)
                str += ",";
        }
        str +="}";
        return str;
    };
}


/**数组去重*/
function arrDistinct(ar){
    var m,n=[],o= {};
    for (var i=0;(m= ar[i])!==undefined;i++)
        if (!o[m]){n.push(m);o[m]=true;}
    return n.sort(function(a,b){return a-b});;

}

/**去掉字符串中的特殊符号*/
function replaceTSFH(_text){
    //var c=b.replace(/[&\|\\\*^%$#@\-]/g,"");去掉指定符号
    //return this.replace(//s/g, '');去掉空格
    return _text.replace(/[^\u4e00-\u9fa5\w]/ig,"");
}

/**得到字符串中的字母*/
function strGetNotNum(text){
    return text.replace(/[^a-zA-Z]/ig,"");
}

/**得到字符串中的数字*/
function strGetNum(text){
    return text.replace(/[^0-9]/ig,"");
}

/**截取字符串后几位*/
function subRight(str,i){
    if(str==null || i==0){
        return "";
    }
    return str.slice(str.length - i,str.length)
}

/**截取指定中括号之间的字符串，如 [ggg] */
function getStrBetweenTwoChar(str){
    return str.match(/\[[^\]]+\]/gi);
}
/**获取字符串中的大写字母*/
function getUpperChar(str){
    return str.replace(/[^A-Z]/g,"");
}

/**将指定元素内的指定name开头属性进行重新整合，如abcd[0]，将进行重新编号
 * objid上一级的id
 * nameStart名字是以什么开头的
 * */
function domReIndex(objid,nameStart){
    $('#'+objid).find("[name^="+nameStart+"]").each(function(i,d){
        var name1=$(d).attr("name");//对象原来的名字
        //alert($(d.parentNode.parentNode).css("left"))
        var replaceStr=getStrBetweenTwoChar(name1);//需要替换的字符串
        $(d).attr("name",name1.replace(replaceStr,"["+i+"]"));
    });
}


/**解析URL  var parse = parse_url(url1)  parse['pageNum'];  */
function parse_url(url){ //定义函数
    var pattern = /(\w+)=(\w+)/ig;//定义正则表达式
    var parames = {};//定义数组
    url.replace(pattern, function(a, b, c){parames[b] = c;});
    return parames;//返回这个数组.

}

/*json对象转为字符串*/
function jsonToString (obj){
    var THIS = this;
    switch(typeof(obj)){
        case 'string':
            return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
        case 'array':
            return '[' + obj.map(THIS.jsonToString).join(',') + ']';
        case 'object':
            if(obj instanceof Array){
                var strArr = [];
                var len = obj.length;
                for(var i=0; i<len; i++){
                    strArr.push(THIS.jsonToString(obj[i]));
                }
                return '[' + strArr.join(',') + ']';
            }else if(obj==null){
                return 'null';

            }else{
                var string = [];
                for (var property in obj) string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj

                    [property]));
                return '{' + string.join(',') + '}';
            }
        case 'number':
            return obj;
        case false:
            return obj;
    }
}


/**获取指定位数的随机数*/
function generateMixedRandom(n) {
    var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}

/**一个查看页面的遮罩层*/
function converDiv_(){
	var div="<div id=\"pop1\" style='text-align:center;align-content:center;left: 0px; top: 0px; width: 100%; height: 100%; overflow: hidden; opacity:0.6;filter:  alpha(opacity=0); position: fixed; z-index: 999; zoom: 1; background-image: none; background-attachment: scroll; background-repeat: repeat; background-position-x: 0%; background-position-y: 0%; background-color: #666666' >" +
        "<img src='"+path+"/img/longLoading.gif' style='padding-top: 20%;'/></div>";
	$('body').append(div);
}


/**获取最顶层window*/
function getTopWin_(){
	var obj=window.self;

	if(self.location==top.location){
		return obj;
	}

	while(true)
	{
		if(obj.document.getElementById("contentMaindiv"))
		{
			return obj;
		}
		obj=obj.window.parent;
	}
}

/**一个替代alert的弹出提示框*/
function myAlert(cont){
	var topWin=getTopWin_();

	var isFirstOpen=topWin.diagTempPar_;
	$("body").queue(function(){
		try{openMyAlert(cont)}catch (e){oldAlert(cont);console.log(e);}
	});
	if(isFirstOpen==0 || isFirstOpen==null){
		$("body").dequeue();
	}

}
function openMyAlert(cont){
	var topWin=getTopWin_();
	topWin.diagTempPar_=topWin.$.dialog({title: '',
		//id: 'myAlert2',
		content: '<table style="width: 500px;height: 100px;font-weight: bold;text-align: center;font-size: 14px;letter-spacing: 4px">' +
		'<tr><td>'+cont+'</td></tr>' +
		'</table>',
		max: false,
		min: false,
		//parent:W,
		//time:5,
		cancel: true,
		cancelVal: '关闭',
		width:500,
		zIndex:999999,
		height:100,
		init: function () {
			/*var that = this, i = 30;
			var fn = function () {
				that.title(i + '秒后自动关闭');
				try {
					!i && that.close();
				} catch (e) {
					oldAlert(cont);
					console.log(e);
					//return;
				}
				i --;
			};
			topWin.timer = topWin.setInterval(fn, 1000);
			fn();*/
		},
		close: function () {
			//topWin.clearInterval(topWin.timer);
			//oldAlert($("body").queue().length)
			if($("body").queue().length==1){
				topWin.diagTempPar_=0;
			}else{
				$("body").dequeue();
			}

		},
		lock:true
	});
}
/**
 * 大图片地址处理在小图片地址
 * @param url
 */
function chuLiPotoUrl(url){
    if(url.indexOf("img.tembin.com")>0){
        if(url!=null&&url!=""){
            url = url.substr(0,url.lastIndexOf("."))+"_small"+url.substr(url.lastIndexOf("."));
        }
    }
    return url;
}
String.prototype.startWith=function(str){
	var reg=new RegExp("^"+str);
	return reg.test(this);
};

String.prototype.endWith=function(str){
	var reg=new RegExp(str+"$");
	return reg.test(this);
};

/**
 * 得到站点URl
 * @param dataType
 * @param site
 * @returns {*}
 */
function getSiteUrl(dataType,site){
    if(localStorage.getItem("siteUrlStr")==null||localStorage.getItem("siteUrlStr")==""){
        var url = path + "/ajax/getSiteUrl.do";
        $().delayInvoke(url, {},
            [function (m, r) {
                localStorage.setItem("siteUrlStr", r.siteUrlStr);
                localStorage.setItem("siteStrList_local", r.siteListStr);
                var siteUrlStr = localStorage.getItem("siteUrlStr");
                var siteListStr = localStorage.getItem("siteStrList_local");
                var jsonUrlStr = eval("(" + siteUrlStr + ")");
                var jsonSiteList = eval("(" + siteListStr + ")");
                if(dataType=="1"){
                    return jsonUrlStr["URL"+site];
                }else if(dataType=="2"){
                    for(var i=0;i<jsonSiteList.length;i++){
                        if(site==jsonSiteList[i].id){
                            return jsonUrlStr["URL"+jsonSiteList[i].name1];
                        }
                    }
                }else if(dataType=="3"){
                    for(var i=0;i<jsonSiteList.length;i++){
                        if(site==jsonSiteList[i].value){
                            return jsonUrlStr["URL"+jsonSiteList[i].name1];
                        }
                    }
                }
            },
                function (m, r) {
                    alert(r);
                }]
        );
    }else{
        var siteUrlStr = localStorage.getItem("siteUrlStr");
        var siteListStr = localStorage.getItem("siteStrList_local");
        var jsonUrlStr = eval("(" + siteUrlStr + ")");
        var jsonSiteList = eval("(" + siteListStr + ")");
        if(dataType=="1"){
            return jsonUrlStr["URL"+site];
        }else if(dataType=="2"){
            for(var i=0;i<jsonSiteList.length;i++){
                if(site==jsonSiteList[i].id){
                    return jsonUrlStr["URL"+jsonSiteList[i].name1];
                }
            }
        }else if(dataType=="3"){
            for(var i=0;i<jsonSiteList.length;i++){
                if(site==jsonSiteList[i].value){
                    return jsonUrlStr["URL"+jsonSiteList[i].name1];
                }
            }
        }
    }
}






