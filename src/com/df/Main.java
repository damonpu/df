package com.df;

public class Main {

    public static void main(String[] args) {
//        double[][] array = {{2,7},{3,8},{4,9}};
        double[][] array = {{2,3,11,5},{1,1,5,2},{2,1,3,2},{1,1,3,4}};
//    	double[][] array = {{0,1,2},{1,1,4},{2,-1,0}};
//    	double[][] array = {{1,1,2},{-1,2,0},{1,1,3}};
//        double [][] result = {{1,6},{2,0}};
        Matrix matrix = new Matrix(array);
//        System.out.println(matrix);
//        double[] x = matrix.solve(result);
//        Matrix i = matrix.getInverse();
        Matrix t  = matrix.trsanpose();
        System.out.println(t);
        System.out.println(matrix.multiply(t));
//        System.out.print(matrix.multiply(new Matrix(result)));
//        for(int i = 0; i<x.length;i++) {
       //     System.out.print(x[i]);
      //      System.out.print(" ");
      //  }
    }
}
