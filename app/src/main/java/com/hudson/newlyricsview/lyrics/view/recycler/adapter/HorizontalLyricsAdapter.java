package com.hudson.newlyricsview.lyrics.view.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hudson.newlyricsview.lyrics.view.item.HorizontalLyricsTextView;
import com.hudson.newlyricsview.lyrics.view.item.LyricsTextView;
import com.hudson.newlyricsview.lyrics.view.recycler.viewholder.BaseViewHolder;
import com.hudson.newlyricsview.lyrics.view.recycler.viewholder.EmptyViewHolder;
import com.hudson.newlyricsview.lyrics.view.recycler.viewholder.LyricsViewHolder;

/**
 * Created by Hudson on 2018/12/23.
 */
public class HorizontalLyricsAdapter extends LyricsAdapter {

    public HorizontalLyricsAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_TOP){//headerView
            View itemView = new TextView(parent.getContext());
            itemView.setLayoutParams(new ViewGroup.LayoutParams(mFirstViewDimension,ViewGroup.LayoutParams.MATCH_PARENT));
            return new EmptyViewHolder(itemView);
        }else if(viewType == TYPE_BOTTOM){//bottomView
            View itemView = new TextView(parent.getContext());
            itemView.setLayoutParams(new ViewGroup.LayoutParams( mEndViewDimension,ViewGroup.LayoutParams.MATCH_PARENT));
            return new EmptyViewHolder(itemView);
        }else{//normalLyricsItem
            LyricsTextView item = new HorizontalLyricsTextView(parent.getContext());
            item.setTypeface(mLyricsTypeface);
            item.setLayoutParams(new ViewGroup.LayoutParams(mLyricsItemDimension,ViewGroup.LayoutParams.MATCH_PARENT));
            return new LyricsViewHolder(item);
        }
    }
}
