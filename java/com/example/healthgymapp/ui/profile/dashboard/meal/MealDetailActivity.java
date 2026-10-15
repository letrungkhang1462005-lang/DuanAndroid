package com.example.healthgymapp.ui.profile.dashboard.meal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthgymapp.R;

public class MealDetailActivity extends AppCompatActivity {

    private ImageView imgMeal;
    private TextView btnBack, tvMealTitle, tvMealTypeTag, tvMealGoalTag, tvMealNutriSummary;
    private TextView tvIng1Name, tvIng1Weight, tvIng2Name, tvIng2Weight, tvIng3Name, tvIng3Weight, tvIng4Name, tvIng4Weight;
    private TextView tvCookingSteps;
    private Button btnSaveToLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        // Ánh xạ View
        imgMeal = findViewById(R.id.imgMeal);
        btnBack = findViewById(R.id.btnBack);
        tvMealTitle = findViewById(R.id.tvMealTitle);
        tvMealTypeTag = findViewById(R.id.tvMealTypeTag);
        tvMealGoalTag = findViewById(R.id.tvMealGoalTag);
        tvMealNutriSummary = findViewById(R.id.tvMealNutriSummary);

        tvIng1Name = findViewById(R.id.tvIng1Name);
        tvIng1Weight = findViewById(R.id.tvIng1Weight);
        tvIng2Name = findViewById(R.id.tvIng2Name);
        tvIng2Weight = findViewById(R.id.tvIng2Weight);
        tvIng3Name = findViewById(R.id.tvIng3Name);
        tvIng3Weight = findViewById(R.id.tvIng3Weight);
        tvIng4Name = findViewById(R.id.tvIng4Name);
        tvIng4Weight = findViewById(R.id.tvIng4Weight);

        tvCookingSteps = findViewById(R.id.tvCookingSteps);
        btnSaveToLog = findViewById(R.id.btnSaveToLog);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Nhận dữ liệu truyền sang từ Intent
        String mealType = getIntent().getStringExtra("MEAL_TYPE");
        String mealTitle = getIntent().getStringExtra("MEAL_TITLE");
        String mealNutri = getIntent().getStringExtra("MEAL_NUTRI");

        if (mealTitle != null) {
            tvMealTitle.setText(mealTitle);
        }
        if (mealType != null) {
            tvMealTypeTag.setText(mealType);
        }
        if (mealNutri != null) {
            tvMealNutriSummary.setText(mealNutri);
        }

        // Khớp chính xác theo từng tên món ăn thực tế hiển thị trên màn hình của bạn
        if (mealTitle != null) {
            if (mealTitle.contains("Phở bò đặc biệt")) {
                imgMeal.setImageResource(R.drawable.img_bo); // Ảnh thịt bò
                tvIng1Name.setText("🍜 Bánh phở tươi"); tvIng1Weight.setText("180g");
                tvIng2Name.setText("🥩 Thịt bò tái/chín"); tvIng2Weight.setText("120g");
                tvIng3Name.setText("🥛 Sữa tươi nguyên kem"); tvIng3Weight.setText("250ml");
                tvIng4Name.setText("🌱 Hành hoa, rau mùi"); tvIng4Weight.setText("30g");
                tvCookingSteps.setText("1. Chần bánh phở và thịt bò qua nước sôi.\n2. Đun sôi nước dùng phở đậm đà rồi chan vào tô.\n3. Thưởng thức kèm 1 ly sữa tươi nguyên kem bên cạnh.");
            }
            else if (mealTitle.contains("Cơm gạo lứt")) {
                imgMeal.setImageResource(R.drawable.img_chicken); // Ảnh ức gà / cơm
                tvIng1Name.setText("🍚 Cơm gạo lứt"); tvIng1Weight.setText("2 bát");
                tvIng2Name.setText("🥩 Bò xào bông cải"); tvIng2Weight.setText("180g");
                tvIng3Name.setText("🥦 Bông cải xanh"); tvIng3Weight.setText("150g");
                tvIng4Name.setText("🥣 Canh rong biển"); tvIng4Weight.setText("1 bát");
                tvCookingSteps.setText("1. Nấu chín cơm gạo lứt dẻo ngon.\n2. Xào thịt bò với bông cải xanh vừa tới.\n3. Ăn kèm canh rong biển thanh mát.");
            }
            else if (mealTitle.contains("Bánh quy ngũ cốc")) {
                imgMeal.setImageResource(R.drawable.img_sinhto); // Ảnh sinh tố / ngũ cốc
                tvIng1Name.setText("🍪 Bánh quy ngũ cốc"); tvIng1Weight.setText("4 cái");
                tvIng2Name.setText("🥛 Sữa chua Hy Lạp"); tvIng2Weight.setText("1 hộp");
                tvIng3Name.setText("🥜 Hạt dinh dưỡng"); tvIng3Weight.setText("20g");
                tvIng4Name.setText("🍯 Mật ong"); tvIng4Weight.setText("1 thìa");
                tvCookingSteps.setText("1. Múc sữa chua Hy Lạp ra bát nhỏ.\n2. Trộn cùng hạt dinh dưỡng và thưởng thức kèm bánh quy.");
            }
            else if (mealTitle.contains("Thịt bò bít tết")) {
                imgMeal.setImageResource(R.drawable.img_bo); // Ảnh thịt bò bít tết
                tvIng1Name.setText("🥩 Thăn bò bít tết"); tvIng1Weight.setText("200g");
                tvIng2Name.setText("🍟 Khoai tây chiên"); tvIng2Weight.setText("120g");
                tvIng3Name.setText("🍄 Súp kem nấm"); tvIng3Weight.setText("1 bát");
                tvIng4Name.setText("🧈 Bơ lạt / Dầu olive"); tvIng4Weight.setText("1 thìa");
                tvCookingSteps.setText("1. Áp chảo thịt bò bít tết với bơ và tỏi thơm.\n2. Chiên vàng giòn khoai tây.\n3. Dùng kèm súp kem nấm nóng hổi.");
            }
            else {
                // Mặc định cho các món cá hồi / ức gà khác
                imgMeal.setImageResource(R.drawable.img_cahoi);
                tvIng1Name.setText("🐟 Cá hồi tươi"); tvIng1Weight.setText("160g");
                tvIng2Name.setText("🍠 Khoai lang hấp"); tvIng2Weight.setText("150g");
                tvIng3Name.setText("🥗 Salad rau củ"); tvIng3Weight.setText("150g");
                tvIng4Name.setText("🍋 Dầu olive"); tvIng4Weight.setText("1 thìa");
                tvCookingSteps.setText("1. Áp chảo cá hồi chín vàng đều 2 mặt.\n2. Hấp chín khoai lang.\n3. Trộn salad với dầu olive và chanh.");
            }
        }

        btnSaveToLog.setOnClickListener(v -> {
            Toast.makeText(this, "Đã lưu bữa ăn vào nhật ký hôm nay!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}