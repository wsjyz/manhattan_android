package com.ivan.android.manhattanenglish.app.core.question;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.question.Question;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.DateFormatUtils;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

public class QuestionDetailActivity extends BaseActivity {

    public static final String JSON_QUESTION_KEY = "QUESTION";

    TextView mQuestionContent;

    TextView mCreateTime;

    ImageView mQuestionPic;

    Question question;

    TextView mCreateUserName;

    //answer field
    ImageView mAnswerPic;

    TextView mAnswer;

    TextView mAnswerUser;

    RoundedImageView mAnswerUserAvatar;

    TextView mAnswerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        String questionJson = getIntent().getStringExtra(JSON_QUESTION_KEY);

        question = JSON.parseObject(questionJson, Question.class);

        String content = question.getQuestionContent();
        String createTime = DateFormatUtils.format(question.getCreateTime());

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mQuestionContent = (TextView) findViewById(R.id.question_content_text);
        mQuestionContent.setText(content);

        mCreateUserName = (TextView) findViewById(R.id.create_user_name);
        if (question.getAskUser() != null) {
            mCreateUserName.setText(question.getAskUser().getUserName());
        }

        mQuestionPic = (ImageView) findViewById(R.id.question_pic);
        if (TextUtils.isEmpty(question.getQuestionPic())) {
            mQuestionPic.setVisibility(View.GONE);
        } else {
            Picasso.with(this)
                    .load(question.getQuestionPic())
                    .into(mQuestionPic);
        }


        mCreateTime = (TextView) findViewById(R.id.create_time_text);
        mCreateTime.setText(createTime);

        //answer info
        String answerContent = question.getAnswer();
        if (!TextUtils.isEmpty(answerContent)) {
            View container = findViewById(R.id.answer_container);
            container.setVisibility(View.VISIBLE);

            mAnswer = (TextView) findViewById(R.id.answer_content_text);
            mAnswer.setText(answerContent);

            mAnswerPic = (ImageView) findViewById(R.id.answer_pic);
            if (TextUtils.isEmpty(question.getAnswerPic())) {
                mAnswerPic.setVisibility(View.GONE);
            } else {
                Picasso.with(this)
                        .load(question.getAnswerPic())
                        .into(mAnswerPic);
            }

            //answer userInfo
            User replyUser = question.getRepUser();

            if (replyUser != null) {
                mAnswerUser = (TextView) findViewById(R.id.teacher_name_text);
                mAnswerUser.setText(replyUser.getUserName());

                mAnswerUserAvatar = (RoundedImageView) findViewById(R.id.avatar_image);
                if (!TextUtils.isEmpty(replyUser.getAvatar())) {
                    Picasso.with(this)
                            .load(replyUser.getAvatar())
                            .placeholder(R.drawable.avatar)
                            .fit()
                            .into(mAnswerUserAvatar);
                }
            }

            String answerTime = DateFormatUtils.format(question.getAnswerTime());
            mAnswerTime = (TextView) findViewById(R.id.answer_time_text);
            mAnswerTime.setText(answerTime);

        }


    }
}
