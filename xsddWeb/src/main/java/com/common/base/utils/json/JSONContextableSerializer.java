/** Created by flym at 13-9-16 */
package com.common.base.utils.json;

import com.alibaba.fastjson.serializer.SerialContext;

/** @author flym */
public class JSONContextableSerializer extends com.alibaba.fastjson.serializer.JSONSerializer {
	public void setContext(SerialContext parent, Object object, Object fieldName) {
		SerialContext context = new SerialContext(parent, object, fieldName);
		setContext(context);

		super.setContext(parent, object, fieldName);
	}


	public void setContext(SerialContext parent, Object object) {
		setContext(new SerialContext(parent, object, null));
		super.setContext(parent, object);
	}
}
