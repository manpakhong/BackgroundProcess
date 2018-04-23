package com.rabbitforever.backgroundprocess.threads;

import android.os.Handler;

import java.util.TimerTask;

public class SmallDelay extends TimerTask {
    Handler handler;
    public SmallDelay(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run() {
        handler.sendEmptyMessage(0);
    }
}
