package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AppMain extends AppCompatActivity {

    SharedPreferences logout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef,downRef,userRef;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    String uuid,currentUsername, showUsername;
    int wordIndex;
    Context context;
    HashMap<String, String> hashMap;

    ArrayList<String> questionList;
    ArrayList<String> meaningList;
    AdapterClass adapter;
    ListView listView;
    boolean isNotDataInsert=true;
    String[][] wordList = new String[5][2];
    ArrayList<String> word, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        wordIndex=0;
        currentUsername=null;

        logout=this.getSharedPreferences("com.example.barbaros.myapplication", Context.MODE_PRIVATE);

        word=new ArrayList<String>();
        username=new ArrayList<String>();


        meaningList=new ArrayList<String>();
        adapter=new AdapterClass(questionList,meaningList,this);
        listView=(ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        getDataFromFB();
    }

    protected void getDataFromFB() {


            firebaseDatabase = FirebaseDatabase.getInstance();
            questionList = new ArrayList<String>();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Dictionary/Race/Username");
            databaseReference.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        hashMap = (HashMap<String, String>) ds.getValue();
                        questionList.add(hashMap.get("Word"));
                        questionList.add(hashMap.get("Meaning"));
                        adapter.notifyDataSetChanged();
                    }
                    isNotDataInsert=false;
                    YarismaActivity yaris = new YarismaActivity();
                    yaris.YarisDatalari(questionList);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    public void uploadToFirebase(ArrayList<String>liste){

        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        for(int i=0; i<liste.size();i+=2){
            uuid= UUID.randomUUID().toString();
            myRef.child("Dictionary").child("Race").child("Username").child(uuid).child("Word").setValue(liste.get(i));
            myRef.child("Dictionary").child("Race").child("Username").child(uuid).child("Meaning").setValue(liste.get(i+1));

        }

    }

    public void downloadFromFirebase(View v){
        downRef=firebaseDatabase.getReference("Dictionary/Race/Username");
        downRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    hashMap= (HashMap<String, String>) ds.getValue();

                    word.add(hashMap.get("Word"));
                    word.add(hashMap.get("Meaning"));

                    //AppMain.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        showWordList(word);
    }

    public void signOut(View v){
        logout.edit().remove("email").apply();
        logout.edit().remove("password").apply();
        logout.edit().remove("autoLogin").apply();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void getUsername(View v){
        showUsername=username.get(0);
        Toast.makeText(this,showUsername, Toast.LENGTH_SHORT).show();
    }

    private void addValueToList(){
        wordList[0][0]="Apple";
        wordList[0][1]="Apple";

        wordList[1][0]="Eyşan";
        wordList[1][1]="Kaşar";

        wordList[2][0]="Report";
        wordList[2][1]="Rapor";

        wordList[3][0]="Hilti";
        wordList[3][1]="Necmi";

        wordList[4][0]="Hayri";
        wordList[4][1]="Pıtırcık";
    }

    private void showWordList(ArrayList<String> d){
        for(int j=0; j<d.size();j++){
            if(j%2==0){
                System.out.println("Kelime ----->"+d.get(j));
            }else{
                System.out.println("Anlam ----->"+d.get(j));
            }
        }
    }

    public ArrayList<String> getList(){
       // getDataFromFB();
        return questionList;
    }



    private void getCurrentUsername(){
        final FirebaseUser currentUser=mAuth.getCurrentUser();
        Toast.makeText(this,currentUser.getEmail().toString(),Toast.LENGTH_SHORT).show();
        userRef=firebaseDatabase.getReference("Dictionary");
        userRef.addValueEventListener((new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.child("Users").getChildren()){
                    hashMap= (HashMap<String, String>) ds.getValue();
                    System.out.println("         Current User Email: "+ currentUser.getEmail());
                    System.out.println("         User Email: "+ hashMap.get("EMail"));
                    System.out.println("         Username: "+ hashMap.get("Username"));
                    //Toast.makeText(getApplicationContext(),hashMap.get("Username").toString(),Toast.LENGTH_SHORT).show();

                    if(currentUser.getEmail().toString().equals(hashMap.get("EMail").toString())){
                        currentUsername= hashMap.get("Username").toString();
                        username.add(hashMap.get("Username"));
                        //Toast.makeText(getApplicationContext(),currentUsername,Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }));
    }
}

