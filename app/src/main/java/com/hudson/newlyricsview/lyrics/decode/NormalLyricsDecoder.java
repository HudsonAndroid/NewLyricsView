package com.hudson.newlyricsview.lyrics.decode;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;

/**
 * Created by Hudson on 2018/12/8.
 */
public class NormalLyricsDecoder extends AbsLyricsDecoder<AbsLyrics> {

    @Override
    public void decode(String path) {
        for (int i = 0; i < 30; i++) {
            long lrcProgressTime = (i + 1) * 1000;
            mLyrics.add(new AbsLyrics("我是歌词"+i, lrcProgressTime));
            mTimeList.add(lrcProgressTime);
        }
    }
}
