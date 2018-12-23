package com.hudson.newlyricsview.lyrics.view.style;

import android.content.Context;

import com.hudson.newlyricsview.lyrics.view.ILyricsView;
import com.hudson.newlyricsview.lyrics.view.recycler.RecyclerLyricsView;

/**
 * Created by Hudson on 2018/12/23.
 */
public enum LyricsViewStyle implements ILyricsStyle{
    VerticalNormalStyle{
        @Override
        public ILyricsView getLyricsView(Context activityContext) {
            return new RecyclerLyricsView(activityContext);
        }
    }

}
