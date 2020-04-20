package com.android.thongbaogdu.dao;

import com.android.thongbaogdu.data.model.Employee;

import java.util.ArrayList;

public interface IEmployeeDao {
    ArrayList<Employee> getAllEmployee();
    Employee getEmployeeByUserName(String username);
}
