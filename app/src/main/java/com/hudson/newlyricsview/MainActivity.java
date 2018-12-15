package com.hudson.newlyricsview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hudson.newlyricsview.lyrics.LyricsController;

public class MainActivity extends AppCompatActivity {

    public LyricsController mLyricsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLyricsController = new LyricsController(this);
        mLyricsController.setLyricsCount(9);
        mLyricsController.decodeLyrics("");
        mLyricsController.initialLyricsView(2000);
        View lyricsView = mLyricsController.getLyricsView();
        RelativeLayout container= findViewById(R.id.rl_container);
        container.addView(lyricsView,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void start(View v){
        mLyricsController.play();
    }
}
