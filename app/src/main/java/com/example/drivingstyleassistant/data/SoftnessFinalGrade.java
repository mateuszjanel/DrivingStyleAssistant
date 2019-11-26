package com.example.drivingstyleassistant.data;

import java.util.ArrayList;

public class SoftnessFinalGrade implements GradeHelper {

    ArrayList<Float> fragmentaryGrades = new ArrayList<>();
    float sum;

    @Override
    public void analyze() {
        sum = 0;
        for(float grade:fragmentaryGrades){
            sum = sum + grade;
        }
    }

    @Override
    public float grade() {
        analyze();
        float finalGrade = sum / fragmentaryGrades.size();
        return finalGrade;
    }
}
