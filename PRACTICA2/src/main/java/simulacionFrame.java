
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Icon;
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
    
    public class HiloTemporizador implements Runnable{
        JLabel tiempoTranscurrido;
        boolean Temp=true;
        int horas, minutos, segundos;
        public HiloTemporizador(JLabel tiempoTranscurrido) {
            this.tiempoTranscurrido = tiempoTranscurrido;
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
                    Logger.getLogger(SimulacionJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                segundos++;
                if (segundos == 60) {minutos++;segundos = 0;}
                if (minutos == 60) {horas++;minutos = 0;}
                tiempoTranscurrido.setText(toString(horas) + ":" + toString(minutos) + ":" + toString(segundos));
                if(ContadorFinal==30){
                    jButton1.setVisible(true);
                    jButton2.setVisible(true);
                    JOptionPane.showMessageDialog(null, "Procesamiento Terminado. Elija una de las opciones.", "COTIZACIÓN DE PAQUETES Y COMPRA", JOptionPane.INFORMATION_MESSAGE); 
                    Temp=false;
                }
            }
        }
    }
     public class HiloIncial implements Runnable{
        public boolean ValidarHilo=true;
        JLabel contadorIncioLBL;
        public HiloIncial(JLabel contadorIncioLBL){
            this.contadorIncioLBL = contadorIncioLBL;
        }
        @Override
        public void run() {
            while(ValidarHilo){ 
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SimulacionJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Trabaja Hilo Inicial");
                ContadorIncial--; 
                contadorIncioLBL.setText(String.valueOf(ContadorIncial));
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
        JLabel contadorInventarioLBL;
        boolean direccionDercha = true;
        public HiloInventario(JLabel contadorInventarioLBL, JPanel Panel){
            this.contadorInventarioLBL = contadorInventarioLBL;
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
                    Logger.getLogger(SimulacionJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Trabaja Hilo Inventario");
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                incInventario();
                PintarCiculoInventario CirculoInventario = new PintarCiculoInventario(Panel.getGraphics(),x,y); 
                contadorInventarioLBL.setText(String.valueOf(ContadorInventario));  
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
        JLabel contadorInventarioLBL, contadorProduccionLBL; JPanel Panel1, Panel2;
        public HiloProduccion(JLabel contadorInventarioLBL, JLabel contadorProduccionLBL, JPanel Panel1, JPanel Panel2){
            this.contadorInventarioLBL = contadorInventarioLBL;
            this.contadorProduccionLBL = contadorProduccionLBL;
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
                    Logger.getLogger(SimulacionJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Trabaja Hilo Produccion");
                if(x>=300){y=y+30;x=6;}
                incProduccion();
                PintarCiculoProduccion CirculoProduccion = new PintarCiculoProduccion(Panel1.getGraphics(),x,y);
                contadorProduccionLBL.setText(String.valueOf(ContadorProduccion));
                DespintarCiculo Circulo = new DespintarCiculo(Panel2.getGraphics(),x,y);
                decInventario();
                contadorInventarioLBL.setText(String.valueOf(ContadorInventario));
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
        JLabel contadorProduccionLBL,contadorEmpaquetadoLBL;
        JPanel Panel1, Panel2;
        public HiloEmpaquetado(JLabel contadorProduccionLBL, JLabel contadorEmpaquetadoLBL, JPanel Panel1, JPanel Panel2){
            this.contadorProduccionLBL = contadorProduccionLBL;
            this.contadorEmpaquetadoLBL = contadorEmpaquetadoLBL;
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
                    Logger.getLogger(SimulacionJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Trabaja Hilo Empaquetado");
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                incEmpaquetado();
                PintarCiculoEmpaquetado CirculoEmpaquetado = new PintarCiculoEmpaquetado(Panel1.getGraphics(),x,y);
                contadorEmpaquetadoLBL.setText(String.valueOf(ContadorEmpaquetado));     
                decProduccion();
                DespintarCiculo Circulo = new DespintarCiculo(Panel2.getGraphics(),x,y);
                contadorProduccionLBL.setText(String.valueOf(ContadorProduccion));
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
        JLabel contadorEmpaquetadoLBL, contadorSalidaLBL;
        JPanel Panel1, Panel2;
        public HiloSalida(JLabel contadorEmpaquetadoLBL, JLabel contadorSalidaLBL, JPanel Panel1, JPanel Panel2){
            this.contadorEmpaquetadoLBL = contadorEmpaquetadoLBL;
            this.contadorSalidaLBL = contadorSalidaLBL;
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
                    Logger.getLogger(SimulacionJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Trabaja Hilo Salida");
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                PintarCiculoSalida CirculoSalida = new PintarCiculoSalida(Panel1.getGraphics(),x,y);
                incSalida();
                contadorSalidaLBL.setText(String.valueOf(ContadorSalida));     
                decEmpaquetado();
                DespintarCiculo Circulo = new DespintarCiculo(Panel2.getGraphics(),x,y);
                contadorEmpaquetadoLBL.setText(String.valueOf(ContadorEmpaquetado));
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
        JLabel contadorSalidaLBL,contadorFinalLBL;
        JPanel Panel2;
        public HiloFinal(JLabel contadorSalidaLBL, JLabel contadorFinalLBL, JPanel Panel2){
            this.contadorSalidaLBL = contadorSalidaLBL;
            this.contadorFinalLBL = contadorFinalLBL;
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
                    Logger.getLogger(SimulacionJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Trabaja Hilo Salida");
                incFinal();
                contadorFinalLBL.setText(String.valueOf(ContadorFinal));
                if(x>=300){
                    y=y+30;
                    x=6;
                }
                decSalida();
                DespintarCiculo Circulo = new DespintarCiculo(Panel2.getGraphics(),x,y);
                contadorSalidaLBL.setText(String.valueOf(ContadorSalida));
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
        if (TiempoEmpaquetado==AppState.listaTiempo.get(0).getInventario()+AppState.listaTiempo.get(0).getProduccion()){
            TiempoEmpaquetado=AppState.listaTiempo.get(0).getInventario();
        }
    }
    public synchronized void CambioTiempoSalida(){
        if (TiempoSalida==AppState.listaTiempo.get(0).getInventario()+AppState.listaTiempo.get(0).getProduccion()+AppState.listaTiempo.get(0).getEmpaquetado()){
            TiempoSalida=AppState.listaTiempo.get(0).getInventario();
        }
    }
    public synchronized void CambioTiempoFinal(){
        if (TiempoFinal==AppState.listaTiempo.get(0).getInventario()+AppState.listaTiempo.get(0).getProduccion()+AppState.listaTiempo.get(0).getEmpaquetado()+AppState.listaTiempo.get(0).getSalida()){
            TiempoFinal=AppState.listaTiempo.get(0).getInventario();
        }
    }
        hiloTemporizador= new HiloTemporizador(jLabel15);
        HiloTemporizador = new Thread(hiloTemporizador);
        HiloTemporizador.start(); 

        hiloInicial= new HiloIncial(contadorIncioLBL);
        HiloInicial = new Thread(hiloInicial);
        HiloInicial.start();
        
        hiloInventario= new HiloInventario(contadorInventarioLBL, jPanel5);
        HiloInventario = new Thread(hiloInventario);
        HiloInventario.start();
        
        hiloProduccion= new HiloProduccion(contadorInventarioLBL,contadorProduccionLBL,jPanel2, jPanel5);
        HiloProduccion = new Thread(hiloProduccion);
        HiloProduccion.start();
        
        hiloEmpaquetado= new HiloEmpaquetado(contadorProduccionLBL,contadorEmpaquetadoLBL, jPanel3, jPanel2);
        HiloEmpaquetado = new Thread(hiloEmpaquetado);
        HiloEmpaquetado.start();
        
        hiloSalida= new HiloSalida(contadorEmpaquetadoLBL, contadorSalidaLBL, jPanel4, jPanel3);
        HiloSalida = new Thread(hiloSalida);
        HiloSalida.start();
        
        hiloFinal= new HiloFinal(contadorSalidaLBL, contadorFinalLBL, jPanel4);
        HiloFinal = new Thread(hiloFinal);
        HiloFinal.start();
    
    
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
    public simulacionFrame() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("MENU MONKEY");
  
    }

    public JButton getJButton3() {
        return jButton3;
    }
        
    
    

    /**
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
        b1 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        b3 = new javax.swing.JButton();
        b4 = new javax.swing.JButton();
        b5 = new javax.swing.JButton();
        b6 = new javax.swing.JButton();
        b7 = new javax.swing.JButton();
        b8 = new javax.swing.JButton();
        b9 = new javax.swing.JButton();
        b10 = new javax.swing.JButton();
        b11 = new javax.swing.JButton();
        b12 = new javax.swing.JButton();
        b13 = new javax.swing.JButton();
        b14 = new javax.swing.JButton();
        b15 = new javax.swing.JButton();
        b16 = new javax.swing.JButton();
        b17 = new javax.swing.JButton();
        b18 = new javax.swing.JButton();
        b19 = new javax.swing.JButton();
        b20 = new javax.swing.JButton();
        b21 = new javax.swing.JButton();
        b22 = new javax.swing.JButton();
        b23 = new javax.swing.JButton();
        b24 = new javax.swing.JButton();
        b25 = new javax.swing.JButton();
        b26 = new javax.swing.JButton();
        b27 = new javax.swing.JButton();
        b28 = new javax.swing.JButton();
        b29 = new javax.swing.JButton();
        b30 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cpr = new javax.swing.JLabel();
        empaquetadoPanel = new javax.swing.JPanel();
        EMPAQUETADO = new javax.swing.JLabel();
        cem = new javax.swing.JLabel();
        b91 = new javax.swing.JButton();
        b92 = new javax.swing.JButton();
        b93 = new javax.swing.JButton();
        b94 = new javax.swing.JButton();
        b95 = new javax.swing.JButton();
        b96 = new javax.swing.JButton();
        b97 = new javax.swing.JButton();
        b98 = new javax.swing.JButton();
        b99 = new javax.swing.JButton();
        b100 = new javax.swing.JButton();
        b101 = new javax.swing.JButton();
        b102 = new javax.swing.JButton();
        b103 = new javax.swing.JButton();
        b104 = new javax.swing.JButton();
        b105 = new javax.swing.JButton();
        b106 = new javax.swing.JButton();
        b107 = new javax.swing.JButton();
        b108 = new javax.swing.JButton();
        b109 = new javax.swing.JButton();
        b110 = new javax.swing.JButton();
        b111 = new javax.swing.JButton();
        b112 = new javax.swing.JButton();
        b113 = new javax.swing.JButton();
        b114 = new javax.swing.JButton();
        b115 = new javax.swing.JButton();
        b116 = new javax.swing.JButton();
        b117 = new javax.swing.JButton();
        b118 = new javax.swing.JButton();
        b119 = new javax.swing.JButton();
        b120 = new javax.swing.JButton();
        salidaPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        csa = new javax.swing.JLabel();
        b61 = new javax.swing.JButton();
        b62 = new javax.swing.JButton();
        b63 = new javax.swing.JButton();
        b64 = new javax.swing.JButton();
        b65 = new javax.swing.JButton();
        b66 = new javax.swing.JButton();
        b67 = new javax.swing.JButton();
        b68 = new javax.swing.JButton();
        b69 = new javax.swing.JButton();
        b70 = new javax.swing.JButton();
        b71 = new javax.swing.JButton();
        b72 = new javax.swing.JButton();
        b73 = new javax.swing.JButton();
        b74 = new javax.swing.JButton();
        b75 = new javax.swing.JButton();
        b76 = new javax.swing.JButton();
        b77 = new javax.swing.JButton();
        b78 = new javax.swing.JButton();
        b79 = new javax.swing.JButton();
        b80 = new javax.swing.JButton();
        b81 = new javax.swing.JButton();
        b82 = new javax.swing.JButton();
        b83 = new javax.swing.JButton();
        b84 = new javax.swing.JButton();
        b85 = new javax.swing.JButton();
        b86 = new javax.swing.JButton();
        b87 = new javax.swing.JButton();
        b88 = new javax.swing.JButton();
        b89 = new javax.swing.JButton();
        b90 = new javax.swing.JButton();
        inventarioPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cin = new javax.swing.JLabel();
        b41 = new javax.swing.JButton();
        b56 = new javax.swing.JButton();
        b49 = new javax.swing.JButton();
        b42 = new javax.swing.JButton();
        b39 = new javax.swing.JButton();
        b52 = new javax.swing.JButton();
        b34 = new javax.swing.JButton();
        b53 = new javax.swing.JButton();
        b33 = new javax.swing.JButton();
        b44 = new javax.swing.JButton();
        b48 = new javax.swing.JButton();
        b47 = new javax.swing.JButton();
        b51 = new javax.swing.JButton();
        b38 = new javax.swing.JButton();
        b58 = new javax.swing.JButton();
        b32 = new javax.swing.JButton();
        b46 = new javax.swing.JButton();
        b45 = new javax.swing.JButton();
        b55 = new javax.swing.JButton();
        b57 = new javax.swing.JButton();
        b37 = new javax.swing.JButton();
        b40 = new javax.swing.JButton();
        b43 = new javax.swing.JButton();
        b31 = new javax.swing.JButton();
        b36 = new javax.swing.JButton();
        b35 = new javax.swing.JButton();
        b60 = new javax.swing.JButton();
        b50 = new javax.swing.JButton();
        b59 = new javax.swing.JButton();
        b54 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        contadorinicio = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

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

        b1.setBackground(new java.awt.Color(0, 102, 0));
        b1.setForeground(new java.awt.Color(0, 102, 0));
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b2.setBackground(new java.awt.Color(0, 102, 0));
        b2.setForeground(new java.awt.Color(0, 102, 0));
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b3.setBackground(new java.awt.Color(0, 102, 0));
        b3.setForeground(new java.awt.Color(0, 102, 0));
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b4.setBackground(new java.awt.Color(0, 102, 0));
        b4.setForeground(new java.awt.Color(0, 102, 0));
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        b5.setBackground(new java.awt.Color(0, 102, 0));
        b5.setForeground(new java.awt.Color(0, 102, 0));
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b5ActionPerformed(evt);
            }
        });

        b6.setBackground(new java.awt.Color(0, 102, 0));
        b6.setForeground(new java.awt.Color(0, 102, 0));
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b6ActionPerformed(evt);
            }
        });

        b7.setBackground(new java.awt.Color(0, 102, 0));
        b7.setForeground(new java.awt.Color(0, 102, 0));
        b7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b7ActionPerformed(evt);
            }
        });

        b8.setBackground(new java.awt.Color(0, 102, 0));
        b8.setForeground(new java.awt.Color(0, 102, 0));
        b8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b8ActionPerformed(evt);
            }
        });

        b9.setBackground(new java.awt.Color(0, 102, 0));
        b9.setForeground(new java.awt.Color(0, 0, 204));
        b9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b9ActionPerformed(evt);
            }
        });

        b10.setBackground(new java.awt.Color(0, 102, 0));
        b10.setForeground(new java.awt.Color(0, 102, 0));
        b10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b10ActionPerformed(evt);
            }
        });

        b11.setBackground(new java.awt.Color(0, 102, 0));
        b11.setForeground(new java.awt.Color(0, 102, 0));
        b11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b11ActionPerformed(evt);
            }
        });

        b12.setBackground(new java.awt.Color(0, 102, 0));
        b12.setForeground(new java.awt.Color(0, 102, 0));
        b12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b12ActionPerformed(evt);
            }
        });

        b13.setBackground(new java.awt.Color(0, 102, 0));
        b13.setForeground(new java.awt.Color(0, 102, 0));
        b13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b13ActionPerformed(evt);
            }
        });

        b14.setBackground(new java.awt.Color(0, 102, 0));
        b14.setForeground(new java.awt.Color(0, 102, 0));
        b14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b14ActionPerformed(evt);
            }
        });

        b15.setBackground(new java.awt.Color(0, 102, 0));
        b15.setForeground(new java.awt.Color(0, 102, 0));
        b15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b15ActionPerformed(evt);
            }
        });

        b16.setBackground(new java.awt.Color(0, 102, 0));
        b16.setForeground(new java.awt.Color(0, 102, 0));
        b16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b16ActionPerformed(evt);
            }
        });

        b17.setBackground(new java.awt.Color(0, 102, 0));
        b17.setForeground(new java.awt.Color(0, 0, 204));
        b17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b17ActionPerformed(evt);
            }
        });

        b18.setBackground(new java.awt.Color(0, 102, 0));
        b18.setForeground(new java.awt.Color(0, 102, 0));
        b18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b18ActionPerformed(evt);
            }
        });

        b19.setBackground(new java.awt.Color(0, 102, 0));
        b19.setForeground(new java.awt.Color(0, 102, 0));
        b19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b19ActionPerformed(evt);
            }
        });

        b20.setBackground(new java.awt.Color(0, 102, 0));
        b20.setForeground(new java.awt.Color(0, 102, 0));
        b20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b20ActionPerformed(evt);
            }
        });

        b21.setBackground(new java.awt.Color(0, 102, 0));
        b21.setForeground(new java.awt.Color(0, 102, 0));
        b21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b21ActionPerformed(evt);
            }
        });

        b22.setBackground(new java.awt.Color(0, 102, 0));
        b22.setForeground(new java.awt.Color(0, 102, 0));
        b22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b22ActionPerformed(evt);
            }
        });

        b23.setBackground(new java.awt.Color(0, 102, 0));
        b23.setForeground(new java.awt.Color(0, 102, 0));
        b23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b23ActionPerformed(evt);
            }
        });

        b24.setBackground(new java.awt.Color(0, 102, 0));
        b24.setForeground(new java.awt.Color(0, 102, 0));
        b24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b24ActionPerformed(evt);
            }
        });

        b25.setBackground(new java.awt.Color(0, 102, 0));
        b25.setForeground(new java.awt.Color(0, 102, 0));
        b25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b25ActionPerformed(evt);
            }
        });

        b26.setBackground(new java.awt.Color(0, 102, 0));
        b26.setForeground(new java.awt.Color(0, 102, 0));
        b26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b26ActionPerformed(evt);
            }
        });

        b27.setBackground(new java.awt.Color(0, 102, 0));
        b27.setForeground(new java.awt.Color(0, 102, 0));
        b27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b27ActionPerformed(evt);
            }
        });

        b28.setBackground(new java.awt.Color(0, 102, 0));
        b28.setForeground(new java.awt.Color(0, 102, 0));
        b28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b28ActionPerformed(evt);
            }
        });

        b29.setBackground(new java.awt.Color(0, 102, 0));
        b29.setForeground(new java.awt.Color(0, 102, 0));
        b29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b29ActionPerformed(evt);
            }
        });

        b30.setBackground(new java.awt.Color(0, 102, 0));
        b30.setForeground(new java.awt.Color(0, 102, 0));
        b30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b30ActionPerformed(evt);
            }
        });

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
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(produccionPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(produccionPanelLayout.createSequentialGroup()
                                .addComponent(b25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b26, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b28, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b29, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b30, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(produccionPanelLayout.createSequentialGroup()
                                        .addComponent(b14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(produccionPanelLayout.createSequentialGroup()
                                        .addComponent(b13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(produccionPanelLayout.createSequentialGroup()
                                        .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(produccionPanelLayout.createSequentialGroup()
                                        .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(b6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(produccionPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cpr)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        produccionPanelLayout.setVerticalGroup(
            produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produccionPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cpr))
                .addGap(10, 10, 10)
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(b6, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(b5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(b3, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(b1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(b2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(b4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b12, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b21, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b19, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b17, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b22, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(produccionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b30, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b29, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b28, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b27, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b26, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b25, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        empaquetadoPanel.setBackground(new java.awt.Color(204, 0, 204));

        EMPAQUETADO.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        EMPAQUETADO.setForeground(new java.awt.Color(255, 255, 255));
        EMPAQUETADO.setText("EMPAQUETADO:");

        cem.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cem.setForeground(new java.awt.Color(255, 255, 255));
        cem.setText("00");

        b91.setBackground(new java.awt.Color(102, 0, 102));
        b91.setForeground(new java.awt.Color(102, 0, 153));
        b91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b91ActionPerformed(evt);
            }
        });

        b92.setBackground(new java.awt.Color(102, 0, 102));
        b92.setForeground(new java.awt.Color(102, 0, 153));
        b92.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b92ActionPerformed(evt);
            }
        });

        b93.setBackground(new java.awt.Color(102, 0, 102));
        b93.setForeground(new java.awt.Color(102, 0, 153));
        b93.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b93ActionPerformed(evt);
            }
        });

        b94.setBackground(new java.awt.Color(102, 0, 102));
        b94.setForeground(new java.awt.Color(102, 0, 153));
        b94.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b94ActionPerformed(evt);
            }
        });

        b95.setBackground(new java.awt.Color(102, 0, 102));
        b95.setForeground(new java.awt.Color(102, 0, 153));
        b95.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b95ActionPerformed(evt);
            }
        });

        b96.setBackground(new java.awt.Color(102, 0, 102));
        b96.setForeground(new java.awt.Color(102, 0, 153));
        b96.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b96ActionPerformed(evt);
            }
        });

        b97.setBackground(new java.awt.Color(102, 0, 102));
        b97.setForeground(new java.awt.Color(102, 0, 153));
        b97.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b97ActionPerformed(evt);
            }
        });

        b98.setBackground(new java.awt.Color(102, 0, 102));
        b98.setForeground(new java.awt.Color(102, 0, 153));
        b98.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b98ActionPerformed(evt);
            }
        });

        b99.setBackground(new java.awt.Color(102, 0, 102));
        b99.setForeground(new java.awt.Color(102, 0, 153));
        b99.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b99ActionPerformed(evt);
            }
        });

        b100.setBackground(new java.awt.Color(102, 0, 102));
        b100.setForeground(new java.awt.Color(102, 0, 153));
        b100.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b100ActionPerformed(evt);
            }
        });

        b101.setBackground(new java.awt.Color(102, 0, 102));
        b101.setForeground(new java.awt.Color(102, 0, 153));
        b101.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b101ActionPerformed(evt);
            }
        });

        b102.setBackground(new java.awt.Color(102, 0, 102));
        b102.setForeground(new java.awt.Color(102, 0, 153));
        b102.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b102ActionPerformed(evt);
            }
        });

        b103.setBackground(new java.awt.Color(102, 0, 102));
        b103.setForeground(new java.awt.Color(102, 0, 153));
        b103.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b103ActionPerformed(evt);
            }
        });

        b104.setBackground(new java.awt.Color(102, 0, 102));
        b104.setForeground(new java.awt.Color(102, 0, 153));
        b104.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b104ActionPerformed(evt);
            }
        });

        b105.setBackground(new java.awt.Color(102, 0, 102));
        b105.setForeground(new java.awt.Color(102, 0, 153));
        b105.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b105ActionPerformed(evt);
            }
        });

        b106.setBackground(new java.awt.Color(102, 0, 102));
        b106.setForeground(new java.awt.Color(102, 0, 153));
        b106.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b106ActionPerformed(evt);
            }
        });

        b107.setBackground(new java.awt.Color(102, 0, 102));
        b107.setForeground(new java.awt.Color(102, 0, 153));
        b107.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b107ActionPerformed(evt);
            }
        });

        b108.setBackground(new java.awt.Color(102, 0, 102));
        b108.setForeground(new java.awt.Color(102, 0, 153));
        b108.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b108ActionPerformed(evt);
            }
        });

        b109.setBackground(new java.awt.Color(102, 0, 102));
        b109.setForeground(new java.awt.Color(102, 0, 153));
        b109.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b109ActionPerformed(evt);
            }
        });

        b110.setBackground(new java.awt.Color(102, 0, 102));
        b110.setForeground(new java.awt.Color(102, 0, 153));
        b110.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b110ActionPerformed(evt);
            }
        });

        b111.setBackground(new java.awt.Color(102, 0, 102));
        b111.setForeground(new java.awt.Color(102, 0, 153));
        b111.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b111ActionPerformed(evt);
            }
        });

        b112.setBackground(new java.awt.Color(102, 0, 102));
        b112.setForeground(new java.awt.Color(102, 0, 153));
        b112.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b112ActionPerformed(evt);
            }
        });

        b113.setBackground(new java.awt.Color(102, 0, 102));
        b113.setForeground(new java.awt.Color(102, 0, 153));
        b113.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b113ActionPerformed(evt);
            }
        });

        b114.setBackground(new java.awt.Color(102, 0, 102));
        b114.setForeground(new java.awt.Color(102, 0, 153));
        b114.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b114ActionPerformed(evt);
            }
        });

        b115.setBackground(new java.awt.Color(102, 0, 102));
        b115.setForeground(new java.awt.Color(102, 0, 153));
        b115.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b115ActionPerformed(evt);
            }
        });

        b116.setBackground(new java.awt.Color(102, 0, 102));
        b116.setForeground(new java.awt.Color(102, 0, 153));
        b116.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b116ActionPerformed(evt);
            }
        });

        b117.setBackground(new java.awt.Color(102, 0, 102));
        b117.setForeground(new java.awt.Color(102, 0, 153));
        b117.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b117ActionPerformed(evt);
            }
        });

        b118.setBackground(new java.awt.Color(102, 0, 102));
        b118.setForeground(new java.awt.Color(102, 0, 153));
        b118.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b118ActionPerformed(evt);
            }
        });

        b119.setBackground(new java.awt.Color(102, 0, 102));
        b119.setForeground(new java.awt.Color(102, 0, 153));
        b119.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b119ActionPerformed(evt);
            }
        });

        b120.setBackground(new java.awt.Color(102, 0, 102));
        b120.setForeground(new java.awt.Color(102, 0, 153));
        b120.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b120ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout empaquetadoPanelLayout = new javax.swing.GroupLayout(empaquetadoPanel);
        empaquetadoPanel.setLayout(empaquetadoPanelLayout);
        empaquetadoPanelLayout.setHorizontalGroup(
            empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                        .addComponent(EMPAQUETADO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cem))
                    .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                        .addComponent(b115, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b116, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b117, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b118, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b119, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b120, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                                .addComponent(b109, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b110, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b111, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b112, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b113, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b114, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                                .addComponent(b103, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b104, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b105, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b106, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b107, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b108, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                                .addComponent(b97, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b98, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b99, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b100, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b101, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b102, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                                .addComponent(b91, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b92, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b93, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b94, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b95, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b96, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        empaquetadoPanelLayout.setVerticalGroup(
            empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(empaquetadoPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EMPAQUETADO)
                    .addComponent(cem))
                .addGap(10, 10, 10)
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(b96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b95, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b93, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b94, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b102, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b101, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b100, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b99, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b98, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b97, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b108, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b107, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b106, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b105, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b104, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b103, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b114, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b113, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b112, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b111, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b110, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b109, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(empaquetadoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b120, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b119, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b118, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b117, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b116, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b115, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        salidaPanel.setBackground(new java.awt.Color(255, 153, 153));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SALIDA:");

        csa.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        csa.setForeground(new java.awt.Color(255, 255, 255));
        csa.setText("00");

        b61.setBackground(new java.awt.Color(255, 0, 0));
        b61.setForeground(new java.awt.Color(0, 0, 204));
        b61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b61ActionPerformed(evt);
            }
        });

        b62.setBackground(new java.awt.Color(255, 0, 0));
        b62.setForeground(new java.awt.Color(0, 0, 204));
        b62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b62ActionPerformed(evt);
            }
        });

        b63.setBackground(new java.awt.Color(255, 0, 0));
        b63.setForeground(new java.awt.Color(0, 0, 204));
        b63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b63ActionPerformed(evt);
            }
        });

        b64.setBackground(new java.awt.Color(255, 0, 0));
        b64.setForeground(new java.awt.Color(0, 0, 204));
        b64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b64ActionPerformed(evt);
            }
        });

        b65.setBackground(new java.awt.Color(255, 0, 0));
        b65.setForeground(new java.awt.Color(0, 0, 204));
        b65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b65ActionPerformed(evt);
            }
        });

        b66.setBackground(new java.awt.Color(255, 0, 0));
        b66.setForeground(new java.awt.Color(0, 0, 204));
        b66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b66ActionPerformed(evt);
            }
        });

        b67.setBackground(new java.awt.Color(255, 0, 0));
        b67.setForeground(new java.awt.Color(0, 0, 204));
        b67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b67ActionPerformed(evt);
            }
        });

        b68.setBackground(new java.awt.Color(255, 0, 0));
        b68.setForeground(new java.awt.Color(0, 0, 204));
        b68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b68ActionPerformed(evt);
            }
        });

        b69.setBackground(new java.awt.Color(255, 0, 0));
        b69.setForeground(new java.awt.Color(0, 0, 204));
        b69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b69ActionPerformed(evt);
            }
        });

        b70.setBackground(new java.awt.Color(255, 0, 0));
        b70.setForeground(new java.awt.Color(0, 0, 204));
        b70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b70ActionPerformed(evt);
            }
        });

        b71.setBackground(new java.awt.Color(255, 0, 0));
        b71.setForeground(new java.awt.Color(0, 0, 204));
        b71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b71ActionPerformed(evt);
            }
        });

        b72.setBackground(new java.awt.Color(255, 0, 0));
        b72.setForeground(new java.awt.Color(0, 0, 204));
        b72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b72ActionPerformed(evt);
            }
        });

        b73.setBackground(new java.awt.Color(255, 0, 0));
        b73.setForeground(new java.awt.Color(0, 0, 204));
        b73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b73ActionPerformed(evt);
            }
        });

        b74.setBackground(new java.awt.Color(255, 0, 0));
        b74.setForeground(new java.awt.Color(0, 0, 204));
        b74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b74ActionPerformed(evt);
            }
        });

        b75.setBackground(new java.awt.Color(255, 0, 0));
        b75.setForeground(new java.awt.Color(0, 0, 204));
        b75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b75ActionPerformed(evt);
            }
        });

        b76.setBackground(new java.awt.Color(255, 0, 0));
        b76.setForeground(new java.awt.Color(0, 0, 204));
        b76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b76ActionPerformed(evt);
            }
        });

        b77.setBackground(new java.awt.Color(255, 0, 0));
        b77.setForeground(new java.awt.Color(0, 0, 204));
        b77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b77ActionPerformed(evt);
            }
        });

        b78.setBackground(new java.awt.Color(255, 0, 0));
        b78.setForeground(new java.awt.Color(0, 0, 204));
        b78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b78ActionPerformed(evt);
            }
        });

        b79.setBackground(new java.awt.Color(255, 0, 0));
        b79.setForeground(new java.awt.Color(0, 0, 204));
        b79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b79ActionPerformed(evt);
            }
        });

        b80.setBackground(new java.awt.Color(255, 0, 0));
        b80.setForeground(new java.awt.Color(0, 0, 204));
        b80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b80ActionPerformed(evt);
            }
        });

        b81.setBackground(new java.awt.Color(255, 0, 0));
        b81.setForeground(new java.awt.Color(0, 0, 204));
        b81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b81ActionPerformed(evt);
            }
        });

        b82.setBackground(new java.awt.Color(255, 0, 0));
        b82.setForeground(new java.awt.Color(0, 0, 204));
        b82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b82ActionPerformed(evt);
            }
        });

        b83.setBackground(new java.awt.Color(255, 0, 0));
        b83.setForeground(new java.awt.Color(0, 0, 204));
        b83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b83ActionPerformed(evt);
            }
        });

        b84.setBackground(new java.awt.Color(255, 0, 0));
        b84.setForeground(new java.awt.Color(0, 0, 204));
        b84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b84ActionPerformed(evt);
            }
        });

        b85.setBackground(new java.awt.Color(255, 0, 0));
        b85.setForeground(new java.awt.Color(0, 0, 204));
        b85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b85ActionPerformed(evt);
            }
        });

        b86.setBackground(new java.awt.Color(255, 0, 0));
        b86.setForeground(new java.awt.Color(0, 0, 204));
        b86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b86ActionPerformed(evt);
            }
        });

        b87.setBackground(new java.awt.Color(255, 0, 0));
        b87.setForeground(new java.awt.Color(0, 0, 204));
        b87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b87ActionPerformed(evt);
            }
        });

        b88.setBackground(new java.awt.Color(255, 0, 0));
        b88.setForeground(new java.awt.Color(0, 0, 204));
        b88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b88ActionPerformed(evt);
            }
        });

        b89.setBackground(new java.awt.Color(255, 0, 0));
        b89.setForeground(new java.awt.Color(0, 0, 204));
        b89.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b89ActionPerformed(evt);
            }
        });

        b90.setBackground(new java.awt.Color(255, 0, 0));
        b90.setForeground(new java.awt.Color(0, 0, 204));
        b90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b90ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout salidaPanelLayout = new javax.swing.GroupLayout(salidaPanel);
        salidaPanel.setLayout(salidaPanelLayout);
        salidaPanelLayout.setHorizontalGroup(
            salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salidaPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(salidaPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(csa))
                    .addGroup(salidaPanelLayout.createSequentialGroup()
                        .addComponent(b85, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b86, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b87, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b88, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b89, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b90, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(salidaPanelLayout.createSequentialGroup()
                                .addComponent(b79, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b80, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b81, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b82, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b83, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b84, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(salidaPanelLayout.createSequentialGroup()
                                .addComponent(b73, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b74, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b75, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b76, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b77, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b78, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(salidaPanelLayout.createSequentialGroup()
                                .addComponent(b67, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b68, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b69, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b70, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b71, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b72, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(salidaPanelLayout.createSequentialGroup()
                                .addComponent(b61, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b62, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b63, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b64, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b65, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b66, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        salidaPanelLayout.setVerticalGroup(
            salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salidaPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(csa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(b66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b64, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b72, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b71, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b70, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b69, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b68, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b67, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b78, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b77, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b76, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b75, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b74, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b73, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b84, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b83, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b82, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b81, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b80, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b79, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(salidaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b90, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b89, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b88, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b87, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b86, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b85, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        inventarioPanel.setBackground(new java.awt.Color(51, 102, 255));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("INVENTARIO:");

        cin.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        cin.setForeground(new java.awt.Color(255, 255, 255));
        cin.setText("00");

        b41.setBackground(new java.awt.Color(0, 0, 255));
        b41.setForeground(new java.awt.Color(0, 0, 204));
        b41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b41ActionPerformed(evt);
            }
        });

        b56.setBackground(new java.awt.Color(0, 0, 255));
        b56.setForeground(new java.awt.Color(0, 0, 204));
        b56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b56ActionPerformed(evt);
            }
        });

        b49.setBackground(new java.awt.Color(0, 0, 255));
        b49.setForeground(new java.awt.Color(0, 0, 204));
        b49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b49ActionPerformed(evt);
            }
        });

        b42.setBackground(new java.awt.Color(0, 0, 255));
        b42.setForeground(new java.awt.Color(0, 0, 204));
        b42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b42ActionPerformed(evt);
            }
        });

        b39.setBackground(new java.awt.Color(0, 0, 255));
        b39.setForeground(new java.awt.Color(0, 0, 204));
        b39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b39ActionPerformed(evt);
            }
        });

        b52.setBackground(new java.awt.Color(0, 0, 255));
        b52.setForeground(new java.awt.Color(0, 0, 204));
        b52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b52ActionPerformed(evt);
            }
        });

        b34.setBackground(new java.awt.Color(0, 0, 255));
        b34.setForeground(new java.awt.Color(0, 0, 204));
        b34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b34ActionPerformed(evt);
            }
        });

        b53.setBackground(new java.awt.Color(0, 0, 255));
        b53.setForeground(new java.awt.Color(0, 0, 204));
        b53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b53ActionPerformed(evt);
            }
        });

        b33.setBackground(new java.awt.Color(0, 0, 255));
        b33.setForeground(new java.awt.Color(0, 0, 204));
        b33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b33ActionPerformed(evt);
            }
        });

        b44.setBackground(new java.awt.Color(0, 0, 255));
        b44.setForeground(new java.awt.Color(0, 0, 204));
        b44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b44ActionPerformed(evt);
            }
        });

        b48.setBackground(new java.awt.Color(0, 0, 255));
        b48.setForeground(new java.awt.Color(0, 0, 204));
        b48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b48ActionPerformed(evt);
            }
        });

        b47.setBackground(new java.awt.Color(0, 0, 255));
        b47.setForeground(new java.awt.Color(0, 0, 204));
        b47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b47ActionPerformed(evt);
            }
        });

        b51.setBackground(new java.awt.Color(0, 0, 255));
        b51.setForeground(new java.awt.Color(0, 0, 204));
        b51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b51ActionPerformed(evt);
            }
        });

        b38.setBackground(new java.awt.Color(0, 0, 255));
        b38.setForeground(new java.awt.Color(0, 0, 204));
        b38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b38ActionPerformed(evt);
            }
        });

        b58.setBackground(new java.awt.Color(0, 0, 255));
        b58.setForeground(new java.awt.Color(0, 0, 204));
        b58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b58ActionPerformed(evt);
            }
        });

        b32.setBackground(new java.awt.Color(0, 0, 255));
        b32.setForeground(new java.awt.Color(0, 0, 204));
        b32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b32ActionPerformed(evt);
            }
        });

        b46.setBackground(new java.awt.Color(0, 0, 255));
        b46.setForeground(new java.awt.Color(0, 0, 204));
        b46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b46ActionPerformed(evt);
            }
        });

        b45.setBackground(new java.awt.Color(0, 0, 255));
        b45.setForeground(new java.awt.Color(0, 0, 204));
        b45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b45ActionPerformed(evt);
            }
        });

        b55.setBackground(new java.awt.Color(0, 0, 255));
        b55.setForeground(new java.awt.Color(0, 0, 204));
        b55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b55ActionPerformed(evt);
            }
        });

        b57.setBackground(new java.awt.Color(0, 0, 255));
        b57.setForeground(new java.awt.Color(0, 0, 204));
        b57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b57ActionPerformed(evt);
            }
        });

        b37.setBackground(new java.awt.Color(0, 0, 255));
        b37.setForeground(new java.awt.Color(0, 0, 204));
        b37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b37ActionPerformed(evt);
            }
        });

        b40.setBackground(new java.awt.Color(0, 0, 255));
        b40.setForeground(new java.awt.Color(0, 0, 204));
        b40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b40ActionPerformed(evt);
            }
        });

        b43.setBackground(new java.awt.Color(0, 0, 255));
        b43.setForeground(new java.awt.Color(0, 0, 204));
        b43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b43ActionPerformed(evt);
            }
        });

        b31.setBackground(new java.awt.Color(0, 0, 255));
        b31.setForeground(new java.awt.Color(0, 0, 204));
        b31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b31ActionPerformed(evt);
            }
        });

        b36.setBackground(new java.awt.Color(0, 0, 255));
        b36.setForeground(new java.awt.Color(0, 0, 204));
        b36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b36ActionPerformed(evt);
            }
        });

        b35.setBackground(new java.awt.Color(0, 0, 255));
        b35.setForeground(new java.awt.Color(0, 0, 204));
        b35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b35ActionPerformed(evt);
            }
        });

        b60.setBackground(new java.awt.Color(0, 0, 255));
        b60.setForeground(new java.awt.Color(0, 0, 204));
        b60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b60ActionPerformed(evt);
            }
        });

        b50.setBackground(new java.awt.Color(0, 0, 255));
        b50.setForeground(new java.awt.Color(0, 0, 204));
        b50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b50ActionPerformed(evt);
            }
        });

        b59.setBackground(new java.awt.Color(0, 0, 255));
        b59.setForeground(new java.awt.Color(0, 0, 204));
        b59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b59ActionPerformed(evt);
            }
        });

        b54.setBackground(new java.awt.Color(0, 0, 255));
        b54.setForeground(new java.awt.Color(0, 0, 204));
        b54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b54ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inventarioPanelLayout = new javax.swing.GroupLayout(inventarioPanel);
        inventarioPanel.setLayout(inventarioPanelLayout);
        inventarioPanelLayout.setHorizontalGroup(
            inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inventarioPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cin))
                    .addGroup(inventarioPanelLayout.createSequentialGroup()
                        .addComponent(b55, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b56, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b57, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b58, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b59, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(b60, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inventarioPanelLayout.createSequentialGroup()
                                .addComponent(b49, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b50, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b51, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b52, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b53, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b54, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(inventarioPanelLayout.createSequentialGroup()
                                .addComponent(b43, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b44, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b45, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b46, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b47, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b48, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inventarioPanelLayout.createSequentialGroup()
                                .addComponent(b37, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b39, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b40, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b41, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b42, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(inventarioPanelLayout.createSequentialGroup()
                                .addComponent(b31, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b32, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b33, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b34, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b35, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(b36, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );
        inventarioPanelLayout.setVerticalGroup(
            inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventarioPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cin))
                .addGap(10, 10, 10)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(b36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b34, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b42, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b41, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b40, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b39, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b37, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b48, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b47, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b46, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b45, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b44, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b43, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b54, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b53, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b52, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b51, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b50, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b49, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(inventarioPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b60, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b59, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b58, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b57, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b56, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b55, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("INICIO");

        contadorinicio.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        contadorinicio.setForeground(new java.awt.Color(0, 0, 0));
        contadorinicio.setText("00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(contadorinicio)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addComponent(contadorinicio)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("FINAL");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel4.setText("1");

        jLabel9.setText("1");

        jLabel10.setText("1");

        jLabel11.setText("1");

        jLabel12.setText("1");

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(salidaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(empaquetadoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(produccionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventarioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(69, 69, 69)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(240, 240, 240)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                                .addGap(27, 27, 27)
                                .addComponent(jLabel4)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inventarioPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(salidaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(339, 339, 339))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        menumonkeyFrame mmframe = new menumonkeyFrame();
        mmframe.setVisible(true);
        dispose();
        
        
    }//GEN-LAST:event_jButton1ActionPerformed
public String generarReporte() {
     menumonkeyFrame mn = new menumonkeyFrame();
    System.out.println("METODO");
    System.out.println(e1);
    System.out.println(e2);
    System.out.println(e3);
    System.out.println(e4);
    System.out.println(e5);
    
    StringBuilder sb = new StringBuilder();
        sb.append("<font font face=\"Arial\">");
        sb.append("<h2>").append("Reporte Costo De Simulación").append("</h2>");
        
        sb.append("<table border=\"2\">\n");
        sb.append("<tr>");
        sb.append("<td>").append("Costo Inventario").append("</td>");
        sb.append("<td>").append("Q. "+e1+".00").append("</td>");
        sb.append("</tr>");
        sb.append("<tr>");
        sb.append("<td>").append("Costo Producción").append("</td>");
        sb.append("<td>").append("Q. "+e2+".00").append("</td>");
        sb.append("</tr>");  
        sb.append("<tr>");
        sb.append("<td>").append("Costo Empaquetado").append("</td>");
        sb.append("<td>").append("Q. "+e3+".00").append("</td>");
        sb.append("</tr>");     
        sb.append("<tr>");
        sb.append("<td>").append("Costo Salida").append("</td>");
        sb.append("<td>").append("Q. "+e4+".00").append("</td>");
        sb.append("</tr>"); 
        sb.append("<tr>");
        sb.append("<td>").append("Costo Total").append("</td>"); 
        sb.append("<td>").append("Q. "+e5+".00").append("</td>");
        sb.append("</tr>"); 
        sb.append("</table>");
        sb.append("<h4>").append("PABLO ANDRES RODRIGUEZ LIMA - 202201947").append("</h4>");
        sb.append("</font>");
        //SE CIERRA
        return sb.toString();
}
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jLabel8.getText().equals("30")){
        e1 = jLabel4.getText();
        e2 = jLabel9.getText();
        e3 = jLabel10.getText();
        e4 = jLabel11.getText();
        e5 = jLabel12.getText();
        
        
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

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b3ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b4ActionPerformed

    private void b5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b5ActionPerformed

    private void b6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b6ActionPerformed

    private void b7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b7ActionPerformed

    private void b8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b8ActionPerformed

    private void b9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b9ActionPerformed

    private void b10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b10ActionPerformed

    private void b11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b11ActionPerformed

    private void b12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b12ActionPerformed

    private void b13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b13ActionPerformed

    private void b14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b14ActionPerformed

    private void b15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b15ActionPerformed

    private void b16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b16ActionPerformed

    private void b17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b17ActionPerformed

    private void b18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b18ActionPerformed

    private void b19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b19ActionPerformed

    private void b20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b20ActionPerformed

    private void b21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b21ActionPerformed

    private void b22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b22ActionPerformed

    private void b23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b23ActionPerformed

    private void b24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b24ActionPerformed

    private void b25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b25ActionPerformed

    private void b26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b26ActionPerformed

    private void b27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b27ActionPerformed

    private void b28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b28ActionPerformed

    private void b29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b29ActionPerformed

    private void b30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b30ActionPerformed

    private void b31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b31ActionPerformed

    private void b32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b32ActionPerformed

    private void b33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b33ActionPerformed

    private void b34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b34ActionPerformed

    private void b35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b35ActionPerformed

    private void b36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b36ActionPerformed

    private void b37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b37ActionPerformed

    private void b38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b38ActionPerformed

    private void b39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b39ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b39ActionPerformed

    private void b40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b40ActionPerformed

    private void b41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b41ActionPerformed

    private void b42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b42ActionPerformed

    private void b43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b43ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b43ActionPerformed

    private void b44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b44ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b44ActionPerformed

    private void b45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b45ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b45ActionPerformed

    private void b46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b46ActionPerformed

    private void b47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b47ActionPerformed

    private void b48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b48ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b48ActionPerformed

    private void b49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b49ActionPerformed

    private void b50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b50ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b50ActionPerformed

    private void b51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b51ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b51ActionPerformed

    private void b52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b52ActionPerformed

    private void b53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b53ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b53ActionPerformed

    private void b54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b54ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b54ActionPerformed

    private void b55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b55ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b55ActionPerformed

    private void b56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b56ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b56ActionPerformed

    private void b57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b57ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b57ActionPerformed

    private void b58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b58ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b58ActionPerformed

    private void b59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b59ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b59ActionPerformed

    private void b60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b60ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b60ActionPerformed

    private void b61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b61ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b61ActionPerformed

    private void b62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b62ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b62ActionPerformed

    private void b63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b63ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b63ActionPerformed

    private void b64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b64ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b64ActionPerformed

    private void b65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b65ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b65ActionPerformed

    private void b66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b66ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b66ActionPerformed

    private void b67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b67ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b67ActionPerformed

    private void b68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b68ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b68ActionPerformed

    private void b69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b69ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b69ActionPerformed

    private void b70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b70ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b70ActionPerformed

    private void b71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b71ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b71ActionPerformed

    private void b72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b72ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b72ActionPerformed

    private void b73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b73ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b73ActionPerformed

    private void b74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b74ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b74ActionPerformed

    private void b75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b75ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b75ActionPerformed

    private void b76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b76ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b76ActionPerformed

    private void b77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b77ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b77ActionPerformed

    private void b78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b78ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b78ActionPerformed

    private void b79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b79ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b79ActionPerformed

    private void b80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b80ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b80ActionPerformed

    private void b81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b81ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b81ActionPerformed

    private void b82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b82ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b82ActionPerformed

    private void b83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b83ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b83ActionPerformed

    private void b84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b84ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b84ActionPerformed

    private void b85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b85ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b85ActionPerformed

    private void b86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b86ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b86ActionPerformed

    private void b87ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b87ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b87ActionPerformed

    private void b88ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b88ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b88ActionPerformed

    private void b89ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b89ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b89ActionPerformed

    private void b90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b90ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b90ActionPerformed

    private void b91ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b91ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b91ActionPerformed

    private void b92ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b92ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b92ActionPerformed

    private void b93ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b93ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b93ActionPerformed

    private void b94ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b94ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b94ActionPerformed

    private void b95ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b95ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b95ActionPerformed

    private void b96ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b96ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b96ActionPerformed

    private void b97ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b97ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b97ActionPerformed

    private void b98ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b98ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b98ActionPerformed

    private void b99ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b99ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b99ActionPerformed

    private void b100ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b100ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b100ActionPerformed

    private void b101ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b101ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b101ActionPerformed

    private void b102ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b102ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b102ActionPerformed

    private void b103ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b103ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b103ActionPerformed

    private void b104ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b104ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b104ActionPerformed

    private void b105ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b105ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b105ActionPerformed

    private void b106ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b106ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b106ActionPerformed

    private void b107ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b107ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b107ActionPerformed

    private void b108ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b108ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b108ActionPerformed

    private void b109ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b109ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b109ActionPerformed

    private void b110ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b110ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b110ActionPerformed

    private void b111ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b111ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b111ActionPerformed

    private void b112ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b112ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b112ActionPerformed

    private void b113ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b113ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b113ActionPerformed

    private void b114ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b114ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b114ActionPerformed

    private void b115ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b115ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b115ActionPerformed

    private void b116ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b116ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b116ActionPerformed

    private void b117ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b117ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b117ActionPerformed

    private void b118ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b118ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b118ActionPerformed

    private void b119ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b119ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b119ActionPerformed

    private void b120ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b120ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_b120ActionPerformed

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
    private javax.swing.JButton b1;
    private javax.swing.JButton b10;
    private javax.swing.JButton b100;
    private javax.swing.JButton b101;
    private javax.swing.JButton b102;
    private javax.swing.JButton b103;
    private javax.swing.JButton b104;
    private javax.swing.JButton b105;
    private javax.swing.JButton b106;
    private javax.swing.JButton b107;
    private javax.swing.JButton b108;
    private javax.swing.JButton b109;
    private javax.swing.JButton b11;
    private javax.swing.JButton b110;
    private javax.swing.JButton b111;
    private javax.swing.JButton b112;
    private javax.swing.JButton b113;
    private javax.swing.JButton b114;
    private javax.swing.JButton b115;
    private javax.swing.JButton b116;
    private javax.swing.JButton b117;
    private javax.swing.JButton b118;
    private javax.swing.JButton b119;
    private javax.swing.JButton b12;
    private javax.swing.JButton b120;
    private javax.swing.JButton b13;
    private javax.swing.JButton b14;
    private javax.swing.JButton b15;
    private javax.swing.JButton b16;
    private javax.swing.JButton b17;
    private javax.swing.JButton b18;
    private javax.swing.JButton b19;
    private javax.swing.JButton b2;
    private javax.swing.JButton b20;
    private javax.swing.JButton b21;
    private javax.swing.JButton b22;
    private javax.swing.JButton b23;
    private javax.swing.JButton b24;
    private javax.swing.JButton b25;
    private javax.swing.JButton b26;
    private javax.swing.JButton b27;
    private javax.swing.JButton b28;
    private javax.swing.JButton b29;
    private javax.swing.JButton b3;
    private javax.swing.JButton b30;
    public javax.swing.JButton b31;
    public javax.swing.JButton b32;
    public javax.swing.JButton b33;
    public javax.swing.JButton b34;
    public javax.swing.JButton b35;
    public javax.swing.JButton b36;
    private javax.swing.JButton b37;
    private javax.swing.JButton b38;
    private javax.swing.JButton b39;
    private javax.swing.JButton b4;
    private javax.swing.JButton b40;
    private javax.swing.JButton b41;
    private javax.swing.JButton b42;
    private javax.swing.JButton b43;
    private javax.swing.JButton b44;
    private javax.swing.JButton b45;
    private javax.swing.JButton b46;
    private javax.swing.JButton b47;
    private javax.swing.JButton b48;
    private javax.swing.JButton b49;
    private javax.swing.JButton b5;
    private javax.swing.JButton b50;
    private javax.swing.JButton b51;
    private javax.swing.JButton b52;
    private javax.swing.JButton b53;
    private javax.swing.JButton b54;
    private javax.swing.JButton b55;
    private javax.swing.JButton b56;
    private javax.swing.JButton b57;
    private javax.swing.JButton b58;
    private javax.swing.JButton b59;
    private javax.swing.JButton b6;
    private javax.swing.JButton b60;
    private javax.swing.JButton b61;
    private javax.swing.JButton b62;
    private javax.swing.JButton b63;
    private javax.swing.JButton b64;
    private javax.swing.JButton b65;
    private javax.swing.JButton b66;
    private javax.swing.JButton b67;
    private javax.swing.JButton b68;
    private javax.swing.JButton b69;
    private javax.swing.JButton b7;
    private javax.swing.JButton b70;
    private javax.swing.JButton b71;
    private javax.swing.JButton b72;
    private javax.swing.JButton b73;
    private javax.swing.JButton b74;
    private javax.swing.JButton b75;
    private javax.swing.JButton b76;
    private javax.swing.JButton b77;
    private javax.swing.JButton b78;
    private javax.swing.JButton b79;
    private javax.swing.JButton b8;
    private javax.swing.JButton b80;
    private javax.swing.JButton b81;
    private javax.swing.JButton b82;
    private javax.swing.JButton b83;
    private javax.swing.JButton b84;
    private javax.swing.JButton b85;
    private javax.swing.JButton b86;
    private javax.swing.JButton b87;
    private javax.swing.JButton b88;
    private javax.swing.JButton b89;
    private javax.swing.JButton b9;
    private javax.swing.JButton b90;
    private javax.swing.JButton b91;
    private javax.swing.JButton b92;
    private javax.swing.JButton b93;
    private javax.swing.JButton b94;
    private javax.swing.JButton b95;
    private javax.swing.JButton b96;
    private javax.swing.JButton b97;
    private javax.swing.JButton b98;
    private javax.swing.JButton b99;
    private javax.swing.JLabel cem;
    private javax.swing.JLabel cin;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel produccionPanel;
    private javax.swing.JPanel salidaPanel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {        
        //TiempoTranscurrido.setText((String) arg);
        //contadorinicio.setText((String) arg); 
    }
    
}
