/* Copyright 2021 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.data.clipboard;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.LruCache;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description: 截屏管理
 * @Author: Luzhuo
 * @Creation Date: 2021/5/28 21:00
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ScreenshotManager {
    @Nullable
    public static Bitmap screenshot(@Nullable FragmentActivity activity) {
        if (activity == null) return null;

        final View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        final Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    @Nullable
    public static Bitmap screenshot(@Nullable View view) {
        if (null == view) return null;

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
            view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());
        } else {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        final Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    @Nullable
    public static Bitmap screenshot(@Nullable ScrollView scrollView) {
        if (scrollView == null) return null;
        
        int height = 0;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            height += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(0xffffffff);
        }
        final Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), height, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    @Nullable
    public static Bitmap screenshot(@Nullable ListView listview) {
        if (listview == null) return null;

        final ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<>();
        for (int i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }
        final Bitmap bigBitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
        final Canvas bigCanvas = new Canvas(bigBitmap);
        final Paint paint = new Paint();
        int iHeight = 0;
        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigCanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
        }
        return bigBitmap;
    }

    @Nullable
    public static Bitmap screenshot(@Nullable RecyclerView recyclerView) {
        if (recyclerView == null) return null;

        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) return null;

        final int size = adapter.getItemCount();
        int height = 0;
        final Paint paint = new Paint();
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
        for (int i = 0; i < size; i++) {
            RecyclerView.ViewHolder holder = adapter.createViewHolder(recyclerView, adapter.getItemViewType(i));
            adapter.onBindViewHolder(holder, i);
            holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
            holder.itemView.setDrawingCacheEnabled(true);
            holder.itemView.buildDrawingCache();
            Bitmap drawingCache = holder.itemView.getDrawingCache();
            if (drawingCache != null) { bitmaCache.put(String.valueOf(i), drawingCache); }
            height += holder.itemView.getMeasuredHeight();
        }
        final Bitmap bigBitmap = Bitmap.createBitmap(recyclerView.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
        final Canvas bigCanvas = new Canvas(bigBitmap);
        final Drawable lBackground = recyclerView.getBackground();
        if (lBackground instanceof ColorDrawable) {
            ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
            int lColor = lColorDrawable.getColor();
            bigCanvas.drawColor(lColor);
        }

        int iHeight = 0;
        for (int i = 0; i < size; i++) {
            Bitmap bitmap = bitmaCache.get(String.valueOf(i));
            bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
            iHeight += bitmap.getHeight();
            bitmap.recycle();
        }
        return bigBitmap;
    }

    /**
     * 在WebView创建之前调用
     * 可以放在Application调用
     */
    public static void initWebViewScreenshot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.webkit.WebView.enableSlowWholeDocumentDraw();
        }
    }

    /**
     * 对WebView进行截图
     * 在WebView还未创建之前, 请先调用 {@link ScreenshotManager#initWebViewScreenshot() }
     */
    @Nullable
    public static Bitmap screenshot(@Nullable WebView webView) {
        if (webView == null) return null;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            Picture picture = webView.capturePicture();
            int width = picture.getWidth();
            int height = picture.getHeight();
            if (width > 0 && height > 0) {
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(bitmap);
                picture.draw(canvas);
                return bitmap;
            }
        } else {
            float scale = webView.getScale();
            int width = webView.getWidth();
            int height = (int) (webView.getContentHeight() * scale + 0.5);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            webView.draw(canvas);
            return bitmap;
        }
        return null;
    }

    /**
     * 禁止用户截屏
     * 代码依旧可以使用
     */
    public static void noScreenshot(FragmentActivity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }
}
