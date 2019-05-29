package tz.ac.udom.lasa.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tz.ac.udom.lasa.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        final String userProfile = sharedPreferences.getString("profile", null);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        if(userProfile == null)
                            // go to login
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                        else
                            // go to main
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));

                        // then finish this splash screen
                        finish();

                    }
                }
        , 2000);
    }
}
