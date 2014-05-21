package com.ivan.android.manhattanenglish.app.core.question;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.question.Question;

public class AskQuestionActivity extends BaseActivity {

    final static int PICK_IMAGE_CODE = 1;
    final static int PICK_TEACHER_CODE = 2;

    EditText mTitle;

    EditText mQuestion;

    TextView mChoosePic;

    TextView mChooseTeacher;

    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitle = (EditText) findViewById(R.id.question_title);

        mQuestion = (EditText) findViewById(R.id.content_text);

        //todo choose pic and upload
        mChoosePic = (TextView) findViewById(R.id.choose_pic);
        mChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_PICK);
                pickImage.setType("image/*");
                startActivityForResult(pickImage, PICK_IMAGE_CODE);
            }
        });

        mChooseTeacher = (TextView) findViewById(R.id.choose_teacher);
        mChooseTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickTeacher = new Intent(Intent.ACTION_PICK);
                startActivityForResult(pickTeacher, PICK_TEACHER_CODE);
            }
        });

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SubmitQuestionTask().execute();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_CODE:
                Uri uri = data.getData();
                new UploadImageTask().execute(uri);
                break;
            case PICK_TEACHER_CODE:
                String teacherId = data.getStringExtra("teacherId");
                String teacherName = data.getStringExtra("teacherName");
                mChooseTeacher.setTag(teacherId);
                mChooseTeacher.setText(teacherName);
                break;
            default:
                break;
        }

    }

    class UploadImageTask extends AsyncTask<Uri, Void, String> {
        /**
         *
         * @param params
         * @return fileUrl return by server
         */
        @Override
        protected String doInBackground(Uri... params) {
            Uri uri = params[0];

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    class SubmitQuestionTask extends AsyncTask<Void, Void, Boolean> {

        private Question question = new Question();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            collectParams();
            //todo submit
            return null;
        }

        private void collectParams() {
            question.setTitle(mTitle.getText().toString());
            question.setContent(mQuestion.getText().toString());
            String imageUrl = (String) mChoosePic.getTag();
            question.setImageUrl(imageUrl);

            String teacherId = (String) mChooseTeacher.getTag();
            question.setSpecifyTeacherId(teacherId);

        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            hideLoadingDialog();
        }
    }

}
