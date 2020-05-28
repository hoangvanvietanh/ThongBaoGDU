package com.android.thongbaogdu.dao;

import android.content.Context;

import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.Employee;

import java.util.ArrayList;
import java.util.List;

public interface IAccountDao {
    Employee login(String userName, String password);

}
