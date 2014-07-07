package com.ivan.android.manhattanenglish.app.core.login;

import android.view.View;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.remote.user.User;

/**
 * @author: Ivan Vigoss
 * Date: 14-4-27
 * Time: PM4:09
 */
public class TeacherRegisterFragment extends RegisterFragment {

    public TeacherRegisterFragment() {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_teacher_register;
    }

    @Override
    public void init(View rootView) {
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTel() && checkPassword() && checkAuthCode()) {
                    mCallback.register(getInputTel(), getInputPassword(), getInputAuthCode(), User.USER_TYPE_TEACHER);
                }
            }
        });
    }
}