
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Pablo Rodriguez
 */

public class Hilo_TiempoTrans extends Observable implements Runnable{
        public simulacionFrame sf = new simulacionFrame();
        public int min=0, seg=0, hora=0;
    
public Hilo_TiempoTrans(int hora, int min, int seg){
    
    this.hora = hora;
    this.min = min;
    this.seg = seg;
   
    
    
}    
    
    public void run(){
        
        String tiempo = "";
        
        try{
            
            while(true){
            
                tiempo = "";
                
                if(hora<10){
                    tiempo += "0" + hora;
                }else{
                    tiempo += hora;
                }
                
                tiempo += ":";
                
                if(min<10){
                    tiempo += "0" + min;
                }else{
                    tiempo += min;
                }
                
                tiempo += ":";
                
                if(seg<10){
                    tiempo += "0" + seg;
                }else{
                    tiempo += seg;
                }
                
                this.setChanged();
                this.notifyObservers(tiempo);
                this.clearChanged();
                Thread.sleep(700);
                
                seg++;
                
                if(seg==60){
                    min++;
                    seg=0;
                    if(min==60){
                        min=0;
                        hora++;
                        if(hora==24){
                            hora=0;
                        }
                    }
                }
                
            }
            
            }catch (InterruptedException ex) {
                Logger.getLogger(Hilo_TiempoTrans.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
}
