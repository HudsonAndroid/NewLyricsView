package com.hudson.newlyricsview.lyrics.schedule;

import com.hudson.newlyricsview.lyrics.schedule.strategy.AbsScheduleWork;
import com.hudson.newlyricsview.lyrics.view.ILyricsView;

import java.util.List;

/**
 * 歌词定期任务（策略模式）
 * 策略模式与状态模式极为相似，本质却是
 * 不同的。策略模式针对的是相同情景使用
 * 不同的算法，状态模式则是针对不同情景
 * 使用不同的规则（行为）。
 * Created by hpz on 2018/12/6.
 */
public class LyricsSchedule implements AbsScheduleWork.IScheduleWorkListener {
    private AbsScheduleWork mScheduleWork;
    private ILyricsView mLyricsView;

    public LyricsSchedule(AbsScheduleWork work,ILyricsView lyricsView){
        mScheduleWork = work;
        mScheduleWork.setScheduleWorkListener(this);
        mLyricsView = lyricsView;
    }

    public void setScheduleTimeList(List<Long> timeList){
        mScheduleWork.setTimeList(timeList);
    }

    @Override
    public void onNextWork(){
        mLyricsView.next();
    }

    public int getCurPosition() {
        return mScheduleWork.getCurrentIndex();
    }

    public void pause(long pauseTime){
        mScheduleWork.pause(pauseTime);
    }

    public void play(long currentTime){
        mScheduleWork.start(currentTime);
    }

    public void setStartTime(long startTime){
        mScheduleWork.setStartTime(startTime);
    }

    public void play(){
        mScheduleWork.start();
    }

    public void end(){
        mScheduleWork.end();
    }

    public boolean isWorkRunning(){
        return mScheduleWork.isRunning();
    }

    public long getNextLyricsTimeOffset(){
        return mScheduleWork.getNextLyricsTimeOffset();
    }
}
