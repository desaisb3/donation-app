package com.example.donapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MakeADonation extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUploadImage;
    private EditText mEditTextFileName;
    private EditText mItemDesc;
    private EditText phoneNum;
    private EditText email;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask;

    //STORING IMAGE PATH
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_a_donation);

        mButtonChooseImage=findViewById(R.id.chooseFile);
        mButtonUploadImage=findViewById(R.id.upload);
        mEditTextFileName=findViewById(R.id.imageName);
        mImageView=findViewById(R.id.imageView);
        mItemDesc=findViewById(R.id.itemDesc);
        mProgressBar=findViewById(R.id.progressBar);
        phoneNum=findViewById(R.id.phoneNum);
        email=findViewById(R.id.email);

        //THIS MEANS THAT WE SAVE OUR IMAGES IN THE FOLDER NAMED "uploads"
        mStorageRef= FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();            }
        });



        mButtonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });



    }

    private void openFileChooser(){

        Intent intent =new Intent();

        //WHICH SHOWS ONLY IMAGE FILES
        intent.setType("image/*");

        //TO OPEN GALLERY AND SELECT CONTENT
        intent.setAction(Intent.ACTION_GET_CONTENT);

        //WHEN WE CALL THIS METHOD, WE EXPECT AND GET SOMETHING BIG
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }


    //TO GET THE DATA BACK FROM THE ABOVE METHOD, WE USE ON ACTIVITY RESULT METHOD
    //THIS METHOD WILL BE CALLED WHEN WE CLICK ON THE FILE THAT WE WANT TO UPLOAD FROM THE GALLERY
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //IN ORDER TO IDENTIFY THE REQUEST
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            //WE GET THE IMAGE URI HERE
            //WE WILL USE THIS TO SET THIS TO IMAGE VIEW AND LATER UPLOAD IT TO THE FIREBASE DATABASE
            mImageUri = data.getData();

            //TO LOAD THE IMAGE IN THE IMAGEVIEW, WE USE PICASSO
            Picasso.with(this).load(mImageUri).into(mImageView);

            /*ALTERNATE METHOD IF YOU DONT WANT TO USE PICASSO TO DISPLAY IMAGE
            mImageView.setImageURI(mImageUri);
             */
        }
    }

    //THIS METHOD RETURNS THE EXTENSION OF THE FILE THAT WE PICK (EXAMPLE : JPEG,JPG,PNG)
    private String getFileExtension(Uri uri){

        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void uploadFile(){
        if(mImageUri!=null){

            //GIVE UNIQUE NAME THE FILES WITH SAME NAME DO NOT OVERRIDE
            StorageReference fileReference= mStorageRef.child(System.currentTimeMillis()
                 + "." +getFileExtension(mImageUri));

            mUploadTask=fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //DELAY THE PROGRESSBAR FOR HALF A SECOND WHEN THE UPLOAD IS SUCCESSFUL
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },500);

                            Toast.makeText(MakeADonation.this,"UPLOAD SUCCESSFUL",Toast.LENGTH_LONG).show();

                            /*
                            //CREATING NEW ENTRY IN OUR DATABASE
                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),mItemDesc.getText().toString());

                            //GETS THE REFERENCE TO STORE THE DETAILS
                            //HAS UNIQUE ID
                            String uploadId = mDatabaseRef.push().getKey();

                            //UPLOADS THE DATA TO THE DATABASE
                            mDatabaseRef.child(uploadId).setValue(upload);

                             */
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadUrl = uriTask.getResult();

                            Upload upload = new Upload(mEditTextFileName.getText().toString(),downloadUrl.toString(),mItemDesc.getText().toString(),phoneNum.getText().toString(),email.getText().toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MakeADonation.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress= (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int)progress);
                        }
                    });
        }
        else{
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }
}
