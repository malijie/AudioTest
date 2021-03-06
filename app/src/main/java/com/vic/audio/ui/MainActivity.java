package com.vic.audio.ui;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vic.audio.R;
import com.vic.audio.audio.AudioCapture;
import com.vic.audio.audio.AudioPlayer;
import com.vic.audio.wav.WavFileReader;
import com.vic.audio.wav.WavFileWriter;



public class MainActivity extends AppCompatActivity implements AudioCapture.OnAudioFrameCapturedListener{
    private Button mBtnAudioRecord = null;
    private Button mBtnAudioPlay = null;
    private Button mBtnStopRecord = null;

    private AudioCapture mAudioCapture;
    private WavFileWriter mWavFileWriter;
    private AudioPlayer mAudioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
    }

    private void initViews() {
        mBtnAudioRecord = findViewById(R.id.btn_audio_record);
        mBtnStopRecord = findViewById(R.id.btn_audio_stop_record);
        mBtnAudioPlay = findViewById(R.id.btn_audio_play);
    }



    /**
     * 录音
     * @param id
     */
    public void startRecord(View id){
        Toast.makeText(this, "开始录音", Toast.LENGTH_SHORT).show();
        mAudioCapture = new AudioCapture();
        mWavFileWriter = new WavFileWriter();
        mWavFileWriter.openFile();

        mAudioCapture.setOnAudioFrameCapturedListener(this);
        startCapture();

    }

    private boolean startCapture(){
        return mAudioCapture.startCapture(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
    }

    public void stopRecord(View id){
        Toast.makeText(this, "停止录音", Toast.LENGTH_SHORT).show();
        mAudioCapture.stopRecord();
        mWavFileWriter.closeFile();

    }

    /**
     * 播放
     * @param id
     */
    public void startPlay(View id){
        Toast.makeText(this,"开始播放",Toast.LENGTH_SHORT).show();

        mAudioPlayer = new AudioPlayer();
        mAudioPlayer.startPlay();

    }

    @Override
    public void onAudioFrameCaptured(byte[] audioData) {
        mWavFileWriter.writeData(audioData, 0, audioData.length);

    }
}
