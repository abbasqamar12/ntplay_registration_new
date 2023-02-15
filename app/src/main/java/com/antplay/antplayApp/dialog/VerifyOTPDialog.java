package com.antplay.antplayApp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.antplay.antplayApp.R;
import com.antplay.antplayApp.listeners.OtpVerifyListener;
import com.antplay.antplayApp.utils.GenericTextWatcher;


public class VerifyOTPDialog extends Dialog implements View.OnClickListener {

    Context context;
    LayoutInflater inflater;
    OtpVerifyListener listener;
    Button btnCancel, btnProceed;
    RelativeLayout rlParent;
    EditText otpOne, otpTwo, otpThree, otpFour;


    public VerifyOTPDialog(Context context, OtpVerifyListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        inflater = LayoutInflater.from(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //View view = inflater.inflate(R.layout.dialog_verify_otp, null);
        setContentView(R.layout.dialog_verify_otp);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(R.color.blackTransparent);
        //setContentView(R.layout.dialog_verify_otp);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btnCancel = findViewById(R.id.btnCancel);
        btnProceed = findViewById(R.id.btnProceed);
        rlParent = findViewById(R.id.rlParent);

        otpOne = findViewById(R.id.editTextOne);
        otpTwo = findViewById(R.id.editTextTwo);
        otpThree = findViewById(R.id.editTextThree);
        otpFour = findViewById(R.id.editTextFour);
        EditText[] edit = {otpOne, otpTwo, otpThree, otpFour};

        otpOne.addTextChangedListener(new GenericTextWatcher(otpOne, edit));
        otpTwo.addTextChangedListener(new GenericTextWatcher(otpTwo, edit));
        otpThree.addTextChangedListener(new GenericTextWatcher(otpThree, edit));
        otpFour.addTextChangedListener(new GenericTextWatcher(otpFour, edit));

        setCanceledOnTouchOutside(true);

        setOnClickListener();

        setTextWatcher();

    }

    private void setTextWatcher() {
        otpOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                otpTwo.requestFocus();
            }
        });
    }

    private void setOnClickListener() {
        rlParent.setOnClickListener(this);
        btnProceed.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnProceed:
                if (validateOTPTextFields()) {
                    listener.otpVerified(getOTP(), true);
                }
                //dismiss();
                break;

            case R.id.btnCancel:
            case R.id.rlParent:
                listener.otpVerified("", false);
                dismiss();
                break;
        }
    }

    private String getOTP() {
        String otpText = otpOne + "" + otpTwo + "" + otpThree + "" + otpFour;
        return otpText;
    }

    private boolean validateOTPTextFields() {
        if (otpOne.getText().length() < 1 || otpTwo.getText().length() < 1 || otpThree.getText().length() < 1 || otpFour.getText().length() < 1) {
            Toast.makeText(context, context.getResources().getString(R.string.enter_otp), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


}
