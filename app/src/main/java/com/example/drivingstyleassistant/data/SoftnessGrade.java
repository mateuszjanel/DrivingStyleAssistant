package com.example.drivingstyleassistant.data;

import org.apache.commons.math3.analysis.interpolation.LoessInterpolator;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SoftnessGrade implements GradeHelper {

    public ArrayList<Double> speeds;
    public ArrayList<Double> xValsList;
    public double[] smooths;

    public float grade(){

    }

    public void analyze(){
        LoessInterpolator loessInterpolator = new LoessInterpolator();
        smooths = loessInterpolator.smooth(arrayListToDoubleArray(xValsList), arrayListToDoubleArray(speeds));

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
