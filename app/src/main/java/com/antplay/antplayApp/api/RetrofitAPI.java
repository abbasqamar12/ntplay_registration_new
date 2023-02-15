package com.antplay.antplayApp.api;

import com.antplay.antplayApp.models.BulkDataRequest;
import com.antplay.antplayApp.models.OtpRequest;
import com.antplay.antplayApp.models.OtpResponse;
import com.antplay.antplayApp.models.SignUpResponse;
import com.antplay.antplayApp.models.SignUpResponseAntplay;
import com.antplay.antplayApp.models.SignupRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
   /* @POST("api-register/")
    Call<SignUpResponse> signupAPIRequest(@Body SignupRequest signupRequest);*/

    @POST("antplayApi.php/")
    Call<SignUpResponseAntplay> signupAPIRequest(@Body SignupRequest signupRequest);

    @POST("bulkantplayApi.php")
    Call<SignUpResponse> bulkSignupAPIRequest(@Body BulkDataRequest bulkDataRequest);

    @POST("send/")
    Call<OtpResponse> otpAPIRequest(@Body OtpRequest otpRequest);

}
