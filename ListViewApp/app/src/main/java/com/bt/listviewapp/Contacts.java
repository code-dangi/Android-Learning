package com.bt.listviewapp;

/**
 * POJO for contact that contains Phone Number and Name
 * Created by Monika on 11/10/2016.
 */
import java.util.Random;

public class Contacts {
    private final String DEFALUT_NAME = "name";
    private int mId;
    private String mName;
    private String mPhoneNumber;
    private Random mRand = new Random();


    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getmId(){
        return mId;
    }
    public void setmId(int mId) {

        this.mId = mId;
    }

    public Contacts(int mId, String mName, String mPhoneNumber ) {
        this.mId = mId;
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
    }

    public Contacts( int i ){
        setmId(i);
        setName(genrateDummyName(i));
        setPhoneNumber(genrateDummyNumber());
    }

    public String toString(){
        return this.getName();
    }
    
    public String genrateDummyNumber(){
        setPhoneNumber(convertToString(mRand.nextInt(9))+convertToString(mRand.nextInt(9))
                +convertToString(mRand.nextInt(9))+convertToString(mRand.nextInt(9))
                +convertToString(mRand.nextInt(9))+convertToString(mRand.nextInt(9))
                +convertToString(mRand.nextInt(9))+convertToString(mRand.nextInt(9))
                +convertToString(mRand.nextInt(9)));
        return getPhoneNumber();
    }
    
    public String genrateDummyName(int i){
        return DEFALUT_NAME+i;
    }
    
    protected String convertToString(Integer i ){
        return i.toString();
    }

    public Contacts(){}

}
