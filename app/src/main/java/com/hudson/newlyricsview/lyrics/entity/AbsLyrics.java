package com.hudson.newlyricsview.lyrics.entity;

/**
 * Created by hpz on 2018/12/6.
 */
public class AbsLyrics {
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
}
