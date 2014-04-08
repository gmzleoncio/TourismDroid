package socketserver;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import javax.swing.*;

/**
 * Programa autónomo para Servidor.
 * 
 * @author Leoncio Gómez
 * @version 2014
 */

public class Server extends Frame implements ActionListener, WindowListener{
  
    // 	Definición de los objetos de la interfaz gráfica  
    private JLabel l1,l2,l3,l4,l5,l6, totalRegular, totalPremium,currentRegular, currentPremium, officeRegular,officePremium;
    private JButton nextRegular, nextPremium;
    private JPanel p1,p2,p3;
    public static int cRegular = 0;
    public static int cPremium = 0;
    public static int tRegular = 0;
    public static int tPremium = 0;
    public int oRegular = 0;
    public int oPremium  = 0;
    
    /**
    * Método principal: se ejecuta al iniciar el programa
    */
    
        public static void main(String[] args) {
		
        Server mVentana = new Server();
        mVentana.setSize(500,500);
        mVentana.setVisible(true);
        
        /**
         * Inicialización de Servidor en Puerto PORT
         */
                
        ServerSocket SS = null;
        Socket cliente = null;
        int PORT = 7060;
                
            boolean escuchando = true;
              
            try{
                SS = new ServerSocket(PORT);
                
                    while(escuchando){
                        cliente = SS.accept();
                         new Atiende(cliente).start();
                    }
	 	SS.close();
                        
   		}catch (IOException e){
   			System.err.println(e.getMessage());
   			System.exit(1);
   		}
    }
        /**
         * Constructor: Inicializar Frame
         */
    
        public Server(){
            setTitle("SERVIDOR TOURISMDROID");
            setLayout(new GridLayout(5,1,1,1));
            this.addWindowListener(this);
            p1 = new JPanel(new GridLayout(2,2,1,1));
            p2 = new JPanel(new GridLayout(2,3,1,1));
            p3 = new JPanel(new GridLayout(1,2,1,1));
            l1 = new JLabel("TOTAL CLIENTS", JLabel.CENTER);
            l2 = new JLabel("Regular" , JLabel.CENTER);
            l3 = new JLabel("Premium", JLabel.CENTER);
            l4 = new JLabel("CURRENTS CLIENTS", JLabel.CENTER);
            l5 = new JLabel("Regular", JLabel.CENTER);
            l6 = new JLabel("Premium", JLabel.CENTER);
            totalRegular = new JLabel("", JLabel.CENTER);
            totalPremium = new JLabel("", JLabel.CENTER);
            currentRegular = new JLabel("", JLabel.CENTER);
            currentPremium = new JLabel("", JLabel.CENTER);
            officeRegular = new JLabel("", JLabel.CENTER);
            officePremium = new JLabel("", JLabel.CENTER);
            
            l1.setFont(new Font("SansSerif", Font.BOLD, 14));
            l4.setFont(new Font("SansSerif", Font.BOLD, 14));
            
            nextRegular = new JButton("Next Regular");
            nextPremium = new JButton("Next Premium");
            
            nextRegular.addActionListener(this);
            nextPremium.addActionListener(this);
            
          
                     
            p1.add(l2);
            p1.add(totalRegular);
            p1.add(l3);
            p1.add(totalPremium);
            
            p2.add(l5);
            p2.add(currentRegular);
            p2.add(officeRegular);
            p2.add(l6);
            p2.add(currentPremium);
            p2.add(officePremium);
            
            p3.add(nextRegular);
            p3.add(nextPremium);
            
            add(l1);
            add(p1);
            add(l4);
            add(p2);
            add(p3);
            
            Timer timer = new Timer (2000, new ActionListener () { 
                @Override
                public void actionPerformed(ActionEvent e) { 
                    totalRegular.setText("" + tRegular);
                    totalPremium.setText("" + tPremium);
                    currentRegular.setText("" + cRegular);
                    currentPremium.setText("" + cPremium);
                    officeRegular.setText("Office: " + oRegular);
                    officePremium.setText("Office: "+ oPremium);
                } 
            }); 
            timer.start();
            
    }

  /**
  * Cazador de eventos
  * @param e El evento ocurrido
  */
   @Override
   public void actionPerformed(ActionEvent e){
       if (e.getSource() == nextRegular){
           cRegular += 1;
           setOffice(1);
       }
       if(e.getSource() == nextPremium){
           cPremium +=1;
           setOffice(2);
       }
   }
   
   /**
    * Establecer Oficina (5 Oficinas Regular, 5 Oficinas Premium)
    */   
   
   public int setOffice(int type){
           
        switch(type){
            case 1: 
                if (oRegular < 5){
                    oRegular +=1;
                    return oRegular;
                }else{
                    oRegular = 1;
                    return oRegular;
                }
            case 2:
                 if (oPremium < 5){
                    oPremium +=1;
                    return oPremium;
                }else{
                    oPremium = 1;
                    return oPremium;
                }
            default:
                return 1;
         }
    }
   
   
   /**
    * Captura windowClosing cierra la aplicación al capturar este evento.
    * Eventos de ventana, en este caso solo usamos WindowClosing
    * @param e WindowEvent
    */ 
   @Override
    public void windowClosing(WindowEvent e){
    
        System.exit(0);
    }
    
    @Override
    public void windowIconified(WindowEvent e){
	
    }
    
    @Override
    public void windowOpened(WindowEvent e){

    }    
    
    @Override
    public void windowClosed(WindowEvent e) {

    }
    
    @Override
    public void windowDeiconified(WindowEvent e) {

    }
    
    @Override
    public void windowActivated(WindowEvent e) {

    }    
    
    @Override
    public void windowDeactivated(WindowEvent e) {

    }   
}

class Atiende extends Thread{
    private DataInputStream dis;
    private DataOutputStream dos;
    private String received;
    private String type;
    private Socket client = null;
    StringTokenizer  st;
    String nombreyDirIP;
    
    public Atiende(Socket cliente){
            this.client = cliente;
            nombreyDirIP = this.client.getInetAddress().toString();
    }
    
    public void run(){

        try{
            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
            dis = new DataInputStream(new DataInputStream(client.getInputStream()));
            dos = new DataOutputStream(client.getOutputStream());
            do{
                received = dis.readUTF();
              
                switch (received){
                    case "1": 
                        Server.tRegular +=1;
                        type = "Regular Client";
                        dos.writeUTF(""+Server.tRegular);
                    break;
                    case "2": 
                        Server.tPremium +=1;
                        type = "Premium Client";
                        dos.writeUTF(""+Server.tPremium);
                    break;
                    default: type ="Nothing";
                    break;
                }
                
            }while(type.equals("Nothing") == false);
            dis.close();
            dos.close();
    }catch(IOException e){
            System.out.println(e.getMessage());
            System.exit(1);
    }
 }
    

}