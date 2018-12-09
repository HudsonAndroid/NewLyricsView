package com.hudson.newlyricsview.lyrics;

import android.content.Context;
import android.view.View;

import com.hudson.newlyricsview.lyrics.decode.AbsLyricsDecoder;
import com.hudson.newlyricsview.lyrics.decode.NormalLyricsDecoder;
import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;
import com.hudson.newlyricsview.lyrics.view.EmptyLyricsView;
import com.hudson.newlyricsview.lyrics.view.ILyricsView;
import com.hudson.newlyricsview.lyrics.view.recycler.RecyclerLyricsView;

import java.util.List;

/**
 * 歌词控制器(状态模式)
 * Created by hpz on 2018/12/6.
 */
public class LyricsController{
    private ILyricsView<AbsLyrics> mLyricsView;
    private AbsLyricsDecoder<AbsLyrics> mLyricsDecoder;
    private Context mContext;

    public LyricsController(Context context){
        mContext = context;
        mLyricsDecoder = new NormalLyricsDecoder();
    }

    /**
     * 最好放入异步线程
     * @param path
     */
    public void decodeLyrics(String path){
        mLyricsDecoder.decode(path);
    }

    public void initialLyricsView(){
        List<AbsLyrics> lyrics = mLyricsDecoder.getLyrics();
        if(lyrics == null || lyrics.size() == 0){
            mLyricsView = new EmptyLyricsView(mContext);
        }else{
            mLyricsView = new RecyclerLyricsView(mContext);
            mLyricsView.setLyrics(lyrics,mLyricsDecoder.getLyricsTimeList());
        }
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
