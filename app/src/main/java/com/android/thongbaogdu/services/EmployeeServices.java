package com.android.thongbaogdu.services;

import com.android.thongbaogdu.dao.IEmployeeDao;
import com.android.thongbaogdu.daoimpl.EmployeeDaoImpl;
import com.android.thongbaogdu.data.model.Employee;

import java.util.ArrayList;

public class EmployeeServices {
    IEmployeeDao employeeDao = new EmployeeDaoImpl();

    public ArrayList<Employee> getAllEmployee()
    {
        return employeeDao.getAllEmployee();
    }
}