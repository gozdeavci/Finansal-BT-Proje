package com.example.barbaros.myapplication;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tolga on 10.12.2017.
 */

public class AdapterClass extends ArrayAdapter<String> {
    private final ArrayList<String> word;
    private final ArrayList<String> meaning;
    private final Activity context;

    public AdapterClass(ArrayList<String> word, ArrayList<String> meaning, Activity context) {
        super(context,R.layout.custom_view,word);
        this.word = word;
        this.meaning = meaning;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.custom_view,null,true);

        TextView tv_word = (TextView) customView.findViewById(R.id.tv_word);
        TextView tv_meaning = (TextView) customView.findViewById(R.id.tv_meaning);

        tv_word.setText(word.get(position));
        tv_meaning.setText(meaning.get(position));

        return customView;
    }
}
