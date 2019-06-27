/**
 * com.utils contains additional tools like InternetConnection checker.
 */

package com.utils;

/**
 * Checks internet connection status.
 * @author Paweł Turoń
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class InternetConnection {
    // checks internet connection
    public static boolean checkInternetConnection(@NonNull Context context){
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
