package tz.ac.udom.lasa.Activities;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import java.net.HttpURLConnection;

import tz.ac.udom.lasa.R;
import tz.ac.udom.lasa.Utils.DialogHelper;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private EditText editTextUsername, editTextPassword;
    private CoordinatorLayout coordinatorLayout;
    AlertDialog progressDialog;
    private HttpURLConnection urlConnection;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Login");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        DialogHelper.buildProgressCancelableDialog(LoginActivity.this,
                "Please Wait...",
                false);

        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);


    }

    private boolean isFormValide(){

        if(TextUtils.isEmpty(editTextUsername.getText().toString()) ||
        TextUtils.isEmpty(editTextPassword.getText().toString())){
            Snackbar.make(coordinatorLayout, "Please enter all required fields",
                    2000);
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }
}