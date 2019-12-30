package com.example.drivingstyleassistant.data;

import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;

import java.util.ArrayList;

public class SmoothnessFragmentaryGrade implements GradeHelper {

    public ArrayList<Double> speeds;
    public ArrayList<Double> xValsList;
    public double[] smooths;
    int deviationsCounter;

    public SmoothnessFragmentaryGrade(){
        speeds = new ArrayList<>();
        xValsList = new ArrayList<>();
    }

    public float grade(){
        deviationsCounter = 0;
        analyze();
        float grade = 5 * (deviationsCounter/smooths.length);

        return grade;
    }

    public void analyze(){
        LoessInterpolator loessInterpolator = new LoessInterpolator();
        smooths = loessInterpolator.smooth(arrayListToDoubleArray(xValsList), arrayListToDoubleArray(speeds));
        for(int i = 0; i < smooths.length; i++){
            if(Math.abs(speeds.get(i) - smooths[i]) > 10){
                deviationsCounter++;
            }
        }
    }

    public double[] arrayListToDoubleArray(ArrayList<Double> arrayList){
        int arraySize = arrayList.size();
        double[] tempDoubleArray = new double[arraySize];
        for(int i = 0; i<arraySize; i++){
            tempDoubleArray[i] = (double)arrayList.get(i);
        }
        return tempDoubleArray;
    }
}
