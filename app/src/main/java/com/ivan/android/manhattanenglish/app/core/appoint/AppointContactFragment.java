package com.ivan.android.manhattanenglish.app.core.appoint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ivan.android.manhattanenglish.app.R;

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
        mContactUser = (EditText) view.findViewById(R.id.contact_user_text);
        mContactAddress = (EditText) view.findViewById(R.id.contact_address_text);
        mContactPhone = (EditText) view.findViewById(R.id.contact_phone_text);
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

}
