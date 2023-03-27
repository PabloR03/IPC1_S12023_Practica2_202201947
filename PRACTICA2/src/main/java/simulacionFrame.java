
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * @author Pablo Rodriguez
 */
public class simulacionFrame extends javax.swing.JFrame implements Observer{

        
    
    public int ContadorIncial;
    static int ContadorInventario=0, ContadorProduccion=0, ContadorEmpaquetado=0, ContadorSalida=0, ContadorFinal=0;

    public int TiempoProduccion;
    public int TiempoEmpaquetado;
    public int TiempoSalida;
    public int TiempoFinal;
    
    public HiloTemporizador hiloTemporizador;
    public Thread HiloTemporizador;
    
    public HiloIncial hiloInicial;
    public Thread HiloInicial;
    
    public HiloInventario hiloInventario;
    public Thread HiloInventario;
    
    public HiloProduccion hiloProduccion;
    public Thread HiloProduccion;
    
    public HiloEmpaquetado hiloEmpaquetado;
    public Thread HiloEmpaquetado;
    
    public HiloSalida hiloSalida;
    public Thread HiloSalida;
    
    public HiloFinal hiloFinal;
    public Thread HiloFinal;
    
    public static synchronized void incInventario(){
       ContadorInventario++;
    }
    public static synchronized void decInventario(){
        ContadorInventario--;
    }
    public static synchronized void incProduccion(){
       ContadorProduccion++;
    }
    public static synchronized void decProduccion(){
        ContadorProduccion--;
    }
    public static synchronized void incEmpaquetado(){
       ContadorEmpaquetado++;
    }
    public static synchronized void decEmpaquetado(){
        ContadorEmpaquetado--;
    }
    public static synchronized void incSalida(){
       ContadorSalida++;
    }
    public static synchronized void decSalida(){
        ContadorSalida--;
    }
    public static synchronized void incFinal(){
       ContadorFinal++;
    }
    //FUNCIONES PARA PINTAR EL CIRCULO
    
    public class DespintariCiculo{
        public DespintariCiculo(Graphics g, int x, int y) {
            Color id = new Color(51,102,255);
            g.setColor(id);
            g.fillOval(x, y, 20, 20);
        }
    }
        public class DespintarpCiculo{
        public DespintarpCiculo(Graphics g, int x, int y) {
            Color pd = new Color(102,255,102);
            g.setColor(pd);
            g.fillOval(x, y, 20, 20);
        }
    }
            public class DespintareCiculo{
        public DespintareCiculo(Graphics g, int x, int y) {
            Color ed = new Color(204,0,204);
            g.setColor(ed);
            g.fillOval(x, y, 20, 20);
        }
    }
                public class DespintarsCiculo{
        public DespintarsCiculo(Graphics g, int x, int y) {
            Color sd = new Color(255,153,153);
            g.setColor(sd);
            g.fillOval(x, y, 20, 20);
        }
    }
    public class PintarCiculoInventario{
        public PintarCiculoInventario(Graphics g, int x, int y) {
            Color Inventario = new Color(0,0,102);
            g.setColor(Inventario);
            g.fillOval(x, y, 20, 20);
        }
    }
    
    public class PintarCiculoProduccion{
        public PintarCiculoProduccion(Graphics g, int x, int y) {
            Color Produccion = new Color(0, 102, 0);
            g.setColor(Produccion);
            g.fillOval(x, y, 20, 20);
        }
    }
    public class PintarCiculoEmpaquetado{
        public PintarCiculoEmpaquetado(Graphics g, int x, int y) {
            Color Produccion = new Color(102,0,102);
            g.setColor(Produccion);
            g.fillOval(x, y, 20, 20);
        }
    }
    public class PintarCiculoSalida{
        public PintarCiculoSalida(Graphics g, int x, int y) {
            Color Produccion = new Color(102,0,0);
            g.setColor(Produccion);
            g.fillOval(x, y, 20, 20);
        }
    }
    public class HiloTemporizador implements Runnable{
        JLabel TiempoTranscurrido;
        boolean Temp=true;
        int horas, minutos, segundos;
        public HiloTemporizador(JLabel tiempoTranscurrido) {
            this.TiempoTranscurrido = tiempoTranscurrido;
        }
        public String toString(int numero) {
            if (numero < 10) {
                return "0" + Integer.toString(numero);
            }
            return Integer.toString(numero);
        }
        @Override
        public void run() {
            while (Temp) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(simulacionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                segundos++;
                if (segundos == 60) {minutos++;segundos = 0;}
                if (minutos == 60) {horas++;minutos = 0;}
                TiempoTranscurrido.setText(toString(horas) + ":" + toString(minutos) + ":" + toString(segundos));
                if(ContadorFinal==30){
                    JOptionPane.showMessageDialog(null, "TERMINADO, regrese para iniciaar con nuevos tiempos o Imprima su reporte"); 
                    Temp=false;
                }
            }
        }
    }
     public class HiloIncial implements Runnable{
        public boolean ValidarHilo=true;
        JLabel contadorinicio;
        public HiloIncial(JLabel contadorinicio){
            this.contadorinicio = contadorinicio;
        }
        @Override
        public void run() {
            while(ValidarHilo){ 
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(simulacionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("inicial");
                ContadorIncial--; 
                contadorinicio.setText(String.valueOf(ContadorIncial));
                if(ContadorIncial==0){
                    ValidarHilo=false;
                    HiloInicial.stop();
                }
            }
        }
    }
     
      public class HiloInventario implements Runnable{       
        public boolean ValidarHilo1=true;
        JPanel Panel;
        JLabel cin;
        boolean direccionDercha = true;
        public HiloInventario(JLabel cin, JPanel Panel){
            this.cin = cin;
            this.Panel=Panel;
        }
        @Override
        public void run() {
            int x=6;
            int y=40;
            while(ValidarHilo1){ 
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(simulacionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("inventario");
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                incInventario();
                PintarCiculoInventario CirculoInventario = new PintarCiculoInventario(inventarioPanel.getGraphics(),x,y); 
                cin.setText(String.valueOf(ContadorInventario));  
                if(ContadorIncial==0){
                    ValidarHilo1=false;
                    HiloInventario.stop();
                }
                x=x+50;   
            }
        }
    }
      public class HiloProduccion implements Runnable{       
        public boolean ValidarHilo2=true;
        JLabel cin, cpr; JPanel Panel1, Panel2;
        public HiloProduccion(JLabel cin, JLabel cpr, JPanel Panel1, JPanel Panel2){
            this.cin = cin;
            this.cpr = cpr;
            this.Panel1=Panel1;
            this.Panel2=Panel2;
        }
        @Override
        public void run() {
            int x=6; int y=40;
            //Tiempo Para Pasar de Inventario a Produccion  
            while(ValidarHilo2){ 
                try {
                    Thread.sleep(TiempoProduccion*1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(simulacionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("produccion");
                if(x>=300){y=y+30;x=6;}
                incProduccion();
                PintarCiculoProduccion CirculoProduccion = new PintarCiculoProduccion(Panel1.getGraphics(),x,y);
                cpr.setText(String.valueOf(ContadorProduccion));
                DespintariCiculo Circulo = new DespintariCiculo(Panel2.getGraphics(),x,y);
                decInventario();
                cin.setText(String.valueOf(ContadorInventario));
                if(ContadorInventario==0&&ContadorIncial==0){
                    ValidarHilo2=false;
                    HiloProduccion.stop();
                }
                x=x+50;
            }
        }
    }
      public class HiloEmpaquetado implements Runnable{     
        public boolean ValidarHilo3=true;
        JLabel cpr,cem;
        JPanel Panel1, Panel2;
        public HiloEmpaquetado(JLabel cpr, JLabel cem, JPanel Panel1, JPanel Panel2){
            this.cpr = cpr;
            this.cem = cem;
            this.Panel1=Panel1;
            this.Panel2=Panel2;
        }
        @Override
        public void run() {
            int x=6;
            int y=40;
            //Tiempo Para Pasar de Produccion a Empaquetado
            while(ValidarHilo3){ 
                try {
                    Thread.sleep(TiempoEmpaquetado*1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(simulacionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("empaquetado");
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                incEmpaquetado();
                PintarCiculoEmpaquetado CirculoEmpaquetado = new PintarCiculoEmpaquetado(Panel1.getGraphics(),x,y);
                cem.setText(String.valueOf(ContadorEmpaquetado));     
                decProduccion();
                DespintarpCiculo Circulo = new DespintarpCiculo(Panel2.getGraphics(),x,y);
                cpr.setText(String.valueOf(ContadorProduccion));
                CambioTiempoEmpaquetado();
                if(ContadorInventario==0&&ContadorProduccion==0&&ContadorIncial==0){
                    ValidarHilo3=false;
                    HiloEmpaquetado.stop();
                }
                x=x+50;
            }
        }
    }
       public class HiloSalida implements Runnable{
        public boolean ValidarHilo4=true;
        JLabel cem, csa;
        JPanel Panel1, Panel2;
        public HiloSalida(JLabel cem, JLabel csa, JPanel Panel1, JPanel Panel2){
            this.cem = cem;
            this.csa = csa;
            this.Panel1=Panel1;
            this.Panel2=Panel2;
        }
        @Override
        public void run() {
            //Tiempo Para Pasar de Empaquetado a Salida
            int x=6;
            int y=40;
            while(ValidarHilo4){ 
                try {
                    Thread.sleep(TiempoSalida*1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(simulacionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("salida");
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                PintarCiculoSalida CirculoSalida = new PintarCiculoSalida(Panel1.getGraphics(),x,y);
                incSalida();
                csa.setText(String.valueOf(ContadorSalida));     
                decEmpaquetado();
                DespintareCiculo Circulo = new DespintareCiculo(Panel2.getGraphics(),x,y);
                cem.setText(String.valueOf(ContadorEmpaquetado));
                CambioTiempoSalida();
                if(ContadorInventario==0&&ContadorProduccion==0&&ContadorEmpaquetado==0&&ContadorIncial==0){
                    ValidarHilo4=false;
                    HiloSalida.stop();
                }
                x=x+50;
            }
        }
    }
        public class HiloFinal implements Runnable{
        public boolean ValidarHilo5=true;
        JLabel csa,contadorfinal;
        JPanel Panel2;
        public HiloFinal(JLabel csa, JLabel contadorfinal, JPanel Panel2){
            this.csa = csa;
            this.contadorfinal = contadorfinal;
            this.Panel2=Panel2;
        }
        @Override
        public void run() {
            int x=6;
            int y=40;
            //Tiempo Para Pasar de Salida a Final
            while(ValidarHilo5){ 
                try {
                    Thread.sleep(TiempoFinal*1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(simulacionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("salida");
                incFinal();
                contadorfinal.setText(String.valueOf(ContadorFinal));
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                decSalida();
                DespintarsCiculo Circulo = new DespintarsCiculo(Panel2.getGraphics(),x,y);
                csa.setText(String.valueOf(ContadorSalida));
                CambioTiempoFinal();
                if(ContadorInventario==0&&ContadorProduccion==0&&ContadorEmpaquetado==0&&ContadorSalida==0&&ContadorIncial==0){
                    ValidarHilo5=false;
                    HiloFinal.stop();
                }
                x=x+50;
            }
        }
    }
        public synchronized void CambioTiempoEmpaquetado(){
            menumonkeyFrame mfr = new menumonkeyFrame();
        if (TiempoEmpaquetado== tp+ti){
            TiempoEmpaquetado=ti;
        }
        
        //if (TiempoEmpaquetado== ti+tp){
         //   TiempoEmpaquetado=ti;
        //}
    }
    public synchronized void CambioTiempoSalida(){
        menumonkeyFrame mfr = new menumonkeyFrame();
        if (TiempoSalida==ti+tp+te){
            TiempoSalida=ti;
        }
        //if (TiempoSalida==ti+tp+te){TiempoSalida=ti;}
    }
    public synchronized void CambioTiempoFinal(){
        menumonkeyFrame mfr = new menumonkeyFrame();
        if (TiempoFinal==ti+tp+te+ts){
            TiempoFinal=ti;
        }
        //if (TiempoFinal==ti+tp+te+ts){
          //  TiempoFinal=ti;
        //}
    }
    
    
    /*
    private int inicio = 0;

    public synchronized void aumenta() {
        inicio++;
    }
    
    public int getinicio() {
        return inicio;
    }

    */
    public String e1, e2, e3, e4, e5;
    double a, a2, a3, a4, a5;
    public int ti, tp, te, ts;
    public simulacionFrame() {
        
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("MONKEY");
        System.out.println("TIEMPOS:");
        ti = Integer.parseInt(jLabel8.getText());
        tp = Integer.parseInt(jLabel13.getText());
        te = Integer.parseInt(jLabel14.getText());
        ts = Integer.parseInt(jLabel15.getText());
        System.out.println(ti);
        System.out.println(tp);
        System.out.println(te);
        System.out.println(ts);
        menumonkeyFrame mfr = new menumonkeyFrame();
        
        
        
        TiempoProduccion=0;
        TiempoEmpaquetado=0;
        TiempoSalida=0;
        TiempoFinal=0;
        ContadorIncial=30;
        
        TiempoProduccion=(ti);
        TiempoEmpaquetado=(ti+tp);
        TiempoSalida=(ti+tp+te);
        TiempoFinal=(ti+tp+te+ts);
                
        
        
        hiloTemporizador= new HiloTemporizador(TiempoTranscurrido);
        HiloTemporizador = new Thread(hiloTemporizador);
        HiloTemporizador.start(); 

        hiloInicial= new HiloIncial(contadorinicio);
        HiloInicial = new Thread(hiloInicial);
        HiloInicial.start();
        
        hiloInventario= new HiloInventario(cin, inventarioPanel);
        HiloInventario = new Thread(hiloInventario);
        HiloInventario.start();
        
        hiloProduccion= new HiloProduccion(cin,cpr,produccionPanel, inventarioPanel);
        HiloProduccion = new Thread(hiloProduccion);
        HiloProduccion.start();
        
        hiloEmpaquetado= new HiloEmpaquetado(cpr,cem, empaquetadoPanel, produccionPanel);
        HiloEmpaquetado = new Thread(hiloEmpaquetado);
        HiloEmpaquetado.start();
        
        hiloSalida= new HiloSalida(cem, csa, salidaPanel, empaquetadoPanel);
        HiloSalida = new Thread(hiloSalida);
        HiloSalida.start();
        
        hiloFinal= new HiloFinal(csa, contadorfinal, salidaPanel);
        HiloFinal = new Thread(hiloFinal);
        HiloFinal.start();

        
    }
/**
    public JButton getJButton3() {
        return jButton3;
    }
        
    
    

    
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        TiempoTranscurrido = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        produccionPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cpr = new javax.swing.JLabel();
        empaquetadoPanel = new javax.swing.JPanel();
        EMPAQUETADO = new javax.swing.JLabel();
        cem = new javax.swing.JLabel();
        salidaPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        csa = new javax.swing.JLabel();
        inventarioPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cin = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        contadorfinal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        contadorinicio = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.SystemColor.activeCaptionText);

        jButton1.setBackground(new java.awt.Color(255, 204, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("REGRESAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 163, 215));
        jButton2.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.default.background"));
        jButton2.setText("REPORTE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TIEMPO TRANSCURRIDO: ");

        TiempoTranscurrido.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        TiempoTranscurrido.setForeground(new java.awt.Color(255, 255, 255));
        TiempoTranscurrido.setText("00:00:00");

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.default.background"));
        jButton3.setText("INICIAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        produccionPanel.setBackground(new java.awt.Color(102, 255, 102));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PRODUCCION:");

        cpr.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cpr.setForeground(new java.awt.Color(255, 255, 255));
        cpr.setText("00");

        javax.swing.GroupLayout produccionPanelLayout = new javax.swing.GroupLayout(produccionPanel);
        produccionPanel.setLayout(produccionPanelLayout);
        produccionPanelLayout.setHorizontalGroup(
            produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produccionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(cpr, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        produccionPanelLayout.setVerticalGroup(
            produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produccionPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cpr))
                .addContainerGap(308, Short.MAX_VALUE))
        );

        empaquetadoPanel.setBackground(new java.awt.Color(204, 0, 204));

        EMPAQUETADO.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        EMPAQUETADO.setForeground(new java.awt.Color(255, 255, 255));
        EMPAQUETADO.setText("EMPAQUETADO:");

        cem.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cem.setForeground(new java.awt.Color(255, 255, 255));
        cem.setText("00");

        javax.swing.GroupLayout empaquetadoPanelLayout = new javax.swing.GroupLayout(empaquetadoPanel);
        empaquetadoPanel.setLayout(empaquetadoPanelLayout);
        empaquetadoPanelLayout.setHorizontalGroup(
            empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(EMPAQUETADO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cem, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );
        empaquetadoPanelLayout.setVerticalGroup(
            empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EMPAQUETADO)
                    .addComponent(cem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        salidaPanel.setBackground(new java.awt.Color(255, 153, 153));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SALIDA:");

        csa.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        csa.setForeground(new java.awt.Color(255, 255, 255));
        csa.setText("00");

        javax.swing.GroupLayout salidaPanelLayout = new javax.swing.GroupLayout(salidaPanel);
        salidaPanel.setLayout(salidaPanelLayout);
        salidaPanelLayout.setHorizontalGroup(
            salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salidaPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(csa, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        salidaPanelLayout.setVerticalGroup(
            salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salidaPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(csa))
                .addContainerGap(310, Short.MAX_VALUE))
        );

        inventarioPanel.setBackground(new java.awt.Color(51, 102, 255));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("INVENTARIO:");

        cin.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cin.setForeground(new java.awt.Color(255, 255, 255));
        cin.setText("00");

        javax.swing.GroupLayout inventarioPanelLayout = new javax.swing.GroupLayout(inventarioPanel);
        inventarioPanel.setLayout(inventarioPanelLayout);
        inventarioPanelLayout.setHorizontalGroup(
            inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cin, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inventarioPanelLayout.setVerticalGroup(
            inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("FINAL");

        contadorfinal.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        contadorfinal.setForeground(new java.awt.Color(255, 255, 255));
        contadorfinal.setText("00");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("INICIO");

        contadorinicio.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        contadorinicio.setForeground(new java.awt.Color(255, 255, 255));
        contadorinicio.setText("00");

        jLabel8.setText("5");

        jLabel13.setText("6");

        jLabel14.setText("7");

        jLabel15.setText("8");

        jLabel16.setText("1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(442, 442, 442)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(TiempoTranscurrido, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(240, 240, 240)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(salidaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(empaquetadoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(inventarioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(produccionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(69, 69, 69))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(contadorfinal))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(contadorinicio)))
                        .addGap(32, 32, 32)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TiempoTranscurrido, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(produccionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(empaquetadoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(60, 60, 60))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel16)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 204, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(contadorfinal)
                                .addGap(4, 4, 4)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inventarioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(salidaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contadorinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(340, 340, 340))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(contadorfinal.getText().equals("30")){
        menumonkeyFrame mmframe = new menumonkeyFrame();
        mmframe.setVisible(true);
        dispose();
        HiloTemporizador.stop();
        HiloInicial.stop();
        HiloInventario.stop();
        HiloProduccion.stop();
        HiloEmpaquetado.stop();
        HiloFinal.stop();
        
        }else{
        JOptionPane.showMessageDialog(null, "No puede Regeresar, espere a que termine");
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed
public String generarReporte() {
     menumonkeyFrame mn = new menumonkeyFrame();
    System.out.println("METODO");
    System.out.println(e1);
    System.out.println(e2);
    System.out.println(e3);
    System.out.println(e4);
    System.out.println(e5);
     String tiempo = TiempoTranscurrido.getText();
    
    StringBuilder sb = new StringBuilder();
        sb.append("<body bgcolor=\"cyan\">");
        sb.append("<font font face=\"Arial\">");
        
        sb.append("<h1>").append("Reporte Costo De Simulación").append("</h1>");
        
        sb.append("<table border=\"1\">\n");
        sb.append("<tr>");
        sb.append("<td>").append("Costo Inventario").append("</td>");
        sb.append("<td>").append("Q. "+e1+"0").append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>").append("Costo Producción").append("</td>");
        sb.append("<td>").append("Q. "+e2+"0").append("</td>");
        sb.append("</tr>");  
        sb.append("<tr>");
        sb.append("<td>").append("Costo Empaquetado").append("</td>");
        sb.append("<td>").append("Q. "+e3+"0").append("</td>");
        sb.append("</tr>");     
        sb.append("<tr>");
        sb.append("<td>").append("Costo Salida").append("</td>");
        sb.append("<td>").append("Q. "+e4+"0").append("</td>");
        sb.append("</tr>"); 
        sb.append("<tr>");
        sb.append("<td>").append("Costo Total").append("</td>"); 
        sb.append("<td>").append("Q. "+e5+"0").append("</td>");
        sb.append("<td>").append("Se ha tardado un tiempo de: ").append("</td>"); 
        sb.append("<td>").append(tiempo).append("</td>");
        sb.append("</tr>"); 
        sb.append("</table>");
        sb.append("<h4>").append("PABLO ANDRES RODRIGUEZ LIMA - 202201947").append("</h4>");
        sb.append("</body>");
        sb.append("</font>");
        //SE CIERRA
        return sb.toString();
}
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        
        
        if(contadorfinal.getText().equals("30")){
        e1 = jLabel4.getText();
        e2 = jLabel9.getText();
        e3 = jLabel10.getText();
        e4 = jLabel11.getText();
        e5 = jLabel12.getText();
        HiloTemporizador.stop();
        HiloInicial.stop();
        HiloInventario.stop();
        HiloProduccion.stop();
        HiloEmpaquetado.stop();
        HiloFinal.stop();
        ContadorFinal=0;
        
        File carpeta = new File("C:\\Users\\admin\\Downloads\\ipc1htmllll");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        // Escribe el archivo .html dentro de la carpeta
        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\admin\\Downloads\\ipc1htmllll"+ "TOTAL_monkey"+".html");
            fileWriter.write(generarReporte());
            JOptionPane.showMessageDialog(null, "Reporte Descargado."); 
            
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        } else {
            JOptionPane.showMessageDialog(null, "No puede descargar Reporte, espere a que termine"); 
        }
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.out.println("amigo");
        System.out.println(ti);
        System.out.println(tp);
        System.out.println(te);
        System.out.println(ts);
        
        menumonkeyFrame mfr = new menumonkeyFrame();

        System.out.println(mfr.inventariot+mfr.producciont+mfr.empaquetadot+mfr.salidat);
        
        
        
        
        /*
        //this.jButton3.setVisible(false);
        //Hilo_TiempoTrans ht = new Hilo_TiempoTrans(0,0,0);
        //ht.addObserver(this);
        //Thread hilot = new Thread (ht);  
        //hilot.start();
        
        
        Hilo_contadorinicio ci = new Hilo_contadorinicio(30);        
        ci.addObserver(this);    
        Thread hiloci = new Thread (ci);  
        hiloci.start();
        
        
            if(ht.seg==1){
            b31.setVisible(true);
        }
               if(ht.seg==2){
            b31.setVisible(true);
        }
          */  
        
        
        /*ImageIcon pelotaazul = new ImageIcon("src/imagenes/azul.png");
        Icon icono = new ImageIcon(pelotaazul.getImage().getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_DEFAULT));
        jLabel3.setIcon(icono);
        this.repaint();*/
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(simulacionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(simulacionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(simulacionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(simulacionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new simulacionFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EMPAQUETADO;
    public javax.swing.JLabel TiempoTranscurrido;
    private javax.swing.JLabel cem;
    private javax.swing.JLabel cin;
    private javax.swing.JLabel contadorfinal;
    private javax.swing.JLabel contadorinicio;
    private javax.swing.JLabel cpr;
    private javax.swing.JLabel csa;
    private javax.swing.JPanel empaquetadoPanel;
    private javax.swing.JPanel inventarioPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    private javax.swing.JPanel produccionPanel;
    private javax.swing.JPanel salidaPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {        
        //TiempoTranscurrido.setText((String) arg);
        //contadorinicio.setText((String) arg); 
    }
    
}
