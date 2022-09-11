package com.nagimov.toip;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;

public class Main {

    // ЛР №2
    public static void main(String[] args) {
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
        for (int i = 0; i < 999; i++) {
            BigDecimal x = new BigDecimal(k).multiply(new BigDecimal(sequence[i])).divide(q);
            BigDecimal remainder = x.subtract(x.setScale(0, RoundingMode.DOWN));
            sequenceForMx[i+1] = remainder;
            BigInteger decimal =
                    remainder.remainder(BigDecimal.ONE).movePointRight(remainder.scale()).abs().toBigInteger();
            sequence[i+1] = decimal;
            System.out.println(decimal);
        }
        // Выводим математическое ожидание
        BigDecimal sum = sequenceForMx[0];
        for (int i = 0; i < 999; i++) {
            sum = sequenceForMx[i+1].add(sum);
        }
        BigDecimal m = sum.divide(BigDecimal.valueOf(1000));
        System.out.println("Математическое ожидание: " + m);

        // Выборочная средняя
        BigDecimal sum2 = sequenceForMx[0];
        int selection = 50;
        for (int i = 0; i < selection-1; i++) {
            sum2 = sequenceForMx[i+1].add(sum2);
        }
        BigDecimal m2 = sum2.divide(BigDecimal.valueOf(selection));
        System.out.println("Выборочная средняя по " + selection + " значениям: " + m2);

        // Дисперсия
        BigDecimal sumForDispersion = (sequenceForMx[0].subtract(m)).pow(2);
        for (int i = 0; i < 999; i++) {
            sumForDispersion = (sequenceForMx[i+1].subtract(m)).pow(2).add(sumForDispersion);
        }
        BigDecimal disp = sumForDispersion.divide(BigDecimal.valueOf(1000));
        System.out.println("Дисперсия: " + disp);
        MathContext mc = new MathContext(2);
        System.out.println("Среднеквадратичное отклонение: " + disp.sqrt(mc));

        // Выборочная дисперсия
        BigDecimal sumForDispersion2 = (sequenceForMx[0].subtract(m)).pow(2);
        for (int i = 0; i < selection-1; i++) {
            sumForDispersion2 = (sequenceForMx[i+1].subtract(m)).pow(2).add(sumForDispersion2);
        }
        BigDecimal disp2 = sumForDispersion2.divide(BigDecimal.valueOf(selection));
        System.out.println("Выборочная дисперсия по " + selection + " значениям: " + disp2);


        // Частотная таблица
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

    // ЛР №1
//    static int statist1;
//    static int statist2;
//
//    public static void main(String[] args) {
//        int n = 3; // входной алфавит
//        int m = 4; // выходной алфавит
//        int p = 2; // количество состояний
//
//
//
//        int[] enterArray = new int[5];
//        int[] startCondition = new int[5];
//
//        for (int i = 0; i < 5; i++) {
//            enterArray[i] = ((int) (Math.random() * n)) + 1;
//            startCondition[i] = ((int) (Math.random() * p)) + 1;
//        }
//
//        int[] exitArray = new int[5];
//        int[] endCondition = new int[5];
//
//        for (int i = 0; i < 5; i++) {
//            endCondition[i] = getCondition(enterArray[i], startCondition[i]);
//            exitArray[i] = getExitValue(enterArray[i], startCondition[i]);
//        }
//
//        System.out.println("Начальное состояние" + Arrays.toString(startCondition));
//        System.out.println("Конечное состояние" + Arrays.toString(endCondition));
//        System.out.println("Входное слово" + Arrays.toString(enterArray));
//        System.out.println("Выходной алфавит" + Arrays.toString(exitArray));
//        System.out.println("Статистика: Выходные значения '1': " + statist1 + "; Выходные значения '2': " + statist2);
//    }
//
//    private static int getExitValue(int enterValue, int startCondition) {
//        int exitValue = 0;
//        if(startCondition == 1) {
//            switch (enterValue) {
//                case 1:
//                    exitValue = 2;
//                    break;
//                case 2:
//                    exitValue = 1;
//                    break;
//                case 3:
//                    exitValue = 3;
//                    break;
//            }
//        } else {
//            switch (enterValue) {
//                case 1:
//                    exitValue = 3;
//                    break;
//                case 2:
//                    exitValue = 4;
//                    break;
//                case 3:
//                    exitValue = 2;
//                    break;
//            }
//        }
//
//        return exitValue;
//    }
//
//    private static int getCondition(int enterValueX, int startConditionZ) {
//        int endCondition = 0;
//        if(startConditionZ == 1) {
//            switch (enterValueX) {
//                case 1:
//                    endCondition = 1;
//                    statist1++;
//                    break;
//                case 2:
//                    endCondition = 2;
//                    statist2++;
//                    break;
//                case 3:
//                    endCondition = 2;
//                    statist2++;
//                    break;
//            }
//        } else {
//            switch (enterValueX) {
//                case 1:
//                    endCondition = 1;
//                    statist1++;
//                    break;
//                case 2:
//                    endCondition = 1;
//                    statist1++;
//                    break;
//                case 3:
//                    endCondition = 2;
//                    statist2++;
//                    break;
//            }
//        }
//
//        return endCondition;
//    }
}

