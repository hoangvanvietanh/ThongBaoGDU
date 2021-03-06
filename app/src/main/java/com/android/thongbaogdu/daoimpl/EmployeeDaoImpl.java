package com.android.thongbaogdu.daoimpl;

import android.os.AsyncTask;

import com.android.thongbaogdu.config.Connection;
import com.android.thongbaogdu.dao.IEmployeeDao;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.Employee;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class EmployeeDaoImpl implements IEmployeeDao {


    @Override
    public void updateEmployee(Employee employee)
    {
        Gson gson = new Gson();
        String json = gson.toJson(employee);
        EmployeeDaoImpl.HttpSendData httpSendData = new EmployeeDaoImpl.HttpSendData(json);
        httpSendData.execute();
    }


    private class HttpSendData extends AsyncTask<Void, Void, String> {
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
            try {
                // connect to the server
                URL myUrl = new URL(Connection.URL_UpdateEmployee);
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
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }
//    private DataApi dataApi = new DataApi();
//
//    @Override
//    public ArrayList<Employee> getAllEmployee() {
//        System.out.println("Dao impl ------------------------>" + dataApi.getAllEmployee().size());
//        return dataApi.getAllEmployee();
//    }
//
//    @Override
//    public Employee getEmployeeByUserName(String username) {
//        Employee employee = new Employee();
//        for (Employee emp:  dataApi.getAllEmployee())
//        {
//            if(emp.getAccount().getUserName().trim().equals(username.trim()))
//            {
//                employee = emp;
//            }
//        }
//        return employee;
//    }
}
