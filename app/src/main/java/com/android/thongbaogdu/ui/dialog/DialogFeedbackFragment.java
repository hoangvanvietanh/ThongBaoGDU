package com.android.thongbaogdu.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.android.thongbaogdu.R;
import com.android.thongbaogdu.data.model.Feedback;
import com.android.thongbaogdu.services.FeedbackServices;

public class DialogFeedbackFragment extends DialogFragment {
    EditText subjectFeedback, contentFeedback;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_feedback, null);
         subjectFeedback = view.findViewById(R.id.editTextSubjectFeedback);
         contentFeedback= view.findViewById(R.id.editTextContentFeedback);
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
                        if(subjectFeedback.getText().toString().trim().equals("") || contentFeedback.getText().toString().trim().equals(""))
                        {
                            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ nội dung góp ý !!!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            FeedbackServices feedbackServices = new FeedbackServices();
                            Feedback feedback = new Feedback();
                            SharedPreferences sp1=getContext().getSharedPreferences("Login", getContext().MODE_PRIVATE);
                            String userName=sp1.getString("UserName", null);
                            feedback.setEmail(userName);
                            feedback.setSubject(subjectFeedback.getText().toString());
                            feedback.setContent(contentFeedback.getText().toString());
                            feedbackServices.sendFeedback(feedback);

                            Toast.makeText(getContext(), "Cám ơn bạn đã góp ý ^^", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).create();
        //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorGduBlue);
        //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorGduBlue);


        return dialog;
    }
}
