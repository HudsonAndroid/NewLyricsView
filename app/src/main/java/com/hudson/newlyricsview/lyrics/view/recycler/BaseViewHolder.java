package com.hudson.newlyricsview.lyrics.view.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;

/**
 * 抽象的ViewHolder
 * Created by hpz on 2018/5/11.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void refreshView(AbsLyrics data, boolean isCurrent){}
}
