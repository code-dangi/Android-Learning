package com.bt.filedownloadapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Monika on 12/5/2016.
 * Launching Activity
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        DownloadStatusReceiver.IReceiver, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Snackbar mInternetNotificationBar;
    private ProgressBar mDownloadProgressBar;
    private Handler mImageHandler;
    private RadioGroup mTypeSelectionGroup;
    private ImageView mDownloadedImage;
    private String mUrlString;
    private TextView mPdfTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTypeSelectionGroup = (RadioGroup) findViewById(R.id.radio_grp);
        mDownloadedImage = (ImageView) findViewById(R.id.image1);
        mTypeSelectionGroup.setOnCheckedChangeListener(this);
        mDownloadProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mPdfTextView = (TextView) findViewById(R.id.pdf_text_view);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        mUrlString = null;
        int checkId = radioGroup.getCheckedRadioButtonId();
        switch (checkId) {
            case R.id.radio_jpg:
                mUrlString = IConstants.URL_STRING_JPG;
                break;
            case R.id.radio_png:
                mUrlString = IConstants.URL_STRING_PNG;
                break;
            case R.id.radio_patch:
                mUrlString = IConstants.URL_STRING_9_PATCH;
                break;
            case R.id.radio_pdf:
                mUrlString = IConstants.URL_STRING_PDF;
                break;
            default:
                Log.d(TAG, "onRadioButtonClick: there is no selection");
        }
        mDownloadedImage.setVisibility(View.GONE);
        mPdfTextView.setVisibility(View.GONE);
        downloadImage(mUrlString);
    }

    @Override
    public void onClick(View view) {
            downloadImage(mUrlString);
    }
    /**
     * to start intent service
     */
    private void startImageDownload(String urlString) {

        Intent imageDownloadIntent = new Intent(this, ImageDownloadService.class);
        imageDownloadIntent.putExtra(IConstants.EXTRA_URL, urlString);
        DownloadStatusReceiver statusReceiver= new DownloadStatusReceiver(new Handler());
        statusReceiver.setReceiver(this);
        imageDownloadIntent.putExtra(IConstants.EXTRA_RECEIVER, statusReceiver);
        mDownloadProgressBar.setIndeterminate(true);
        mDownloadProgressBar.setVisibility(View.VISIBLE);
        startService(imageDownloadIntent);
    }
    /**
     * showing snackBar when there is no internet connection and keep checking internet
     */
    private void downloadImage(String urlString) {
        if (UtilityMethods.isConnectedToInternet(this)) {
            if (mInternetNotificationBar != null && mInternetNotificationBar.isShown()) {
                mInternetNotificationBar.dismiss();
            }
            startImageDownload(urlString);
        } else {
            mInternetNotificationBar = Snackbar
                    .make(mTypeSelectionGroup, IConstants.SNACKBAR_TEXT, Snackbar.LENGTH_INDEFINITE)
                    .setAction(IConstants.RE_TRY, this);
            mInternetNotificationBar.show();
        }
    }

    /**
     * Method called by result receiver after completion of download
     * @param resultCode the status of download
     * @param resultData bundle containing byte array of bitmap
     */
    @Override
    public void onFinishDownload(int resultCode, Bundle resultData) {
        String fileExtension = null;
        String filePath = null;
        if (resultCode == IConstants.DOWNLOAD_FINISH) {
            filePath = resultData.getString(IConstants.BUNDLE_PATH);
            fileExtension = filePath.substring(filePath.lastIndexOf(".")+1);
            mDownloadProgressBar.setIndeterminate(false);
            mDownloadProgressBar.setVisibility(View.GONE);
            if (fileExtension.equals("jpg") || fileExtension.equals("png")) {
                mPdfTextView.setVisibility(View.GONE);
                loadImageFromPath(filePath);
                mDownloadedImage.setVisibility(View.VISIBLE);
            } else {
                mDownloadedImage.setVisibility(View.GONE);
                mPdfTextView.setVisibility(View.VISIBLE);
                mPdfTextView.setText(filePath);
            }
            /*byte[] imageByteArray = resultData.getByteArray(IConstants.BUNDLE_BYTE_ARRAY);
            loadImageFromByteArray(imageByteArray);*/
        } else {
            mDownloadProgressBar.setIndeterminate(false);
            mDownloadProgressBar.setVisibility(View.GONE);
            Toast downloadError = Toast.makeText(this, IConstants.ERROR_MESSAGE, Toast.LENGTH_SHORT);
            downloadError.show();
        }
    }

    private void loadImageFromPath(final String imagePath) {
        mImageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mDownloadedImage.setImageBitmap((Bitmap) msg.obj);
            }
        };
        Thread loadingThread = new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.obj = BitmapFactory.decodeFile(imagePath);
                mImageHandler.sendMessage(msg);
            }
        };
        loadingThread.start();
    }
    /**
     * Method to load image from bitmap byte array by launching a thread and using handler
     * @param bitmapByteArray input byte array
     */
    private void loadImageFromByteArray(final byte[] bitmapByteArray) {
        mImageHandler = new Handler() {
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
                    mImageHandler.sendMessage(msg);
                }
            }
        };
        loadingThread.start();
    }
}
