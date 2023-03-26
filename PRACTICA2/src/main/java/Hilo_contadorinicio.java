/*
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Pablo Rodriguez
 

public class Hilo_contadorinicio extends Observable implements Runnable{
        public simulacionFrame sf = new simulacionFrame();
        public int contador=30;
    
public Hilo_contadorinicio(int contador){
    
    this.contador = contador;   
    
}    
    
    public void run(){
        
        String contadorini = "";
        
        try{
            
            while(true){
            
                contadorini = "";
                
                if(contador<10){
                    contadorini += "0" + contador;
                }else{
                    contadorini += contador;
                }
                
                
                this.setChanged();
                this.notifyObservers(contadorini);
                this.clearChanged();
                Thread.sleep(700);
                
                contador--;
                
                if(contador==-1){
                    Thread.sleep(1000000000);
                    
                }
                
            }
            
            }catch (InterruptedException exs) {
                Logger.getLogger(Hilo_contadorinicio.class.getName()).log(Level.SEVERE, null, exs);
        }
        
    }  
}
*/