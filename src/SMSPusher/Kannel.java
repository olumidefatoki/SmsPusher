
/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMSPusher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

/**
 *
 * @author Ahmed
 */
public class Kannel {
    //private final String dKey = "w3aF1Bank";

    
    static String urlComposer(Message msg) throws UnsupportedEncodingException {
        StringBuilder param = new StringBuilder();
  
        param.append("https://www.lordaragorn.cellulant.com.ng/SmartWallet/Proxy/SendSMS?Pin=8071&SourcePhone=08171717463&Channel=12&PlatformId=001&Mode=3");
        param.append("&Header=").append(URLEncoder.encode(msg.getSourceAddress().trim(), "UTF-8"));
        param.append("&Recipients=").append(msg.getDestAddress());
        param.append("&Text=").append(URLEncoder.encode(msg.getMessageContent(), "UTF-8"));
       // param.append("&dlr-mask=").append("31");
        return param.toString();
    }

    static int sendSms(Message msg) {
        int responseCode = 0;
        String responseMessage = "";
        try {
            URL myUrl = new URL(urlComposer(msg));
            System.out.println("Invoke URL " + myUrl.toString());
          //  Log.l.infoLog.info("Invoke URL " + myUrl.toString());           
            HttpURLConnection response = (HttpURLConnection) myUrl.openConnection();
            responseCode = response.getResponseCode();
            responseMessage = response.getResponseMessage();
          //  Log.l.infoLog.info("  Response  for " + msg.getMessageId() + " --- HTTP_STATUS_CODE - " + responseCode + "--- HTTP_STATUS_REASON - " + responseMessage);
            System.out.println("Response  for " + msg.getMessageId() + " --- HTTP_STATUS_CODE - " + responseCode + "--- HTTP_STATUS_REASON - " + responseMessage);
        } 
        catch (IOException ex) {
          //  Log.l.errorLog.fatal(Kannel.class.getName() + ex);
          
            responseCode = 111;
        }
        return responseCode;
    }

    public static String formatDestination(String phone) {

        String destinationMsisdn = phone.trim(), pre = "234";
        String formattedDest = "invalid";
        try {
            if (destinationMsisdn.length() == 14) {
                if (destinationMsisdn.startsWith("+234")) {
                    if (Pattern.matches("[+][2][3][4][0-9]*", destinationMsisdn)) {
                        formattedDest = destinationMsisdn;
                    }
                }
            } else if (destinationMsisdn.length() == 13) {
                if (destinationMsisdn.startsWith("234")) {
                    if (Pattern.matches("[2][3][4][0-9]*", destinationMsisdn)) {
                        formattedDest = destinationMsisdn;

                    }
                }
            } else if (destinationMsisdn.length() == 11) {
                if (destinationMsisdn.startsWith("0")) {
                    if (Pattern.matches("[0-9]*", destinationMsisdn));
                    formattedDest = destinationMsisdn.replaceFirst("0", "234");
                    
                }
            } else if (destinationMsisdn.length() == 10) {
                if (Pattern.matches("[0-9]*", destinationMsisdn));
                formattedDest = pre.concat(destinationMsisdn);
                
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        //    Log.l.fatalLog.fatal(DBUtils.class.getName() + e);
        }
        return formattedDest;

    }
}
