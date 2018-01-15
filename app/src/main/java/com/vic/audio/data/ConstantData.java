package com.vic.audio.data;

import android.os.Environment;

import java.io.File;

/**
 * Created by malijie on 2018/1/15.
 */

public class ConstantData {
    public static final String AUDIO_FILE_PATH =  Environment.getExternalStorageDirectory()
            + File.separator + "apk" + File.separator + "aaa.wav";

}
