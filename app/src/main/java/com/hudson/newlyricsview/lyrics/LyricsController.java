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
    private int mLyricsCount;

    public LyricsController(Context context){
        mContext = context;
        mLyricsDecoder = new NormalLyricsDecoder();
    }

    /**
     * 最好放入异步线程
     * @param path
     */
    public void decodeLyrics(Context context,String path){
        mLyricsDecoder.decode(context,path);
    }

    public void initialLyricsView(long startTime){
        List<AbsLyrics> lyrics = mLyricsDecoder.getLyrics();
        if(lyrics == null || lyrics.size() == 0){
            mLyricsView = new EmptyLyricsView(mContext);
        }else{
            mLyricsView = new RecyclerLyricsView(mContext);
            mLyricsView.setLyrics(lyrics,mLyricsDecoder.getLyricsTimeList(),startTime);
        }
        mLyricsCount = mLyricsView.setLyricsCount(mLyricsCount);
    }

    /**
     * 播放
     */
    public void play(){
        mLyricsView.play();
    }

    /**
     * 从某个位置开始播放
     * @param position
     */
    public void play(int position) {
        mLyricsView.play(position);
    }

    public AbsLyrics getCurLyrics() {
        return mLyricsView.getCurLyrics();
    }

    public void forward(long curProgress,long timeOffset) {
        mLyricsView.forward(curProgress,timeOffset);
    }

    public void backward(long curProgress,long timeOffset) {
        mLyricsView.backward(curProgress,timeOffset);
    }

    public void pause(long time) {
        mLyricsView.pause(time);
    }

    public View getLyricsView(){
        return mLyricsView.getView();
    }

    public void setLyricsCount(int count){
        mLyricsCount = count;
    }
}
