package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;
import com.example.healthgymapp.data.local.remote.repository.AppDatabase;
import com.example.healthgymapp.utils.HealthCalculator;

import java.util.Random;

public class MealSuggestionActivity extends AppCompatActivity {

    private TextView btnBack, tvGoalTitle, tvGoalCalories, tvMenuHeaderTitle;
    private TextView btnTabToday, btnTab7Days, btnTabCustom;
    private TextView tvBreakfastMeal, tvBreakfastNutri, tvLunchMeal, tvLunchNutri, tvSnackMeal, tvSnackNutri, tvDinnerMeal, tvDinnerNutri;
    private LinearLayout layoutBreakfastItem, layoutLunchItem, layoutSnackItem, layoutDinnerItem;
    private Button btnGenerateOtherMenu;

    private int currentTargetCalo = 2111;
    private int currentTab = 1; // 1: Hôm nay, 2: 7 ngày, 3: Tùy chỉnh
    private String currentUserGoal = "Duy trì cân nặng & Sức khỏe"; // Mặc định

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_suggestion);

        // 1. Ánh xạ View
        btnBack = findViewById(R.id.btnBack);
        tvGoalTitle = findViewById(R.id.tvGoalTitle);
        tvGoalCalories = findViewById(R.id.tvGoalCalories);
        tvMenuHeaderTitle = findViewById(R.id.tvMenuHeaderTitle);

        btnTabToday = findViewById(R.id.btnTabToday);
        btnTab7Days = findViewById(R.id.btnTab7Days);
        btnTabCustom = findViewById(R.id.btnTabCustom);

        tvBreakfastMeal = findViewById(R.id.tvBreakfastMeal);
        tvBreakfastNutri = findViewById(R.id.tvBreakfastNutri);
        tvLunchMeal = findViewById(R.id.tvLunchMeal);
        tvLunchNutri = findViewById(R.id.tvLunchNutri);
        tvSnackMeal = findViewById(R.id.tvSnackMeal);
        tvSnackNutri = findViewById(R.id.tvSnackNutri);
        tvDinnerMeal = findViewById(R.id.tvDinnerMeal);
        tvDinnerNutri = findViewById(R.id.tvDinnerNutri);

        // Ánh xạ các khối item bữa ăn
        layoutBreakfastItem = (LinearLayout) tvBreakfastMeal.getParent();
        layoutLunchItem = (LinearLayout) tvLunchMeal.getParent();
        layoutSnackItem = (LinearLayout) tvSnackMeal.getParent();
        layoutDinnerItem = (LinearLayout) tvDinnerMeal.getParent();

        btnGenerateOtherMenu = findViewById(R.id.btnGenerateOtherMenu);

        // Quay lại
        btnBack.setOnClickListener(v -> finish());

        // Bắt sự kiện chuyển tab
        btnTabToday.setOnClickListener(v -> switchTab(1));
        btnTab7Days.setOnClickListener(v -> switchTab(2));
        btnTabCustom.setOnClickListener(v -> switchTab(3));

        // Đổi thực đơn ngẫu nhiên
        btnGenerateOtherMenu.setOnClickListener(v -> generateRandomMenu());

        // Bấm vào từng bữa ăn để xem chi tiết
        layoutBreakfastItem.setOnClickListener(v -> openMealDetail("Bữa sáng", tvBreakfastMeal.getText().toString(), tvBreakfastNutri.getText().toString()));
        layoutLunchItem.setOnClickListener(v -> openMealDetail("Bữa trưa", tvLunchMeal.getText().toString(), tvLunchNutri.getText().toString()));
        layoutSnackItem.setOnClickListener(v -> openMealDetail("Bữa phụ", tvSnackMeal.getText().toString(), tvSnackNutri.getText().toString()));
        layoutDinnerItem.setOnClickListener(v -> openMealDetail("Bữa tối", tvDinnerMeal.getText().toString(), tvDinnerNutri.getText().toString()));

        // Tải mục tiêu tập luyện từ Intent / Database
        loadUserGoalData();
    }

    private void switchTab(int tabIndex) {
        currentTab = tabIndex;

        // Reset màu tab
        btnTabToday.setBackgroundResource(0);
        btnTabToday.setTextColor(Color.parseColor("#6B7280"));

        btnTab7Days.setBackgroundResource(0);
        btnTab7Days.setTextColor(Color.parseColor("#6B7280"));

        btnTabCustom.setBackgroundResource(0);
        btnTabCustom.setTextColor(Color.parseColor("#6B7280"));

        if (tabIndex == 1) {
            btnTabToday.setBackgroundResource(R.drawable.bg_green_banner);
            btnTabToday.setTextColor(Color.WHITE);
            tvMenuHeaderTitle.setText("Thực đơn gợi ý cho Hôm nay");
        } else if (tabIndex == 2) {
            btnTab7Days.setBackgroundResource(R.drawable.bg_green_banner);
            btnTab7Days.setTextColor(Color.WHITE);
            tvMenuHeaderTitle.setText("Thực đơn trung bình trong 7 ngày");
        } else {
            btnTabCustom.setBackgroundResource(R.drawable.bg_green_banner);
            btnTabCustom.setTextColor(Color.WHITE);
            tvMenuHeaderTitle.setText("Thực đơn Tùy chỉnh (Eat Clean)");
        }

        updateMenuContent(currentUserGoal);
    }

    private void updateMenuContent(String goal) {
        if (goal == null) goal = "Duy trì";
        String g = goal.toLowerCase();

        if (g.contains("tăng cơ") || g.contains("bulking")) {
            // --- THỰC ĐƠN TĂNG CƠ (BULKING) ---
            if (currentTab == 1) {
                tvBreakfastMeal.setText("Bánh mì đen + 3 trứng ốp la + Bơ thực vật + 1 quả chuối");
                tvBreakfastNutri.setText("~ 650 kcal  |  35g protein");
                tvLunchMeal.setText("Cơm gạo lứt (2 bát) + Bò xào bông cải xanh + Canh rong biển");
                tvLunchNutri.setText("~ 850 kcal  |  58g protein");
                tvSnackMeal.setText("Sinh tố bơ chuối sữa tươi + 1 nắm hạt điều");
                tvSnackNutri.setText("~ 450 kcal  |  18g protein");
                tvDinnerMeal.setText("Ức gà nướng thảo mộc + Khoai tây nghiền + Salad dầu giấm");
                tvDinnerNutri.setText("~ 900 kcal  |  62g protein");
            } else if (currentTab == 2) {
                tvBreakfastMeal.setText("Phở bò đặc biệt + 1 ly sữa tươi nguyên kem");
                tvBreakfastNutri.setText("~ 700 kcal  |  40g protein");
                tvLunchMeal.setText("Cơm trắng (2.5 bát) + Cá hồi kho tiêu + Canh sườn củ quả");
                tvLunchNutri.setText("~ 920 kcal  |  55g protein");
                tvSnackMeal.setText("Bánh quy ngũ cốc + 1 hộp sữa chua Hy Lạp");
                tvSnackNutri.setText("~ 350 kcal  |  20g protein");
                tvDinnerMeal.setText("Thịt bò bít tết + Khoai tây chiên + Súp kem nấm");
                tvDinnerNutri.setText("~ 950 kcal  |  65g protein");
            } else {
                tvBreakfastMeal.setText("Yến mạch nấu sữa tươi nguyên kem + Bơ đậu phộng + Chuối");
                tvBreakfastNutri.setText("~ 680 kcal  |  28g protein");
                tvLunchMeal.setText("Mì ý sốt bò băm + Phô mai bào + Salad dầu olive");
                tvLunchNutri.setText("~ 880 kcal  |  52g protein");
                tvSnackMeal.setText("Bánh mì sandwich bơ đậu phộng + 1 quả trứng luộc");
                tvSnackNutri.setText("~ 400 kcal  |  22g protein");
                tvDinnerMeal.setText("Cá hồi phi lê áp chảo + Cơm gạo lứt + Súp lơ luộc");
                tvDinnerNutri.setText("~ 850 kcal  |  58g protein");
            }
        } else if (g.contains("duy trì") || g.contains("sức khỏe") || g.contains("maintain")) {
            // --- THỰC ĐƠN DUY TRÌ CÂN NẶNG & SỨC KHỎE ---
            if (currentTab == 1) {
                tvBreakfastMeal.setText("Phở bò tái chín vừa + 1 ly trà ấm");
                tvBreakfastNutri.setText("~ 500 kcal  |  30g protein");
                tvLunchMeal.setText("Cơm trắng (1.5 bát) + Cá kho tộ + Canh bầu nấu tôm");
                tvLunchNutri.setText("~ 680 kcal  |  40g protein");
                tvSnackMeal.setText("Sữa chua không đường + Táo cắt lát");
                tvSnackNutri.setText("~ 220 kcal  |  10g protein");
                tvDinnerMeal.setText("Thịt lợn nạc luộc + Cơm trắng + Canh rau ngót thịt băm");
                tvDinnerNutri.setText("~ 750 kcal  |  45g protein");
            } else if (currentTab == 2) {
                tvBreakfastMeal.setText("Bánh mỳ trứng ốp la + 1 ly sữa đậu nành");
                tvBreakfastNutri.setText("~ 450 kcal  |  22g protein");
                tvLunchMeal.setText("Cơm gạo lứt + Gà luộc xé phay + Canh chua cá lóc");
                tvLunchNutri.setText("~ 650 kcal  |  45g protein");
                tvSnackMeal.setText("1 Quả chuối tiêu + Hạt hạnh nhân");
                tvSnackNutri.setText("~ 200 kcal  |  8g protein");
                tvDinnerMeal.setText("Thịt bò xào hành tây + Khoai lang hấp");
                tvDinnerNutri.setText("~ 600 kcal  |  38g protein");
            } else {
                tvBreakfastMeal.setText("Cháo yến mạch thịt băm + Hành lá");
                tvBreakfastNutri.setText("~ 420 kcal  |  25g protein");
                tvLunchMeal.setText("Cá sốt cà chua + Đậu hũ nhồi thịt + Cơm trắng");
                tvLunchNutri.setText("~ 690 kcal  |  42g protein");
                tvSnackMeal.setText("Sinh tố xoài sữa chua ít đường");
                tvSnackNutri.setText("~ 240 kcal  |  9g protein");
                tvDinnerMeal.setText("Tôm rang thịt ba chỉ + Rau muống luộc");
                tvDinnerNutri.setText("~ 630 kcal  |  40g protein");
            }
        } else {
            // --- THỰC ĐƠN GIẢM MỠ (CUTTING) ---
            if (currentTab == 1) {
                tvBreakfastMeal.setText("Yến mạch + chuối + hạt chia + sữa hạt");
                tvBreakfastNutri.setText("~ 520 kcal  |  28g protein");
                tvLunchMeal.setText("Ức gà áp chảo + cơm gạo lứt + súp lơ luộc");
                tvLunchNutri.setText("~ 680 kcal  |  48g protein");
                tvSnackMeal.setText("Sữa chua Hy Lạp + hạt hạnh nhân / Whey Protein");
                tvSnackNutri.setText("~ 310 kcal  |  22g protein");
                tvDinnerMeal.setText("Cá hồi áp chảo + khoai lang hấp + salad rau củ");
                tvDinnerNutri.setText("~ 601 kcal  |  42g protein");
            } else if (currentTab == 2) {
                tvBreakfastMeal.setText("4 Lòng trắng trứng + 2 lát bánh mì đen + bơ quả");
                tvBreakfastNutri.setText("~ 480 kcal  |  32g protein");
                tvLunchMeal.setText("Thịt bò xào măng tây + cơm gạo lứt + canh rau đay");
                tvLunchNutri.setText("~ 710 kcal  |  50g protein");
                tvSnackMeal.setText("1 Quả táo + 1 Muỗng Whey Protein Isolate");
                tvSnackNutri.setText("~ 250 kcal  |  25g protein");
                tvDinnerMeal.setText("Tôm nướng muối ớt + rau củ luộc");
                tvDinnerNutri.setText("~ 550 kcal  |  40g protein");
            } else {
                tvBreakfastMeal.setText("Sinh tố bơ yến mạch + bột protein");
                tvBreakfastNutri.setText("~ 500 kcal  |  30g protein");
                tvLunchMeal.setText("Thăn lợn áp chảo dầu olive + khoai tây nướng + xà lách");
                tvLunchNutri.setText("~ 640 kcal  |  45g protein");
                tvSnackMeal.setText("Hạt óc chó / Hạnh nhân + 1 hộp sữa chua không đường");
                tvSnackNutri.setText("~ 280 kcal  |  15g protein");
                tvDinnerMeal.setText("Cá ngừ ngâm dầu vắt chanh + bún lứt + rau sống");
                tvDinnerNutri.setText("~ 580 kcal  |  38g protein");
            }
        }
    }

    private void generateRandomMenu() {
        String[] breakfasts = {
                "Yến mạch ngâm sữa chua + quả mọng",
                "3 Trứng ốp la + Bánh mì nguyên cám",
                "Bún lứt nấu thịt băm ức gà"
        };
        String[] lunches = {
                "Đùi gà bỏ da nướng nồi chiên + Cơm gạo lứt",
                "Thịt bò lúc lắc + Dưa chuột + Cơm trắng 1/2 bát",
                "Cá thu sốt cà chua + Bắp luộc"
        };
        String[] dinners = {
                "Mực hấp gừng + Rau củ luộc thập cẩm",
                "Ức gà xé phay trộn salad bắp cải",
                "Tôm áp chảo + Khoai lang tím"
        };

        Random r = new Random();
        tvBreakfastMeal.setText(breakfasts[r.nextInt(breakfasts.length)]);
        tvLunchMeal.setText(lunches[r.nextInt(lunches.length)]);
        tvDinnerMeal.setText(dinners[r.nextInt(dinners.length)]);

        Toast.makeText(this, "Đã đổi thực đơn gợi ý mới!", Toast.LENGTH_SHORT).show();
    }

    private void openMealDetail(String mealType, String title, String nutri) {
        Intent intent = new Intent(MealSuggestionActivity.this, MealDetailActivity.class);
        intent.putExtra("MEAL_TYPE", mealType);
        intent.putExtra("MEAL_TITLE", title);
        intent.putExtra("MEAL_NUTRI", nutri);
        startActivity(intent);
    }

    private void loadUserGoalData() {
        // 1. Nhận trực tiếp từ Intent truyền sang (Ưu tiên cao nhất)
        String intentGoal = getIntent().getStringExtra("USER_GOAL");
        if (intentGoal != null && !intentGoal.isEmpty()) {
            currentUserGoal = intentGoal;
            tvGoalTitle.setText(currentUserGoal);
            updateMenuContent(currentUserGoal);
        }

        // 2. Lấy dữ liệu từ Database để tính toán Calo chuẩn
        AppDatabase.getInstance(getApplicationContext()).userDao().getUserProfile().observe(this, profile -> {
            if (profile != null) {
                // Nếu Intent không có, dùng mục tiêu từ Database
                if (intentGoal == null || intentGoal.isEmpty()) {
                    if (profile.getFitnessGoal() != null && !profile.getFitnessGoal().isEmpty()) {
                        currentUserGoal = profile.getFitnessGoal();
                    }
                }

                tvGoalTitle.setText(currentUserGoal);

                float bmr = HealthCalculator.calculateBMR(profile.getWeight(), profile.getHeight(), profile.getAge(), profile.getGender());
                float actualTdee = HealthCalculator.calculateTDEE(bmr, profile.getActivityLevel());
                currentTargetCalo = HealthCalculator.calculateTargetCalories(actualTdee, currentUserGoal);

                tvGoalCalories.setText("Calo mục tiêu: " + currentTargetCalo + " kcal/ngày");
                updateMenuContent(currentUserGoal);
            } else if (intentGoal == null) {
                tvGoalTitle.setText("Duy trì cân nặng & Sức khỏe");
                tvGoalCalories.setText("Calo mục tiêu: 2250 kcal/ngày");
                updateMenuContent("Duy trì");
            }
        });
    }
}