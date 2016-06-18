/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMSPusher;


/**
 *
 * @author Ahmed
 */
public class Handler {

    void startThreads(int NO_OF_THREAD) {
       Pusher[]push= new Pusher[ NO_OF_THREAD];
       DBUtils myDbAccess= new DBUtils();
       myDbAccess.updatePendingRec(Log.l.PICKED_STATUS,Log.l.PENDING_STATUS);
      //  Log.l.infoLog.info(" | update all  picked record to unprocessed ");
       
        for (int i = 0; i <  NO_OF_THREAD; i++) {
            push[i]= new Pusher(i);
            push[i].start();
           
         }
    }
  
}
