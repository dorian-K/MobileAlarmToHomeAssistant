package ch.dorianko.notificationtohomeassistant

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationService : NotificationListenerService() {
	private val TAG = this.javaClass.simpleName

	override fun onCreate() {
		Log.i(TAG, "Service created")
		super.onCreate()
	}

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
		return START_STICKY
	}

	override fun onDestroy() {
		super.onDestroy()
		Log.i(TAG, "Service destroyed")
	}

	override fun onNotificationPosted(sbn: StatusBarNotification) {
		try {
			val not = sbn.notification
			val pkg = sbn.packageName
			val chn = not.channelId
			if (!pkg.equals(
					"com.android.deskclock",
					ignoreCase = true
				))
					return
			if (not.actions.size != 1)
				return

			if(!chn.equals("channel_id_deskclock_alarm", ignoreCase = true))
				return

			val actionTitle = not.actions[0].title.toString()
			if(!(actionTitle.equals("stop", ignoreCase = true) || actionTitle.equals("silence", ignoreCase = true)))
				return
			Log.i(TAG, "Deskclock active!")

			// Alarm clock notification active!
			// Let's send the ring event
			HAHelper.ringAlarm(this)
		} catch (e: Exception) {
			Log.e(TAG, "Error in onNotificationPosted", e)
		}
	}
}