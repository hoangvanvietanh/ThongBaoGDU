package com.android.thongbaogdu.daoimpl;

import com.android.thongbaogdu.dao.IEmployeeDao;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.Employee;

import java.util.ArrayList;

public class EmployeeDaoImpl implements IEmployeeDao {

    private DataApi dataApi = new DataApi();

    @Override
    public ArrayList<Employee> getAllEmployee() {
        System.out.println("Dao impl ------------------------>" + dataApi.getAllEmployee().size());
        return dataApi.getAllEmployee();
    }

    @Override
    public Employee getEmployeeByUserName(String username) {
        Employee employee = new Employee();
        for (Employee emp:  dataApi.getAllEmployee())
        {
            if(emp.getAccount().getUserName().trim().equals(username.trim()))
            {
                employee = emp;
            }
        }
        return employee;
    }
}
