package com.hudson.newlyricsview.lyrics.view;

import android.view.View;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;

import java.util.List;


/**
 * Created by hpz on 2018/12/6.
 */
public interface ILyricsView<T extends AbsLyrics> {

    void setLyrics(List<T> lyrics,List<Long> timeList);

    /**
     * 播放歌词
     * @param currentProgress
     */
    void play(long currentProgress);

    /**
     * 准备状态
     */
    void initial();

    /**
     * 获取当前歌词
     * @return
     */
    T getCurLyrics();

    /**
     * 快进
     * @param timeOffset
     */
    void forward(long timeOffset);

    /**
     * 快退
     * @param timeOffset
     */
    void backward(long timeOffset);

    /**
     * 下一句
     */
    void next();

    /**
     * 暂停
     */
    void pause();

    /**
     * 获取布局
     * @return
     */
    View getView();
}
