package com.example.barbaros.raceyourwords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.barbaros.raceyourwords.R.id.button_A;
import static com.example.barbaros.raceyourwords.R.id.button_B;


public class TestActivity extends AppCompatActivity {

    Button button_a,button_b,button_c,button_d;
    TextView tv_question;
    Context context=this;
    ImageView imgtrue,imgfalse;
    String table_name;
    int sayac=0;
    boolean answering=true;
    static int score=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ActionBar ab= getSupportActionBar();
        ab.hide();

        button_a=(Button) findViewById(button_A);
        button_b=(Button) findViewById(button_B);
        button_c=(Button) findViewById(R.id.button_c);
        button_d=(Button) findViewById(R.id.button_d);
        tv_question=(TextView) findViewById(R.id.textView);

        imgtrue=(ImageView) findViewById(R.id.imageView);
        imgfalse=(ImageView) findViewById(R.id.imageView2);
       Bundle extras=getIntent().getExtras();

        table_name=extras.getString("key");
        CreateQuestion(tv_question, table_name);
        CreateOptions(table_name);
        sayac++;

    }



    public void CreateQuestion(TextView tw_question, String Table_Name)
    {
        Database data=new Database(context);
        String question=data.QuestionSearch(Table_Name);
        if(question!=null)
            tw_question.setText(question);
        else
        {
            Intent intent=new Intent(context,FinishActivity.class);
            data.AgainBeZero(Table_Name);
            startActivity(intent);
        }

    }

    public void CreateOptions(String Table_name){
        Database data=new Database(this);
        ArrayList<String> options=new ArrayList<String>();
        options=data.Options(Table_name);
        int id=0;
        int randomnumber[]=new int[4];
        randomnumber=data.RandomNumber(4);
        button_a.setText(options.get(randomnumber[0]-1));
        button_b.setText(options.get(randomnumber[1]-1));
        button_c.setText(options.get(randomnumber[2]-1));
        button_d.setText(options.get(randomnumber[3]-1));

    }

    public void Answer(View v){

        Database data=new Database(context);
        if(v.getId()==R.id.button_A&&answering){
            answering=false;

            if(data.TrueorFalse(table_name,button_a.getText().toString())){
                score++;

                imgtrue.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgtrue.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }

                            }
                        });
                    }
                }).start();

            }
            else{

                imgfalse.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgfalse.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();

            }
        }
        else if(v.getId()==R.id.button_B&&answering){
            answering=false;
            if(data.TrueorFalse(table_name,button_b.getText().toString())){
                score++;

                imgtrue.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgtrue.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();

            }
            else{
                imgfalse.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgfalse.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();
            }
        }

       else if(v.getId()==R.id.button_c&&answering){
            answering=false;
            if(data.TrueorFalse(table_name,button_c.getText().toString())){
                score++;

                imgtrue.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgtrue.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();

            }
            else{
                imgfalse.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgfalse.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();
            }
        }

     else   if(v.getId()==R.id.button_d&&answering){
            answering=false;
            if(data.TrueorFalse(table_name,button_d.getText().toString())){
                score++;

                imgtrue.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgtrue.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();

            }
            else{
                imgfalse.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgfalse.setVisibility(View.INVISIBLE);
                                answering=true;
                                if(sayac<10) {
                                    CreateQuestion(tv_question, table_name);
                                    CreateOptions(table_name);
                                    sayac++;
                                }
                                else
                                {
                                    Intent intent=new Intent(context,FinishActivity.class);
                                    Database data=new Database(context);
                                    data.AgainBeZero(table_name);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }).start();
            }

        }


    }


}
