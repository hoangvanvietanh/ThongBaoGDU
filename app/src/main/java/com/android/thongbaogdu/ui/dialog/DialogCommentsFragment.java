package com.android.thongbaogdu.ui.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.android.thongbaogdu.R;

public class DialogCommentsFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_comments, null);
        EditText subjectComment = view.findViewById(R.id.editTextSubjectComment);
        EditText contentComment= view.findViewById(R.id.editTextContentComment);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        AlertDialog dialog =  builder.setView(view)
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
                }).create();
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorGduBlue);
        //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorGduBlue);


        return dialog;
    }
}
