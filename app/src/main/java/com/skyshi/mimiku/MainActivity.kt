package com.skyshi.mimiku

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import java.util.*


class MainActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("mimiku", Context.MODE_PRIVATE) ?: return

        val c = Calendar.getInstance()
        c.timeInMillis = sharedPreferences.getLong("time", 0)
        c.get(Calendar.DAY_OF_MONTH)

        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) != c.get(Calendar.DAY_OF_MONTH)) {
            setText(0)
        } else {
            setText(sharedPreferences.getInt("counter", 0))
        }


        frame_layout.setOnClickListener {
            setText(sharedPreferences.getInt("counter", 0) + 1)

        }

        val time = Calendar.getInstance()
        time.timeInMillis = System.currentTimeMillis()
        time.set(Calendar.HOUR_OF_DAY, 12)
        time.set(Calendar.MINUTE, 0)
        time.set(Calendar.SECOND, 0)

        val intent = Intent(this, MyBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 110, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.timeInMillis, 1000 * 60 * 24, pendingIntent)

    }

    private fun setText(y: Int) {
        val x = sharedPreferences.edit()
        x.putInt("counter", y)
        x.putLong("time", Calendar.getInstance().timeInMillis)
        text_view.text = y.toString()
        x.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menux, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.setting -> {
                startActivity(intentFor<SettingActivity>().singleTop())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
