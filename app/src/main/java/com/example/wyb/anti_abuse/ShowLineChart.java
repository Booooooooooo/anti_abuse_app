package com.example.wyb.anti_abuse;

import android.graphics.Color;
import android.view.View;

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

public class ShowLineChart {

    private LineChartView lineChart;
    String[] date = {"10-22", "11-22", "12-22", "1-22", "6-22", "5-23", "5-22", "6-22", "5-23", "5-22"};
    int[] score = {50, 42, 90, 33, 10, 74, 22, 18, 79, 20};
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    public ShowLineChart(LineChartView _lineChart){
        lineChart = _lineChart;
        getAxisXLabels();
        getAxisPoints();
        initLineChart();
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

    public void initLineChart(){
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
