package tz.ac.udom.lasa.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import tz.ac.udom.lasa.Models.Student;
import tz.ac.udom.lasa.R;
import tz.ac.udom.lasa.Utils.DialogHelper;
import tz.ac.udom.lasa.Utils.JSONParser;
import tz.ac.udom.lasa.Utils.NetworkHelper;
import tz.ac.udom.lasa.Utils.RegNoHelper;
import tz.ac.udom.lasa.Utils.SharedPrefsHelper;

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

        progressDialog = DialogHelper.buildProgressCancelableDialog(LoginActivity.this,
                "Please Wait...",
                false);

        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);

        findViewById(R.id.buttonLogin).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard();

                        if(isFormValide()){
                            // send login request to server
                            if(NetworkHelper.isOnline(LoginActivity.this)){
                                // There is network start login
                            }else{
                                // show offline error
                                Snackbar.make(coordinatorLayout,
                                        "Your Device is offline",
                                        2000);
                            }

                        }


                    }
                }
        );
    }

    private boolean isFormValide(){

        if(TextUtils.isEmpty(editTextUsername.getText().toString()) ||
        TextUtils.isEmpty(editTextPassword.getText().toString())){
            Snackbar.make(coordinatorLayout, "Please enter all required fields",
                    2000);
            return false;
        }else if(RegNoHelper.isValid(editTextUsername.getText().toString())){
            // show reg no format error
            Snackbar.make(coordinatorLayout,
                    "Invalid registration number",
                    2000);
            return false;
        }

        return true;
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();

        if(imm != null && currentFocus != null)
            imm.hideSoftInputFromWindow(
                    currentFocus.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
            );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         menu.add(1000, 0, 0, "Help");
         return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // kill all activities to exit
        finishAffinity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;

            case 1000:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    private void onStartLogin(){

        // start the dialog
        if(!progressDialog.isShowing())
            progressDialog.show();

        progressDialog.findViewById(R.id.textCancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(urlConnection != null)
                            urlConnection.disconnect();

                        if(progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                }
        );

        new LoginTask().execute(editTextUsername.getText().toString(),
                editTextUsername.getText().toString());
    }

    private void onSuccessLogin(String jsonStudent){

        Log.d(TAG, "onSuccessLogin: jsonStudent : " + jsonStudent);

        // update prefs
        SharedPrefsHelper.syncStudentProfile(LoginActivity.this,
                jsonStudent);

        //go to main Activity
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

        // clear top
        finish();

    }

    private void onErrorOccurred(){

        editTextPassword.setText(""); // clear password editText

        // show error message
        Snackbar.make(coordinatorLayout,
                "Error has occurred, please try again",
                5000)
                .show();
    }

    private void onFailAuthentication(){

        // clear editTextPassword
        editTextPassword.setText("");

        //show  error message
        Snackbar.make(coordinatorLayout,
                "Wrong Registration number or Password",
                5000)
                .show();
    }

    public class LoginTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... params) {

            String regNo = params[0];
            String password = params[1];

            try {
                // opening/setting the HttpURLConnection
                URL url = new URL(getString(R.string.server_url) + "login.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(getString(R.string.request_method));
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                // setting the outputStream for passing data
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, "UTF-8")
                );

                // Creating the data to pass
                String data = URLEncoder.encode("registrationNo", "UTF-8") + "=" +
                        URLEncoder.encode(regNo, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // getting the inputStream for reading result or response
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String response = "";
                String line = "";

                while((line = reader.readLine()) != null){
                    response.concat(line).concat("\n");
                }

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(progressDialog.isShowing())
                progressDialog.dismiss();

            if(result == null || result.contains("<br>") || result.contains("<!DOCTYPE")){
                onErrorOccurred();
            }else if(result.contains("Registration Number or Password is not valid")){
                onFailAuthentication();
            }else{

                // Extract student profile from response string
//                Student student = new Gson().fromJson(result, Student.class);
                 Student student = JSONParser.parseStudent(result);

                 if(student != null){
                     student.setRegNo(editTextUsername.getText().toString());
                     onSuccessLogin(new Gson().toJson(student));
                 }else
                     onErrorOccurred();

            }
        }
    }
}