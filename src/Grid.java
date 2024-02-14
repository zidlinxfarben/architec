import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

public class Grid extends JPanel implements MouseListener, KeyListener {
    private ArrayList <PointA> points = new ArrayList<>();
    private ArrayList <Stick> sticks = new ArrayList<>();
    private ArrayList <Force> forces = new ArrayList<>();
    private ArrayList <Zaklada> zakladas = new ArrayList<>();
    private ArrayList <PointA> animatedPoints;
    private ArrayList <Stick> animatedStick;
    private Boolean exitedState = false;
    private Boolean Animate = false;
    private PointA exitedPoint = null;
    private static final int BOUND = 15;
    private int id = 0;
    private final JFrame frame;
    public ArrayList<Force> getForces() {
        return forces;
    }

    public ArrayList<Zaklada> getZakladas() {
        return zakladas;
    }

    public ArrayList<PointA> getPoints() {
        return points;
    }

    public ArrayList<Stick> getSticks() {
        return sticks;
    }

    public Grid(JFrame frame){
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        this.frame=frame;
    }

    public void setAnimatedPoints(ArrayList<PointA> animatedPoints) {
        this.animatedPoints = animatedPoints;
    }
    public void setAnimatedStick(ArrayList<Stick> animatedStick) {
        this.animatedStick = animatedStick;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr2d = (Graphics2D) g;
        gr2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gr2d.setStroke(new BasicStroke(3));
        if(Animate){
            try {
                for (Stick i : animatedStick) {
                    i.paint(gr2d);
                }
                for (PointA i : animatedPoints) {
                    i.paint(gr2d);
                }
                return;
            }
            catch (NullPointerException e){
                Animate=false;
                animatedPoints = null;
                animatedStick = null;
                repaint();
                return;
            }
        }
        for (Stick i : sticks) {
            i.paint(gr2d);
        }
        for (PointA i :
                points) {
            i.paint(gr2d);
        }
    }

    private void getIdInOrder(int idd){
        for(; idd<=id; idd++){
            for(PointA a: points){
                if(a.getId()==idd){
                    a.setId(idd-1);
                }
            }
        }
    }
    public int toX(int a, int bound){
        int mod = a%bound;
        if(mod<=bound/2){
            a=a-mod;
            return a;
        }
        a=a+bound-mod;
        return a;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (e.getButton() == 1) {
            for(PointA i:points) {
                if (i.intesection(x, y)) {
                    if (exitedState && i != exitedPoint) {
                        Stick a = new Stick(i, exitedPoint);
                        for (Stick b : sticks) {
                            if (a.getA() == b.getA() && a.getB() == b.getB()) {
                                return;
                            }
                            if (a.getA() == b.getB() && a.getB() == b.getA()) {
                                return;
                            }
                        }
                        sticks.add(a);
                        revalidate();
                        repaint();
                        return;
                    }
                    if (exitedState) {
                        Options options = new Options(frame, this, i, true);
                        options.runner();
                        revalidate();
                        repaint();
                        return;
                    }
                    exitedState = true;
                    exitedPoint = i;
                    i.setExited(true);
                    revalidate();
                    repaint();
                    return;
                }
            }
            if (!exitedState) {
                x = toX(x, BOUND);
                y = toX(y, BOUND);
                PointA b = new PointA(x, y, id);
                id++;
                points.add(b);
                zakladas.add(b.getZaklada());
                revalidate();
                repaint();
                return;
            }
            exitedState = false;
            exitedPoint.setExited(false);
            exitedPoint = null;
            revalidate();
            repaint();
        }
        if(e.getButton()==3){
            Iterator<PointA> itp = points.iterator();
            Iterator<Stick> stickIterator = sticks.iterator();
            while (itp.hasNext()){
                PointA i= itp.next();
                if (i.intesection(x, y)){
                    while (stickIterator.hasNext()) {
                        Stick s = stickIterator.next();
                        if (s.getA() == i || s.getB() == i) {
                            stickIterator.remove();
                        }
                    }
                    zakladas.remove(i.getZaklada());
                    if(i.isExited()){
                        exitedState=false;
                        exitedPoint=null;
                    }
                    int a = i.getId()+1;
                    itp.remove();
                    getIdInOrder(a);
                    id--;
                    revalidate();
                    repaint();
                    return;
                }
            }
            while (stickIterator.hasNext()){
                Stick a = stickIterator.next();
                if(a.intersection(x,y)){
                    stickIterator.remove();
                }
            }
            revalidate();
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A){
            if(Animate){
                Animate=false;
                animatedPoints = null;
                animatedStick = null;
                repaint();
                return;
            }
            Animate=true;
            Equation eq = new Equation(this);
            eq.getResult();
            repaint();
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_DELETE){
            exitedPoint=null;
            exitedState=false;
            id = 0;
            points = new ArrayList<>();
            sticks = new ArrayList<>();
            zakladas = new ArrayList<>();
            forces = new ArrayList<>();
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}