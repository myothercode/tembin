package com.base.utils.common;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * Created by Administrtor on 2014/8/26.
 */
public class MyStringUtil {

    /**根据日期时间生成一个文件名*/
    public static String generateRandomFilename(){
        String fourRandom = "";
        //产生4位的随机数(不足4位前加零)
        int   randomNum =   (int)(Math.random()*10000);
        fourRandom = randomNum +"";
        int randLength =  fourRandom.length();
        if(randLength <4){
            for(int i=1; i <=4-randLength; i++)
                fourRandom = fourRandom + "0";
        }
        StringBuilder sb = new StringBuilder("");
        sb.append(DateUtils.getFullYear(new Date()))
                .append(twoNumbers(DateUtils.getMonth(new Date())))
                .append(twoNumbers(DateUtils.getDayOfMonth(new Date())))
                .append(twoNumbers(DateUtils.getHour(new Date())))
                .append(twoNumbers(DateUtils.getMinute(new Date())))
                .append(twoNumbers(DateUtils.getSecond(new Date())))
                .append(fourRandom);
        return sb.toString();
    }
    private static String twoNumbers(int number){
        String _number = number + "";
        if(_number.length() < 2){
            _number = "0" + _number;
        }
        return _number;
    }


    /**提取后缀*/
    public static String getExtension(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i >-1) && (i < (filename.length() - 1))) {
                return filename.substring(i );
            }
        }
        return defExt.toLowerCase();
    }
    /**提取文件名，不包括后缀*/
    public static String getFimeNoStuff(String fileName){
        String stuff=getExtension(fileName,"");
        return fileName.replace(stuff,"").toLowerCase();
    }

    /**产生指定规则的随机数包含字母和数字
     * //产生5位长度的随机字符串，中文环境下是乱码
     RandomStringUtils.random(5);
     //使用指定的字符生成5位长度的随机字符串
     RandomStringUtils.random(5, new char[]{'a','b','c','d','e','f', '1', '2', '3'});
     //生成指定长度的字母和数字的随机组合字符串
     RandomStringUtils.randomAlphanumeric(5);
     //生成随机数字字符串
     RandomStringUtils.randomNumeric(5);
     //生成随机[a-z]字符串，包含大小写
     RandomStringUtils.randomAlphabetic(5);
     //生成从ASCII 32到126组成的随机字符串
     RandomStringUtils.randomAscii(4)
     * */
    public static String getRandomStringAndNum(int n){
        String b= RandomStringUtils.randomAlphanumeric(n);
        return b;
    }

    public static void main(String[] args) {
        System.out.println(getRandomStringAndNum(10));

    }

}
