package com.nagimov.toip.lab2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Lab22 {

    public void execute() {
        // Вариант 17
        int[] sequenceX = {4, 6, 10, 14, 16, 20, 24};
        double[] sequenceP = {0.04, 0.1, 0.1, 0.27, 0.33, 0.13, 0.03};
        double[] raw = {0, 0.04, 0.14, 0.24, 0.51, 0.84, 0.97, 1};

        Random random = new Random();
        BigInteger a = BigInteger.valueOf(3141592);
        BigDecimal d = BigDecimal.valueOf(8);
        BigDecimal q = BigDecimal.valueOf(Math.pow(10, d.doubleValue()));
        BigInteger t = BigInteger.valueOf(random.nextInt(100));
        int[] pArray = {3, 11, 13, 19, 21, 27, 29, 37, 53, 59, 61, 67, 69, 77, 83, 91};
        BigInteger p = BigInteger.valueOf(pArray[random.nextInt(pArray.length)]);
        final BigInteger k = BigInteger.valueOf(200).multiply(t).add(p);

        BigInteger[] sequence = new BigInteger[100];
        sequence[0] = a;
        BigDecimal[] sequenceOfRandomDecimals= new BigDecimal[100];
        sequenceOfRandomDecimals[0] = new BigDecimal(a).divide(q);

        for (int i = 0; i < 99; i++) {
            BigDecimal x = new BigDecimal(k).multiply(new BigDecimal(sequence[i])).divide(q);
            BigDecimal remainder = x.subtract(x.setScale(0, RoundingMode.DOWN));
            BigInteger bigInteger =
                    remainder.remainder(BigDecimal.ONE).movePointRight(remainder.scale()).abs().toBigInteger();
            remainder = remainder.setScale(2, RoundingMode.DOWN);
            sequenceOfRandomDecimals[i+1] = remainder;
            sequence[i+1] = bigInteger;
            System.out.println(remainder);
        }

        // Математическое ожидание
        double mx = getMx(sequenceX, sequenceP);

        // Дисперсия
        getDispertion(sequenceX, sequenceP, mx);

        // Заполняем мапу ключами "Интервал" и значениями "Кол-во повторений" (2 часть)
        Map<Double, Integer> intervalCounterMap = new HashMap<>();
        for (int i = 1; i < raw.length; i++) {
            intervalCounterMap.put(raw[i], 0);
        }
        for (int i = 0; i < 100; i++) {
            for (int j = 1; j < raw.length; j++) {
                if(sequenceOfRandomDecimals[i].doubleValue() > raw[j-1]
                        && sequenceOfRandomDecimals[i].doubleValue() <= raw[j]) {
                    int counter = intervalCounterMap.get(raw[j]);
                    intervalCounterMap.put(raw[j], ++counter);
                }
            }
        }
//        for (Map.Entry<Double, Integer> doubleIntegerEntry : map.entrySet()) {
//            Double dou = doubleIntegerEntry.getKey();
//            Integer integer = doubleIntegerEntry.getValue();
//            System.out.println(dou + ": " + integer);
//        }

        // Частотная таблица (2 часть)
        System.out.printf("%-10s%-10s%-10s%n", "Интервал", "Кол-во СВ", "Относительная частота попадания");
        for (int i = 1; i < raw.length; i++) {
            System.out.printf("%-10s", raw[i]);
            System.out.printf("%-10s", intervalCounterMap.get(raw[i]));
            double rawValue = (double) intervalCounterMap.get(raw[i]);
            double freq = rawValue/ 100;
            System.out.printf("%-10s%n", freq);
        }

        // Гистограмма (2 часть)
        System.out.printf("%-10s%-100s%n", "Интервал", "Частота");
        for (int i = 1; i < raw.length; i++) {
            System.out.printf("%-10s", raw[i]);
            for (int j = 1; j < intervalCounterMap.get(raw[i]); j++) {
                System.out.print("*");
            }
            System.out.println("");
        }

        // 3 часть ЛР
        double[] raw2 = {0, 0, 0.0625, 0.125, 0.1875, 0.25, 0.1875, 0.125, 0.0625, 0, 0.125, 0.25, 0.375, 0.5, 0, 0, 0,  0, 0, 0, 0, 0};
        double[] sequenceX2 = {0, 0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10};

        // Заполняем мапу ключами "Интервал" и значениями "Кол-во повторений" (3 часть)
        Map<Double, Integer> intervalCounterMap2 = new HashMap<>();
        List<Double> listForMx = new ArrayList<>();
        for (int i = 1; i < raw2.length; i++) {
            intervalCounterMap2.put(sequenceX2[i], 0);
        }
        for (int i = 0; i < 100; i++) {
            for (int j = 1; j < raw2.length; j++) {
                if(sequenceOfRandomDecimals[i].doubleValue() <= raw2[j]) {
                    int counter = intervalCounterMap2.get(sequenceX2[j]);
                    intervalCounterMap2.put(sequenceX2[j], ++counter);
                    listForMx.add(sequenceOfRandomDecimals[i].doubleValue());
                }
            }
        }

        // Мат.ожидание (3 часть)
        double sum = 0;
        for (Double value :
                listForMx) {
            sum = sum + value;
        }
        double mx2 = sum / listForMx.size();

        // Дисперсия (3 часть)

        // Частотная таблица (3 часть)
        System.out.println("----------------------");
        System.out.println("3 часть ЛР");
        System.out.println("----------------------");
        System.out.println("Математическое ожидание 3 часть: " + mx2);

        System.out.printf("%-10s%-10s%-10s%n", "Интервал", "Кол-во СВ", "Относительная частота попадания");
        for (int i = 1; i < raw2.length; i++) {
            System.out.printf("%-10s", sequenceX2[i]);
            System.out.printf("%-10s", intervalCounterMap2.get(sequenceX2[i]));
            double raw2Value = (double) intervalCounterMap2.get(sequenceX2[i]);
            double freq = raw2Value/ 100;
            System.out.printf("%-10s%n", freq);
        }

        // Гистограмма (3 часть)
        System.out.printf("%-10s%-100s%n", "Интервал", "Частота");
        for (int i = 1; i < raw2.length; i++) {
            System.out.printf("%-10s", sequenceX2[i]);
            for (int j = 1; j < intervalCounterMap2.get(sequenceX2[i]); j++) {
                System.out.print("*");
            }
            System.out.println("");
        }
    }

    private void getDispertion(int[] sequenceX, double[] sequenceP, double mx) {
        double dispersion = 0;
        for (int i = 0; i < sequenceX.length; i++) {
            double d = Math.pow(sequenceX[i], 2) * sequenceP[i];
            dispersion = dispersion + d;
        }
        dispersion = dispersion - Math.pow(mx, 2);
        System.out.println("Дисперсия (2 часть): " + dispersion);
    }

    private double getMx(int[] sequenceX, double[] sequenceP) {
        double mx = 0;
        for (int i = 0; i < sequenceX.length; i++) {
            double m = sequenceX[i] * sequenceP[i];
            mx = mx + m;
        }
        System.out.println("Математическое ожидание (2 часть): " + mx);
        return mx;
    }
}
