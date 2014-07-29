/** Created by flym at 12-12-12 */
package com.base.utils.exception;


/**
 * 业务断言,满足业务条件的方可继续进行后续业务
 *
 * @author flym
 */
public class Asserts {
	public static void assertTrue(boolean condition, String errorMessage) {
		if(!condition) {
			throw new AssertsException(errorMessage);
		}
	}
}
