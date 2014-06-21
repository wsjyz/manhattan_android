package com.ivan.android.manhattanenglish.app.core.login;

import android.view.View;
import android.widget.Button;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.user.User;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-27
 * Time: PM4:09
 */
public class StudentRegisterFragment extends RegisterFragment {

    private Button mTobeVipBtn;

    public StudentRegisterFragment() {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_student_register;
    }

    @Override
    public void init(View rootView) {
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel() && checkPassword() && checkAuthCode()) {
                    mCallback.register(getInputTel(), getInputPassword(), getInputAuthCode(), User.USER_TYPE_STUDENT);
                }
            }
        });

        mTobeVipBtn = (Button) rootView.findViewById(R.id.bevip_button);
        mTobeVipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel() && checkPassword() && checkAuthCode()) {
                    mCallback.register(getInputTel(), getInputPassword(), getInputAuthCode(), User.USER_TYPE_VIP_STUDENT);
                }
            }
        });
    }


}