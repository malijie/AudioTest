package com.vic.audio.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.vic.audio.wav.WavFileWriter;


/**
 * Created by malijie on 2018/1/8.
 */

public class AudioRecorder {
    private static final String TAG = AudioRecorder.class.getSimpleName();

    private AudioRecord mAudioRecord = null;
    private WavFileWriter mWavFileWriter = null;
    private int mBufferSize;
    private boolean canLoop = true;
    private Thread mRecordThread = null;

    public AudioRecorder(){


    }

    /**
     * 开始录音
     */
    public void startRecord(){


    }







}
