package com.bt.recyclerviewwithoutprefetch.model;

import java.net.URL;

/**
 * Created by Monika on 1/11/2017.
 * DAO for recycler view item
 */

public class Image {
    private String mImageUrl;
    private String mImageDescription;

    public Image(String url, String description) {
        mImageUrl = url;
        mImageDescription = description;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public String getImageDescription() {
        return mImageDescription;
    }
}
