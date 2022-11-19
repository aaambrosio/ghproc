package app.aaambrosio.ghproc_kotlin

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import lib.aaambrosio.ghproc.GhostAction
import lib.aaambrosio.ghproc.GhostProcess

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        //Notification every 10 seconds

        val channel = NotificationChannel(
            "app_kotlin_notification_channel_id",
            "App Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)

        GhostProcess.setNotificationChannelID("ghost_process_test_notification_channel_id")
        GhostProcess.setNotificationChannelName("Background Process Notifications")
        GhostProcess.setNotificationIcon(R.drawable.ic_launcher_foreground)
        GhostProcess.setNotificationTitle("Ghost Process")
        GhostProcess.setNotificationContent("Background processes are running in the background")
        GhostProcess.setProcessLoopIntervals(5000, 5000)
        GhostProcess.setForegroundID(1001)
        GhostProcess.setProcessAction(object : GhostAction {
            override fun execute() {
                val builder: NotificationCompat.Builder =
                    NotificationCompat.Builder(applicationContext, "app_kotlin_notification_channel_id")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("App Kotlin")
                        .setContentText("Notification from Ghost Process")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                val managerCompat = NotificationManagerCompat.from(applicationContext)
                managerCompat.notify(null, 1002, builder.build())
            }
        })
        GhostProcess.init(applicationContext)

        Log.i(GhostProcess.GHOST_PROCESS_LOG_TAG, "App Init")
    }
}