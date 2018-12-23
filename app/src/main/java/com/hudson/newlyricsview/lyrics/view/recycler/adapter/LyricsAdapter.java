package com.hudson.newlyricsview.lyrics.view.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hudson.newlyricsview.lyrics.entity.Lyrics;
import com.hudson.newlyricsview.lyrics.view.item.LyricsTextView;
import com.hudson.newlyricsview.lyrics.view.recycler.viewholder.BaseViewHolder;
import com.hudson.newlyricsview.lyrics.view.recycler.viewholder.EmptyViewHolder;
import com.hudson.newlyricsview.lyrics.view.recycler.viewholder.LyricsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的统一adapter
 * Created by hpz on 2018/5/11.
 */

public class LyricsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    protected static final int TYPE_TOP = 1;
    protected static final int TYPE_BOTTOM = 2;
    private final List<Lyrics> mDatas = new ArrayList<>();
    protected Context mContext;
    private int mCurPosition;
    protected int mFirstViewDimension;
    protected int mEndViewDimension;
    protected int mLyricsItemDimension;

    public LyricsAdapter(Context context){
        mContext = context;
    }

    public void setViewHeight(int itemDimension,int firstViewDimension,int endViewDimension){
        mLyricsItemDimension = itemDimension;
        mFirstViewDimension = firstViewDimension;
        mEndViewDimension = endViewDimension;
    }

    public void refreshList(List<Lyrics> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setCurPosition(int position){
        mCurPosition = position;
    }

    /**
     * 获取普通歌词之前的顶部emptyView的数目
     * @return
     */
    public int getLyricsIndexOffset(){
        return 1;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_TOP){//headerView
            View itemView = new TextView(parent.getContext());
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mFirstViewDimension));
            return new EmptyViewHolder(itemView);
        }else if(viewType == TYPE_BOTTOM){//bottomView
            View itemView = new TextView(parent.getContext());
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mEndViewDimension));
            return new EmptyViewHolder(itemView);
        }else{//normalLyricsItem
            View item = new LyricsTextView(parent.getContext());
            item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mLyricsItemDimension));
            return new LyricsViewHolder(item);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType != TYPE_TOP && itemViewType != TYPE_BOTTOM){
            position = position - 1;
            holder.refreshView(mDatas.get(position),mCurPosition == position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_TOP;
        }else if(position == getItemCount()-1){
            return TYPE_BOTTOM;
        }
        return super.getItemViewType(position);
    }
}
