
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Pablo Rodriguez
 */
public class menumonkeyFrame extends javax.swing.JFrame {

    
    /**
     * Creates new form menumonkeyFrame
     */
    
    public int inventariot, producciont, empaquetadot, salidat;
    public double inventarioc, produccionc, empaquetadoc, salidac, t1, t2, t3, t4,t5; 
    public String t11, t211,t311, t411,t511;
    public menumonkeyFrame() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("MENU MONKEY");
        System.out.println("hola");
    }
        
        /**
        iniciarsimulacionButton = new JButton("Activar botón");
        iniciarsimulacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulacionFrame sfr = new simulacionFrame(); 
                sfr.getJButton3().setEnabled(true); 
            }
        });
        
        }
        * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        inventariotiempoTF = new javax.swing.JTextField();
        producciontiempoTF = new javax.swing.JTextField();
        salidatiempoTF = new javax.swing.JTextField();
        empaquetadotiempoTF = new javax.swing.JTextField();
        inventariocostoTF = new javax.swing.JTextField();
        produccioncostoTF = new javax.swing.JTextField();
        empaquetadocostoTF = new javax.swing.JTextField();
        salidacostoTF = new javax.swing.JTextField();
        iniciarsimulacionButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BIENVENIDO A MONKEY");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("INVENTARIO");

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PRODUCCION:");

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("EMPAQUETADO:");

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SALIDA:");

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TIEMPO (s)");

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("COSTO (Q/s)");

        inventariotiempoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        producciontiempoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        salidatiempoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        empaquetadotiempoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        empaquetadotiempoTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empaquetadotiempoTFActionPerformed(evt);
            }
        });

        inventariocostoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        produccioncostoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        empaquetadocostoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        salidacostoTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        iniciarsimulacionButton.setBackground(java.awt.Color.darkGray);
        iniciarsimulacionButton.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 24)); // NOI18N
        iniciarsimulacionButton.setForeground(new java.awt.Color(255, 255, 255));
        iniciarsimulacionButton.setText("INICIAR SIMULACION");
        iniciarsimulacionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iniciarsimulacionButtonActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("seg");

        jLabel9.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("seg");

        jLabel10.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("seg");

        jLabel11.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("seg");

        jLabel12.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Q.");

        jLabel13.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Q.");

        jLabel14.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Q.");

        jLabel15.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Q.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(empaquetadotiempoTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(producciontiempoTF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(inventariotiempoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(salidatiempoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inventariocostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(produccioncostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(empaquetadocostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(salidacostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(126, 126, 126))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(168, 168, 168))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(iniciarsimulacionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inventariotiempoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(producciontiempoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empaquetadotiempoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(salidatiempoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inventariocostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(produccioncostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empaquetadocostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(salidacostoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(iniciarsimulacionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void iniciarsimulacionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iniciarsimulacionButtonActionPerformed
        
        
        if(inventariotiempoTF.getText().isEmpty()||  producciontiempoTF.getText().isEmpty() || empaquetadotiempoTF.getText().isEmpty() || salidatiempoTF.getText().isEmpty() || salidacostoTF.getText().isEmpty() ||  produccioncostoTF.getText().isEmpty() || empaquetadocostoTF.getText().isEmpty() || salidacostoTF.getText().isEmpty()){
            
            JOptionPane.showMessageDialog(null, "CAMPOS VACIOS, ASEGURECE DE LLENAR TODOS, VERIFIQUE Y AGREGUE UN NUMERO VALIDO (positivo mayor a 0");   
                
        }
        else{
        String inventarios = inventariotiempoTF.getText(); 
        String produccions = producciontiempoTF.getText();
        String empaquetados = empaquetadotiempoTF.getText();
        String salidas = salidatiempoTF.getText();
        
        String inventariosc = inventariocostoTF.getText(); 
        String produccionsc = produccioncostoTF.getText();
        String empaquetadosc = empaquetadocostoTF.getText();
        String salidasc = salidacostoTF.getText();
        
        inventariot = Integer.parseInt(inventarios);
        producciont = Integer.parseInt(produccions);
        empaquetadot = Integer.parseInt(empaquetados);
        salidat = Integer.parseInt(salidas);
        
        
        inventarioc = Double.parseDouble(inventariosc);
        produccionc = Double.parseDouble(produccionsc);
        empaquetadoc = Double.parseDouble(empaquetadosc);
        salidac = Double.parseDouble(salidasc);
        
        t1 = inventariot * inventarioc * 30;
        t2 = producciont * produccionc * 30;
        t3 = empaquetadot * empaquetadoc * 30;
        t4 = salidat * salidac * 30;
        t5 = t1 + t2 + t3 + t4;
        
        
        
            System.out.println(t1);
            System.out.println(t2);
            System.out.println(t3);
            System.out.println(t4);
            
        
        
        
        if(inventariot>0 && producciont>0 && empaquetadot>0 && salidat>0){
            if(inventarioc>0 && produccionc>0 && empaquetadoc>0 && salidac>0){
            
            simulacionFrame sframe = new simulacionFrame();
            sframe.setVisible(true);
            dispose();
            
            sframe.jLabel8.setText(inventarios);
            sframe.jLabel13.setText(produccions);
            sframe.jLabel14.setText(empaquetados);
            sframe.jLabel15.setText(salidas);
            
            
            sframe.a = t1;
            sframe.a2 = t2;
            sframe.a3 = t3;
            sframe.a4 = t4;
            sframe.a5 = t5;
            
            t11 = String.valueOf(t1);
            t211 = String.valueOf(t2);
            t311 = String.valueOf(t3);
            t411 = String.valueOf(t4);
            t511 = String.valueOf(t5);
            
            sframe.jLabel4.setText(t11);
            sframe.jLabel9.setText(t211);
            sframe.jLabel10.setText(t311);
            sframe.jLabel11.setText(t411);
            sframe.jLabel12.setText(t511);
            
            sframe.jLabel8.setText(inventarios);
            sframe.jLabel13.setText(produccions);
            sframe.jLabel14.setText(empaquetados);
            sframe.jLabel15.setText(salidas);
            
            sframe.jButton3.setVisible(true);
            /*
            sframe.jButton3.setEnabled(false);
            Hilo_TiempoTrans ht = new Hilo_TiempoTrans(0,0,0);
            ht.addObserver((Observer) this);
            Thread hilot = new Thread (ht);  
            hilot.start();
            Hilo_contadorinicio ci = new Hilo_contadorinicio(30);        
            ci.addObserver((Observer) this);    
            Thread hiloci = new Thread (ci);  
            hiloci.start();
            */
            
            
            }
            else{
                JOptionPane.showMessageDialog(null, " Ingreso un costo invalido, debe ser un valor positivo diferente a 0");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, " Ingreso un tiempo invalido, Debe ingresar un tiempo, (considere que esta en segundos) debe ser positivo y entero");
        }
        
        //ya creo
        
        
        }   
    }//GEN-LAST:event_iniciarsimulacionButtonActionPerformed

    private void empaquetadotiempoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empaquetadotiempoTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_empaquetadotiempoTFActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(menumonkeyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menumonkeyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menumonkeyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menumonkeyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menumonkeyFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField empaquetadocostoTF;
    public javax.swing.JTextField empaquetadotiempoTF;
    private javax.swing.JButton iniciarsimulacionButton;
    public javax.swing.JTextField inventariocostoTF;
    public javax.swing.JTextField inventariotiempoTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JTextField produccioncostoTF;
    public javax.swing.JTextField producciontiempoTF;
    public javax.swing.JTextField salidacostoTF;
    public javax.swing.JTextField salidatiempoTF;
    // End of variables declaration//GEN-END:variables
}
