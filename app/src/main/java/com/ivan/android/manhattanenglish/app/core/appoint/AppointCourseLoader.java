package com.ivan.android.manhattanenglish.app.core.appoint;

import android.content.Context;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.course.Course;

import java.util.List;

/**
 *
 * @author: Ivan Vigoss
 * Date: 14-5-13
 * Time: PM11:35
 */
public class AppointCourseLoader extends CommonDataLoader<List<Course>> {

    public AppointCourseLoader(Context context) {
        super(context);
    }

    @Override
    public List<Course> loadInBackground() {
        //todo load my collect course
        return null;
    }
}