package com.hudson.newlyricsview.lyrics.view.recycler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;
import com.hudson.newlyricsview.lyrics.schedule.LyricsSchedule;
import com.hudson.newlyricsview.lyrics.schedule.strategy.HandlerStrategy;
import com.hudson.newlyricsview.lyrics.view.ILyricsView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpz on 2018/12/6.
 */
public class RecyclerLyricsView extends RecyclerView implements ILyricsView<AbsLyrics> {
    private static final int USER_INFECTION_TIME = 3000;
    private static final int MSG_ADJUST_LYRICS = 1;
    private int mItemHeight = 100;//px
    private LyricsSchedule mLyricsSchedule;
    private final List<AbsLyrics> mLyrics = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private LyricsAdapter mAdapter;
    private boolean mIsUserActive = false;
    private boolean mIsInterrupt = false;
    private LyricsViewHandler mHandler;

    public RecyclerLyricsView(Context context) {
        this(context, null);
    }

    public RecyclerLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerLyricsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLyricsSchedule = new LyricsSchedule(new HandlerStrategy(),this);
        mLayoutManager = new CenterLayoutManager(getContext());
        mLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mLayoutManager);
        mAdapter = new LyricsAdapter(getContext());
        mHandler = new LyricsViewHandler(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mAdapter.setEmptyViewHeight((h + mItemHeight)/2,(h - mItemHeight)/2);
//        mCount = h/mItemHeight;
        setAdapter(mAdapter);
    }

    @Override
    public void setLyrics(List<AbsLyrics> lyrics, List<Long> timeList) {
        mLyrics.clear();
        mLyrics.addAll(lyrics);
        mLyricsSchedule.setScheduleTimeList(timeList);
        mAdapter.refreshList(lyrics);
    }

    @Override
    public void play(long currentProgress) {
        mLyricsSchedule.play(currentProgress);
        mLayoutManager.scrollToPositionWithOffset(mLyricsSchedule.getCurPosition(),getHeight()/2);
    }

    @Override
    public void initial() {

    }

    @Override
    public AbsLyrics getCurLyrics() {
        return mLyrics.get(mLyricsSchedule.getCurPosition());
    }

    @Override
    public void forward(long timeOffset) {

    }

    @Override
    public void backward(long timeOffset) {

    }

    @Override
    public void next() {
        int curPosition = mLyricsSchedule.getCurPosition();

//        View firstView = mLayoutManager.findViewByPosition(visibleItemPosition);
//        if(firstView != null){
//            Log.e("hudson","当前的top相对view是"+firstView.getTop());
//        }
        if(!mIsUserActive){
            if(isNeedAdjust){
                isNeedAdjust = false;
                Log.e("hudson","调整了");
//                int position;
                scrollToFocus();
//                if(visibleItemPosition > curPosition){
//                    position = curPosition - mCount / 2 + mAdapter.getLyricsIndexOffset();
//                }else{
//                    position = curPosition + mCount / 2 + mAdapter.getLyricsIndexOffset();
//                }
//                position = (position< 0)?0:position;
//                position = (position > mAdapter.getItemCount()-1)?mAdapter.getItemCount()-1:position;
//                mLayoutManager.smoothScrollToPosition(this,null, position);
            }else{
                smoothScrollBy(0,mItemHeight);
            }
        }
        mAdapter.setCurPosition(curPosition);
        View view = mLayoutManager.findViewByPosition(curPosition + mAdapter.getLyricsIndexOffset());
        if(view != null){
            ((TextView)view).setTextColor(0xffff0000);
        }
        view = mLayoutManager.findViewByPosition(curPosition);
        if(view != null){
            ((TextView)view).setTextColor(0xff000000);
        }
    }

    @Override
    public void pause() {
        mLyricsSchedule.pause();
    }

    @Override
    public View getView() {
        return this;
    }

    private void scrollToFocus(){
        int position = mLyricsSchedule.getCurPosition() + 1;
        position = position >= mLyrics.size() ? mLyrics.size()-1 : position;
        mLayoutManager.smoothScrollToPosition(this,null, position);
//        int curPosition = mLyricsSchedule.getCurPosition();
//        int visibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
//        if(curPosition < visibleItemPosition){
//            smoothScrollBy(0,(curPosition - visibleItemPosition-4)*mItemHeight);
//        }else{
//            smoothScrollBy(0,(curPosition - visibleItemPosition+1)*mItemHeight);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                mIsUserActive = true;
                mIsInterrupt = true;
                mHandler.removeMessages(MSG_ADJUST_LYRICS);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHandler.sendEmptyMessageDelayed(MSG_ADJUST_LYRICS,USER_INFECTION_TIME);
                mIsInterrupt = false;
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }

    private void adjustLyrics(){
        if(!mIsInterrupt){
            mIsUserActive = false;
            if(mLyricsSchedule.getCurPosition() != mLyrics.size()){
                isNeedAdjust = true;
            }else{//手动调整
                scrollToFocus();
            }
        }
    }

    private boolean isNeedAdjust = false;

    private static class LyricsViewHandler extends Handler {
        private WeakReference<RecyclerLyricsView> mLyricsItem;

        public LyricsViewHandler(RecyclerLyricsView lyricsView){
            mLyricsItem = new WeakReference<>(lyricsView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MSG_ADJUST_LYRICS){
                RecyclerLyricsView recyclerLyricsView = mLyricsItem.get();
                if(recyclerLyricsView != null){
                    recyclerLyricsView.adjustLyrics();
                }
            }
        }
    }
}
