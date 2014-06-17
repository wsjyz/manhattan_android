package com.ivan.android.manhattanenglish.app.remote.course;

import com.ivan.android.manhattanenglish.app.utils.OpenPage;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-7
 * Time: PM9:07
 */
public interface CourseService {

    /**
     * 获得精品课程
     *
     * @param page
     * @return
     */
    OpenPage<Course> loadNiceCourse(OpenPage<Course> page);


    /**
     * 根据条件搜索课程
     *
     * @param page
     * @param param
     * @return
     */
    OpenPage<Course> search(OpenPage<Course> page, QueryParam param);


    /**
     * 获得课程信息
     *
     * @param courseId
     * @return
     */
    Course loadCourse(String courseId);


    /**
     * 发布课程
     *
     * @param course
     */
    void postCourse(Course course);

}
