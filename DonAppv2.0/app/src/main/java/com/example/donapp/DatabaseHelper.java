package com.example.donapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String TAG = "DatabaseHelper";
    
    public static final String DATABASE_NAME = "DonAppUsers.db"; //THE NAME HERE IS NOT CASE SENSITIVE
    public static final String TABLE_NAME = "DonAppUsers";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "ACCOUNT";
    public static final String TABLE_NAME_2 = "USERINFORMATION";
    public static final String COL_FIRST_NAME = "FIRSTNAME";
    public static final String COL_LAST_NAME = "LASTNAME";
    public static final String COL_EMAIL = "EMAIL";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //executes whatever query is passed as argument here..
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, USERNAME TEXT, PASSWORD TEXT, ACCOUNT TEXT)");
        db.execSQL("create table " + TABLE_NAME_2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, EMAIL TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }


    //METHOD TO INSERT DATA WHEN THE USER ENTERS CORRECT USERNAME AND PASSWORD REQUIREMENTS, OTHERWISE RETURNS FALSE...
    public boolean insertData1(String firstname, String lastname, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result;

        ContentValues contentValues = new ContentValues();

        //EXECUTES ONLY IF THE USERNAME AND PASSWORD CONDITIONS MEET...
        //NEED TO FIX THIS PART..
        contentValues.put(COL_FIRST_NAME, firstname);
        contentValues.put(COL_LAST_NAME, lastname);
        contentValues.put(COL_EMAIL, email);
        result = db.insert(TABLE_NAME_2, null, contentValues);

        return result != -1;
    }


    //METHOD TO INSERT DATA WHEN THE USER ENTERS CORRECT USERNAME AND PASSWORD REQUIREMENTS, OTHERWISE RETURNS FALSE...
    public boolean insertData(String firstName, String lastName, String username, String password, String confirmPassword, String accountType) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result;

        ContentValues contentValues = new ContentValues();

        //EXECUTES ONLY IF THE USERNAME AND PASSWORD CONDITIONS MEET...
        contentValues.put(COL_FIRST_NAME, firstName);
        contentValues.put(COL_LAST_NAME, lastName);
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        contentValues.put(COL_4, accountType);
        result = db.insert(TABLE_NAME, null, contentValues);


        return result != -1;
    }
    
     // UPDATE THE DATABASE OF THE USER (FIRST NAME, LAST NAME, EMAIL)
    public void updateUserInfo(DatabaseHelper DOP,Integer id, String newName, String newSurname, String newEmail){
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FIRST_NAME,newName);
        values.put(COL_LAST_NAME,newSurname);
        values.put(COL_EMAIL,newEmail);
        SQ.update(TABLE_NAME_2,values, COL_1 +" = " + id,null);
    }

    // Get the Integer ID for invokation 
     public Integer getID (String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Integer result = null;
        //WRITING THE REQUIRED QUERY
        String query = "select id, username, password, account from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);


        String usernameFromDatabase, passwordFromDatabase;
        String accountTypefromDatabase;
        Integer ID = null;

        if (cursor.moveToFirst()) {
            do {
                ID = cursor.getInt(0);
                usernameFromDatabase = cursor.getString(1);
                passwordFromDatabase = cursor.getString(2);
                accountTypefromDatabase = cursor.getString(3);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if (username.equals(usernameFromDatabase) && password.equals(passwordFromDatabase)) {
                    result = ID;
                }
            } while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        return result;
    }
    
    

    //METHOD TO CHECK IF THE USERNAME ENTERED ALREADY EXISTS IN THE DATABASE, IF IT DOES THEN THE METHOD RETURNS FALSE...
    public boolean usernameCheck(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        boolean result = false;

        //WRITING THE REQUIRED QUERY
        String query = "select username from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);

        String usernameFromDatabase;

        if (cursor.moveToFirst()) {
            do {
                usernameFromDatabase = cursor.getString(0);
                System.out.println(usernameFromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if (username.equals(usernameFromDatabase)) {
                    result = true;
                }
            } while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        return result;

    }

    public String getAccountType(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String result = "";

        //WRITING THE REQUIRED QUERY
        String query = "select username, password, account from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);


        String usernameFromDatabase, passwordFromDatabase;
        String accountTypefromDatabase;


        if (cursor.moveToFirst()) {
            do {
                usernameFromDatabase = cursor.getString(0);
                passwordFromDatabase = cursor.getString(1);
                accountTypefromDatabase = cursor.getString(2);
                System.out.println(usernameFromDatabase);
                System.out.println(passwordFromDatabase);
                System.out.println(accountTypefromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if (username.equals(usernameFromDatabase) && (password.equals(passwordFromDatabase))) {
                    result = accountTypefromDatabase;

                }
            } while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        return result;

    }


    //METHOD TO LOGIN TO HOME PAGE OF THE APP IF THE USERNAME AND PASSWORD FROM THE DATABASE MATCHES..
    public boolean comparePasswordAndLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        //WRITING THE REQUIRED QUERY
        String query = "select username, password from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);

        String usernameFromDatabase, passwordFromDatabase;

        //BY DEFAULT RETURNING FALSE IF NOT FOUND...
        boolean result = false;

        if (cursor.moveToFirst()) {
            do {
                usernameFromDatabase = cursor.getString(0);
                passwordFromDatabase = cursor.getString(1);
                System.out.println(usernameFromDatabase);
                System.out.println(passwordFromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if (username.equals(usernameFromDatabase) && (password.equals(passwordFromDatabase))) {
                    //IF MATCHES RETURN TRUE..
                    result = true;
                }
            } while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        //IF NOT FOUND, BY DEFAULT IT WILL RETURN FALSE...
        return result;
    }


    //METHOD TO SEARCH PASSWORD FOR THE SPECIFIED USERNAME...
    public String searchPassword(String username, String accountType) {
        SQLiteDatabase db = this.getReadableDatabase();

        String result = "";
        //WRITING THE REQUIRED QUERY
        String query = "select username, password, account from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);


        String usernameFromDatabase, passwordFromDatabase;
        String accountTypefromDatabase;


        if (cursor.moveToFirst()) {
            do {
                usernameFromDatabase = cursor.getString(0);
                passwordFromDatabase = cursor.getString(1);
                accountTypefromDatabase = cursor.getString(2);
                System.out.println(usernameFromDatabase);
                System.out.println(passwordFromDatabase);
                System.out.println(accountTypefromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if (username.equals(usernameFromDatabase) && accountType.equals(accountTypefromDatabase)) {
                    result = passwordFromDatabase;
                }
            } while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        return result;
    }
    
    // retrive information from the database
    public Cursor getInformation(com.example.donapp.DatabaseHelper dop,Integer ID) {
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] coloumns = {COL_FIRST_NAME,COL_LAST_NAME,COL_EMAIL};
        Cursor CR = SQ.query(TABLE_NAME_2,coloumns,COL_1 + " = " + ID,null,null,null,null);
        return CR;
    }

    public void deleteAccount(Integer ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_2 + " WHERE "
                + COL_1 +" = '" + ID + "'";
        Log.d(TAG,"deleteName: " + query);
        Log.d(TAG,"deleteName: " + ID + "from database.");
        db.execSQL(query);
    }
    public void deleteAccount2(Integer ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL_1 +" = '" + ID + "'";
        Log.d(TAG,"deleteName: " + query);
        Log.d(TAG,"deleteName: " + ID + "from database.");
        db.execSQL(query);
    }

    
    
}
