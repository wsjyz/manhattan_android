package com.ivan.android.manhattanenglish.app.remote.course;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-17
 * Time: AM11:04
 */
public class CourseServiceImpl extends AbstractService implements CourseService {

    Type pageType = new TypeReference<OpenPage<Course>>() {
    }.getType();

    @Override
    public OpenPage<Course> loadNiceCourse(OpenPage<Course> page) {
        String action = "/course/getWorthCourses";
        if (page == null) page = new OpenPage<Course>();

        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(page));
        return postForObject(pageType, getUrl(action), params);
    }

    @Override
    public OpenPage<Course> search(OpenPage<Course> page, QueryParam param) {
        String action = "/course/getOrderCourses";
        if (page == null) page = new OpenPage<Course>();

        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(page));
        params.put("queryParam", JSON.toJSONString(param));
        return postForObject(pageType, getUrl(action), params);
    }

    @Override
    public Course loadCourse(String courseId) {
        String action = "/course/getCourseDetail";
        Map<String, String> params = new HashMap<String, String>();
        params.put("courseId", courseId);
        return postForObject(Course.class, getUrl(action), params);
    }

    @Override
    public void postCourse(TeacherDetail teacherDetail) {
        String action = "/course/postCourses";
        Map<String, String> params = new HashMap<String, String>();
        params.put("teacherDetail", JSON.toJSONString(teacherDetail));
        post(getUrl(action), params);
    }

    @Override
    public void submitAppointment(Appointment appointment) {
        String action = "/course/addAppointment";
        Map<String, String> params = new HashMap<String, String>();
        params.put("appointment", JSON.toJSONString(appointment));
        post(getUrl(action), params);
    }

    @Override
    public List<Course> loadMyAppointCourse() {
        String action = "/course/getOrderCoursesByUserId";
        return getCourseListByAction(action);
    }

    @Override
    public List<Course> loadMyAuditionCourse() {
        String action = "/course/getListenCoursesByUserId";
        return getCourseListByAction(action);
    }


    private List<Course> getCourseListByAction(String action) {
        OpenPage<Course> page = new OpenPage<Course>();
        page.setAutoPaging(false);

        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(page));
        OpenPage<Course> result = postForObject(pageType, getUrl(action), params);
        return result.getRows();
    }
}
