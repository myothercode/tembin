package com.common.base.utils.ajax;

/**
 * ajax返回成功信息结果包装类.<br/>
 *
 *
 */
public class AjaxMessageResult<T> {
	/** 结果返回值,默认为true * */
	private boolean bool = true;
	/** 返回的描述性信息 */
	private String message;
	/** 具体的结果 */
	private T result;

	public AjaxMessageResult() {
	}

	public AjaxMessageResult(boolean bool, String message, T result) {
		this.bool = bool;
		this.message = message;
		this.result = result;
	}


	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
