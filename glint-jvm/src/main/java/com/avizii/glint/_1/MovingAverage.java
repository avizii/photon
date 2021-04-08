package com.avizii.glint._1;

/**
 * @Author : Avizii
 * @Create : 2021.04.08
 */
public class MovingAverage {

    private int count = 0;
    private double sum = 0.0d;

    public void submit(double value) {
        this.count++;
        sum += value;
    }

    public double getAvg() {
        if (count == 0) return sum;
        return sum / count;
    }

}