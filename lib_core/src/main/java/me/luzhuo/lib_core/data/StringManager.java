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
import android.text.style.StyleSpan;
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

import androidx.annotation.ColorInt;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;

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
	public static boolean isContainChinese(@Nullable String str) {
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
		private List<Pair<String, Integer>> firstColors = new ArrayList<>();
		private List<Pair<String, Integer>> colors = new ArrayList<>();
		private List<Pair<String, Integer>> backgrounds = new ArrayList<>();
		private List<String> suppers = new ArrayList<>();
		private List<String> subs = new ArrayList<>();
		private List<Pair<String, Integer>> images = new ArrayList<>();
		private List<String> underLines = new ArrayList<>();
		private List<String> deleteLines = new ArrayList<>();
		private List<Pair<String, OnClickListener>> callbacks = new ArrayList<>();
		private List<Pair<String, String>> urls = new ArrayList<>();
		private List<String> bolds = new ArrayList<>();
		private List<String> italics = new ArrayList<>();

		public Text(String text) {
			this.text = text;
		}

		/**
		 * 对第一个匹配到的字符设置颜色
		 */
		@NonNull
		public StringManager.Text firstColor(@Nullable String text, @ColorInt int color) {
			if (TextUtils.isEmpty(text)) return this;

			firstColors.add(new Pair<>(text, color));
			return this;
		}

		/**
		 * 对所有匹配到的字符设置颜色
		 */
		@NonNull
		public StringManager.Text color(@Nullable String text, @ColorInt int color) {
			if (TextUtils.isEmpty(text)) return this;

			colors.add(new Pair<>(text, color));
			return this;
		}

		/**
		 * 字符的背景颜色
		 */
		@NonNull
		public StringManager.Text background(@Nullable String text, @ColorInt int background) {
			if (TextUtils.isEmpty(text)) return this;

			backgrounds.add(new Pair<>(text, background));
			return this;
		}

		/**
		 * 字体加粗
		 */
		@NonNull
		public StringManager.Text bold(@Nullable String text) {
			bolds.add(text);
			return this;
		}

		/**
		 * 斜体
		 */
		@NonNull
		public StringManager.Text italic(@Nullable String text) {
			italics.add(text);
			return this;
		}

		/**
		 * 字体的相对大小
		 * @param scale 放大比例
		 */
		@NonNull
		public StringManager.Text scale(@Nullable String text, float scale) {
			if (TextUtils.isEmpty(text)) return this;

			scales.add(new Pair<>(text, scale));
			return this;
		}

		/**
		 * 字体的绝对大小
		 */
		@NonNull
		public StringManager.Text size(@Nullable String text, int sp) {
			if (TextUtils.isEmpty(text)) return this;

			sizes.add(new Pair<>(text, sp));
			return this;
		}

		/**
		 * 上标
		 */
		@NonNull
		public StringManager.Text supper(@Nullable String text) {
			if (TextUtils.isEmpty(text)) return this;

			suppers.add(text);
			return this;
		}

		/**
		 * 下标
		 */
		@NonNull
		public StringManager.Text sub(@Nullable String text) {
			if (TextUtils.isEmpty(text)) return this;

			subs.add(text);
			return this;
		}

		/**
		 * 图片
		 * 根据图片的实际大小添加到内容中
		 */
		@NonNull
		public StringManager.Text image(@Nullable String text, @IdRes int image) {
			if (TextUtils.isEmpty(text)) return this;

			images.add(new Pair<>(text, image));
			return this;
		}

		/**
		 * 下划线
		 */
		@NonNull
		public StringManager.Text underLine(@Nullable String text) {
			if (TextUtils.isEmpty(text)) return this;

			underLines.add(text);
			return this;
		}

		/**
		 * 删除线
		 */
		@NonNull
		public StringManager.Text deleteLine(@Nullable String text) {
			if (TextUtils.isEmpty(text)) return this;

			deleteLines.add(text);
			return this;
		}

		/**
		 * 点击事件回调
		 * 需要调用 {@link #setTextClickable(TextView)} } 才能实现回调事件
		 */
		@NonNull
		public StringManager.Text click(@Nullable String text, @NonNull OnClickListener callback) {
			if (TextUtils.isEmpty(text)) return this;

			callbacks.add(new Pair<>(text, callback));
			return this;
		}

		/**
		 * URL路径跳转
		 */
		@NonNull
		public StringManager.Text url(@Nullable String text, @NonNull String url) {
			if (TextUtils.isEmpty(text)) return this;

			urls.add(new Pair<>(text, url));
			return this;
		}

		public SpannableString build() {
			final SpannableString ss = new SpannableString(text);
			int startIndex;
			int textLength;

			// first color
			for (Pair<String, Integer> color : firstColors) {
				startIndex = text.indexOf(color.first);
				if (startIndex == -1) continue;
				textLength = startIndex + color.first.length();
				ss.setSpan(new ForegroundColorSpan(color.second), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// colors
			for (Pair<String, Integer> color : colors) {
				int previous = 0;
				int current;
				while((current = text.indexOf(color.first, previous)) > -1) {
					ss.setSpan(new ForegroundColorSpan(color.second), current, current + color.first.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
					previous = current + 1;
				}
			}

			// background
			for (Pair<String, Integer> background : backgrounds) {
				startIndex = text.indexOf(background.first);
				if (startIndex == -1) continue;
				textLength = startIndex + background.first.length();
				ss.setSpan(new BackgroundColorSpan(background.second), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// bold
			for (String bold : bolds) {
				startIndex = text.indexOf(bold);
				if (startIndex == -1) continue;
				textLength = startIndex + bold.length();
				ss.setSpan(new StyleSpan(BOLD), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			}

			// italic
			for (String italic : italics) {
				startIndex = text.indexOf(italic);
				if (startIndex == -1) continue;
				textLength = startIndex + italic.length();
				ss.setSpan(new StyleSpan(ITALIC), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
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
				ss.setSpan(new ImageSpan(CoreBaseApplication.appContext, image.second, ImageSpan.ALIGN_BASELINE), startIndex, textLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
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

		@Override
		public String toString() {
			return this.build().toString();
		}

		/**
		 * 设置 TextView 为可点击
		 */
		public static void setTextClickable(@Nullable TextView textView) {
			if (textView == null) return;

			textView.setMovementMethod(LinkMovementMethod.getInstance());
			textView.setHighlightColor(0x00000000);
		}

		public interface OnClickListener {
			void onClick(@Nullable String text);
		}

		public static class OnClickSpan extends ClickableSpan {
			private String text;
			private OnClickListener onClickListener;
			public OnClickSpan(@Nullable String text, @Nullable OnClickListener onClickListener) {
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
