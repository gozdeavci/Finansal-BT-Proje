package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String uuid;
    SharedPreferences login;

    EditText mEt_regEmail, mEt_regUsername, mEt_regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar ab= getSupportActionBar();
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        ab.setDisplayHomeAsUpEnabled(true);

        login=this.getSharedPreferences("com.example.barbaros.myapplication", Context.MODE_PRIVATE);

        mEt_regEmail = (EditText) findViewById(R.id.mEt_regEmail);
        mEt_regUsername = (EditText) findViewById(R.id.mEt_regUsername);
        mEt_regPassword = (EditText) findViewById(R.id.mEt_regPassword);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
    }

    public void signUp(View v){
        mAuth.createUserWithEmailAndPassword(mEt_regEmail.getText().toString(),mEt_regPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    uuid= UUID.randomUUID().toString();

                    login.edit().putString("email",mEt_regEmail.getText().toString()).apply();
                    login.edit().putString("password",mEt_regPassword.getText().toString()).apply();
                    login.edit().putString("username", mEt_regUsername.getText().toString()).apply();

                    myRef.child("RaceYourWords").child("Kullanicilar").child(uuid).child("EMail").setValue(mEt_regEmail.getText().toString());
                    myRef.child("RaceYourWords").child("Kullanicilar").child(uuid).child("Username").setValue(mEt_regUsername.getText().toString());

                    Toast.makeText(getApplicationContext(), "Welcome "+mEt_regUsername.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
