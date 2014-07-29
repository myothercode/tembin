/** Created by flym at 13-3-21 */
package com.common.base.utils.logUtil;

import com.google.common.base.Joiner;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.QuietWriter;
import org.apache.log4j.spi.LoggingEvent;

//import javax.servlet.http.HttpServletRequest;


/**
 * 错误信息处理器,以在出现logger.error时输出自定义信息
 *
 *
 */
public class ErrorInforHandler {
	private static Joiner joiner = Joiner.on(",");

	public static void handleError(LoggingEvent event, QuietWriter qw) {
        if(event.getLevel().isGreaterOrEqual(org.apache.log4j.Level.ERROR)){
            qw.write("===QuietWriter====");
            qw.write(Layout.LINE_SEP);
        }


		/*//如果为error信息时,这里将显示相应的操作信息,包括操作员,请求地址,所有前台参数,以及浏览器信息
		if(event.getLevel().isGreaterOrEqual(org.apache.log4j.Level.ERROR)) {
			SessionVO sessionVO = SessionVOHolder.getSessionVO();
			qw.write(Layout.LINE_SEP);
			qw.write("以下为出错的相关上下文信息------------------------");
			qw.write(Layout.LINE_SEP);

			if(sessionVO != null) {
				//医疗机构,显示名称,以方便查找
				qw.write("医疗机构:" + sessionVO.getJgmc());
				qw.write(Layout.LINE_SEP);
				//操作员
				qw.write("操作员:" + sessionVO.getSfzjh());
				qw.write(Layout.LINE_SEP);
			}
			if(ServletActionContext.getContext() != null) {
				HttpServletRequest request = ServletActionContext.getRequest();
				if(request != null) {
					//请求地址:
					qw.write("请求地址:" + request.getRequestURI());
					qw.write(Layout.LINE_SEP);
					//浏览器信息
					qw.write("使用浏览器:" + request.getHeader("User-Agent"));
					qw.write(Layout.LINE_SEP);

					//参数信息
					Map<String, String[]> params = request.getParameterMap();
					if(!params.isEmpty()) {
						qw.write("----参数信息----start");
						qw.write(Layout.LINE_SEP);

						for(Map.Entry<String, String[]> e : params.entrySet()) {
							qw.write(e.getKey() + " -> " + joiner.join(e.getValue()));
							qw.write(Layout.LINE_SEP);
						}
						qw.write("----参数信息----end");
					}
				}
			}
			qw.write("以上为出错的相关上下文信息-------------------------");
			qw.write(Layout.LINE_SEP);
		}*/
	}
}
