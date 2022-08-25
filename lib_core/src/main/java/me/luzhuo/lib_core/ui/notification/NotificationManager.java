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
package me.luzhuo.lib_core.ui.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import me.luzhuo.lib_core.ui.notification.bean.ChannelBean;
import me.luzhuo.lib_core.ui.notification.bean.NotificationBean;

/**
 * Description:
 *
 * Example:
 * <p>
 * private ChannelBean HIGH = new ChannelBean("HIGH", "HIGH", ChannelBean.ChannelImportance.HIGH);
 * NotificationManager manager = new NotificationManager(this, HIGH, DEFAULT, LOW, MIN);
 * // 普通通知
 * manager.show(manager.createNotification(HIGH, new NotificationBean("标题1", "内容1", R.drawable.ic_launcher_background), manager.createIntent_Activity(new Intent(this, NotificationActivity.class))));
 * // 长内容通知
 * manager.show(manager.createNotification_BigText(HIGH, new NotificationBean("很长的内容", longContent, R.drawable.ic_launcher_background), null));
 * // 大图片通知
 * Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.orange);
 * manager.show(manager.createNotification_BigImage(HIGH, new NotificationBean("很长的内容", "longContent", R.drawable.ic_launcher_background), bitmap, null));
 * </p>
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/9/24 0:25
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class NotificationManager {
    private android.app.NotificationManager manager;
    private Context context;

    public NotificationManager(@NonNull Context context, @NonNull ChannelBean... channels) {
        this.context = context;
        manager = (android.app.NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Android 8.0+ 支持渠道通知, 即使多次执行, 系统也只会调用一次
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            for (ChannelBean channelBean : channels) {
                NotificationChannel channel = new NotificationChannel(channelBean.channelID, channelBean.chancelName, channelBean.importance.value());
                manager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * 创建通知
     * @param channelBean 通知渠道
     * @param notificationBean 通知
     * @param intent 点击通知后的跳转intent
     */
    public Notification createNotification(@NonNull ChannelBean channelBean, @NonNull NotificationBean notificationBean, @NonNull PendingIntent intent) {
        // 创建一个通知
        Notification notification = new NotificationCompat.Builder(context, channelBean.channelID)
                .setContentTitle(notificationBean.title)
                .setContentText(notificationBean.content)
                .setSmallIcon(notificationBean.icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), notificationBean.icon))
                .setContentIntent(intent)
                .setAutoCancel(true)
                .build();
        return notification;
    }

    public Notification createNotification(@NonNull ChannelBean channelBean, @NonNull NotificationBean notificationBean) {
        return createNotification(channelBean, notificationBean, createIntent_Activity(notificationBean.intent));
    }

    public Notification createNotification_BigText(@NonNull ChannelBean channelBean, @NonNull NotificationBean notificationBean, @NonNull PendingIntent intent) {
        Notification notification = new NotificationCompat.Builder(context, channelBean.channelID)
                .setContentTitle(notificationBean.title)
                .setContentText(notificationBean.content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationBean.content))
                .setSmallIcon(notificationBean.icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), notificationBean.icon))
                .setContentIntent(intent)
                .setAutoCancel(true)
                .build();
        return notification;
    }

    public Notification createNotification_BigImage(@NonNull ChannelBean channelBean, @NonNull NotificationBean notificationBean, @NonNull Bitmap bitmap, @NonNull PendingIntent intent) {
        Notification notification = new NotificationCompat.Builder(context, channelBean.channelID)
                .setContentTitle(notificationBean.title)
                .setContentText(notificationBean.content)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setSmallIcon(notificationBean.icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), notificationBean.icon))
                .setContentIntent(intent)
                .setAutoCancel(true)
                .build();
        return notification;
    }

    /**
     * 显示通知
     */
    public void show(@NonNull Notification notification) {
        show((int)(Math.random() * 100D), notification);
    }

    public void show(int id, @NonNull Notification notification) {
        manager.notify(id, notification);
    }

    public PendingIntent createIntent_Activity(@NonNull Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flags());
        return pendingIntent;
    }

    public PendingIntent createIntent_Service(@NonNull Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, flags());
        return pendingIntent;
    }

    public PendingIntent createIntent_Broadcast(@NonNull Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, flags());
        return pendingIntent;
    }

    public int flags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return PendingIntent.FLAG_IMMUTABLE; // Targeting S+ (version 31 and above) requires that one of FLAG_IMMUTABLE or FLAG_MUTABLE be specified when creating a PendingIntent.
        return  0;
    }
}
