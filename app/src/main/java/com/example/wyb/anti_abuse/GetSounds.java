package com.example.wyb.anti_abuse;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GetSounds {
    private List<SoundItem> soundList = new ArrayList<>();

    public GetSounds(RecyclerView recyclerView){
        SoundAdapter adapter = new SoundAdapter(soundList);
        recyclerView.setAdapter(adapter);
    }

    private void initSound(){
        soundList.add(new SoundItem("2019-03-22", "17:00"));
        soundList.add(new SoundItem("2019-03-22", "17:32"));
        soundList.add(new SoundItem());
        soundList.add(new SoundItem());
    }
}
