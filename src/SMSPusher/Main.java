/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMSPusher;

/**
 *
 * @author Ahmed
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        // TODO code application logic here
//        Log.l.infoLog.info("SMSPusher is starting up");
        Handler hand = new Handler();     
        hand.startThreads(Log.l.NO_OF_THREAD);   
    }
}
