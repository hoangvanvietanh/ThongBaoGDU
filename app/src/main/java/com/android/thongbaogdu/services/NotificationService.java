package com.android.thongbaogdu.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.data.model.Schedule;
import com.android.thongbaogdu.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class NotificationService {

    public void createNotification(Context context, String ALARM_SERVICE, ArrayList<Employee> employeeArrayList)
    {
        System.out.println("-->" + employeeArrayList.size());
        for(Employee employee: employeeArrayList)
        {
            System.out.println("2");
            for (Schedule schedule: employee.getSchedules())
            {
                System.out.println("3");
                System.out.println(schedule.getMonth()-1+"-" + schedule.getDay_of_month()+"-" + schedule.getHour() +"-"+ schedule.getMinute());
                //Toast.makeText(context, "Remider set!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,(int)System.currentTimeMillis(),intent,PendingIntent.FLAG_ONE_SHOT);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(schedule.getYear(),schedule.getMonth()-1,schedule.getDay_of_month(),schedule.getHour(),schedule.getMinute(),0);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
            }

        }

    }
}
