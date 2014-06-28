package com.ivan.android.manhattanenglish.app.core.question;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.question.Question;
import com.ivan.android.manhattanenglish.app.utils.DateFormatUtils;

public class QuestionDetailActivity extends BaseActivity {

    public static final String JSON_QUESTION_KEY = "QUESTION";

    TextView mQuestionContent;

    TextView mCreateTime;

    Question question;

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

        mCreateTime = (TextView) findViewById(R.id.create_time_text);
        mCreateTime.setText(createTime);

    }
}
