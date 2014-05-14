package com.ivan.android.manhattanenglish.app.core.audition;

import android.content.Context;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.course.Course;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-13
 * Time: PM11:35
 */
public class AuditionCourseLoader extends CommonDataLoader<List<Course>> {

    public AuditionCourseLoader(Context context) {
        super(context);
    }

    @Override
    public List<Course> loadInBackground() {
        //todo load my collect course
        return null;
    }
}