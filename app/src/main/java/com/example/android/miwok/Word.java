package com.example.android.miwok;

public class Word {
    private String english;
    private String miwok;
    private int audioSrc;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int imageSrc = NO_IMAGE_PROVIDED;
    public Word(String english, String miwok, int audioSrc)
    {
        this.english = english;
        this.miwok = miwok;
        this.audioSrc = audioSrc;
    }
    public Word(String english, String miwok, int audioSrc, int imageSrc)
    {
        this.english = english;
        this.miwok = miwok;
        this.audioSrc = audioSrc;
        this.imageSrc = imageSrc;
    }

    public String getDefaultTranslation() {
        return english;
    }

    public String getMiwokTranslation() {
        return miwok;
    }

    public int getImageSrc()
    {
        return imageSrc;
    }
    public boolean hasImage()
    {
        return imageSrc != NO_IMAGE_PROVIDED;
    }
    public int getAudioSrc()
    {
        return audioSrc;
    }
}
