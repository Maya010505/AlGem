package StepanovLuchshiy;

class GaussianMethod {
    public double accuracySolving;

    GaussianMethod(double accuracySolving) {
        this.accuracySolving = accuracySolving;
    }

    void forward(Matrix coefficientMatrix, Matrix additionalMatrix) {
        int row = 0;
        int col = 0;

        while (col < coefficientMatrix.colsCount && row < coefficientMatrix.rowsCount) {
            if (Math.abs(coefficientMatrix.array[row][col]) < accuracySolving) {
                int checkNullRow = -1;

                for (int i = row + 1; i < coefficientMatrix.rowsCount; i++) {
                    if (Math.abs(coefficientMatrix.array[i][col]) >= accuracySolving) {
                        checkNullRow = i;
                    }
                }

                if (checkNullRow == -1) {
                    col++;
                    continue;
                }

                swapRows(row, checkNullRow, coefficientMatrix);
                if (additionalMatrix != null) {
                    swapRows(row, checkNullRow, additionalMatrix);
                }
            }

            for (int i = row + 1; i < coefficientMatrix.rowsCount; i++) {
                double k = (coefficientMatrix.array[i][col] / coefficientMatrix.array[row][col]) * -1;

                double[] resultCoef = multiplicationRow(coefficientMatrix.array[row], k);
                summRow(coefficientMatrix.array[i], resultCoef);
                if (additionalMatrix != null) {
                    double[] resultAd = multiplicationRow(additionalMatrix.array[row], k);
                    summRow(additionalMatrix.array[i], resultAd);
                }
            }
            row++;
            col++;
        }
    }

    public void swapRows(int currentRow, int replacementRow, Matrix matrix) {
        double[] value = matrix.array[currentRow];
        matrix.array[currentRow] = matrix.array[replacementRow];
        matrix.array[replacementRow] = value;
    }

    public static double[] multiplicationRow(double[] row, double k) {
        double[] res = new double[row.length];
        for (int i = 0; i < row.length; i++) {
            res[i] = row[i] * k;
        }
        return res;
    }

    public void summRow(double[] rowAdditional, double[] rowBellow) {
        for (int i = 0; i < rowAdditional.length; i++) {
            rowAdditional[i] += rowBellow[i];
        }
    }

    void backward(Matrix coefficientMatrix, Matrix additionalMatrix) {
        int row = coefficientMatrix.rowsCount - 1;
        int col = coefficientMatrix.colsCount - 1;

        while (col >= 0 && row >= 0) {
            if (Math.abs(coefficientMatrix.array[row][col]) < accuracySolving) {
                int checkNullRow = -1;

                for (int i = row - 1; i >= 0; i--) {
                    if (Math.abs(coefficientMatrix.array[i][col]) >= accuracySolving) {
                        checkNullRow = i;
                    }
                }

                if (checkNullRow == -1) {
                    col--;
                    continue;
                }
                swapRows(row, checkNullRow, coefficientMatrix);
                if (additionalMatrix != null) {
                    swapRows(row, checkNullRow, additionalMatrix);
                }
            }

            for (int i = row - 1; i >= 0; i--) {
                double k = (coefficientMatrix.array[i][col] / coefficientMatrix.array[row][col]) * -1;

                double [] resultCoef = multiplicationRow(coefficientMatrix.array[row], k);
                summRow(coefficientMatrix.array[i], resultCoef);
                if (additionalMatrix != null) {
                    double [] resultAd = multiplicationRow(additionalMatrix.array[row], k);
                    summRow(additionalMatrix.array[i], resultAd);
                }
            }
            row--;
            col--;
        }
    }
}