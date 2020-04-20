package com.android.thongbaogdu.data

import android.content.Context
import androidx.core.content.ContextCompat
import com.alamkanak.weekview.WeekViewDisplayable
import com.android.thongbaogdu.R
import com.android.thongbaogdu.data.model.Account
import com.android.thongbaogdu.data.model.Event
import com.android.thongbaogdu.services.AccountServices
import com.android.thongbaogdu.services.EmployeeServices
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class EventsDatabase(context: Context, username: String) {
    private val color1 = ContextCompat.getColor(context, R.color.event_color_01)
    private val color2 = ContextCompat.getColor(context, R.color.event_color_02)
    private val color3 = ContextCompat.getColor(context, R.color.event_color_03)
    private val color4 = ContextCompat.getColor(context, R.color.event_color_04)
    private val employeeServices =  EmployeeServices().getEmployByUserName(username);

    fun getEventsInRange(
        //startDate: Calendar,
        //endDate: Calendar
    ): List<WeekViewDisplayable<Event>> {

        //val year = startDate.get(Calendar.YEAR)
        //val month = startDate.get(Calendar.MONTH)
        //val monthEnd = endDate.get(Calendar.MONTH)
        //val yearEnd = endDate.get(Calendar.YEAR)
        //val idOffset = year + 10L * month
        val events = mutableListOf<WeekViewDisplayable<Event>>()
        var i  = 0;
        println("sss---------------------------------------------------------------------------------------->" + employeeServices.account.userName)
            for(schedule in employeeServices.schedules)
            {
                i += 1;
                var color = color1;
                if(schedule.color == 1)
                {
                    color = color1;
                }
                else if(schedule.color == 2)
                {
                    color = color2;
                }
                else if(schedule.color == 3)
                {
                    color = color3;
                }
                else
                {
                    color = color4;
                }
                events += newEvent(
                        id =  schedule.id,
                        year = schedule.year,
                        month = schedule.month - 1,
                        dayOfMonth = schedule.day_of_month,
                        hour = schedule.hour,
                        minute = schedule.minute,
                        duration = schedule.duration,
                        isAllDay = schedule.isAllDay,
                        isCanceled = schedule.isCanceled,
                        color = color,
                        content = schedule.content
                )
                println( i.toString() + ":::year: "+schedule.year + "=== month: " + schedule.month + "===> day: " + schedule.day_of_month);
            }


        // Add multi-day event
//        events += newEvent(
//            id = idOffset + 2,
//            year = year,
//            month = month,
//            dayOfMonth = 27,
//            hour = 20,
//            minute = 0,
//            duration = 5 * 60,
//            color = color4
//        )
//
//        events += newEvent(
//            id = idOffset + 3,
//            year = year,
//            month = month,
//            dayOfMonth = 28,
//            hour = 9,
//            minute = 30,
//            duration = 60,
//            color = color4,
//            isCanceled = true
//        )
//
//        events += newEvent(
//            id = idOffset + 3,
//            year = year,
//            month = month,
//            dayOfMonth = 28,
//            hour = 9,
//            minute = 30,
//            duration = 60,
//            color = color2
//        )
//
//        events += newEvent(
//            id = idOffset + 4,
//            year = year,
//            month = month,
//            dayOfMonth = 28,
//            hour = 10,
//            minute = 30,
//            duration = 45,
//            color = color3
//        )
//
//        events += newEvent(
//            id = idOffset + 5,
//            year = year,
//            month = month,
//            dayOfMonth = 28,
//            hour = 12,
//            minute = 30,
//            duration = 2 * 60,
//            color = color2
//        )
//
//        events += newEvent(
//            id = idOffset + 6,
//            year = year,
//            month = month,
//            dayOfMonth = 17,
//            hour = 11,
//            minute = 0,
//            duration = 4 * 60,
//            color = color3
//        )
//
//        events += newEvent(
//            id = idOffset + 7,
//            year = year,
//            month = month,
//            dayOfMonth = 15,
//            hour = 3,
//            minute = 0,
//            duration = 3 * 60,
//            color = color4,
//            isCanceled = true
//        )
//
//        events += newEvent(
//            id = idOffset + 8,
//            year = year,
//            month = month,
//            dayOfMonth = 1,
//            hour = 9,
//            minute = 0,
//            duration = 3 * 60,
//            color = color1
//        )
//
//        events += newEvent(
//            id = idOffset + 9,
//            year = year,
//            month = month,
//            dayOfMonth = startDate.getActualMaximum(Calendar.DAY_OF_MONTH),
//            hour = 15,
//            minute = 0,
//            duration = 3 * 60,
//            color = color2
//        )
//
//        // All-day event
//        events += newEvent(
//            id = idOffset + 10,
//            year = year,
//            month = month,
//            dayOfMonth = 28,
//            hour = 0,
//            minute = 0,
//            duration = 24 * 60,
//            isAllDay = true,
//            color = color4
//        )
//
//        // All-day event
//        events += newEvent(
//            id = idOffset + 11,
//            year = year,
//            month = month,
//            dayOfMonth = 28,
//            hour = 0,
//            minute = 0,
//            duration = 24 * 60,
//            isAllDay = true,
//            color = color2
//        )
//
//        // All-day event until 00:00 next day
//        events += newEvent(
//            id = idOffset + 12,
//            year = year,
//            month = month,
//            dayOfMonth = 14,
//            hour = 0,
//            minute = 0,
//            duration = 10 * 60,
//            isAllDay = true,
//            color = color4
//        )

        return events
    }

    private fun newEvent(
        id: Long,
        year: Int,
        month: Int,
        dayOfMonth: Int,
        hour: Int,
        minute: Int,
        duration: Int,
        color: Int,
        isAllDay: Boolean = false,
        isCanceled: Boolean = false,
        content: String
    ): Event {
        val startTime = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val endTime = startTime.clone() as Calendar
        endTime.add(Calendar.MINUTE, duration)

        val title = buildEventTitle(startTime)
        return Event(id, title, startTime, endTime, content, color, isAllDay, isCanceled)
    }

    private fun buildEventTitle(time: Calendar): String {
        val sdf = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM)
        val formattedDate = sdf.format(time.time)
        val hour = time.get(Calendar.HOUR_OF_DAY)
        val minute = time.get(Calendar.MINUTE)
        return String.format("🦄 Event of %02d:%02d %s", hour, minute, formattedDate)
    }
}
