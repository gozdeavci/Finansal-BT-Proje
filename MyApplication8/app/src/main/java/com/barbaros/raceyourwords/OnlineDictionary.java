package com.example.barbaros.raceyourwords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OnlineDictionary extends AppCompatActivity {
    AdapterClass adapter;
    ArrayList<String> wordList;
    ArrayList<String> meaningList;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_dictionary);
        adapter=new AdapterClass(wordList,meaningList,this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        getDataFromFB();
    }

    protected void getDataFromFB() {
        firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Dictionary/Race/Username");
        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    wordList.add(hashMap.get("Word"));
                    meaningList.add(hashMap.get("Meaning"));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
