package com.ivan.android.manhattanenglish.app.remote.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.remote.course.TeacherDetail;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-11
 * Time: PM9:24
 */
public class UserServiceImpl extends AbstractService implements UserService {
    Type userPageType = new TypeReference<OpenPage<User>>() {
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
    public OpenPage<User> search(OpenPage<User> page, String keyword) {
        String action = "/question/listPage";
        Map<String, String> params = new HashMap<String, String>();
        params.put("page", JSON.toJSONString(page));
        params.put("searchKey", keyword);
        return postForObject(userPageType, getUrl(action), params);
    }

    @Override
    public TeacherDetail loadTeacherDetail(String userId) {

        return null;
    }
}
