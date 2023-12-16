package com.example.stikynotifycation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_nomal_notifycation).setOnClickListener {
            NomalNotification(this)
        }

        GoogleSearchNotification(this).showNotify()

//        val request = OneTimeWorkRequest.Builder(
//            StartNotificationWork::class.java,)
//            .setInitialDelay(10000, TimeUnit.SECONDS)
//            .build()
//        WorkManager.getInstance(applicationContext).enqueue(request)

//        val request2 = PeriodicWorkRequest.Builder(
//            StartNotificationWork::class.java,
//            1,
//            TimeUnit.MINUTES
//        ).build()

//        setPeriodicWorkRequest()
    }

    private fun setPeriodicWorkRequest(){
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(DownloadingWorker::class.java,16,TimeUnit.MINUTES)
            .setInitialDelay(10L, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
    }
}