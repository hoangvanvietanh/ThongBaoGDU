package com.android.thongbaogdu.dao;

import com.android.thongbaogdu.data.model.Schedule;

public interface IScheduleDao {
    void addSchedule(Schedule schedule, String username);
}
