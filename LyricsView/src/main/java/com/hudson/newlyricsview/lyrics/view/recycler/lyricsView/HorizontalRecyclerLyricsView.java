package com.hudson.newlyricsview.lyrics.view.recycler.lyricsView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
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
    protected int getDimension(int w, int h) {
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

    protected void initTrianglePath(int w,int h) {
        mTrianglePath = new Path();
        mTrianglePath.moveTo(w/2 - LOCATE_TRIANGLE_HALF_HEIGHT,0);
        mTrianglePath.lineTo(w/2 + LOCATE_TRIANGLE_HALF_HEIGHT,0);
        mTrianglePath.lineTo(w/2, LOCATE_TRIANGLE_DIMENSION);
        mTrianglePath.close();
        mLocateViewRegion = new RectF(w/2 - LOCATE_TRIANGLE_HALF_HEIGHT,0,w/2 + LOCATE_TRIANGLE_HALF_HEIGHT,h);
    }

    @Override
    protected void drawLocateView(Canvas canvas){
        int x = getWidth() / 2;
        //绘制三角形
        canvas.drawPath(mTrianglePath,mLocatePaint);
        canvas.drawLine(x, 0,x,getHeight(),mLocatePaint);
    }
}
