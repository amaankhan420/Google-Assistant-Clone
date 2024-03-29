package com.example.googleassistantcloning.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.googleassistantcloning.R;


public class UiUtils {
        public static final String[] Commands = {"how to send message on WhatsApp", "check my mail","what can i do","can we date","how to use google assistant","hey ", "for example : search assistant","explore","how to use google assistant","hi","hello","thanks","welcome","clear","date","time","dial","send SMS", "send sms", "joke", "tell me a joke"
        ,"ask me fun questions", "click selfie","open Whatsapp" , "open Facebook" , "open Gmail", "open Youtube" , "open  GoogleMaps" , "open Google",
        "turn on Bluetooth" , "For example : call mum or call papa ",  "dial" , "turn off Bluetooth" , "turn on Flash" , "turn off Flash","capture photo" , "any thoughts",
                "play ringtone","stop ringtone","are you married","haha","read me","read my last sms","share file","share a text message that your message",
        "get bluetooth devices","copy to clipboard","read last clipboard","open google lens","explore","what is your name" , "play some music",
        "stop music"};
        public static final String logTTS = "Text To Speech";

        public static final String logSR = "SR";

        public static final String logKeeper = "Keeper";

        public static void setCustomActionBar(ActionBar supportActionBar, Context context) {
                if (supportActionBar != null) {
                        supportActionBar.setDisplayShowHomeEnabled(true);
                        supportActionBar.setDisplayShowTitleEnabled(false);
                        LayoutInflater mInflater = LayoutInflater.from(context);
                        @SuppressLint("InflateParams") View mCustomView = mInflater.inflate(R.layout.customtoolbar, null);
                        supportActionBar.setCustomView(mCustomView);
                        supportActionBar.setDisplayShowCustomEnabled(true);
                } else {
                        Log.e("UiUtils", "ActionBar is null");
                }
        }
}
