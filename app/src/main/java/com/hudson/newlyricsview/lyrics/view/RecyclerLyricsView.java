package com.hudson.newlyricsview.lyrics.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;
import com.hudson.newlyricsview.lyrics.schedule.LyricsSchedule;
import com.hudson.newlyricsview.lyrics.schedule.strategy.HandlerStrategy;

/**
 * Created by hpz on 2018/12/6.
 */
public class RecyclerLyricsView extends RecyclerView implements ILyricsView<AbsLyrics> {

    private LyricsSchedule mLyricsSchedule;

    public RecyclerLyricsView(Context context) {
        this(context, null);
    }

    public RecyclerLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerLyricsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLyricsSchedule = new LyricsSchedule(new HandlerStrategy(0));
    }

    @Override
    public void play(int position) {
        mLyricsSchedule.play();
    }

    @Override
    public void initial() {

    }

    @Override
    public AbsLyrics getCurLyrics() {
        return null;
    }

    @Override
    public void forward(long timeOffset) {

    }

    @Override
    public void backward(long timeOffset) {

    }

    @Override
    public void next() {

    }

    @Override
    public void pause() {
        mLyricsSchedule.pause();
    }

    @Override
    public View getView() {
        return this;
    }
}
