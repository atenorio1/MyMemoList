package com.example.mymemolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Memo3.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String CREATE_TABLE_MEMO =
            "create table memo3" +
                    " (_id integer primary key autoincrement, "
                        + "memoText text, memoPriority text, memoDate date, memoTitle text);";

    public MemoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_MEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.w(ContactDBHelper.class.getName(), "Upgrading database from
// version " + oldVersion + " to "
//                + newVersion + ", which will destroy all old data");
//  db.execSQL("DROP TABLE IF EXISTS memo");
//  onCreate(db);
        try {
            db.execSQL("ALTER TABLE memo ADD COLUMN contactphoto blob");
        }
        catch (Exception e) {
            //do nothing
        }
    }

}
