package com.df;

/**
 * Created by Damon Pu on 2016/9/5.
 */
public class Matrix {
    private double[][] matrix = null;

    public static double[][] getIdentity(double[][] matrix) {
        double[][] identity = new double[matrix.length][matrix.length];
        for(int row = 0; row < matrix.length; row ++) {
            for(int column = 0; column < matrix[row].length; column ++) {
                if(row == column) {
                    identity[row][column] = 1;
                } else {
                    identity[row][column] = 0;
                }
            }
        }
        return identity;
    }

    public Matrix(){
    }

    public Matrix(double[][] array) {
        this.matrix = array;
    }

    private double[][] cloneA() {
        double[][] a = new double[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i ++) {
            for (int j = 0; j <matrix[i].length; j ++){
                a[i][j] = matrix[i][j];
            }
        }
        return a;
    }

    public double[] solve(double[] b) {
        double[][] a = cloneA();
        for(int n = 0; n < a.length; n++) {
            int exchangeRowIndex = exchangeRow(a, n);
            if(n!=exchangeRowIndex) {
                double tempValue = b[n];
                b[n] = b[exchangeRowIndex];
                b[exchangeRowIndex] = tempValue;
            }
            elimination(a, n, b);
        }

        return substitution(a, b);
    }

    public int exchangeRow(double[][] a, int n) {
        int exchangeRow = n;
        double[] tempRow = a[n];
        if(a[n][n] == 0) {
            for(int row = n+1; row < a.length; row ++) {
                if(a[row][n] !=0) {
                    tempRow = a[row];
                    exchangeRow = row;
                    break;
                }
            }
            a[exchangeRow] = a[n];
            a[n] = tempRow;
        }
        return exchangeRow;

    }

    public void elimination(double[][] a, int n, double[] b) {
        for(int row = n+1; row <a.length; row ++) {
            if(a[row][n] == 0) {
                continue;
            }
            double index = a[row][n]/a[n][n];
            for(int column = n; column <a[row].length; column ++) {
                a[row][column] = a[row][column] - index*a[n][column];
            }
            b[row] = b[row] - b[n]*index;
        }
    }

    public void elimination2(double[][] a, int n, double[][] b) {
        for(int row = n+1; row <a.length; row ++) {
            if(a[row][n] == 0) {
                continue;
            }
            double index = a[row][n]/a[n][n];
            for(int column = n; column <a[row].length; column ++) {
                a[row][column] = a[row][column] - index*a[n][column];
            }
            if(b != null) {
                for (int i = 0; i < b[row].length; i++) {
                    b[row][i] = b[row][i] - b[n][i] * index;
                }
            }
        }
    }

    public double[] substitution(double[][] a, double[] b) {
        double[] x = new double[a[0].length];
        for(int row = a.length-1; row >= 0 ; row --) {
            double sum = 0;
            for(int i = 0; i < a[row].length; i++) {
                if(i != row) {
                    sum = sum + a[row][i]* x[i];
                }
            }
            x[row] = (b[row]-sum)/a[row][row];
        }
        return x;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if(matrix == null) {
            return sb.toString();
        }
        for(int i = 0; i < matrix.length; i ++) {
            for (int j = 0; j <matrix[i].length; j ++){
                sb.append(matrix[i][j]);
                if(j == matrix[i].length-1) {
                    sb.append("\r\n");
                } else {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    public Matrix getInverse() {
        double[][] b = Matrix.getIdentity(this.matrix);
        double[][] a = this.cloneA();
        for(int n = 0; n < a.length; n++) {
            int exchangeRowIndex = exchangeRow(a, n);
            if(n!=exchangeRowIndex) {
                double[] tempValue = b[n];
                b[n] = b[exchangeRowIndex];
                b[exchangeRowIndex] = tempValue;
            }
            elimination2(a, n, b);
        }
        for(int n = a.length-1; n >= 0; n --) {
            inverseElimination(a, n, b);
        }
        return new Matrix(b);
    }

    public void inverseElimination(double[][] a, int n, double[][] b) {
        if(n != 0) {
            double povit=0;
            int povitColumn = 0;
            for (int i = 0; i<a[n].length; i++) {
                if(a[n][i] != 0) {
                    povit = a[n][i];
                    povitColumn = i;
                    break;
                }
            }
            if(povit == 0) {
                return;
            }
            for (int row = n - 1; row >= 0; row--) {
                if (a[row][povitColumn] == 0) {
                    continue;
                }
                double index = a[row][povitColumn] / povit;
                for (int column = a[row].length-1; column >= 0; column--) {
                    a[row][column] = a[row][column] - index * a[n][column];
                }
                if(b != null) {
                    for (int i = 0; i < b[row].length; i++) {
                        b[row][i] = b[row][i] - b[n][i] * index;
                    }
                }
            }
        }

        if(a[n][n] !=1 && b != null) {
            double index = a[n][n];
            a[n][n] = 1;
            for (int i = 0; i < b[n].length; i++) {
                b[n][i] = b[n][i]/index;
            }
        }
    }

    public Matrix multiply(Matrix matrix) {
        double[][] a = this.cloneA();
        double[][] b = matrix.cloneA();
        if(a[0].length != b.length) {
            return null;
        }
        double[][] c = new double[a.length][b[0].length];
        for(int row = 0; row< c.length; row++) {
            for(int column = 0; column <c[row].length; column ++) {
                c[row][column] = 0;
                for(int i=0; i<b.length;i++) {
                    c[row][column] = c[row][column] + a[row][i]*b[i][column];
                }
            }
        }
        return new Matrix(c);
    }

    public Matrix transpose() {
        double [][] t = new double[matrix[0].length][matrix.length];
        for(int row = 0; row< t.length; row++) {
            for (int column = 0; column < t[row].length; column++) {
                t[row][column] = matrix[column][row];
            }
        }
        return new Matrix(t);
    }

    public Matrix rref() {
        double[][] r = this.cloneA();
        for(int n = 0; n < r.length; n++) {
            int exchangeRowIndex = exchangeRow(r, n);
            elimination2(r, n, null);
        }
        for(int n = r.length-1; n >= 0; n --) {
            inverseElimination(r, n, null);
        }
        return new Matrix(r);
    }
}
