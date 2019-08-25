package com.hudson.newlyricsview.lyrics.view.item;

import android.content.Context;
import android.util.AttributeSet;

import com.hudson.newlyricsview.lyrics.entity.Lyrics;

/**
 * Created by Hudson on 2018/12/23.
 */
public class HorizontalLyricsTextView extends LyricsTextView {
    public HorizontalLyricsTextView(Context context) {
        this(context, null);
    }

    public HorizontalLyricsTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalLyricsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setLyrics(Lyrics lyrics) {
        String lrcContent = lyrics.getLrcContent();
        StringBuffer sb = new StringBuffer();
        int length = lrcContent.length();
        for (int i = 0; i < length; i++) {
            sb.append(lrcContent.charAt(i));
            if(i != length - 1){
                sb.append("\n");
            }
        }
        setText(sb.toString());
    }
}
