package com.vic.audio.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by malijie on 2018/1/9.
 */

public class AudioCapture {
    private static final String TAG = AudioCapture.class.getSimpleName();
    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;//音频源
    private static final int SAMPLE_RATE_IN_HZ = 44100;//采样率
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;//单通道
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;//数据位宽

    private OnAudioFrameCapturedListener mAudioFrameCapturedListener;

    private int mBufferSize;
    private boolean canLoop = true;
    private boolean mIsStartCapture = false;

    private Thread mRecordThread = null;
    private AudioRecord mAudioRecord = null;



    public interface OnAudioFrameCapturedListener {
        void onAudioFrameCaptured(byte[] audioData);
    }

    public void setOnAudioFrameCapturedListener(OnAudioFrameCapturedListener listener) {
        mAudioFrameCapturedListener = listener;
    }


    public boolean startCapture() {
        return startCapture(AUDIO_SOURCE, SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
    }

    public boolean startCapture(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        if(mIsStartCapture){
            Log.e(TAG, "Already start capture audio!");
            return false;
        }

        mBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        if (mBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            Log.e(TAG, "Invalid parameter !");
            return false;
        }

        mAudioRecord = new AudioRecord(AUDIO_SOURCE, SAMPLE_RATE_IN_HZ, CHANNEL_CONFIG, AUDIO_FORMAT, mBufferSize * 4);

        if (mAudioRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
            Log.e(TAG, "AudioRecord initialize fail !");
            return false;
        }

        mAudioRecord.startRecording();
        mRecordThread = new Thread(new RecordRunnable());
        mRecordThread.start();

        mIsStartCapture = true;

        return true;
    }

    private class RecordRunnable implements Runnable{
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
                    mAudioFrameCapturedListener. onAudioFrameCaptured(buffer);
                    Log.d(TAG,"start write,buffer length=" + buffer.length);
                }
            }
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord(){
        if(!mIsStartCapture){
            return;
        }

        canLoop = false;
        try {
            mRecordThread.interrupt();
            mRecordThread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            mAudioRecord.stop();
        }
        mAudioRecord.release();

        mIsStartCapture = false;
        mAudioFrameCapturedListener = null;
        Log.d(TAG,"stop capture audio success");
    }
}
