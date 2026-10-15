package com.example.healthgymapp.data.local.remote.repository;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class UserProfile {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fullName;
    private int age;
    private String gender; // "Nam" hoặc "Nữ"
    private float height;  // cm
    private float weight;  // kg
    private float activityLevel; // Hệ số vận động (1.2 -> 1.9)
    private String fitnessGoal;  // "Bulking", "Cutting", "Maintenance"

    public UserProfile(String fullName, int age, String gender, float height, float weight, float activityLevel, String fitnessGoal) {
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.fitnessGoal = fitnessGoal;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public float getHeight() { return height; }
    public float getWeight() { return weight; }
    public float getActivityLevel() { return activityLevel; }
    public String getFitnessGoal() { return fitnessGoal; }
}
