package com.example.wyb.anti_abuse;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import okhttp3.Call;
import okhttp3.Response;

public class ShowLineChart {

    private LineChartView lineChart;
    String[] date = {"10-22", "11-22", "12-22"};
    int[] score = {50, 42, 90};
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    List<Line> lines;
    private String type;
    private long lastTime = 1557060389;
    private long nowTime = 1557822776;

    public ShowLineChart(LineChartView _lineChart, String type){
        lineChart = _lineChart;
        this.type = type;
        getAxisXLabels();
        getAxisPoints();
        initLineChart();
        getData();
    }

    public void getAxisXLabels(){
        for(int i = 0; i < date.length; i++){
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }

    public void getAxisPoints(){
        for(int i = 0; i < score.length; i++){
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    public void get(){
        String address = "http://47.102.151.34:8000/"+type+"?currentStamp=" +nowTime + "&startStamp=" +lastTime;
        HttpUtil.sendOkHttpRequest(address, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
                Log.d(type, responseData);
            }

            @Override
            public void onFailure(Call call, IOException e){
                e.printStackTrace();
                Log.d(type, "error3");
            }
        });
    }

    private void getData() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lastTime = nowTime;
                nowTime = lastTime + 5;
                //normal = true;
                get();
                Log.d(type, "refreshed");

                handler.postDelayed(this, 5000);
                //lineChart.setLineChartData();
//                Collections.reverse(array);
//                adapter.notifyDataSetChanged();
                //view.notifyAll();
                //setListViewHeightBaseOnChildren(listView, 30);
            }
        };
        runnable.run();
    }




    private void dynamic() {
        mPointValues.add(new PointValue(mPointValues.size(), 10));
        //根据新的点的集合画出新的线
        Line line = new Line(mPointValues);
        line.setColor(Color.parseColor("#FFCD41"));//设置折线颜色
        line.setShape(ValueShape.CIRCLE);//设置折线图上数据点形状为 圆形 （共有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，true是平滑曲线，false是折线
        line.setHasLabels(true);//数据是否有标注
//        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据,设置了line.setHasLabels(true);之后点击无效
        line.setHasLines(true);//是否用线显示，如果为false则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 ，如果为false则没有原点只有点显示（每个数据点都是个大圆点）
        lines.add(line);
        LineChartData lineChartData = new LineChartData(lines);
//        lineChartData.setAxisYLeft(axisY);//设置Y轴在左
//        lineChartData.setAxisXBottom(axisX);//X轴在底部
        lineChart.setLineChartData(lineChartData);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
//        float xAxisValue = mPointValues.get(position).getX();
//        //根据点的横坐标实时变换X坐标轴的视图范围
//        Viewport port;
//        if(xAxisValue > 10){
//            port = initViewPort(xAxisValue - 10,xAxisValue);
//        }
//        else {
//            port = initViewPort(0,10);
//        }
//        lineChartView.setMaximumViewport(port);
//        lineChartView.setCurrentViewport(port);
//
//        position++;
//
//        if(position > points.size()-1) {
//            isFinish = true;
//        }

    }
    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String currentstamp = jsonObject.getString("currentStamp");
            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            String startStamp = jsonObject.getString("startStamp");
            Log.d(type, "currentStamp: " + currentstamp);
            Log.d(type, "startStamp: " + startStamp);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject dataObject = jsonArray.getJSONObject(i);
                String result = dataObject.getString("state");
                long stamp = Long.parseLong(dataObject.getString("stamp"));
                Date date = new Date(stamp * 1000L);
                Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                calendar.setTime(date);
                if(result != "0.0"){
                    //array.add(calendar.getTime() + "异常");
                }
                dynamic();
//                getAxisPoints();
//                getAxisXLabels();
//                initLineChart();
                Log.d(type, "result: " + result);
                Log.d(type, "stamp:" + stamp);
            }
        }catch (Exception e){
            Log.d(type, "error2");
            e.printStackTrace();
        }
    }


    public void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.BLUE);
        lines = new ArrayList<Line>();
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
    private Viewport initViewPort(float left,float right) {
        Viewport port = new Viewport();
        port.top = 100;//Y轴上限，固定(不固定上下限的话，Y轴坐标值可自适应变化)
        port.bottom = 0;//Y轴下限，固定
        port.left = left;//X轴左边界，变化
        port.right = right;//X轴右边界，变化
        return port;
    }

}
