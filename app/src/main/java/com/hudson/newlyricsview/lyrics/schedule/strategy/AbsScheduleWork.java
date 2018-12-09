package com.hudson.newlyricsview.lyrics.schedule.strategy;

import com.hudson.newlyricsview.lyrics.exception.ScheduleInitialStateInvalidException;

import java.util.ArrayList;
import java.util.List;

/**
 * 定期任务
 * Created by hpz on 2018/12/6.
 */
public abstract class AbsScheduleWork {
    protected IScheduleWorkListener mListener;
    protected final List<Long> mTimeList = new ArrayList<>();
    protected int mCurrentIndex;

    public AbsScheduleWork(){
    }

    /**
     * 设置事件序列
     * @param timeList
     */
    public void setTimeList(List<Long> timeList){
        mTimeList.clear();
        mTimeList.addAll(timeList);
    }

    public void setScheduleWorkListener(IScheduleWorkListener listener) {
        mListener = listener;
    }

    public interface IScheduleWorkListener{
        void onNextWork();
    }

    public void start(long startTime){
        if(mTimeList.size() == 0){
            throw new ScheduleInitialStateInvalidException("time list is empty!");
        }
        if(mListener == null){
            throw new ScheduleInitialStateInvalidException("have you ever set the listener of schedule work?");
        }
        int currentIndex = getCurrentIndex(startTime);
        if(currentIndex != -1){
            startSchedule(currentIndex,startTime);
        }
    }

    public abstract void pause();

    protected abstract void startSchedule(int currentIndex,long currentTime);

    /**
     *
     * @param time 返回mTimeList中的Index,返回-1
     *             表示不需要进行计划任务
     * @return
     */
    private int getCurrentIndex(long time){
        for (int i = 0; i < mTimeList.size(); i++) {
            if(mTimeList.get(i) > time){
                return i;
            }
        }
        return -1;
    }

    protected void next(){
        if(mListener != null){
            mListener.onNextWork();
        }
    }

    public int getCurrentIndex(){
        return mCurrentIndex;
    }
}
