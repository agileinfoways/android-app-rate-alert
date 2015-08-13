package com.agileinfoways.appratealert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.concurrent.TimeUnit;

/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Application Rating alert with customized button label, action and alert interval
 *
 * @author Bhaumik Soni
 * @version 1.0
 * @since 08/12/2015
 */

public class AppRateAlert {

    /**
     * Available Action
     * RATE_APP,
     * SEND_FEEDBACK,
     * DONT_SHOW_AGAIN,
     * DISMISS
     */
    static class Action {
        public static final int RATE_APP = 0;
        public static final int SEND_FEEDBACK = 1;
        public static final int DONT_SHOW_AGAIN = 2;
        public static final int DISMISS = 3;
    }

    private static final String KEY_LAST_ALERT_TIME = "alert_time";
    private static final String KEY_DONT_SHOW = "dont_show";

    // default values
    public static final int DEFAULT_ALERT_INTERVAL = 0;
    public static final String DEFAULT_POSITIVE_BUTTON_LABEL = "Rate";
    public static final String DEFAULT_NEGATIVE_BUTTON_LABEL = "Close";
    public static final String DEFAULT_NEUTRAL_BUTTON_LABEL = "Send Feedback";

    private Context context;
    private AlertDialog.Builder builder;

    // initializing default values
    private int alertInterval = DEFAULT_ALERT_INTERVAL;
    private String positiveButtonLabel = DEFAULT_POSITIVE_BUTTON_LABEL;
    private String negativeButtonLabel = DEFAULT_NEGATIVE_BUTTON_LABEL;
    private String neutralButtonLabel = DEFAULT_NEUTRAL_BUTTON_LABEL;

    private String feedbackMail;
    private SharedPreferences preferences;
    private Editor editor;

    public AppRateAlert(Context context) {
        builder = new AlertDialog.Builder(context);
        initPref(context);
    }

    public AppRateAlert(Context context, int theme) {
        builder = new AlertDialog.Builder(context, theme);
        initPref(context);
    }

    private void initPref(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public void initialize() {
        showAppRateAlert();
    }

    private void showAppRateAlert() {
        if (preferences.getBoolean(KEY_DONT_SHOW, false)) {
            return;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime >= (preferences.getLong(KEY_LAST_ALERT_TIME, currentTime) + TimeUnit.DAYS.toMillis(alertInterval))) {
            editor.putLong(KEY_LAST_ALERT_TIME, System.currentTimeMillis()).apply();
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    /**
     * Set Positive button label and action
     *
     * @param label  - Button label
     * @param action - {@link Action} to be performed when button clicked
     * @return {@link AppRateAlert}
     */

    public AppRateAlert setPositiveButton(String label, final int action) {

        if (!TextUtils.isEmpty(label)) {
            positiveButtonLabel = label;
        }

        builder.setPositiveButton(positiveButtonLabel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                performAction(dialog, action);
            }
        });

        return this;

    }

    /**
     * Set Neutral button label and action
     *
     * @param label  - Button label
     * @param action - {@link Action} to be performed when button clicked
     * @return {@link AppRateAlert}
     */
    public AppRateAlert setNeutralButton(String label, final int action) {

        if (!TextUtils.isEmpty(label)) {
            neutralButtonLabel = label;
        }

        builder.setNeutralButton(neutralButtonLabel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                performAction(dialog, action);
            }
        });

        return this;

    }

    /**
     * Set Negative button label and action
     *
     * @param label  - Button label
     * @param action - {@link Action} to be performed when button clicked
     * @return {@link AppRateAlert}
     */

    public AppRateAlert setNegativeButton(String label, final int action) {

        if (!TextUtils.isEmpty(label)) {
            negativeButtonLabel = label;
        }

        builder.setNegativeButton(negativeButtonLabel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                performAction(dialog, action);
            }
        });

        return this;

    }

    /**
     * set alert Title
     *
     * @param title - alert title
     * @return {@link AppRateAlert}
     */

    public AppRateAlert setTitle(CharSequence title) {
        builder.setTitle(title);
        return this;
    }


    /**
     * set alert message
     *
     * @param msg - alert message
     * @return {@link AppRateAlert}
     */
    public AppRateAlert setMessage(CharSequence msg) {
        builder.setMessage(msg);
        return this;
    }

    /**
     * set alert interval
     *
     * @param days - alert interval
     * @return {@link AppRateAlert}
     */
    public AppRateAlert setAlertInterval(int days) {
        this.alertInterval = days;
        return this;
    }

    /**
     * set alert icon
     *
     * @param iconId - alert icon
     * @return {@link AppRateAlert}
     */
    public AppRateAlert setIcon(int iconId) {
        builder.setIcon(iconId);
        return this;
    }

    /**
     * set feedback mail id which will appear as recipient
     *
     * @param mail - mail id for the feedback
     * @return {@link AppRateAlert}
     */
    public AppRateAlert setFeedbackMail(String mail) {
        this.feedbackMail = mail;
        return this;
    }

    private void launchPlaystore(DialogInterface dialog) {
        dialog.dismiss();
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
    }

    private void dontShow(DialogInterface dialog) {
        if (editor != null) {
            editor.putBoolean(KEY_DONT_SHOW, true).apply();
        }
    }

    private void sendFeedback(DialogInterface dialog) {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (!TextUtils.isEmpty(feedbackMail)) {
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{feedbackMail});
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        intent.setType("text/html");
        context.startActivity(Intent.createChooser(intent, "Send mail"));
    }

    private void performAction(DialogInterface dialog, int action) {
        switch (action) {
            case Action.DISMISS:
                dialog.dismiss();
                break;
            case Action.DONT_SHOW_AGAIN:
                dontShow(dialog);
                break;
            case Action.RATE_APP:
                launchPlaystore(dialog);
                break;
            case Action.SEND_FEEDBACK:
                sendFeedback(dialog);
                break;

            default:
                break;
        }
    }

}
