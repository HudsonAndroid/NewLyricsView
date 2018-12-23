package com.hudson.newlyricsview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hudson.newlyricsview.lyrics.LyricsController;
import com.hudson.newlyricsview.lyrics.view.config.LyricsViewConfig;
import com.hudson.newlyricsview.lyrics.view.style.LyricsViewStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public LyricsController mLyricsController;
    private EditText mEtMusic;
    private MediaPlayer mediaPlayer;
    private String musicPath;
    private String lyricsPath;
    public RelativeLayout mContainer;
    private boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        mLyricsController = new LyricsController(this);
        LyricsViewConfig config =
                new LyricsViewConfig()
                        .setLyricsViewStyle(LyricsViewStyle.HorizontalStyle)
                        .setLyricsCount(9);
        mLyricsController.init(config);
        mEtMusic = findViewById(R.id.et_music);
        mContainer = findViewById(R.id.rl_container);
        musicPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/donglingMusic/download/";
        lyricsPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/donglingMusic/Lyrics/";
    }

    public void start(View v){
        if(mediaPlayer.isPlaying() && isStart){
            mediaPlayer.pause();
            mLyricsController.pause(mediaPlayer.getCurrentPosition());
        }else if(isStart){
            mediaPlayer.start();
            mLyricsController.play();
        }else{
            try {
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        isStart = false;
                    }
                });
            } catch(FileNotFoundException e){
                e.printStackTrace();
                Toast.makeText(this, "文件没有找到!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLyricsController.play();
            isStart = true;
        }
    }

    public void pause(View v){
        String content = mEtMusic.getText().toString();
        mLyricsController.decodeLyrics(this,lyricsPath+content+".lrc");
        mLyricsController.initialLyricsView(0);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(musicPath+ content+".mp3");// 设置播放的数据源。
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        View lyricsView = mLyricsController.getLyricsView();
        mContainer.removeAllViews();
        mContainer.addView(lyricsView,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mLyricsController.pause();
    }

    public void forward(View v){
        mLyricsController.forward(mediaPlayer.getCurrentPosition(),500);
    }

    public void backward(View v){
        mLyricsController.backward(mediaPlayer.getCurrentPosition(),500);
    }

    /**
     * 请求权限并初始化数据库
     */
    public void requestPermission(){
        ArrayList<String> permissions = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        String[] tmp = new String [permissions.size()];
        permissions.toArray(tmp);
        if(tmp.length>0){
            ActivityCompat.requestPermissions(this,tmp, 0);
        }
    }
}
