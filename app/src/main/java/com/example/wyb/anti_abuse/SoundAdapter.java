package com.example.wyb.anti_abuse;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {
    private List<SoundItem> mSoundList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateText;
        TextView timeText;
        Button playButton;

        public ViewHolder(View view){
            super(view);
            dateText = (TextView)view.findViewById(R.id.date_txt);
            timeText = (TextView)view.findViewById(R.id.time_txt);
            playButton = (Button)view.findViewById(R.id.play);
        }
    }

    public SoundAdapter(List<SoundItem> soundList){
        mSoundList = soundList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        SoundItem sound = mSoundList.get(position);
        holder.dateText.setText(sound.getDate());
        holder.timeText.setText(sound.getTime());
        holder.playButton.setText(sound.getButton());
    }

    @Override
    public int getItemCount(){
        return mSoundList.size();
    }
}
