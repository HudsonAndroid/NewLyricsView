package com.hudson.newlyricsview.lyrics.view.style;

import android.content.Context;

import com.hudson.newlyricsview.lyrics.view.ILyricsView;
import com.hudson.newlyricsview.lyrics.view.recycler.lyricsView.HorizontalRecyclerLyricsView;
import com.hudson.newlyricsview.lyrics.view.recycler.lyricsView.RecyclerLyricsView;

/**
 * Created by Hudson on 2018/12/23.
 */
public enum LyricsViewStyle implements ILyricsStyle{
    /**
     * 普通传统竖直滚动方式歌词
     */
    VerticalNormalStyle{
        @Override
        public ILyricsView getLyricsView(Context activityContext) {
            return new RecyclerLyricsView(activityContext);
        }
    },
    /**
     * 水平滚动歌词，仅限用于中国传统风格,
     * 不支持翻译歌词内容
     */
    HorizontalStyle{
        @Override
        public ILyricsView getLyricsView(Context activityContext) {
            return new HorizontalRecyclerLyricsView(activityContext);
        }
    }
}
