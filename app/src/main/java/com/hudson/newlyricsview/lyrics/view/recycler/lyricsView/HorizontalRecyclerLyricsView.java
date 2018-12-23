package com.hudson.newlyricsview.lyrics.view.recycler.lyricsView;

import android.content.Context;
import android.util.AttributeSet;

import com.hudson.newlyricsview.lyrics.view.recycler.adapter.HorizontalLyricsAdapter;
import com.hudson.newlyricsview.lyrics.view.recycler.adapter.LyricsAdapter;

/**
 * Created by Hudson on 2018/12/23.
 */
public class HorizontalRecyclerLyricsView extends RecyclerLyricsView {
    public HorizontalRecyclerLyricsView(Context context) {
        this(context, null);
    }

    public HorizontalRecyclerLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalRecyclerLyricsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getOrientation() {
        return HORIZONTAL;
    }

    @Override
    protected int getTotalDimension(int w, int h) {
        return w;
    }

    @Override
    protected LyricsAdapter getLyricsAdapter() {
        return new HorizontalLyricsAdapter(getContext());
    }

    @Override
    protected void smoothScrollToNext() {
        smoothScrollBy(mItemDimension + mScrollExtend, 0);
    }
}
