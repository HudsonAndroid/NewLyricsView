package com.hudson.newlyricsview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hudson.newlyricsview.lyrics.LyricsController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View lyricsView = new LyricsController(null, this).getLyricsView();
    }
}
