import java.awt.*;

public class Zaklada {
    public boolean x = false; //if it's not supposed to move in x
    public boolean y = false; //if it's not supposed to move in y
    private PointA a; //which pointa

    public Zaklada(boolean x, boolean y, PointA a) {
        this.x = x;
        this.y = y;
        this.a = a;
    }

    public Zaklada(PointA a) {
        this.a = a;
    }

    public PointA getA() {
        return a;
    }

    public boolean isX() {
        return x;
    }

    public boolean isY() {
        return y;
    }
    public void paint(Graphics2D g, int xs, int ys){
        if(x||y) {
            int[] xt = {xs - 10, xs, xs + 10};
            int[] yt = {ys + 10, ys, ys + 10};
            g.setColor(Color.gray);
            g.fillPolygon(xt, yt, 3); //draws a triangle
            if (!x) {
                g.drawString("y",xs,ys+20);
            }
            if(!y){
                g.drawString("x",xs,ys+20);
            }
            g.setColor(Color.black);
        }
    }
}
