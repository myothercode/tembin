package com.common.interceptor.filter;

import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxResponse;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2014/12/5.
 * 过滤掉一些特殊字符
 */
public class StringFilter implements Filter {
    private static List<String> excludePaths=new ArrayList<String>();//不进行拦截的url
    static {
        excludePaths.add("http://www.tembin.com");
        excludePaths.add("http://tembin.com");
        excludePaths.add("http://localhost");
        excludePaths.add("http://192.168");
        excludePaths.add("http://localhost");
        excludePaths.add("http://127.0.0.1");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //Enumeration params = servletRequest.getParameterNames();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();

        if(requestUrl!=null && !requestUrl.endsWith(".do")){//如果不是以do结尾的请求，直接放行
            filterChain.doFilter(request, response);
            return;
        }

        //String host = request.getRemoteHost();
        String referer=request.getHeader("Referer");

        if(referer==null || !excludeUrl(referer) ){//如果Referer属性没有在可允许范围内，那么返回登陆框
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }

            Map<String,String[]> m = new HashMap<String,String[]>(request.getParameterMap());
            //m.put("nameoooooooo", new String[]{"newname"});
            request = new ParameterRequestWrapper((HttpServletRequest)request, m);
            //System.out.println("==");

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }




    private boolean excludeUrl(String url) {
        for (String ur : excludePaths){
            if(url.startsWith(ur)){
                return true;
            }
        }
        return false;
    }

}



class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params;

    public ParameterRequestWrapper(HttpServletRequest request,
                                   Map<String, String[]> newParams) {
        super(request);

        this.params = newParams;
        // RequestDispatcher.forward parameter
        renewParameterMap(request);
    }

    @Override
    public String getParameter(String name) {
        String result = "";

        Object v = params.get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                result =  strArr[0];
            } else {
                result = null;
            }
        } else if (v instanceof String) {
            result = (String) v;
        } else {
            result =  v.toString();
        }

        return result;
    }

    @Override
    public Map getParameterMap() {
        return params;
    }

    @Override
    public Enumeration getParameterNames() {
        return new Vector(params.keySet()).elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;

        Object v = params.get(name);
        if (v == null) {
            result =  null;
        } else if (v instanceof String[]) {
            result =  (String[]) v;
        } else if (v instanceof String) {
            result =  new String[] { (String) v };
        } else {
            result =  new String[] { v.toString() };
        }

        return result;
    }

    private void renewParameterMap(HttpServletRequest req) {

        String queryString = req.getQueryString();

        if (queryString != null && queryString.trim().length() > 0) {
            String[] params = queryString.split("&");

            for (int i = 0; i < params.length; i++) {
                int splitIndex = params[i].indexOf("=");
                if (splitIndex == -1) {
                    continue;
                }

                String key = params[i].substring(0, splitIndex);

                if (!this.params.containsKey(key)) {
                    if (splitIndex < params[i].length()) {
                        String value = params[i].substring(splitIndex + 1);
                        this.params.put(key, new String[] { value });
                    }
                }
            }
        }

        if(this.params==null || this.params.size()<=0){return;}
        Map<String, String[]> pp=new HashMap<String, String[]>();
        /**遍历参数map，兵替换掉危险字符*/
        for (Map.Entry<String, String[]> entry: this.params.entrySet()) {
            String key = entry.getKey();
            String[] value1 = entry.getValue();

            String[] stringsValue=new String[value1.length];
            if (value1!=null && value1.length>0){
                for (int i=0;i<value1.length;i++){
                    stringsValue[i]=replaceSpecChar(value1[i]);
                }
            }
            pp.put(key,stringsValue);
        }
        this.params=pp;
    }
    private static String replaceSpecChar(String str) {

        if(StringUtils.isNotEmpty(str)) {
            for (String s : safeless) {
                str=str.replaceAll("(?i)"+s,"");
            }

            /*String lows=str.toLowerCase();;
            for (String s : safeless) {
                if (){

                }
                lows=StringUtils.replaceAll(lows,s,"");
            }
            str=lows;*/
        }
        if ("".equalsIgnoreCase(str)){
            str=null;
        }
        return str;
    }

    private static String[] safeless = {
            "<script",   //需要拦截的JS字符关键字
            "</script",
            "<iframe",
            "</iframe",
            "<frame",
            "</frame",
            "set-cookie",
            "%3cscript",
            "%3c/script",
            "%3ciframe",
            "%3c/iframe",
            "%3cframe",
            "%3c/frame",
            "src=\"javascript:"
           // "<body",
           // "</body",
           // "%3cbody",
           // "%3c/body",
            //"<",
            //">",
            //"</",
            //"/>",
            //"%3c",
            //"%3e",
            //"%3c/",
            //"/%3e"
    };
}
