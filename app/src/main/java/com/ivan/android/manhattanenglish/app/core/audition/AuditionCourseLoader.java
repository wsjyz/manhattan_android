package com.ivan.android.manhattanenglish.app.core.audition;

import android.content.Context;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.ivan.android.manhattanenglish.app.remote.course.CourseService;

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
        CourseService courseService = ServiceFactory.getService(CourseService.class);
        List<Course> result = null;
        try {
            result = courseService.loadMyAuditionCourse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}