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
package me.luzhuo.lib_core.app.appinfo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.appinfo.callback.AppForegroundCallback;

/**
 * Description: App前后台切换管理
 * @Author: Luzhuo
 * @Creation Date: 2021/12/25 11:27
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
class AppForeground {
    private int count = 0;

    private AppForeground(){ }
    private static final AppForeground instance = new AppForeground();
    public static AppForeground getInstance() {
        return instance;
    }

    /**
     * App前后台进程切换的监听
     * @param context Application的上下文
     */
    public void registerAppForegroundCallback(@NonNull Application context, @Nullable final AppForegroundCallback callback) {
        // 注册监听器
        context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) { }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                if (count == 0) {
                    if (callback != null) callback.onForegroundCallback();
                }
                count++;
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) { }
            @Override
            public void onActivityPaused(@NonNull Activity activity) { }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                count--;
                if (count == 0) {
                    if (callback != null) callback.onBackgroundCallback();
                }
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) { }
            @Override
            public void onActivityDestroyed(@NonNull Activity activity) { }
        });
    }
}
