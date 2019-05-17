package com.example.wyb.anti_abuse;


import android.graphics.Color;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MyLineChart {
    private LineChartView lineChartView;
    private int lineNum = 1;
    private int MAX_VIEW_POINT_COUNT = 100;
    private int period = 20;
    private int[] colors = {Color.RED, Color.BLACK, Color.BLUE, Color.YELLOW, Color.GREEN};

    //private OnChartValueSelectListener listener;

    private List<LinkedList<PointValue>> points;
    private List<Line> lines;
    private LineChartData lineChartData;
    private float[] dataSize;

    public MyLineChart(LineChartView lineChartView, int lineNum, int MAX_VIEW_POINT_COUNT, int period, int... colors){
        this.lineChartView = lineChartView;
        if (lineNum > 1){
            this.lineNum = lineNum;
        }
        if (MAX_VIEW_POINT_COUNT > 0){
            this.MAX_VIEW_POINT_COUNT = MAX_VIEW_POINT_COUNT;
        }
        if (period > 20){
            this.period = period;
        }
//        if (listener != null){
//            this.listener = listener;
//        }
        if (colors.length > 0){
            this.colors = colors;
        }

        init();
        handler.postDelayed(runnable,period);
    }

    //每20ms刷新一次，相当于50Hz
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateView();
            handler.postDelayed(runnable,period);
        }
    };

    private void init() {
        lineChartView.setInteractive(false);
        lineChartView.setBackgroundColor(Color.TRANSPARENT);
        lineChartView.setValueTouchEnabled(true);
        lineChartView.setViewportCalculationEnabled(false);//打开就会自动计算y轴范围，现在是人工计算
        lineChartView.setZoomType(ZoomType.HORIZONTAL);

        lineChartData = new LineChartData();
        lineChartData.setAxisYLeft(new Axis().setHasLines(true).setInside(true).setMaxLabelChars(6).setName("y"));
        lineChartData.setAxisXBottom(new Axis().setHasLines(false).setName("x"));
        Axis axisX = new Axis(); //X轴
        //axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        //axisX.setTextColor(Color.BLACK);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        //axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        //axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        lineChartData.setAxisXBottom(axisX); //x 轴在底部  （顶部底部一旦设置就意味着x轴）
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(false); //x 轴分割线  每个x轴上 面有个虚线 与x轴垂直

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        //axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setTextColor(Color.BLACK);
        lineChartData.setAxisYLeft(axisY);  //Y轴设置在左边（左面右面一旦设定就意味着y轴）
        //data.setAxisYRight(axisY);  //y轴设置在右边


        lines = new ArrayList<Line>();
        points = new ArrayList<LinkedList<PointValue>>();
        dataSize = new float[lineNum];
        int j = 0;
        for (int i = 0; i < lineNum; i++) {
            if (j >= colors.length){
                j = 0;
            }
            Line line = new Line();
            line.setColor(colors[j]);
            line.setShape(ValueShape.CIRCLE);
            line.setPointRadius(1);
            line.setStrokeWidth(1);
            line.setCubic(false);
            line.setFilled(false);
            line.setHasLabels(true);
            line.setHasLines(true);
            line.setHasPoints(true);
            lines.add(line);

            LinkedList<PointValue> pointValues = new LinkedList<>();
            points.add(pointValues);
            j++;
        }

        Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.left = 0;
        v.right= 7;
//        v.bottom= 1;
//        v.top= 30;
        lineChartView.setCurrentViewport(v);
    }

    public void add(float[] value) {
        for (int i = 0; i < lineNum; i++){
            LinkedList<PointValue> pointValues = points.get(i);
            pointValues.add(new PointValue(dataSize[i], value[i]));
            if (dataSize[i] > MAX_VIEW_POINT_COUNT){
                pointValues.removeFirst();
            }
            dataSize[i]++;
        }
    }

    private void updateView(){
        List<Float> tmp = new ArrayList<>();
        for (int i  = 0; i < lineNum; i++){
            tmp.add(dataSize[i]);
            LinkedList<PointValue> pointValues = points.get(i);
            Line line = lines.get(i);
            line.setValues(pointValues);
        }
        float size = Collections.max(tmp);
        if (size <= 0){
            return;
        }
        lineChartData.setLines(lines);
        lineChartView.setLineChartData(lineChartData);
        size += MAX_VIEW_POINT_COUNT >> 2;
        float[] y = setChartHeight();
        Viewport viewport;
        if (size > MAX_VIEW_POINT_COUNT){
            viewport = new Viewport(size - MAX_VIEW_POINT_COUNT, y[0], size, y[1]);
        }else {
            viewport = new Viewport(0, y[0], size, y[1]);
        }
        lineChartView.setCurrentViewport(viewport);
        lineChartView.setMaximumViewport(viewport);
    }

    /**
     * 使用两个全局变量来更新y轴上下限，避免数值跳动太大
     */
    private float top;
    private float bottom;
    private float[] setChartHeight(){
        List<Float> tmp = new ArrayList<>();
        for (int i = 0; i < lineNum; i++){
            List<PointValue> pointValues = points.get(i);
            for (PointValue value : pointValues){
                tmp.add(value.getY());
            }
        }
        float max_top = Collections.max(tmp);
        float min_bottom = Collections.min(tmp);

        if (max_top == min_bottom){
            if (max_top == 0) {
                return new float[]{1, -1};
            }else {
                return new float[]{max_top + Math.abs(max_top) * 0.2f,min_bottom - Math.abs(min_bottom) * 0.2f};
            }
        }else {
            if (top == 0){
                top = max_top;
            }else if (top < max_top - Math.abs(max_top) * 0.03f){
                top = top + Math.abs(top - max_top) * 0.03f;
            }else if (top > max_top + Math.abs(max_top) * 0.03f){
                top = top - Math.abs(top - max_top) * 0.03f;
            }else {
                top = max_top;
            }
            if (bottom == 0){
                bottom = min_bottom;
            }else if (bottom > min_bottom + Math.abs(min_bottom * 0.03f)){
                bottom = bottom - Math.abs(bottom - min_bottom) * 0.03f;
            }else if (bottom < min_bottom - Math.abs(min_bottom * 0.03f)){
                bottom = bottom + Math.abs(bottom - min_bottom) * 0.03f;
            }else {
                bottom = min_bottom;
            }
        }
        float y = top - bottom;
        return new float[]{top + y * 0.2f, bottom - y * 0.2f};
    }

//    public interface OnChartValueSelectListener {
//        void onChartValueSelect(int position, int value, PointValue pointValue);
//    }
}


