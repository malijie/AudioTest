package com.vic.audio.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vic.audio.R;
import com.vic.audio.audio.AudioRecorder;



public class MainActivity extends AppCompatActivity{
    private Button mBtnAudioRecord = null;
    private Button mBtnAudioPlay = null;
    private Button mBtnStopRecord = null;
    private AudioRecorder mAudioRecorder= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
        mAudioRecorder = new AudioRecorder();
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
Log.d("MLJ","startRecord AudioRecorder=" + mAudioRecorder);
        mAudioRecorder.startRecord();
    }

    public void stopRecord(View id){
        Toast.makeText(this, "停止录音", Toast.LENGTH_SHORT).show();
Log.d("MLJ","stopRecord AudioRecorder=" + mAudioRecorder);

        mAudioRecorder.stopRecord();
    }

    /**
     * 播放
     * @param id
     */
    public void startPlay(View id){

    }
}
