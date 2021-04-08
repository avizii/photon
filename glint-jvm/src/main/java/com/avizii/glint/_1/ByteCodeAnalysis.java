package com.avizii.glint._1;

/**
 * @Author : Avizii
 * @Create : 2021.04.08
 */
public class ByteCodeAnalysis {

    public static void main(String[] args) {
        MovingAverage average1 = new MovingAverage();
        int num1 = 1, num2 = 2;
        average1.submit(num1);
        average1.submit(num2);

        double avg = average1.getAvg();
        System.out.println(avg);

        int[] nums = {1, 6, 8, 7, 3, 4};
        MovingAverage average2 = new MovingAverage();
        for (int num : nums) {
            if (num % 2 == 0) {
                average2.submit(num);
            }
        }
        System.out.println(average2.getAvg());
        for (int i = 0; i < nums.length; i++) {
            System.out.println(i);
        }
    }
}
