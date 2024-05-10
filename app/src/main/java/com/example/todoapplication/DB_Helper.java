package com.example.todoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB_Helper extends SQLiteOpenHelper {
    Context context;
    public DB_Helper(Context context) {
        super(context, "db_todo", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tbl_todo(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT, city TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean insertData(String name, String number, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("number",number);
        cv.put("city",city);
        long isInser = db.insert("tbl_todo",null,cv);
        if(isInser >= 0){
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<Todo_Model> selectAll(){
        ArrayList<Todo_Model> todosArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_todo",null);
        if(cursor.moveToFirst()){
            do {
                todosArrayList.add(new Todo_Model(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3))
                );
            } while (cursor.moveToNext());
        }
        return todosArrayList;
    }

    public boolean deleteData(int position) {
        SQLiteDatabase db = this.getWritableDatabase();
        int isDelete = db.delete("tbl_todo","id="+position,new String[]{});
        if(isDelete>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateData(int position, String name, String number, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("number",number);
        cv.put("city",city);
        int isUpdate = db.update("tbl_todo",cv,"id="+position,new String[]{});
        if(isUpdate > 0){
            return true;
        }else{
            return false;
        }
    }
}