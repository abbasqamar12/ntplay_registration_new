package com.antplay.antplayApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BulkDataRequest {

    @SerializedName("bulk_data")
    @Expose
    private List<SignupRequest> bulkData = null;

    public BulkDataRequest(List<SignupRequest> bulkData) {
        this.bulkData = bulkData;
    }

    public List<SignupRequest> getBulkData() {
        return bulkData;
    }

    public void setBulkData(List<SignupRequest> bulkData) {
        this.bulkData = bulkData;
    }
}
