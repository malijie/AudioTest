package com.vic.audio.wav;

import android.util.Log;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.vic.audio.data.ConstantData.AUDIO_FILE_PATH;

/**
 * Created by malijie on 2018/1/15.
 */

public class WavFileReader {
    private static final String TAG = WavFileHeader.class.getSimpleName();
    private DataInputStream dis = null;
    private WavFileHeader mWavFileHeader = null;

    public WavFileReader(){

    }

    public boolean openFile(){
        try {
            if(dis != null){
                closeFile();
            }
            dis = new DataInputStream(new FileInputStream(AUDIO_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return readWavData();
    }

    //关闭文件
    public void closeFile() {
        try {
            if(dis != null){
                dis.close();
                dis = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean readWavData() {
        if (dis == null) {
            return false;
        }

        WavFileHeader header = new WavFileHeader();

        byte[] intValue = new byte[4];
        byte[] shortValue = new byte[2];

        try {
            header.mChunkID = "" + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte();
            Log.d(TAG, "Read file chunkID:" + header.mChunkID);

            dis.read(intValue);
            header.mChunkSize = byteArrayToInt(intValue);
            Log.d(TAG, "Read file chunkSize:" + header.mChunkSize);

            header.mFormat = "" + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte();
            Log.d(TAG, "Read file format:" + header.mFormat);

            header.mSubChunk1ID = "" + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte();
            Log.d(TAG, "Read fmt chunkID:" + header.mSubChunk1ID);

            dis.read(intValue);
            header.mSubChunk1Size = byteArrayToInt(intValue);
            Log.d(TAG, "Read fmt chunkSize:" + header.mSubChunk1Size);

            dis.read(shortValue);
            header.mAudioFormat = byteArrayToShort(shortValue);
            Log.d(TAG, "Read audioFormat:" + header.mAudioFormat);

            dis.read(shortValue);
            header.mNumChannel = byteArrayToShort(shortValue);
            Log.d(TAG, "Read channel number:" + header.mNumChannel);

            dis.read(intValue);
            header.mSampleRate = byteArrayToInt(intValue);
            Log.d(TAG, "Read samplerate:" + header.mSampleRate);

            dis.read(intValue);
            header.mByteRate = byteArrayToInt(intValue);
            Log.d(TAG, "Read byterate:" + header.mByteRate);

            dis.read(shortValue);
            header.mBlockAlign = byteArrayToShort(shortValue);
            Log.d(TAG, "Read blockalign:" + header.mBlockAlign);

            dis.read(shortValue);
            header.mBitsPerSample = byteArrayToShort(shortValue);
            Log.d(TAG, "Read bitspersample:" + header.mBitsPerSample);

            header.mSubChunk2ID = "" + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte() + (char) dis.readByte();
            Log.d(TAG, "Read data chunkID:" + header.mSubChunk2ID);

            dis.read(intValue);
            header.mSubChunk2Size = byteArrayToInt(intValue);
            Log.d(TAG, "Read data chunkSize:" + header.mSubChunk2Size);

            Log.d(TAG, "Read wav file success !");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        mWavFileHeader = header;

        return true;
    }

    public int readData(byte[] buf,int offset,int count){
        if(dis == null || mWavFileHeader == null){
            return -1;
        }

        int nbyte = 0;
        try {
             nbyte = dis.read(buf,offset,count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nbyte;

    }

    private static short byteArrayToShort(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    private static int byteArrayToInt(byte[] b) {
        return ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

}
