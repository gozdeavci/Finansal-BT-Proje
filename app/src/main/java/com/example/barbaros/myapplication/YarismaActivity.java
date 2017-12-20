package com.example.barbaros.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class YarismaActivity extends AppCompatActivity {

    Context context=this;
    String [][] dizi=new String[5][2];
    ArrayList<String>sendlist=new ArrayList<String>();
    ArrayList<String>downloadList=new ArrayList<String>();
    boolean isdatacome=false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    String uuid;
    SharedPreferences kullanici;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yarisma);


        ActionBar ab= getSupportActionBar();
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        ab.setDisplayHomeAsUpEnabled(true);

        kullanici = this.getSharedPreferences("com.example.barbaros.myapplication", Context.MODE_PRIVATE);

        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
    }


    public void YarisSecim(View v){
        if(v.getId()==R.id.button_yaris){


            uploadToFirebase(SendDatas());
            Intent intent = new Intent(this,RakipAra.class);
            startActivity(intent);



        }
        if(v.getId()==R.id.button_pratik){
            //yarisDatalari(dizi);

            String veri="Object";
            Intent intent=new Intent(this,TestActivity.class);
            intent.putExtra("key",veri);
            startActivity(intent);
        }


        if(v.getId()==R.id.button_sina){
            String veri = "Race";
            Intent intent = new Intent(getApplicationContext(), TestActivity.class);
            intent.putExtra("key", veri);
            startActivity(intent);
        }
        if(v.getId()==R.id.button_sinirzorla){
            downloadFromFirebase();
            /*System.out.println("**********************************************");
            for(int i=0; i<downloadList.size();i+=2){
                System.out.println(downloadList.get(i)+": "+downloadList.get(i+1));
            }
            System.out.println("**********************************************");*/
        }
    }

    public void YarisDatalari(ArrayList<String>liste){
        Database data=new Database(context);
        for(int i=0;i<liste.size();i+=2){
            data.InsertData_Y(liste.get(i),liste.get(i+1),"Race");
        }

    }
    public ArrayList<String> SendDatas(){
        Database data=new Database(context);
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

    private void downloadFromFirebase(){
        DatabaseReference downRef = firebaseDatabase.getReference("Dictionary/Race/Username");
        downRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();

                    SendData(hashMap.get("Word").toString(),hashMap.get("Meaning").toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendData(String kelime, String anlam) {

        SQLiteDatabase db;
        Database data = new Database(context);

        if(ValueControl(kelime,anlam)) {
            data.InsertData( kelime,anlam,"Race");
            Toast toast = Toast.makeText(getApplicationContext(), "Kayıt İşlemi Başarılı", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 250, 1500);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Sadece harf kullanabilrisiniz", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 250, 250);
            toast.show();
        }

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
