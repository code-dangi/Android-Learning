package com.bt.accessibleapp.dataobjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Monika on 2/4/2017.
 * data object for quote
 */

public class Quote implements Parcelable{
    private String mQuote;
    private String mAuthor;
    private String mDetail;

    public Quote() {}

    public Quote(Parcel in) {
        setQuote(in.readString());
        setAuthor(in.readString());
        setId(in.readInt());
        setDetail(in.readString());

    }
    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    private int mId;

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public String getQuote() {
        return mQuote;
    }

    public void setQuote(String mQuote) {
        this.mQuote = mQuote;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuote);
        dest.writeString(mAuthor);
        dest.writeInt(mId);
        dest.writeString(mDetail);
    }
    public static final Parcelable.Creator<Quote> CREATOR = new Parcelable.Creator<Quote>() {
        public Quote createFromParcel(Parcel pc) {
            return new Quote(pc);
        }
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };
}
