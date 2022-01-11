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
package me.luzhuo.lib_core.app.phone;

import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.phone.enums.Rom;

/**
 * Description: 获取手机Rom系统的工具类
 * @Author: Luzhuo
 * @Creation Date: 2021/12/11 17:13
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
class RomUtils {

    public @Nullable Rom getPhoneRom() {
        if (Build.MANUFACTURER.contains("HUAWEI")) return Rom.Rom_Huawei;
        if (!TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"))) return Rom.Rom_Miui;
        if (checkIsMeizuRom()) return Rom.Rom_Meizu;
        if (Build.MANUFACTURER.contains("QiKU") || Build.MANUFACTURER.contains("360")) return Rom.Rom_360;
        if (Build.MANUFACTURER.contains("OPPO") || Build.MANUFACTURER.contains("oppo")) return Rom.Rom_Oppo;
        if (Build.MANUFACTURER.contains("VIVO") || Build.MANUFACTURER.contains("vivo")) return Rom.Rom_Vivo;
        return null;
    }

    private boolean checkIsMeizuRom() {
        String systemProperty = getSystemProperty("ro.build.display.id");
        if (TextUtils.isEmpty(systemProperty)) return false;
        else return (systemProperty.contains("flyme") || systemProperty.toLowerCase().contains("flyme"));
    }

    private @Nullable String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process process = Runtime.getRuntime().exec("getprop $propName");
            input = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (Exception e) {
            return null;
        } finally {
            if (input != null) {
                try { input.close();
                } catch (IOException io) { io.printStackTrace(); }
            }
        }
        return line;
    }
}
