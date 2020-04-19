package com.android.thongbaogdu.daoimpl;

import android.os.AsyncTask;

import com.android.thongbaogdu.config.Connection;
import com.android.thongbaogdu.dao.IScheduleDao;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.EmployeeAndSchedule;
import com.android.thongbaogdu.data.model.Schedule;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDaoImpl implements IScheduleDao {
    @Override
    public void addSchedule(Schedule schedule, String username) {
        EmployeeAndSchedule employeeAndSchedule = new EmployeeAndSchedule(username,schedule);
        Gson gson = new Gson();
        String json = gson.toJson(employeeAndSchedule);
        HttpSendData httpSendData = new HttpSendData(json);
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
                URL myUrl = new URL(Connection.URL_SendData);
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
}
