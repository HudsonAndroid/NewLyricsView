package com.hudson.newlyricsview.lyrics.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;

import java.util.List;


/**
 * Created by hpz on 2018/12/7.
 */
public class EmptyLyricsView extends TextView implements ILyricsView<AbsLyrics> {
    public EmptyLyricsView(Context context) {
        this(context, null);
    }

    public EmptyLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLyricsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setText("找不到歌词");
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setLyrics(List<AbsLyrics> lyrics, List<Long> timeList) {

    }

    @Override
    public void play(long currentProgress) {

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

    }

    @Override
    public View getView() {
        return this;
    }
}
