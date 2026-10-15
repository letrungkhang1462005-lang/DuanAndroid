package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

import java.util.Calendar;

public class HistoryActivity extends AppCompatActivity {

    private TextView tvTotalWorkoutCount, tvTotalTime, tvTotalCalories;
    private TextView tvAct1Title, tvAct1Sub;
    private TextView tvSeeAllActivity, tvSeeAllMeals;
    private TextView btnFilter7Days, btnFilterMonth, btnFilterCustom;
    private LinearLayout menuHome, menuHistory, menuAiCoach, menuSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 1. Ánh xạ các View
        tvTotalWorkoutCount = findViewById(R.id.tvTotalWorkoutCount);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvTotalCalories = findViewById(R.id.tvTotalCalories);

        tvAct1Title = findViewById(R.id.tvAct1Title);
        tvAct1Sub = findViewById(R.id.tvAct1Sub);
        tvSeeAllActivity = findViewById(R.id.tvSeeAllActivity);
        tvSeeAllMeals = findViewById(R.id.tvSeeAllMeals);

        btnFilter7Days = findViewById(R.id.btnFilter7Days);
        btnFilterMonth = findViewById(R.id.btnFilterMonth);
        btnFilterCustom = findViewById(R.id.btnFilterCustom);

        menuHome = findViewById(R.id.menuHome);
        menuHistory = findViewById(R.id.menuHistory);
        menuAiCoach = findViewById(R.id.menuAiCoach);
        menuSettings = findViewById(R.id.menuSettings);

        // 2. Hiển thị dữ liệu mặc định (7 ngày qua)
        loadDataForFilter("7DAYS");

        // 3. Xử lý sự kiện chọn bộ lọc thời gian
        btnFilter7Days.setOnClickListener(v -> {
            setActiveFilter(btnFilter7Days);
            loadDataForFilter("7DAYS");
        });

        btnFilterMonth.setOnClickListener(v -> {
            setActiveFilter(btnFilterMonth);
            loadDataForFilter("MONTH");
        });

        btnFilterCustom.setOnClickListener(v -> {
            setActiveFilter(btnFilterCustom);
            showDatePickerDialog();
        });

        // 4. Sự kiện bấm nút "Xem tất cả" Hoạt động & Ăn uống
        if (tvSeeAllActivity != null) {
            tvSeeAllActivity.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, ActivityListActivity.class)));
        }

        if (tvSeeAllMeals != null) {
            tvSeeAllMeals.setOnClickListener(v -> startActivity(new Intent(HistoryActivity.this, MealListActivity.class)));
        }

        // 5. Điều hướng menu dưới đáy
        if (menuHome != null) {
            menuHome.setOnClickListener(v -> {
                startActivity(new Intent(HistoryActivity.this, DashboardActivity.class));
                finish();
            });
        }

        if (menuSettings != null) {
            menuSettings.setOnClickListener(v -> {
                startActivity(new Intent(HistoryActivity.this, SettingsActivity.class));
                finish();
            });
        }
    }

    // Hàm đổi màu nền và kiểu chữ cho nút bộ lọc đang được chọn
    private void setActiveFilter(TextView selectedButton) {
        // Reset tất cả về trạng thái chưa chọn
        btnFilter7Days.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnFilter7Days.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnFilter7Days.setTypeface(null, android.graphics.Typeface.NORMAL);

        btnFilterMonth.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnFilterMonth.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnFilterMonth.setTypeface(null, android.graphics.Typeface.NORMAL);

        btnFilterCustom.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btnFilterCustom.setTextColor(getResources().getColor(android.R.color.darker_gray));
        btnFilterCustom.setTypeface(null, android.graphics.Typeface.NORMAL);

        // Áp dụng style cho nút được bấm
        selectedButton.setBackgroundResource(R.drawable.bg_gender_selected);
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
        selectedButton.setTypeface(null, android.graphics.Typeface.BOLD);
    }

    // Hàm thay đổi số liệu thống kê tùy theo loại bộ lọc
    private void loadDataForFilter(String filterType) {
        SharedPreferences prefs = getSharedPreferences("HistoryPrefs", MODE_PRIVATE);
        int savedCal = prefs.getInt("LAST_CALORIES", 410);
        int savedDur = prefs.getInt("LAST_DURATION", 60);

        if ("7DAYS".equals(filterType)) {
            if (tvTotalWorkoutCount != null) tvTotalWorkoutCount.setText("5");
            if (tvTotalTime != null) tvTotalTime.setText(String.valueOf(180 + savedDur));
            if (tvTotalCalories != null) tvTotalCalories.setText("1." + (800 + savedCal));
        } else if ("MONTH".equals(filterType)) {
            // Số liệu cho cả tháng sẽ cao hơn
            if (tvTotalWorkoutCount != null) tvTotalWorkoutCount.setText("22");
            if (tvTotalTime != null) tvTotalTime.setText("980");
            if (tvTotalCalories != null) tvTotalCalories.setText("6.450");
        }
    }

    // Hiển thị bảng chọn ngày khi bấm "Tùy chọn"
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            Toast.makeText(this, "Đã chọn ngày: " + selectedDate, Toast.LENGTH_SHORT).show();

            // Cập nhật số liệu tượng trưng cho ngày được chọn tùy chỉnh
            if (tvTotalWorkoutCount != null) tvTotalWorkoutCount.setText("1");
            if (tvTotalTime != null) tvTotalTime.setText("45");
            if (tvTotalCalories != null) tvTotalCalories.setText("350");
        }, year, month, day);

        datePickerDialog.show();
    }
}