package com.hudson.newlyricsview.lyrics.schedule;

import com.hudson.newlyricsview.lyrics.schedule.strategy.AbsScheduleWork;

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
    private int mCurPosition = 0;
    private int mMaxPosition;

    public LyricsSchedule(AbsScheduleWork work){
        mScheduleWork = work;
        mScheduleWork.setScheduleWorkListener(this);
    }

    @Override
    public void onNextWork() {
        mCurPosition ++;
    }

    public int getCurPosition() {
        return mCurPosition;
    }

    public void pause(){

    }

    public void play(){
        mScheduleWork.start();
    }
}
