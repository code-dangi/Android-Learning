package com.bt.newsfeedapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Monika on 11/29/2016.
 * DTO for News
 */

public class News {
    private String mTitle;
    private String mDate;
    private String mPlace;

    public News(int i) {
        setTitle(generateDummyNewsTitle(i));
        setDate(generateDummyNewsDate());
        setPlace(generateDummyNewsPlace());
    }
    public News(String title, String date, String place) {
        setTitle(title);
        setDate(date);
        setPlace(place);
    }
    /**
     * to generate dummy data
     * @param i index to be added as suffix
     * @return string var
     */
    public String generateDummyNewsTitle(int i){
        return "News Title"+i;
    }
    public String generateDummyNewsDate() { return new SimpleDateFormat("mm/dd/yyyy").format(new Date()); }
    public String generateDummyNewsPlace() {return "Bengalore"; }

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

    public String getTitle() {
        return mTitle;

    }

    private void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
