import javax.swing.*;
public class Main{

    public static void main(String[] args){ // creates frame
        JFrame frame = new JFrame("Architec");
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grid grid = new Grid(frame);
        frame.add(grid);
        frame.setVisible(true);
    }
}