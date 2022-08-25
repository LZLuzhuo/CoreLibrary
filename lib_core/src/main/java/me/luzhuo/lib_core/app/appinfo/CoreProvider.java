package me.luzhuo.lib_core.app.appinfo;

import android.app.Application;

import androidx.core.content.FileProvider;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;

/**
 * 使用 ContentProvider 进行SDK的初始化, 即获取 ApplicationContext.
 */
public class CoreProvider extends FileProvider {
    @Override
    public boolean onCreate() {
        // 此处的onCreate()先于 Application的onCreate()执行, 但是 Application 已创建.
        CoreBaseApplication.appContext = (Application) getContext().getApplicationContext();
        return super.onCreate();
    }
}
