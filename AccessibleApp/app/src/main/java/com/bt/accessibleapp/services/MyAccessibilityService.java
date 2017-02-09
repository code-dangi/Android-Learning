package com.bt.accessibleapp.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import static com.bt.accessibleapp.IConstants.IConstants.ACTION;
import static com.bt.accessibleapp.IConstants.IConstants.EXTRA_SPEAK_OPTION;
import static com.bt.accessibleapp.IConstants.IConstants.SERVICE_MESSAGE;
import static com.bt.accessibleapp.IConstants.IConstants.START_SPEAK;

/**
 * Created by Monika on 2/3/2017.
 * Accessibility service for this app only
 */

public class MyAccessibilityService extends AccessibilityService {
    private static final String LOG_TAG = MyAccessibilityService.class.getSimpleName();
    private static final int SCREEN_ON = 1;
    private static final int SCREEN_OFF = 2;
    LocalBroadcastManager mBroadcaster;
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();

        final int eventType = event.getEventType();
        String eventText = event.getText().toString();
        if (!eventText.isEmpty()) {
            speak(eventText);
            return;
        }
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED :
                eventText = "clicked on";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED :
                eventText = "Focused on";
                break;

        }
        String contentDescription = event.getContentDescription().toString();
        eventText = eventText + contentDescription;
        speak(eventText);
        AccessibilityNodeInfo rowNode = source.getParent();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBroadcaster = LocalBroadcastManager.getInstance(this);
    }

    private void speak(String eventText) {
        Intent intent = new Intent(ACTION);
        if (!eventText.isEmpty()) {
            intent.putExtra(EXTRA_SPEAK_OPTION, START_SPEAK);
            intent.putExtra(SERVICE_MESSAGE, eventText);
            mBroadcaster.sendBroadcast(intent);
        }
    }

    @Override
    public void onInterrupt() {
        Intent intent = new Intent(ACTION);
        intent.putExtra(EXTRA_SPEAK_OPTION, START_SPEAK);
        mBroadcaster.sendBroadcast(intent);
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                provideScreenStateChangeFeedback(SCREEN_ON);
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                provideScreenStateChangeFeedback(SCREEN_OFF);
            } else {
                Log.w(LOG_TAG, "Registered for but not handling action " + action);
            }
        }
    };

    private void provideScreenStateChangeFeedback(int feedback) {
        switch (feedback) {
            case SCREEN_ON :
                speak("Screen On");
                break;
            case SCREEN_OFF :
                speak("Screen Off");
        }
    }


    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        // We are interested in all types of accessibility events.
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        //info.packageNames = new String[] {"com.bt.accessibleapp"};
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);
    }

    @Override
    protected boolean onGesture(int gestureId) {
        return super.onGesture(gestureId);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }

}
