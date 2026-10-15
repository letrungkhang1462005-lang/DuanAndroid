package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

public class IntroActivity extends AppCompatActivity {

    private TextView btnNextArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Ánh xạ đúng ID 'btnNextArrow' từ file XML của bạn
        btnNextArrow = findViewById(R.id.btnNextArrow);

        // Xử lý sự kiện bấm vào mũi tên để chuyển sang trang nhập hồ sơ
        btnNextArrow.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}