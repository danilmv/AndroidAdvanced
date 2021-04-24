package com.andriod.lesson6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MessageReceiver extends BroadcastReceiver {
    private final String MSG_KEY = "MESSAGE";

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra(MSG_KEY);
        if (msg == null) msg = "<empty>";

        MainActivity.showNotification(context, String.format("Message received: %s", msg));
    }
}
