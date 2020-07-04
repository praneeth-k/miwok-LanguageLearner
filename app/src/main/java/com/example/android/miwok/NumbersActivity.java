package com.example.android.miwok;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //release media playback resources
                releaseMediaPlayer();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
                // reset media playback
                if(mediaPlayer!=null) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback
                // reset media playback
                if(mediaPlayer!=null) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // resume playback
                if(mediaPlayer!=null)
                    mediaPlayer.start();
            }
        }
    };
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        Toolbar myChildToolbar =  (Toolbar) findViewById(R.id.toolbar);
        setActionBar(myChildToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("One","Lutti", R.raw.number_one, R.drawable.number_one));
        words.add(new Word("Two","Otiiko", R.raw.number_two, R.drawable.number_two));
        words.add(new Word("Three","Tolookosu", R.raw.number_three, R.drawable.number_three));
        words.add(new Word("Four","Oyyisa", R.raw.number_four, R.drawable.number_four));
        words.add(new Word("Five","Massokka", R.raw.number_five, R.drawable.number_five));
        words.add(new Word("Six","Temmokka", R.raw.number_six, R.drawable.number_six));
        words.add(new Word("Seven","Kenekaku", R.raw.number_seven, R.drawable.number_seven));
        words.add(new Word("Eight","Kawinta", R.raw.number_eight, R.drawable.number_eight));
        words.add(new Word("Nine","Wo'e", R.raw.number_nine, R.drawable.number_nine));
        words.add(new Word("Ten","Na'aacha", R.raw.number_ten, R.drawable.number_ten));
        ListView listView = (ListView) findViewById(R.id.numbersRootView);
//        TextView textView;
//        for (String word: words)
//        {
//            textView = new TextView(this);
//            textView.setText(word);
//            assert rootView != null;
//            rootView.addView(textView);
//        }
        WordAdapter wordAdapter = new WordAdapter(this, words, R.color.category_numbers);
        assert listView != null;
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request temp focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    mediaPlayer = MediaPlayer.create(getBaseContext(), word.getAudioSrc());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer()
    {
        if(mediaPlayer != null)
        {
            //if media player is not null release the audio player resources;
            mediaPlayer.release();;
            mediaPlayer = null;
            //unregister the onAudioFocusChange listener when playback is done
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }
}