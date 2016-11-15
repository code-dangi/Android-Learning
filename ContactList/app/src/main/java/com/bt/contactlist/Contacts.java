package com.bt.contactlist;

import java.util.Objects;
import java.util.Random;

/**
 * Created by Monika on 11/11/2016.
 */

public class Contacts {
    private int id;
    private String name;
    private String number;
    Random rand = new Random();
    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Contacts(int id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public Contacts(int id, String name) {
        this.id=id;
        this.name=name;

    }
    public Contacts(int i){
        setId(i);
        setName(genrateDummyName(i));
        setNumber(genrateDummyNumber());
    }

    public String toString(){
        return this.getName();
    }

    public String genrateDummyNumber(){
        setNumber(convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9))+convertToString(rand.nextInt(9)));
        return getNumber();
    }
    public String genrateDummyName(int i){
        return "name"+i;
    }
    protected String convertToString(Integer i ){
        return i.toString();
    }
    public Contacts(){}

}
