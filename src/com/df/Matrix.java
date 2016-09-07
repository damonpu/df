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

    public double[][] cloneMartix() {
        double[][] a = new double[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i ++) {
            for (int j = 0; j <matrix[i].length; j ++){
                a[i][j] = matrix[i][j];
            }
        }
        return a;
    }

    public double[] solve(double[] b) {
        double[][] a = cloneMartix();
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

    public void elimination(double[][] a, int n, double[][] b) {
        for(int row = n+1; row <a.length; row ++) {
            if(a[row][n] == 0) {
                continue;
            }
            double index = a[row][n]/a[n][n];
            for(int column = n; column <a[row].length; column ++) {
                a[row][column] = a[row][column] - index*a[n][column];
            }
            for(int i = 0; i<b[row].length; i++) {
                b[row][i] = b[row][i] - b[n][i] * index;
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
        double[][] a = this.cloneMartix();
        for(int n = 0; n < a.length; n++) {
            int exchangeRowIndex = exchangeRow(a, n);
            if(n!=exchangeRowIndex) {
                double[] tempValue = b[n];
                b[n] = b[exchangeRowIndex];
                b[exchangeRowIndex] = tempValue;
            }
            elimination(a, n, b);
        }
        for(int n = a.length-1; n >= 0; n --) {
            inverseElimination(a, n, b);
        }
        return new Matrix(b);
    }

    public void inverseElimination(double[][] a, int n, double[][] b) {
        if(n != 0) {
            for (int row = n - 1; row >= 0; row--) {
                if (a[row][n] == 0) {
                    continue;
                }
                double index = a[row][n] / a[n][n];
                for (int column = n; column >= 0; column--) {
                    a[row][column] = a[row][column] - index * a[n][column];
                }
                for (int i = 0; i < b[row].length; i++) {
                    b[row][i] = b[row][i] - b[n][i] * index;
                }
            }
        }

        if(a[n][n] !=1) {
            double index = a[n][n];
            a[n][n] = 1;
            for (int i = 0; i < b[n].length; i++) {
                b[n][i] = b[n][i]/index;
            }
        }
    }

    public Matrix multiply(Matrix matrix) {
        double[][] a = this.cloneMartix();
        double[][] b = matrix.cloneMartix();
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
    
    public Matrix trsanpose() {
    	double[][] t = new double[this.matrix[0].length][this.matrix.length];
    	
    	for(int i = 0; i < matrix.length; i ++) {
            for (int j = 0; j <matrix[i].length; j ++){
            	t[i][j] = matrix[j][i];
            }
        }
    	
    	return new Matrix(t);
    }
}
