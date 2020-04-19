package com.android.thongbaogdu.data.model;

import java.util.List;

public class EmployeeAndSchedule {
    private String username;
    private Schedule schedules;

    public EmployeeAndSchedule(String username, Schedule schedules) {
        this.username = username;
        this.schedules = schedules;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Schedule getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedule schedules) {
        this.schedules = schedules;
    }
}
