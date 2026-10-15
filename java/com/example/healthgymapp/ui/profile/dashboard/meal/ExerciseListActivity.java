package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

public class ExerciseListActivity extends AppCompatActivity {

    private int accumulatedCalories = 0; // Biến tích lũy tổng calo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        // Xử lý nút back hiện đại
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                returnCaloriesToDashboard();
            }
        });

        // 1. Chạy bộ (+300 kcal)
        setupExerciseClick(R.id.cardRunning, 300, "Chạy bộ");
        // 2. Boxing (+450 kcal)
        setupExerciseClick(R.id.cardBoxing, 450, "Boxing");
        // 3. Bơi lội (+380 kcal)
        setupExerciseClick(R.id.cardSwimming, 380, "Bơi lội");
        // 4. Đạp xe (+320 kcal)
        setupExerciseClick(R.id.cardCycling, 320, "Đạp xe ngoài trời");
        // 5. Nhảy dây (+280 kcal)
        setupExerciseClick(R.id.cardJumpRope, 280, "Nhảy dây tốc độ");
        // 6. Tập Gym Ngực (+410 kcal)
        setupExerciseClick(R.id.cardGymChest, 410, "Tập Gym: Ngực & Tay sau");
        // 7. Tập Gym Lưng Xô (+390 kcal)
        setupExerciseClick(R.id.cardGymBack, 390, "Tập Gym: Lưng Xô & Tay trước");
        // 8. Tập Chân Đùi (+480 kcal)
        setupExerciseClick(R.id.cardGymLegs, 480, "Tập Gym: Chân & Đùi");
        // 9. Yoga (+150 kcal)
        setupExerciseClick(R.id.cardYoga, 150, "Yoga thư giãn");
        // 10. Đi bộ (+220 kcal)
        setupExerciseClick(R.id.cardWalking, 220, "Đi bộ nhanh");

        // Nút Hoàn tất
        Button btnFinish = findViewById(R.id.btnFinishExercises);
        if (btnFinish != null) {
            btnFinish.setOnClickListener(v -> returnCaloriesToDashboard());
        }
    }

    private void setupExerciseClick(int viewId, int calories, String exerciseName) {
        LinearLayout card = findViewById(viewId);
        if (card != null) {
            card.setOnClickListener(v -> {
                accumulatedCalories += calories;
                Toast.makeText(this, "Đã chọn " + exerciseName + " (+" + calories + " kcal)", Toast.LENGTH_SHORT).show();
            });
        }
    }

    // Dùng setResult để gửi ngược tổng calories về DashboardActivity mà không làm mất dữ liệu trang chủ
    private void returnCaloriesToDashboard() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("TOTAL_CALORIES", accumulatedCalories);
        setResult(RESULT_OK, resultIntent);
        finish(); // Đóng màn hình danh sách, tự động quay lại màn hình Dashboard trước đó
    }
}