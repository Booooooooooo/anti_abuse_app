package com.example.wyb.anti_abuse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

public class UserFragment extends Fragment implements View.OnClickListener{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_layout, container, false);
        RoundImageView user_image = (RoundImageView)view.findViewById(R.id.user_img);
        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendar);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boy);
        user_image.setmBitmap(bitmap);
        Button testButton = (Button)view.findViewById(R.id.test);
        testButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.test:
                NotificationManager manager = (NotificationManager)view.getContext().getSystemService(view.getContext().NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(view.getContext())
                        .setContentTitle("title")
                        .setContentText("text")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .build();
                manager.notify(1, notification);
                break;
        }


    }

}
