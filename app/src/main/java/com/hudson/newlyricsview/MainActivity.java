package com.hudson.newlyricsview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hudson.newlyricsview.lyrics.LyricsController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LyricsController lyricsController = new LyricsController(this);
        lyricsController.decodeLyrics("");
        lyricsController.initialLyricsView();
        View lyricsView = lyricsController.getLyricsView();
        LinearLayout container= findViewById(R.id.ll_container);
        container.addView(lyricsView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        lyricsController.play(0);
    }
}
