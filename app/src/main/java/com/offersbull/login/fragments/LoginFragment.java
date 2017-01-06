package com.offersbull.login.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.offersbull.MainActivity;
import com.offersbull.R;

import java.io.IOException;

import static com.offersbull.utils.Validation.validateEmail;
import static com.offersbull.utils.Validation.validateFields;

public class LoginFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = LoginFragment.class.getSimpleName();

    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtLogin;
    private TextView mTvRegister;
    private TextView mTvForgotPassword;
    private TextView mTvSkip;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private ProgressBar mProgressBar;

    private SharedPreferences mSharedPreferences;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        initViews(view);
        initSharedPreferences();
        return view;
    }

    private void initViews(View v) {

        mEtEmail = (EditText) v.findViewById(R.id.et_email);
        mEtPassword = (EditText) v.findViewById(R.id.et_password);
        mBtLogin = (Button) v.findViewById(R.id.btn_login);
        mTiEmail = (TextInputLayout) v.findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout) v.findViewById(R.id.ti_password);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress);
        mTvRegister = (TextView) v.findViewById(R.id.tv_register);
        mTvForgotPassword = (TextView) v.findViewById(R.id.tv_forgot_password);
        mTvSkip = (TextView) v.findViewById(R.id.tv_skip);

        mBtLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvForgotPassword.setOnClickListener(this);
        mTvSkip.setOnClickListener(this);
    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_login:
                login();
                break;

            case R.id.tv_register:
                goToRegister();
                break;

            case R.id.tv_forgot_password:
                showDialog();
                break;
            case R.id.tv_skip:
                goToHome();
                break;
        }
    }

    private void login() {

        setError();

        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {

            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            mTiPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            loginProcess(email,password);
            mProgressBar.setVisibility(View.VISIBLE);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void setError() {

        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

    private void loginProcess(String email, String password) {


    }





    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void goToRegister(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        RegisterFragment fragment = new RegisterFragment();
        ft.replace(R.id.loginFragment,fragment,RegisterFragment.TAG);
        ft.commit();
    }

    private void showDialog(){

        ResetPasswordDialog fragment = new ResetPasswordDialog();

        fragment.show(getFragmentManager(), ResetPasswordDialog.TAG);
    }

    private void goToHome(){
        Intent intent =  new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
