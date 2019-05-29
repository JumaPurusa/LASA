package tz.ac.udom.lasa.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import tz.ac.udom.lasa.R;

public class SharedPrefsHelper {
    private static SharedPreferences sharedPrefs;
    private static final String TAG = SharedPreferences.class.getName();

    private static String getSyncedStudentProfile(Context context){

        if(sharedPrefs == null)
            sharedPrefs = context.getSharedPreferences(context.getString(R.string.app_name),
                    context.MODE_PRIVATE);
        
        String jsonStudent = sharedPrefs.getString("profile", null);
        Log.d(TAG, "getSyncedStudentProfile: profile: " + jsonStudent);

        return jsonStudent;
    }
}
