/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMSPusher;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER-PC
 */
public class tesing {
    public static void main(String[] args) {
        try {
             String responseMessage="";
             int responseCode=0;
            StringBuilder param = new StringBuilder();
            //http://94.229.79.114:36000/proxySenders/proxySender.php?username=foo&password=bar&source=HEADER&destination=PHONENUMBER&message=MESSAGE
               String HEADER ="FIKS/FADAMA",PHONENUMBER="2348060099476",MESSAGE="hello Olumide. You are born to be great";
             param.append("http://omfb.zoedeck.net/cellulant/index.php?Action=AccountQuery");
            param.append("&RefId=").append("33434555");
            param.append("&phoneNumber=").append("08012345671");   
              System.out.println(param);
              URL myUrl= new URL(param.toString());
               System.out.println(myUrl.toString());
               HttpURLConnection response= (HttpURLConnection) myUrl.openConnection();
               
               responseCode=response.getResponseCode();
               System.out.println(responseCode);
               responseMessage= response.getResponseMessage();
               System.out.println(responseMessage);
        } catch (IOException ex) {
            Logger.getLogger(tesing.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
