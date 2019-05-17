package com.example.wyb.anti_abuse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        myFragmentPagerAdapter.setSoundContext(this);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bindView();
        disableShiftMode();

    }
    public void simpleNotification(){
        //        获取NotificationManager实例
        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //        构造Notification.Builder 对象
        NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);

        //        设置Notification图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(myIcon);
        //        设置Notification tickertext
        builder.setTicker("A new Message");
        //        设置通知的题目
        builder.setContentTitle("A new notification");
        //        设置通知的内容
        builder.setContentText("This is content text");
        builder.setContentInfo("Info");
        //        设置通知可以被自动取消
        builder.setAutoCancel(true);
        //        设置通知栏显示的Notification按时间排序
        builder.setWhen(System.currentTimeMillis());
        //        设置其他物理属性，包括通知提示音、震动、屏幕下方LED灯闪烁
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));//这里设置一个本地文件为提示音
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setLights(Color.RED,0,1);
        //        设置该通知点击后将要启动的Intent,这里需要注意PendingIntent的用法,构造方法中的四个参数(context,int requestCode,Intent,int flags);
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(MainActivity.this,0,intent,0);
        builder.setContentIntent(pi);

        //        实例化Notification

        Notification notification=builder.build();//notify(int id,notification对象);id用来标示每个notification
        manager.notify(1,notification);


    }


    private void bindView(){
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //点击BottomNavigationView的Item项，切换ViewPager页面
            //menu/navigation.xml里加的android:orderInCategory属性就是下面item.getOrder()取的值
            viewPager.setCurrentItem(item.getOrder());
            return true;
        }

    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //页面滑动的时候，改变BottomNavigationView的Item高亮
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void disableShiftMode(){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)bottomNavigationView.getChildAt(0);
        try{
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for(int i = 0; i < menuView.getChildCount(); i++){
                BottomNavigationItemView item = (BottomNavigationItemView)menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        }catch (NoSuchFieldException e){
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        }catch(IllegalAccessException e){
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}
