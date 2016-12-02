package com.bt.newsfeddappusinghandler;


/**
 * Created by Monika on 11/29/2016.
 * DTO for News
 */

public class News {
    private String mTitle;
    private String mDate;
    private String mPlace;


    public News(String title, String date, String place) {
        setTitle(title);
        setDate(date);
        setPlace(place);
    }

    public String getDate() {
        return mDate;
    }

    private void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getPlace() {
        return mPlace;
    }

    private void setPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getTitle() { return mTitle; }

    private void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

   /* *//**
     * to generate dummy data
     * @param i index to be added as suffix
     * @return string var
     *//*
    private String generateDummyNewsTitle(int i){
        return "News Title"+i;
    }
    private String generateDummyNewsDate() { return "11/30/2016"; }
    private String generateDummyNewsPlace() {return "Bengalore"; }*/

    /*public News(int i) {
        setTitle(generateDummyNewsTitle(i));
        setDate(generateDummyNewsDate());
        setPlace(generateDummyNewsPlace());
    }*/
}
