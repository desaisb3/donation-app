package com.example.donapp;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String itemDesc;
    private String mPhoneNum;
    private String mEmail;
    private String mKey;


    public Upload(){
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String aboutItem, String phoneNum, String email){

        //IF THE PERSON DOES NOT ADD ANY NAME FOR IMAGE, SET IT AS NO NAME BY DEFAULT
        if(name.trim().equals("")){
            name="No Name";
        }

        mName=name;
        mImageUrl=imageUrl;
        itemDesc=aboutItem;
        mPhoneNum=phoneNum;
        mEmail=email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getPhoneNum() {
        return mPhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.mPhoneNum = phoneNum;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }


    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String mKey) {
        this.mKey = mKey;
    }

}
