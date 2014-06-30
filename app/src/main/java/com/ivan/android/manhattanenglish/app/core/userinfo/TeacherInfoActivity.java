package com.ivan.android.manhattanenglish.app.core.userinfo;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
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

public class TeacherInfoActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<User> {


    ImageView mAvatar;

    TextView mUserName;

    ImageView mSex;

    TextView mMobile;

    TextView mEmail;

    TextView mAddress;

    TextView mCredits;

    /**
     * 评分
     */
    TextView mGrade;

    /**
     * 评价
     */
    TextView mComment;

    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);

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

        mGrade = (TextView) findViewById(R.id.grade_text);

        mComment = (TextView) findViewById(R.id.comment_text);

        showLoadingDialog();
        getSupportLoaderManager().initLoader(0, null, this);

    }


    @Override
    public Loader<User> onCreateLoader(int id, Bundle args) {
        Log.i("studentInfoActivity", "create loader");
        return new UserInfoLoader(this, UserCache.getUserId());
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
                    .fit()
                    .into(mAvatar);
        }

        mUserName.setText(mUser.getUserName());
        mSex.setImageResource(mUser.getSexDrawableResource());

        mMobile.setText(mUser.getMobile());
        mEmail.setText(mUser.getEmail());
        mAddress.setText(mUser.getAddress());

        mCredits.setText(String.valueOf(mUser.getCredits()));

        mGrade.setText(String.valueOf(mUser.getRating()));
        mComment.setText(mUser.getEvaluation());


    }
}
