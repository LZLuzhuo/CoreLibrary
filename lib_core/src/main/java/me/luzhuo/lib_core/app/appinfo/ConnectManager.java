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

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;

/**
 * Description: 手机网络连接管理
 * @Author: Luzhuo
 * @Creation Date: 2022/1/8 23:22
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public class ConnectManager {
    private Context context;

    public ConnectManager() {
        this.context = CoreBaseApplication.appContext;
    }

    public ConnectManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public enum ConnectType {
        NONE, WIFI, MOBILE
    }

    /**
     * 当前网络连接类型
     * 需要添加权限: android.permission.ACCESS_NETWORK_STATE
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    @NonNull
    public ConnectType checkConnectType() {
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

    /**
     * 获取wifi的ip
     * @return ipv4
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public int wifiIP() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getIpAddress(); // IPV4
    }

    /**
     * int类型的ipv4转为String类型的IPV4
     * @param ipv4 int类型的IPV4
     * @return String类型的IPV4
     */
    @NonNull
    public String ipv4(int ipv4) {
        return (ipv4 & 0xFF) + "." + (ipv4 >> 8 & 0xFF) + "." + (ipv4 >> 16 & 0xFF) + "." + (ipv4 >> 24 & 0xFF);
    }
}
