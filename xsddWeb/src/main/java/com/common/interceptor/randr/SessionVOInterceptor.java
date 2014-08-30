package com.common.interceptor.randr;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.common.base.utils.ajax.AjaxSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/21.
 * session过滤器未登录的返回到登陆页面
 * ajax请求则返回错误信息
 */
public class SessionVOInterceptor extends HandlerInterceptorAdapter {
    private static final Log logger = LogFactory.getLog(SessionVOInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        String currURL=request.getRequestURI();
        /**判断是否是登录页面*/
        if (currURL.endsWith("login.do") || currURL.endsWith("getCountSize.do")){
            return true;
        }
        /**判断是否有登录信息*/
        HttpSession session = request.getSession();
        if (session==null || ObjectUtils.isLogicalNull(session.getAttribute(SessionCacheSupport.USERLOGINID))){
            redirectLogin(request,response);
            return false;
        }
        String loginID= (String) session.getAttribute(SessionCacheSupport.USERLOGINID);
        if (StringUtils.isEmpty(loginID)){//判断是否有登录成功标记
            redirectLogin(request,response);
            return false;
        }
        SessionVO sessionVO = SessionCacheSupport.get(loginID);
        //如果缓存里面没有登录信息或者sessionid不同
        if(sessionVO==null
                || StringUtils.isEmpty(sessionVO.getLoginId())
                ||StringUtils.isEmpty(sessionVO.getSessionID())
                || !sessionVO.getSessionID().equalsIgnoreCase(request.getSession().getId())){
            if(sessionVO!=null){
                logger.error(sessionVO.getLoginId()+"session里面有,缓存里面没有登录信息，重新登录");
            }

            session.removeAttribute(SessionCacheSupport.USERLOGINID);
            session.invalidate();
            redirectLogin(request, response);
            return false;
        }
       return true;
    }


private void redirectLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
    Map<String, String[]> map =request.getParameterMap();
    List<String> AjaxMode = getParmByName(map, "AjaxMode", null);
    if(!ObjectUtils.isLogicalNull(AjaxMode)){
        AjaxSupport.sendFailText("sessionStatusFalse","未登录或者已过期");
        return;
    }
    response.sendRedirect(request.getContextPath()+"/login.jsp");
}

    /**在参数request的参数map内，查找指定条件的的参数*/
    private List<String> getParmByName(Map<String, String[]> map,String startWith,String endWith){
        List<String> stringList = new ArrayList<String>();
        if (map==null || map.isEmpty() || StringUtils.isEmpty(startWith)) {
            return stringList;
        }
        if (StringUtils.isNotEmpty(endWith)){
            for(Map.Entry _o : map.entrySet()){
                String s=_o.getKey().toString();
                if (s.startsWith(startWith) && s.endsWith(endWith)){
                    String _v =  ((String[])_o.getValue())[0];
                    if (_v!=null && !"".equals(_v)){
                        stringList.add(_v);
                    }

                }
            }
        }else {
            String[] strings = (String[])map.get(startWith);
            if (strings!=null && strings.length>0){
                String _v =  strings[0];
                if (_v!=null && !"".equals(_v)){
                    stringList.add((String)_v);
                }
            }

        }
        return stringList;
    }



}
