package com.example.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import androidx.core.app.NotificationManagerCompat;
import android.content.Context;
import android.widget.Toast;

public class Notif extends BroadcastReceiver {
    public static final String CHANNEL_1_ID = "chan1";
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    /*public void onReceive (Context context , Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        android.app.Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
        //if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        //}
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        assert notificationManager != null;
        notificationManager.notify(id , notification) ;
    }*/

    public void onReceive(Context context, Intent intent) {
       // Toast.makeText(context.getApplicationContext(),"y",Toast.LENGTH_LONG).show();
        //NotificationManagerCompat nmc=NotificationManagerCompat.from(this);
        //NotificationCompat.Builder nb=new NotificationCompat.Builder(this,"chan1")
        //        .setContentTitle("your event is coming soon");
        //nmc.notify(17,nb.build());
        String title=intent.getStringExtra("title");
        String message=intent.getStringExtra("message");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
        NotificationManager nm=( NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
       // Toast.makeText(context.getApplicationContext(),"x",Toast.LENGTH_LONG).show();
        //nm.notify(1, notification);
    }
}
