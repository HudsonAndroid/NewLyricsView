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
    private boolean mIsRunning = false;
    private long mStartTime = 0;

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

    public void setStartTime(long startTime) {
        mStartTime = startTime;
        mCurrentIndex = getCurrentIndex(startTime);
    }

    public void setScheduleWorkListener(IScheduleWorkListener listener) {
        mListener = listener;
    }

    public interface IScheduleWorkListener{
        void onNextWork();
    }

    /**
     * 从指定位置开始播放
     * @param startTime
     */
    public void start(long startTime){
        mCurrentIndex = getCurrentIndex(startTime);
        commonStart(startTime);
    }

    private void commonStart(long startTime){
        if(mTimeList.size() == 0){
            throw new ScheduleInitialStateInvalidException("time list is empty!");
        }
        if(mListener == null){
            throw new ScheduleInitialStateInvalidException("have you ever set the listener of schedule work?");
        }
        if(mCurrentIndex != mTimeList.size()){
            startSchedule(startTime);
            mIsRunning = true;
        }
    }

    public void start(){
        commonStart(mStartTime);
    }

    /**
     * 子类必须回调父类的pause方法
     * @param pauseTime
     */
    public void pause(long pauseTime){
        mStartTime = pauseTime;
        mIsRunning = false;
    }

    /**
     * 子类必须回调父类的end方法
     */
    public void end(){
        mIsRunning = false;
    }

    protected abstract void startSchedule(long currentTime);

    /**
     * 当前的歌词index,-1表示歌词前奏阶段，size表示歌词后节奏
     * @param time 返回mTimeList中的Index,返回size
     *             表示不需要进行计划任务
     * @return
     */
    public int getCurrentIndex(long time) throws ScheduleInitialStateInvalidException{
        if(mTimeList.size() == 0){
            throw new ScheduleInitialStateInvalidException("time list is empty!");
        }
        for (int i = -1; i < mTimeList.size(); i++) {
            if(mTimeList.get(i+1) > time){
                return i;
            }
        }
        return mTimeList.size();
    }

    protected void next(){
        if(mListener != null){
            mListener.onNextWork();
        }
    }

    public int getCurrentIndex(){
        return mCurrentIndex;
    }

    /**
     * 当前的定时任务是否正在进行
     * @return
     */
    public boolean isRunning() {
        return mIsRunning;
    }

    /**
     * 获取下句歌词播放的时间
     * @return
     */
    public long getNextLyricsTimeOffset(){
        if(mCurrentIndex >= 0 && mCurrentIndex < mTimeList.size()-1){
            return mTimeList.get(mCurrentIndex+1)-mTimeList.get(mCurrentIndex);
        }
        if(mCurrentIndex == -1){
            return mTimeList.get(0);
        }
        return Long.MAX_VALUE;
    }

}
