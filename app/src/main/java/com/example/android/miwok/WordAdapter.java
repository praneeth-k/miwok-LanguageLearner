package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    int colorResourceId;
    MediaPlayer mediaPlayer;
    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId)
    {
        super(context, 0, words);
        this.colorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent,false);
        int color = ContextCompat.getColor(getContext(), colorResourceId);
        listItemView.findViewById(R.id.words).setBackgroundColor(color);
        final Word word = getItem(position);
        assert word != null;
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english);
        englishTextView.setText(word.getDefaultTranslation());
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok);
        miwokTextView.setText(word.getMiwokTranslation());
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        if(word.hasImage()) {
            imageView.setImageResource(word.getImageSrc());
            imageView.setVisibility(View.VISIBLE);
        }
        else
            imageView.setVisibility(View.GONE);
        return listItemView;
    }
}
