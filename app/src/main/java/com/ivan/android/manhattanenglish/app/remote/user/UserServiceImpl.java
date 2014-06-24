package com.ivan.android.manhattanenglish.app.remote.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.remote.course.Course;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-11
 * Time: PM9:24
 */
public class UserServiceImpl extends AbstractService implements UserService {
    Type teacherDetailPage = new TypeReference<OpenPage<TeacherDetail>>() {
    }.getType();

    @Override
    public void collect(String teacherId) {
        String action = "/user/collect";
        Map<String, String> params = new HashMap<String, String>();
        params.put("teacherId", teacherId);
        post(getUrl(action), params);
    }

    @Override
    public void cancelCollect(String teacherId) {
        String action = "/user/cancelCollect";
        Map<String, String> params = new HashMap<String, String>();
        params.put("teacherId", teacherId);
        post(getUrl(action), params);
    }

    @Override
    public User loadUser(String userId) {
        String action = "/user/getUser";
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        return postForObject(User.class, getUrl(action), params);
    }

    @Override
    public OpenPage<TeacherDetail> search(OpenPage<TeacherDetail> page, String keyword) {
        String action = "/teacher/listPage";
        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(page));
        params.put("searchKey", keyword);
        return postForObject(teacherDetailPage, getUrl(action), params);
    }

    @Override
    public TeacherDetail loadTeacherDetail(String teacherId) {
        String action = "/teacher/getTeacherDetailById";
        Map<String, String> params = new HashMap<String, String>();
        params.put("teacherId", teacherId);
        return postForObject(TeacherDetail.class, getUrl(action), params);
    }


    @Override
    public List<Date> loadCourseSchedule(Date startTime, Date endTime) {
        Type type = new TypeReference<List<Date>>() {
        }.getType();
        String action = "/course/getSchedule";
        Map<String, String> params = new HashMap<String, String>();
        params.put("startTime", JSON.toJSONString(startTime));
        params.put("endTime", JSON.toJSONString(endTime));

        return postForObject(type, getUrl(action), params);
    }

    @Override
    public List<TeacherDetail> loadAppointTeacherDetail() {
        String action = "/teacher/getOrderTeachersByUserId";
        return getTeacherDetailListByAction(action);
    }

    @Override
    public List<TeacherDetail> loadAuditionTeacherDetail() {
        String action = "/teacher/getListenTeachersByUserId";
        return getTeacherDetailListByAction(action);
    }

    @Override
    public List<TeacherDetail> loadCollectTeacherDetail() {
        String action = "/teacher/getCollectTeachersByUserId";
        return getTeacherDetailListByAction(action);
    }


    private List<TeacherDetail> getTeacherDetailListByAction(String action) {
        OpenPage<TeacherDetail> page = new OpenPage<TeacherDetail>();
        page.setAutoPaging(false);

        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(page));
        OpenPage<TeacherDetail> result = postForObject(teacherDetailPage, getUrl(action), params);
        return result.getRows();
    }

}
