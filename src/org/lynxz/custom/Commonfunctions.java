package org.lynxz.custom;

import android.util.Log;

/**
 * @function 自定义一些常用功能,如日志输出,获取手机信息等.
 * @author Lynxz
 * @date Apr 4, 2015
 */
public class Commonfunctions {

	private static StackTraceElement mTraceElement = null;

	public static StackTraceElement getTraceElement() {

		if (mTraceElement == null) {
			mTraceElement = ((new Exception()).getStackTrace())[1];
		}
		return mTraceElement;
	}

	/**
	 * @function 输出指定格式的log error级别的日志,格式形如: [ xxx.java - L100 - init() ] -- sth output.
	 * @date Apr 4, 2015
	 * @param tag 检索关键字
	 * @param content 要输出的信息
	 */
	public static void log(String tag, String content) {
		getTraceElement();
		StringBuffer sb = new StringBuffer("[ ")
				.append(mTraceElement.getFileName()).append(" - ")
				.append(mTraceElement.getLineNumber()).append(" - ")
				.append(mTraceElement.getMethodName()).append("()")
				.append(" ]");
		Log.e(tag, sb.toString() + " -- " + content);
	}

	/**
	 * @function 输出指定格式的log error级别的日志,默认tag为当前类名.
	 * <br>参见: {@link org.lynxz.custom.Commonfunctions#log(String tag, String content) log(tag,content)}
	 * @date Apr 4, 2015
	 * @param content 要输出的自定义信息
	 */
	public static void log(String content) {
		getTraceElement();
		log(mTraceElement.getFileName());
	}
}
