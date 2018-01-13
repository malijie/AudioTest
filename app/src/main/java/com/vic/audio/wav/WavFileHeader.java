package com.vic.audio.wav;

/**
 * Created by malijie on 2018/1/9.
 */

public class WavFileHeader {
//
    public static final int WAV_CHUNKSIZE_OFFSET = 4;
    public static final int WAV_SUB_CHUNKSIZE2_OFFSET = 40;
    public static final int WAV_CHUNKSIZE_EXCLUDE_DATA = 36;

    public String mChunkID = "RIFF";
    public int mChunkSize = 0;
    public String mFormat = "WAVE";

    public String mSubChunk1ID = "fmt ";
    public int mSubChunk1Size = 16;
    public short mAudioFormat = 1;
    public short mNumChannel = 1;

    public int mSampleRate = 8000;
    public int mByteRate = 0;
    public short mBlockAlign = 0;//NumChannels * BitsPerSample/8
    public short mBitsPerSample = 8;

    public String mSubChunk2ID = "data";
    public int mSubChunk2Size = 0;



//
//
//
//    public WavFileHeader(){
//
//    }
//
//    public WavFileHeader(int sampleRateInHz, int channels, int bitsPerSample){
//        mSampleRate = sampleRateInHz;
//        mBitsPerSample = (short) bitsPerSample;
//        mNumChannel = (short) channels;
//        mByteRate = mSampleRate * mNumChannel * mBitsPerSample/8;
//        mBlockAlign = (short)(mNumChannel * mBitsPerSample/8);
//
//    }
//
//
//
//    public WavFileHeader(int sampleRateInHz, int channels, int bitsPerSample) {
//        mSampleRate = sampleRateInHz;
//        mBitsPerSample = (short) bitsPerSample;
//        mNumChannel = (short) channels;
//        mByteRate = mSampleRate * mNumChannel * mBitsPerSample / 8;
//        mBlockAlign = (short) (mNumChannel * mBitsPerSample / 8);
//    }
//
//    @Override
//    public String toString() {
//        return "WavFileHeader MLJ{" +
//                "mChunkID='" + mChunkID + '\'' +
//                ", mChunkSize=" + mChunkSize +
//                ", mFormat='" + mFormat + '\'' +
//                ", mSubChunk1ID='" + mSubChunk1ID + '\'' +
//                ", mSubchunk1Size=" + mSubChunk1Size +
//                ", mAudioFormat=" + mAudioFormat +
//                ", mNumChannel=" + mNumChannel +
//                ", mSampleRate=" + mSampleRate +
//                ", mByteRate=" + mByteRate +
//                ", mBlockAlign=" + mBlockAlign +
//                ", mBitsPerSample=" + mBitsPerSample +
//                ", mSubChunk2ID='" + mSubChunk2ID + '\'' +
//                ", mSubChunk2Size=" + mSubChunk2Size +
//                '}';
//    }



    public WavFileHeader() {

    }

    public WavFileHeader(int sampleRateInHz, int channels, int bitsPerSample) {
        mSampleRate = sampleRateInHz;
        mBitsPerSample = (short) bitsPerSample;
        mNumChannel = (short) channels;
        mByteRate = mSampleRate * mNumChannel * mBitsPerSample / 8;
        mBlockAlign = (short) (mNumChannel * mBitsPerSample / 8);
    }

    @Override
    public String toString() {
        return "WavFileHeader PROD{" +
                "mChunkID='" + mChunkID + '\'' +
                ", mChunkSize=" + mChunkSize +
                ", mFormat='" + mFormat + '\'' +
                ", mSubChunk1ID='" + mSubChunk1ID + '\'' +
                ", mSubChunk1Size=" + mSubChunk1Size +
                ", mAudioFormat=" + mAudioFormat +
                ", mNumChannel=" + mNumChannel +
                ", mSampleRate=" + mSampleRate +
                ", mByteRate=" + mByteRate +
                ", mBlockAlign=" + mBlockAlign +
                ", mBitsPerSample=" + mBitsPerSample +
                ", mSubChunk2ID='" + mSubChunk2ID + '\'' +
                ", mSubChunk2Size=" + mSubChunk2Size +
                '}';
    }
}
