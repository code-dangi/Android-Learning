package com.bt.newsfeedapp;

/**
 * Created by Monika on 11/29/2016.
 * Pojo for News
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

    /**
     * to generate dummy data
     * @param i index to be added as suffix
     * @return string var
     */
    public String generateDummyNewsTitle(int i){
        return "News Title"+i;
    }
    public String generateDummyNewsDate() { return "11/29/2016"; }
    public String generateDummyNewsPlace() {return "Bengalore"; }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public String getTitle() {
        return mTitle;

    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
