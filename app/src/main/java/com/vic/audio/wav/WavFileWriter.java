package com.vic.audio.wav;

import android.os.Environment;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by malijie on 2018/1/9.
 */

public class WavFileWriter {
    private static final String TAG = WavFileWriter.class.getSimpleName();
    private static final String FILE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "apk" + File.separator + "aaa.wav";

    private DataOutputStream dos = null;
    private int mDataSize = 0;

    public boolean openFile(){
        try {
            if(dos != null){
                closeFile();
            }
            dos = new DataOutputStream(new FileOutputStream(FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return writeWavFile(44100,1,16);
    }

    /**
     * 构建wav文件s
     * @return
     *
     */
    private boolean writeWavFile(int sampleRateInHz, int channels, int bitsPerSample ) {
        if(dos == null){
            return false;
        }
        WavFileHeader wavFileHeader = new WavFileHeader(sampleRateInHz,channels,bitsPerSample);
        try {
            dos.writeBytes(wavFileHeader.mChunkID);
            dos.write(intToByteArray(wavFileHeader.mChunkSize),0,4);
            dos.writeBytes(wavFileHeader.mFormat);

            dos.writeBytes(wavFileHeader.mSubChunk1ID);
            dos.write(intToByteArray(wavFileHeader.mSubChunk1Size),0,4);
            dos.write(shortToByteArray(wavFileHeader.mAudioFormat),0,2);
            dos.write(shortToByteArray(wavFileHeader.mNumChannel),0,2);
            dos.write(intToByteArray(wavFileHeader.mSampleRate),0,4);
            dos.write(intToByteArray(wavFileHeader.mByteRate),0,4);
            dos.write(shortToByteArray(wavFileHeader.mBlockAlign),0,2);
            dos.write(shortToByteArray(wavFileHeader.mBitsPerSample),0,2);

            dos.writeBytes(wavFileHeader.mSubChunk2ID);
            dos.write(intToByteArray(wavFileHeader.mSubChunk2Size),0,4);

            Log.d(TAG,"wavFileHeader=" + wavFileHeader);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean writeData(byte[] buf,int offset,int count) {
        if(dos == null){
            return false;
        }

        try {
            dos.write(buf,offset,count);
            mDataSize += count;
            Log.d(TAG,"DataSize=" + mDataSize);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean closeFile(){
        boolean ret = true;
        try {
            if(dos != null){
                ret = writeDataSize();
                dos.close();
                dos = null;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private boolean writeDataSize() {
        if (dos == null) {
            return false;
        }

        try {
            RandomAccessFile wavFile = new RandomAccessFile(FILE_PATH, "rw");
            wavFile.seek(WavFileHeader.WAV_CHUNKSIZE_OFFSET);
            wavFile.write(intToByteArray((mDataSize + WavFileHeader.WAV_CHUNKSIZE_EXCLUDE_DATA)), 0, 4);
            wavFile.seek(WavFileHeader.WAV_SUB_CHUNKSIZE2_OFFSET);
            wavFile.write(intToByteArray((mDataSize)), 0, 4);
            wavFile.close();
            Log.d(TAG,"writeDataSize,waveFile=" +wavFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    private static byte[] intToByteArray(int data) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(data).array();
    }

    private static byte[] shortToByteArray(short data) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(data).array();
    }
}
