package com.hudson.newlyricsview.lyrics.entity;

import android.support.annotation.NonNull;

/**
 * Created by hpz on 2018/12/6.
 */
public class AbsLyrics implements Comparable<AbsLyrics>{
    private String mLrcContent;
    private long mLrcProgressTime;

    public AbsLyrics() {
    }

    public AbsLyrics(String lrcContent, long lrcProgressTime) {
        mLrcContent = lrcContent;
        mLrcProgressTime = lrcProgressTime;
    }

    public String getLrcContent() {
        return mLrcContent;
    }

    public void setLrcContent(String lrcContent) {
        mLrcContent = lrcContent;
    }

    public long getLrcProgressTime() {
        return mLrcProgressTime;
    }

    public void setLrcProgressTime(long lrcProgressTime) {
        mLrcProgressTime = lrcProgressTime;
    }

    @Override
    public int compareTo(@NonNull AbsLyrics other) {
        if(mLrcProgressTime != other.mLrcProgressTime){
            return (int) (mLrcProgressTime - other.mLrcProgressTime);
        }
        return 1;//如果相同返回大于.注意我们不能让它相同
    }
}
