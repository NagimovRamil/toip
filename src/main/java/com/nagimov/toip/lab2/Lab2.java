package com.nagimov.toip.lab2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.util.Arrays.asList;

public class Lab2 {
    public void execute() {
        Random random = new Random();
        BigInteger a = BigInteger.valueOf(3141592);
        BigDecimal d = BigDecimal.valueOf(8);
        BigDecimal q = BigDecimal.valueOf(Math.pow(10, d.doubleValue()));
        BigInteger t = BigInteger.valueOf(random.nextInt(100));
        int[] pArray = {3, 11, 13, 19, 21, 27, 29, 37, 53, 59, 61, 67, 69, 77, 83, 91};
        BigInteger p = BigInteger.valueOf(pArray[random.nextInt(pArray.length)]);
        final BigInteger k = BigInteger.valueOf(200).multiply(t).add(p);

        BigInteger[] sequence = new BigInteger[1000];
        sequence[0] = a;
        BigDecimal[] sequenceForMx = new BigDecimal[1000];
        sequenceForMx[0] = new BigDecimal(a).divide(q);
        Integer[] intArray = new Integer[1000];
        intArray[0] = a.intValue();
        for (int i = 0; i < 999; i++) {
            BigDecimal x = new BigDecimal(k).multiply(new BigDecimal(sequence[i])).divide(q);
            BigDecimal remainder = x.subtract(x.setScale(0, RoundingMode.DOWN));
            sequenceForMx[i+1] = remainder;
            BigInteger bigInteger =
                    remainder.remainder(BigDecimal.ONE).movePointRight(remainder.scale()).abs().toBigInteger();
            sequence[i+1] = bigInteger;
            intArray[i+1] = bigInteger.intValue();
            System.out.println(bigInteger);
        }

        // Математическое ожидание
        BigDecimal m = getM(sequenceForMx);
        int m2 = getMInt(intArray);

        // Выборочная средняя
        int sampleMean = getSampleMean(sequenceForMx);

        // Дисперсия
        calculateDispersion(sequenceForMx, m);

        // Выборочная дисперсия
        calculateSampleVariance(sequenceForMx, m, sampleMean);

        // Частотная таблица
        buildFrequencyTable(sequenceForMx);

        // Гистограмма
        buildHistogram(sequenceForMx);
    }

    private int getMInt(Integer[] intArray) {
        Integer min = Arrays.stream(intArray).min(Integer::compare).get();
        Integer max = Arrays.stream(intArray).max(Integer::compare).get();
        int m = (min + max) / 2;
        System.out.println("Математическое ожидание: " + m);
        return m;
    }

    private BigDecimal getM(BigDecimal[] sequenceForMx) {
        BigDecimal sum = sequenceForMx[0];
        for (int i = 0; i < 999; i++) {
            sum = sequenceForMx[i+1].add(sum);
        }
        BigDecimal m = sum.divide(BigDecimal.valueOf(1000));
//        System.out.println("Математическое ожидание: " + m);
        return m;
    }

    private int getSampleMean(BigDecimal[] sequenceForMx) {
        BigDecimal sum2 = sequenceForMx[0];
        int selection = 50;
        for (int i = 0; i < selection-1; i++) {
            sum2 = sequenceForMx[i+1].add(sum2);
        }
        BigDecimal m2 = sum2.divide(BigDecimal.valueOf(selection));
        System.out.println("Выборочная средняя по " + selection + " значениям: " + m2);
        return selection;
    }

    private void calculateDispersion(BigDecimal[] sequenceForMx, BigDecimal m) {
        BigDecimal sumForDispersion = (sequenceForMx[0].subtract(m)).pow(2);
        for (int i = 0; i < 999; i++) {
            sumForDispersion = (sequenceForMx[i+1].subtract(m)).pow(2).add(sumForDispersion);
        }
        BigDecimal disp = sumForDispersion.divide(BigDecimal.valueOf(1000));
        System.out.println("Дисперсия: " + disp);
        MathContext mc = new MathContext(2);
        System.out.println("Среднеквадратичное отклонение: " + disp.sqrt(mc));
    }

    private void calculateSampleVariance(BigDecimal[] sequenceForMx, BigDecimal m, int selection) {
        BigDecimal sumForDispersion2 = (sequenceForMx[0].subtract(m)).pow(2);
        for (int i = 0; i < selection -1; i++) {
            sumForDispersion2 = (sequenceForMx[i+1].subtract(m)).pow(2).add(sumForDispersion2);
        }
        BigDecimal disp2 = sumForDispersion2.divide(BigDecimal.valueOf(selection));
        System.out.println("Выборочная дисперсия по " + selection + " значениям: " + disp2);
    }

    private void buildFrequencyTable(BigDecimal[] sequenceForMx) {
        System.out.printf("%-10s%-10s%-10s%n", "Интервал", "Кол-во СВ"
                , "Относительная частота попадания");
        BigDecimal baseInterval = BigDecimal.valueOf(1)
                .divide(BigDecimal.valueOf(20)).setScale(2);
        for (int i = 0; i < 20; i++) {
            BigDecimal interval = (BigDecimal.valueOf(i).add(BigDecimal.valueOf(1)))
                    .divide(BigDecimal.valueOf(20)).setScale(2);
            System.out.printf("%-10s", interval);
            int sumForFrequency = 0;
            for (int j = 0; j < 1000; j++) {
                if ((sequenceForMx[j].compareTo(interval) < 0) &&
                        (sequenceForMx[j].compareTo(interval.subtract(baseInterval)) > 0)) {
                    sumForFrequency++;
                }
            }
            System.out.printf("%-10s", sumForFrequency);
            System.out.printf("%-10s%n", BigDecimal.valueOf(sumForFrequency).divide(BigDecimal.valueOf(1000)).setScale(2, RoundingMode.DOWN));
        }
    }

    private void buildHistogram(BigDecimal[] sequenceForMx) {
        System.out.printf("%-10s%-100s%n", "Интервал", "Частота");
        BigDecimal baseInterval2 = BigDecimal.valueOf(1)
                .divide(BigDecimal.valueOf(20)).setScale(2);
        for (int i = 0; i < 20; i++) {
            BigDecimal interval = (BigDecimal.valueOf(i).add(BigDecimal.valueOf(1)))
                    .divide(BigDecimal.valueOf(20)).setScale(2);
            System.out.printf("%-10s", interval);
            int sumForFrequency = 0;
            for (int j = 0; j < 1000; j++) {
                if ((sequenceForMx[j].compareTo(interval) < 0) &&
                        (sequenceForMx[j].compareTo(interval.subtract(baseInterval2)) > 0)) {
                    sumForFrequency++;
                }
            }
            for (int j = 0; j < sumForFrequency; j++) {
                System.out.print("*");
            }
            System.out.println("");
        }
    }
}
