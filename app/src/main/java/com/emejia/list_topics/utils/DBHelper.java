package com.emejia.list_topics.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.emejia.list_topics.model.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emejia on 08/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_emejia_listTopics";
    private static final String TABLE_TOPICS = "topics";
    private Context context;
    private String CREATE_TABLE_TOPICS = "CREATE TABLE "+ TABLE_TOPICS
            +" ( "+ Keys.IDTOPIC_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Keys.HEAD_KEY + " VARCHAR, "
            + Keys.DESC_KEY + " VARCHAR, "
            + Keys.URL_KEY + " VARCHAR, "
            +Keys.IMAGE_KEY +" VARCHAR )";
    private static DBHelper helper;


      public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    public static synchronized DBHelper getInstance(Context context) {
        if (helper == null) {
            helper = new DBHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_TOPICS = "CREATE TABLE " + TABLE_TOPICS
                + " ( " + Keys.IDTOPIC_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Keys.HEAD_KEY + " VARCHAR, "
                + Keys.DESC_KEY + " VARCHAR, "
                + Keys.URL_KEY + " VARCHAR, "
                + Keys.IMAGE_KEY + " VARCHAR ) ";

        db.execSQL(CREATE_TABLE_TOPICS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_TOPICS);
            onCreate(db);
        }

    }

    public List<ListItem> ListItems() {


        SQLiteDatabase db = this.getWritableDatabase();
        List<ListItem> items = new ArrayList<ListItem>();

        String selectQuery = "SELECT * FROM " + TABLE_TOPICS +
                //" WHERE " + Keys.IDTOPIC_KEY  + " = '" + user + "'"  +
                " ORDER BY " + Keys.HEAD_KEY + " DESC ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {

            if (cursor.moveToFirst()) {
                do {
                    ListItem item = new ListItem();

                    item.setHead(cursor.getString(0));
                    item.setDesc(cursor.getString(1));
                    item.setUrl(cursor.getString(2));
                    item.setImage(cursor.getString(3));

                    items.add(item);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return items;

    }


    public boolean addItems(ListItem item) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.putNull(Keys.IDTOPIC_KEY);
        values.put(Keys.HEAD_KEY, item.getHead());
        values.put(Keys.DESC_KEY, item.getDesc());
        values.put(Keys.URL_KEY, item.getUrl());
        values.put(Keys.IMAGE_KEY, item.getImage());

        int id = (int) db.insert(TABLE_TOPICS, null, values);
        return id > 0 ? true : false;
    }

}



