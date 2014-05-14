package com.ivan.android.manhattanenglish.app.core.collect;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ivan.android.manhattanenglish.app.remote.course.Course;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-13
 * Time: PM11:35
 */
public class CollectCourseLoader extends AsyncTaskLoader<List<Course>> {

    public CollectCourseLoader(Context context) {
        super(context);
    }

    @Override
    public List<Course> loadInBackground() {
        //todo load my collect course
        return null;
    }
}