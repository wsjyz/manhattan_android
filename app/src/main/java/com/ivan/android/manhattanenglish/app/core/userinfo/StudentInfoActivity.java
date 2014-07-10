package com.ivan.android.manhattanenglish.app.core.userinfo;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.UserCache;
import com.squareup.picasso.Picasso;

/**
 * 学生个人资料Activity
 */
public class StudentInfoActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<User> {

    public static final String USER_ID_KEY = "USER_ID";

    ImageView mAvatar;

    TextView mUserName;

    ImageView mSex;

    TextView mMobile;

    TextView mEmail;

    TextView mAddress;

    TextView mCredits;

    TextView mVipExpired;

    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mAvatar = (ImageView) findViewById(R.id.avatar_image);

        mUserName = (TextView) findViewById(R.id.nick_name);
        mSex = (ImageView) findViewById(R.id.sex_image);
        mMobile = (TextView) findViewById(R.id.mobile_text);
        mEmail = (TextView) findViewById(R.id.email_text);
        mAddress = (TextView) findViewById(R.id.address_text);

        mCredits = (TextView) findViewById(R.id.credits_text);
        mVipExpired = (TextView) findViewById(R.id.vip_expired_text);

        showLoadingDialog();
        getSupportLoaderManager().initLoader(0, getIntent().getExtras(), this);
    }

    @Override
    public Loader<User> onCreateLoader(int id, Bundle args) {
        Log.i("studentInfoActivity", "create loader");

        String userId = UserCache.getUserId();
        if (args != null && !TextUtils.isEmpty(args.getString(USER_ID_KEY))) {
            userId = args.getString(USER_ID_KEY);
        }
        return new UserInfoLoader(this, userId);
    }

    @Override
    public void onLoadFinished(Loader<User> loader, User data) {
        Log.i("studentInfoActivity", "load finish.");
        mUser = data;
        refresh();
        hideLoadingDialog();
    }

    @Override
    public void onLoaderReset(Loader<User> loader) {
        mUser = null;
    }

    private void refresh() {
        if (mUser == null) return;
        if (!TextUtils.isEmpty(mUser.getAvatar())) {
            Picasso.with(this)
                    .load(mUser.getAvatar())
                    .placeholder(R.drawable.avatar)
                    .fit()
                    .into(mAvatar);
        }

        mUserName.setText(mUser.getUserName());
        mSex.setImageResource(mUser.getSexDrawableResource());

        mMobile.setText(mUser.getMobile());
        mEmail.setText(mUser.getEmail());
        mAddress.setText(mUser.getAddress());

        mCredits.setText(String.valueOf(mUser.getCredits()));
        if (mUser.getVipExpiredTime() != null) {
            mVipExpired.setText(DateFormat.format("yyyy-MM-dd", mUser.getVipExpiredTime()));
        }
    }

}
