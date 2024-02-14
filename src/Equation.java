

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class Equation {
    private final Grid grid;

    public Equation(Grid grid) {
        this.grid = grid;
    }

    private double[][] matrixMultiplyByMatrix(double[][] m, double[][] n, int rowM, int columnM, int columnn){ //perform matrix multiplication
        double[][] res = new double[rowM][columnn];
        for (int i = 0; i < rowM; i++) {
            for (int j = 0; j < columnn; j++) {
                for (int k = 0; k < columnM; k++) {
                    res[i][j]+=m[i][k]*n[k][j];
                }
            }
        }
        return res;
    }

    private static void zeros(boolean[] mt, int n){ // fills out false
        for (int i = 0; i < n; i++) {
            mt[i]=false;
        }
    }
    private double[][] getMatOfUnitVector(Stick i){ //get matrix of directions
        double[][] n = new double[4][1];
        n[0][0] = -i.getV().cosf;
        n[1][0] = -i.getV().sinf;
        n[2][0] = i.getV().cosf;
        n[3][0] = i.getV().sinf;
        return n;
    }
    private static void matrixMultiplyByValue(double value, double[][] matrix, int row, int column){ // multiplies matrix by value
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j]*=value;
            }
        }
    }

    private double[][] getMatrixOfStiffness(Stick i){
        double[][] n = getMatOfUnitVector(i);
        double[][] nt = transpose(4,1,n);
        double[][] mS = matrixMultiplyByMatrix(n,nt,4,1,4);
        matrixMultiplyByValue(i.prures*(i.pruznost/i.getV().l),mS,4,4);
        return mS;
    }

    private double getStrengthInElement(Stick i, double[] ue){ // calculate strengths in xy
        double[][] n = getMatOfUnitVector(i);
        double p = 0;
        for (int j = 0; j < 4; j++) {
            p+=ue[j]*n[j][0];
        }
        return i.prures*i.pruznost/i.getV().l*p;
    }

    private int[][] transpose(int row, int column, int[][]matrix){ // return transposed matrix
        int[][] transpose= new int[column][row];
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        return transpose;
    }

    private double[][] transpose(int row, int column, double[][]matrix){ // return transposed matrix
        double[][] transpose= new double[column][row];
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        return transpose;
    }

    private double[][] reshapeToXY(double[] ug, int np){ //  divides values of xy into individual columns - np number of pointA
        double[][] ugn= new double[np][2];
        for (int i = 0; i < np; i++) {
            ugn[i][0] = ug[i*2];
            ugn[i][1] = ug[i*2+1];
        }
        return ugn;
    }

    private double getMaxDeformation(double[][] ugn, int np){  //returns max deformation
        double max=0;
        for (int i = 0; i < np; i++) {
            double a = Math.sqrt(ugn[i][0]* ugn[i][0]+ ugn[i][1]* ugn[i][1]);
            if(max<a){
                max=a;
            }
        }
        return max;
    }
    private int getMax(int[] a){ // returns maximum coordinate in dimension
        int max=a[0];
        for (int i = 1; i < a.length; i++) {
            if(max<a[i]){
                max=a[i];
            }
        }
        return max;
    }

    private int getMin(int[] a){ // returns maximum coordinate in dimension
        int min = a[0];
        for (int i = 1; i < a.length; i++) {
            if(min>a[i]){
                min = a[i];
            }
        }
        return min;
    }

    private int[] getDimension(){ // returns size of construction
        int[][] mpoints = new int[grid.getPoints().size()][2];
        int i = 0;
        for(PointA a : grid.getPoints()){
            mpoints[i][0] = a.getX();
            mpoints[i][1] = a.getY();
            i++;
        }
        int[] ret = new int[2];
        i = 0;
        mpoints = transpose(grid.getPoints().size(), 2, mpoints);
        for (int[] b : mpoints){
            ret[i] = getMax(b)-getMin(b);
            i++;
        }
        return ret;
    }

    private PointA searchSameId(PointA s, ArrayList<PointA> points){ //search for same ids and if finds one throws error
        for(PointA a: points){
            if(s.getId()==a.getId()){
                return a;
            }
        }
        throw new RuntimeException("error in id");
    }

    public void getResult(){
        int np = grid.getPoints().size();
        double[][] kg = new double[np*2][np*2]; // global matrix of stiffness
        for (Stick i : grid.getSticks()){  // calculate stiffness in sticks and stack values of stiffness into global matrix of stiffness
            int[] id = new int[2];
            id[0]=i.getA().getId();
            id[1]=i.getB().getId(); // gets ids end pointAs
            double[][] ke = getMatrixOfStiffness(i); // matrix of stiffness
            int[] ide = {2*id[0], 2*id[0]+1, 2*id[1], 2*id[1]+1}; // decomposes by dimensions
            int l = 0;
            for(int u: ide){
                int j =0;
                for(int m:ide){
                    kg[u][m] += ke[j][l]; // stack values into global matrix
                    j++;
                }
                l++;
            }
        }

        double[] fgG = new double[np*2]; //global matrix of forces
        for (Force f : grid.getForces()){  // decomposes forces by dimensions into global matrix fo force
            fgG[2*f.getPointA().getId()] += f.getV().lx;
            fgG[2*f.getPointA().getId()+1] += f.getV().ly;
        }

        boolean[] uset = new boolean[np*2];
        zeros(uset,np*2); // get global matrix fo Zakladnas and decomposes by dimenssions
        for (Zaklada z: grid.getZakladas()){
            uset[2 * z.getA().getId()] = !z.isX();
            uset[(2 * z.getA().getId())+1] = !z.isY();
        }
        double[] ug = new double[np*2];

        int nsv = 0; // number of dimensions of non-zakladnas
        for(boolean i : uset){
            if(i){
                nsv++;
            }
        }
        double[][] kgr = new double[nsv][nsv];  //reduced matrix of stiffness
        double[] fgr = new double[nsv]; // reduced matrix of forces
        int k = 0;
        for(int i = 0; i<2*np; i++){ // reducing both of matrix
            if(uset[i]) {
                int l = 0;
                for (int j = 0; j < 2 * np; j++) {
                    if (uset[j]){
                        kgr[k][l]= kg[i][j];
                        l++;
                    }
                }
                fgr[k] = fgG[i];
                k++;
            }
        }
        double[] ugr;
        try {
            ugr = SolveLDL.LDLsolve(kgr, fgr); //getting deformation matrix
        }
        catch (RuntimeException e){
            if(!Objects.equals(e.getMessage(), "matrix A is singular")) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "construction is not possible", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        k=0;
        for(int i = 0; i<2*np; i++){
            if(uset[i]) {
                ug[i] = ugr[k]; //reducing deformation matrix
                k++;
            }
        }

        int ne = grid.getSticks().size(); // gets number of stick
        double[] fe = new double[ne]; //matrix of strength in element
        for (int j = 0; j < ne; j++){
            Stick s = grid.getSticks().get(j);
            int[] id = {s.getA().getId(), s.getB().getId()}; // gets ids end pointAs
            int[] ide = {2*id[0], 2*id[0]+1, 2*id[1], 2*id[1]+1}; //// decomposes by dimensions
            double[] ue = new double[4]; //local matrix of deformation
            for (int i = 0; i < 4; i++) {
                ue[i]=ug[ide[i]];
            }
            fe[j]=getStrengthInElement(s,ue);
        }

        double[][] ugn =reshapeToXY(ug, np); //reshaped deformation matrix
        double maxug = getMaxDeformation(ugn, np);
        int[] maxmin = getDimension();
        double lkon = Math.sqrt(maxmin[0]*maxmin[0]+maxmin[1]*maxmin[1]); //diagonal line

        ArrayList<PointA> aPoints = new ArrayList<>(); //gets new construction
        int i = 0;
        for (PointA a:grid.getPoints()){
            PointA n = new PointA(a);
            n.setX((int) Math.round(n.getX() + 0.1* lkon/maxug*ugn[i][0]));
            n.setY((int) Math.round(n.getY() + 0.1* lkon/maxug*ugn[i][1])); //expanding area of deformation
            i++;
            aPoints.add(n);
        }
        grid.setAnimatedPoints(aPoints);

        ArrayList<Stick> aStick = new ArrayList<>(); // same here
        int j = 0;
        for (Stick a :grid.getSticks()){
            Stick b = new Stick(searchSameId(a.getA(),aPoints), searchSameId(a.getB(), aPoints));
            b.setForce(""+(Math.round(fe[j]*100)/100));
            aStick.add(b);
            j++;
        }
        grid.setAnimatedStick(aStick); //set for animation

    }
}
