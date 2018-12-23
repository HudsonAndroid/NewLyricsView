package com.hudson.newlyricsview.lyrics.view.recycler.lyricsView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.hudson.newlyricsview.R;
import com.hudson.newlyricsview.lyrics.entity.Lyrics;
import com.hudson.newlyricsview.lyrics.schedule.LyricsSchedule;
import com.hudson.newlyricsview.lyrics.schedule.strategy.AbsScheduleWork;
import com.hudson.newlyricsview.lyrics.view.ILyricsView;
import com.hudson.newlyricsview.lyrics.view.recycler.CenterLayoutManager;
import com.hudson.newlyricsview.lyrics.view.recycler.adapter.LyricsAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpz on 2018/12/6.
 */
public class RecyclerLyricsView extends RecyclerView implements ILyricsView {
    private static final int USER_INFECTION_TIME = 5000;//ms
    private static final int ADJUST_SCROLL_MIN_TIME = 1000;//ms
    private static final int MSG_ADJUST_LYRICS = 1;
    private static final int DEFAULT_LYRICS_COUNT = 5;
    private static final int DEFAULT_FOCUS_LYRICS_COLOR = 0xffff0000;
    private static final int DEFAULT_NORMAL_LYRICS_COLOR = 0xff000000;
    protected int mItemDimension;//px
    protected int mScrollExtend = 0;//px
    private LyricsSchedule mLyricsSchedule;
    private final List<Lyrics> mLyrics = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private LyricsAdapter mAdapter;
    private boolean mIsUserActive = false;
    private boolean mIsInterrupt = false;
    private LyricsViewHandler mHandler;
    private int mLyricsCount;
    private int mFocusColor,mNormalColor;//高亢色、普通色
    private TextView mLastView;
    private long mLyricsTimeOffset;//歌词播放快进快退offset

    public RecyclerLyricsView(Context context) {
        this(context, null);
    }

    public RecyclerLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerLyricsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mLayoutManager = new CenterLayoutManager(getContext());
        mLayoutManager.setOrientation(getOrientation());
        setLayoutManager(mLayoutManager);
        mHandler = new LyricsViewHandler(this);
        mAdapter = getLyricsAdapter();
        mFocusColor = DEFAULT_FOCUS_LYRICS_COLOR;
        mNormalColor = DEFAULT_NORMAL_LYRICS_COLOR;
        mLyricsCount = DEFAULT_LYRICS_COUNT;
        setAdapter(mAdapter);
    }

    protected int getOrientation(){
        return VERTICAL;
    }

    protected LyricsAdapter getLyricsAdapter(){
        return new LyricsAdapter(getContext());
    }

    /**
     * 设置一页展示的歌词个数
     * 控件将会将外界传入的值转为奇数
     * @param count
     */
    @Override
    public int setLyricsCount(int count){
        if(count<=0){
            return DEFAULT_LYRICS_COUNT;
        }
        mLyricsCount = count/2*2+1;
        return mLyricsCount;
    }

    @Override
    public void setScheduleType(AbsScheduleWork scheduleWork) {
        mLyricsSchedule = new LyricsSchedule(scheduleWork,this);
    }

    @Override
    public void setFocusLyricsColor(int color) {
        if(color != 0){
            mFocusColor = color;
        }
    }

    @Override
    public void setNormalLyricsColor(int color) {
        if(color != 0){
            mNormalColor = color;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int totalDimension = getTotalDimension(w, h);
        mItemDimension = totalDimension / mLyricsCount;
        mAdapter.setViewHeight(mItemDimension,
                (totalDimension + mItemDimension)/2,
                (totalDimension - mItemDimension)/2);
        mAdapter.notifyDataSetChanged();
    }

    protected int getTotalDimension(int w,int h){
        return h;
    }

    @Override
    public void setLyrics(List<Lyrics> lyrics, List<Long> timeList, long startTime) {
        mLyrics.clear();
        mLyrics.addAll(lyrics);
        mAdapter.refreshList(lyrics);
        mLyricsSchedule.setScheduleTimeList(timeList);
        mLyricsSchedule.setStartTime(startTime);
        int initialPosition = mLyricsSchedule.getCurPosition();
        initStartScrollPosition(initialPosition);
        mAdapter.setCurPosition(initialPosition);
    }

    @Override
    public void setTypeface(Typeface typeface) {
        mAdapter.setLyricsTypeface(typeface);
    }

    /**
     * 初始状态下滑动到指定位置
     * @param initialPosition
     */
    private void initStartScrollPosition(final int initialPosition){
        int height = getHeight();
        if(height != 0){
            scrollToTarget(initialPosition);
        }else{
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    scrollToTarget(initialPosition);
                    invalidate();
                }
            });
        }
    }

    private void scrollToTarget(int curPosition){
        int offset = getTotalDimension(getWidth(),getHeight()) / 2 / mItemDimension;
        int firstVisiblePosition = mLayoutManager.findFirstVisibleItemPosition();
        int position;
        if(curPosition >= firstVisiblePosition){
            position = curPosition + offset;
        }else{
            position = curPosition < offset ?curPosition:curPosition - offset;
        }
        mLayoutManager.scrollToPosition(position+mAdapter.getLyricsIndexOffset());
        mAdapter.setCurPosition(curPosition);
        focusCurrentItem(curPosition);
    }

    public void play(){
        mLyricsSchedule.play();
    }

    @Override
    public void play(long currentProgress) {
        mLyricsSchedule.play(currentProgress);
        initStartScrollPosition(mLyricsSchedule.getCurPosition());
    }

    @Override
    public Lyrics getCurLyrics() {
        int curPosition = mLyricsSchedule.getCurPosition();
        if(curPosition >=0 && curPosition < mLyrics.size()){
            return mLyrics.get(curPosition);
        }
        return null;
    }

    @Override
    public void forward(long currentProgress,long timeOffset) {
        mLyricsTimeOffset += timeOffset;
        mLyricsSchedule.play(currentProgress + mLyricsTimeOffset);
        showToast(getResources().getString(R.string.lyrics_tips_forward,timeOffset+""));
    }

    private void showToast(String tips){
        Toast.makeText(getContext(),tips,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void backward(long currentProgress,long timeOffset) {
        mLyricsTimeOffset -= timeOffset;
        mLyricsSchedule.play(currentProgress + mLyricsTimeOffset);
        showToast(getResources().getString(R.string.lyrics_tips_backward,timeOffset+""));
    }

    @Override
    public void next() {
        mLastLyricsScrollTime = System.currentTimeMillis();
        if(!mIsUserActive){
            if(isNeedAdjust){
                isNeedAdjust = false;
                scrollToCenter();
            }else{
                //如果前后两句歌词相差时间小于等于0，则会出现中间某次不会滑动的情形，因此此时叠加，并且不滑动
                if(mLyricsSchedule.getNextLyricsTimeOffset() <= 0){
                    mScrollExtend += mItemDimension;
                }else{
                    smoothScrollToNext();
                    mScrollExtend = 0;
                }
            }
        }
        int curPosition = mLyricsSchedule.getCurPosition();
        mAdapter.setCurPosition(curPosition);
        focusCurrentItem(curPosition);
    }

    protected void smoothScrollToNext(){
        smoothScrollBy(0, mItemDimension + mScrollExtend);
    }

    /**
     * 高亢显示当前item，并取消之前的item高亢色
     * @param curPosition
     */
    private void focusCurrentItem(int curPosition){
        if(mLastView != null){
            mLastView.setTextColor(mNormalColor);
        }
        View view = mLayoutManager.findViewByPosition(curPosition + mAdapter.getLyricsIndexOffset());
        if(view != null){
            mLastView = (TextView)view;
            mLastView.setTextColor(mFocusColor);
        }
        view = mLayoutManager.findViewByPosition(curPosition);
        if(view != null){
            ((TextView)view).setTextColor(mNormalColor);
        }
    }

    @Override
    public void pause(long pauseTime) {
        mLyricsSchedule.pause(pauseTime);
    }

    @Override
    public View getView() {
        return this;
    }

    /**
     * 滑动当前歌词到中央
     */
    public void scrollToCenter(){
        //需要加上除了普通歌词外的头布局数
        mLayoutManager.smoothScrollToPosition(this,null,
                mLyricsSchedule.getCurPosition() + mAdapter.getLyricsIndexOffset());
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
            long offset = mLyricsSchedule.getNextLyricsTimeOffset();
            long passBy = System.currentTimeMillis() - mLastLyricsScrollTime;
            if(mLyricsSchedule.isWorkRunning() &&
                    offset - passBy < ADJUST_SCROLL_MIN_TIME){//可以由next接管
                isNeedAdjust = true;
            }else{//当前计划任务未进行或者next下句歌词还需要较长时间，因此手动调整
                scrollToCenter();
            }
        }
    }
    private long mLastLyricsScrollTime;
    private boolean isNeedAdjust = false;

    private static class LyricsViewHandler extends Handler {
        private WeakReference<RecyclerLyricsView> mLyricsView;

        public LyricsViewHandler(RecyclerLyricsView lyricsView){
            mLyricsView = new WeakReference<>(lyricsView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RecyclerLyricsView recyclerLyricsView = mLyricsView.get();
            if(recyclerLyricsView != null){
                switch (msg.what){
                    case MSG_ADJUST_LYRICS:
                        recyclerLyricsView.adjustLyrics();
                        break;
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLyricsSchedule.end();
    }
}
