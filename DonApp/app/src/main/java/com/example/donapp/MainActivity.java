package com.example.donapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);


        myDb = new DatabaseHelper(this); //Call the constructor of DatabaseHelperClass and in the constructor we are creating the database and the table..

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


                //CHECKING IF THE DATA IS APPROPRIATE AND ADD DATA SUCCESSFULLY TO THE DATABASE
                boolean isInserted = myDb.insertData(username.getText().toString(),password.getText().toString(),enterPasswordAgain.getText().toString(),radioButton.getText().toString());
                if(password.equals(enterPasswordAgain)){
                    Toast.makeText(MainActivity.this,"Passwords do not match. Please enter again!",Toast.LENGTH_LONG).show();
                }

                /*
                int values = myDb.insertData1(username.getText().toString(),password.getText().toString(),enterPasswordAgain.getText().toString(),radioButton.getText().toString());

                switch (values){
                    case -1:
                        Toast.makeText(MainActivity.this,"There was an error creating your account. Please try again!",Toast.LENGTH_LONG).show();
                        break;
                    case -2:
                        Toast.makeText(MainActivity.this,"Username already exists! Please try another one!",Toast.LENGTH_LONG).show();
                        break;
                    case -3:
                        Toast.makeText(MainActivity.this,"Password conditions do not meet! Please try another one!",Toast.LENGTH_LONG).show();
                        break;
                    case -4:
                        Toast.makeText(MainActivity.this,"Passwords do not match. Please try again!",Toast.LENGTH_LONG).show();
                }
                 */


                //TO CHECK IF THE DATA WAS SUCCESSFULLY ENTERED INTO THE DATABASE, OTHERWISE SHOW ERROR
                if (isInserted){

                    //WHEN THE ACCOUNT IS CREATED, OPEN THE RESPECTIVE MAIN HOME OF THE ACCOUNT TYPE THAT THE USER SELECTS(DONATOR OR RECIPIENT).
                    //OPEN DONATOR MAIN SCREEN ON CREATING A DONATOR ACCOUNT
                    Toast.makeText(MainActivity.this,username.getText().toString() + " Your Account is Created Successfully!",Toast.LENGTH_LONG).show();
                    if(radioButton.getText().toString().equals("Donator")){
                        openDonatorMainScreen();
                    }

                    //OPEN DONATOR MAIN SCREEN ON CREATING A RECIPIENT ACCOUNT
                    else if (radioButton.getText().toString().equals("Recipient")){
                        Toast.makeText(MainActivity.this,username.getText().toString() +" Your Account is Created Successfully!",Toast.LENGTH_LONG).show();
                        openRecipientMainScreen();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Please enter a valid password!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //CREATING METHOD TO OPEN HOME PAGE OF THE APP WHEN THE USER LOGS IN..
    public void openDonatorMainScreen(){
        Intent intent = new Intent(this, DonatorMainScreen.class);
        startActivity(intent);
    }


    public void openRecipientMainScreen(){
        Intent intent = new Intent(this, RecipientMainScreen.class);
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
