package com.android.thongbaogdu.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.android.thongbaogdu.MainActivity;
import com.android.thongbaogdu.R;
import com.android.thongbaogdu.services.AccountServices;
import com.android.thongbaogdu.ui.dialog.DialogEditInfoFragment;
import com.android.thongbaogdu.ui.dialog.DialogShowInfoFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorGduBlue));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thông tin tài khoản");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ImageButton btnViewInfo = findViewById(R.id.btnShowInfo);
        ImageButton btnEditUser = findViewById(R.id.btnEditUser);
        ImageButton btnChangePass = findViewById(R.id.btnChangePass);
        ImageButton btnComments = findViewById(R.id.btnComments);

        btnViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "view ok", Toast.LENGTH_SHORT).show();
                AccountServices accountServices = new AccountServices();
                SharedPreferences sp1=ProfileActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
                String userName=sp1.getString("UserName", null);
                String password = sp1.getString("Password", null);
                DialogShowInfoFragment noticeDialogFragment = new DialogShowInfoFragment(accountServices.login(userName,password));
                noticeDialogFragment.show(getSupportFragmentManager(), "Hiển thị nè");

            }
        });

        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "edit user ok", Toast.LENGTH_SHORT).show();
                AccountServices accountServices = new AccountServices();
                SharedPreferences sp1=ProfileActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
                String userName=sp1.getString("UserName", null);
                String password = sp1.getString("Password", null);
                DialogEditInfoFragment noticeDialogFragment = new DialogEditInfoFragment(accountServices.login(userName,password));
                noticeDialogFragment.show(getSupportFragmentManager(), "Hiển thị nè");
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "change pass ok", Toast.LENGTH_SHORT).show();
            }
        });

        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Comment ok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }


}
