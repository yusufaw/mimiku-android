package com.skyshi.mimiku

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedpreferences = getSharedPreferences("mimiku", Context.MODE_PRIVATE) ?: return
        setText(sharedpreferences.getInt("counter", 0))

        frame_layout.setOnClickListener {
            val x = sharedpreferences.edit()
            x.putInt("counter", sharedpreferences.getInt("counter", 0) + 1)
            setText(sharedpreferences.getInt("counter", 0) + 1)
            x.apply()
        }
    }

    private fun setText(y: Int) {
        text_view.text = y.toString()
    }
}
