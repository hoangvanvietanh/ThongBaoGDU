package com.android.thongbaogdu.data.model;

public class Account {
    private String AccountId;
    private String UserName;
    private String Password;
    private String Role;

    public Account(String accountId, String userName, String password, String role) {
        AccountId = accountId;
        UserName = userName;
        Password = password;
        Role = role;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
