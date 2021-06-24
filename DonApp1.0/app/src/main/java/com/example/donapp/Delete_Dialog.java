package com.example.donapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Delete_Dialog extends AppCompatDialogFragment {

    DatabaseHelper DOP;

 public Dialog onCreateDialog(Bundle savedInstanceState){
     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

     LayoutInflater inflater = getActivity().getLayoutInflater();

     builder.setTitle("Are you sure ?")
             .setMessage("Deleting this account will result in completly removing your account from the system and you won't be able to go back.")
             .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             })
             .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {


                 }
             });
    return builder.create();
 }

}
