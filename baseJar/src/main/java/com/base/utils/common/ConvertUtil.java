package com.base.utils.common;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrtor on 2014/7/22.
 * 数据类型转换工具
 */
public class ConvertUtil {
    private static Table<Object,Object,Integer> table= HashBasedTable.create();
    static{
        table.put(Boolean.class,String.class,1);//字符串
        table.put(String.class,Boolean.class,2);//布尔型转为字符串型
        table.put(Integer.class,Long.class,3);
        table.put(Long.class,Integer.class,4);
        table.put(int.class,Integer.class,5);
        table.put(Integer.class,int.class,5);
        table.put(long.class,Long.class,5);
        table.put(Long.class,long.class,5);
        table.put(boolean.class,Boolean.class,5);
        table.put(Boolean.class,boolean.class,5);
        table.put(float.class,Float.class,5);
        table.put(Float.class,float.class,5);
        /*table.put(int.class,Integer.class,5);
        table.put(Integer.class,int.class,6);
        table.put(long.class,Long.class,7);
        table.put(Long.class,long.class,8);
        table.put(boolean.class,Boolean.class,9);
        table.put(Boolean.class,boolean.class,10);*/
    }

    /**
     * 转换
     * @param t  目标类型
     * @param s 原类型
     * @return
     */
    public static <S> Object convert(Class t,S s){
        if(s.getClass().equals(t)){
              return s;
        }
        Integer i = table.get(t,s.getClass());
        if(i==null){return null;}
        switch (i){
            case 2:
                return  ("true".equalsIgnoreCase(((Boolean)s).toString())?"1":"0");
            case 1:
                return "0".equals(s)?false:true;
            case 3:
                return Integer.parseInt(((Long)s).toString());
            case 4:
                return Long.parseLong(((Integer)s).toString());
            case 5:
                return s;
            /*case 6:
                return (Integer) s;
            case 7:
                return ((Long)s).longValue();
            case 8:
                return (Long)s;
            case 9:
                return ((Boolean)s).booleanValue();
            case 10:
                return (Boolean)s;*/
            default:return null;

        }
    }


    /***/
    /**
     * 将InputStream转换成byte数组
     * @param in InputStream
     * @return byte[]
     * @throws java.io.IOException
     */
    public static byte[] InputStreamTOByte(InputStream in) throws IOException {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[512];
        int count = -1;
        in.mark(1);
        while((count = in.read(data,0,512)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return outStream.toByteArray();
    }

    /**
     * 将byte数组转换成InputStream
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream byteTOInputStream(byte[] in) throws Exception{
        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }
    /**
     * 将InputStream转换成某种字符编码的String
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String InputStreamTOString(InputStream in,String encoding) throws Exception{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[512];
        int count = -1;
        while((count = in.read(data,0,512)) != -1)
            outStream.write(data, 0, count);

        data = null;
        if(StringUtils.isEmpty(encoding)){encoding="ISO-8859-1";}
        return new String(outStream.toByteArray(),encoding);
    }
    /**
     * 将String转换成InputStream
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream StringTOInputStream(String in) throws Exception{

        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("ISO-8859-1"));
        return is;
    }

    /**
     * 将byte数组转换成String
     * @param in
     * @return
     * @throws Exception
     */
    public static String byteTOString(byte[] in) throws Exception{
        InputStream is = byteTOInputStream(in);
        return InputStreamTOString(is,null);
    }


}
