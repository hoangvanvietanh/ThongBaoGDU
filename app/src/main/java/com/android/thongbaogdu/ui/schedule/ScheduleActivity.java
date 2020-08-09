package com.android.thongbaogdu.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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


public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    ScheduleService scheduleService = new ScheduleService();
    Switch switchAllDay,switchCancel;
    Button  btnDateFrom, btnDateTo, btnTimeFrom, btnTimeTo;
    EditText txtDate, txtTime,editText;
    Spinner spinner;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateFrom, timeFrom, dateTo, timeTo, dateTimeFrom, dateTimeTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorGduBlue));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSchedule);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thêm sự kiện");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_color_events, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        btnDateFrom=(Button)findViewById(R.id.buttonDateFrom);
        btnDateTo=(Button)findViewById(R.id.buttonDateTo);

        btnTimeFrom=(Button)findViewById(R.id.buttonTimeFrom);
        btnTimeTo=(Button)findViewById(R.id.buttonTimeTo);

        btnDateFrom.setOnClickListener(this);
        btnDateTo.setOnClickListener(this);
        btnTimeFrom.setOnClickListener(this);
        btnTimeTo.setOnClickListener(this);

        //System.out.println("check nè========>" + btnDateFrom + "---------" + btnTimeFrom +"--"+btnTimeTo);
        setDateTimeButton(btnDateFrom,btnDateTo,btnTimeFrom,btnTimeTo);

        //System.out.println("get duration ====================>" + getDuration(dateTimeTo,dateTimeFrom));
        //System.out.println(dateTimeFrom);
        //System.out.println(dateTimeTo);

        editText = (EditText)findViewById(R.id.editTextContent);
        switchAllDay = (Switch)findViewById(R.id.switchAllDay);
        switchCancel = (Switch)findViewById(R.id.switchCancel);
        final Button button = findViewById(R.id.btnAddSchedule);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int duration  = getDuration(dateTimeTo,dateTimeFrom);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


                boolean isAllDay = switchAllDay.isChecked();
                boolean isCancel = switchCancel.isChecked();
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
                    Schedule schedule = new Schedule(100,Year,Month+1,Day,Hour,Minute,duration,spinner.getSelectedItemPosition(),isAllDay,isCancel,editText.getText().toString());
                    SharedPreferences sp1=getSharedPreferences("Login", MODE_PRIVATE);
                    String username=sp1.getString("UserName", null);

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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
         parent.getItemAtPosition(pos);
        Toast.makeText(this, parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
        Bundle b = this.getIntent().getExtras();
        String eventDate = b.getString("eventDate");
        String eventTime = b.getString("eventTime");
        String eventId = b.getString("eventId");
        String eventContent = b.getString("eventContent");
        Calendar cal = Calendar.getInstance();
        if(eventDate != null && eventTime != null && eventContent != null)
        {
            try {
                this.dateFrom = eventDate;
                this.timeFrom = eventTime;
                dateTimeFrom = eventDate + " "+eventTime;
                dateFrom.setText(eventDate);
                btnTimeFrom.setText(eventTime);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date date = dateFormat.parse(dateTimeFrom);
                cal.setTime(date);
                editText.setText(eventContent);
                //System.out.println("va cute"+date);
                Toast.makeText(this, "okey nè hihi3", Toast.LENGTH_SHORT).show();
            }catch (ParseException e) {
                e.printStackTrace();
            }

        }
        else
        {
            this.dateFrom = formattedDateFrom;
            this.timeFrom = formattedTimeFrom;
            dateTimeFrom = formattedDateFrom +" "+formattedTimeFrom;
            dateFrom.setText(formattedDateFrom);
            cal.setTime(currentTime);
        }


        cal.add(Calendar.MINUTE, 30);
        String formattedTimeTo = formatTime.format(cal.getTime());
        String formattedDateTo = formatDate.format(cal.getTime());
        dateTo.setText(formattedDateTo);
        timeTo.setText(formattedTimeTo);
        this.dateTo = formattedDateTo;
        this.timeTo = formattedTimeTo;
        dateTimeTo = formattedDateTo +" "+formattedTimeTo;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
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
