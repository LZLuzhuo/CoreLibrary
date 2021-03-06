/* Copyright 2020 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_core.data;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;

/**
 * Description: 字符串工具
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:44
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class StringManager {

	/**
	 * 是否含有中文字符 (不检测标点符号)
	 * @param str 字符串
	 * @return true含有, false不含有
	 */
	public static boolean isContainChinese(String str) {
		if (TextUtils.isEmpty(str)) return false;

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) return true;
        return false;
    }

	public static class Text {
		private String text;
		private List<Pair<String, Float>> scales = new ArrayList<>();
		private List<Pair<String, Integer>> sizes = new ArrayList<>();
		private List<Pair<String, Integer>> colors = new ArrayList<>();
		private List<Pair<String, Integer>> backgrounds = new ArrayList<>();
		private List<String> suppers = new ArrayList<>();
		private List<String> subs = new ArrayList<>();
		private List<Pair<String, Integer>> images = new ArrayList<>();
		private List<String> underLines = new ArrayList<>();
		private List<String> deleteLines = new ArrayList<>();
		private List<Pair<String, OnClickListener>> callbacks = new ArrayList<>();
		private List<Pair<String, String>> urls = new ArrayList<>();

		public Text(String text) {
			this.text = text;
		}

		public StringManager.Text color(String text, int color) {
			colors.add(new Pair<>(text, color));
			return this;
		}

		public StringManager.Text background(String text, int background) {
			backgrounds.add(new Pair<>(text, background));
			return this;
		}

		/**
		 * 字体的相对大小
		 * @param scale 放大比例
		 */
		public StringManager.Text scale(String text, float scale) {
			scales.add(new Pair<>(text, scale));
			return this;
		}

		/**
		 * 字体的绝对大小
		 */
		public StringManager.Text size(String text, int sp) {
			sizes.add(new Pair<>(text, sp));
			return this;
		}

		/**
		 * 上标
		 */
		public StringManager.Text supper(String text) {
			suppers.add(text);
			return this;
		}

		/**
		 * 下标
		 */
		public StringManager.Text sub(String text) {
			subs.add(text);
			return this;
		}

		/**
		 * 图片
		 * 根据图片的实际大小添加到内容中
		 */
		public StringManager.Text image(String text, int image) {
			images.add(new Pair<>(text, image));
			return this;
		}

		/**
		 * 下划线
		 */
		public StringManager.Text underLine(String text) {
			underLines.add(text);
			return this;
		}

		/**
		 * 删除线
		 */
		public StringManager.Text deleteLine(String text) {
			deleteLines.add(text);
			return this;
		}

		/**
		 * 点击事件回调
		 * 需要调用 {@link #setTextClickable(TextView)} } 才能实现回调事件
		 */
		public StringManager.Text click(String text, OnClickListener callback) {
			callbacks.add(new Pair<>(text, callback));
			return this;
		}

		/**
		 * URL路径跳转
		 */
		public StringManager.Text url(String text, String url) {
			urls.add(new Pair<>(text, url));
			return this;
		}

		public SpannableString build() {
			final SpannableString ss = new SpannableString(text);
			int startIndex;
			int textLength;

			// color
			for (Pair<String, Integer> color : colors) {
				startIndex = text.indexOf(color.first);
				if (startIndex == -1) continue;
				textLength = startIndex + color.first.length();
				ss.setSpan(new ForegroundColorSpan(color.second), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// background
			for (Pair<String, Integer> background : backgrounds) {
				startIndex = text.indexOf(background.first);
				if (startIndex == -1) continue;
				textLength = startIndex + background.first.length();
				ss.setSpan(new BackgroundColorSpan(background.second), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// scale
			for (Pair<String, Float> scale : scales) {
				startIndex = text.indexOf(scale.first);
				if (startIndex == -1) continue;
				textLength = startIndex + scale.first.length();
				ss.setSpan(new RelativeSizeSpan(scale.second), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// size
			for (Pair<String, Integer> size : sizes) {
				startIndex = text.indexOf(size.first);
				if (startIndex == -1) continue;
				textLength = startIndex + size.first.length();
				ss.setSpan(new AbsoluteSizeSpan(size.second, true), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// supper
			for (String supper : suppers) {
				startIndex = text.indexOf(supper);
				if (startIndex == -1) continue;
				textLength = startIndex + supper.length();
				ss.setSpan(new SuperscriptSpan(), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// sub
			for (String sub : subs) {
				startIndex = text.indexOf(sub);
				if (startIndex == -1) continue;
				textLength = startIndex + sub.length();
				ss.setSpan(new SubscriptSpan(), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// image
			for (Pair<String, Integer> image : images) {
				startIndex = text.indexOf(image.first);
				if (startIndex == -1) continue;
				textLength = startIndex + image.first.length();
				ss.setSpan(new ImageSpan(CoreBaseApplication.context, image.second, ImageSpan.ALIGN_BASELINE), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// underLine
			for (String underLine : underLines) {
				startIndex = text.indexOf(underLine);
				if (startIndex == -1) continue;
				textLength = startIndex + underLine.length();
				ss.setSpan(new UnderlineSpan(), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// deleteLine
			for (String deleteLine : deleteLines) {
				startIndex = text.indexOf(deleteLine);
				if (startIndex == -1) continue;
				textLength = startIndex + deleteLine.length();
				ss.setSpan(new StrikethroughSpan(), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// callback
			for (Pair<String, OnClickListener> callback : callbacks) {
				startIndex = text.indexOf(callback.first);
				if (startIndex == -1) continue;
				textLength = startIndex + callback.first.length();
				ss.setSpan(new OnClickSpan(callback.first, callback.second), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// urls
			for (Pair<String, String> url : urls) {
				startIndex = text.indexOf(url.first);
				if (startIndex == -1) continue;
				textLength = startIndex + url.first.length();
				ss.setSpan(new URLSpan(url.second), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			return ss;
		}

		/**
		 * 设置 TextView 为可点击
		 */
		public static void setTextClickable(TextView textView) {
			textView.setMovementMethod(LinkMovementMethod.getInstance());
			textView.setHighlightColor(0x00000000);
		}

		public interface OnClickListener {
			void onClick(String text);
		}

		public static class OnClickSpan extends ClickableSpan {
			private String text;
			private OnClickListener onClickListener;
			public OnClickSpan(String text, OnClickListener onClickListener) {
				this.text = text;
				this.onClickListener = onClickListener;
			}
			@Override
			public void onClick(@NonNull View widget) {
				if (onClickListener != null) onClickListener.onClick(text);
			}
			@Override
			public void updateDrawState(@NonNull TextPaint ds) { }
		}
	}
}
