package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StartActivity extends AppCompatActivity {

    Intent intent;
    SharedPreferences autoLogin;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar ab= getSupportActionBar();
        ab.hide();

        autoLogin = this.getSharedPreferences("com.example.barbaros.myapplication", Context.MODE_PRIVATE);


        mAuth= FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        if(autoLogin.getString("email",null)!=null && autoLogin.getString("password",null)!=null){
            autoSignIn();
            addCurrentUsername();
        }
        else if(autoLogin.getBoolean("autoLogin",false)!=false){
            login();
        }
    }


    public void goToLogin(View v){
        intent= new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }

    public void goToApp(View v){
        autoLogin.edit().putBoolean("autoLogin",true).apply();
        login();
    }

    private void autoSignIn(){
        mAuth.signInWithEmailAndPassword(autoLogin.getString("email",null),autoLogin.getString("password",null)).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void login(){
        intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    private void addCurrentUsername(){
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference("RaceYourWords/Kullanicilar");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();

                    if(autoLogin.getString("email",null).equals(hashMap.get("EMail").toString())){
                        autoLogin.edit().putString("username",hashMap.get("Username").toString()).apply();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
