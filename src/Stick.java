import java.awt.*;

public class Stick{
    private final PointA a;
    private final PointA b;
    public Color color = Color.black;
    public Color textColor = new Color(62, 72, 51, 255);
    public double pruznost = 1000;
    public double prures = 100;
    private String force;
    private Vector v;


    public Stick(PointA a, PointA b) {
        this.a = a;
        this.b = b;
        this.v = new Vector(a.getX()+5, a.getY()+5,b.getX()+5, b.getY()+5);
    }

    public Stick(PointA a, PointA b, double pruznost, double prures) {
        this.a = a;
        this.b = b;
        this.pruznost = pruznost;
        this.prures = prures;
        this.v = new Vector(a.getX()+5, a.getY()+5,b.getX()+5, b.getY()+5);
    }

    public PointA getA() {
        return a;
    }

    public PointA getB() {
        return b;
    }

    public boolean intersection(int xi, int yi){
        return getIntersect(xi,yi, a);
    }

    public Vector getV() {
        return v;
    }

    private boolean getIntersect(int xi, int yi, PointA b) { //if new theoretical stick is same as this
        for (int i = 0; i <= v.l; i++) {
            int c = (int) Math.round(i* v.cosf);
            int d = (int) Math.round(i* v.sinf);
            if((xi<=-c+ b.getX()+10&&xi>=-c+ b.getX())&&(yi<= -d+ b.getY()+10&&yi>= -d+ b.getY())){
                return true;
            }
        }
        return false;
    }

    private int getMiddle(int a1, int a2){ //return middle of dimension
        if(a1>=a2){
            return a1-(a1-a2)/2;
        }
        return a2-(a2-a1)/2;
    }

    public void setForce(String force) {
        this.force = force;
    }

    public void paint(Graphics2D g){
        if(force!=null) {
            if(Double.parseDouble(force)>0){ //if is shrinking
                g.setColor(new Color(189, 102, 87));
            } else if (Double.parseDouble(force)<0) { //if is extending
                g.setColor(new Color(78, 157, 190));
            }
            else {
                g.setColor(this.color);
            }
        }
        else {
            g.setColor(this.color);
        }
        g.drawLine(a.getX() + 5, a.getY() + 5, b.getX() + 5, b.getY() + 5);
        if(force!=null) {
            g.setColor(this.textColor); // painting size of force
            g.drawString(force, getMiddle(a.getX(), b.getX()) + 15, getMiddle(a.getY(), b.getY()) + 15);
        }
    }
}
