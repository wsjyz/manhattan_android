package com.ivan.android.manhattanenglish.app.core.homework;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ivan.android.manhattanenglish.app.R;
import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.homework.Homework;
import com.ivan.android.manhattanenglish.app.remote.homework.HomeworkService;
import com.ivan.android.manhattanenglish.app.remote.homework.HomeworkSubmit;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import java.io.File;

public class SubmitHomeworkActivity extends BaseActivity {

    public final static String KEY_HOMEWORK = "HOMEWORK";
    public final static int REQ_CODE_PICK_IMAGE = 1;

    Homework homework;

    TextView mHomeworkTitle;

    EditText mHomeworkAnswer;

    TextView mChoosePic;

    Button mSubmit;

    HomeworkSubmit mHomeworkSubmit;

    File selectedPic;

    HomeworkService homeworkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_homework);

        mHomeworkSubmit = new HomeworkSubmit();
        homeworkService = ServiceFactory.getService(HomeworkService.class);

        String json = getIntent().getStringExtra(KEY_HOMEWORK);
        homework = JSON.parseObject(json, Homework.class);

        mHomeworkSubmit.setHomeworkId(homework.getHomeworkId());
        mHomeworkSubmit.setUserId(UserCache.getUserId());


        mHomeworkTitle = (TextView) findViewById(R.id.homework_title);
        mHomeworkTitle.setText(homework.getHomeworkTitle());

        mHomeworkAnswer = (EditText) findViewById(R.id.homework_answer);

        mChoosePic = (TextView) findViewById(R.id.choose_pic);
        mChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPicture = new Intent(Intent.ACTION_PICK);
                pickPicture.setType("image/*");

                startActivityForResult(pickPicture, REQ_CODE_PICK_IMAGE);
            }
        });

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeworkAnswer.setError(null);
                if (TextUtils.isEmpty(getInputContent())) {
                    mHomeworkAnswer.setError(getString(R.string.error_homework_content_required));
                    mHomeworkAnswer.requestFocus();
                    return;
                }
                new SubmitHomeworkTask().execute();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImg = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(
                    selectedImg, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            selectedPic = new File(filePath);
            mChoosePic.setText(selectedPic.getName());
        }

    }

    private String getInputContent() {
        return mHomeworkAnswer.getText().toString();
    }

    class SubmitHomeworkTask extends AsyncTask<Void, Void, Boolean> {

        String errorMsg;

        @Override
        protected Boolean doInBackground(Void... params) {
            String homeworkId = homework.getHomeworkId();
            String content = getInputContent();

            try {
                homeworkService.submitHomework(homeworkId, selectedPic, content);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                errorMsg = e.getMessage();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Toast.makeText(SubmitHomeworkActivity.this, R.string.info_submit_success, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(SubmitHomeworkActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
