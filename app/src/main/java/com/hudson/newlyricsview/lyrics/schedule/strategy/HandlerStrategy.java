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
    public void pause() {
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void startSchedule(int currentIndex, long currentTime) {
        mHandler.removeCallbacksAndMessages(null);
        mCurrentIndex = currentIndex;
        mHandler.sendEmptyMessageDelayed(MSG_NORMAL_UPDATE,mTimeList.get(mCurrentIndex) - currentTime);
    }

    @Override
    protected void next() {
        super.next();
        mCurrentIndex ++;
        if(mCurrentIndex >= mTimeList.size()){
            return ;
        }
        if(mCurrentIndex >= 1){
            mHandler.sendEmptyMessageDelayed(MSG_NORMAL_UPDATE,
                    mTimeList.get(mCurrentIndex) - mTimeList.get(mCurrentIndex-1));
        }else{
            mHandler.sendEmptyMessageDelayed(MSG_NORMAL_UPDATE,mTimeList.get(mCurrentIndex));
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
