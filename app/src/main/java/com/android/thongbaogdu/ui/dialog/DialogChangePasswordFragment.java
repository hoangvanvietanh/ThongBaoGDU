package com.android.thongbaogdu.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.services.EmployeeServices;
import com.android.thongbaogdu.ui.login.LoginActivity;

public class DialogChangePasswordFragment extends DialogFragment {
    private Employee _employee;
    public DialogChangePasswordFragment (Employee employee)
    {
        this._employee = employee;
    }
    private EditText passwordCurrent, newPassword, newPasswordAgain;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        passwordCurrent = view.findViewById(R.id.editTextPasswordCurrent);
        newPassword= view.findViewById(R.id.editTextNewPassword);
        newPasswordAgain= view.findViewById(R.id.editTextNewPasswordAgain);


        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...

                    }
                })
                .setNegativeButton(R.string.btn_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EmployeeServices employeeServices = new EmployeeServices();
                        Employee employeeUpdate;
                        if(newPasswordAgain.getText().toString().trim().equals("") || newPassword.getText().toString().trim().equals("") || passwordCurrent.getText().toString().trim().equals(""))
                        {
                            Toast.makeText(getContext(), "Không được để trống !!!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(_employee.getAccount().getPassword().trim().equals(passwordCurrent.getText().toString().trim()))
                            {
                                if(newPassword.getText().toString().trim().equals(newPasswordAgain.getText().toString().trim()))
                                {
                                    employeeUpdate = _employee;
                                    employeeUpdate.getAccount().setPassword(newPassword.getText().toString());
                                    employeeServices.updateEmployee(employeeUpdate);

                                    Toast.makeText(getActivity(), "Đổi mật khẩu thành công ^^", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sp=getActivity().getSharedPreferences("Login", getActivity().MODE_PRIVATE);
                                    SharedPreferences.Editor Ed=sp.edit();
                                    Ed.putString("UserName",null);
                                    Ed.putString("Password",null);
                                    Ed.commit();

                                    SharedPreferences sp1=getActivity().getSharedPreferences("user_data", getActivity().MODE_PRIVATE);
                                    SharedPreferences.Editor Ed1 =sp1.edit();
                                    Ed1.putString("data",null);
                                    Ed.commit();

                                    Intent intent = new Intent(getContext(), LoginActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Hai mật khẩu không trùng khớp !!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Mật khẩu cũ không trùng !!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });


        return builder.create();
    }
}
