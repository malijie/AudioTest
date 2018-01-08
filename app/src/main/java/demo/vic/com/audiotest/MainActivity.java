package demo.vic.com.audiotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{
    private Button mBtnAudioRecord = null;
    private Button mBtnAudioPlay = null;

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
        mBtnAudioPlay = findViewById(R.id.btn_audio_record);
    }

    /**
     * 录音
     * @param id
     */
    public void startRecord(View id){

    }

    /**
     * 播放
     * @param id
     */
    public void startPlay(View id){

    }
}
