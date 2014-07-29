/** Created by flym at 13-3-21 */
package com.common.base.utils.logUtil;

import org.apache.log4j.spi.LoggingEvent;

/**
 * 使用自定义appender以输出自定义信息到控制台
 *
 *
 */
public class ConsoleAppender extends org.apache.log4j.ConsoleAppender {
	@Override
	protected void subAppend(LoggingEvent event) {
		ErrorInforHandler.handleError(event, qw);
		super.subAppend(event);
	}
}
