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
        Toast.makeText(getActivity(), "Clicked " + event.getTitle(), LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongClick(@NonNull Event event, @NonNull RectF eventRect) {
        Toast.makeText(getActivity(), "Long-clicked event: " + event.getTitle(), LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongClick(@NonNull Calendar time) {
        String username = getActivity().getIntent().getStringExtra("USERNAME");
        Intent myIntent2 = new Intent(getContext(), ScheduleActivity.class);
        myIntent2.putExtra("USERNAME",username);
        startActivity(myIntent2);

        DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
        String formattedTime = sdf.format(time.getTime());
        Toast.makeText(getActivity(), "Empty view long pressed: " + formattedTime, LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewClicked(@NotNull Calendar calendar) {
        Toast.makeText(getActivity(), "Empty view long pressed: ^^", LENGTH_SHORT).show();;
    }
}
