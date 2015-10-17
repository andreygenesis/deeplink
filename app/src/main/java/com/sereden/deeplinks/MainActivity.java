package com.sereden.deeplinks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleDeepLink(getIntent());
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink(intent);
    }

    /**
     * Test with this:
     * adb shell am start -W -a android.intent.action.VIEW -d "http://www.example.com/data/?Sample+DeepLink+test+1" com.sereden.deeplinks
     * OR
     * adb shell am start -W -a android.intent.action.VIEW -d "webacademy://data/?Sample+DeepLink+test+1" com.sereden.deeplinks
     *
     * @param intent
     */
    private void handleDeepLink(Intent intent) {
        if (intent != null) {
            Uri data = intent.getData();
            // may not happen but...
            if (data != null) {
                // get old passed data.
                Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(MainFragment.TAG);
                StringBuilder sb = new StringBuilder();
                // Attach received results to old data
                if (oldFragment != null) {
                    sb.append(MainFragment.getData(oldFragment.getArguments()));
                }

                // get new data, encode and attach to old data
                String query = data.getQuery();
                if (!TextUtils.isEmpty(query)) {
                    try {
                        sb.append(URLDecoder.decode(data.getQuery(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        // Do nothing
                        e.printStackTrace();
                    }
                }
                handleData(sb.toString());
            } else {
                handleData();
            }
        } else {
            handleData();
        }
    }

    /**
     * Either inflate with empty data or get previous show fragment and show to user
     */
    private void handleData() {
        Fragment fragment;
        // first init ever
        if ((fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.TAG)) == null) {
            handleData(getString(R.string.simple_data));
        } else {
            // rotation
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    private void handleData(String data) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, MainFragment.getInstance(data), MainFragment.TAG)
                .commit();
    }
}
