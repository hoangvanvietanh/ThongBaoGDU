package com.android.thongbaogdu.daoimpl;

import com.android.thongbaogdu.dao.IEmployeeDao;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.Employee;

import java.util.ArrayList;

public class EmployeeDaoImpl implements IEmployeeDao {

    private DataApi dataApi = new DataApi();

    @Override
    public ArrayList<Employee> getAllEmployee() {
        return dataApi.getAllEmployee();
    }
}
