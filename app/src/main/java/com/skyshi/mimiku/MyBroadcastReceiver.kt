package com.skyshi.mimiku

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.app.NotificationManager
import android.app.NotificationChannel
import android.media.RingtoneManager
import android.app.PendingIntent
import android.support.v4.app.NotificationCompat


class MyBroadcastReceiver : BroadcastReceiver() {
    var CHANNEL_ID = "EXAMPLE_CHANNEL_ID"

    override fun onReceive(context: Context?, p1: Intent?) {
        context?.let {
            showNotification(context, "Hai Kamu, iya Kamu", "Jangan lupa mimik")
        }
    }

    fun showNotification(context: Context, messageTitle: String, messageBody: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "android_notification", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}