import java.awt.*;

public class Force {
    private final PointA pointA;
    private final Vector v;

    public Vector getV() {
        return v;
    }

    public Force(PointA pointA, double x, double y) {
        this.pointA = pointA;
        this.v = new Vector(x,y);
    }

    public PointA getPointA() {
        return pointA;
    }

    public void paint(Graphics2D g){ // paints force
        int a = (int) (pointA.getY()-v.sinf*10+5);
        int b = (int) (pointA.getX()-v.cosf*10+5);
        int c = (int) (pointA.getY()-v.sinf*95+5);
        int d = (int) (pointA.getX()-v.cosf*95+5);
        g.drawLine(b,a,d,c);
        int h = (int) (pointA.getY()-v.sinf*30+5);
        int f = (int) (pointA.getX()-v.cosf*30+5);
        int l = (int) (h+ v.cosf*10);
        int m = (int) (f - v.sinf*10);
        g.drawLine(b,a, m, l);
        l = (int) (h- v.cosf*10);
        m = (int) (f + v.sinf*10);
        g.drawLine(b,a, m, l); // draws arrow
        double i = v.l*1000;
        i = Math.round(i);
        i = i/1000;
        if(d>b){ // draws string
            if(c>a){
                g.drawString(String.valueOf(v.l),d-4,c+4);
            }
            else {
                g.drawString(String.valueOf(v.l),d-4,c-4);
            }
        }
        else {
            if (c>a){
                g.drawString(String.valueOf(i),d+4,c+4);
            }
            else {
                g.drawString(String.valueOf(i),d+4,c-4);
            }
        }
    }
}
