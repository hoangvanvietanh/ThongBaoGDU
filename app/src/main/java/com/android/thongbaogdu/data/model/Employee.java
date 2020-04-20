package com.android.thongbaogdu.data.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String _id;
    private String EmployeeId;
    private String FullName;
    private String Email;
    private String BirthDate;
    private String PhoneNumber;
    private String PositionName;
    private String DepartmentName;
    private String Image;
    private String Address;
    private Account Account;
    private ArrayList<Schedule> Schedules;

    public Employee()
    {

    }

    public Employee(String _id, String employeeId, String fullName, String email, String birthDate, String phoneNumber, String positionName, String departmentName, String image, String address, com.android.thongbaogdu.data.model.Account account, ArrayList<Schedule> schedules) {
        this._id = _id;
        EmployeeId = employeeId;
        FullName = fullName;
        Email = email;
        BirthDate = birthDate;
        PhoneNumber = phoneNumber;
        PositionName = positionName;
        DepartmentName = departmentName;
        Image = image;
        Address = address;
        Account = account;
        Schedules = schedules;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public com.android.thongbaogdu.data.model.Account getAccount() {
        return Account;
    }

    public void setAccount(com.android.thongbaogdu.data.model.Account account) {
        Account = account;
    }

    public ArrayList<Schedule> getSchedules() {
        return Schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        Schedules = schedules;
    }
}
