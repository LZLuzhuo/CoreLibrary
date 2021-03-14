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
package me.luzhuo.lib_core.ui.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import me.luzhuo.lib_core.R;

/**
 * Description: 动画管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/12/29 15:09
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class AnimationManager {
    private Context context;

    public AnimationManager(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 开始旋转
     */
    public void startRotate(View view, @AnimRes int anim) {
        if (view.getAnimation() != null) return;

        Animation aa = AnimationUtils.loadAnimation(context, anim);
        view.startAnimation(aa);
    }

    public void startRotate(View view) {
        startRotate(view, R.anim.core_anim_rotate);
    }

    /**
     * 停止旋转
     */
    public void stopRotate(View view) {
        view.clearAnimation();
    }
}
