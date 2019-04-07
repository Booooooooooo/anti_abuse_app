package com.example.wyb.anti_abuse;

public class SoundItem {
    private String date;
    private String time;
    private String result;
    private String play = "播放";

    public SoundItem(){
        date = "无";
        time = "";
        result = "";
        play = "";
    }
    public SoundItem(String _date, String _time, String _result){
        date = _date;
        time = _time;
        result = _result;
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
    public String getResult(){ return result; }
}
