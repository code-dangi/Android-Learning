package com.bt.accessibleapp.db;

import android.provider.BaseColumns;

/**
 * Created by Monika on 2/4/2017.
 * Constant and table info
 */

public class QuoteContract {
    public static class Quote implements BaseColumns{
        public static final String TABLE_NAME = "Quotes";
        public static final String COLUMN_NAME_QUOTE = "quote";
        public static final String COLUMN_NAME_AUTHOR_NAME = "authorName";
        public static final String COLUMN_NAME_ID = "columnId";
        public static final String COLUMN_NAME_DETAIL = "detail";
        public static final String[] PROJECTION_ALL = {COLUMN_NAME_QUOTE, COLUMN_NAME_AUTHOR_NAME,
                COLUMN_NAME_ID, COLUMN_NAME_DETAIL};
        public static final String[] INSERT = {COLUMN_NAME_QUOTE, COLUMN_NAME_AUTHOR_NAME,
                COLUMN_NAME_ID, COLUMN_NAME_DETAIL};
    }
}
