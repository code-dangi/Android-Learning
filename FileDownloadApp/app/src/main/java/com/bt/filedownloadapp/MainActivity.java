package com.bt.filedownloadapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Monika on 12/5/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        DownloadStatusReceiver.IReceiver, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences mDownloadedFiles;
    private Snackbar mNotificationBar;
    private ProgressBar mDownloadProgressBar;
    private static Handler sImageHandler;
    private RadioGroup mTypeSelectionGroup;
    private ImageView mDownloadedImage;
    private String mUrlString;
    private TextView mPdfPathTextView;
    private int mSavedCheckId;
    private int mSelectedType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadedFiles = getSharedPreferences(IConstants.PREFERENCE_NAME, MODE_PRIVATE);
        mTypeSelectionGroup = (RadioGroup) findViewById(R.id.radio_grp);
        mDownloadedImage = (ImageView) findViewById(R.id.image1);
        mTypeSelectionGroup.setOnCheckedChangeListener(this);
        mDownloadProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mPdfPathTextView = (TextView) findViewById(R.id.pdf_text_view);
        Button deleteButton = (Button) findViewById(R.id.delete_pref_button);
        deleteButton.setOnClickListener(this);
        deleteButton.setHovered(true);
        setHandler();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        mUrlString = null;
        int checkId = radioGroup.getCheckedRadioButtonId();
        switch (checkId) {
            case R.id.radio_jpg:
                mUrlString = IConstants.URL_STRING_JPG;
                mSavedCheckId = checkId;
                mSelectedType = IConstants.JPG;
                break;
            case R.id.radio_png:
                mUrlString = IConstants.URL_STRING_PNG;
                mSavedCheckId = checkId;
                mSelectedType = IConstants.PNG;
                break;
            case R.id.radio_patch:
                mUrlString = IConstants.URL_STRING_9_PATCH;
                mSavedCheckId = checkId;
                mSelectedType = IConstants.PATCH_9;
                break;
            case R.id.radio_pdf:
                mUrlString = IConstants.URL_STRING_PDF;
                mSavedCheckId = checkId;
                mSelectedType = IConstants.PDF;
                break;
            default:
                Log.d(TAG, "onRadioButtonClick: there is no selection");
        }
        cancelMessages();
        downloadIfNotExist(mUrlString, mSavedCheckId, mSelectedType);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.delete_pref_button) {
            clearDownloadedFiles(mDownloadedFiles);
        } else {
            downloadFile(mUrlString, mTypeSelectionGroup.getCheckedRadioButtonId(), mSelectedType);
        }
    }
    /**
     * clear the cache from shared preferences
     */
    private void clearDownloadedFiles(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        showNotification(IConstants.All_DELETED, 0);
    }
    /**
     * download file if its not exist in preferences
     */
    private void downloadIfNotExist(String url, int savedCheckId, int type) {
        String filePath = getFilePath(url);
        if (filePath.equals(IConstants.FILE_NOT_FOUND)) {
            mDownloadedImage.setVisibility(View.GONE);
            mPdfPathTextView.setVisibility(View.GONE);
            downloadFile(mUrlString, savedCheckId, type);
        } else {
            String fileExtension = UtilityMethods.getFileExtension(filePath);
            if (fileExtension.equals("jpg") || fileExtension.equals("png")) {
                readImage(filePath);
            } else {
                readFile(filePath);
            }
        }
    }
    /**
     * read image from the given path
     */
    private void readImage(String imagePath) {
        mDownloadProgressBar.setIndeterminate(true);
        mDownloadProgressBar.setVisibility(View.VISIBLE);
        mPdfPathTextView.setVisibility(View.GONE);
        cancelMessages();
        loadImageFromPath(imagePath);
    }
    /**
     * read file other than image
     */
    private void readFile(String filePath) {
        mDownloadProgressBar.setIndeterminate(true);
        mDownloadProgressBar.setVisibility(View.VISIBLE);
        mDownloadedImage.setVisibility(View.GONE);
        cancelMessages();
        loadPdfPath(filePath);
    }
    /**
     * to set the path of downloaded file from other thread
     */
    private void loadPdfPath(String filePath) {
        FileReadThread fileReadThread = new FileReadThread();
        fileReadThread.setImagePath(filePath);
        fileReadThread.setHandler(sImageHandler);
        fileReadThread.start();
    }
    /**
     * to start intent service
     */
    private void startFileDownload(String urlString, int savedCheckId, int type) {
        Intent imageDownloadIntent = new Intent(this, FileDownloadService.class);
        imageDownloadIntent.putExtra(IConstants.EXTRA_URL, urlString);
        imageDownloadIntent.putExtra(IConstants.EXTRA_CHECK_ID, savedCheckId);
        imageDownloadIntent.putExtra(IConstants.EXTRA_TYPE, type);
        DownloadStatusReceiver statusReceiver= new DownloadStatusReceiver(sImageHandler);
        statusReceiver.setReceiver(this);
        imageDownloadIntent.putExtra(IConstants.EXTRA_RECEIVER, statusReceiver);
        mDownloadProgressBar.setIndeterminate(true);
        mDownloadProgressBar.setVisibility(View.VISIBLE);
        startService(imageDownloadIntent);
    }
    /**
     * showing snackBar when there is no internet connection and keep checking internet
     */
    private void downloadFile(String urlString, int savedCheckId, int type) {
        if (UtilityMethods.isConnectedToInternet(this)) {
            if (mNotificationBar != null && mNotificationBar.isShown()) {
                mNotificationBar.dismiss();
            }
            startFileDownload(urlString, savedCheckId, type);
        } else {
            mNotificationBar = Snackbar
                    .make(mTypeSelectionGroup, IConstants.SNACK_BAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction(IConstants.RE_TRY, this);
            mNotificationBar.show();
        }
    }
    /**
     * to cancel all the messages having specific code
     */
    private void cancelMessages() {
        if (sImageHandler.hasMessages(IConstants.IMAGE_MESSAGE_WHAT)) {
            sImageHandler.removeMessages(IConstants.IMAGE_MESSAGE_WHAT);
        }
        if (sImageHandler.hasMessages(IConstants.PDF_MESSAGE_WHAT)) {
            sImageHandler.removeMessages(IConstants.PDF_MESSAGE_WHAT);
        }
    }
    /**
     * check if file is present in preferences
     * @param url: url is key
     * @return file path if url is present or file not found if url is not present
     */
    private String getFilePath(String url) {
        return mDownloadedFiles.getString(url, IConstants.FILE_NOT_FOUND);
    }
    /**
     * Method called by result receiver after completion of download
     * @param resultCode the status of download
     * @param resultData bundle containing byte array of bitmap
     */
    @Override
    public void onFinishDownload(int resultCode, Bundle resultData) {
        String fileExtension;
        String filePath;
        int checkId;
        if (resultCode == IConstants.CODE_DOWNLOAD_FINISH) {
            filePath = resultData.getString(IConstants.BUNDLE_PATH);
                checkId = resultData.getInt(IConstants.BUNDLE_CHECK_ID);
                int type_id = resultData.getInt(IConstants.BUNDLE_TYPE);
                if (checkId == mTypeSelectionGroup.getCheckedRadioButtonId()) {
                    fileExtension = UtilityMethods.getFileExtension(filePath);
                    mDownloadProgressBar.setIndeterminate(false);
                    mDownloadProgressBar.setVisibility(View.GONE);
                    if (fileExtension.equals("jpg") || fileExtension.equals("png")) {
                        readImage(filePath);
                    } else {
                        readFile(filePath);
                    }
                    showNotification(IConstants.SUCCESS_MESSAGE, type_id);
                } else {
                mDownloadProgressBar.setIndeterminate(false);
                mDownloadProgressBar.setVisibility(View.GONE);
            }
        } else {
            mDownloadProgressBar.setIndeterminate(false);
            mDownloadProgressBar.setVisibility(View.GONE);
            showNotification(IConstants.ERROR_MESSAGE, 0);
        }
    }

    /**
     * set handler for ui thread
     */
    private void setHandler() {
        sImageHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                mDownloadProgressBar.setIndeterminate(false);
                mDownloadProgressBar.setVisibility(View.GONE);
                if (msg.what == IConstants.IMAGE_MESSAGE_WHAT) {
                    mPdfPathTextView.setVisibility(View.GONE);
                    mDownloadedImage.setImageBitmap((Bitmap) msg.obj);
                    mDownloadedImage.setVisibility(View.VISIBLE);
                    cancelMessages();
                } else {
                    mDownloadedImage.setVisibility(View.GONE);
                    mPdfPathTextView.setText((String) msg.obj);
                    mPdfPathTextView.setVisibility(View.VISIBLE);
                    cancelMessages();
                }
            }
        };
    }

    /**
     * load image from given image path
     * @param imagePath external storage path of the image
     */
    private void loadImageFromPath(final String imagePath) {
        ImageReadThread imageReadThread;
        imageReadThread = new ImageReadThread();
        imageReadThread.setImagePath(imagePath);
        imageReadThread.setHandler(sImageHandler);
        imageReadThread.start();
    }
    /**
     * show the notifications on the bottom as toast message
     */
    private void showNotification(String message, int type) {
        if (type == IConstants.JPG) {
            message = "JPG file downloaded successfully";
        } else if (type == IConstants.PNG) {
            message = "PNG file downloaded successfully";
        } else if (type == IConstants.PATCH_9) {
            message = "9 patch file downloaded successfully";
        } else if (type == IConstants.PDF) {
            message = "PDF file downloaded successfully";
        }
        mNotificationBar = Snackbar
                .make(mTypeSelectionGroup, message, Snackbar.LENGTH_SHORT);
        mNotificationBar.setDuration(3000);
        mNotificationBar.show();
    }
    /**
     * Method to load image from bitmap byte array by launching a thread and using handler
     * @param bitmapByteArray input byte array
     */
    private void loadImageFromByteArray(final byte[] bitmapByteArray) {
        sImageHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Bitmap bmp = (Bitmap) msg.obj;
                ((ImageView) findViewById(R.id.image1)).setImageBitmap(bmp);
            }
        };
        Thread loadingThread = new Thread() {
            @Override
            public void run() {
                if (bitmapByteArray != null) {
                    Message msg = Message.obtain();
                    msg.obj = BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray.length);
                    sImageHandler.sendMessage(msg);
                }
            }
        };
        loadingThread.start();
    }
}
