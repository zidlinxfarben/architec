public class Vector { // calculates unit vector and saves it
    public double l;
    public double lx;
    public double ly;
    public double sinf;
    public double cosf;


    public Vector(int x1, int y1, int x2, int y2) {
        lx = x1-x2;
        ly = y1-y2;
        l = Math.sqrt(lx*lx+ly*ly);
        cosf=lx/l;
        sinf=ly/l;
    }

    public Vector(double lx, double ly) {
        this.lx = lx;
        this.ly = ly;
        l = Math.sqrt(lx*lx+ly*ly);
        cosf=lx/l;
        sinf=ly/l;
    }
}
