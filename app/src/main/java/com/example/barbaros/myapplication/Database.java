package com.example.barbaros.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Barbaros on 12/7/2017.
 */

public class Database extends SQLiteOpenHelper {


    private static final int DataBase_Versiyon = 1;
    private static final String YourDictionary = "YourDictionary";
    private static final String OBJECT = "Object";
    private static final String Race = "Race";
    private static final String Col_ID = "ID";
    private static final String Col_Turkish = "Turkish";
    private static final String Col_Engilish = "Engilish";
    private static final String Col_Again = "Again";
    private static final String Col_ID_y = "ID";
    private static final String Col_Turkish_y = "Turkish";
    private static final String Col_Engilish_y = "Engilish";
    private static final String Col_Again_y = "Again";

    private static int soru_id=0;
    private static int soru_id2=0;

    List<Integer> soru_id_list = new ArrayList<Integer>();
    public Database(Context context) {
        super(context, YourDictionary, null, DataBase_Versiyon);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + OBJECT + "("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Col_Turkish + " TEXT,"
                + Col_Engilish + " TEXT,"
                + Col_Again + " INTEGER"
                + ")";

        String CREATE_TABLE2 = "CREATE TABLE " + Race + "("
                + Col_ID_y + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Col_Turkish_y + " TEXT,"
                + Col_Engilish_y + " TEXT,"
                + Col_Again_y + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);

    }

    public void InsertData(String Turkish, String Engilish,String table_name) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Col_Turkish, Turkish);
        value.put(Col_Engilish, Engilish);
        value.put(Col_Again,0);
        db.insert(table_name, null, value);
        db.close();
    }

    public void InsertData_Y(String Turkish, String Engilish,String table_name) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Col_Turkish_y, Turkish);
        value.put(Col_Engilish_y, Engilish);
        value.put(Col_Again_y,0);
        db.insert(table_name, null, value);
        db.close();
    }

    public int DatabaseLenght(String Table_Name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name, null);
        int count = cursor.getCount();
        return count;
    }


    public String QuestionSearch(String Table_Name) {

        Random rnd=new Random();

        String searchStr = "hello";
        boolean login=true;
        if(soru_id==1)
        {
            soru_id2++;
        }
        String soru = null;
        SQLiteDatabase db = this.getReadableDatabase();
        while(FinishQuestion(Table_Name)) {
            soru_id = rnd.nextInt(DatabaseLenght(Table_Name)) + 1;
            while(SameQuestion(soru_id)){
                soru_id = rnd.nextInt(DatabaseLenght(Table_Name)) + 1;
            }

            String sqlstring = "SELECT * FROM " + Table_Name + " WHERE ID=" + soru_id;//****
            Cursor cursor = db.rawQuery(sqlstring, null);//*****

            cursor.moveToFirst();//*** bu sadece ilk kaydı alır

            if (cursor.getCount() > 0 && cursor.getInt(cursor.getColumnIndex("Again")) == 0) {
                soru = cursor.getString(cursor.getColumnIndex("Engilish"));
                UpdateDatabase(soru_id,Table_Name);
                login = false;
                break;
            }

        }
        db.close();
        soru_id2=1;
        return soru;
    }

    public boolean FinishQuestion(String Table_Name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlstring = "SELECT  Again,Engilish FROM " + Table_Name;
        Cursor cursor = db.rawQuery(sqlstring, null);
        int id=1;
        int a=DatabaseLenght(Table_Name);
        cursor.moveToFirst();
        String veri;
        while(cursor.getInt(cursor.getColumnIndex("Again")) == 1){
            veri=cursor.getString(cursor.getColumnIndex("Engilish"));
            if(id==DatabaseLenght(Table_Name))
            {
                return false;
            }
            cursor.moveToNext();
            id++;
        }
        return true;
    }

    public boolean SameQuestion(int id)
    {
        for(int i=0;i<soru_id_list.size();i++)
        {
            if(soru_id_list.get(i)==id){
                return true;
            }
        }
        soru_id_list.add(id);
        return false;
    }

    public void UpdateDatabase(int id,String table_name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value=new ContentValues();

        value.put("Again",1);
        db.update(table_name, value, "ID" + " = ?",
                new String[] { String.valueOf(id) });

    }

    public int [] RandomNumber(int count)
    {
        int id=0;
        Random rnd=new Random();
        int randomnumbers[]=new int[4];

        for(int i=0;i<4;i++)
        {
            randomnumbers[i]=-1;
        }

        for (int i = 0; i < 4; i++) {
            id = ((rnd.nextInt(count))+1);
            for(int j=0;j<4;j++ ) {
                if (randomnumbers[j] == id) {
                    id=((rnd.nextInt(count))+1);
                    j=-1;
                }

            }
            randomnumbers[i]=id;
        }
        return randomnumbers;
    }

    public ArrayList<String> Options(String Table_Name) {

        int id = 0;
        ArrayList<String> options = new ArrayList<String>();
        Random rnd=new Random();
        int randomnumbers[]=new int[4];
        randomnumbers=RandomNumber(DatabaseLenght(Table_Name));
        for(int i=0;i<4;i++)
        {
            if(randomnumbers[i]==soru_id)
            {
                randomnumbers=RandomNumber(DatabaseLenght(Table_Name));
                i=-1;
            }

        }
        randomnumbers[0]=soru_id;
        SQLiteDatabase db = this.getReadableDatabase();
        for (int i = 0; i < 4; i++) {

            Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name + " WHERE ID=" + randomnumbers[i], null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                options.add(cursor.getString(cursor.getColumnIndex("Turkish")));
            }

        }
        db.close();
        return options;
    }

    public boolean TrueorFalse(String Table_Name,String result)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name + " WHERE ID=" + soru_id, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String answer=cursor.getString(cursor.getColumnIndex("Turkish"));

            if(result.equals(answer))
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        else
            return false;
    }

    public void AgainBeZero(String Table_Name) {

        int id = 0;
        while (id <= DatabaseLenght(Table_Name)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues value = new ContentValues();

            value.put("Again", 0);
            db.update(Table_Name, value, "ID" + " = ?",
                    new String[]{String.valueOf(id)});
            id++;
        }
    }

    public String GetOption(String Table_Name){
        String veri;
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlstring = "SELECT  Turkish,Engilish FROM " + Table_Name;//sql sorgusu
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Name + " WHERE ID=" + soru_id, null);
        cursor.moveToFirst();
        veri=cursor.getString(cursor.getColumnIndex("Turkish"));
        return veri;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
