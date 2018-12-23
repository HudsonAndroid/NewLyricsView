package com.hudson.newlyricsview.lyrics.decode;

import android.content.Context;
import android.util.Log;

import com.hudson.newlyricsview.lyrics.entity.Lyrics;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * Created by Hudson on 2018/12/8.
 */
public class NormalLyricsDecoder extends AbsLyricsDecoder {

    @Override
    public void decode(Context context,String path) {
        mLyrics.clear();
        mTimeList.clear();
        final InputStreamReader isr;
        FileInputStream fis= null;
        if (new File(path).exists()) {
            File file = new File(path);
            try {
                //创建一个文件输入流对象
                fis = new FileInputStream(file);
                String fileCode = getFileEncode(file);
                isr = new InputStreamReader(fis,(fileCode==null)?"utf-8":fileCode);
                readLrcFromInputStream(isr);
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if(fis!=null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            System.err.println("the lyric file doesn't exist!");
        }
    }

    //从流中读取歌词
    private void readLrcFromInputStream(InputStreamReader isr){
        try{
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            Lyrics lyrics ;
            int len;
            String splitLrcData[];
            while((s = br.readLine()) != null) {
                //替换字符
                s = s.replace("[", "");
                //分离“@”字符
                splitLrcData = s.split("]");
                len = splitLrcData.length;
                //判断是否以]结尾，存在[xx][xx]情况
                if(s.endsWith("]")){//不加入该歌词
                    continue;
                }
                if(len > 1) {
                    for(int i = 0;i<len-1;i++){//存在多个时间戳使用同一句歌词的情况
                        lyrics = new Lyrics();
                        lyrics.setLrcContent(splitLrcData[len-1]);//添加内容
//                        if(!s.endsWith("]")){//如果以"]"结尾，如："[00:08.78][04:35.99]"没有歌词内容
//
//                        }else {
//                            lyrics.setLrcStr("music...");//添加空白内容,linux系统以\n结尾，所以不可能跑进这个方法中
//                        }
                        long time = StringTime2IntTime(splitLrcData[i]);
                        if(time!=-1){
                            lyrics.setLrcProgressTime(time);
                            mTimeList.add(time);
                            mLyrics.add(lyrics);
                        }
                    }
                }
            }
            Collections.sort(mTimeList);
            Collections.sort(mLyrics);//对歌词对象进行排序，2015.10.9发现有些歌词为了简单，出现了一句歌词有多个时间的情况，所以需要排序
        }catch(Exception e){
            Log.e("readLyric","read lyric occur error!");
            e.printStackTrace();
        }
    }

    public static String getFileEncode(File file) {
        if (!file.exists()) {
            System.err.println("getFileEncode: file not exists!");
            return null;
        }
        byte[] buf = new byte[4096];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            UniversalDetector detector = new UniversalDetector(null);
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            detector.reset();
            fis.close();
            return encoding;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析歌词时间
     * 1.标准格式歌词内容格式如下：
     * [00:02.32]陈奕迅
     * [00:03.43]好久不见
     * [00:05.22]歌词制作  王涛
     * 2.其他格式：
     * [00:02]陈奕迅
     * [00:03]好久不见
     * [00:05]歌词制作  王涛
     * 3.其他格式  本格式忽略
     * [00:02:32]陈奕迅
     * [00:03:43]好久不见
     * [00:05:22]歌词制作  王涛
     * @param timeStr 时间字符串
     * @return
     */
    public long StringTime2IntTime(String timeStr) {
        long currentTime = 0,second,minute;
        if(timeStr.contains(".")){
            timeStr = timeStr.replace(":", ".");  //用后面的替换前面的
            timeStr = timeStr.replace(".", "@");
            String timeData[] = timeStr.split("@"); //将时间分隔成字符串数组
            //分离出分、秒并转换为整型
            minute = Integer.parseInt(timeData[0]);
            second = Integer.parseInt(timeData[1]);
            long millisecond = Integer.parseInt(timeData[2]);  //我认为是10毫秒为单位，但是百度百科说是毫秒
            //计算上一行与下一行的时间转换为毫秒数
            currentTime = (minute * 60 + second) * 1000 + millisecond * 10;
        }else if(timeStr.contains(":")&&timeStr.length()<6){//第三种格式几乎没见过，不予考虑
            String timeData[] = timeStr.split(":"); //将时间分隔成字符串数组
            //分离出分、秒并转换为整型
            minute = Integer.parseInt(timeData[0]);
            second = Integer.parseInt(timeData[1]);
            currentTime = (minute * 60 + second) * 1000;
        }else{//说明字符串不是时间格式或者字符串是第三种类型（没处理）
            return -1;
        }
        return currentTime;
    }
}
