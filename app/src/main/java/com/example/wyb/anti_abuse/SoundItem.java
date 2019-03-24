package com.example.wyb.anti_abuse;

public class SoundItem {
    private String date;
    private String time;
    private String play = "播放";

    public SoundItem(){
        date = "无";
        time = "";
        play = "";
    }
    public SoundItem(String _date, String _time){
        date = _date;
        time = _time;
        play = "播放";
    }

    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public String getButton(){
        return play;
    }
}
