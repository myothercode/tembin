package com.common.base.utils.ajax;

import com.base.utils.applicationcontext.RequestResponseContext;
import com.common.base.utils.json.JsonUtils;
import com.google.common.collect.Sets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 将对象序列化成json字符串，利用response将此信息输出到客户端
 *
 */
public class AjaxSupport {
	private static final Log logger = LogFactory.getLog(AjaxSupport.class);

	private static void sendText(Object object, List<String> includePropertyList) {
		String text = JsonUtils.toBrowserJson(object, includePropertyList);
	    AjaxResponse.sendText(RequestResponseContext.getResponse(), text);
	}

	private static void sendText(Object object) {
		sendText(object, null);
	}

	private static boolean isEmpty(String... ss) {
		if(ss == null || ss.length == 0)
			return true;
		for(String s : ss) {
			if(!StringUtils.hasText(s))
				return true;
		}
		return false;
	}

	/** 发送失败信息，返回信息参考为：{bool:false,message:xx,result:xx} */
	public static <T> void sendFailText(String message, T t, String... includeProperties) {
		AjaxMessageResult<T> ajaxMessageResult = new AjaxMessageResult<T>(false, message, t);
		if(isEmpty(includeProperties)) {
			sendText(ajaxMessageResult);
		} else {
			List<String> includePropertyList = composeSortedList(includeProperties);
			sendText(ajaxMessageResult, includePropertyList);
		}
	}

	/** 发送成功信息，返回信息参考为：{bool:true,message:xx,result:xx} */
	public static <T> void sendSuccessText(String message, T t, String... includeProperties) {
		AjaxMessageResult<T> ajaxMessageResult = new AjaxMessageResult<T>(true, message, t);
		if(isEmpty(includeProperties)) {
			sendText(ajaxMessageResult);
		} else {
			List<String> includePropertyList = composeSortedList(includeProperties);
			sendText(ajaxMessageResult, includePropertyList);
		}
	}

	/** 组成成内部私有实现模式 */
	private static List<String> composeSortedList(String... includeProperties) {
		int includePropertiesLength = includeProperties == null ? 0 : includeProperties.length;
		Set<String> resultSet = Sets.newHashSet();
		if(includePropertiesLength == 0) {
			resultSet.add("result.*");
		} else {
			for(String s : includeProperties) {
				if(StringUtils.hasText(s)) {
					resultSet.add("result." + s);
				}
			}
		}
		//add the regular bool and message
		resultSet.add("bool");
		resultSet.add("message");
		List<String> strList = new ArrayList<String>(resultSet);
		Collections.sort(strList);
		return strList;
	}
}
