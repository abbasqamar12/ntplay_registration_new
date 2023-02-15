package com.antplay.antplayApp.models;

import com.antplay.antplayApp.utils.Const;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpRequest {
    @SerializedName("apikey")
    @Expose
    private String apikey;
    @SerializedName("numbers")
    @Expose
    private String numbers;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("message")
    @Expose
    private String message;

    public OtpRequest(String numbers, String message) {
        this.apikey = Const.OTP_KEY;
        this.numbers = numbers;
        this.sender = "ANTPLY";
        this.message = message;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
