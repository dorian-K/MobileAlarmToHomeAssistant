package ch.dorianko.notificationtohomeassistant

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.nio.charset.StandardCharsets

class HAHelper {
	companion object {
		var TAG: String = this::class.java.name

		fun ringAlarm(ctx: Context){
			val volleyQueue = Volley.newRequestQueue(ctx)
			val settings = ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE)
			val attribute = settings.getString("ha_attribute", "isEngaged")
			val haurl = settings.getString("ha_url", "")
			val wbId = settings.getString("ha_webhook", "")

			val url = "${haurl}/api/webhook/${wbId}"

			val stringRequest: StringRequest = object : StringRequest(
				Method.POST, url,
				Response.Listener {
					Log.i(TAG, "Request successful!")
				},
				Response.ErrorListener {
					Log.e(TAG, "Request errored!", it)
					Log.e(TAG, it.message+"")
				}) {
				@Throws(AuthFailureError::class)
				override fun getBody(): ByteArray {
					return ("{\"$attribute\": \"true\"}").toByteArray(StandardCharsets.UTF_8)
				}

				override fun getBodyContentType(): String {
					return "application/json"
				}
			}

			stringRequest.tag = "wb"

			volleyQueue.add(stringRequest)
		}
	}
}