package com.hudson.newlyricsview.lyrics.view.recycler;

import android.content.Context;
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
    private static final int USER_INFECTION_TIME = 3000;//ms
    private static final int MSG_ADJUST_LYRICS = 1;
    private static final int DEFAULT_LYRICS_COUNT = 5;
    private int mItemHeight;//px
    private LyricsSchedule mLyricsSchedule;
    private final List<AbsLyrics> mLyrics = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private LyricsAdapter mAdapter;
    private boolean mIsUserActive = false;
    private boolean mIsInterrupt = false;
    private LyricsViewHandler mHandler;
    private int mLyricsCount;
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
        mLyricsSchedule = new LyricsSchedule(new HandlerStrategy(),this);
        mLayoutManager = new CenterLayoutManager(getContext());
        mLayoutManager.setOrientation(VERTICAL);
        setLayoutManager(mLayoutManager);
        mHandler = new LyricsViewHandler(this);
        mAdapter = new LyricsAdapter(getContext());
        setAdapter(mAdapter);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mLyricsCount == 0){
            mLyricsCount = DEFAULT_LYRICS_COUNT;
        }
        mItemHeight = h / mLyricsCount;
        mAdapter.setViewHeight(mItemHeight,
                (h + mItemHeight)/2,(h - mItemHeight)/2);
    }

    @Override
    public void setLyrics(List<AbsLyrics> lyrics, List<Long> timeList,long startTime) {
        mLyrics.clear();
        mLyrics.addAll(lyrics);
        mAdapter.refreshList(lyrics);
        mLyricsSchedule.setScheduleTimeList(timeList);
        mLyricsSchedule.setStartTime(startTime);
        int initialPosition = mLyricsSchedule.getCurPosition();
        initStartScrollPosition(initialPosition);
        mAdapter.setCurPosition(initialPosition);
    }

    /**
     * 初始状态下滑动到指定位置
     * @param initialPosition
     */
    public void initStartScrollPosition(final int initialPosition){
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
        int offset = getHeight() / 2 / mItemHeight;
        int firstVisiblePosition = mLayoutManager.findFirstVisibleItemPosition();
        int position;
        if(curPosition > firstVisiblePosition){
            position = curPosition + offset;
        }else{
            position = curPosition - offset;
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
    public AbsLyrics getCurLyrics() {
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
        if(!mIsUserActive){
            if(isNeedAdjust){
                isNeedAdjust = false;
                scrollToFocus();
            }else{
                smoothScrollBy(0,mItemHeight);
            }
        }
        int curPosition = mLyricsSchedule.getCurPosition();
        mAdapter.setCurPosition(curPosition);
        focusCurrentItem(curPosition);
    }

    /**
     * 高亢显示当前item，并取消之前的item高亢色
     * @param curPosition
     */
    private void focusCurrentItem(int curPosition){
        if(mLastView != null){
            mLastView.setTextColor(0xff000000);
        }
        View view = mLayoutManager.findViewByPosition(curPosition + mAdapter.getLyricsIndexOffset());
        if(view != null){
            mLastView = (TextView)view;
            mLastView.setTextColor(0xffff0000);
        }
        view = mLayoutManager.findViewByPosition(curPosition);
        if(view != null){
            ((TextView)view).setTextColor(0xff000000);
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

    private void scrollToFocus(){
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
            if(mLyricsSchedule.isWorkRunning()){
                isNeedAdjust = true;
            }else{//当前计划任务未进行，因此手动调整
                scrollToFocus();
            }
        }
    }

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
