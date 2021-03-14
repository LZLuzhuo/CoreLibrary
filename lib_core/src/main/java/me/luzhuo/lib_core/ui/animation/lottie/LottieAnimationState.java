package me.luzhuo.lib_core.ui.animation.lottie;

/**
 * Description: Lottie's three-level animation is divided into default, positive order, and reverse order.
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/6/25 14:04
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public enum LottieAnimationState {
    /**
     * 默认, 自动无限循环播放
     */
    Default,
    /**
     * 正序播放, 只正序播放一次, 结束后停止动画
     */
    Forward,
    /**
     * 倒序播放, 只按倒序播放一次, 停止后继续执行默认循环动画
     */
    Reverse
}
