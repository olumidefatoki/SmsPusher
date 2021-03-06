/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMSPusher;
import java.sql.*;
import java.util.LinkedList;
/**
 *
 * @author Ahmed
 */
public class DBUtils {
    private Connection conn;
    public DBUtils() 
    {
        try {
            getConnection();
        } catch (InstantiationException ex) {
         //   Log.l.fatalLog.fatal(DBUtils.class.getName()+  ex.getMessage());
            System.out.println(ex.getMessage());
        }
        catch (SQLException ex) 
            { 
         //   Log.l.fatalLog.fatal(DBUtils.class.getName()+  ex.getMessage());
                System.out.println(ex.getMessage());  }
        
    }
    private void getConnection() throws InstantiationException, SQLException {
            try 
            {                
                conn =null;
                Class.forName(Log.l.DB_DRIVER).newInstance();
                conn = DriverManager.getConnection(Log.l.DB_URL+Log.l.DB_NAME, Log.l.DB_USERNAME, Log.l.DB_PASSWORD);
              //  Log.l.infoLog.info("Local database connection Successful");       
            } 
            catch (IllegalAccessException ex) 
            {
              //  Log.l.fatalLog.fatal(DBUtils.class.getName()+  ex.getMessage());
                System.out.println(ex.getMessage());
            } 
            catch (SQLException ex) 
            {            
             //   Log.l.infoLog.info("Local database unreachable!");       
             //   Log.l.infoLog.info("Retrying after 5 seconds...");  
                System.out.println(ex.getMessage());
                 
                try 
                {
                    Thread.sleep(5000);
                } catch (InterruptedException ex1) {
                   // Log.l.fatalLog.fatal(DBUtils.class.getName()+  ex.getMessage());
                    System.out.println(ex1.getMessage());
                     
                }
                conn = null;
            } catch (ClassNotFoundException ex) {
                 //Log.l.fatalLog.fatal(DBUtils.class.getName()+  ex.getMessage());
                System.out.println(ex.getMessage());
                conn = null;
            }
        
     }
     boolean checkRecord( int statusId) {
            boolean isFound=false;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String GETMESSAGES = "SELECT * FROM outMessages WHERE statusID =? and dateInserted<now()  limit 1";      
                ps =  conn.prepareStatement(GETMESSAGES); 
                ps.setInt(1, statusId);
                rs = ps.executeQuery();
                if (rs.next()) {
                     isFound=true;
                }
                
                rs.close();
               } 
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
              }
                       
                finally {
                closeConnection(ps);
            }
             return isFound;      
    }
 public void closeConnection(PreparedStatement ps)
    {
        if (ps!=null && conn!=null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
              //   Log.l.fatalLog.fatal(DBUtils.class.getName()+  ex.getMessage());
                    System.out.println(ex);
                }
            }
    }
    int UpdateBucketId(int threadId, int BUCKET_SIZE,int pickedstatus,int pendingStatus ) {
            PreparedStatement ps = null;
             int result=0;
            try{
                 String sql= "Update outMessages  set bucketID= ? ,statusID=?, dateModified=now() where statusID=? and dateInserted<now() limit ?  " ;
                 ps= conn.prepareStatement(sql);
                 ps.setInt(1, threadId);
                 ps.setInt(2, pickedstatus);
                 ps.setInt(3, pendingStatus);
                 ps.setInt(4, BUCKET_SIZE);
                 result= ps.executeUpdate();
                }
             catch (SQLException ex) {
                 System.out.println(ex.getMessage());
                    }
            finally{
                closeConnection(ps);
            }
            return result;
        }
    LinkedList<Message> FectchRecord(int i, int myThreadid,int bucketSize) {
        LinkedList<Message> rec = new LinkedList<Message>();
        PreparedStatement ps = null;
        ResultSet rs = null;             
        try {
            String GETMESSAGES = "SELECT * FROM outMessages WHERE statusID =? and bucketID=?  limit ? ";      
            ps =  conn.prepareStatement(GETMESSAGES); 
            ps.setInt(1, i);
            ps.setInt(2, myThreadid);
            ps.setInt(3, bucketSize);
            rs = ps.executeQuery();
            while (rs.next()) {
              //  Log.l.infoLog.info("Info :  " + myThreadid +" has selected "+bucketSize+" records");
           Message msg = new Message();
           msg.setMessageId(rs.getInt(1));
           msg.setMessageContent(rs.getString(2));
           msg.setDestAddress(rs.getString(3).trim());
           msg.setSourceAddress(rs.getString(4));
           msg.setStatusId(rs.getInt(5));
           msg.setDateInserted(rs.getDate(6));
           msg.setNo_of_Retry(rs.getInt(7));
           msg.setMaxSend(rs.getInt(8));
           msg.setBucketId(rs.getInt(9));
           msg.setDateModified(rs.getDate(10));
           rec.add(msg);
            }
            rs.close();
           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
                    }finally {
            
            closeConnection(ps);

        }
         return rec;
    }

    void UpdateStausResult(Message msg, int i) {
        PreparedStatement ps = null;
         int result=0;
        try{
             String sql= "Update outMessages  set statusID=?, dateModified=now() where messageID =? " ;
             ps= conn.prepareStatement(sql);
             ps.setInt(1, i);
             ps.setInt(2, msg.getMessageId());
               result= ps.executeUpdate();
            }
         catch (SQLException ex) {
             System.out.println(ex.getMessage());
                    }
        finally{
            closeConnection(ps);
        }
    }
    void UpdateRetry(Message msg,int count) {
       PreparedStatement ps = null;
         int result=0;
        try{
             String sql= "Update outMessages  set no_Of_Retry=?,  dateModified=now() where messageID= ? " ;
             ps= conn.prepareStatement(sql);
             ps.setInt(1, count);
             ps.setInt(2, msg.getMessageId());
             result= ps.executeUpdate();
            }
         catch (SQLException ex) {
             System.out.println(ex.getMessage());
                    }
        finally{
            closeConnection(ps);
        }
    }

    void updatePendingRec(int PICKED_STATUS,int PENDING_STATUS) {
       PreparedStatement ps = null;
         int result=0;
        try{
             String sql= "Update outMessages  set statusID=?,bucketID=0,no_Of_retry=0  where statusID= ? " ;
             System.out.println(sql);
             ps= conn.prepareStatement(sql);             
             ps.setInt(1,PENDING_STATUS );
             ps.setInt(2,PICKED_STATUS);              
             result= ps.executeUpdate();
            }
         catch (Exception ex) {
             System.out.println(ex.getMessage()); 
                    }
       
        finally{
            closeConnection(ps);
        }
    }
void insert(Message msg) {
        PreparedStatement ps = null;
         int result=0;        
        try{ 
             String sql= "insert into outMessages  values (?,?,?,?,?,now(),?,?,?,now())" ;
             ps= conn.prepareStatement(sql);
             ps.setInt(1, msg.getMessageId());
             ps.setString(2, msg.getMessageContent());
             ps.setString(3, msg.getDestAddress());
             ps.setString(4, msg.getSourceAddress());
             ps.setInt(5, msg.getStatusId());
             ps.setInt(6,msg.getNo_of_Retry());
             ps.setInt(7,msg.getMaxSend());
             ps.setInt(8,msg.getBucketId());
             result= ps.executeUpdate();
             closeConnection(ps);
            }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
}

    void reverseStatus(Message msg, int PENDING_STATUS) {
        PreparedStatement ps = null;
         int result=0;
        try{
             String sql= "Update outMessages  set statusID=?,bucketID=0,no_Of_retry=0  where messageID= ? " ;
             ps= conn.prepareStatement(sql);
             ps.setInt(1,PENDING_STATUS );
             ps.setInt(2,msg.getMessageId());              
             result= ps.executeUpdate();
            }
         catch (SQLException ex) {
             System.out.println(ex.getMessage());
                    }
        finally{
            closeConnection(ps);
        }
    }
}