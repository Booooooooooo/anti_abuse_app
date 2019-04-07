package com.example.wyb.anti_abuse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import javax.xml.transform.Templates;

public class SoundListAdapter extends ArrayAdapter<SoundItem> {
    private int resourceId;

    public SoundListAdapter(Context context, int textViewResourceId, List<SoundItem> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SoundItem sound = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView date = (TextView)view.findViewById(R.id.date_txt);
        TextView time = (TextView)view.findViewById(R.id.time_txt);
        TextView result = (TextView)view.findViewById(R.id.result);
        date.setText(sound.getDate());
        time.setText(sound.getTime());
        result.setText(sound.getResult());
        return view;
    }


}
