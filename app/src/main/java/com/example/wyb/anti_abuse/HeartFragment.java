package com.example.wyb.anti_abuse;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class HeartFragment extends Fragment {
    private View view;
    private ShowLineChart showLineChart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.heart_layout, container, false);
        showLineChart = new ShowLineChart((LineChartView)view.findViewById(R.id.heart_chart));
        return view;
    }

}
