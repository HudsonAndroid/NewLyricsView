package com.hudson.newlyricsview.lyrics.decode;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌词解析器
 * Created by Hudson on 2018/12/8.
 */
public abstract class AbsLyricsDecoder<T extends AbsLyrics> {
    protected final List<T> mLyrics = new ArrayList<>();
    protected final List<Long> mTimeList = new ArrayList<>();

    public abstract void decode(String path);

    public List<T> getLyrics(){
        return mLyrics;
    }

    public List<Long> getLyricsTimeList(){
        return mTimeList;
    }

}
