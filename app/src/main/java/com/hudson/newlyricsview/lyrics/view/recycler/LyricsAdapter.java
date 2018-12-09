package com.hudson.newlyricsview.lyrics.view.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hudson.newlyricsview.R;
import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的统一adapter
 * Created by hpz on 2018/5/11.
 */

public class LyricsAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int TYPE_TOP = 1;
    private static final int TYPE_BOTTOM = 2;
    protected List<AbsLyrics> mDatas = new ArrayList<>();
    protected Context mContext;
    private int mCurPosition;
    private int mTopViewHeight;
    private int mBottomViewHeight;

    protected LyricsAdapter(Context context){
        mContext = context;
    }

    public void refreshList(List<AbsLyrics> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void setCurPosition(int position){
        mCurPosition = position;
    }

    public void setEmptyViewHeight(int topHeight,int bottomHeight){
        mTopViewHeight = topHeight;
        mBottomViewHeight = bottomHeight;
    }

    public int getLyricsIndexOffset(){
        return 1;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_TOP){
            View itemView = new TextView(parent.getContext());
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mTopViewHeight));
            return new EmptyViewHolder(itemView);
        }else if(viewType == TYPE_BOTTOM){
            View itemView = new TextView(parent.getContext());
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mBottomViewHeight));
            return new EmptyViewHolder(itemView);
        }else{
            return new LyricsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lyrics,parent,false));
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
