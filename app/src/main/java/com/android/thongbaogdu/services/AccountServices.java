package com.android.thongbaogdu.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.android.thongbaogdu.dao.IAccountDao;
import com.android.thongbaogdu.daoimpl.AccountDaoImpl;
import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.DataApi;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.ui.login.LoginActivity;
import com.android.thongbaogdu.util.Ultil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AccountServices {
    private  DataApi dataApi = new DataApi();
    private IAccountDao accountDao = new AccountDaoImpl();

    public Employee login(String userName, String password)
    {
        Context context = LoginActivity.getAppContext();

        SharedPreferences sp= context.getSharedPreferences("user_data", context.MODE_PRIVATE);
        String data=sp.getString("data", null);
        if(data != null && isNetworkConnected() == false)
        {
            return Ultil.convertJSONtoEmployee(data);
        }
        return accountDao.login(userName,password);
    }

//    public ArrayList<Account> getAllAccount()
//    {
//        return accountDao.getAllAccount();
//    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)LoginActivity.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
