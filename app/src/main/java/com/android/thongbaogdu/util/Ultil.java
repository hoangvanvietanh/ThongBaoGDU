package com.android.thongbaogdu.util;

import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.data.model.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ultil {
    public static Employee convertJSONtoEmployee(String json)
    {
        try {
            JSONObject arr = new JSONObject(json);
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
            return employee;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
