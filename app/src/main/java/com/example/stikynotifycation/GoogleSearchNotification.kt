package com.example.stikynotifycation

import android.app.*
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class GoogleSearchNotification(context: Activity) {
    //通知Id
    private val permanentNotificationId = 4
    private var notificationManager: NotificationManager
    var builder: NotificationCompat.Builder

    init {
        builder = NotificationCompat.Builder(context)
        notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        var remoteView = RemoteViews(context.packageName, R.layout.layout_google_search)
        builder?.setSmallIcon(R.mipmap.google)
        builder?.setContent(remoteView)
        builder?.setOngoing(false)
        setClickSearchIntent(context, remoteView)
        addChannelForNotificationBuilder(notificationManager, builder, "searchGroup", "search", "searchid", "search")
    }

    fun showNotify() {
        notificationManager.notify(permanentNotificationId, builder?.build().apply {
            flags = Notification.FLAG_AUTO_CANCEL or this.flags
        })
    }

    private fun setClickSearchIntent(activity: Activity, remoteView: RemoteViews) {
        var intent = Intent(activity, JumpActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0)
//        remoteView.setOnClickPendingIntent(R.id.iv_google_search, pendingIntent)
        builder?.setContentIntent(pendingIntent)
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