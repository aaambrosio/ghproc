package app.aaambrosio.ghproc_java;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import lib.aaambrosio.ghproc.GhostProcess;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Notification every 10 seconds

        NotificationChannel channel = new NotificationChannel("app_java_notification_channel_id", "App Notifications", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        GhostProcess.setNotificationChannelID("ghost_process_test_notification_channel_id");
        GhostProcess.setNotificationChannelName("Background Process Notifications");
        GhostProcess.setNotificationIcon(R.drawable.ic_launcher_foreground);
        GhostProcess.setNotificationTitle("Ghost Process");
        GhostProcess.setNotificationContent("Background processes are running in the background");
        GhostProcess.setProcessLoopInterval(10000);
        GhostProcess.setForegroundID(1001);
        GhostProcess.setProcessAction(() -> {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "app_java_notification_channel_id")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("App Java")
                    .setContentText("Notification from Ghost Process")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
            managerCompat.notify(null, 1002, builder.build());
        });
        GhostProcess.init(getApplicationContext());

        Log.i(GhostProcess.GHOST_PROCESS_LOG_TAG, "App Init");
    }
}
