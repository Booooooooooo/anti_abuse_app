package com.example.wyb.anti_abuse;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SoundFragment extends Fragment {

    //private GetSounds getSounds;
    private View view;
    private Context context;
    public RecyclerView recyclerView;
    private SoundAdapter soundAdapter;
    private ListView listView;
    private List<SoundItem> soundList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private String[] testdata = {"a", "b", "c", "I hate listveiw", "I hate recyclerview"};
    private final ArrayList<String> array = new ArrayList<String>();

    private final int maxdata = 10;
    private String[] data;
    private int datanum = 0;

    public void setContext(Context c){
        context = c;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sound_layout, container, false);

        //initTest();
        //initSound();
        adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_list_item_1, array
        );
        //SoundListAdapter adapter = new SoundListAdapter(context, R.layout.sound_item_layout, soundList);
        listView = (ListView)view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        //setListViewHeightBaseOnChildren(listView, 30);
        getData();
        return view;
    }

    private void initTest(){
        array.add("wushuju");
        array.add("无数据");
        array.add("去你的");
        array.add("ListView");
    }

    public void initSound(){
        //data = new String[maxdata];
        String address = "http://47.102.151.34:8000/iscry?currentStamp=4534545&startStamp=42524525";
        HttpUtil.sendOkHttpRequest(address, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException{
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
                Log.d("SoundFragment", responseData);
            }

            @Override
            public void onFailure(Call call, IOException e){
                Log.d("SoundFragment", "error3");
            }
        });
    }

    private void getData() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initSound();
                Log.d("SoundFragment", "refreshed");
                handler.postDelayed(this, 2000);
                Collections.reverse(array);
                adapter.notifyDataSetChanged();
                //setListViewHeightBaseOnChildren(listView, 30);
            }
        };
        runnable.run();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONObject jsonObject = new JSONObject(jsonData);
            String currentstamp = jsonObject.getString("currentStamp");
            JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
            String startStamp = jsonObject.getString("startStamp");
            Log.d("SoundFragment", "currentStamp: " + currentstamp);
            Log.d("SoundFragment", "startStamp: " + startStamp);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject dataObject = jsonArray.getJSONObject(i);
                String result = dataObject.getString("result");
                String stamp = dataObject.getString("stamp");
                if(array.size() > maxdata){
                    array.remove(0);
                }
                array.add("日期"+stamp+result+(datanum++));
                Log.d("SoundFragment", "result: " + result);
                Log.d("SoundFragment", "stamp:" + stamp);
            }
        }catch (Exception e){
            Log.d("SoundFragment", "error2");
            e.printStackTrace();
        }
    }

    private void setListViewHeightBaseOnChildren(ListView listview, int maxCount){
        ListAdapter listAdapter = listview.getAdapter();
        if(listAdapter == null){
            return;
        }
        int totalHeight = 0;
        ViewGroup.LayoutParams params = listview.getLayoutParams();
        if(listAdapter.getCount() > maxCount){
            for(int i = 0; i < maxCount; i++){
                View listItem = listAdapter.getView(i, null, listview);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }else{
            for(int i = 0; i < listAdapter.getCount(); i++){
                View listItem = listAdapter.getView(i, null, listview);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            params.height = totalHeight + (listview.getDividerHeight() * (listAdapter.getCount() - 1));
        }
        listview.setLayoutParams(params);
    }

//    private void initRecyclerView(){
//        //recyclerView = (RecyclerView)view.findViewById(R.id.sound_records);
//        soundAdapter = new SoundAdapter(getActivity(), soundList);
//        recyclerView.setAdapter(soundAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//    }



}
