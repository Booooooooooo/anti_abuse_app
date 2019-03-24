package com.example.wyb.anti_abuse;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SoundFragment extends Fragment {

    private GetSounds getSounds;
    private View view;
    private Context context;

    public void setContext(Context c){
        context = c;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sound_layout, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.sound_records);
        getSounds = new GetSounds(recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

}
