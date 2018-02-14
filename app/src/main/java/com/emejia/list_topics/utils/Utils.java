package com.emejia.list_topics.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.widget.Toast;

/**
 * Created by emejia on 09/03/2017.
 */

public class Utils {

    public static boolean isConnectedToNetwork(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo != null /*&& networkInfo.isConnectedOrConnecting()*/
                && networkInfo.isConnected() && networkInfo.isAvailable()) {
            connected = true;
        } else {
            connected = false;
        }

        return connected;
    }

    public static void cusomToast(Context context, LayoutInflater inflater, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }



}
