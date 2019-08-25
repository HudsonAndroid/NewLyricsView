package com.hudson.newlyricsview.lyrics.entity;

import android.support.annotation.NonNull;

/**
 * Created by hpz on 2018/12/6.
 */
public class Lyrics implements Comparable<Lyrics>{
    private String mLrcContent;
    private String mLrcTranslate;
    private long mLrcProgressTime;

    public Lyrics() {
    }

    public Lyrics(String lrcContent, long lrcProgressTime) {
        mLrcContent = lrcContent;
        mLrcProgressTime = lrcProgressTime;
    }

    public String getLrcTranslate() {
        return mLrcTranslate;
    }

    public void setLrcTranslate(String lrcTranslate) {
        mLrcTranslate = lrcTranslate;
    }

    public String getLrcContent() {
        return mLrcContent;
    }

    public void setLrcContent(String lrcContent) {
        mLrcContent = lrcContent.trim();
    }

    public long getLrcProgressTime() {
        return mLrcProgressTime;
    }

    public void setLrcProgressTime(long lrcProgressTime) {
        mLrcProgressTime = lrcProgressTime;
    }

    @Override
    public int compareTo(@NonNull Lyrics other) {
        if(mLrcProgressTime != other.mLrcProgressTime){
            return (int) (mLrcProgressTime - other.mLrcProgressTime);
        }
        return 1;//如果相同返回大于.注意我们不能让它相同
    }
}
