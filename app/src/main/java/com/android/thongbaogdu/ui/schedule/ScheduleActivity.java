package com.android.thongbaogdu.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.thongbaogdu.MainActivity;
import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.model.Schedule;
import com.android.thongbaogdu.services.EmployeeServices;
import com.android.thongbaogdu.services.NotificationService;
import com.android.thongbaogdu.services.ScheduleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;


public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {

    ScheduleService scheduleService = new ScheduleService();

    Button  btnDateFrom, btnDateTo, btnTimeFrom, btnTimeTo;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateFrom, timeFrom, dateTo, timeTo, dateTimeFrom, dateTimeTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        btnDateFrom=(Button)findViewById(R.id.buttonDateFrom);
        btnDateTo=(Button)findViewById(R.id.buttonDateTo);

        btnTimeFrom=(Button)findViewById(R.id.buttonTimeFrom);
        btnTimeTo=(Button)findViewById(R.id.buttonTimeTo);

        btnDateFrom.setOnClickListener(this);
        btnDateTo.setOnClickListener(this);
        btnTimeFrom.setOnClickListener(this);
        btnTimeTo.setOnClickListener(this);

        System.out.println("check nè========>" + btnDateFrom + "---------" + btnTimeFrom +"--"+btnTimeTo);
        setDateTimeButton(btnDateFrom,btnDateTo,btnTimeFrom,btnTimeTo);

        System.out.println("get duration ====================>" + getDuration(dateTimeTo,dateTimeFrom));
        System.out.println(dateTimeFrom);
        System.out.println(dateTimeTo);



        final Button button = findViewById(R.id.btnAddSchedule);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int duration  = getDuration(dateTimeTo,dateTimeFrom);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                EditText editText = (EditText)findViewById(R.id.editTextContent);
                Switch switchAllDay = (Switch)findViewById(R.id.switchAllDay);
                boolean isAllDay = switchAllDay.isChecked();
                try {
                    dateTimeFrom = dateFrom + " " + timeFrom;
                    dateTimeTo = dateTo + " " + timeTo;

                    Date dateTime = dateFormat.parse(dateTimeFrom);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateTime);

                    int Year = cal.get(Calendar.YEAR);
                    int Month = cal.get(Calendar.MONTH);
                    int Day = cal.get(Calendar.DAY_OF_MONTH);
                    int Hour = cal.get(Calendar.HOUR_OF_DAY);
                    int Minute = cal.get(Calendar.MINUTE);

                    System.out.println("Kết quả ===>" + Year +"--"+Month+"--"+Day+"--"+Hour+"--"+Minute + "----"+duration);
                    Toast.makeText(ScheduleActivity.this, "hahahah", Toast.LENGTH_SHORT).show();
                    Schedule schedule = new Schedule(100,Year,Month+1,Day,Hour,Minute,duration,4,isAllDay,false,editText.getText().toString());
                    String username = getIntent().getStringExtra("USERNAME");
                    scheduleService.addSchedule(schedule,username);
                    Intent myIntent = new Intent(ScheduleActivity.this, MainActivity.class);

                    NotificationService notificationService = new NotificationService();
                    notificationService.createNotification2(ScheduleActivity.this, ALARM_SERVICE, username, schedule);

                    myIntent.putExtra("USERNAME",username);
                    startActivity(myIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // Code here executes on main thread after user presses button
            }
        });
    }

    public void setDateTimeButton(Button dateFrom,Button dateTo,Button timeFrom,Button timeTo)
    {
        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateFrom = formatDate.format(currentTime);
        System.out.println(formattedDateFrom);

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        String formattedTimeFrom = formatTime.format(currentTime);
        timeFrom.setText(formattedTimeFrom);
        this.dateFrom = formattedDateFrom;
        this.timeFrom = formattedTimeFrom;
        dateTimeFrom = formattedDateFrom +" "+formattedTimeFrom;
        dateFrom.setText(formattedDateFrom);

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        cal.add(Calendar.MINUTE, 30);
        String formattedTimeTo = formatTime.format(cal.getTime());
        String formattedDateTo = formatDate.format(cal.getTime());
        dateTo.setText(formattedDateTo);
        timeTo.setText(formattedTimeTo);
        this.dateTo = formattedDateTo;
        this.timeTo = formattedTimeTo;
        dateTimeTo = formattedDateTo +" "+formattedTimeTo;
    }


    public int getDuration(String dateTimeFrom, String dateTimeTo)
    {

        int totalMinute = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date dateTime1 = dateFormat.parse(dateTimeFrom);
            Date dateTime2 = dateFormat.parse(dateTimeTo);
            long diff = dateTime1.getTime() - dateTime2.getTime();
            System.out.println("fuck === >" +dateTime2.getTime() +"----"+dateTime1.getTime()+"-----" + diff);
            totalMinute = (int)diff/1000/60;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return totalMinute;
    }
    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();

        if (v == btnDateFrom) {

            // Get Current Date
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            btnDateFrom.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            dateFrom = dayOfMonth + "/" + (month + 1) + "/" + year;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if(v == btnDateTo)
        {
            // Get Current Date
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            btnDateTo.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                            dateTo = dayOfMonth + "/" + (month + 1) + "/" + year;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if(v == btnTimeFrom)
        {
            // Get Current Time
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            btnTimeFrom.setText(hourOfDay + ":" + minute);
                            timeFrom =  hourOfDay + ":" + minute;
                        }
                    }, mHour, mMinute, true );
            timePickerDialog.show();
        }
        else if(v == btnTimeTo)
        {
            // Get Current Time
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            btnTimeTo.setText(hourOfDay + ":" + minute);
                            timeTo =  hourOfDay + ":" + minute;
                        }
                    }, mHour, mMinute, true );
            timePickerDialog.show();
        }
    }

}
