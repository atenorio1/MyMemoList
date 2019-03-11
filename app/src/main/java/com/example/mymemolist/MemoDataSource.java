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

            initialValues.put("memoText", c.getMemoData());


            didSucceed = database.insert("memo", null, initialValues) > 0;
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

            updateValues.put("memoText", c.getMemoData());

            didSucceed = database.update("memo", updateValues, "_id=" + rowId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -will return false if there is an exception
        }
        return didSucceed;
    }

    /*public int getLastContactId() {
        int lastId = -1;
        try {
            String query = "Select MAX(_id) from contact";
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

    public ArrayList<String> getContactName() {
        ArrayList<String> contactNames = new ArrayList<String>();
        try {
            String query = "Select contactname from contact";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contactNames.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            contactNames = new ArrayList<String>();
        }
        return contactNames;
    }

    public ArrayList<Contact> getContacts(String sortField, String sortOrder) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        try {
            String query = "SELECT  * FROM contact ORDER BY " + sortField + " " + sortOrder;

            Cursor cursor = database.rawQuery(query, null);

            Contact newContact;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newContact = new Contact();                                          //1
                newContact.setContactID(cursor.getInt(0));
                newContact.setContactName(cursor.getString(1));
                newContact.setStreetAddress(cursor.getString(2));
                newContact.setCity(cursor.getString(3));
                newContact.setState(cursor.getString(4));
                newContact.setZipCode(cursor.getString(5));
                newContact.setPhoneNumber(cursor.getString(6));
                newContact.setCellNumber(cursor.getString(7));
                newContact.setEMail(cursor.getString(8));
                Calendar calendar = Calendar.getInstance();                         //2
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
                newContact.setBirthday(calendar);

                contacts.add(newContact);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch (Exception e) {
            contacts = new ArrayList<Contact>();
        }
        return contacts;
    }

    public boolean deleteContact(int contactId) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("contact", "_id=" + contactId, null) > 0;
        }
        catch (Exception e) {
            //Do nothing -return value already set to false
        }
        return didDelete;
    }

    public Contact getSpecificContact(int contactId) {
        Contact contact = new Contact();
        String query = "SELECT  * FROM contact WHERE _id =" + contactId;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            contact.setContactID(cursor.getInt(0));
            contact.setContactName(cursor.getString(1));
            contact.setStreetAddress(cursor.getString(2));
            contact.setCity(cursor.getString(3));
            contact.setState(cursor.getString(4));
            contact.setZipCode(cursor.getString(5));
            contact.setPhoneNumber(cursor.getString(6));
            contact.setCellNumber(cursor.getString(7));
            contact.setEMail(cursor.getString(8));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(9)));
            contact.setBirthday(calendar);
            byte[] photo = cursor.getBlob(10);
            if (photo != null) {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                Bitmap thePicture= BitmapFactory.decodeStream(imageStream);
                contact.setPicture(thePicture);
            }

            cursor.close();
        }
        return contact;
    }*/

}
