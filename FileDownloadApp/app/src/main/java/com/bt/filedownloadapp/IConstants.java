package com.bt.filedownloadapp;
public interface IConstants {
    /*URL strings */
    String URL_STRING_PNG = "http://icdn.pro/images/en/f/i/file-icone-9468-128.png";
    /*String URL_STRING_JPG = "http://www.flooringvillage.co.uk/ekmps/shops/flooringvillage/images/request-a-sample--547-p.jpg";*/
    /*String URL_STRING_JPG = "http://i-cdn.phonearena.com/images/articles/138999-image/iPhone-6-main-camera-sample.jpg";*/
    String URL_STRING_JPG = "https://people.sc.fsu.edu/~jburkardt/data/jpg/newton.jpg";
    String URL_STRING_9_PATCH = "http://www.queness.com/resources/images/png/apple_ex.png";
    String URL_STRING_PDF = "http://www.pdf995.com/samples/pdf.pdf";
    String PREFERENCE_NAME = "downloadedFiles";
    String SNACK_BAR_TEXT = "No internet connection";
    String FILE_NOT_FOUND = "notPresent";
    String FILE_LOCATION = "/savedImages";

    /*extra variables fro intent*/
    String EXTRA_URL = "com.bt.filedownloadapp.url";
    String EXTRA_CHECK_ID = "com.bt.filedownloadapp.last_saved_check_id";
    String EXTRA_RECEIVER = "com.bt.filedownloadapp.receiver";
    String EXTRA_TYPE = "com.bt.filedownloadapp.type_of_file";

    /*bundle variables*/
    String BUNDLE_PATH = "external_storage_image_path";
    String BUNDLE_CHECK_ID = "check_id_of_corresponding_url";
    String BUNDLE_TYPE = "downloaded_image_type";

    /*snack bar messages*/
    String SUCCESS_MESSAGE = "Successfully Downloaded";
    String ERROR_MESSAGE = "Something went wrong check your internet connection";
    String RE_TRY = "Re-Try";

    /*codes*/
    int CODE_DOWNLOAD_FINISH = 100;
    int REQUEST_OK_CODE = 200;
    int DOWNLOAD_ERROR_CODE = 300;
    int IMAGE_MESSAGE_WHAT = 500;
    int PDF_MESSAGE_WHAT = 600;

    /*codes for different types*/
    int JPG = 1;
    int PNG = 2;
    int PATCH_9 = 3;
    int PDF = 4;
}