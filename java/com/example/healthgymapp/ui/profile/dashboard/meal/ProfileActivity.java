package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

public class ProfileActivity extends AppCompatActivity {

    private EditText etFullName, etAge, etHeight, etWeight;
    private LinearLayout btnMale, btnFemale, btnSave;
    private Spinner spinnerActivityLevel, spinnerGoal;

    private boolean isMaleSelected = true; // Mặc định là Nam

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // 1. Ánh xạ các View
        etFullName = findViewById(R.id.etFullName);
        etAge = findViewById(R.id.etAge);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);
        spinnerActivityLevel = findViewById(R.id.spinnerActivityLevel);
        spinnerGoal = findViewById(R.id.spinnerGoal);
        btnSave = findViewById(R.id.btnSave);

        // 2. Thiết lập dữ liệu cho Spinner
        setupSpinners();

        // 3. Xử lý chọn giới tính
        btnMale.setOnClickListener(v -> {
            if (!isMaleSelected) {
                isMaleSelected = true;
                btnMale.setBackgroundResource(R.drawable.bg_gender_selected);
                btnFemale.setBackgroundResource(R.drawable.bg_gender_unselected);
            }
        });

        btnFemale.setOnClickListener(v -> {
            if (isMaleSelected) {
                isMaleSelected = false;
                btnMale.setBackgroundResource(R.drawable.bg_gender_unselected);
                btnFemale.setBackgroundResource(R.drawable.bg_gender_selected);
            }
        });

        // 4. Xử lý sự kiện khi bấm nút Lưu & Chuyển sang Trang chủ
        btnSave.setOnClickListener(v -> {
            saveAndGoToDashboard();
        });
    }

    private void setupSpinners() {
        String[] activityLevels = {
                "Ít vận động (1.2)",
                "Vận động nhẹ (1.375)",
                "Vận động vừa (1.55)",
                "Vận động nhiều (1.725)",
                "Vận động rất nhiều (1.9)"
        };
        ArrayAdapter<String> adapterActivity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, activityLevels);
        spinnerActivityLevel.setAdapter(adapterActivity);

        String[] goals = {
                "Tăng cơ (Bulking)",
                "Giảm mỡ (Cutting)",
                "Duy trì cân nặng & Sức khỏe"
        };
        ArrayAdapter<String> adapterGoal = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, goals);
        spinnerGoal.setAdapter(adapterGoal);
    }

    private void saveAndGoToDashboard() {
        try {
            String fullName = etFullName.getText().toString().trim();
            String ageStr = etAge.getText().toString().trim();
            String heightStr = etHeight.getText().toString().trim();
            String weightStr = etWeight.getText().toString().trim();
            String gender = isMaleSelected ? "Nam" : "Nữ";

            // Kiểm tra dữ liệu trống
            if (fullName.isEmpty() || ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // An toàn chống lỗi Null từ Spinner
            String activityLevel = spinnerActivityLevel.getSelectedItem() != null
                    ? spinnerActivityLevel.getSelectedItem().toString() : "Ít vận động (1.2)";
            String goal = spinnerGoal.getSelectedItem() != null
                    ? spinnerGoal.getSelectedItem().toString() : "Duy trì cân nặng & Sức khỏe";

            Toast.makeText(this, "Lưu hồ sơ thành công!", Toast.LENGTH_SHORT).show();

            // Gói dữ liệu chuyển sang DashboardActivity
            Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
            intent.putExtra("USER_NAME", fullName);
            intent.putExtra("USER_AGE", ageStr);
            intent.putExtra("USER_HEIGHT", heightStr);
            intent.putExtra("USER_WEIGHT", weightStr);
            intent.putExtra("USER_GENDER", gender);
            intent.putExtra("USER_GOAL", goal);
            intent.putExtra("USER_ACTIVITY", activityLevel);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}