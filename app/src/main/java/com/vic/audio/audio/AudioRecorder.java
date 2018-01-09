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
    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;//音频源
    private static final int SAMPLE_RATE_IN_HZ = 44100;//采样率
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;//单通道
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;//数据位宽

    private AudioRecord mAudioRecord = null;
    private WavFileWriter mWavFileWriter = null;
    private int mBufferSize;
    private boolean canLoop = true;

    public AudioRecorder(){
        mWavFileWriter = new WavFileWriter();
        boolean canWrite = mWavFileWriter.openFile();
Log.d("MLJ","canWrite=" + canWrite);
        mBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,CHANNEL_CONFIG,AUDIO_FORMAT);
        mAudioRecord = new AudioRecord(AUDIO_SOURCE,SAMPLE_RATE_IN_HZ,CHANNEL_CONFIG,AUDIO_FORMAT,mBufferSize * 4);
    }

    /**
     * 开始录音
     */
    public void startRecord(){

        if (mBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            Log.e(TAG, "Invalid parameter !");
            return;
        }

        if(mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED){
            Log.e(TAG, "AudioRecord initialize fail !");
            return;
        }

        mAudioRecord.startRecording();
        new RecordThread().start();
    }

    /**
     * 停止录音
     */
    public void stopRecord(){
        canLoop = false;
        mAudioRecord.stop();
        mAudioRecord.release();
        mWavFileWriter.closeFile();
    }

    private class RecordThread extends Thread{
        @Override
        public void run() {
            while(canLoop){
                byte[] buffer = new byte[1024 * 2];
                int ret = mAudioRecord.read(buffer, 0, buffer.length);
                if (ret == AudioRecord.ERROR_INVALID_OPERATION) {
                    Log.e(TAG, "Error ERROR_INVALID_OPERATION");
                } else if (ret == AudioRecord.ERROR_BAD_VALUE) {
                    Log.e(TAG, "Error ERROR_BAD_VALUE");
                } else {
                    Log.d(TAG,"start write");

                    mWavFileWriter.writeData(buffer,0,buffer.length);
                }
            }
        }
    }

}
