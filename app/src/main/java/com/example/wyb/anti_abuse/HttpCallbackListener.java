package com.example.wyb.anti_abuse;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
