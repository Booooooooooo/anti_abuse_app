package com.example.wyb.anti_abuse;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class HeartFragment extends Fragment {
    private View view;
    private MyLineChart lineChart;
    private String type = "heart";
    private long lastTime = 1557560309;
    private long nowTime = 1557560309 + 10;
    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.heart_layout, container, false);
        //showLineChart = new ShowLineChart((LineChartView)view.findViewById(R.id.heart_chart), "heart");
        lineChart = new MyLineChart((LineChartView)view.findViewById(R.id.heart_chart), 1, 10, 1000, Color.BLUE);
        textView = view.findViewById(R.id.heart_text);
        //get();
        getData();

        return view;
    }


    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String currentstamp = jsonObject.getString("currentStamp");
            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            String startStamp = jsonObject.getString("startStamp");
            Log.d(type, "currentStamp: " + currentstamp);
            Log.d(type, "startStamp: " + startStamp);
//            float[] arr = new float[100];
//            int tmp = 0;
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject dataObject = jsonArray.getJSONObject(i);
                String result = dataObject.getString("state");
                String avg = dataObject.getString("heart");
                long stamp = Long.parseLong(dataObject.getString("stamp"));
                Date date = new Date(stamp * 1000L);
                Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                calendar.setTime(date);
                float arr[] = {Float.parseFloat(avg)};
                lineChart.add(arr);//arr[(tmp++)] = Float.parseFloat(avg);
                Log.d("heartavg", avg);
                if(result != "0.0"){
                   textView.setText("异常");
                    //lineChart.add();//array.add(calendar.getTime() + "异常");
                }
                else{
                    textView.setText("正常");
                }

                Log.d(type, "result: " + result);
                Log.d(type, "stamp:" + stamp);
            }

        }catch (Exception e){
            Log.d(type, "error2");
            e.printStackTrace();
        }
    }
    public void get(){
        String address = "http://47.102.151.34:8000/" + type + "?currentStamp=" +nowTime + "&startStamp=" +lastTime;
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

    //int tmp = 0;
    private void getData() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                //normal = true;
                get();
                Log.d(type, "refreshed");

                handler.postDelayed(this, 5000);
                lastTime = nowTime;
                nowTime = lastTime + 5;
                //lineChart.setLineChartData();
//                Collections.reverse(array);
//                adapter.notifyDataSetChanged();
                //view.notifyAll();
                //setListViewHeightBaseOnChildren(listView, 30);
            }
        };
        runnable.run();
    }

}
