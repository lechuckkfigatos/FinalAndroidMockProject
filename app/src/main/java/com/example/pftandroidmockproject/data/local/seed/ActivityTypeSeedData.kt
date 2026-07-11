package com.example.pftandroidmockproject.data.local.seed

import com.example.pftandroidmockproject.data.local.entity.ActivityTypeEntity

object ActivityTypeSeedData {

    val activityTypes = listOf(
        // 4 môn có sẵn của bạn
        ActivityTypeEntity(
            nameVi = "Đi bộ",
            nameEn = "Walking",
            metValue = 3.5
        ),
        ActivityTypeEntity(
            nameVi = "Chạy bộ",
            nameEn = "Running",
            metValue = 9.8
        ),
        ActivityTypeEntity(
            nameVi = "Đạp xe",
            nameEn = "Cycling",
            metValue = 7.5
        ),
        ActivityTypeEntity(
            nameVi = "Bơi lội",
            nameEn = "Swimming",
            metValue = 8.0
        ),

        // === 15 MÔN THỂ THAO & BÀI TẬP BỔ SUNG ===
        ActivityTypeEntity(
            nameVi = "Tập Gym (Kháng lực/Tạ nặng)",
            nameEn = "Weight lifting / Resistance training",
            metValue = 6.0
        ),
        ActivityTypeEntity(
            nameVi = "Thể dục nhịp điệu",
            nameEn = "Aerobics",
            metValue = 6.5
        ),
        ActivityTypeEntity(
            nameVi = "Nhảy dây",
            nameEn = "Jumping rope",
            metValue = 11.0
        ),
        ActivityTypeEntity(
            nameVi = "Tập Yoga",
            nameEn = "Yoga",
            metValue = 2.5
        ),
        ActivityTypeEntity(
            nameVi = "Đá bóng",
            nameEn = "Soccer",
            metValue = 7.0
        ),
        ActivityTypeEntity(
            nameVi = "Đánh cầu lông",
            nameEn = "Badminton",
            metValue = 5.5
        ),
        ActivityTypeEntity(
            nameVi = "Chơi bóng rổ",
            nameEn = "Basketball",
            metValue = 6.5
        ),
        ActivityTypeEntity(
            nameVi = "Chơi bóng bàn",
            nameEn = "Table tennis",
            metValue = 4.0
        ),
        ActivityTypeEntity(
            nameVi = "Đánh Tennis",
            nameEn = "Tennis",
            metValue = 7.3
        ),
        ActivityTypeEntity(
            nameVi = "Nhảy hiện đại / Zumba",
            nameEn = "Dancing / Zumba",
            metValue = 5.0
        ),
        ActivityTypeEntity(
            nameVi = "Cardio cường độ cao",
            nameEn = "High-intensity cardio",
            metValue = 8.0
        ),
        ActivityTypeEntity(
            nameVi = "Tập Pilates",
            nameEn = "Pilates",
            metValue = 3.0
        ),
        ActivityTypeEntity(
            nameVi = "Leo cầu thang",
            nameEn = "Stair climbing",
            metValue = 4.0
        ),
        ActivityTypeEntity(
            nameVi = "Lao động việc nhà (Quét dọn, lau nhà)",
            nameEn = "Housework (Cleaning, mopping)",
            metValue = 2.8
        ),
        ActivityTypeEntity(
            nameVi = "Giãn cơ",
            nameEn = "Stretching",
            metValue = 2.3
        )
    )
}