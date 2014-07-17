package com.ivan.android.manhattanenglish.app.core.question;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.core.teacher.TeacherActivity;
import com.ivan.android.manhattanenglish.app.customviews.TitleBar;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.question.Question;
import com.ivan.android.manhattanenglish.app.remote.question.QuestionService;
import com.ivan.android.manhattanenglish.app.remote.upload.UploadService;
import com.ivan.android.manhattanenglish.app.remote.user.User;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import java.io.File;

public class AskQuestionActivity extends BaseActivity {

    final static int PICK_IMAGE_CODE = 1;

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

        mChoosePic = (TextView) findViewById(R.id.choose_pic);
        mChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImage, PICK_IMAGE_CODE);
            }
        });

        Log.i("AskQuestionActivity", "Current User Type is " + UserCache.getCurrentUser().getType());
        if (User.USER_TYPE_VIP_STUDENT.equals(UserCache.getCurrentUser().getType())) {
            View container = findViewById(R.id.vip_setting_container);
            container.setVisibility(View.VISIBLE);

            mChooseTeacher = (TextView) findViewById(R.id.choose_teacher);
            mChooseTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pickTeacher = new Intent(AskQuestionActivity.this, TeacherActivity.class);
                    pickTeacher.setAction(TeacherActivity.ACTION_PICK_TEACHER);
                    startActivityForResult(pickTeacher, TeacherActivity.PICK_TEACHER_CODE);
                }
            });

        }


        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    new SubmitQuestionTask(AskQuestionActivity.this).execute();
                }
            }
        });

    }

    private boolean checkForm() {
        boolean result = true;

        mTitle.setError(null);
        mQuestion.setError(null);

        View focusView = null;
        if (TextUtils.isEmpty(getTitleText())) {
            focusView = mTitle;
            result = false;
        } else if (TextUtils.isEmpty(getContentText())) {
            focusView = mQuestion;
            result = false;
        }

        if (!result) {
            focusView.requestFocus();
        }

        return result;
    }

    private String getTitleText() {
        return mTitle.getText().toString();
    }

    private String getContentText() {
        return mQuestion.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        switch (requestCode) {
            case PICK_IMAGE_CODE:
                Uri uri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), uri, filePathColumn);

                Log.i("SubmitHomework", "URI: " + uri
                        + " \n cursor isnull:" + (cursor == null));
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    File selectedPic = new File(filePath);
                    new UploadImageTask().execute(selectedPic);
                }

                break;
            case TeacherActivity.PICK_TEACHER_CODE:
                String teacherId = data.getStringExtra("teacherId");
                String teacherName = data.getStringExtra("teacherName");
                mChooseTeacher.setTag(teacherId);
                mChooseTeacher.setText(teacherName);
                break;
            default:
                break;
        }

    }

    class UploadImageTask extends AsyncTask<File, Void, String> {

        File file;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingDialog(R.string.upload_text);
        }

        @Override
        protected String doInBackground(File... params) {
            String result = null;
            try {
                file = params[0];
                UploadService uploadService = ServiceFactory.getService(UploadService.class);
                result = uploadService.uploadImage(AskQuestionActivity.this,file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideLoadingDialog();
            if (!TextUtils.isEmpty(s)) {
                mChoosePic.setTag(s);
                mChoosePic.setText(file.getName());
            } else {
                Toast.makeText(AskQuestionActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SubmitQuestionTask extends CommonAsyncTask<Void, Void, Void> {

        private Question question = new Question();

        protected SubmitQuestionTask(Context context) {
            super(context);
        }

        @Override
        protected Void getResultInBackground(Void... params) {
            collectParams();
            QuestionService questionService = ServiceFactory.getService(QuestionService.class);
            questionService.askQuestion(question);
            return null;
        }

        private void collectParams() {
            question.setQuestionTitle(getTitleText());
            question.setQuestionContent(getContentText());
            String imageUrl = (String) mChoosePic.getTag();
            question.setQuestionPic(imageUrl);
            if (mChooseTeacher != null) {
                String teacherId = (String) mChooseTeacher.getTag();
                question.setAssignTeacher(teacherId);
            }
            question.setUserId(UserCache.getUserId());
        }

        @Override
        protected void onSuccess(Void aVoid) {
            super.onSuccess(aVoid);
            Toast.makeText(AskQuestionActivity.this, R.string.info_submit_success, Toast.LENGTH_SHORT).show();
            Intent emptyIntent = new Intent();
            setResult(RESULT_OK, emptyIntent);
            finish();
        }

    }

}
