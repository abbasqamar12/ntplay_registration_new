package com.antplay.antplayApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupRequest {
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("demoRequired")
    @Expose
    private String demoRequired;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("suggestion")
    @Expose
    private String suggestion;
    @SerializedName("org")
    @Expose
    private String org;

    public SignupRequest(String firstName, String lastName, String email, String phoneNumber, String age, String state, String reference, String demoRequired, String rating, String suggestion, String org) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.state = state;
        this.reference = reference;
        this.demoRequired = demoRequired;
        this.rating = rating;
        this.suggestion = suggestion;
        this.org = org;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDemoRequired() {
        return demoRequired;
    }

    public void setDemoRequired(String demoRequired) {
        this.demoRequired = demoRequired;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
