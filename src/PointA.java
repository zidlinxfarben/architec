
import java.awt.*;

public class PointA{
    private final int diameter=10;
    private Zaklada zaklada;
    private Force force;
    private boolean exited = false; // for getting new line forces and zakladnas
    private int id;
    private final Color color1 = Color.gray;
    private int x;
    private int y;
    private Color color = Color.black;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public PointA(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.zaklada = new Zaklada(this);
    }
    public PointA(PointA a) {
        this.x = a.getX();
        this.y = a.getY();
        this.id = a.getId();
        this.zaklada = new Zaklada(this);
    }

    public Zaklada getZaklada() {
        return zaklada;
    }

    public void setZaklada(Zaklada zaklada) {
        this.zaklada = zaklada;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public void setExited(boolean exited) {
        this.exited = exited;
    }

    public boolean isExited() {
        return exited;
    }

    public boolean intesection(int xp, int yp){
        if(xp<=x+diameter&&xp>=x-diameter){
            return yp <= y + diameter && yp >= y - diameter;
        }
        return false;
    }

    public void paint(Graphics2D g){
        if(exited){
            g.setColor(this.color1);
            g.fillOval(x-5,y-5,diameter+10,diameter+10);
        }
        g.setColor(this.color);
        g.fillOval(this.x, this.y, diameter, diameter);
        zaklada.paint(g,x+5,y+15);
        if(force!=null){
            force.paint(g);
        }
    }
}
