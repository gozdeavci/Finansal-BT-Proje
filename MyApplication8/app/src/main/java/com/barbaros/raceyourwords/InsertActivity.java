package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    EditText et_kelime,et_anlamlari;
    TextView tv_kelime,tv_anlamlari,tv_not;
    Button button_kaydet;
    Context context=this;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        ActionBar ab= getSupportActionBar();
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));
        ab.setDisplayHomeAsUpEnabled(true);

        et_kelime=(EditText) findViewById(R.id.et_kelime);
        et_anlamlari=(EditText) findViewById(R.id.et_Anlamlari);
        tv_kelime=(TextView) findViewById(R.id.tv_kelime);
        tv_anlamlari=(TextView) findViewById(R.id.tv_anlamlari);
        button_kaydet=(Button)findViewById(R.id.bt_kaydet);

    }

    public void Islem(View v)
    {
        if(v.getId()==R.id.bt_kaydet){
            SendData();

        }
    }


    private void SendData() {

        SQLiteDatabase db;
        Database data = new Database(context);


            data.InsertData( et_anlamlari.getText().toString(), et_kelime.getText().toString(),"Object");
            Toast toast = Toast.makeText(getApplicationContext(), "Kayıt İşlemi Başarılı", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, 250, 1500);
            toast.show();

        this.finish();
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
