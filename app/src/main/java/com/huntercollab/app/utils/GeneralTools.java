package com.huntercollab.app.utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import android.content.Context;
import android.content.Intent;

import com.huntercollab.app.MainActivity;

import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;

public class GeneralTools {

    //Creates an ASyncHttpClient, sets cookies, and returns it.
    public static AsyncHttpClient createAsyncHttpClient(Context context) {
        AsyncHttpClient client = new AsyncHttpClient();
        //Retrieve cookie store for this application context.
        PersistentCookieStore cookieStore = new PersistentCookieStore(context.getApplicationContext());
        //attach those cookies to the HttpClient which will be making the request
        client.setCookieStore(cookieStore);
        client.setTimeout(30 * 1000); // 60 second timeout
        return client;
    }

    //Creates an ASyncHttpClient, doesn't set cookies.
    public static AsyncHttpClient createAsyncHttpClient() {
        AsyncHttpClient client = new AsyncHttpClient();

        return client;
    }

    public static String getAuthToken(Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context.getApplicationContext());
        List<Cookie> cks = cookieStore.getCookies();
        for (Cookie c : cks) {
            if (c.getName().equals("capstoneAuth")) {
                return c.getValue();
            }
        }
        return null;
    }

    // restart the app
    public static void doRestart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        //Runtime.getRuntime().exit(0);
    }

}
