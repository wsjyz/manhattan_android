package com.ivan.android.manhattanenglish.app.core.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.utils.FormValidator;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-27
 * Time: PM4:09
 */
public class StudentRegisterFragment extends Fragment {

    private static final String TAG = "register";

    private EditText mTelView;

    private EditText mPasswordView;

    private EditText mAuthCodeView;

    private Button mAuthCodeBtn;

    private Button mRegisterBtn;

    private Button mTobeVipBtn;

    private RegisterListener mCallback;

    private Context mContext;

    public StudentRegisterFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (RegisterListener) activity;
            mContext = activity;
        } catch (ClassCastException e) {
            Log.e(TAG, "activity must implement RegisterListener", e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_register, container, false);

        mTelView = (EditText) rootView.findViewById(R.id.user_name);
        mPasswordView = (EditText) rootView.findViewById(R.id.password);
        mAuthCodeView = (EditText) rootView.findViewById(R.id.auth_code);

        mAuthCodeBtn = (Button) rootView.findViewById(R.id.auth_code_button);
        mAuthCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel()) {
                    String tel = mTelView.getText().toString();
                    mCallback.sendAuthCode(tel);
                }
            }
        });

        mRegisterBtn = (Button) rootView.findViewById(R.id.register_button);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel() && checkPassword() && checkAuthCode()) {
                    String tel = mTelView.getText().toString();
                    String psw = mPasswordView.getText().toString();
                    String authCode = mAuthCodeView.getText().toString();
                    mCallback.register(tel, psw, authCode);
                }
            }
        });

        mTobeVipBtn = (Button) rootView.findViewById(R.id.bevip_button);
        mTobeVipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel() && checkPassword() && checkAuthCode()) {
                    String tel = mTelView.getText().toString();
                    String psw = mPasswordView.getText().toString();
                    String authCode = mAuthCodeView.getText().toString();
                    mCallback.beVip(tel, psw, authCode);
                }
            }
        });

        return rootView;
    }

    private boolean checkTel() {
        String tel = mTelView.getText().toString();
        mTelView.setError(null);

        boolean valid = false;
        if (!FormValidator.isStringNotBlank(tel)) {
            mTelView.setError(mContext.getString(R.string.error_tel_required));
        } else if (!FormValidator.isMobileNumber(tel)) {
            mTelView.setError(mContext.getString(R.string.error_invalid_tel));
        } else {
            valid = true;
        }

        return valid;
    }

    private boolean checkPassword() {
        String psw = mPasswordView.getText().toString();

        boolean valid = false;
        if (!FormValidator.isStringNotBlank(psw)) {
            mPasswordView.setError(mContext.getString(R.string.error_password_required));
        } else if (psw.length() < 8) {
            mPasswordView.setError(mContext.getString(R.string.error_invalid_password));
        } else {
            valid = true;
        }
        return valid;
    }

    private boolean checkAuthCode() {
        String authCode = mAuthCodeView.getText().toString();

        boolean valid = false;
        if (!FormValidator.isStringNotBlank(authCode)) {
            mAuthCodeView.setError(mContext.getString(R.string.error_auth_code_required));
        } else {
            valid = true;
        }
        return valid;
    }


    public static interface RegisterListener {

        void sendAuthCode(String tel);

        void register(String tel, String password, String authCode);

        void beVip(String tel, String password, String authCode);

    }


}