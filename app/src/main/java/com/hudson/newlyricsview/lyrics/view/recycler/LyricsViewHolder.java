package com.hudson.newlyricsview.lyrics.view.recycler;

import android.view.View;
import android.widget.TextView;

import com.hudson.newlyricsview.lyrics.entity.AbsLyrics;

/**
 * Created by hpz on 2018/5/11.
 */

public class LyricsViewHolder extends BaseViewHolder {
    private TextView mContent;

    public LyricsViewHolder(View itemView) {
        super(itemView);
        mContent = (TextView) itemView;
    }

    @Override
    public void refreshView(AbsLyrics data,boolean isCurrent) {
        mContent.setText(data.getLrcContent());
        if(isCurrent){
            mContent.setTextColor(0xffff0000);
        }else{
            mContent.setTextColor(0xff000000);
        }
    }
}
