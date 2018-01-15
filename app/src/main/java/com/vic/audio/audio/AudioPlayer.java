package com.vic.audio.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.vic.audio.wav.WavFileReader;

/**
 * Created by malijie on 2018/1/15.
 */

public class AudioPlayer {
    private static final String TAG = AudioPlayer.class.getSimpleName();

    private static final int STREAM_TYPE = AudioManager.STREAM_MUSIC;
    private static final int SAMPLER_RATE_IN_HZ = 44100;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_OUT_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int MODE = AudioTrack.MODE_STREAM;
    private final WavFileReader mWavFileReader;

    private AudioTrack mAudioTrack = null;
    private boolean isStartPlay = false;

    public AudioPlayer(){
        mWavFileReader = new WavFileReader();
        mWavFileReader.openFile();
    }

    public void startPlay(){
        if(isStartPlay){
            Log.d(TAG,"already start play...");
            return;
        }

        int bufferSize = AudioTrack.getMinBufferSize(SAMPLER_RATE_IN_HZ,CHANNEL_CONFIG,AUDIO_FORMAT);
        //int streamType, int sampleRateInHz, int channelConfig, int audioFormat,int bufferSizeInBytes, int mode
        mAudioTrack = new AudioTrack(STREAM_TYPE,SAMPLER_RATE_IN_HZ,CHANNEL_CONFIG,AUDIO_FORMAT,bufferSize,MODE);

        isStartPlay = true;

        Thread playThread = new Thread(new AudioRunnable());
        playThread.start();

    }

    private void play(byte[] buf,int offset,int count){
        if(!isStartPlay){
            Log.d(TAG,"can not start play");
        }
        mAudioTrack.write(buf,offset,count);
        mAudioTrack.play();
    }

    private void stopPlayer(){
        if(!isStartPlay){
            return;
        }

        if(mAudioTrack.getState() == AudioTrack.PLAYSTATE_PLAYING){
            mAudioTrack.stop();
        }

        mAudioTrack.release();
        isStartPlay = false;
        Log.i(TAG, "Stop audio player success !");

    }

    private class AudioRunnable implements Runnable{

        @Override
        public void run() {
            byte[] buf = new byte[1024 * 2];
            while(isStartPlay && mWavFileReader.readData(buf,0,buf.length) != -1){
                play(buf,0,buf.length);
            }

            stopPlayer();
            mWavFileReader.closeFile();

        }
    }
}
