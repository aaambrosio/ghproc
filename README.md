# ghproc
 Run background processes on boot. Written in Kotlin for Android.


## Add jitpack repository

```
repositories {
   maven { url 'https://jitpack.io' }
}
```

## Add dependency

```
dependencies {
   implementation 'com.github.aaambrosio:ghproc:0.1.4'
}
```

## Usage in Kotlin

```kotlin
GhostProcess.setNotificationChannelID("ghost_process_test_notification_channel_id")
GhostProcess.setNotificationChannelName("Background Process Notifications")
GhostProcess.setNotificationIcon(R.drawable.ic_launcher_foreground)
GhostProcess.setNotificationTitle("Ghost Process")
GhostProcess.setNotificationContent("Background processes are running in the background")
GhostProcess.setProcessLoopInterval(10000) // set interval to 10 seconds
GhostProcess.setForegroundID(1001)
GhostProcess.setProcessAction(object : GhostAction {
    override fun execute() {
        // do something
    }
})
GhostProcess.init(applicationContext)
```

## Usage in Java

```java
GhostProcess.setNotificationChannelID("ghost_process_test_notification_channel_id");
GhostProcess.setNotificationChannelName("Background Process Notifications");
GhostProcess.setNotificationIcon(R.drawable.ic_launcher_foreground);
GhostProcess.setNotificationTitle("Ghost Process");
GhostProcess.setNotificationContent("Background processes are running in the background");
GhostProcess.setProcessLoopInterval(10000); // set interval to 10 seconds
GhostProcess.setForegroundID(1001);
GhostProcess.setProcessAction(() -> {
    // do something
});
GhostProcess.init(getApplicationContext());
```
\
See ```app_kotlin``` and ```app_java``` for examples.
