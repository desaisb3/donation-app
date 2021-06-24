package com.example.donapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EmailRecoveryPage extends AppCompatActivity {

    public EditText username;
    public EditText accountType;
    public Button getPassword;
    public TextView password;
    DatabaseHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_recovery_page);

        username = findViewById(R.id.username);
        accountType = findViewById(R.id.accountType);
        password = findViewById(R.id.password);
        getPassword = findViewById(R.id.getMyPasswordBttn);
        myDB = new DatabaseHelper(this);

        getPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recoverPassword();
            }
        });



    }

    public void recoverPassword(){

        String result=myDB.searchPassword(username.getText().toString(), accountType.getText().toString());
        if (!result.equals("")) {
            password.setText(result);
        }
        else {
            Toast.makeText(EmailRecoveryPage.this,"Sorry! We couldn't this account! Please try Again!", Toast.LENGTH_LONG).show();
        }


    }



}
