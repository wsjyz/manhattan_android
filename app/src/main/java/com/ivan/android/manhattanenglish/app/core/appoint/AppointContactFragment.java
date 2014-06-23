package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

/**
 * 预约课程或者教师，提交联系人的表单Fragment
 *
 * @author: Ivan Vigoss
 * Date: 14-5-27
 * Time: AM11:43
 */
public class AppointContactFragment extends Fragment {

    EditText mContactUser;

    EditText mContactAddress;

    EditText mContactPhone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appoint_contact, null);
        User user = UserCache.getCurrentUser();

        mContactUser = (EditText) view.findViewById(R.id.contact_user_text);
        mContactUser.setText(user.getUserName());

        mContactAddress = (EditText) view.findViewById(R.id.contact_address_text);
        mContactAddress.setText(user.getAddress());

        mContactPhone = (EditText) view.findViewById(R.id.contact_phone_text);
        mContactPhone.setText(user.getMobile());

        return view;
    }

    public String getUserName() {
        return mContactUser.getText().toString();
    }


    public String getAddress() {
        return mContactAddress.getText().toString();
    }

    public String getPhone() {
        return mContactPhone.getText().toString();
    }

    public boolean checkForm() {
        mContactUser.setError(null);
        mContactAddress.setError(null);
        mContactPhone.setError(null);

        View focusView = null;
        boolean result = true;

        if (TextUtils.isEmpty(getUserName())) {
            focusView = mContactUser;
            mContactUser.setError(getActivity().getText(R.string.error_contact_user_required));
            result = false;
        } else if (TextUtils.isEmpty(getPhone())) {
            focusView = mContactPhone;
            mContactPhone.setError(getActivity().getText(R.string.error_tel_required));
            result = false;
        }

        if (!result) {
            focusView.requestFocus();
        }


        return result;

    }

}
