package com.example.wyb.anti_abuse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

public class UserFragment extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_layout, container, false);
        RoundImageView user_image = (RoundImageView)view.findViewById(R.id.user_img);
        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendar);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boy);
        user_image.setmBitmap(bitmap);

        return view;
    }

}
