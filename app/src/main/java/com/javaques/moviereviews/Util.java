package com.javaques.moviereviews;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Random;

/**
 * Created by Anil on 22-Sep-15.
 */
public class Util {
    private static final String TAG = "Util";

    public static int gridColor() {
        Random random = new Random();
        int color = R.color.white;

        int num = random.nextInt(100) % 14;

        Log.d(TAG, "" + num);
        switch (num) {
            case 0:
                color = R.color.colorGridItem1;
                break;
            case 1:
                color = R.color.colorGridItem2;
                break;
            case 2:
                color = R.color.colorGridItem3;
                break;
            case 3:
                color = R.color.colorGridItem4;
                break;
            case 4:
                color = R.color.colorGridItem5;
                break;
            case 5:
                color = R.color.colorGridItem6;
                break;
            case 6:
                color = R.color.colorGridItem7;
                break;
            case 7:
                color = R.color.colorGridItem8;
                break;
            case 8:
                color = R.color.colorGridItem9;
                break;
            case 9:
                color = R.color.colorGridItem10;
                break;
            case 10:
                color = R.color.colorGridItem11;
                break;
            case 11:
                color = R.color.colorGridItem12;
                break;
            case 12:
                color = R.color.colorGridItem13;
                break;
            case 13:
                color = R.color.colorGridItem14;
                break;
            case 14:
                color = R.color.colorGridItem15;
                break;


        }

        return color;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
