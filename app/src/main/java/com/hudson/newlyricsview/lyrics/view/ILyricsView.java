package com.hudson.newlyricsview.lyrics.view;

import android.view.View;

import com.hudson.newlyricsview.lyrics.entity.Lyrics;
import com.hudson.newlyricsview.lyrics.schedule.strategy.AbsScheduleWork;

import java.util.List;


/**
 * Created by hpz on 2018/12/6.
 */
public interface ILyricsView {

    void setLyrics(List<Lyrics> lyrics,List<Long> timeList,long startTime);

    /**
     * 设置歌词个数，可能会被LyricsView修改
     * @param count 个数
     * @return 实际LyricsView的歌词个数
     */
    int setLyricsCount(int count);

    /**
     * 设置定时任务的方式
     * @param scheduleWork
     */
    void setScheduleType(AbsScheduleWork scheduleWork);

    /**
     * 设置播放歌词颜色
     * @param color
     */
    void setFocusLyricsColor(int color);

    /**
     * 设置未播放歌词颜色
     * @param color
     */
    void setNormalLyricsColor(int color);

    /**
     * 从某个位置开始播放歌词
     * @param currentProgress
     */
    void play(long currentProgress);

    /**
     * 播放
     */
    void play();

    /**
     * 获取当前歌词
     * @return
     */
    Lyrics getCurLyrics();

    /**
     * 快进
     * @param currentProgress
     * @param timeOffset
     */
    void forward(long currentProgress,long timeOffset);

    /**
     * 快退
     * @param currentProgress
     * @param timeOffset
     */
    void backward(long currentProgress,long timeOffset);

    /**
     * 下一句
     */
    void next();

    /**
     * 暂停
     */
    void pause(long pauseTime);

    /**
     * 获取布局
     * @return
     */
    View getView();
}
