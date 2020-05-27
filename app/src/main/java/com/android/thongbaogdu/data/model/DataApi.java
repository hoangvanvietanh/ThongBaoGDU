package com.android.thongbaogdu.data.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.android.thongbaogdu.R;
import com.android.thongbaogdu.config.Connection;
import com.android.thongbaogdu.ui.login.LoginActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataApi {

    private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();

    public DataApi()
    {
        HttpGetRequest request = new HttpGetRequest();
        try {
            String result = request.execute().get();
            request.onPostExecute(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        employeeArrayList = request.getAllEmployee();
    }

    public ArrayList<Employee> getAllEmployee()
    {
        return employeeArrayList;
    }

    private List<Employee> getData(String url, Context context)
    {
        final List<Employee> listAccount = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //String pageName = obj.getJSONObject("pageInfo").getString("pageName");
                            JSONArray arr = obj.getJSONArray("Danh_sach_Tai_khoan");

//                            for (int i = 0; i < arr.length(); i++) {
//                                String _id = arr.getJSONObject(i).getString("_id");
//                                String email = arr.getJSONObject(i).getString("email");
//                                String password = arr.getJSONObject(i).getString("password");
//                                String role = arr.getJSONObject(i).getString("name");
//
//                                Account account = new Account(_id, email, password, role);
//                                employeeArrayList.add(account);
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
        return listAccount;
    }

    public List<Employee> listAccount(Context context)
    {
        return getData(Connection.URL_GetAllAccount,context);
    }


    public class HttpGetRequest extends AsyncTask<Void, Void, String> {
        ArrayList<Employee> employeesArrayList = new ArrayList<Employee>();
        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(Void... params){
            String result;
            String inputLine;

            try {
                // connect to the server
                URL myUrl = new URL(Connection.URL_GetAllAccount);
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.connect();

                // get the string from the input stream
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();
                result = stringBuilder.toString();

            } catch(IOException e) {
                e.printStackTrace();
                result = "error";
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            String a = "";
            try {
                JSONArray arr = new JSONArray(result);
                ObjectMapper mapper = new ObjectMapper();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    String employee_id = arr.getJSONObject(i).getString("EmployeeId");
                    String full_name = arr.getJSONObject(i).getString("FullName");
                    String email = arr.getJSONObject(i).getString("Email");
                    String birth_day = arr.getJSONObject(i).getString("BirthDate");
                    String phone_number = arr.getJSONObject(i).getString("PhoneNumber");
                    String department = arr.getJSONObject(i).getString("DepartmentName");
                    String position = arr.getJSONObject(i).getString("PositionName");
                    String image = arr.getJSONObject(i).getString("Image");
                    String address = arr.getJSONObject(i).getString("Address");
                    String accountJson = arr.getJSONObject(i).getString("Account");
                    JSONArray scheduleJson = arr.getJSONObject(i).getJSONArray("Schedules");
                    ArrayList<Schedule> schedulesList =  new ArrayList<Schedule>();
                    for(int j = 0; j<scheduleJson.length();j++)
                    {
                        Schedule schedule = gson.fromJson(scheduleJson.get(j).toString(),Schedule.class);
                        schedulesList.add(schedule);
                    }
                    //List<Schedule> schedule = Arrays.asList(mapper.readValue(scheduleJson, Schedule[].class));
                    Account account = gson.fromJson(accountJson,Account.class);
                    System.out.println(account.getUserName() + "----------------------------------------||||||---->" + schedulesList.size());
                    Employee employee = new Employee( employee_id ,full_name,  email,  birth_day,  phone_number,  position,  department,  image,  address,account,schedulesList);
                    employeesArrayList.add(employee);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //tvServerResponse.setText(a);
        }

        public ArrayList<Employee> getAllEmployee()
        {
            System.out.println("Check reurn employee--->" + employeesArrayList.size());
            return employeesArrayList;
        }
    }



}
