package SMSPusher;

import java.util.LinkedList;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class Pusher extends Thread {

    private LinkedList smsList;
    private DBUtils dbService;
    private int threadId;
    private DBUtils dbAccess;

    Pusher(int threadId) {
        this.threadId = threadId + 1;
        dbAccess = new DBUtils();
    }

    private synchronized void doJob() {
        while (true) {
            boolean unprocessedRec = false;
            LinkedList<Message> threadQueue = new LinkedList<Message>();
            //ThreadId is checking for unprocessed record 
            unprocessedRec = dbAccess.checkRecord(Log.l.PENDING_STATUS);
            //     Log.l.infoLog.info(" | ThreadId is checking for unprocessed record ");
            //    System.out.println(" | ThreadId   "+threadId +"  is checking for unprocessed record ");
            if (unprocessedRec) {
                //Updateing the record as picked and allocating bucketId
                dbAccess.UpdateBucketId(threadId, Log.l.BUCKET_SIZE, Log.l.PICKED_STATUS, Log.l.PENDING_STATUS);

                //select record with a particular bucketId
                threadQueue = dbAccess.FectchRecord(Log.l.PICKED_STATUS, threadId, Log.l.BUCKET_SIZE);
             //   Log.l.infoLog.info(" | ThreadId " + threadId + " has Picked " + threadQueue.size() + " unprocessed record  from the database");
              //     System.out.println("| ThreadId "+threadId+ " has Picked "+threadQueue.size()+" unprocessed record  from the database");
                for (Message msg : threadQueue) {
                    // Log.l.infoLog.info(" | MessageId "+msg.getMessageId()+ " has Picked   for process");
                    //   System.out.println("| MessageId "+msg.getMessageId()+ " has Picked   for process");
                    process(msg);

                }
            } else {
                try {
                    
                  //  Log.l.infoLog.info("Thread is going to sleep  for " + Log.l.SLEEP_TIME / 1000 + " seconds ");
                  //  System.out.println("Thread is going to sleep  for " + Log.l.SLEEP_TIME / 1000 + " seconds ");
                    Thread.currentThread().sleep(Log.l.SLEEP_TIME);
                } catch (Exception ex) {
                  //  Log.l.errorLog.info(Pusher.class.getName(), ex);
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @Override
    public void run() {
        doJob();
    }

    public void process(Message msg) {
        String status = "";
        int result = 0;
        try {
            status = Kannel.formatDestination(msg.getDestAddress());
            if (status.equalsIgnoreCase("invalid")) {
              //  Log.l.infoLog.info(" | Wrong  Phone Number,set StatusId to Failed for " + msg.getMessageId());
               System.out.println("| Wrong  Phone Number,set StatusId to Failed for " + msg.getMessageId());
                dbAccess.UpdateStausResult(msg, Log.l.FAILED_STATUS);
            } else {
             //   Log.l.infoLog.info(" | correct  Phone Number " + msg.getDestAddress() + " Accepted for sending");
             //   System.out.println(" | correct  Phone Number " + msg.getDestAddress() + " Accepted for sending");
                msg.setDestAddress(status);
                result = Kannel.sendSms(msg);
                if (result == 200 || result == 202) {
                    dbAccess.UpdateStausResult(msg, Log.l.SUCCESS_STATUS);
                } else {
               //     Log.l.infoLog.info(" message not sent. Reversing Status back to pending status");
                    System.out.println("message not sent. Reversing Status back to pending status");
                    dbAccess.reverseStatus(msg, Log.l.PENDING_STATUS);
                }
            }
        } catch (Exception e) {
        //    Log.l.errorLog.error(" message not sent... try sending again later" + e);
            System.out.println(e.getMessage());
        }
    }
}
