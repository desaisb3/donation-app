package com.example.donapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*TO SEE THE DATA THAT WAS ADDED TO THE DATABASE, GO TO (DEVICE FILE EXPLORER -> DATA -> DATA ->
   com.example.donapp -> databases -> DonAppUsers.db )
   DOWNLOAD THE DATABASE ON YOUR DESKTOP AND YOU CAN USE ANY ONLINE TOOL(THAT ACCEPTS DATABASE EXTENSION)
   TO SEE THE INFORMATION STORED INSIDE THE APPLICATION*/

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText username;
    EditText password;
    EditText enterPasswordAgain;
    Button createAccount;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText firstName;
    EditText lastName;
    EditText email;
    Integer ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);


        myDb = new DatabaseHelper(this); //Call the constructor of DatabaseHelperClass and in the constructor we are creating the database and the table..

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        enterPasswordAgain = findViewById(R.id.confirmPassword);
        createAccount = findViewById(R.id.button_Create);


        AddData();


    }


    //THIS METHOD IS TO ADD DATA WHEN THE USER ENTERS VALID USERNAME AND ID AND THE INFORMATION IS ADDED TO THE DATABASE. ALSO, THE USER IS REDIRECTED TO THE
    //HOME PAGE OF THE APP..
    public void AddData(){
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //GETCHECKEDRADIOBUTTIONID IS A PREDEFINED METHOD WHICH GIVES US THE BUTTON ID WHICH WAS CLICKED FROM THE RADIO GROUP..
                int radioId  = radioGroup.getCheckedRadioButtonId();

                //PASSING THE ID TO THE RADIO BUTTON TELLS US STORES AND TELLS US WHICH BUTTON WAS CLICKED
                radioButton = findViewById(radioId);

                //METHODS TO CHECK IF DATA IS ADDED SUCCESSFULLY TO THE DATABASE.
                boolean passwordCheck = isValid(password.getText().toString());
                boolean isUserNameEmpty = (username.getText().toString()).equals("");
                boolean userNameExists = myDb.usernameCheck(username.getText().toString());
                boolean checkEnteredPasswords = (password.getText().toString()).equals(enterPasswordAgain.getText().toString());
                boolean isInserted;
                boolean isInfoInserted;

                if(isUserNameEmpty){
                    Toast.makeText(MainActivity.this,"Username field cannot be left empty",Toast.LENGTH_LONG).show();
                }
                if(userNameExists){
                    Toast.makeText(MainActivity.this,"Username already exists! Please try another one!",Toast.LENGTH_LONG).show();
                }
                if(!passwordCheck){
                    Toast.makeText(MainActivity.this,"Password conditions do not meet! Please try another one!",Toast.LENGTH_LONG).show();
                }
                if(!checkEnteredPasswords){
                    Toast.makeText(MainActivity.this,"Passwords do not match. Please enter again!",Toast.LENGTH_LONG).show();
                }


                //THE DATA IS ADDED ONLY IF THE CONDITIONS ARE MET OTHERWISE SHOWS THE RESPECTIVE TOAST ERROR..
                if(passwordCheck && !isUserNameEmpty && checkEnteredPasswords && !userNameExists){
                    isInserted = myDb.insertData(firstName.getText().toString(),lastName.getText().toString(),username.getText().toString(),password.getText().toString(),
                            enterPasswordAgain.getText().toString(),radioButton.getText().toString());
                    isInfoInserted=myDb.insertData1(firstName.getText().toString(),lastName.getText().toString(),email.getText().toString());
                     ID = myDb.getID(username.getText().toString(),password.getText().toString());
                   
                    if (isInserted && isInfoInserted){

                        //WHEN THE ACCOUNT IS CREATED, OPEN THE RESPECTIVE MAIN HOME OF THE ACCOUNT TYPE THAT THE USER SELECTS(DONATOR OR RECIPIENT).
                        //OPEN DONATOR MAIN SCREEN ON CREATING A DONATOR ACCOUNT
                        Toast.makeText(MainActivity.this,firstName.getText().toString() + " your Account is Created Successfully! WELCOME TO DONAPP!",Toast.LENGTH_LONG).show();
                        if(radioButton.getText().toString().equals("Donator")){
                            openDonatorMainScreen(username.getText().toString(),ID);
                        }

                        //OPEN DONATOR MAIN SCREEN ON CREATING A RECIPIENT ACCOUNT
                        else if (radioButton.getText().toString().equals("Recipient")){
                            Toast.makeText(MainActivity.this,firstName.getText().toString() + " your Account is Created Successfully! WELCOME TO DONAPP!",Toast.LENGTH_LONG).show();
                            openRecipientMainScreen(username.getText().toString(),ID);
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this,"There was an error creating your account! Please try again!",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
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


    //CREATING METHOD TO OPEN HOME PAGE OF THE APP WHEN THE USER LOGS IN..
    public void openDonatorMainScreen(String user_username,Integer ID) {
        // PASS TO THE OTHER ACTIVITY THE INFORMATION ACCOUNT USERNAME
        Bundle BN = new Bundle();
        BN.putInt("ID_AccountDonator",ID);
        BN.putString("DonatorAccountName", user_username);
        Intent intent = new Intent(this, DonatorMainScreen.class);
        intent.putExtras(BN);
        startActivity(intent);
    }


    public void openRecipientMainScreen(String user_username, Integer ID){
        //PASS TO THE OTHER ACTIVITY THE INFORMATION ACCOUNT USERNAME
        Bundle BN = new Bundle();
        BN.putInt("ID_AccountRecipient",ID);
        BN.putString("RecipientAccountName", user_username);
        Intent intent = new Intent(this, RecipientMainScreen.class);
        intent.putExtras(BN);
        startActivity(intent);
    }


    public void checkButton(View v){
        //GETCHECKEDRADIOBUTTIONID IS A PREDEFINED METHOD WHICH GIVES US THE BUTTON ID WHICH WAS CLICKED FROM THE RADIO GROUP..
        int radioId  = radioGroup.getCheckedRadioButtonId();

        //PASSING THE ID TO THE RADIO BUTTON TELLS US STORES AND TELLS US WHICH BUTTON WAS CLICKED
        radioButton = findViewById(radioId);

        //DISPLAYING WHICH BUTTON WAS CLICKED
        Toast.makeText(this,"You selected: " + radioButton.getText(),Toast.LENGTH_SHORT).show();
    }

}
