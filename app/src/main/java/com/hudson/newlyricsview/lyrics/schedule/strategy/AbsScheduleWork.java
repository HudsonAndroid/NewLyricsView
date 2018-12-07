package com.hudson.newlyricsview.lyrics.schedule.strategy;

/**
 * 定期任务
 * Created by hpz on 2018/12/6.
 */
public abstract class AbsScheduleWork {
    protected IScheduleWorkListener mListener;
    protected long mInitialOffsetTime;

    public AbsScheduleWork(long initialOffset){
        mInitialOffsetTime = initialOffset;
    }

    public void setScheduleWorkListener(IScheduleWorkListener listener) {
        mListener = listener;
    }

    public interface IScheduleWorkListener{
        void onNextWork();
    }

    public abstract void start();
}
