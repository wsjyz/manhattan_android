package com.ivan.android.manhattanenglish.app.core.question;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.question.Question;
import com.ivan.android.manhattanenglish.app.remote.question.QuestionService;
import com.ivan.android.manhattanenglish.app.remote.upload.UploadService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.DateFormatUtils;
import com.ivan.android.manhattanenglish.app.utils.UserCache;
import com.squareup.picasso.Picasso;

import java.io.File;

public class AnswerQuestionActivity extends BaseActivity {

    public static final String JSON_QUESTION_KEY = "QUESTION";
    public final static int REQ_CODE_PICK_IMAGE = 1;

    TextView mQuestionContent;

    ImageView mQuestionPic;

    TextView mCreateTime;

    TextView mCreateUserName;

    TextView mChoosePic;

    Button mSubmit;

    Question question;

    EditText mAnswerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

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

        mQuestionPic = (ImageView) findViewById(R.id.question_pic);
        if (TextUtils.isEmpty(question.getQuestionPic())) {
            mQuestionPic.setVisibility(View.GONE);
        } else {
            Picasso.with(this)
                    .load(AbstractService.getImageUrl(question.getQuestionPic()))
                    .into(mQuestionPic);
        }

        mQuestionContent = (TextView) findViewById(R.id.question_content_text);
        mQuestionContent.setText(content);

        mCreateTime = (TextView) findViewById(R.id.create_time_text);
        mCreateTime.setText(createTime);

        mCreateUserName = (TextView) findViewById(R.id.create_user_name);
        if (question.getAskUser() != null) {
            mCreateUserName.setText(question.getAskUser().getUserName());
        }

        mAnswerContent = (EditText) findViewById(R.id.answer_content_input);

        mChoosePic = (TextView) findViewById(R.id.choose_pic);
        mChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPicture, REQ_CODE_PICK_IMAGE);
            }
        });

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerContent.setError(null);
                if (TextUtils.isEmpty(getInputContent())) {
                    mAnswerContent.setError(getString(R.string.error_homework_content_required));
                    mAnswerContent.requestFocus();
                    return;
                }
                new SubmitAnswerTask(AnswerQuestionActivity.this).execute();
            }
        });

    }

    private String getInputContent() {
        return mAnswerContent.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImg = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), selectedImg, filePathColumn);

            Log.i("SubmitAnswer", "URI: " + selectedImg
                    + " \n cursor isnull:" + (cursor == null));
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                File selectedPic = new File(filePath);
                new UploadImageTask(this).execute(selectedPic);
            }
        }

    }

    class SubmitAnswerTask extends CommonAsyncTask<Void, Void, Void> {
        protected SubmitAnswerTask(Context context) {
            super(context);
        }

        @Override
        protected Void getResultInBackground(Void... params) {
            QuestionService questionService = ServiceFactory.getService(QuestionService.class);
            Question q = new Question();
            q.setAnswer(getInputContent());
            q.setReplyUser(UserCache.getUserId());
            q.setQuestionId(question.getQuestionId());
            q.setAnswerPic(question.getAnswerPic());

            questionService.answerQuestion(q);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!hasError) {
                Toast.makeText(AnswerQuestionActivity.this, R.string.info_submit_success, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    class UploadImageTask extends CommonAsyncTask<File, Void, String> {

        private File file;

        protected UploadImageTask(Context context) {
            super(context);
        }

        @Override
        protected int getMessageResource() {
            return R.string.upload_text;
        }

        @Override
        protected String getResultInBackground(File... params) {
            file = params[0];
            UploadService uploadService = ServiceFactory.getService(UploadService.class);
            return uploadService.uploadImage(context, file);
        }

        @Override
        protected void onSuccess(String s) {
            Toast.makeText(context, R.string.upload_success, Toast.LENGTH_SHORT).show();
            mChoosePic.setText(file.getName());
            question.setAnswerPic(s);
        }
    }
}
