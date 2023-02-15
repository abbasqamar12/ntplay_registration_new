package com.antplay.antplayApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpResponse {


    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("batch_id")
    @Expose
    private Long batchId;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("num_messages")
    @Expose
    private Integer numMessages;
    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("receipt_url")
    @Expose
    private String receiptUrl;
    @SerializedName("custom")
    @Expose
    private String custom;
    @SerializedName("messages")
    @Expose
    private List<MessageListItem> messages = null;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getNumMessages() {
        return numMessages;
    }

    public void setNumMessages(Integer numMessages) {
        this.numMessages = numMessages;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public List<MessageListItem> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageListItem> messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
