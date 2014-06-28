package com.ivan.android.manhattanenglish.app;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.android.manhattanenglish.app.core.BaseActivity;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.homework.HomeworkService;
import com.ivan.android.manhattanenglish.app.utils.CommonAsyncTask;

public class PublishHomeworkActivity extends BaseActivity {

    EditText mHomeworkTitle;

    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_homework);

        mHomeworkTitle = (EditText) findViewById(R.id.homework_title_input);

        mSubmit = (Button) findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getInputText();
                mHomeworkTitle.setError(null);
                if (TextUtils.isEmpty(title)) {
                    mHomeworkTitle.setError(getText(R.string.error_homework_title_required));
                    mHomeworkTitle.requestFocus();
                    return;
                }

                new PublishHomeworkTask(PublishHomeworkActivity.this).execute();
            }
        });
    }


    private String getInputText() {
        return mHomeworkTitle.getText().toString();
    }

    class PublishHomeworkTask extends CommonAsyncTask<Void, Void, Void> {

        protected PublishHomeworkTask(Context context) {
            super(context);
        }

        @Override
        protected Void getResultInBackground(Void... params) {
            HomeworkService homeworkService = ServiceFactory.getService(HomeworkService.class);
            homeworkService.postHomework(getInputText());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!hasError) {
                Toast.makeText(PublishHomeworkActivity.this, R.string.info_submit_success, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}
