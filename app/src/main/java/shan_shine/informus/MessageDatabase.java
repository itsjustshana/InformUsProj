package shan_shine.informus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shanakay on 5/13/2015.
 */

public class MessageDatabase extends SQLiteOpenHelper {

    Context context;


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MessageDatabase";

    // Todo table name
    private static final String TABLE_MESSAGE = "message_items";

    // Todo Table Columns names
    private static final String KEY_MESSID = "messageid";
    private static final String KEY_MESSTEXT = "body";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DATECREATED = "dateCreated";
    private static final String KEY_GROUPID = "groupID";
    private static final String KEY_STATUS= "status";


    public MessageDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating our initial tables
    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Construct a table for todo items


        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_MESSAGE + "("
                + KEY_MESSID + " VARCHAR(1000) PRIMARY KEY," + KEY_MESSTEXT + " VARCHAR(4000),"
                + KEY_EMAIL + " VARCHAR(200)," + KEY_DATECREATED + " VARCHAR(50),"
                + KEY_GROUPID + " VARCHAR(200)," + KEY_STATUS + " VARCHAR(1)" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    // Upgrading the database between versions
    // This method is called when database is upgraded like modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (newVersion == 1) {
            // Wipe older tables if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
            // Create tables again
            onCreate(db);
        }
    }

    public void addMessageItem(Message item) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();
        // Define values for each field
        ContentValues values = new ContentValues();
        values.put(KEY_MESSID, item.getMessageId());
        values.put(KEY_MESSTEXT, item.getMessageText());
        values.put(KEY_EMAIL, item.getEmail());
        values.put(KEY_DATECREATED, item.getDateCreated());
        values.put(KEY_GROUPID, item.getGroupId());
        values.put(KEY_STATUS, item.getRead());
        // Insert Row
        db.insertOrThrow(TABLE_MESSAGE, null, values);
        db.close(); // Closing database connection
    }


    public void addMessageItemStr(String id, String text, String email, String date, String group, String stat) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();
        // Define values for each field
        ContentValues values = new ContentValues();
        values.put(KEY_MESSID, id);
        values.put(KEY_MESSTEXT, text);
        values.put(KEY_EMAIL, email);
        values.put(KEY_DATECREATED, date);
        values.put(KEY_GROUPID, group);
        values.put(KEY_STATUS, stat);
        // Insert Row
        db.insertOrThrow(TABLE_MESSAGE, null, values);
        db.close(); // Closing database connection
    }




    public void addMessageItemR(Message item2) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();
        // Define values for each field
        ContentValues values = new ContentValues();

        Log.d("Hell-ID",item2.getMessageId());
        Log.d("Hell-Text",item2.getMessageText());
        Log.d("Hell-DateCreated",item2.getDateCreated());
        Log.d("Hell-Read",item2.getRead());
        Log.d("Hell-GroupId",item2.getGroupId());
        Log.d("Email", item2.getEmail());


        values.put(KEY_MESSID, item2.getMessageId());
        values.put(KEY_MESSTEXT, item2.getMessageText());
        values.put(KEY_EMAIL, item2.getEmail());
        values.put(KEY_DATECREATED, item2.getDateCreated());
        values.put(KEY_GROUPID, item2.getGroupId());
        values.put(KEY_STATUS, item2.getRead());
        // Insert Row
        db.insertOrThrow(TABLE_MESSAGE, null, values);
        db.close(); // Closing database connection
    }

    public Message getMessageItem(String id) {
        // Open database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query(TABLE_MESSAGE,  // TABLE
                new String[] { KEY_MESSID, KEY_MESSTEXT, KEY_GROUPID, KEY_DATECREATED, KEY_EMAIL, KEY_STATUS }, // SELECT
                KEY_MESSID + "= ?", new String[] { String.valueOf(id) },  // WHERE, ARGS
                null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        Message item = new Message(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(3));
        //item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        // return todo item
        return item;
    }

    public List<Message> getAllMessageItems() {
        List<Message> todoItems = new ArrayList<Message>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Message item = new Message(cursor.getString(0), cursor.getString(1),cursor.getString(2),cursor.getString(4),cursor.getString(3));
                // Adding todo item to list
               Log.d("What is the value here", cursor.getString(5));
                item.setRead(cursor.getString(5));
                todoItems.add(item);
            } while (cursor.moveToNext());
        }

        // return todo list
        return todoItems;
    }

    public int getMessageItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    public int updateTodoItem(Message item) {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Setup fields to update
        ContentValues values = new ContentValues();
        values.put(KEY_MESSID, item.getMessageId());
        values.put(KEY_MESSTEXT, item.getMessageText());
        values.put(KEY_EMAIL, item.getEmail());
        values.put(KEY_DATECREATED, item.getDateCreated());
        values.put(KEY_GROUPID, item.getGroupId());
        values.put(KEY_STATUS, item.getRead());
        // Updating row
        int result = db.update(TABLE_MESSAGE, values, KEY_MESSID + " = ?",
                new String[]{String.valueOf(item.getMessageId())});
        // Close the database
        db.close();
        return result;
    }

    public void deleteTodoItem(Message item) {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the record with the specified id
        db.delete(TABLE_MESSAGE, KEY_MESSID + " = ?",
                new String[]{String.valueOf(item.getMessageId())});
        // Close the database
        db.close();
    }

    public void updateRow(Message item, String value4) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSID, item.getMessageId());
        values.put(KEY_MESSTEXT, item.getMessageText());
        values.put(KEY_EMAIL, item.getEmail());
        values.put(KEY_DATECREATED, item.getDateCreated());
        values.put(KEY_GROUPID, item.getGroupId());
        values.put(KEY_STATUS, value4);

        db.update(TABLE_MESSAGE, values, KEY_MESSID + "= ?" + item.getMessageId(), null);
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE,null,null);
        db.execSQL("DELETE * FROM "+ TABLE_MESSAGE);
        db.execSQL("TRUNCATE table " + TABLE_MESSAGE);
        db.close();
    }



}