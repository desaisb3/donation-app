package com.example.donapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import RecipientBottomNavigationFragment.SettingsFragment;

public class AccountUpdate extends AppCompatActivity {

    Integer ID;
    String AccountName;
    EditText NameChange;
    EditText SurnameChange;
    EditText EmailChange;
    Context CTX = this;
    Button changeInfo ;
    String newNameChange,newSurnameChange,newEmailChange;
    DatabaseHelper DOP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update);
        setAccountName();

        Toast.makeText(getBaseContext(),"What do you want to change user "+ AccountName + " ?",Toast.LENGTH_SHORT).show();
        NameChange = findViewById(R.id.editTextChangeName);
        SurnameChange = findViewById(R.id.editTextChangeSurname);
        EmailChange = findViewById(R.id.editTextChangeEmail);

        changeInfo = findViewById(R.id.buttonChangeInfo);
        showCurrentInformation();

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newNameChange = NameChange.getText().toString();
                newSurnameChange = SurnameChange.getText().toString();
                newEmailChange = EmailChange.getText().toString();

                DOP = new DatabaseHelper(CTX);
                DOP.updateUserInfo(DOP,ID,newNameChange, newSurnameChange, newEmailChange);
                loginPage();
                Toast.makeText(getBaseContext(), "Your informations has been changed", Toast.LENGTH_SHORT).show();
            }
            //}
        });
    }
    // set The USer Account depending from which fragment it recives the information (Donator / Recipient)
    public void setAccountName(){
        if (RecipientMainScreen.AccountUserRecipient != null){
            AccountName = RecipientMainScreen.AccountUserRecipient;
            ID = RecipientMainScreen.ID;
        }else {
            ID = DonatorMainScreen.ID;
            AccountName = DonatorMainScreen.AccountUserDonator;
        }

    }

        public void showCurrentInformation(){
            DatabaseHelper DOP = new DatabaseHelper(CTX);
            Cursor CR = DOP.getInformation(DOP,ID);
            CR.moveToFirst();

            do {
                //column index is because it has to check the value from the array zero created in .getInformation
                if(DOP.usernameCheck(AccountName)){
                    NameChange.setText(CR.getString(0));
                    SurnameChange.setText(CR.getString(1));
                    EmailChange.setText(CR.getString(2));

                }
            }while(CR.moveToNext());
        }



// RETURN TO THE MAIN PAGE
    public void loginPage(){
        Intent intent;
        intent = new Intent(this, LoginPage.class);

        startActivity(intent);
    }
}
