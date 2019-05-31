package tz.ac.udom.lasa.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import tz.ac.udom.lasa.R;

public class SharedPrefsHelper {
    private static SharedPreferences sharedPrefs;
    private static final String TAG = SharedPreferences.class.getName();

    public static String getSyncedStudentProfile(Context context){

        if(sharedPrefs == null)
            sharedPrefs = context.getSharedPreferences(context.getString(R.string.app_name),
                    context.MODE_PRIVATE);
        
        String jsonStudent = sharedPrefs.getString("profile", null);
        Log.d(TAG, "getSyncedStudentProfile: profile: " + jsonStudent);

        return jsonStudent;
    }

    public static void syncStudentProfile(Context context, String jsonStudent){

        if(sharedPrefs == null)
            sharedPrefs = context.getSharedPreferences(context.getString(R.string.app_name),
                    Context.MODE_PRIVATE);

        if(jsonStudent != null){
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString("profile", jsonStudent);
            editor.apply();
        }else{
            sharedPrefs.edit().remove("profile").apply();
        }



    }

}
