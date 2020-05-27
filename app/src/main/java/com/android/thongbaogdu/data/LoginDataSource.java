package com.android.thongbaogdu.data;

import android.content.SharedPreferences;

import com.android.thongbaogdu.data.model.Account;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.data.model.LoggedInUser;
import com.android.thongbaogdu.services.AccountServices;
import com.android.thongbaogdu.services.EmployeeServices;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private EmployeeServices employeeServices = new EmployeeServices();
    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication

            for(Employee employee: employeeServices.getAllEmployee())
            {
                System.out.println(employee.getAccount().getUserName());
                if(username.compareTo(employee.getAccount().getUserName().trim()) == 0)
                {
                    if(password.compareTo(employee.getAccount().getPassword().trim()) == 0)
                    {
                        LoggedInUser fakeUser =
                                new LoggedInUser(
                                        java.util.UUID.randomUUID().toString(),
                                        employee.getAccount().getUserName());
                        return new Result.Success<>(fakeUser);
                    }
                }
            }

            return new Result.Error(new IOException("Sai mật khẩu hoặc tài khoản"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
