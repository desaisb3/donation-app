package com.example.donapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    private Button logoutBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        logoutBttn = findViewById(R.id.logoutButton);

        //CALLING THE METHOD TO CHANGE FROM MAIN PAGE TO LOGIN PAGE..
        logoutBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutBackToLoginScreen();
            }
        });
    }


    //INTENT FOR CHANGING MAIN PAGE TO CREATE ACCOUNT PAGE..
    public void logoutBackToLoginScreen(){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

}
