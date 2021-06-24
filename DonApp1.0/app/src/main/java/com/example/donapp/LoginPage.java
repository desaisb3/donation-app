package com.example.donapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/*I HAVE ADDED THE LOGIN PAGE(THIS PAGE) AS MAIN PAGE(MEANS BY DEFAULT LAUNCH THIS PAGE) BY GOING TO THE ANDROIDMANIFEST.XML FILE AND ADDING INTENT FILTER OPTION IN ORDER TO
  LAUNCH THE THIS PAGE AS THE MAIN PAGE INSTEAD OF MAIN ACTIVITY PAGE (CREATE ACCOUNT PAGE)  */

/* I HAVE CREATED SLIDING ANIMATION BY ADDING THE FOUR DIFFERENT ANIMATION XML FILES TO THE (APP-> ANIM) FOLDER AND IMPLEMENTING THEM IN (VALUES->STYLES.XML)
   CHECK THAT OUT
   */

public class LoginPage extends AppCompatActivity {
    public Button login;
    public TextView createAccountText;
    public TextView forgotPassword;
    public EditText usernameID,passwordID;
    DatabaseHelper myDB;

    public boolean loginSuccessful;
    public boolean donatorLoginSuccessful;
    public boolean recipientLoginSuccessful;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        usernameID = findViewById(R.id.username);
        passwordID = findViewById(R.id.password);

        myDB = new DatabaseHelper(this);
        login = findViewById(R.id.loginBttn);

        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPassword();
            }
        });


        createAccountText = findViewById(R.id.createAccountText); //CREATE ACCOUNT BUTTON


        //CALLING THE METHOD TO OPEN THE CREATE ACCOUNT PAGE..
        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountPage();
            }
        });

        checkPasswords();

        loginSuccessful = false;
        donatorLoginSuccessful = false;
        recipientLoginSuccessful = false;

    }

    //METHOD FOR CHECKING THE USERNAME AND PASSWORDS FROM DATABASE AND LOGGING IN THE APP IF BOTH ARE TRUE OTHERWISE SHOW MESSAGE THAT THEY ARE INCORRECT..
    public void checkPasswords(){
        //WHEN THE USER CLICKS ON THE LOGIN BUTTON..
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //CALL THE METHOD TO CHECK FROM THE DATABASE THE USERNAMES AND PASSWORDS ONE BY ONE...

                boolean result = myDB.comparePasswordAndLogin(usernameID.getText().toString(),passwordID.getText().toString());
                System.out.println(usernameID.getText().toString());
                System.out.println(passwordID.getText().toString());


                //CALLING getAccountType METHOD TO GET THE USER'S ACCOUNT TYPE (DONATOR OR RECIPIENT) AND LOGIN IN THEIR RESPECTIVE ACCOUNTS
                String accountType = myDB.getAccountType(usernameID.getText().toString(),passwordID.getText().toString());



                //IF THE USERNAME AND PASSWORD MATCH, CHECK FOR THE ACCOUNT TYPE
                //if the username and password is from donator open donator main page
                //OPEN THE DONATOR MAIN SCREEN
                //OTHERWISE THE RECIPIENT MAIN SCREEN
                if(result && accountType.equals("Donator")) {
                    loginSuccessful = true;
                    donatorLoginSuccessful = true;
                    Toast.makeText(LoginPage.this,"WELCOME  " + usernameID.getText().toString(), Toast.LENGTH_LONG).show();
                    openDonatorMainScreen(usernameID.getText().toString());
                }

                //else if from recipient open the recipient home page...
                else if (result && accountType.equals("Recipient")) {
                    loginSuccessful = true;
                    recipientLoginSuccessful = true;
                    Toast.makeText(LoginPage.this,"WELCOME  " + usernameID.getText().toString(), Toast.LENGTH_LONG).show();
                    openRecipeintMainScreen(usernameID.getText().toString());
                }

                //IF THE USERNAME AND PASSWORD DO NOT MATCH, SHOW THE ERROR TOAST MESSAGE...
                else{
                    Toast.makeText(LoginPage.this,"Wrong username and password. Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    //INTENT FOR CHANGING LOGIN PAGE TO DONATOR MAIN SCREEN PAGE..
    public void openDonatorMainScreen(String user_username) {
        // PASS TO THE OTHER ACTIVITY THE INFORMATION ACCOUNT USERNAME
        Bundle BN = new Bundle();
        BN.putString("DonatorAccountName", user_username);
        Intent intent = new Intent(this, DonatorMainScreen.class);
        intent.putExtras(BN);
        startActivity(intent);
    }

    //INTENT FOR CHANGING LOGIN TO RECIPIENT MAIN SCREEN PAGE
    public void openRecipeintMainScreen(String user_username){
        //PASS TO THE OTHER ACTIVITY THE INFORMATION ACCOUNT USERNAME
        Bundle BN = new Bundle();
        BN.putString("RecipientAccountName", user_username);
        Intent intent = new Intent(this, RecipientMainScreen.class);
        intent.putExtras(BN);
        startActivity(intent);
    }

    //INTENT FOR CHANGING LOGIN PAGE TO CREATE ACCOUNT PAGE..
    public void createAccountPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //INTENT FOR CHANGING LOGIN PAGE TO FORGOT PASSWORD PAGE..
    public void openForgotPassword(){
        Intent intent = new Intent(this, EmailRecoveryPage.class);
        startActivity(intent);
    }

}
