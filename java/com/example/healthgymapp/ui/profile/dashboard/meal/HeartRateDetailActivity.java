package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

public class HeartRateDetailActivity extends AppCompatActivity {

    private TextView btnBack, tabDay, tabWeek, tabMonth;
    private TextView tvCurrentBpm;
    private HeartRateChartView chartView;
    private LinearLayout menuHome, menuHistory, menuAiCoach, menuSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_detail);

        // 1. Ánh xạ các thành phần giao diện
        btnBack = findViewById(R.id.btnBack);
        tabDay = findViewById(R.id.tabDay);
        tabWeek = findViewById(R.id.tabWeek);
        tabMonth = findViewById(R.id.tabMonth);

        tvCurrentBpm = findViewById(R.id.tvCurrentBpm);
        chartView = findViewById(R.id.chartView);

        menuHome = findViewById(R.id.menuHome);
        menuHistory = findViewById(R.id.menuHistory);
        menuAiCoach = findViewById(R.id.menuAiCoach);
        menuSettings = findViewById(R.id.menuSettings);

        // 2. Nút quay lại (Mũi tên ←)
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 3. Sự kiện bấm chọn Tab Ngày, Tuần, Tháng (Tách bạch rõ ràng, không bị nhầm lẫn)
        if (tabDay != null) {
            tabDay.setOnClickListener(v -> selectTab(1));
        }
        if (tabWeek != null) {
            tabWeek.setOnClickListener(v -> selectTab(2));
        }
        if (tabMonth != null) {
            tabMonth.setOnClickListener(v -> selectTab(3));
        }

        // 4. Thanh điều hướng dưới đáy (Chỉ khi bấm vào biểu tượng Trang chủ mới gọi finish())
        if (menuHome != null) {
            menuHome.setOnClickListener(v -> finish());
        }
        if (menuHistory != null) {
            menuHistory.setOnClickListener(v -> {
                startActivity(new Intent(HeartRateDetailActivity.this, HistoryActivity.class));
            });
        }
        if (menuSettings != null) {
            menuSettings.setOnClickListener(v -> {
                startActivity(new Intent(HeartRateDetailActivity.this, SettingsActivity.class));
            });
        }
    }

    private void selectTab(int index) {
        // Reset giao diện tất cả các tab về trạng thái chưa chọn
        if (tabDay != null) {
            tabDay.setBackgroundResource(0);
            tabDay.setTextColor(Color.parseColor("#7A9187"));
        }
        if (tabWeek != null) {
            tabWeek.setBackgroundResource(0);
            tabWeek.setTextColor(Color.parseColor("#7A9187"));
        }
        if (tabMonth != null) {
            tabMonth.setBackgroundResource(0);
            tabMonth.setTextColor(Color.parseColor("#7A9187"));
        }

        // Kích hoạt tab được bấm
        if (index == 1) {
            if (tabDay != null) {
                tabDay.setBackgroundResource(R.drawable.bg_gender_selected);
                tabDay.setTextColor(Color.WHITE);
            }
            if (tvCurrentBpm != null) tvCurrentBpm.setText("72");
            if (chartView != null) {
                chartView.setData(new float[]{50f, 120f, 90f, 100f, 130f, 110f, 170f, 115f, 210f, 85f, 250f, 95f, 290f, 60f, 330f, 110f, 370f, 90f});
            }
        } else if (index == 2) {
            if (tabWeek != null) {
                tabWeek.setBackgroundResource(R.drawable.bg_gender_selected);
                tabWeek.setTextColor(Color.WHITE);
            }
            if (tvCurrentBpm != null) tvCurrentBpm.setText("75");
            if (chartView != null) {
                chartView.setData(new float[]{50f, 90f, 110f, 130f, 140f, 100f, 120f, 150f, 135f});
            }
        } else if (index == 3) {
            if (tabMonth != null) {
                tabMonth.setBackgroundResource(R.drawable.bg_gender_selected);
                tabMonth.setTextColor(Color.WHITE);
            }
            if (tvCurrentBpm != null) tvCurrentBpm.setText("78");
            if (chartView != null) {
                chartView.setData(new float[]{50f, 110f, 85f, 145f, 105f, 125f, 95f, 140f, 115f});
            }
        }
    }
}