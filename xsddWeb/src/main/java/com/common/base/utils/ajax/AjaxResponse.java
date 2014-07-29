package com.common.base.utils.ajax;

import com.base.utils.applicationcontext.RequestResponseContext;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by wula on 2014/7/6.
 */
public class AjaxResponse {
    public static void sendText(HttpServletResponse response, String responseText) {
        sendText(response, "text/html", responseText);
    }

    public static void sendText(HttpServletResponse response, String contentType, String responseText) {
        if(response==null){
            response=RequestResponseContext.getResponse();}
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(responseText);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch(Exception ex) {
                    //nothing
                }
            }
        }
    }
}
