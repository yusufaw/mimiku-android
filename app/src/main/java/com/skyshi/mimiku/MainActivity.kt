package com.skyshi.mimiku

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
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

    }

    private fun setText(y: Int) {
        val x = sharedPreferences.edit()
        x.putInt("counter", y)
        x.putLong("time", Calendar.getInstance().timeInMillis)
        text_view.text = y.toString()
        x.apply()
    }
}
