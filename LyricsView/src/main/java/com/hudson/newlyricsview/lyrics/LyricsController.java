package com.hudson.newlyricsview.lyrics;

import android.content.Context;
import android.view.View;

import com.hudson.newlyricsview.lyrics.decode.AbsLyricsDecoder;
import com.hudson.newlyricsview.lyrics.entity.Lyrics;
import com.hudson.newlyricsview.lyrics.view.ILyricsView;
import com.hudson.newlyricsview.lyrics.view.config.LyricsViewConfig;
import com.hudson.newlyricsview.lyrics.view.locateProgress.ILocateProgressListener;

import java.util.List;

/**
 * 歌词控制器(状态模式)
 * Created by hpz on 2018/12/6.
 */
public class LyricsController{
    private ILyricsView mLyricsView;
    private ILyricsView mNonEmptyLyricsView;
    private LyricsViewConfig mConfig;
    private AbsLyricsDecoder mLyricsDecoder;
    private Context mContext;

    public LyricsController(Context context){
        mContext = context;
    }

    public void init(LyricsViewConfig config){
        mConfig = config;
        mLyricsView = mConfig.getLyricsViewStyle().getLyricsView(mContext);
        mNonEmptyLyricsView = mLyricsView;
        mLyricsDecoder = config.getLyricsDecoder();
        mNonEmptyLyricsView.setLyricsCount(config.getLyricsCount());//内部可能会修改该值
        mNonEmptyLyricsView.setFocusLyricsColor(config.getFocusColor());
        mNonEmptyLyricsView.setNormalLyricsColor(config.getNormalColor());
        mNonEmptyLyricsView.setScheduleType(config.getSchedulePolicy());
        mNonEmptyLyricsView.setTypeface(config.getTypeface());
        mNonEmptyLyricsView.setTextSize(config.getTextSize());
    }

    /**
     * 最好放入异步线程
     * @param path
     */
    public void decodeLyrics(Context context,String path){
        mLyricsDecoder.decode(context,path);
    }

    public void initialLyricsView(long startTime){
        List<Lyrics> lyrics = mLyricsDecoder.getLyrics();
        if(lyrics == null || lyrics.size() == 0){
            mLyricsView = mConfig.getEmptyLyricsView(mContext);
        }else{
            mLyricsView = mNonEmptyLyricsView;
            mLyricsView.setLyrics(lyrics,mLyricsDecoder.getLyricsTimeList(),startTime);
        }
    }

    public void setOnLocateCenterListener(ILocateProgressListener listener){
        mLyricsView.setLocateCenterListener(listener);
    }

    /**
     * 播放
     */
    public void play(){
        mLyricsView.play();
    }

    /**
     * 从某个位置开始播放
     * @param progress
     */
    public void play(long progress) {
        mLyricsView.play(progress);
    }

    public Lyrics getCurLyrics() {
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
}
