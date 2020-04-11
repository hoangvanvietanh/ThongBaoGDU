package com.android.thongbaogdu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        // Use constants provided by Java Calendar class
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.setLocale(TimeZone.getDefault(),Locale.getDefault());
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.hideCalendar();
        actionBar = getSupportActionBar();
        // define a listener to receive callbacks when certain events happen.
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                actionBar.setTitle(dateFormatForMonth.format(dateClicked).toUpperCase());
                Toast.makeText(MainActivity.this, "Date: " + dateClicked.toString(), Toast.LENGTH_SHORT).show();
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                R.id.nav_today, R.id.nav_threeday,R.id.nav_week, R.id.nav_month)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        weekView = findViewById(R.id.weekView);
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
                weekView.goToCurrentTime();
                compactCalendarView.setCurrentDate(currentDate);
                actionBar.setTitle(dateFormatForMonth.format(currentDate).toUpperCase());
                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_today:
                Toast.makeText(MainActivity.this, "Today", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_threeday:
                Toast.makeText(MainActivity.this, "Three day", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_month:
                Toast.makeText(MainActivity.this, "Month", Toast.LENGTH_SHORT).show();
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
