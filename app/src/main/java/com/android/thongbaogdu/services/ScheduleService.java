package com.android.thongbaogdu.services;

import com.android.thongbaogdu.dao.IScheduleDao;
import com.android.thongbaogdu.daoimpl.ScheduleDaoImpl;
import com.android.thongbaogdu.data.model.Schedule;

public class ScheduleService {
    IScheduleDao scheduleDao = new ScheduleDaoImpl();

    public void addSchedule(Schedule schedule, String username)
    {
        scheduleDao.addSchedule(schedule, username);
    }
}
