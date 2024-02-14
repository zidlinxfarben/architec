import javax.swing.*;
import java.util.logging.Logger;

public class Options extends javax.swing.JDialog {

    private javax.swing.JButton ExitButton;
    private javax.swing.JTextField fieldOfX;
    private javax.swing.JTextField fieldOfY;
    private javax.swing.JCheckBox xCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JCheckBox yCheck;
    private Grid grid;
    private final PointA a;
    private final Options options;


    /**
     * Creates new form Options
     */
    public Options(JFrame parent, Grid grid, PointA a, boolean modal) {
        super(parent, modal);
        initComponents();
        this.grid = grid;
        this.a = a;
        this.options = this;
    }

    private void initComponents() {

        ExitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fieldOfX = new javax.swing.JTextField();
        fieldOfY = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        xCheck = new javax.swing.JCheckBox();
        yCheck = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Options");
        setSize(400, 300);
        setName("Options");
        setResizable(false);
        setLocationRelativeTo(null);

        ExitButton.setText("OK");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Force");

        fieldOfX.setText("0");
        fieldOfY.setText("0");

        jLabel2.setText("Foundations");

        xCheck.setText("In x");
        xCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        yCheck.setText("In y");
        yCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yCheckActionPerformed(evt);
            }
        });

        jLabel3.setText("Size of x");

        jLabel4.setText("Size of y");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addComponent(jLabel3))
                                                        .addComponent(jLabel1))
                                                .addGap(0, 322, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(fieldOfX)
                                                        .addComponent(fieldOfY)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(10, 10, 10)
                                                                                .addComponent(jLabel4))
                                                                        .addComponent(jLabel2)
                                                                        .addComponent(xCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(yCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(175, 175, 175)
                                .addComponent(ExitButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(2, 2, 2)
                                .addComponent(fieldOfX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldOfY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(xCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addComponent(ExitButton)
                                .addContainerGap())
        );

        pack();
    }
    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // give information to Point a
        double fx;
        double fy;
        try {
            if(fieldOfX.getText()==null){
                fx = 0;
            }
            else {
                fx = Double.parseDouble(fieldOfX.getText());
            }
            if(fieldOfY.getText() == null){
                fy = 0;
            }
            else {
                fy = Double.parseDouble(fieldOfY.getText());
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "please input a valid number","Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Force force = new Force(a, fx, fy);
        a.setForce(force);
        grid.getForces().add(force);
        Zaklada zaklada = new Zaklada(xCheck.isSelected(), yCheck.isSelected(), a);
        a.setZaklada(zaklada);
        grid.getZakladas().add(zaklada);
        grid.revalidate();
        grid.repaint();
        this.dispose();
    }

    private void yCheckActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //sety =true if checked else false
    }

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //setx =true if checked else false
    }


    public void runner() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {

            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    options.dispose();
                }
            });
            options.setVisible(true);
        });
    }
}
