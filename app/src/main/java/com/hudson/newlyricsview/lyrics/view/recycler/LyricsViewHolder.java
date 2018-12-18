package com.hudson.newlyricsview.lyrics.view.recycler;

import android.view.View;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;
import com.hudson.newlyricsview.lyrics.view.item.LyricsTextView;

/**
 * Created by hpz on 2018/5/11.
 */

public class LyricsViewHolder extends BaseViewHolder {
    private LyricsTextView mContent;

    public LyricsViewHolder(View itemView) {
        super(itemView);
        mContent = (LyricsTextView) itemView;
    }

    @Override
    public void refreshView(AbsLyrics data,boolean isCurrent) {
        mContent.setLyrics(data);
        if(isCurrent){
            mContent.setTextColor(0xffff0000);
        }else{
            mContent.setTextColor(0xff000000);
        }
    }
}
