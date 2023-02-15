package com.antplay.antplayApp.activities;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.antplay.antplayApp.R;
import com.antplay.antplayApp.api.APIClient;
import com.antplay.antplayApp.api.RetrofitAPI;
import com.antplay.antplayApp.dialog.VerifyOTPDialog;
import com.antplay.antplayApp.listeners.GetUsersAsyncListener;
import com.antplay.antplayApp.listeners.OtpVerifyListener;
import com.antplay.antplayApp.models.BulkDataRequest;
import com.antplay.antplayApp.models.OtpRequest;
import com.antplay.antplayApp.models.OtpResponse;
import com.antplay.antplayApp.models.SignUpResponse;
import com.antplay.antplayApp.models.SignUpResponseAntplay;
import com.antplay.antplayApp.models.SignupRequest;
import com.antplay.antplayApp.networkdetection.NetworkAvailability;
import com.antplay.antplayApp.networkdetection.NetworkReceiver;
import com.antplay.antplayApp.roomdb.AntPlayRegDatabase;
import com.antplay.antplayApp.roomdb.ComiconUser;
import com.antplay.antplayApp.roomdb.ComiconUserDao;
import com.antplay.antplayApp.roomdb.UserDao;
import com.antplay.antplayApp.utils.Const;
import com.antplay.antplayApp.utils.SendOtp;
import com.antplay.antplayApp.utils.SendOtpAsyncTask;
import com.antplay.antplayApp.utils.SnackBarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NetworkAvailability, AdapterView.OnItemSelectedListener, GetUsersAsyncListener, OtpVerifyListener {

    EditText edtFirstName, edtLastName, edtEmail, edtMobile, edtUserAge, edtState, edtSuggestion;
    CheckBox checkBoxTerms, checkBoxDemo;
    TextView txtSubmit;
    String referenceSelected;
    String[] referenceType = {"------Select One----", "CII", "YOU TUBE", "DISCORD", "INSTAGRAM", "FACEBOOK", "GOOGLE", "BING", "QUORA", "OTHER"};
    Spinner referenceSpinner;
    RatingBar ratingBar;
    float ratingExperience;

    String firstName, lastName, userMobile, userEmail, userAge, state, usersSuggestion, demoRequired, referenceText, ratingCount;
    private ProgressBar loadingPB;

    private BroadcastReceiver mNetworkReceiver;
    boolean isNetworkAvailable = false;
    boolean isDataListUpdatedOnServer = false;

    AntPlayRegDatabase db;
    UserDao userDao;
    ComiconUserDao comiconUserDao;

    String otp = "0000";
    boolean isOtpVerified = false;
    VerifyOTPDialog verifyOTPDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtMobile = findViewById(R.id.edtMobile);
        edtEmail = findViewById(R.id.edtEmail);
        edtUserAge = findViewById(R.id.edtAge);
        edtState = findViewById(R.id.edtState);
        edtSuggestion = findViewById(R.id.edtFeedback);

        referenceSpinner = findViewById(R.id.spinReference);
        checkBoxTerms = findViewById(R.id.checkbox);
        checkBoxDemo = findViewById(R.id.checkboxDemo);

        ratingBar = findViewById(R.id.ratingBar);

        txtSubmit = findViewById(R.id.txtSubmit);

        loadingPB = (ProgressBar) findViewById(R.id.progressBar);
        referenceSpinner.setOnItemSelectedListener(MainActivity.this);
        setSpinnerAdapter();
        setOnClickListener();
        setDatabase();
        mNetworkReceiver = new NetworkReceiver(this);
        registerNetworkBroadcastForNougat();


    }


    private void setDatabase() {
        db = Room.databaseBuilder(getApplicationContext(),
                AntPlayRegDatabase.class, "database-name").build();
        //userDao = db.userDao();
        comiconUserDao = db.comiconUserDao();

    }

    private void setOnClickListener() {
        txtSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSubmit:
             //   new VerifyOTPDialog(MainActivity.this,MainActivity.this).show();

                if (validateTextFields()) {
                    firstName = edtFirstName.getText().toString();
                    lastName = edtLastName.getText().toString();
                    userMobile = edtMobile.getText().toString();
                    userEmail = edtEmail.getText().toString();
                    userAge = edtUserAge.getText().toString();
                    state = edtState.getText().toString();

                    ratingExperience = ratingBar.getRating();
                    if (checkBoxDemo.isChecked()) {
                        demoRequired = "Yes";
                    } else {
                        demoRequired = "No";
                    }
                    ratingCount = String.valueOf(ratingExperience);
                    if (referenceSelected.equals("------Select One----")) {
                        referenceText = "No Reference";
                    } else {
                        referenceText = referenceSelected;
                    }
                    if (edtSuggestion.getText() == null || edtSuggestion.getText().equals("") || edtSuggestion.getText().length() < 1) {
                        usersSuggestion = "No Suggestions";
                    } else {
                        usersSuggestion = edtSuggestion.getText().toString();
                    }

                    if (isNetworkAvailable) {
                        isDataListUpdatedOnServer = true;

                        callSignUpAPIAntPlay(firstName, lastName, userMobile, userEmail, userAge, state, demoRequired, ratingCount, referenceText,usersSuggestion);
                        // callSendOtpAPI(userMobile, generateRandomNumber());
                    } else {
                        isDataListUpdatedOnServer = false;
                        //saveUseToLocalDB(userName, userMobile, userEmail, referenceSelected);
                        saveUseToLocalDB(Const.API_KEY, firstName, lastName, userEmail, userMobile, userAge, state, referenceText, demoRequired, ratingCount, usersSuggestion);
                    }
                }
                break;

        }
    }

    private String generateRandomNumber() {
        int min = 1000;
        int max = 9999;
        int randomNumber = new Random().nextInt(max - min)+1000;
        otp = String.valueOf(randomNumber);
        Toast.makeText(MainActivity.this, "Random: " + randomNumber, Toast.LENGTH_LONG).show();
        return otp;
    }

    private void callSignUpAPIAntPlay(String firstName, String lastName, String userMobile, String userEmail, String userAge, String state, String demoRequired, String ratingCount, String referenceText, String usersSuggestion) {
        loadingPB.setVisibility(View.VISIBLE);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance(Const.DEV_URL_ANT).create(RetrofitAPI.class);
        //SignupRequest signUpRequestModel = new SignupRequest(Const.API_KEY, firstName, lastName, userEmail, userMobile, userAge, state, referenceText, demoRequired, ratingCount, usersSuggestion);
        SignupRequest signUpRequestModel = new SignupRequest(firstName, lastName, userEmail, userMobile, userAge, state, "antplay",referenceText, demoRequired, ratingCount, usersSuggestion);
        Call<SignUpResponseAntplay> call = retrofitAPI.signupAPIRequest(signUpRequestModel);
        call.enqueue(new Callback<SignUpResponseAntplay>() {
            @Override
            public void onResponse(Call<SignUpResponseAntplay> call, Response<SignUpResponseAntplay> response) {
                assert response.body() != null;
                if (response.body().getStatus()) {
                    loadingPB.setVisibility(View.GONE);
                    Log.d(TAG, "" + response.body().getMessage());
                    // Toast.makeText(MainActivity.this, "Congratulations! you are successfully registered with Bingo", Toast.LENGTH_LONG).show();
                    //SharedPreferenceUtils.saveString(LoginActivity.this, Const.ACCESS_TOKEN, response.body().getToken());
                    Intent i = new Intent(MainActivity.this, ThanksActivity.class);
                    startActivity(i);
                    finish();


                } else if (response.code() == 200) {
                    loadingPB.setVisibility(View.GONE);
                    Log.e(TAG, "Else condition");
                     Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    isDataListUpdatedOnServer = false;
                   // saveUseToLocalDB(Const.API_KEY, firstName, lastName, userEmail, userMobile, userAge, state, referenceText, demoRequired, ratingCount, usersSuggestion);
                    // Toast.makeText(MainActivity.this,"User already exists, Please try with different entries",Toast.LENGTH_LONG).show();
                    // AppUtils.showToast(Const.password_error, LoginActivity.this);
                } else {
                    loadingPB.setVisibility(View.GONE);
                    // AppUtils.showToast(Const.no_records, LoginActivity.this);

                }
            }

            @Override
            public void onFailure(Call<SignUpResponseAntplay> call, Throwable t) {
                Log.e(TAG, "" + t);
                loadingPB.setVisibility(View.GONE);
                //AppUtils.showToast(Const.something_went_wrong, LoginActivity.this);
            }
        });

    }


    private void callBulkSignUpAPI(List<SignupRequest> localUsersList) {
        loadingPB.setVisibility(View.VISIBLE);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance(Const.DEV_URL_ANT).create(RetrofitAPI.class);
        BulkDataRequest bulkDataRequestModel = new BulkDataRequest(localUsersList);
        Call<SignUpResponse> call = retrofitAPI.bulkSignupAPIRequest(bulkDataRequestModel);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                assert response.body() != null;
                if (response.body().getStatus()) {
                    loadingPB.setVisibility(View.GONE);
                    Log.d(TAG, "" + response.body().getMessage());
                    // Toast.makeText(MainActivity.this, "Congratulations! you are successfully registered with Bingo", Toast.LENGTH_LONG).show();


                } else if (response.code() == 200) {
                    loadingPB.setVisibility(View.GONE);
                    Log.e(TAG, "Else condition");
                   // Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                } else {
                    loadingPB.setVisibility(View.GONE);
                    // AppUtils.showToast(Const.no_records, LoginActivity.this);

                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.e(TAG, "" + t);
                loadingPB.setVisibility(View.GONE);
                //AppUtils.showToast(Const.something_went_wrong, LoginActivity.this);
            }
        });

    }

    private void callSendOtpAPI(String mobile, String otp) {
        loadingPB.setVisibility(View.VISIBLE);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance(Const.OTP_URL).create(RetrofitAPI.class);
        String phoneNumber = "+91" + mobile;
        String otpMessage = otp + " " + Const.OTP_MESSAGE;
        OtpRequest otpRequestModel = new OtpRequest(phoneNumber, otpMessage);
        Call<OtpResponse> call = retrofitAPI.otpAPIRequest(otpRequestModel);
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                assert response.body() != null;
                if (response.body().getStatus().equals("success")) {
                    loadingPB.setVisibility(View.GONE);
                    Log.d(TAG, "" + response.body().getMessage());
                    verifyOTPDialog = new VerifyOTPDialog(MainActivity.this, MainActivity.this);
                    verifyOTPDialog.show();

                } else if (response.code() == 200) {
                    loadingPB.setVisibility(View.GONE);
                    Log.e(TAG, "Else condition");

                } else {
                    loadingPB.setVisibility(View.GONE);
                    // AppUtils.showToast(Const.no_records, LoginActivity.this);

                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Log.e(TAG, "" + t);
                loadingPB.setVisibility(View.GONE);
                //AppUtils.showToast(Const.something_went_wrong, LoginActivity.this);
            }
        });

    }

    private boolean validateTextFields() {
        if (edtFirstName.getText() == null || edtFirstName.getText().equals("") || edtFirstName.getText().length() < 1) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_first_name), Toast.LENGTH_LONG).show();
            return false;
        } else if (edtLastName.getText() == null || edtLastName.getText().equals("") || edtLastName.getText().length() < 1) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_last_name), Toast.LENGTH_LONG).show();
            return false;
        } else if (edtMobile.getText() == null || edtMobile.getText().equals("") || edtMobile.getText().length() < 1) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_mobile), Toast.LENGTH_LONG).show();
            return false;
        } else if (edtMobile.getText().length() != 10) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_correct_mobile), Toast.LENGTH_LONG).show();
            return false;
        } else if (TextUtils.isEmpty(edtEmail.getText()) || !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_correct_email), Toast.LENGTH_LONG).show();
            return false;
        } else if (edtUserAge.getText() == null || edtUserAge.getText().equals("") || edtUserAge.getText().length() < 1) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_age), Toast.LENGTH_LONG).show();
            return false;
        } else if (edtState.getText() == null || edtState.getText().equals("") || edtState.getText().length() < 1) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.enter_state), Toast.LENGTH_LONG).show();
            return false;
        } /*else if (referenceSelected.equals("------Select One----")) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.select_flavour), Toast.LENGTH_LONG).show();
            return false;
        }*/ else if (!checkBoxTerms.isChecked()) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.check_term_of_use), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void setSpinnerAdapter() {
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter leaveTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, referenceType);
        leaveTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        referenceSpinner.setAdapter(leaveTypeAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }


    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    private void saveUseToLocalDB(String apiKey, String firstName, String lastName, String userEmail, String userMobile, String userAge, String state, String referenceSelected, String demoSelected, String experienceRating, String usersSuggestion) {
        ComiconUser newUser = new ComiconUser(apiKey, firstName, lastName, userEmail, userMobile, userAge, state, referenceSelected, demoSelected, experienceRating, usersSuggestion);

        //insert Data into Database
        new InsertUserAsyncTask(comiconUserDao).execute(newUser);

        // Toast.makeText(MainActivity.this, "Congratulations! you are successfully registered with Bingo", Toast.LENGTH_LONG).show();
        Intent i = new Intent(MainActivity.this, ThanksActivity.class);
        startActivity(i);
        finish();
    }

    private void getUserListFromLocalDB() {
        //Retrieve data from Database
        new GetUsersAsyncTask(comiconUserDao, this).execute();

    }

    @Override
    public void networkStatus(boolean isAvailable) {
        // Push data from Database to network
        isNetworkAvailable = isAvailable;
        SnackBarUtils.customSnackBar(MainActivity.this, isAvailable);
        if (isAvailable) {
            if (!isDataListUpdatedOnServer) {
                getUserListFromLocalDB();
                Log.d("USER_DB", "User updated on Server");
            }
        }
    }


    @Override
    public void otpVerified(String otp, boolean isVerified) {
        // Move from here to Thanks screen
        if (isVerified) {
            if (this.otp.equals(otp)) {
                Toast.makeText(MainActivity.this, "Your Phone is verified", Toast.LENGTH_LONG).show();
                verifyOTPDialog.dismiss();
                if (validateTextFields()) {
                    firstName = edtFirstName.getText().toString();
                    lastName = edtLastName.getText().toString();
                    userMobile = edtMobile.getText().toString();
                    userEmail = edtEmail.getText().toString();
                    userAge = edtUserAge.getText().toString();
                    state = edtState.getText().toString();
                    usersSuggestion = edtSuggestion.getText().toString();

                    ratingExperience = ratingBar.getRating();

                    if (isNetworkAvailable) {
                        isDataListUpdatedOnServer = true;
                        // callSignUpAPI(userName, userMobile, userEmail, flavour);
                        //callSendOtpAPI(userMobile, generateRandomNumber());
                    } else {
                        isDataListUpdatedOnServer = false;
                        //saveUseToLocalDB(userName, userMobile, userEmail, referenceSelected);
                    }
                }

            } else {
                Toast.makeText(MainActivity.this, "OTP not matched", Toast.LENGTH_LONG).show();
            }
        }
        Intent i = new Intent(MainActivity.this, ThanksActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// send the selected leave type here
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        referenceSelected = referenceType[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /*****
     * Database Operations
     * Insert Data in Database
     *****/
    public static class InsertUserAsyncTask extends AsyncTask<ComiconUser, Void, Void> {
        private ComiconUserDao dao;

        public InsertUserAsyncTask(ComiconUserDao mDao) {
            this.dao = mDao;
        }

        @Override
        protected Void doInBackground(ComiconUser... users) {
            Log.d("USER", users[0].getFirstName() + " " + users[0].getEmail() + " " + users[0].getPhoneNumber() + " " + users[0].getEmail());
            dao.insertAll(users[0]);
            return null;
        }
    }


    /*****
     * Database Operations
     * Get Data from Database
     *****/
    public static class GetUsersAsyncTask extends AsyncTask<Void, Void, List<ComiconUser>> {
        private ComiconUserDao userDao;
        GetUsersAsyncListener asyncListener = null;

        public GetUsersAsyncTask(ComiconUserDao dao, GetUsersAsyncListener listener) {
            userDao = dao;
            asyncListener = listener;
        }

        @Override
        protected List<ComiconUser> doInBackground(Void... voids) {
            List<ComiconUser> userList = userDao.getAll();
            //userDao.deleteAll();

            return userList;
        }

        @Override
        protected void onPostExecute(List<ComiconUser> users) {
            /*Log.d("USER_DB", users.get(0).getUid() + " " + users.get(0).getFirstName() + " " + users.get(0).getPhoneNumber() + " " + users.get(0).getEmail()+ " " + users.get(0).getRating());
            Log.d("USER_DB","User Count : "+users.size());*/
            asyncListener.userListUpdated(users);
        }
    }

    @Override
    public void userListUpdated(List<ComiconUser> users) {
        Log.d("USER_DB", "User Count : " + users.size());
        if (isNetworkAvailable) {
            if (users.size() > 0) {
                // call API for bulk upload here
                isDataListUpdatedOnServer = true;
                 populateUserListForServer(users);
                callBulkSignUpAPI(populateUserListForServer(users));
                for(ComiconUser user:users){
                    Log.d("USER_DB", user.getUid() + " " + user.getFirstName() + " " + user.getPhoneNumber() + " " + user.getEmail() + " " + user.getAge());
                }
                //Log.d("USER_DB", users.get(0).getUid() + " " + users.get(0).getFirstName() + " " + users.get(0).getPhoneNumber() + " " + users.get(0).getEmail() + " " + users.get(0).getAge());

            } else {
                isDataListUpdatedOnServer = false;
            }

        }
    }

    private List<SignupRequest> populateUserListForServer(List<ComiconUser> users) {
        List<SignupRequest> localUsers = new ArrayList<>();
        for (ComiconUser userItem : users) {
            // localUsers.add(new SignupRequest(userItem.getName(), userItem.getMobile(), userItem.getEmail(), userItem.getFlavour()));
        }
        return localUsers;
    }


}