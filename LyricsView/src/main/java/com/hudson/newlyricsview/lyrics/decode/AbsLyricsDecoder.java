package com.hudson.newlyricsview.lyrics.decode;

import android.content.Context;

import com.hudson.newlyricsview.lyrics.entity.Lyrics;

import java.util.ArrayList;
import java.util.List;

/**
 * 歌词解析器
 * Created by Hudson on 2018/12/8.
 */
public abstract class AbsLyricsDecoder {
    protected final List<Lyrics> mLyrics = new ArrayList<>();
    protected final List<Long> mTimeList = new ArrayList<>();

    public abstract void decode(Context context, String path);

    public List<Lyrics> getLyrics(){
        return mLyrics;
    }

    public List<Long> getLyricsTimeList(){
        return mTimeList;
    }

}
