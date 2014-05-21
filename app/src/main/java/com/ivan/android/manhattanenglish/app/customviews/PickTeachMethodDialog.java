package com.ivan.android.manhattanenglish.app.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ivan.android.manhattanenglish.app.R;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-20
 * Time: AM10:58
 */
public class PickTeachMethodDialog extends AlertDialog {

    protected Button mPositiveButton;

    protected Context context;
    /**
     * 教师上门
     */
    protected TextView mTeacher;

    /**
     * 学生上门
     */
    protected TextView mStudent;

    protected String selectedMethod;

    protected PickMethodEvent mListener;

    protected String teacherWay;

    protected String studentWay;

    public PickTeachMethodDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setSelectedMethod(String selectedMethod) {
        this.selectedMethod = selectedMethod;
    }

    public void refresh() {
        if (TextUtils.isEmpty(selectedMethod)) {
            setTextViewSelected(mTeacher, false);
            setTextViewSelected(mStudent, false);
        } else if (teacherWay.equals(selectedMethod)) {
            setTextViewSelected(mTeacher, true);
            setTextViewSelected(mStudent, false);
        } else {
            setTextViewSelected(mTeacher, false);
            setTextViewSelected(mStudent, true);
        }
    }

    public void setPickMethodEvent(PickMethodEvent mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_method);

        teacherWay = context.getResources().getString(R.string.teach_method_for_teacher);
        studentWay = context.getResources().getString(R.string.teach_method_for_student);

        mTeacher = (TextView) findViewById(R.id.teacher_way);
        mTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedMethod(mTeacher.getText().toString());
                refresh();
            }
        });

        mStudent = (TextView) findViewById(R.id.student_way);
        mStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedMethod(mStudent.getText().toString());
                refresh();
            }
        });

        mPositiveButton = (Button) findViewById(R.id.positiveBtn);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener != null) {
                    mListener.onMethodPicked(selectedMethod);
                }
            }
        });

        refresh();

    }

    private void setTextViewSelected(TextView text, boolean selected) {
        if (selected) {
            text.setBackgroundResource(R.drawable.text_border_blue);
            text.setTextColor(context.getResources().getColor(android.R.color.white));
        } else {
            text.setBackgroundResource(R.drawable.login_form_border);
            text.setTextColor(context.getResources().getColor(R.color.common_text_color));
        }

    }

    public interface PickMethodEvent {
        void onMethodPicked(String sex);
    }
}
