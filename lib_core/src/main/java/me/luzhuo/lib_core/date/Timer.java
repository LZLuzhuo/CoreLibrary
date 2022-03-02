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
package me.luzhuo.lib_core.date;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import me.luzhuo.lib_core.date.callback.ICountDownCallback;
import me.luzhuo.lib_core.date.callback.ITimerCallback;

/**
 * Description: 计时器
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/9/14 18:13
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class Timer implements LifecycleObserver {
    private static Handler mainThread;
    private List<CountDownTimer> countDownTimers = new LinkedList<>();

    public Timer(@NonNull FragmentActivity activity) {
        mainThread = new Handler(Looper.getMainLooper());
        activity.getLifecycle().addObserver(this);
    }

    public Timer(@NonNull Fragment fragment) {
        mainThread = new Handler(Looper.getMainLooper());
        fragment.getLifecycle().addObserver(this);
    }

    /**
     * 倒计时
     * 可以同时开启多个
     * @param countSeconds 总时长, 单位s
     */
    public @Nullable CountDownTimer countDownTimer(int countSeconds, @Nullable final ICountDownCallback callback) {
        if(countSeconds <= 0) return null;

        CountDownTimer countDownTimer = new CountDownTimer(countSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int untilFinishedSecondes = (int) ((millisUntilFinished / 1000f) + 0.5);
                if(callback != null) callback.untilFinishedSecondes(untilFinishedSecondes);
            }
            @Override
            public void onFinish() {
                if(callback != null) callback.finished();
            }
        }.start();
        countDownTimers.add(countDownTimer);
        return countDownTimer;
    }


    private TimerTask task;
    private long startTime = 0;
    /**
     * 开始计时
     * 同时只能开启一个
     */
    @NonNull
    public TimerTask startTimer(@Nullable final ITimerCallback callback) {
        if (task != null) return task;
        final java.util.Timer timer = new java.util.Timer(true);

        task = new TimerTask() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                if (callback == null) return;

                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (task == null) return;

                        long time = (task.scheduledExecutionTime() - startTime) / 1000;
                        callback.onTask(time);
                        callback.onTask(String.format(" %02d:%02d", time / 60, time % 60));
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);
        startTime = task.scheduledExecutionTime();
        return task;
    }

    /**
     * 结束计时
     */
    public long endTimer() {
        if (task == null) return 0;

        task.cancel();
        long endTime = (task.scheduledExecutionTime() - startTime) / 1000;
        task = null;
        return endTime;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        // CountDownTimer
        for (CountDownTimer countDownTimer : countDownTimers) {
            countDownTimer.cancel();
        }
        // TimerTask
        endTimer();
    }
}
