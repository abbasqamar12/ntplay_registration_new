package com.antplay.antplayApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageListItem {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("recipient")
    @Expose
    private Long recipient;

}
