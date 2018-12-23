package com.hudson.newlyricsview.lyrics.view.config;

import android.content.Context;
import android.graphics.Typeface;

import com.hudson.newlyricsview.lyrics.decode.AbsLyricsDecoder;
import com.hudson.newlyricsview.lyrics.decode.NormalLyricsDecoder;
import com.hudson.newlyricsview.lyrics.schedule.strategy.AbsScheduleWork;
import com.hudson.newlyricsview.lyrics.schedule.strategy.HandlerStrategy;
import com.hudson.newlyricsview.lyrics.view.EmptyLyricsView;
import com.hudson.newlyricsview.lyrics.view.ILyricsView;
import com.hudson.newlyricsview.lyrics.view.style.LyricsViewStyle;

/**
 * 歌词配置器
 * Created by Hudson on 2018/12/23.
 */
public class LyricsViewConfig {
    int lyricsCount;
    int foucsColor;
    int normalColor;
    AbsScheduleWork schedulePolicy = new HandlerStrategy();
    LyricsViewStyle lyricsViewStyle = LyricsViewStyle.VerticalNormalStyle;
    AbsLyricsDecoder lyricsDecoder = new NormalLyricsDecoder();
    ILyricsView emptyLyricsView;
    Typeface typeface = Typeface.DEFAULT;

    /**
     * 注意内部可能会修改传入的值，使其变为奇数
     * @param lyricsCount
     * @return
     */
    public LyricsViewConfig setLyricsCount(int lyricsCount) {
        this.lyricsCount = lyricsCount;
        return this;
    }

    public LyricsViewConfig setFoucsColor(int foucsColor) {
        this.foucsColor = foucsColor;
        return this;
    }

    public LyricsViewConfig setNormalColor(int normalColor) {
        this.normalColor = normalColor;
        return this;
    }

    public LyricsViewConfig setSchedulePolicy(AbsScheduleWork schedulePolicy) {
        this.schedulePolicy = schedulePolicy;
        return this;
    }

    public LyricsViewConfig setLyricsViewStyle(LyricsViewStyle lyricsViewStyle) {
        this.lyricsViewStyle = lyricsViewStyle;
        return this;
    }

    public LyricsViewConfig setLyricsDecoder(AbsLyricsDecoder lyricsDecoder) {
        this.lyricsDecoder = lyricsDecoder;
        return this;
    }

    public LyricsViewConfig setEmptyLyricsView(ILyricsView emptyLyricsView){
        this.emptyLyricsView = emptyLyricsView;
        return this;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public ILyricsView getEmptyLyricsView(Context activityContext){
        if(emptyLyricsView == null){
            emptyLyricsView = new EmptyLyricsView(activityContext);
        }
        return emptyLyricsView;
    }

    public AbsLyricsDecoder getLyricsDecoder() {
        return lyricsDecoder;
    }

    public int getLyricsCount() {
        return lyricsCount;
    }

    public int getFoucsColor() {
        return foucsColor;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public AbsScheduleWork getSchedulePolicy() {
        return schedulePolicy;
    }

    public LyricsViewStyle getLyricsViewStyle() {
        return lyricsViewStyle;
    }
}
