package com.example.donapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

public class Delete_Dialog extends AppCompatDialogFragment {

    DatabaseHelper DOP;
    Integer ID = null;
 public Dialog onCreateDialog(Bundle savedInstanceState){
     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

     LayoutInflater inflater = getActivity().getLayoutInflater();



     DOP = new DatabaseHelper(getActivity());
     builder.setTitle("Are you sure ?")
             .setMessage("Deleting this account will result in completly removing your account from the system and you won't be able to go back.")
             .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             })
             .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                     SetID();
                     DOP.deleteAccount2(ID);
                     DOP.deleteAccount(ID);
                     loginPage();
                 }
             });
    return builder.create();
 }
 public Integer SetID(){
     if (DonatorMainScreen.ID == null)
            return ID = RecipientMainScreen.ID;
     else return ID = DonatorMainScreen.ID;

 }
 // return to the login page
    public void loginPage(){
        Intent intent;
        intent = new Intent(getActivity(), LoginPage.class);

        startActivity(intent);
    }

}
