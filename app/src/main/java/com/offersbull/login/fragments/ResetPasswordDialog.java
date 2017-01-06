package com.offersbull.login.fragments;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.offersbull.R;

import java.io.IOException;

import static com.offersbull.utils.Validation.validateEmail;
import static com.offersbull.utils.Validation.validateFields;

public class ResetPasswordDialog extends DialogFragment {

    public interface Listener {

        void onPasswordReset(String message);
    }

    public static final String TAG = ResetPasswordDialog.class.getSimpleName();

    private EditText mEtEmail;
    private EditText mEtToken;
    private EditText mEtPassword;
    private Button mBtResetPassword;
    private TextView mTvMessage;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiToken;
    private TextInputLayout mTiPassword;
    private ProgressBar mProgressBar;


    private String mEmail;

    private boolean isInit = true;

    private Listener mListner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reset_password,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View v) {

        mEtEmail = (EditText) v.findViewById(R.id.et_email);
        mEtToken = (EditText) v.findViewById(R.id.et_token);
        mEtPassword = (EditText) v.findViewById(R.id.et_password);
        mBtResetPassword = (Button) v.findViewById(R.id.btn_reset_password);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progress);
        mTvMessage = (TextView) v.findViewById(R.id.tv_message);
        mTiEmail = (TextInputLayout) v.findViewById(R.id.ti_email);
        mTiToken = (TextInputLayout) v.findViewById(R.id.ti_token);
        mTiPassword = (TextInputLayout) v.findViewById(R.id.ti_password);

        mBtResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInit) resetPasswordInit();
                else resetPasswordFinish();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  mListner = (MainActivity)context;
    }

    private void setEmptyFields() {

        mTiEmail.setError(null);
        mTiToken.setError(null);
        mTiPassword.setError(null);
        mTvMessage.setText(null);
    }

    public void setToken(String token) {

        mEtToken.setText(token);
    }

    private void resetPasswordInit() {

        setEmptyFields();

        mEmail = mEtEmail.getText().toString();

        int err = 0;

        if (!validateEmail(mEmail)) {

            err++;
            mTiEmail.setError("Email Should be Valid !");
        }

        if (err == 0) {

            mProgressBar.setVisibility(View.VISIBLE);
            resetPasswordInitProgress(mEmail);
        }
    }

    private void resetPasswordFinish() {

        setEmptyFields();

        String token = mEtToken.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateFields(token)) {

            err++;
            mTiToken.setError("Token Should not be empty !");
        }

        if (!validateFields(password)) {

            err++;
            mTiEmail.setError("Password Should not be empty !");
        }

        if (err == 0) {

            mProgressBar.setVisibility(View.VISIBLE);


          //  resetPasswordFinishProgress(user);
        }
    }

    private void resetPasswordInitProgress(String email) {

    }

  /*  private void resetPasswordFinishProgress(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit().resetPasswordFinish(mEmail,user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }
*/


    private void showMessage(String message) {

        mTvMessage.setVisibility(View.VISIBLE);
        mTvMessage.setText(message);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
