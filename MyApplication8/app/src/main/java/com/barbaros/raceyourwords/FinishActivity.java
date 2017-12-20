package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.barbaros.raceyourwords.TestActivity.score;

public class FinishActivity extends AppCompatActivity {
TextView tv_bitti,tv_ss,tv_dc,tv_yc,tv_skor;
    Context context=this;
    Button btn_ana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        tv_bitti=(TextView)findViewById(R.id.textView_bitti);
        tv_ss=(TextView)findViewById(R.id.tv_sorusayisi);
        tv_dc=(TextView)findViewById(R.id.textView_dc);
        tv_yc=(TextView)findViewById(R.id.textView_yc);
        tv_skor=(TextView)findViewById(R.id.textView_skor);
        tv_ss.setText("10");
        tv_dc.setText(String.valueOf(score));
        tv_yc.setText(String.valueOf(10-score));
        tv_skor.setText(String.valueOf(score*10));
        btn_ana=(Button)findViewById(R.id.button_anasayfa);

        btn_ana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score=0;
              /*  Database data=new Database(context);bar
                data.Deletedata("Race");*/
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
