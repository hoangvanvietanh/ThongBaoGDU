package com.android.thongbaogdu.data.model;

public class Schedule {
    private long id;
    private int year;
    private int month;
    private int day_of_month;
    private int hour;
    private int minute;
    private int duration;
    private int color;
    private boolean isAllDay;
    private boolean isCanceled;
    private String content;

    public Schedule(int id, int year, int month, int day_of_month, int hour, int minute, int duration, int color, boolean isAllDay, boolean isCanceled, String content) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day_of_month = day_of_month;
        this.hour = hour;
        this.minute = minute;
        this.duration = duration;
        this.color = color;
        this.isAllDay = isAllDay;
        this.isCanceled = isCanceled;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(int day_of_month) {
        this.day_of_month = day_of_month;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isAllDay() {
        return isAllDay;
    }

    public void setAllDay(boolean allDay) {
        isAllDay = allDay;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
