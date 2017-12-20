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
 * Created by Tolga on 17.12.2017.
 */

public class LocalDictionaryAdapter extends ArrayAdapter<String> {
    private final ArrayList<String> list_word;
    private final ArrayList<String> list_meaning;
    private final Activity context;

    public LocalDictionaryAdapter(ArrayList<String> list_word, ArrayList<String> list_meaning, Activity context){
        super(context, R.layout.custom_view, list_word);
        this.list_word=list_word;
        this.list_meaning=list_meaning;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView= layoutInflater.inflate(R.layout.custom_view,null,true);

        TextView usernameText = (TextView) customView.findViewById(R.id.tv_word);
        TextView commentText = (TextView) customView.findViewById(R.id.tv_meaning);

        usernameText.setText(list_word.get(position));
        commentText.setText(list_meaning.get(position));

        return customView;
    }
}
