package com.android.thongbaogdu.daoimpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.android.thongbaogdu.config.Connection;
import com.android.thongbaogdu.dao.IAccountDao;
import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.data.model.Schedule;
import com.android.thongbaogdu.ui.login.LoginActivity;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AccountDaoImpl implements IAccountDao {
    private Employee employee = new Employee();

    @Override
    public Employee login(String userName, String password) {
        Account account = new Account(null,userName,password,null);
        Gson gson = new Gson();
        String json = gson.toJson(account);
//        AccountDaoImpl.HttpSendData httpSendData = new AccountDaoImpl.HttpSendData(json);
//        httpSendData.execute();
        AccountDaoImpl.HttpSendData request = new AccountDaoImpl.HttpSendData(json);
        try {
            String result = request.execute().get();
            request.onPostExecute(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(request.getResultLogin() == "success")
        {
            this.employee = request.getEmployee();
            return this.employee;
        }

        return null;
    }

    private class HttpSendData extends AsyncTask<Void, Void, String> {
        Employee employee = new Employee();
        String resultLogin = "fail";
        static final String REQUEST_METHOD = "POST";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;
        String _json = "";
        public HttpSendData(String json)
        {
            this._json = json;
        }

        @Override
        protected String doInBackground(Void... params){
            String result;
            String inputLine;

            try {
                // connect to the server
                URL myUrl = new URL(Connection.URL_Login);
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setDoOutput(true);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();

                String jsonInputString = this._json;
                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                    result = response.toString();
                }
            } catch(IOException e) {
                e.printStackTrace();
                result = "login_fail";
            }
            return result;
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                if(result.trim().equals("login_fail") == false )
                {
                    System.out.println("..............................");
                    System.out.println("--->" + result);
                    Context context = LoginActivity.getAppContext();
                    SharedPreferences sp= context.getSharedPreferences("user_data", context.MODE_PRIVATE);
                    SharedPreferences.Editor Ed = sp.edit();
                    Ed.putString("data",result);
                    Ed.commit();

                    JSONObject arr = new JSONObject(result);
                    ObjectMapper mapper = new ObjectMapper();
                    Gson gson = new Gson();

                        String employee_id = arr.getString("EmployeeId");
                        String full_name = arr.getString("FullName");
                        String email = arr.getString("Email");
                        String birth_day = arr.getString("BirthDate");
                        String phone_number = arr.getString("PhoneNumber");
                        String department = arr.getString("DepartmentName");
                        String position = arr.getString("PositionName");
                        String image = arr.getString("Image");
                        String address = arr.getString("Address");
                        String accountJson = arr.getString("Account");
                        JSONArray scheduleJson = arr.getJSONArray("Schedules");

                        ArrayList<Schedule> schedulesList =  new ArrayList<Schedule>();

                        for(int j = 0; j<scheduleJson.length();j++)
                        {
                            Schedule schedule = gson.fromJson(scheduleJson.get(j).toString(),Schedule.class);
                            schedulesList.add(schedule);
                        }
                        //List<Schedule> schedule = Arrays.asList(mapper.readValue(scheduleJson, Schedule[].class));
                        Account account = gson.fromJson(accountJson,Account.class);
                        System.out.println(account.getUserName() + "----------------------------------------$$$$---->" + schedulesList.size());
                        Employee employee = new Employee( employee_id ,full_name,  email,  birth_day,  phone_number,  position,  department,  image,  address,account,schedulesList);
                        //employeesArrayList.add(employee);
                        this.employee = employee;

                    resultLogin = "success";
                }
                else
                {
                    resultLogin = "fail";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public String getResultLogin()
        {
            return resultLogin;
        }
        public Employee getEmployee()
        {
            return employee;
        }
    }
}
