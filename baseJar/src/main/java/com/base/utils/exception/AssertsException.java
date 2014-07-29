/** Created by flym at 12-12-12 */
package com.base.utils.exception;

/**
 * 业务断言异常,表示不能满足业务上的要求
 *
 * @author flym
 */
public class AssertsException extends RuntimeException {
	/** 详细的业务要求错误信息 */
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public AssertsException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public AssertsException(String errorMessage, String message) {
		super(message);
		this.errorMessage = errorMessage;
	}

	public AssertsException(String errorMessage, String message, Throwable cause) {
		super(message, cause);
		this.errorMessage = errorMessage;
	}

	public AssertsException(String errorMessage, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
	}
}
