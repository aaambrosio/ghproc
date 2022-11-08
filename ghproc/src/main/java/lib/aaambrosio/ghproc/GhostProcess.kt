package lib.aaambrosio.ghproc

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Suppress("unused")
class GhostProcess : Service() {
    class GhostException(message:String): Exception(message)

    private var executor: ExecutorService = Executors.newSingleThreadExecutor()
    private var handler: Handler = Handler(Looper.getMainLooper())

    private fun executorTask() {
        try {
            Thread.sleep(processLoopInterval)

            processAction?.execute()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        handler.post { executor.execute { executorTask() } }
    }

    override fun onCreate() {
        if(ghostProcessNotification != null) {
            startForeground(foregroundID!!, ghostProcessNotification)
        } else {
            val channel = NotificationChannel(
                notificationChannelID,
                notificationChannelName,
                notificationImportance
            )
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)

            startForeground(
                foregroundID!!,
                NotificationCompat.Builder(this, notificationChannelID)
                    .setSmallIcon(notificationIcon!!)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationContent)
                    .build()
            )
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        GHOST_PROCESS_ACTIVE = false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        executor.execute(this::executorTask)

        return START_STICKY
    }

    companion object {
        @JvmField
        var GHOST_PROCESS_LOG_TAG = "GHOST_PROCESS_LOG_TAG"

        private var GHOST_PROCESS_ACTIVE = false

        private var processAction: GhostAction? = null
        private var processLoopInterval: Long = 5000

        private var notificationChannelID = ""
        private var notificationChannelName = ""
        private var notificationImportance = NotificationManager.IMPORTANCE_DEFAULT
        private var notificationIcon: Int? = null
        private var notificationTitle = ""
        private var notificationContent = ""
        private var foregroundID: Int? = null

        private var ghostProcessNotification: Notification? = null

        @JvmStatic
        fun setProcessAction(action: GhostAction) {
            this.processAction = action
        }

        @JvmStatic
        fun setProcessLoopInterval(interval: Long) {
            this.processLoopInterval = interval
        }

        @JvmStatic
        fun setNotificationChannelID(id: String) {
            this.notificationChannelID = id
        }

        @JvmStatic
        fun setNotificationChannelName(name: String) {
            this.notificationChannelName = name
        }

        @JvmStatic
        fun setNotificationIcon(icon: Int) {
            this.notificationIcon = icon
        }

        @JvmStatic
        fun setNotificationTitle(title: String) {
            this.notificationTitle = title
        }

        @JvmStatic
        fun setNotificationContent(content: String) {
            this.notificationContent = content
        }

        @JvmStatic
        fun setForegroundID(id: Int) {
            this.foregroundID = id
        }

        @JvmStatic
        fun init(context: Context) {
            if(notificationChannelID.trim() == "") {
                throw GhostException("notificationChannelID cannot be empty. Assign value using GhostProcess.setNotificationChannelID() before calling GhostProcess.init().")
            }

            if(notificationChannelName.trim() == "") {
                throw GhostException("notificationChannelName cannot be empty. Assign value using GhostProcess.setNotificationChannelName() before calling GhostProcess.init().")
            }

            if(notificationIcon == null) {
                throw GhostException("notificationIcon cannot be null. Assign value using GhostProcess.setNotificationIcon() before calling GhostProcess.init().")
            }

            if(notificationTitle.trim() == "") {
                throw GhostException("notificationTitle cannot be empty. Assign value using GhostProcess.setNotificationTitle() before calling GhostProcess.init().")
            }

            if(notificationContent.trim() == "") {
                throw GhostException("notificationContent cannot be empty. Assign value using GhostProcess.setNotificationContent() before calling GhostProcess.init().")
            }

            if(foregroundID == null) {
                throw GhostException("foregroundID cannot be null. Assign value using GhostProcess.setForegroundID() before calling GhostProcess.init().")
            }

            startService(context)
        }

        @JvmStatic
        fun init(context: Context, foregroundId: Int, notification: Notification) {
            this.foregroundID = foregroundId
            this.ghostProcessNotification = notification

            startService(context)
        }

        private fun startService(context: Context) {
            try {
                if(!GHOST_PROCESS_ACTIVE) {
                    GHOST_PROCESS_ACTIVE = true
                    val serviceIntent = Intent(context, GhostProcess::class.java)
                    context.startForegroundService(serviceIntent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}