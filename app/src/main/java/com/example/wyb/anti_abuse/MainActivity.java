package com.example.wyb.anti_abuse;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private LineChartView lineChart;
    String[] date = {"10-22", "11-22", "12-22", "1-22", "6-22", "5-23", "5-22", "6-22", "5-23", "5-22"};
    int[] score = {50, 42, 90, 33, 10, 74, 22, 18, 79, 20};
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    private UserFragment userFragment = new UserFragment();
    private CoreFragment coreFragment = new CoreFragment();
    private SoundFragment soundFragment = new SoundFragment();
    private HeartFragment heartFragment = new HeartFragment();
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //添加viewPager事件监听（很容易忘）
        viewPager.addOnPageChangeListener(this);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return coreFragment;
                    case 1:
                        return heartFragment;
                    case 2:
                        return soundFragment;
                    case 3:
                        return userFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });

//        lineChart = (LineChartView)findViewById(R.id.line_chart);
//        getAxisXLabels();
//        getAxisPoints();
//        initLineChart();
//
//        RoundImageView user_image(RoundImageView)findViewById(R.id.user_img);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user);
//        user_image.setmBitmap(bitmap);
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

    private void getAxisXLabels(){
        for(int i = 0; i < date.length; i++){
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    private void getAxisPoints(){
        for(int i = 0; i < score.length; i++){
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.BLUE);
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//数据点形状
        line.setCubic(false);//曲线是否光滑
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        //line.setHasLabelsOnlyForSelected(true);点击数据坐标提示数据
        line.setHasLines(true);//是否用线显示
        line.setHasPoints(true);//是否显示圆点
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true);//X坐标轴字体是斜的
        axisX.setTextColor(Color.BLACK);
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(8);//x轴上最多几个数据
        axisX.setValues(mAxisXValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);//x轴分割线

        Axis axisY = new Axis();
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);
        data.setAxisYLeft(axisY);

        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float)2);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }


}
