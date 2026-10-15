package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcomeName, tvMoreExercises;
    private TextView tvDashboardWeight, tvDashboardBmi, tvDashboardCalories;
    private ImageView btnHeaderSettings, imgUserAvatar;
    private FrameLayout layoutMealSuggestion;
    private LinearLayout cardWater, cardBmi, cardHeartRate;

    private LinearLayout menuHome, menuHistory, menuAiCoach, menuSettings;
    private String currentWeight = "68";
    private String currentHeight = "168";
    private String userGoal = "Duy trì cân nặng & Sức khỏe";

    // Khai báo Launcher để nhận kết quả số Calories trả về từ ExerciseListActivity
    private final ActivityResultLauncher<Intent> exerciseLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    int totalCalories = result.getData().getIntExtra("TOTAL_CALORIES", 0);
                    if (tvDashboardCalories != null) {
                        tvDashboardCalories.setText("🔥 " + totalCalories);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 1. Ánh xạ View
        imgUserAvatar = findViewById(R.id.imgUserAvatar);
        tvWelcomeName = findViewById(R.id.tvWelcomeName);
        btnHeaderSettings = findViewById(R.id.btnHeaderSettings);
        layoutMealSuggestion = findViewById(R.id.layoutMealSuggestion);
        tvMoreExercises = findViewById(R.id.tvMoreExercises);

        cardHeartRate = findViewById(R.id.cardHeartRate);
        cardWater = findViewById(R.id.cardWater);
        cardBmi = findViewById(R.id.cardBmi);
        tvDashboardWeight = findViewById(R.id.tvDashboardWeight);
        tvDashboardBmi = findViewById(R.id.tvDashboardBmi);
        tvDashboardCalories = findViewById(R.id.tvDashboardCalories);

        menuHome = findViewById(R.id.menuHome);
        menuHistory = findViewById(R.id.menuHistory);
        menuAiCoach = findViewById(R.id.menuAiCoach);
        menuSettings = findViewById(R.id.menuSettings);

        // 2. Nhận dữ liệu Profile truyền sang từ Intent
        String fullName = getIntent().getStringExtra("USER_NAME");
        String weightStr = getIntent().getStringExtra("USER_WEIGHT");
        String heightStr = getIntent().getStringExtra("USER_HEIGHT");
        String goalStr = getIntent().getStringExtra("USER_GOAL");
        String genderStr = getIntent().getStringExtra("USER_GENDER");

        // Đọc thêm từ SharedPreferences nếu Intent không truyền sang
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        if (genderStr == null || genderStr.isEmpty()) {
            genderStr = prefs.getString("GENDER", "Nam");
        } else {
            prefs.edit().putString("GENDER", genderStr).apply();
        }

        // Cập nhật ảnh đại diện tương ứng theo giới tính
        updateAvatarByGender(genderStr);

        if (fullName != null && !fullName.isEmpty()) {
            tvWelcomeName.setText("Xin chào, " + fullName + " 👋");
        }

        if (goalStr != null && !goalStr.isEmpty()) {
            userGoal = goalStr;
        }

        if (weightStr != null && !weightStr.isEmpty()) {
            currentWeight = weightStr;
            if (tvDashboardWeight != null) {
                tvDashboardWeight.setText(weightStr + " kg");
            }
        }

        if (heightStr != null && !heightStr.isEmpty()) {
            currentHeight = heightStr;
        }

        // 3. Tính toán lại BMI hiển thị trên trang chủ (Đã xử lý an toàn chống văng app)
        try {
            if (currentWeight != null && currentHeight != null && !currentWeight.isEmpty() && !currentHeight.isEmpty()) {
                String cleanWeight = currentWeight.trim().replace(",", ".");
                String cleanHeight = currentHeight.trim().replace(",", ".");

                double weight = Double.parseDouble(cleanWeight);
                double heightM = Double.parseDouble(cleanHeight) / 100.0;

                if (heightM > 0 && tvDashboardBmi != null) {
                    double bmi = weight / (heightM * heightM);
                    tvDashboardBmi.setText(String.format("%.1f", bmi));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (tvDashboardBmi != null) {
                tvDashboardBmi.setText("23.7");
            }
        }

        // 4. Khởi tạo mặc định Calories ban đầu là 0
        if (tvDashboardCalories != null) {
            tvDashboardCalories.setText("🔥 0");
        }

        // 5. Sự kiện bấm Thẻ Nhịp tim -> Mở HeartRateDetailActivity
        if (cardHeartRate != null) {
            cardHeartRate.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, HeartRateDetailActivity.class);
                startActivity(intent);
            });
        }

        // 6. Sự kiện bấm Thẻ Nước -> Mở WaterReminderActivity
        if (cardWater != null) {
            cardWater.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, WaterReminderActivity.class);
                intent.putExtra("USER_WEIGHT", currentWeight);
                startActivity(intent);
            });
        }

        // 7. Sự kiện bấm vào ô BMI -> Mở BmiCalculatorActivity
        if (cardBmi != null) {
            cardBmi.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, BmiCalculatorActivity.class);
                intent.putExtra("USER_HEIGHT", currentHeight);
                intent.putExtra("USER_WEIGHT", currentWeight);
                startActivity(intent);
            });
        }

        // 8. Sự kiện bấm Gợi ý chế độ ăn uống -> Mở MealSuggestionActivity
        if (layoutMealSuggestion != null) {
            layoutMealSuggestion.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, MealSuggestionActivity.class);
                intent.putExtra("USER_GOAL", userGoal);
                startActivity(intent);
            });
        }

        // 9. Sự kiện bấm "Xem thêm các bài tập gợi ý..." -> Mở ExerciseListActivity qua Launcher
        if (tvMoreExercises != null) {
            tvMoreExercises.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, ExerciseListActivity.class);
                exerciseLauncher.launch(intent);
            });
        }

        // 10. Sự kiện bấm nút AI Coach ở thanh menu dưới đáy -> Mở AiCoachActivity (tích hợp Gemini AI)
        if (menuAiCoach != null) {
            menuAiCoach.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, AiCoachActivity.class);
                startActivity(intent);
            });
        }

        // 11. Các sự kiện điều hướng menu dưới đáy khác & cài đặt
        if (menuHistory != null) {
            menuHistory.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, HistoryActivity.class)));
        }

        if (menuSettings != null || btnHeaderSettings != null) {
            android.view.View.OnClickListener settingsListener = v -> startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
            if (menuSettings != null) menuSettings.setOnClickListener(settingsListener);
            if (btnHeaderSettings != null) btnHeaderSettings.setOnClickListener(settingsListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String genderStr = prefs.getString("GENDER", "Nam");
        updateAvatarByGender(genderStr);
    }

    private void updateAvatarByGender(String gender) {
        if (imgUserAvatar != null) {
            if ("Nam".equalsIgnoreCase(gender)) {
                imgUserAvatar.setImageResource(R.drawable.img_changtrai);
            } else {
                imgUserAvatar.setImageResource(R.drawable.img_girl);
            }
        }
    }
}