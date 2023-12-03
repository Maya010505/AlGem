package StepanovLuchshiy;

public class Main {
    public static void main(String[] args) {
        double[][] a1 = {{1, 2, 1, 2, 2}, {3, 3, 2, -1, 2}, {9, 10, 5, -1, -2}, {1, 2, -1, -1, 2}, {8, 9, 4, 0, 2}};
        double[][] a2 = {{3}, {0}, {0}, {-3}, {-3}};

        Matrix matrix_a1 = new Matrix(a1);
        Matrix matrix_a2 = new Matrix(a2);
        try {
            matrix_a1.solve(matrix_a2).printMatrix();
        } catch (NullPointerException e) {
            System.out.println("null");
        }

        double[][] a5 = {{2, 1, 1}, {1, 1, 1}, {0, 1, 1}};
        double[][] a6 = {{1}, {1}, {1}};

        Matrix matrix_a5 = new Matrix(a5);
        Matrix matrix_a6 = new Matrix(a6);
        try {
            matrix_a5.solve(matrix_a6).printMatrix();
        } catch (NullPointerException e) {
            System.out.println("null");
        }

        double[][] a7 = {{21, 40, -13, 48}, {19, -18, 20, 38}, {-65, 65, -65, 65}, {-38, 36, -40, -76}};
        double[][] a8 = {{-50}, {33}, {16}, {84}};

        Matrix matrix_a7 = new Matrix(a7);
        Matrix matrix_a8 = new Matrix(a8);
        System.out.println(matrix_a7.solve(matrix_a8));

        //Matrix qwerty1 = Matrix.enterMatrix();
        //qwerty1.printMatrix();
        //Matrix qwerty2 = Matrix.enterMatrix();
        //qwerty1.multiply(qwerty2).printMatrix();

        Matrix qwerty3 = Matrix.enterMatrix();
        qwerty3.printMatrix();
        System.out.println(qwerty3.rank());

        //Matrix qwerty4 = Matrix.enterMatrix();
        //qwerty4.printMatrix();
        //qwerty4.transposeMatrix();
        //qwerty4.printMatrix();
    }
}