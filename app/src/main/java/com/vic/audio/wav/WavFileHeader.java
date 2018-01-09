package com.vic.audio.wav;

/**
 * Created by malijie on 2018/1/9.
 */

public class WavFileHeader {

    public static final int WAV_CHUNKSIZE_OFFSET = 4;
    public static final int WAV_SUB_CHUNKSIZE2_OFFSET = 40;
    public static final int WAV_CHUNKSIZE_EXCLUDE_DATA = 36;

    public String mChunkID = "RIFF";
    public int mChunkSize = 0;
    public String mFormat = "WAVE";

    public String mSubchunk1ID = "fmt";
    public int mSubchunk1Size = 16;
    public short mAudioFormat = 1;
    public short mNumChannels = 1;
    public int mSampleRate = 44100;
    public int mByteRate = 0;
    public short mBlockAlign = 0;//NumChannels * BitsPerSample/8
    public short mBitsPerSample = 8;

    public String mSubChunk2ID = "data";
    public int mSubChunk2Size = 0;

    public WavFileHeader(int sampleRateInHz, int channels, int bitsPerSample){
        mSampleRate = sampleRateInHz;
        mBitsPerSample = (short) bitsPerSample;
        mNumChannels = (short) channels;
        mByteRate = mSampleRate * mNumChannels * mBitsPerSample/8;
        mBlockAlign = (short)(mNumChannels * mBitsPerSample/8);

    }

}
