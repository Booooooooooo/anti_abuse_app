package com.example.wyb.anti_abuse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class Notice {
    private int id = 1;
    public void notification(View view, String string){
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), R.drawable.girl);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(view.getContext());
        mBuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
        mBuilder.setLargeIcon(bitmap);
        mBuilder.setContentTitle("防虐待智能设备");
        mBuilder.setContentText("异常！");
        mBuilder.setSubText(string);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentInfo("Info");
        mBuilder.setNumber(2);
        mBuilder.setTicker("在状态栏上显示的文本");
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setOngoing(true);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setVibrate(new long[]{0, 1000, 1000, 1000});

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(view.getContext(), 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) view.getContext().getSystemService(view.getContext().NOTIFICATION_SERVICE);
        notificationManager.notify(id++, mBuilder.build());
    }
}
