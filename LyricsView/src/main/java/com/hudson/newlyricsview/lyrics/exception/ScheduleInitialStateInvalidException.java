package com.hudson.newlyricsview.lyrics.exception;

/**
 * Created by Hudson on 2018/12/8.
 */
public class ScheduleInitialStateInvalidException extends RuntimeException {
    public ScheduleInitialStateInvalidException(String msg){
        super("Schedule work initial state is invalid,"+msg);
    }
}
