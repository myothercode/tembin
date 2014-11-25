<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>下拉select选择框</title>
    <meta. http-equiv="content-type" content="text/html;charset=gb2312">
        <style. type="text/css">
            body{
            margin:20px auto;
            font-family:Arial,Helvetica,sans-serif;
            font-size:12px;width:950px;
            height:400px;
            border:solid 1px #aaa;
            position:relative;
            padding:10px;
            }
            h1{font-size:12px;color:#444;}
            ul{margin:0;padding:0;list-style.:none;} .dropDownList{position:absolute;left:100px;top:100px;}
            .dropDownList div.dropdown{float:left;margin-right:120px;} .dropDownList
            span{display:block;width:146px;height:26px;background:url(/upload/2010-3/20100303231959754.gif) left top
            no-repeat;line-height:26px;text-indent:12px;border:solid 1px #83BBD9;cursor:default;} .dropDownList
            span.over{background-position:left bottom;border-color:#B4C91A;} .dropDownList
            ul{width:200px;display:none;position:absolute;} .dropDownList ul
            li{background:#eee;height:20px;width:100%;padding:3px 0;text-indent:12px;cursor:default;line-height:20px;}
            /*普通状态下的样式*/ .dropDownList ul li.normal{background:#eee;} /*鼠标移上的样式*/ .dropDownList ul
            li.over{background:#ccc;} /*被选中的样式*/ .dropDownList ul li.current{background:#c2c2c2;font-weight:bold;}
            .dropDownList ul.show{display:block;}
        </style.>
</head>
<b>
    <form. action="#" method="get"></p>
        <p>    <select name="birthday">
            <option>请选择</option>
            <option>1986</option>
            <option>1987</option>
            <option selected="selected">1988</option>
            <option>1989</option>
            <option>1990</option>
        </select><span>请选择年份</span>
        <ul></ul>
        </p>
        <p>    <select name="sex">
            <option>性别</option>
            <option>男</option>
            <option selected="selected">女</option>
            <option>不是男，也不是女</option>
        </select><span>请选择性别</span>
        <ul></ul>
        </p>
        <p>    <select name="student">
            <option>幼儿班</option>
            <option>小学</option>
            <option selected="selected">初中</option>
            <option>高中</option>
        </select><span>请选择学历</span>
        <ul></ul>
        </p></form.>
    </body></html>