package com.hudson.newlyricsview.lyrics;

import android.content.Context;
import android.view.View;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;
import com.hudson.newlyricsview.lyrics.view.EmptyLyricsView;
import com.hudson.newlyricsview.lyrics.view.ILyricsView;
import com.hudson.newlyricsview.lyrics.view.RecyclerLyricsView;

import java.util.List;

/**
 * 歌词控制器(状态模式)
 * Created by hpz on 2018/12/6.
 */
public class LyricsController{
    private ILyricsView<AbsLyrics> mLyricsView;

    public LyricsController(String path, Context context){
        List<? extends AbsLyrics> lyrics = initLyrics(path);
        if(lyrics == null || lyrics.size() == 0){
            mLyricsView = new EmptyLyricsView(context);
        }else{
            mLyricsView = new RecyclerLyricsView(context);
        }
    }

    private List<? extends AbsLyrics> initLyrics(String path){
        return null;
    }
    
    public void play(int position) {
        mLyricsView.play(position);
    }
    
    public void initial() {
        mLyricsView.initial();
    }

    public AbsLyrics getCurLyrics() {
        return mLyricsView.getCurLyrics();
    }

    public void forward(long timeOffset) {
        mLyricsView.forward(timeOffset);
    }

    public void backward(long timeOffset) {
        mLyricsView.backward(timeOffset);
    }

    public void next() {
        mLyricsView.next();
    }

    public void pause() {
        mLyricsView.pause();
    }

    public View getLyricsView(){
        return mLyricsView.getView();
    }
}
