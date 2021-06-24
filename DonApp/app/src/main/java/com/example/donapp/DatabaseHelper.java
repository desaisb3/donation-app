package com.example.donapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DonAppUsers.db"; //THE NAME HERE IS NOT CASE SENSITIVE
    public static final String TABLE_NAME= "DonAppUsers";
    public static final String COL_1 = "ID";
    public static final String COL_5 = "NAME";
    public static final String COL_6 = "LAST NAME";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "ACCOUNT";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //executes whatever query is passed as argument here..
        db.execSQL("create table " +  TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, ACCOUNTTYPE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);                //DROPS THE OLD TABLE IF EXISTS
        onCreate(db);
    }




    /*

    //EDITED VERSION
    //METHOD TO INSERT DATA WHEN THE USER ENTERS CORRECT USERNAME AND PASSWORD REQUIREMENTS, OTHERWISE RETURNS FALSE...
    public int insertData1(String username, String password,String confirmPassword, String accountType){
     SQLiteDatabase db = this.getWritableDatabase();
     boolean passwordCondition = isValid(password);
     int result = 0;

        ContentValues contentValues = new ContentValues();

        //EXECUTES ONLY IF THE USERNAME AND PASSWORD CONDITIONS MEET...
        //NEED TO FIX THIS PART..
        if(passwordCondition && !username.equals("") && password.equals(confirmPassword) && usernameCheck(username)) {
            contentValues.put(COL_2, username);
            contentValues.put(COL_3, password);
            contentValues.put(COL_4, accountType);
            //contentValues.put(COL_5, name);
            //contentValues.put(COL_6,lastname);
            result = (int) db.insert(TABLE_NAME,null,contentValues);
        }

        if(usernameCheck(username))
            result=-2;
        if(!passwordCondition)
            result=-3;
        if(password.equals(confirmPassword))
            result=-4;

        return result;

    }


     */


    //METHOD TO INSERT DATA WHEN THE USER ENTERS CORRECT USERNAME AND PASSWORD REQUIREMENTS, OTHERWISE RETURNS FALSE...
    public boolean insertData(String username, String password,String confirmPassword, String accountType){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean passwordCondition = isValid(password);
        long result;

        ContentValues contentValues = new ContentValues();

        //EXECUTES ONLY IF THE USERNAME AND PASSWORD CONDITIONS MEET...
        //NEED TO FIX THIS PART..
        if(passwordCondition && !username.equals("") && password.equals(confirmPassword) && usernameCheck(username)) {
            contentValues.put(COL_2, username);
            contentValues.put(COL_3, password);
            contentValues.put(COL_4, accountType);
            //contentValues.put(COL_5, name);
            //contentValues.put(COL_6,lastname);
            result = db.insert(TABLE_NAME,null,contentValues);
        }
        else{
            result = -1;
        }

        if (result==-1){
            return false;
        }
        else
            return true;
    }




    public boolean usernameCheck(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        boolean result = true;
        //WRITING THE REQUIRED QUERY
        String query = "select username from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);

        String usernameFromDatabase;
        if(cursor.moveToFirst()){
            do {
                usernameFromDatabase = cursor.getString(0);
                System.out.println(usernameFromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if(username.equals(usernameFromDatabase)){
                    result=false;


                }
            }while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        return result;

    }

    public String getAccountType(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        String result = "";
        //WRITING THE REQUIRED QUERY
        String query = "select username, password, account from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);


        String usernameFromDatabase,passwordFromDatabase;
        String accountTypefromDatabase;


        if(cursor.moveToFirst()){
            do {
                usernameFromDatabase = cursor.getString(0);
                passwordFromDatabase = cursor.getString(1);
                accountTypefromDatabase = cursor.getString(2);
                System.out.println(usernameFromDatabase);
                System.out.println(passwordFromDatabase);
                System.out.println(accountTypefromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if(username.equals(usernameFromDatabase) && (password.equals(passwordFromDatabase))){
                    result=accountTypefromDatabase;

                }
            }while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        return result;

    }



    //METHOD TO LOGIN TO HOME PAGE OF THE APP IF THE USERNAME AND PASSWORD FROM THE DATABASE MATCHES..
    public boolean comparePasswordAndLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        //WRITING THE REQUIRED QUERY
        String query = "select username, password from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);

        String usernameFromDatabase,passwordFromDatabase;

        //BY DEFAULT RETURNING FALSE IF NOT FOUND...
        boolean result = false;

        if(cursor.moveToFirst()){
            do {
                usernameFromDatabase = cursor.getString(0);
                passwordFromDatabase = cursor.getString(1);
                System.out.println(usernameFromDatabase);
                System.out.println(passwordFromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if(username.equals(usernameFromDatabase) && (password.equals(passwordFromDatabase))){
                    //IF MATCHES RETURN TRUE..
                    result= true;
                }
            }while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        //IF NOT FOUND, BY DEFAULT IT WILL RETURN FALSE...
        return result;
    }





    //METHOD TO SEARCH PASSWORD FOR THE SPECIFIED USERNAME...
    public String searchPassword(String username,String accountType){
        SQLiteDatabase db = this.getReadableDatabase();

        String result = "";
        //WRITING THE REQUIRED QUERY
        String query = "select username, password, account from " + TABLE_NAME;

        //CREATING A CURSOR TO ITERATE OVER THE ROWS IN THE COLUMN...
        Cursor cursor = db.rawQuery(query, null);


        String usernameFromDatabase,passwordFromDatabase;
        String accountTypefromDatabase;


        if(cursor.moveToFirst()){
            do {
                usernameFromDatabase = cursor.getString(0);
                passwordFromDatabase = cursor.getString(1);
                accountTypefromDatabase = cursor.getString(2);
                System.out.println(usernameFromDatabase);
                System.out.println(passwordFromDatabase);
                System.out.println(accountTypefromDatabase);

                //COMPARING THE USERNAMES AND PASSWORDS FROM THE DATABASE..
                if(username.equals(usernameFromDatabase)&&accountType.equals(accountTypefromDatabase)){
                    result=passwordFromDatabase;
                }
            }while (cursor.moveToNext()); //ITERATING OVER EACH USERNAMES AND PASSWORDS FROM THE DATABASE...
        }

        //CLOSING THE CURSOR WHEN REACHES THE LAST ROW OF USERNAME AND PASSWORDS...
        cursor.close();

        return result;
    }



    //METHOD TO CHECK IF THE PASSWORDS MATCHES THE REQUIRED CONSTRAINS.
    public static boolean isValid(String password){
        boolean atleastoneUpper = false;
        boolean atleastoneLower = false;
        boolean atleastoneDigit = false;

        if(password.length() < 8){
            return false;
        }

        for(int i=0;i<password.length();i++){
            if (Character.isUpperCase(password.charAt(i))){
                atleastoneUpper = true;
            }
            else if (Character.isLowerCase(password.charAt(i))){
                atleastoneLower = true;
            }
            else if (Character.isDigit(password.charAt(i))){
                atleastoneDigit = true;
            }
        }

        return (atleastoneDigit && atleastoneLower && atleastoneUpper);
    }
}
