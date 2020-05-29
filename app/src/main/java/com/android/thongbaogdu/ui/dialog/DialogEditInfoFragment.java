package com.android.thongbaogdu.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.model.Employee;
import com.android.thongbaogdu.services.EmployeeServices;

public class DialogEditInfoFragment extends DialogFragment {

    private Employee _employee;
    private EditText fullName, dateOfBirth,address,email,phoneNumber,department,position,role;

    public DialogEditInfoFragment (Employee employee)
    {
        this._employee = employee;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_info, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
         fullName = view.findViewById(R.id.editTextFullName);
         dateOfBirth= view.findViewById(R.id.editTextDateOfBirth);
         address= view.findViewById(R.id.editTextAddress);
         email= view.findViewById(R.id.editTextEmail);
         phoneNumber= view.findViewById(R.id.editTextPhoneNumber);
         department= view.findViewById(R.id.editTextDepartment);
         position= view.findViewById(R.id.editTextPosition);
         role= view.findViewById(R.id.editTextRole);

        department.setEnabled(false);
        position.setEnabled(false);
        role.setEnabled(false);

        fullName.setText(_employee.getFullName());
        dateOfBirth.setText(_employee.getBirthDate());
        address.setText(_employee.getAddress());
        email.setText(_employee.getEmail());
        phoneNumber.setText(_employee.getPhoneNumber());
        department.setText(_employee.getDepartmentName());
        position.setText(_employee.getPositionName());
        role.setText(_employee.getAccount().getRole());

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
                        employeeUpdate = _employee;
                        employeeUpdate.setAddress(address.getText().toString());
                        employeeUpdate.setFullName(fullName.getText().toString());
                        employeeUpdate.setBirthDate(dateOfBirth.getText().toString());
                        employeeUpdate.setEmail(email.getText().toString());
                        employeeUpdate.setPhoneNumber(phoneNumber.getText().toString());
                        employeeUpdate.setDepartmentName(department.getText().toString());
                        employeeUpdate.setPositionName(position.getText().toString());
                        employeeServices.updateEmployee(employeeUpdate);
                        Toast.makeText(getContext(), "Cập nhật thông tin thành công ^^", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }

//    public void setEnable(Boolean status)
//    {
//        EditText fullName =
//    }
}
