package StepanovLuchshiy;

import java.util.Scanner;

public class Matrix {
    public final double accuracySolving = 0.00000000000001;
    public double[][] array;
    public int rowsCount;
    public int colsCount;

    public Matrix(int rowsCount, int colsCount, double[][] array) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.array = array;
    }

    public Matrix(int rowsCount, int colsCount) {
        this(rowsCount, colsCount, new double[rowsCount][colsCount]);
    }

    public Matrix(double[][] array) {
        this(array.length, array[0].length, array);
    }

    public Matrix(Matrix matrix) {
        this(matrix.rowsCount, matrix.colsCount, copyArray(matrix.array));
    }

    public static Matrix enterMatrix() {
        Scanner sc = new Scanner(System.in);
        int rowsCount = enterCount("рядов");
        int colsCount = enterCount("столбцов");

        Matrix matrix = new Matrix(rowsCount, colsCount);
        String option;
        while (true) {
            System.out.print("Как бы вы хотели ввести матрицу? Введите 'Поэлементно' или 'Построчно': ");
            option = sc.next();
            if (option.equals("Поэлементно") || option.equals("Построчно")) {
                break;
            } else {
                System.out.println("Вы ввели неправильное значение способа ввода матрицы. Попробуйте еще раз");
            }
        }

        if (option.equals("Поэлементно")) {
            enterByElements(matrix);
        } else {
            enterByRows(matrix);
        }

        return matrix;
    }

   public static int enterCount(String enterRowsOrCols) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите количество " + enterRowsOrCols + " в матрице: ");
        String input = sc.next();
        try {
            int count = Integer.parseInt(input);
            if (count < 1) {
                System.out.println("Вы ввели недопустимое значение для количества " + enterRowsOrCols +
                        " в матрице. Попробуйте еще раз");
                return enterCount(enterRowsOrCols);
            }
            return count;
        } catch (NumberFormatException e) {
            System.out.println("Вы ввели недопустимое значение для количества " + enterRowsOrCols +
                    " в матрице. Попробуйте еще раз");
            return enterCount(enterRowsOrCols);
        }
    }

    public static void enterByElements(Matrix matrix) {
        for (int i = 0; i < matrix.rowsCount; i++) {
            for (int j = 0; j < matrix.colsCount; j++) {
                enterElement(matrix, i, j);
            }
        }
    }

    public static void enterElement(Matrix matrix, int row, int col) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите элемент на позиции (" + (row + 1) + ", " + (col + 1) + "): ");

        String value = sc.next();
        try {
            matrix.array[row][col] = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.print("Вы ввели недопустимое значение для элемента. Попробуйте еще раз");
            enterElement(matrix, row, col);
        }
    }

    public static void enterByRows(Matrix matrix) {
        for (int i = 0; i < matrix.rowsCount; i++) {
            enterRow(matrix, i);
        }
    }

    public static void enterRow(Matrix matrix, int row) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите элементы " + (row + 1) + " строки: ");
        String something = sc.nextLine();
        String[] line = something.trim().split("\\s+");

        if (line.length != matrix.colsCount) {
            System.out.println("Вы ввели недопустимое количество элементов строки. Попробуйте еще раз " + line.length);
            enterRow(matrix, row);
            return;
        }
        for (int i = 0; i < matrix.colsCount; i++) {
            try {
                matrix.array[row][i] = Double.parseDouble(line[i]);
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели недопустимое значение для элемента строки. Попробуйте еще раз ввести строку");
                enterRow(matrix, row);
                return;
            }
        }
    }

    public void printMatrix() {
        Scanner sc = new Scanner(System.in);
        int printAccuracy;
        System.out.print("Введите точность вывода элементов матрицы: ");
        String input = sc.next();
        try {
            printAccuracy = Integer.parseInt(input);
            if (printAccuracy < 0) {
                System.out.println("Вы ввели недопустимое значение для точности вывода элементов матрицы. " +
                        "Попробуйте ввести еще раз");
                printMatrix();
                return;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Вы ввели недопустимое значение для точности вывода элементов матрицы. " +
                    "Попробуйте ввести еще раз");
            printMatrix();
            return;
        }
        System.out.println("Матрица [" + rowsCount + " * " + colsCount + "]:");
        for (double[] row: array) {
            for (double element: row) {
                System.out.printf("%."+ printAccuracy + "f ", element);
            }
            System.out.println();
        }
    }

    public Matrix multiply(Matrix anotherMatrix) {
        Scanner sc = new Scanner(System.in);
        if (colsCount == anotherMatrix.rowsCount) {
            return this.multiplicationMatrix(anotherMatrix);
        } else if (rowsCount == anotherMatrix.colsCount) {
            System.out.print("Невозможно прямое умножение. Могу предложить умножить матрицы в обратном порядке: ");
            String choose = sc.next();
            if (choose.equalsIgnoreCase("да") || choose.equalsIgnoreCase("д") ||
                    choose.equalsIgnoreCase("yes") || choose.equalsIgnoreCase("y")) {
                return anotherMatrix.multiplicationMatrix(this);
            } else {return null;}
        } else {return null;}
    }
    public Matrix multiplicationMatrix(Matrix anotherMatrix) {
        Matrix matrixMult = new Matrix(rowsCount, anotherMatrix.colsCount);

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < anotherMatrix.colsCount; j++) {
                matrixMult.array[i][j] = 0;
                for (int k = 0; k < colsCount; k++) {
                    matrixMult.array[i][j] += array[i][k] * anotherMatrix.array[k][j];
                }
            }
        }
        return matrixMult;
    }

    public void transposeMatrix() {
        double[][] transposed = new double[colsCount][rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                transposed[j][i] = array[i][j];
            }
        }

        array = transposed;
        int value = rowsCount;
        rowsCount = colsCount;
        colsCount = value;
    }

    public int rank() {
        Matrix matrixVr = new Matrix(this);
        int rank = 0;

        GaussianMethod gauss = new GaussianMethod(accuracySolving);
        gauss.forward(matrixVr, null);
        for (int i = 0; i < matrixVr.rowsCount; i++) {
            int checkNullRow = -1;
            for (double elem : matrixVr.array[i]) {
                if (Math.abs(elem) >= accuracySolving) {
                    checkNullRow = 0;
                    break;
                }
            }
            if (checkNullRow != -1) {
                rank++;
            }
        }
        return rank;
    }

    public Matrix solve(Matrix matrix) {
        GaussianMethod gauss = new GaussianMethod(accuracySolving);
        Matrix resultMatrix = new Matrix(matrix);
        gauss.forward(this, resultMatrix);

        int checkNullRow = -1;
        for (double elem : array[this.rowsCount - 1]) {
            if (Math.abs(elem) >= accuracySolving) {
                checkNullRow = 0;
                break;
            }
        }
        if (checkNullRow == -1 && Math.abs(resultMatrix.array[this.rowsCount - 1][0]) >= accuracySolving) {
            System.out.println("СЛАУ не имеет решений");
            return null;
        }

        if (checkNullRow == -1 && Math.abs(resultMatrix.array[this.rowsCount - 1][0]) < accuracySolving
        ) {
            System.out.println("СЛАУ имеет бесконечное количество решений");
            return null;
        }

        gauss.backward(this, resultMatrix);

        identityMatrix(this, resultMatrix);

        return resultMatrix;
    }

    private void identityMatrix(Matrix coefficientMatrix, Matrix additionalMatrix) {
        for (int i = 0; i < coefficientMatrix.rowsCount; i++) {
            if (Math.abs(coefficientMatrix.array[i][i]) >= accuracySolving) {
                double k = 1 / coefficientMatrix.array[i][i];
                coefficientMatrix.array[i] = GaussianMethod.multiplicationRow(coefficientMatrix.array[i], k);
                additionalMatrix.array[i] =  GaussianMethod.multiplicationRow(additionalMatrix.array[i],k);
            }
        }
    }
    public static double[][] copyArray(double[][] array) {
        double[][] copyArray = new double[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++)
                copyArray[i][j] = array[i][j];
        }
        return copyArray;
    }
}