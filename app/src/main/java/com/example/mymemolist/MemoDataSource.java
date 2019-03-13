package com.example.mymemolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


public class MemoDataSource {

    private SQLiteDatabase database;
    private MemoDBHelper dbHelper;

    public MemoDataSource(Context context) {
        dbHelper = new MemoDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertMemo(Memo c) {
        boolean didSucceed = false;
        try {

            ContentValues initialValues = new ContentValues();

            initialValues.put("memoTitle", c.getMemoTitle());
            initialValues.put("memoPriority", c.getMemoPriority());
            initialValues.put("memoText", c.getMemoData());
            initialValues.put("memoDate", c.getMemoDate());


            didSucceed = database.insertOrThrow("memo3", null, initialValues) > 0;

        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public boolean updateMemo(Memo c) {
        boolean didSucceed = false;
        try {
            Long rowId = (long) c.getMemoID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("memoTitle", c.getMemoTitle());
            updateValues.put("memoPriority", c.getMemoPriority());
            updateValues.put("memoText", c.getMemoData());


            didSucceed = database.update("memo3", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    public int getLastMemoID() {
        int lastId = -1;
        try {
            String query = "Select MAX(_id) from memo3";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }

    public ArrayList<String> getMemoData() {
        ArrayList<String> memoDatas = new ArrayList<String>();
        try {
            String query = "Select memoText from memo3";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                memoDatas.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memoDatas = new ArrayList<String>();
        }
        return memoDatas;
    }

    public ArrayList<Memo> getMemo(String sortField, String sortOrder) {
        ArrayList<Memo> memos = new ArrayList<Memo>();
        try {
            String query = "SELECT  * FROM memo3 " ;
                    //ORDER BY " + sortField + " " + sortOrder;

            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();                                          //1
                newMemo.setMemoID(cursor.getInt(0));
                newMemo.setMemoTitle(cursor.getString(1));
                newMemo.setMemoPriority(cursor.getString(2));
                newMemo.setMemoData(cursor.getString(3));
                //newMemo.setMemoDate(cursor.getString(4));

                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            memos = new ArrayList<Memo>();
        }
        return memos;
    }

    public boolean deleteMemo(int memoID) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("memo3", "_id=" + memoID, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }

    public Memo getSpecificMemo(int memoID) {
        Memo memo = new Memo();
        String query = "SELECT  * FROM memo3 WHERE _id =" + memoID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            memo.setMemoID(cursor.getInt(0));
            memo.setMemoTitle(cursor.getString(1));
            memo.setMemoPriority(cursor.getString(2));
            memo.setMemoData(cursor.getString(3));
            //memo.setMemoDate(cursor.getString(4));


            cursor.close();
        }
        return memo;
    }

}
