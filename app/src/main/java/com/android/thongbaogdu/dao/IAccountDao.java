package com.android.thongbaogdu.dao;

import android.content.Context;

import com.android.thongbaogdu.data.model.Account;

import java.util.ArrayList;
import java.util.List;

public interface IAccountDao {
    ArrayList<Account> getAllAccount();
}
