package ch.dorianko.notificationtohomeassistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private var haUrl: EditText? = null
    private var haWebhook: EditText? = null
    private var haAttribute: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.haUrl = findViewById(R.id.editForHAUrl)
        this.haWebhook = findViewById(R.id.editHaWebhook)
        this.haAttribute = findViewById(R.id.editHaAttribute)
        val settings = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        this.haUrl?.setText(settings.getString("ha_url", ""))
        this.haWebhook?.setText(settings.getString("ha_webhook", ""))
        this.haAttribute?.setText(settings.getString("ha_attribute", ""))

        findViewById<Button>(R.id.btnSetNotificationAccess).setOnClickListener(this::onClickSettings)
        findViewById<Button>(R.id.btnUpdateData).setOnClickListener(this::onClickUpdateData)
        findViewById<Button>(R.id.btnTestData).setOnClickListener(this::onClickTestWebhook)
    }

    private fun onClickUpdateData(v: View){
        val settings = getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
        settings.putString("ha_url", this.haUrl?.text.toString().trim())
        settings.putString("ha_webhook", this.haWebhook?.text.toString().trim())
        settings.putString("ha_attribute", this.haAttribute?.text.toString().trim())
        settings.apply()
    }
    private fun onClickSettings(v: View){
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        startActivity(intent)
    }
    private fun onClickTestWebhook(v: View){
        HAHelper.ringAlarm(this)
    }
}