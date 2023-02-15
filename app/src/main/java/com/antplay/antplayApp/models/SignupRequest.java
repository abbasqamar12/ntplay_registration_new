package com.antplay.antplayApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupRequest {
   /* @SerializedName("key")
    @Expose
    private String key;*/
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
    @SerializedName("org")
    @Expose
    private String organisation;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("demoRequired")
    @Expose
    private String experience;
 /*   @SerializedName("img_name")
    @Expose
    private String imgName;
    @SerializedName("image")
    @Expose
    private String image;*/
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("suggestion")
    @Expose
    private String suggestion;

   /* public SignupRequest(String key,String firstName, String lastName, String email, String phoneNumber, String age, String state, String reference,String demoSelected ,String rating, String suggestion) {
        this.key = key;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.state = state;
        this.reference = reference;
        this.experience = demoSelected;
      *//*  this.imgName = imgName;
        this.image = imageName;*//*
        this.rating = rating;
        this.suggestion=suggestion;
    }
*/

    public SignupRequest(String firstName, String lastName, String email, String phoneNumber, String age, String state,String organisation, String reference, String experience, String rating, String suggestion) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.state = state;
        this.organisation = organisation;
        this.reference = reference;
        this.experience = experience;
        this.rating = rating;
        this.suggestion = suggestion;
    }

   /* public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }*/

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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    /*public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }*/
}
