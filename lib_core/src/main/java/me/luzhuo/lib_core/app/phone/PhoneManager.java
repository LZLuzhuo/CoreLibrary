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
package me.luzhuo.lib_core.app.phone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import me.luzhuo.lib_core.app.pattern.PatternCheck;
import me.luzhuo.lib_core.app.pattern.RegularType;
import me.luzhuo.lib_core.app.phone.enums.Rom;
import me.luzhuo.lib_core.ui.toast.ToastManager;

/**
 * Description: 电话管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/9/14 18:02
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class PhoneManager {
    private RomUtils romUtils = new RomUtils();

    /**
     * 跳转到拨号盘界面
     * @param phone 手机号码
     */
    public void call2Dial(@Nullable Context context, @Nullable String phone) {
        if (TextUtils.isEmpty(phone) || context == null) return;

        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.parse("tel:" + phone);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 直接拨打电话
     * 需要权限 android.permission.CALL_PHONE
     * @param context Context
     * @param phone   手机号码
     */
    @RequiresPermission(Manifest.permission.CALL_PHONE)
    public void call(@Nullable Context context, @Nullable String phone) {
        if (TextUtils.isEmpty(phone) || context == null) return;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(context, "请授予拨打电话权限!");
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 检查手机号码是否正确
     */
    public boolean check(@Nullable String phone) {
        if(TextUtils.isEmpty(phone)) return false;

        return new PatternCheck().check(RegularType.MobilePhone, phone);
    }

    /**
     * 用*隐藏手机号中间位置的信息 (规则参考浙江移动)
     * 15812349956 => 158****9956
     *
     * @param phone 手机号
     * @return 隐藏处理后的手机号
     */
    @Nullable
    public String hint(@Nullable String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) return phone;

        return phone.substring(0, 3).concat("****").concat(phone.substring(7, 11));
    }

    /**
     * 获取手机Sim卡序列号(单卡)
     * 需要权限 android.permission.READ_PHONE_STATE
     * @return String/null 89860036111651004736
     */
    @Nullable
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public String getSimSN(Context context) {
        if (context == null) return null;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(context, "请授予读取电话状态权限!");
            return null;
        }

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 获取手机Rom
     * @return 手机Rom
     */
    public @Nullable Rom getPhoneRom() {
        return romUtils.getPhoneRom();
    }
}
