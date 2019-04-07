package com.example.wyb.anti_abuse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ConcurrentModificationException;
import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {
    private List<SoundItem> mSoundList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateText;
        TextView timeText;
        TextView result;
        //Button playButton;

        public ViewHolder(View view){
            super(view);
            dateText = (TextView)view.findViewById(R.id.date_txt);
            timeText = (TextView)view.findViewById(R.id.time_txt);
            result = (TextView)view.findViewById(R.id.result);
            //playButton = (Button)view.findViewById(R.id.play);
        }
    }

    public SoundAdapter(Context context, List<SoundItem> soundList){
        this.context = context;
        mSoundList = soundList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = View.inflate(context, R.layout.sound_item_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        SoundItem sound = mSoundList.get(position);
        holder.dateText.setText(sound.getDate());
        holder.timeText.setText(sound.getTime());
        holder.result.setText(sound.getResult());
        //holder.playButton.setText(sound.getButton());
    }

    @Override
    public int getItemCount(){
        return mSoundList.size();
    }
}
