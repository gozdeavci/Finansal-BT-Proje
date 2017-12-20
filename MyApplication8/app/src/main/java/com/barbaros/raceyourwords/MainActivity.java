package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


     TextView tv_kullaniciadi;
     Button btn_sozluk,btn_yarisma,btn_arkekle,btn_arklist,btn_prof;
    Context context=this;
    String [][] dizi=new String[5][2];
    SharedPreferences logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar ab= getSupportActionBar();
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));

        logout=this.getSharedPreferences("com.example.barbaros.myapplication", Context.MODE_PRIVATE);


        btn_sozluk=(Button) findViewById(R.id.button_sozluk);
        btn_yarisma=(Button) findViewById(R.id.button_yarisma);

        btn_prof=(Button) findViewById(R.id.button_prof);
        tv_kullaniciadi=(TextView) findViewById(R.id.kullaniciadi);

        if(logout.getString("username",null)!=null){
            tv_kullaniciadi.setText("Hoşgeldin "+logout.getString("username",null).toString()+"!");
        }
        else{
            tv_kullaniciadi.setText("Hoşgeldin Misafir!");
        }



        dizi[0][0]="merhaba";
        dizi[0][1]="hello";
        dizi[1][0]="kesmek";
        dizi[1][1]="cut";
        dizi[2][0]="dönmek";
        dizi[2][1]="turn";
        dizi[3][0]="kalem";
        dizi[3][1]="pencil";
        dizi[4][0]="ucmak";
        dizi[4][1]="fly";


    }

    public void Anasecim(View v)
    {
        if(v.getId()==R.id.button_sozluk){
            String veri="Object";
            Intent intent=new Intent(this,LocalDictionary.class);
            intent.putExtra("key",veri);
            startActivity(intent);
        }
        if(v.getId()==R.id.button_yarisma){
      //
            Intent intent=new Intent(this,YarismaActivity.class);
            startActivity(intent);
        }
    }
    public void getOnline(View v){
        Intent intent=new Intent(this,OnlineDictionary.class);
        startActivity(intent);
    }
    public void logout(View v){
        logout.edit().remove("email").apply();
        logout.edit().remove("password").apply();
        logout.edit().remove("autoLogin").apply();
        logout.edit().remove("username").apply();

        /*Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);*/
        this.finish();
    }





}
