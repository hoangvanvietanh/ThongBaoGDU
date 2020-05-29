package com.android.thongbaogdu.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.model.Employee;

public class DialogChangePasswordFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
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

                    }
                });
        EditText passwordCurrent = view.findViewById(R.id.editTextPasswordCurrent);
        EditText newPassword= view.findViewById(R.id.editTextNewPassword);
        EditText newPasswordAgain= view.findViewById(R.id.editTextNewPasswordAgain);

        return builder.create();
    }
}
