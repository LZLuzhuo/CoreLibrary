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

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;

/**
 * Description: 动画管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/12/29 15:09
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class AnimationManager {
    private Context context;

    public AnimationManager() {
        this.context = CoreBaseApplication.appContext;
    }

    /**
     * 开始旋转 (一直旋转, 直到调用stopRotate())
     */
    public void startRotate(@NonNull View view, @AnimRes int anim) {
        if (view.getAnimation() != null) return;

        Animation aa = AnimationUtils.loadAnimation(context, anim);
        view.startAnimation(aa);
    }

    public void startRotate(@NonNull View view) {
        startRotate(view, R.anim.core_anim_rotate);
    }

    /**
     * 停止旋转
     */
    public void stopRotate(@NonNull View view) {
        view.clearAnimation();
    }

    /**
     * 箭头指向动画
     * 每次调用, 旋转180°角
     */
    private int rotateArrowCount = 0;
    public void rotateArrow(View view) {
        rotateArrowCount++;
        view.animate()
                .rotation(180f * rotateArrowCount)
                .setDuration(300)
                .start();
    }

    public void fadeInAnimation(@NonNull View view) {
        fadeInAnimation(view, 1300);
    }
    /**
     * 淡入动画
     * 前提View需要时 GONE 或者 INVISIBLE 状态
     */
    public void fadeInAnimation(@NonNull View view, long duration) {
        if (view.getVisibility() == View.VISIBLE) return;

        final ObjectAnimator translationAnim = ObjectAnimator.ofFloat(view, "translationX", -120f, 0f);
        translationAnim.setDuration(duration);
        translationAnim.setInterpolator(new DecelerateInterpolator());

        final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        alphaAnimator.setDuration(duration);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnim, alphaAnimator);
        animatorSet.start();

        view.setVisibility(View.VISIBLE);
    }
}
