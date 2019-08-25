package com.hudson.newlyricsview.lyrics.schedule.strategy;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by hpz on 2018/12/6.
 */
public class HandlerStrategy extends AbsScheduleWork {
    private MyScheduleHandler mHandler;
    private static final int MSG_NORMAL_UPDATE = 0;

    public HandlerStrategy() {
        super();
        mHandler = new MyScheduleHandler(this);
    }

    @Override
    public void pause(long pauseTime) {
        super.pause(pauseTime);
        end();
    }

    @Override
    public void end() {
        super.end();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void startSchedule(long currentTime) {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(MSG_NORMAL_UPDATE,mTimeList.get(mCurrentIndex+1) - currentTime);
    }

    @Override
    protected void next() {
        mCurrentIndex ++;
        super.next();
        int size = mTimeList.size();
        if(mCurrentIndex >= size){
            return ;
        }
        if(mCurrentIndex >= 0 && (mCurrentIndex + 1)<size){
            mHandler.sendEmptyMessageDelayed(MSG_NORMAL_UPDATE,
                    mTimeList.get(mCurrentIndex+1) - mTimeList.get(mCurrentIndex));
        }else{
            end();
        }
    }

    private static class MyScheduleHandler extends Handler{
        private WeakReference<HandlerStrategy> mScheduleWork;

        MyScheduleHandler(HandlerStrategy work){
            mScheduleWork = new WeakReference<>(work);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerStrategy scheduleWork = mScheduleWork.get();
            if(scheduleWork != null){
                switch (msg.what){
                    case MSG_NORMAL_UPDATE:
                        scheduleWork.next();
                        break;
                }
            }
        }
    }
}
