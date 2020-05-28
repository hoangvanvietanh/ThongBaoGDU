package com.android.thongbaogdu.services;

import android.content.Context;

import com.android.thongbaogdu.dao.IAccountDao;
import com.android.thongbaogdu.daoimpl.AccountDaoImpl;
import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AccountServices {
    private  DataApi dataApi = new DataApi();
    private IAccountDao accountDao = new AccountDaoImpl();

    public Employee login(String userName, String password)
    {
        return accountDao.login(userName,password);
    }

//    public ArrayList<Account> getAllAccount()
//    {
//        return accountDao.getAllAccount();
//    }
}
