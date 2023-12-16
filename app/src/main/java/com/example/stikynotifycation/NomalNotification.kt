package com.example.stikynotifycation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat

class NomalNotification(context: Context) {

    init {
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context)
        //设置标题
        builder.setContentTitle("通知标题")
            //设置内容
            .setContentText("通知栏内容")
            //设置大图标
            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.google))
            //设置小图标
            .setSmallIcon(R.mipmap.ic_launcher_round)
            //设置通知时间
            .setWhen(System.currentTimeMillis())
            //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
            .setDefaults(Notification.DEFAULT_SOUND)

        addChannelForNotificationBuilder(notificationManager, builder, "groupId", "groupName", "channelId", "channelName")
        //发送通知请求
        notificationManager?.notify(10, builder.build())
    }

    private fun addChannelForNotificationBuilder(
        notificationManager: NotificationManager,
        build: NotificationCompat.Builder?,
        groupID: String?,
        groupName: String?,
        channelID: String,
        channelName: CharSequence?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val group = NotificationChannelGroup(groupID, groupName)
            notificationManager.createNotificationChannelGroup(group)
            val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.group = groupID
            channel.setShowBadge(false)
            channel.enableLights(false)
            channel.setSound(null, null)
            channel.enableVibration(false)
            channel.vibrationPattern = longArrayOf(0)
            channel.lockscreenVisibility = Notification.VISIBILITY_SECRET
            notificationManager.createNotificationChannel(channel)
            build?.setChannelId(channelID)
        }
    }
}