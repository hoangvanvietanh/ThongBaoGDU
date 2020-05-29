package com.android.thongbaogdu.ui.login;

import android.app.Activity;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.thongbaogdu.MainActivity;
import com.android.thongbaogdu.R;
import com.android.thongbaogdu.config.Connection;
import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.data.model.Schedule;
import com.android.thongbaogdu.services.EmployeeServices;
import com.android.thongbaogdu.services.NotificationService;
import com.android.thongbaogdu.services.ReminderBroadcast;
import com.android.thongbaogdu.ui.schedule.ScheduleActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    //private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
    //private EmployeeServices employeeServices = new EmployeeServices();
    private EditText usernameEditText;
    private EditText passwordEditText;
    private static Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginActivity.context = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorGduBlue));
        }

        loginViewModel = ViewModelProviders.of(LoginActivity.this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);



        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
        String userName=sp1.getString("UserName", null);
        String password = sp1.getString("Password", null);


        SharedPreferences sp= context.getSharedPreferences("user_data", context.MODE_PRIVATE);
        String data=sp.getString("data", null);


        System.out.println("===> username: " + userName);
        System.out.println("===> password: " + password);

        if(userName != null)
        {
            if(password != null)
            {
                loginViewModel.login(userName,password);
            }
        }


        createNotificationChannel();

        //employeeArrayList = employeeServices.getAllEmployee();

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });



        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("UserName",usernameEditText.getText().toString());
                Ed.putString("Password",passwordEditText.getText().toString());
                Ed.commit();

                SharedPreferences sp1=getSharedPreferences("user_data", MODE_PRIVATE);
                SharedPreferences.Editor Ed1 =sp1.edit();
                Ed1.putString("data",null);
                Ed1.commit();
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });



    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static Context getAppContext() {
        return LoginActivity.context;
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        myIntent.putExtra("USERNAME",model.getDisplayName());
        startActivity(myIntent);

//        for(Employee employee: employeeArrayList)
//        {
//            Toast.makeText(this, employee.getFullName(), Toast.LENGTH_SHORT).show();
//        }
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("UserName",null);
        Ed.putString("Password",null);
        Ed.apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "vietanh";
            String decription = "My name is viet anh";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("vietanh",name,importance);
            channel.setDescription(decription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    public class HttpSendData extends AsyncTask<Void, Void, String> {
//        static final String REQUEST_METHOD = "POST";
//        static final int READ_TIMEOUT = 15000;
//        static final int CONNECTION_TIMEOUT = 15000;
//        String _json = "";
//        public HttpSendData(String json)
//        {
//            this._json = json;
//        }
//
//        @Override
//        protected String doInBackground(Void... params){
//            try {
//                // connect to the server
//                URL myUrl = new URL("http://192.168.1.31:1200/Ma_so_Xu_ly=Ket_noi_tu_winform");
//                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
//                connection.setRequestMethod(REQUEST_METHOD);
//                connection.setRequestProperty("Content-Type", "application/json; utf-8");
//                connection.setDoOutput(true);
//                connection.setReadTimeout(READ_TIMEOUT);
//                connection.setConnectTimeout(CONNECTION_TIMEOUT);
//                connection.connect();
//
//                String jsonInputString = this._json;
//                try(OutputStream os = connection.getOutputStream()) {
//                    byte[] input = jsonInputString.getBytes("utf-8");
//                    os.write(input, 0, input.length);
//                }
//                try(BufferedReader br = new BufferedReader(
//                        new InputStreamReader(connection.getInputStream(), "utf-8"))) {
//                    StringBuilder response = new StringBuilder();
//                    String responseLine = null;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
//                    System.out.println(response.toString());
//                }
//            } catch(IOException e) {
//                e.printStackTrace();
//            }
//            return "";
//        }
//        protected void onPostExecute(String result){
//            super.onPostExecute(result);
//        }
//    }
}
