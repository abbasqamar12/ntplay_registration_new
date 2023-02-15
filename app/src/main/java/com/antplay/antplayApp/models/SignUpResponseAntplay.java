package com.antplay.antplayApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpResponseAntplay {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("status")
    @Expose
    private boolean status;

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
