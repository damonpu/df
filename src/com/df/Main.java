package com.df;

public class Main {

    public static void main(String[] args) {
        double[][] array = {{1,2,2,2},{2,4,6,8},{3,6,8,10}};
//        double[][] array = {{2,7},{3,8},{4,9}};
//        double [][] result = {{1,6},{2,0}};
        Matrix matrix = new Matrix(array);
//        System.out.println(matrix);
//        double[] x = matrix.solve(result);
//        Matrix i = matrix.getInverse();
        System.out.print(matrix.rref());
       // for(int i = 0; i<x.length;i++) {
       //     System.out.print(x[i]);
      //      System.out.print(" ");
      //  }
    }
}
