package shan_shine.informus.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import shan_shine.informus.LoginItem;
import shan_shine.informus.LoginLog;

/**
 * Created by Shanakay on 5/16/2015.
 */

public class LogInLogDatabase extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Log";

    // Todo table name


    private static final String Table_Login ="loginLog";



    // Todo Table Columns names
    private static final String USERNAME = "username";
    private static final String PASSWORD = "stat";


    public LogInLogDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating our initial tables
    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Construct a table for todo items


        String CREATE_LOGIN_TABLE = "CREATE TABLE " + Table_Login + "("
                + USERNAME + " VARCHAR(255)," + PASSWORD + " VARCHAR(255)"+ ")";
        db.execSQL(CREATE_LOGIN_TABLE);

    }

    // Upgrading the database between versions
    // This method is called when database is upgraded like modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (newVersion == 1) {
            // Wipe older tables if existed
            db.execSQL("DROP TABLE IF EXISTS " + Table_Login);
            // Create tables again
            onCreate(db);
        }
    }

    public void addLoginItem(LoginLog item)
    {
        //Open Database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USERNAME, item.getUsername());
        values.put(PASSWORD, item.getPassword());

        db.insertOrThrow(Table_Login, null, values);
        db.close();
    }


    public LoginItem getLoginItem(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_Login, new String[]{USERNAME,PASSWORD},USERNAME+ "= ?",new String[]{String.valueOf(username)},null,null, "username ASC", "100");

        if (cursor != null)
            cursor.moveToFirst();

        LoginItem login = new LoginItem(cursor.getString(0), cursor.getString(1));

        return login;

    }


    public List<LoginLog> getAllLoginItems()
    {
        List<LoginLog> loginItems = new ArrayList<LoginLog>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_Login;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LoginLog item = new LoginLog(cursor.getString(0), cursor.getString(1));

                // Adding todo item to list
                loginItems.add(item);
            } while (cursor.moveToNext());
        }

        // return login list
        return loginItems;



    }

    public int getLoginCount(){
        String countQuery = "SELECT  * FROM " + Table_Login;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }


    public int updateLoginItem(LoginItem item)
    {

        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Setup fields to update
        ContentValues values = new ContentValues();
        //values.put(USERNAME, item.getUsername());
        values.put(PASSWORD,item.getPassword());
        // Updating row
        int result = db.update(Table_Login, values, USERNAME + " = ?",
                new String[]{String.valueOf(item.getUsername())});
        // Close the database
        db.close();
        return result;

    }


    public void deleteLoginItem( LoginItem item)
    {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the record with the specified id
        db.delete(Table_Login, USERNAME + " = ?",
                new String[] { String.valueOf(item.getUsername()) });
        // Close the database
        db.close();

    }

    public void deleteLoginItem2( String item)
    {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the record with the specified id
        db.delete(Table_Login, USERNAME + " = ?",
                new String[] { item });
        // Close the database
        db.close();
    }

}