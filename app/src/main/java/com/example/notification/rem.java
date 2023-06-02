package com.example.notification;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import androidx.core.app.NotificationManagerCompat;
import android.content.Context;
import android.widget.Toast;

public class rem extends BroadcastReceiver {
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
        Intent newIntent=new Intent( context , Details.class);

        ////****
        String name=intent.getStringExtra("name");
        String Notes=intent.getStringExtra("Notes");
        String eventDate=intent.getStringExtra("eventDate");
        String eventTime=intent.getStringExtra("eventTime");
        String Priority=intent.getStringExtra("Priority");
        String Type=intent.getStringExtra("Type");
        Intent newIntent=new Intent( context , Details.class);
        //newIntent.putExtra("title",title);
        newIntent.putExtra("name",name);
        newIntent.putExtra("Notes",Notes);
        newIntent.putExtra("eventDate",eventDate);
        newIntent.putExtra("eventTime",eventTime);
        newIntent.putExtra("Priority",Priority);
        newIntent.putExtra("Type",Type);


        //newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        TaskStackBuilder stack=TaskStackBuilder.create(context);
        stack.addNextIntentWithParentStack(newIntent);
        PendingIntent pee=PendingIntent.getActivity(context,0,newIntent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pee)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
        NotificationManager nm=( NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Toast.makeText(context.getApplicationContext(),"x",Toast.LENGTH_LONG).show();
        //nm.notify(1, notification);
    }
}