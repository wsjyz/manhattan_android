package com.ivan.android.manhattanenglish.app.remote.course;

import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.util.List;

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
     * @param teacherDetail
     */
    void postCourse(TeacherDetail teacherDetail);

    /**
     * 预约、试听操作
     *
     * @param appointment
     */
    void submitAppointment(Appointment appointment);


    /**
     * 获得我预约的课程
     *
     * @return
     */
    List<Course> loadMyAppointCourse();


    /**
     * 获得我试听的课程
     *
     * @return
     */
    List<Course> loadMyAuditionCourse();

}
