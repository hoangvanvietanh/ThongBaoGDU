package com.android.thongbaogdu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.android.thongbaogdu.services.NotificationService;
import com.android.thongbaogdu.ui.login.LoginActivity;
import com.android.thongbaogdu.ui.profile.ProfileActivity;
import com.android.thongbaogdu.ui.schedule.ScheduleActivity;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import android.app.FragmentTransaction;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity  {
    private  Boolean isShowCalendar = false;
    private Toolbar toolbar;
    private CompactCalendarView compactCalendarView;
    private WeekView weekView;
    private static final String TAG = "MyActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("dd-MMMM- yyyy", Locale.getDefault());
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private  Date currentDate = new Date();
    private ActionBar actionBar;
    private String textTest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorGduBlue));
        }
        //Toast.makeText(this, textTest, Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setLocale(TimeZone.getDefault(),Locale.getDefault());
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.hideCalendar();
        actionBar = getSupportActionBar();

        String username = getIntent().getStringExtra("USERNAME");
        Toast.makeText(this, "set đc nhè", Toast.LENGTH_SHORT).show();

        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                actionBar.setTitle(dateFormatForMonth.format(dateClicked).toUpperCase());
                weekView = findViewById(R.id.weekView);
                Calendar calendar =Calendar.getInstance();
                calendar.setTime(dateClicked);
                weekView.goToDate(calendar);
                Toast.makeText(MainActivity.this, "Datess: " + dateClicked.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth).toUpperCase());
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                String username = getIntent().getStringExtra("USERNAME");

                NotificationService notificationService = new NotificationService();
                notificationService.createNotification(MainActivity.this, ALARM_SERVICE, username);

                Intent myIntent = new Intent(MainActivity.this, ScheduleActivity.class);
                myIntent.putExtra("USERNAME",username);
                startActivity(myIntent);
                Toast.makeText(MainActivity.this, "Okey", Toast.LENGTH_SHORT).show();
            }
        });
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu_camera);


        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_today, R.id.nav_threeday,R.id.nav_week)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "id: "+ item.getItemId(), Toast.LENGTH_SHORT).show();
                WeekView weekView = findViewById(R.id.weekView);
                switch (item.getItemId()) {
                    case R.id.nav_today:
                        Toast.makeText(MainActivity.this, "Today", Toast.LENGTH_SHORT).show();
                        weekView.setNumberOfVisibleDays(1);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_threeday:
                        Toast.makeText(MainActivity.this, "Three day", Toast.LENGTH_SHORT).show();
                        weekView.setNumberOfVisibleDays(3);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_week:
                        Toast.makeText(MainActivity.this, "Month", Toast.LENGTH_SHORT).show();
                        weekView.setNumberOfVisibleDays(7);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_info:
                        Intent myIntentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(myIntentProfile);
                        return true;
                    case R.id.nav_Logout:
                        //Toast.makeText(this, "Logout ^^", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor Ed=sp.edit();
                        Ed.putString("UserName",null);
                        Ed.putString("Password",null);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //Toast.makeText(this, "Okey con dê " + usernameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                        Ed.commit();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        weekView = findViewById(R.id.weekView);
        Toast.makeText(this, "id: " + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.miProfile:
                if(isShowCalendar == true)
                {
                    isShowCalendar = false;
                    item.setIcon(R.drawable.ic_menu_show);
                    compactCalendarView.hideCalendarWithAnimation();
                    return true;
                }
                    isShowCalendar = true;
                    item.setIcon(R.drawable.ic_menu_hidden);
                    compactCalendarView.showCalendarWithAnimation();
                return true;
            case R.id.show_today:
                weekView.goToCurrentTime();// cái này là phần dưới
                compactCalendarView.setCurrentDate(currentDate);// cái này là lịch ở trên
                actionBar.setTitle(dateFormatForMonth.format(currentDate).toUpperCase());
                //Toast.makeText(MainActivity.this, "ok haha", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
