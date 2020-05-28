package com.android.thongbaogdu.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.model.Employee;

public class DialogShowInfoFragment extends DialogFragment {

    private Employee _employee = new Employee();

    public DialogShowInfoFragment (Employee employee)
    {
        this._employee = employee;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_show_info, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...

                    }
                });
//                .setNegativeButton(R.string.app_name, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
        EditText fullName = view.findViewById(R.id.editTextFullName);
        EditText dateOfBirth= view.findViewById(R.id.editTextDateOfBirth);
        EditText address= view.findViewById(R.id.editTextAddress);
        EditText email= view.findViewById(R.id.editTextEmail);
        EditText phoneNumber= view.findViewById(R.id.editTextPhoneNumber);
        EditText department= view.findViewById(R.id.editTextDepartment);
        EditText position= view.findViewById(R.id.editTextPosition);
        EditText role= view.findViewById(R.id.editTextRole);

        fullName.setEnabled(false);
        dateOfBirth.setEnabled(false);
        address.setEnabled(false);
        email.setEnabled(false);
        phoneNumber.setEnabled(false);
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
        return builder.create();
    }

//    public void setEnable(Boolean status)
//    {
//        EditText fullName =
//    }
}

