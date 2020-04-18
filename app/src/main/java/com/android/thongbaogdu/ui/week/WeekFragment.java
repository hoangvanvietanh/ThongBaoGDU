package com.android.thongbaogdu.ui.week;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class WeekFragment extends Fragment implements OnEventClickListener<Event>, OnMonthChangeListener<Event>,
        OnEventLongClickListener<Event>, OnEmptyViewLongClickListener , OnEmptyViewClickListener {

    private EventsDatabase database;
    private CompactCalendarView compactCalendarView;
    private View root;
    //private WeekViewModel weekViewModel;
    //private EventsDatabase database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        weekViewModel =
//                ViewModelProviders.of(this).get(WeekViewModel.class);
        root = inflater.inflate(R.layout.fragment_week, container, false);
        WeekView<Event> weekView = root.findViewById(R.id.weekView);
        database = new EventsDatabase(getActivity());
        weekView.setOnEventClickListener(this);
        weekView.setOnMonthChangeListener(this);
        weekView.setOnEventLongClickListener(this);
        weekView.setOnEmptyViewLongClickListener(this);
        //final TextView textView = root.findViewById(R.id.weekView);
//        weekViewModel.getWeekView().observe(getViewLifecycleOwner(), new Observer<WeekView>() {
//            @Override
//            public void onChanged(@Nullable WeekView weekView) {
//
//            }
//        });

        return root;
    }

    @NotNull
    @Override
    public List<WeekViewDisplayable<Event>> onMonthChange(@NonNull Calendar startDate,
                                                          @NonNull Calendar endDate) {
        return database.getEventsInRange(startDate, endDate);
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
        DateFormat sdf = SimpleDateFormat.getDateTimeInstance();
        String formattedTime = sdf.format(time.getTime());
        Toast.makeText(getActivity(), "Empty view long pressed: " + formattedTime, LENGTH_SHORT).show();
    }
    @Override
    public void onEmptyViewClicked(@NotNull Calendar calendar) {
        Toast.makeText(getActivity(), "Empty view long pressed: ", LENGTH_SHORT).show();
    }
}
