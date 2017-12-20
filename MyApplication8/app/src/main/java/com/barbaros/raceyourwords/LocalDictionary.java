package com.example.barbaros.raceyourwords;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class LocalDictionary extends AppCompatActivity {
  ListView list;
    String table_name;
    ImageButton btn_ekle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_dictionary);

        ActionBar ab= getSupportActionBar();
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle extras=getIntent().getExtras();
        table_name=extras.getString("key");
        list=(ListView)findViewById(R.id.list_localSozluk);
        btn_ekle=(ImageButton)findViewById(R.id.imageButton) ;
        ArrayDoldur();
        btn_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),InsertActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
    }

    public void ArrayDoldur(){
        String veri;
        String veri2;

        ArrayList<String>liste_e=new ArrayList<String>();
        ArrayList<String>liste_t=new ArrayList<String>();
        Database data=new Database(this);
        SQLiteDatabase db = data.getReadableDatabase();//****
        int count=data.DatabaseLenght(table_name);//databasein boyutunu ölçer
        if(count>0) {
            String sqlstring = "SELECT  Turkish,Engilish FROM " + table_name;//sql sorgusu
            Cursor cursor = db.rawQuery(sqlstring, null);//sorguyu çalıstırır
            cursor.moveToFirst();//ilk kaydı aldım
            veri = cursor.getString(cursor.getColumnIndex("Engilish"));//listeye ekledim
            veri2 = ": " +cursor.getString(cursor.getColumnIndex("Turkish"));//listeye ekledim

            liste_e.add(veri);
            liste_t.add(veri2);

            for (int i = 0; i < count - 1; i++) {
                cursor.moveToNext();//diğer kayıtlarıda alıyorum
                veri = cursor.getString(cursor.getColumnIndex("Engilish")) + ":";//okuduğumu attım
                veri2 = ": " +cursor.getString(cursor.getColumnIndex("Turkish"));//okuduğumu atttım
                liste_e.add(veri);//listeye ekledim
                liste_t.add(veri2);//listeye ekledim
            }
        }
        LocalDictionaryAdapter adapter = new LocalDictionaryAdapter(liste_e,liste_t,this);
        list.setAdapter(adapter);
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
