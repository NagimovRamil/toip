package com.nagimov.toip.lab1;

import java.util.Arrays;

// ЛР №1
public class Lab1 {
    static int statist1;
    static int statist2;

    /**
     *  @param n // входной алфавит
     *  @param m // выходной алфавит
     *  @param p // количество состояний
     */
    public void execute(int n, int m, int p) {




        int[] enterArray = new int[5];
        int[] startCondition = new int[5];

        for (int i = 0; i < 5; i++) {
            enterArray[i] = ((int) (Math.random() * n)) + 1;
            startCondition[i] = ((int) (Math.random() * p)) + 1;
        }

        int[] exitArray = new int[5];
        int[] endCondition = new int[5];

        for (int i = 0; i < 5; i++) {
            endCondition[i] = getCondition(enterArray[i], startCondition[i]);
            exitArray[i] = getExitValue(enterArray[i], startCondition[i]);
        }

        System.out.println("Начальное состояние" + Arrays.toString(startCondition));
        System.out.println("Конечное состояние" + Arrays.toString(endCondition));
        System.out.println("Входное слово" + Arrays.toString(enterArray));
        System.out.println("Выходной алфавит" + Arrays.toString(exitArray));
        System.out.println("Статистика: Выходные значения '1': " + statist1 + "; Выходные значения '2': " + statist2);
    }

    private static int getExitValue(int enterValue, int startCondition) {
        int exitValue = 0;
        if(startCondition == 1) {
            switch (enterValue) {
                case 1:
                    exitValue = 2;
                    break;
                case 2:
                    exitValue = 1;
                    break;
                case 3:
                    exitValue = 3;
                    break;
            }
        } else {
            switch (enterValue) {
                case 1:
                    exitValue = 3;
                    break;
                case 2:
                    exitValue = 4;
                    break;
                case 3:
                    exitValue = 2;
                    break;
            }
        }

        return exitValue;
    }

    private static int getCondition(int enterValueX, int startConditionZ) {
        int endCondition = 0;
        if(startConditionZ == 1) {
            switch (enterValueX) {
                case 1:
                    endCondition = 1;
                    statist1++;
                    break;
                case 2:
                    endCondition = 2;
                    statist2++;
                    break;
                case 3:
                    endCondition = 2;
                    statist2++;
                    break;
            }
        } else {
            switch (enterValueX) {
                case 1:
                    endCondition = 1;
                    statist1++;
                    break;
                case 2:
                    endCondition = 1;
                    statist1++;
                    break;
                case 3:
                    endCondition = 2;
                    statist2++;
                    break;
            }
        }

        return endCondition;
    }
}
