/* Copyright 2022 Luzhuo. All rights reserved.
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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

/**
 * Description: 手机网络连接管理
 * @Author: Luzhuo
 * @Creation Date: 2022/1/8 23:22
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public class ConnectManager {

    public enum ConnectType {
        NONE, WIFI, MOBILE
    }

    /**
     * 当前网络连接类型
     * 需要添加权限: android.permission.ACCESS_NETWORK_STATE
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @NonNull
    public ConnectType checkConnectType(@NonNull Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileWorkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileWorkInfo != null) {
            if (mobileWorkInfo.isConnected()) return ConnectType.MOBILE;
        }

        NetworkInfo wifiWorkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiWorkInfo != null) {
            if (wifiWorkInfo.isConnected()) return ConnectType.WIFI;
        }

        return ConnectType.NONE;
    }

}
