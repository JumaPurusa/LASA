package tz.ac.udom.lasa.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {

   public static boolean isOnline(Context context){

       ConnectivityManager connectivityManager
               = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo networkInfo = connectivityManager
               .getActiveNetworkInfo();

       if(networkInfo != null && networkInfo.isConnected())
           return true;
       else
           return false;
   }
}
