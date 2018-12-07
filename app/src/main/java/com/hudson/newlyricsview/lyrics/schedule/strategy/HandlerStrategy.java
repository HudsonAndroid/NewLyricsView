package com.hudson.newlyricsview.lyrics.schedule.strategy;


import android.os.Handler;

/**
 * Created by hpz on 2018/12/6.
 */
public class HandlerStrategy extends AbsScheduleWork {
    private Handler mHandler = new Handler();

    public HandlerStrategy(long initialOffset) {
        super(initialOffset);
    }

    @Override
    public void start() {

    }
}
