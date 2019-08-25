package com.hudson.newlyricsview.lyrics.view.item;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.hudson.newlyricsview.lyrics.entity.Lyrics;

/**
 * Created by hpz on 2018/12/18.
 */
public class LyricsTextView extends TextView {
    private static final float DEFAULT_TEXT_SIZE = 15;//sp

    public LyricsTextView(Context context) {
        this(context, null);
    }

    public LyricsTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LyricsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTextSize(DEFAULT_TEXT_SIZE);
        setGravity(Gravity.CENTER);
    }

    public void setLyrics(Lyrics lyrics){
        String lrcTranslate = lyrics.getLrcTranslate();
        if(!TextUtils.isEmpty(lrcTranslate)){
            setText(lyrics.getLrcContent()+"\n"+lrcTranslate);
            return ;
        }
        setText(lyrics.getLrcContent());
    }
}
