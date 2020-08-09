package com.android.thongbaogdu.ui.calendar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alamkanak.weekview.OnEmptyViewClickListener;
import com.alamkanak.weekview.OnEmptyViewLongClickListener;
import com.alamkanak.weekview.OnEventClickListener;
import com.alamkanak.weekview.OnEventLongClickListener;
import com.alamkanak.weekview.OnMonthChangeListener;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewDisplayable;
import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.EventsDatabase;
import com.android.thongbaogdu.data.model.Event;
import com.android.thongbaogdu.ui.schedule.ScheduleActivity;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

public class CalendarFragment extends Fragment implements OnEventClickListener<Event>, OnMonthChangeListener<Event>,
        OnEventLongClickListener<Event>, OnEmptyViewLongClickListener, OnEmptyViewClickListener {

    private EventsDatabase database;
    private CompactCalendarView compactCalendarView;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sp1=getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String userName=sp1.getString("UserName", null);
        String password = sp1.getString("Password", null);
        database = new EventsDatabase(getActivity(),userName,password);
        SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("dd-MMMM- yyyy", Locale.getDefault());
        Date currentDate = new Date();
        root = inflater.inflate(R.layout.fragment_calendar, container, false);
        WeekView<Event> weekView = root.findViewById(R.id.weekView);

        weekView.setOnEventClickListener(this);
        weekView.setOnMonthChangeListener(this);
        weekView.setOnEventLongClickListener(this);
        weekView.setOnEmptyViewLongClickListener(this);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(dateFormatForMonth.format(currentDate).toUpperCase());
        weekView.goToCurrentTime();
        return root;
    }

    @NotNull
    @Override
    public List<WeekViewDisplayable<Event>> onMonthChange(@NonNull Calendar startDate,
                                                          @NonNull Calendar endDate) {

        return database.getEventsInRange();
    }

    @Override
    public void onEventClick(@NonNull Event event, @NonNull RectF eventRect) {
        Toast.makeText(getActivity(), "Clicked " + event.getTitle() + "id: " + event.getId(), LENGTH_SHORT).show();

    }

    @Override
    public void onEventLongClick(@NonNull Event event, @NonNull RectF eventRect) {
        String[] arrays = event.getTitle().split("of");
        System.out.println(event.getTitle() + "========" + arrays[1].toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //String dateEvent = dateFormat.format(arrays[1]);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        //String timeEvent = timeFormat.format(arrays[1].substring(0,6).toString());
        String getTime = arrays[1].substring(0,6);
        String[] getDate = arrays[1].substring(7).split(" ");
        System.out.println("đc nè hihi: " + getDate[0] + "/" + getDate[2].split(",")[0] + "/" + getDate[3]);
        Intent myIntent2 = new Intent(getContext(), ScheduleActivity.class);
        String username = getActivity().getIntent().getStringExtra("USERNAME");
        myIntent2.putExtra("USERNAME",username);
        myIntent2.putExtra("eventDate",getDate[0] + "/" + getDate[2].split(",")[0] + "/" + getDate[3]);
        myIntent2.putExtra("eventTime",arrays[1].substring(0,6));
        myIntent2.putExtra("eventId", event.getId());
        myIntent2.putExtra("eventContent",event.getLocation());
        startActivity(myIntent2);
        Toast.makeText(getActivity(), "Long-clicked event: " + event.getLocation(), LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongClick(@NonNull Calendar time) {
        System.out.println("-->nai" + time.getTime());
        //DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateEvent = dateFormat.format(time.getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timeEvent = timeFormat.format(time.getTime());
        String username = getActivity().getIntent().getStringExtra("USERNAME");
        Intent myIntent2 = new Intent(getContext(), ScheduleActivity.class);
        myIntent2.putExtra("USERNAME",username);
        myIntent2.putExtra("eventDate",dateEvent);
        myIntent2.putExtra("eventTime",timeEvent);
        startActivity(myIntent2);


        //Toast.makeText(getActivity(), "Empty view long pressed: " + formattedTime, LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewClicked(@NotNull Calendar calendar) {
        Toast.makeText(getActivity(), "Empty view long pressed: ^^", LENGTH_SHORT).show();;
    }
}
