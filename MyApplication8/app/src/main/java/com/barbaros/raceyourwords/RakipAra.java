package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RakipAra extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String uuid;
    EditText mEt_username;
    Intent intent;
    ArrayList<String> sendlist;
    SharedPreferences kullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rakip_ara);

        ActionBar ab= getSupportActionBar();
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        ab.setDisplayHomeAsUpEnabled(true);

        kullanici = this.getSharedPreferences("com.example.barbaros.myapplication", Context.MODE_PRIVATE);

        mEt_username=(EditText) findViewById(R.id.mEt_rakip);

        sendlist=new ArrayList<String>();
        firebaseDatabase=FirebaseDatabase.getInstance();
    }


    public void rakipleEsles(View v)
    {
        //uploadToFirebase(SendDatas(),mEt_username.getText().toString());
        downloadFromFirebase(mEt_username.getText().toString());
         Toast toast2 = Toast.makeText(getApplicationContext(), "Eşleşme Saglandı", Toast.LENGTH_SHORT);
        toast2.show();

    }
    public void yarisiBaslat(View V){
        String veri="Race";
        intent=new Intent(getApplicationContext(), TestActivity.class);
        intent.putExtra("key",veri);
        startActivity(intent);
    }
    private void downloadFromFirebase(String username){
        DatabaseReference downRef = firebaseDatabase.getReference("RaceYourWords/YarismaVeri/"+username+"/Kelimeler");
        downRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                    //System.out.println(hashMap.get("Kelime").toString()+"----"+hashMap.get("Anlami").toString());
                    SendData(hashMap.get("Kelime").toString(),hashMap.get("Anlami").toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendData(String kelime, String anlam) {

        SQLiteDatabase db;
        Database data = new Database(this);


            data.InsertData( anlam,kelime,"Race");
            /*Toast toast = Toast.makeText(getApplicationContext(), "Kayıt İşlemi Başarılı", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 250, 1500);
            toast.show();*/



    }
    public boolean ValueControl(String edt1,String edt2)
    {
        boolean valuecontrol=true;
        for(int i=0;i<edt1.length();i++) {
            valuecontrol=false;
            if ((edt1.charAt(i) >= 'A' && edt1.charAt(i) <= 'Z')||(edt1.charAt(i) >= 'a' && edt1.charAt(i) <= 'z')) {
                valuecontrol = true;
            }
            else{
                return false;
            }
        }
        for(int i=0;i<edt2.length();i++) {
            valuecontrol=false;
            if ((edt2.charAt(i) >= 'A' && edt2.charAt(i) <= 'Z')||(edt2.charAt(i) >= 'a' && edt2.charAt(i) <= 'z')) {
                valuecontrol = true;
            }
            else
            {
                return false;
            }
        }

        return valuecontrol;
    }

    public ArrayList<String> SendDatas(){
        Database data=new Database(this);
        for(int i=0;i<10;i++){
            data.AgainBeZero("Object");
            String veri=  data.QuestionSearch("Object");
            String veri2= data.GetOption("Object");
            sendlist.add(veri);
            sendlist.add(veri2);
        }
        return sendlist;
    }

    private void uploadToFirebase(ArrayList<String> data){
        firebaseDatabase= FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        for(int i=0; i<data.size();i+=2){
            uuid= UUID.randomUUID().toString();
            myRef.child("RaceYourWords").child("YarismaVeri").child(kullanici.getString("username",null).toString()).child("Kelimeler").child(uuid).child("Kelime").setValue(data.get(i));
            myRef.child("RaceYourWords").child("YarismaVeri").child(kullanici.getString("username",null).toString()).child("Kelimeler").child(uuid).child("Anlami").setValue(data.get(i+1));
        }
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
