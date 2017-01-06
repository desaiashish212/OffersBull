package com.offersbull.login.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
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


public class RegisterFragment extends Fragment implements View.OnClickListener{

    public static final String TAG = RegisterFragment.class.getSimpleName();

    private EditText mEtName;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtRegister;
    private TextView mTvLogin;
    private TextView mTvSkip;
    private TextInputLayout mTiName;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;
    private ProgressBar mProgressbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {

        mEtName = (EditText) v.findViewById(R.id.et_name);
        mEtEmail = (EditText) v.findViewById(R.id.et_email);
        mEtPassword = (EditText) v.findViewById(R.id.et_password);
        mBtRegister = (Button) v.findViewById(R.id.btn_register);
        mTvLogin = (TextView) v.findViewById(R.id.tv_login);
        mTvSkip = (TextView) v.findViewById(R.id.tv_skip);
        mTiName = (TextInputLayout) v.findViewById(R.id.ti_name);
        mTiEmail = (TextInputLayout) v.findViewById(R.id.ti_email);
        mTiPassword = (TextInputLayout) v.findViewById(R.id.ti_password);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);

        mBtRegister.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        mTvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_register:
                register();
                break;
            case R.id.tv_login:
                goToLogin();
                break;
            case  R.id.tv_skip:
                goToHome();
                break;
        }
    }

    private void register() {

        setError();

        String name = mEtName.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateFields(name)) {

            err++;
            mTiName.setError("Name should not be empty !");
        }

        if (!validateEmail(email)) {

            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {

            err++;
            mTiPassword.setError("Password should not be empty !");
        }

        if (err == 0) {



            mProgressbar.setVisibility(View.VISIBLE);
           // registerProcess(user);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }
    }

    private void setError() {

        mTiName.setError(null);
        mTiEmail.setError(null);
        mTiPassword.setError(null);
    }

 /*   private void registerProcess(User user) {


    }
*/


    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void goToLogin(){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        ft.replace(R.id.loginFragment, fragment, LoginFragment.TAG);
        ft.commit();
    }

    private void goToHome(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
