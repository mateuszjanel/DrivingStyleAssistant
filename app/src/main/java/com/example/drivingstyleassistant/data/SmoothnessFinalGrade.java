package com.example.drivingstyleassistant.data;

import com.example.drivingstyleassistant.domain.helpers.RouteHelper;

import java.util.ArrayList;

public class SmoothnessFinalGrade implements GradeHelper {

    public ArrayList<Float> fragmentaryGrades;
    float sum;
    RouteHelper routeHelper = new RouteHelper();

    public SmoothnessFinalGrade(){
        fragmentaryGrades = new ArrayList<>();
    }

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
