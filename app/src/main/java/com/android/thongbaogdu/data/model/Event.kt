package com.android.thongbaogdu.data.model

import android.graphics.Color
import com.alamkanak.weekview.WeekViewDisplayable
import com.alamkanak.weekview.WeekViewEvent
import com.android.thongbaogdu.R
import java.util.Calendar

class Event(
    val id: Long,
    val title: String,
    private val startTime: Calendar,
    private val endTime: Calendar,
     val location: String,
    val color: Int,
    val isAllDay: Boolean,
    val isCanceled: Boolean
) : WeekViewDisplayable<Event> {

    override fun toWeekViewEvent(): WeekViewEvent<Event> {
        val backgroundColor = if (!isCanceled) color else Color.WHITE
        val textColor = if (!isCanceled) Color.WHITE else color
        val borderWidthResId = if (!isCanceled) R.dimen.no_border_width else R.dimen.border_width

        val style = WeekViewEvent.Style.Builder()
            .setTextColor(textColor)
            .setBackgroundColor(backgroundColor)
            .setTextStrikeThrough(isCanceled)
            .setBorderWidthResource(borderWidthResId)
            .setBorderColor(color)
            .build()

        return WeekViewEvent.Builder<Event>(this)
            .setId(id)
            .setTitle(title)
            .setStartTime(startTime)
            .setEndTime(endTime)
            .setLocation(location)
            .setAllDay(isAllDay)
            .setStyle(style)
            .build()
    }
}
