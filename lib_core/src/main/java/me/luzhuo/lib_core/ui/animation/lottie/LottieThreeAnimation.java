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
package me.luzhuo.lib_core.ui.animation.lottie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Description:
 * 3级的Lottie动画, 支持 默认, 正序, 倒序
 * 默认: 自动无限循环播放
 * 正序: 只播放一次, 结束后停止动画
 * 倒序: 反转播放一次, 结束后继续执行默认循环动画
 *
 * Lottie's three-level animation is divided into default, forward sequence, and reverse sequence.
 * default: Automatic infinite loop playback.
 * forward sequence: Play only once in forward sequence, stop the animation after the end.
 * reverse sequence: Play only once in reverse order, and execute the default animation loop after stopping.
 *
 * <p><img src="http://luzhuo.me/luzhuoCode/lib_core/animation/lottie/LottieThreeAnimation/LottieThreeAnimation.gif"/></p>
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/6/25 14:01
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class LottieThreeAnimation {
    private LottieAnimationView animationView;

    // Frames played by default (infinite loop)
    private int nomalStart, nomalEnd;
    // Frames between forward and reverse sequence (only executed once)
    private int sequenceStart, sequenceEnd;
    private LottieAnimationState state;

    public LottieThreeAnimation(LottieAnimationView animationView, int nomalStart, int nomalEnd, int sequenceStart, int sequenceEnd){
        this.animationView = animationView;
        this.nomalStart = nomalStart;
        this.nomalEnd = nomalEnd;
        this.sequenceStart = sequenceStart;
        this.sequenceEnd = sequenceEnd;

        // Automatically play the default animation
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if(state != LottieAnimationState.Reverse) return;

                autoPlay();
            }
        });
        autoPlay();
    }

    /**
     * 自动播放默认动画
     * Automatically play the default animation.
     * If it is currently the default animation, the execution of this function is invalid.
     */
    protected void autoPlay() {
        if(state == LottieAnimationState.Default) return;

        state = LottieAnimationState.Default;
        animationView.setMinAndMaxFrame(nomalStart, nomalEnd);
        animationView.setRepeatCount(-1);
        animationView.playAnimation();
    }

    /**
     * 正序播放: 播放完成后停止动画
     * 如果当前已是正序动画, 执行该函数无效
     * forward sequence: Stop the animation after the playback is complete.
     * If it is currently a forward sequence animation, the execution of this function is invalid.
     */
    public void forward() {
        if(state == LottieAnimationState.Forward) return;

        state = LottieAnimationState.Forward;
        animationView.setMinAndMaxFrame(sequenceStart, sequenceEnd);
        animationView.setRepeatCount(0);
        animationView.setSpeed(1f);
        if(!animationView.isAnimating()) animationView.playAnimation();
    }

    /**
     * 倒序播放, 播放完成后立即播放默认动画
     * 如果当前已是倒序动画, 执行该函数无效
     */
    public void reverse() {
        if(state == LottieAnimationState.Reverse) return;

        state = LottieAnimationState.Reverse;
        animationView.setMinAndMaxFrame(sequenceStart, sequenceEnd);
        animationView.setRepeatCount(0);
        animationView.setSpeed(-1f);
        if(!animationView.isAnimating()) animationView.playAnimation();
    }
}
