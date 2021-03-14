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
package me.luzhuo.lib_core.app.appinfo.bean;

/**
 * Description: 应用信息
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/7 14:21
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class AppInfo {
    /**
     * 应用版本名 (version name)
     * 例如: "0.0.1"
     */
    public String versionName;
    /**
     * 应用版本编号 (version code)
     * 例如: 1
     */
    public int versionCode;
    /**
     * 应用包名 (package name)
     * 比如: me.luzhuo.demo
     */
    public String packageName;
}
