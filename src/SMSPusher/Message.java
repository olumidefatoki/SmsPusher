/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SMSPusher;

import java.sql.Date;

/**
 *
 * @author Ahmed
 */
public class Message {
    private int messageId;
    private int statusId;
    private int no_of_Retry;
    private int maxSend;
    private int bucketId;
    private String messageContent;
    private String destAddress;
    private String sourceAddress;
    private Date dateInserted;
    private Date dateModified;

    /**
     * @return the messageId
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the statusId
     */
    public int getStatusId() {
        return statusId;
    }

    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /**
     * @return the no_of_Retry
     */
    public int getNo_of_Retry() {
        return no_of_Retry;
    }

    /**
     * @param no_of_Retry the no_of_Retry to set
     */
    public void setNo_of_Retry(int no_of_Retry) {
        this.no_of_Retry = no_of_Retry;
    }

    /**
     * @return the maxSend
     */
    public int getMaxSend() {
        return maxSend;
    }

    /**
     * @param maxSend the maxSend to set
     */
    public void setMaxSend(int maxSend) {
        this.maxSend = maxSend;
    }

    /**
     * @return the bucketId
     */
    public int getBucketId() {
        return bucketId;
    }

    /**
     * @param bucketId the bucketId to set
     */
    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    /**
     * @return the messageContent
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * @param messageContent the messageContent to set
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * @return the destAddress
     */
    public String getDestAddress() {
        return destAddress;
    }

    /**
     * @param destAddress the destAddress to set
     */
    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * @return the sourceAddress
     */
    public String getSourceAddress() {
        return sourceAddress;
    }

    /**
     * @param sourceAddress the sourceAddress to set
     */
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * @return the dateInserted
     */
    public Date getDateInserted() {
        return dateInserted;
    }

    /**
     * @param dateInserted the dateInserted to set
     */
    public void setDateInserted(Date dateInserted) {
        this.dateInserted = dateInserted;
    }

    /**
     * @return the dateModified
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    
       
}
