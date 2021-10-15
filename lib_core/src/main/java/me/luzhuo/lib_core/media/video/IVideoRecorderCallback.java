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
package me.luzhuo.lib_core.media.video;

import android.net.Uri;

import java.io.File;

/**
 * Description: 录像回调接口
 * @Author: Luzhuo
 * @Creation Date: 2021/9/8 0:38
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public interface IVideoRecorderCallback {
    /**
     * 录像成功后回调
     * @param fileUri Uri类型的路径
     * @param filePath file类型的路径
     */
    public void onVideoRecorderCallback(Uri fileUri, File filePath);
}
