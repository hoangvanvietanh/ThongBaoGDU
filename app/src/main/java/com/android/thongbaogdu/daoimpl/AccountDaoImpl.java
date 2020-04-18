package com.android.thongbaogdu.daoimpl;

import android.content.Context;

import com.android.thongbaogdu.config.Connection;
import com.android.thongbaogdu.dao.IAccountDao;
import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.DataApi;

import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements IAccountDao {

    private DataApi dataApi = new DataApi();

    @Override
    public ArrayList<Account> getAllAccount() {
        return null;
    }
}
