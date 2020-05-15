package com.android.thongbaogdu.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.data.model.Schedule;
import com.android.thongbaogdu.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class NotificationService {


    public void createNotification2(Context context, String ALARM_SERVICE, String username, Schedule schedule)
    {
        System.out.println("3");
        System.out.println(schedule.getMonth()-1+"-" + schedule.getDay_of_month()+"-" + schedule.getHour() +"-"+ schedule.getMinute());
        Toast.makeText(context, "Remider set haha!" +schedule.getHour()+"----"+schedule.getMinute() , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, ReminderBroadcast.class);
        int currentTimeMilli = (int)System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,currentTimeMilli,intent,PendingIntent.FLAG_ONE_SHOT);

        /*
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "vietanh")
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle("Thông báo họp vào lúc " + schedule.getHour() +":"+ schedule.getMinute())
                .setContentText("Nội dung: " + schedule.getContent())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Nội dung: " + schedule.getContent()))
                .setPriority(NotificationCompat.BADGE_ICON_LARGE);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(currentTimeMilli,builder.build());*/

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        System.out.println("time---------->"+ calendar.getTimeInMillis());
        calendar.set(schedule.getYear(),schedule.getMonth()-1,schedule.getDay_of_month(),schedule.getHour(),schedule.getMinute(),0);
        alarmManager.set(AlarmManager.RTC_WAKEUP , calendar.getTimeInMillis(), pendingIntent);
    }

    public void createNotification(Context context, String ALARM_SERVICE, String username)
    {
        EmployeeServices employeeServices = new EmployeeServices();
        ArrayList<Employee> employeeArrayList = employeeServices.getAllEmployee();
        for(Employee employee: employeeArrayList)
        {
            if(employee.getEmail().equals(username))
            {
                for (Schedule schedule: employee.getSchedules())
                {
                    System.out.println("3");
                    System.out.println(schedule.getMonth()-1+"-" + schedule.getDay_of_month()+"-" + schedule.getHour() +"-"+ schedule.getMinute());
                    Toast.makeText(context, "Remider set haha!" +schedule.getHour()+"----"+schedule.getMinute() , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ReminderBroadcast.class);
                    int currentTimeMilli = (int)System.currentTimeMillis();
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,currentTimeMilli,intent,PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "vietanh")
                            .setSmallIcon(R.drawable.ic_menu_camera)
                            .setContentTitle("Thông báo họp vào lúc " + schedule.getHour() +":"+ schedule.getMinute())
                            .setContentText("Nội dung: " + schedule.getContent())
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText("Nội dung: " + schedule.getContent()))
                            .setPriority(NotificationCompat.BADGE_ICON_LARGE);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    notificationManagerCompat.notify(currentTimeMilli,builder.build());

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    System.out.println("time---------->"+ calendar.getTimeInMillis());
                    calendar.set(schedule.getYear(),schedule.getMonth()-1,schedule.getDay_of_month(),schedule.getHour(),schedule.getMinute(),0);
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP , calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                }
            }


        }

    }
}
