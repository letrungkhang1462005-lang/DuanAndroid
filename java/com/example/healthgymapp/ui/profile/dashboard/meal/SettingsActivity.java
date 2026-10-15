package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

public class SettingsActivity extends AppCompatActivity {

    private ImageView btnBackSettings;
    private TextView btnEditProfileHeader;
    private LinearLayout itemProfile, itemGoal, itemBodyIndex, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Ánh xạ View
        btnBackSettings = findViewById(R.id.btnBackSettings);
        btnEditProfileHeader = findViewById(R.id.btnEditProfileHeader);
        itemProfile = findViewById(R.id.itemProfile);
        itemGoal = findViewById(R.id.itemGoal);
        itemBodyIndex = findViewById(R.id.itemBodyIndex);
        btnLogout = findViewById(R.id.btnLogout);

        // Nút quay lại
        if (btnBackSettings != null) {
            btnBackSettings.setOnClickListener(v -> finish());
        }

        // Chỉnh sửa hồ sơ cá nhân
        android.view.View.OnClickListener openProfileListener = v -> {
            Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
            startActivity(intent);
        };

        if (btnEditProfileHeader != null) btnEditProfileHeader.setOnClickListener(openProfileListener);
        if (itemProfile != null) itemProfile.setOnClickListener(openProfileListener);
        if (itemGoal != null) itemGoal.setOnClickListener(openProfileListener);

        // Bấm vào Chỉ số cơ thể -> Mở BmiCalculatorActivity
        if (itemBodyIndex != null) {
            itemBodyIndex.setOnClickListener(v -> {
                Intent intent = new Intent(SettingsActivity.this, BmiCalculatorActivity.class);
                startActivity(intent);
            });
        }

        // Nút Đăng xuất
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(SettingsActivity.this, "Đã đăng xuất khỏi tài khoản!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
}