

public class SolveLDL {
    private static final double EPSILON = 1e-14;
    
    // norm of matrix A
    private static double normofA(double[][] A)
    {
        int N = A.length;
        
        double maxA=0.;
        
        for (int i=0; i<N; i++){
            if(Math.abs(A[i][i])>maxA )
            {
                maxA=Math.abs(A[i][i]);
            }
        }
        
        return maxA;
    }

    // is symmetric
    private static boolean isSymmetric(double[][] A) {
        int N = A.length;
       
        double maxA = normofA(A);

        if ( maxA == 0 ) return true;
   
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (Math.abs((A[i][j] -  A[j][i]) / maxA)> EPSILON)
                    return false;
            }
        }
        return true;
    }

    // is square
    private static boolean isSquare(double[][] A) {
        int N = A.length;
        for (int i = 0; i < N; i++) {
            if (A[i].length != N) return false;
        }
        return true;
    }
    
    // Factorization symetrix matrix A = L*D*L^T
    // LD ... lower triangular part is L and D is diagonal part of matrix LD
    private static double[][] LDLfactorization( double[][] A)
    {
        int N  = A.length;
        double[][] LD = new double[N][N];
        
        for (int j=0; j<N ; j++) {
            double sumk = 0. ; 
            
            for (int k = 0 ; k<j ; k++){
                sumk = sumk + LD[j][k]*LD[j][k]* LD[k][k];
            }
            
            LD[j][j]=A[j][j]-sumk;
            
            if(Math.abs(LD[j][j])>1e-14)
            {
                
                for (int i = j+1 ; i<N ; i++)
                {
                    sumk = 0.;
                    for (int k = 0 ; k<j ; k++){
                        sumk = sumk + LD[i][k]*LD[j][k]* LD[k][k];
                    }
                    
                    LD[i][j]=(A[i][j]-sumk)/LD[j][j];
                    
                }                
            }            
        }
            
        return LD;
    }
    
    // solve system L*D*L^t*x=y
    private static double[] solvephaseLDL(double[][] LD, double[] y)
    {
        int N  = LD.length;
        double[] x = y;
        
        double xl;
        
        // forward proces
        
        for (int i=0;i<N;i++)
        {
            xl=0.;
        
            for (int j=0; j<i ; j++) 
                xl = xl + x[j]*LD[i][j];
            
            x[i]=x[i]-xl;
        }
        
        
        // divide by diagonal
        
        for (int i=0;i<N;i++)
            x[i]=x[i]/LD[i][i];
        
        
        // backward process
        for (int i=N-1; i>=0; i--)
        {
            xl = 0.;
            
            for (int j=i+1; j<N ; j++) 
                xl = xl + x[j]*LD[j][i];
            
            x[i]=x[i]-xl;
        }
        
        return x;
    }


    // solve symetric system A * x = y by LDL factorization
    public static double[] LDLsolve(double[][] A,double[] y) {
        if (!isSquare(A)) 
            throw new RuntimeException("Matrix is not square");
        
        if (!isSymmetric(A)) 
            throw new RuntimeException("Matrix is not symmetric");
        
        if ( A.length != y.length )
            throw new RuntimeException("matrix A and vector y is not same size");
        
        int N  = A.length;
        
        // factorization
        double[][] LD = LDLfactorization(A);
        
        // check for singularty
        double normLD = normofA(LD);
        
        for (int i = 0; i<N ;i++)
            if (Math.abs(LD[i][i])/normLD < EPSILON)
                throw new RuntimeException("matrix A is singular");
        
        return solvephaseLDL(LD,y);
    }

}
