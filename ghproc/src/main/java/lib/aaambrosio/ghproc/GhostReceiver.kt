package lib.aaambrosio.ghproc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class GhostReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.i(GhostProcess.GHOST_PROCESS_LOG_TAG, "Boot Complete")
        }
    }
}