package org.lynxz.custom;

import org.lynxz.customviews.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @function 设置字体格式,实现跑马灯效果
 * @author <a href="http://blog.csdn.net/zxz_tsgx">Lynxz</a>
 * @date Apr 4, 2015
 */
public class CustomTextView extends TextView {
	private Typeface mTf;// 自定义字体
	private String mCustomFontPath;// 字体文件所在Assert目录下的路径
	private boolean mIfMarquee;// 是否设置为跑马灯效果

	public CustomTextView(Context context) {
		super(context);
		init(null, 0);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	/**
	 * 获取自定义属性:
	 *  ctv_assert_font_path 字体文件在assert目录中的相对路径
	 *  ctv_marquen boolean,是否开启跑马灯效果
	 */
	private void init(AttributeSet attrs, int defStyle) {
		final TypedArray ta = getContext().obtainStyledAttributes(attrs,
				R.styleable.CustomTextView, defStyle, 0);
		mCustomFontPath = ta
				.getString(R.styleable.CustomTextView_ctv_assert_font_path);
		mIfMarquee = ta.getBoolean(R.styleable.CustomTextView_ctv_marquen,
				false);
		setTypeFaceFromAssert(mCustomFontPath);
		if (mIfMarquee) {// 保留原TextView xml中属性ellipse = marqueen等属性可继续发挥作用
			setAutoMarquee(mIfMarquee);
		}
		ta.recycle();
	}

	/**
	 * @function 设置自定义字体,注意大小写
	 * @date Apr 4, 2015
	 * @param fontPath 字体文件所在assert下的路径,如"font/***.TTF"
	 */
	public void setTypeFaceFromAssert(String fontPath) {
		if (fontPath == null || "".equals(fontPath)) {
			return;
		}

		try {
			mCustomFontPath = fontPath;
			mTf = Typeface.createFromAsset(getContext().getAssets(), fontPath);
			setTypeface(mTf);
		} catch (Exception e) {// 字体文件不存在
			Commonfunctions.log("Exception: " + e.getMessage());
		}
	}

	/**
	 * @function 当文本过长时自动滚动
	 * @date Apr 4, 2015
	 */
	public void setAutoMarquee(boolean ifMarquee) {
		mIfMarquee = ifMarquee;
		if (ifMarquee) {
			requestFocus();
			setSingleLine(true);
			setEllipsize(TruncateAt.MARQUEE);
			setMarqueeRepeatLimit(-1);
		} else {
			setSingleLine();
			setEllipsize(TruncateAt.END);
			setMarqueeRepeatLimit(0);
		}
	}

	@Override
	public boolean isFocused() {
		// 保证marquee效果可以实现,需要始终获取焦点
		return true;
	}

}
