package com.ivan.android.manhattanenglish.app.remote.homework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;
import com.ivan.android.manhattanenglish.app.utils.UserCache;

import org.springframework.core.io.UrlResource;

import java.io.File;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-21
 * Time: PM12:52
 */
public class HomeworkServiceImpl extends AbstractService implements HomeworkService {

    Type homeworkPage = new TypeReference<OpenPage<Homework>>() {
    }.getType();

    public OpenPage<Homework> loadUserHomework(OpenPage<Homework> page) {
        String action = "/homeWork/getHomeworksByUser";
        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(page));
        return postForObject(homeworkPage, getUrl(action), params);
    }

    @Override
    public void submitHomework(String homeworkId, File homeworkFile, String content) {
        String action = "/homeWork/submitHomeWork";
        Map<String, String> params = new HashMap<String, String>();
        params.put("homeworkId", homeworkId);
        params.put("homeworkContent", content);

        try {
            UrlResource resource = null;
            if (homeworkFile != null) {
                resource = new UrlResource(homeworkFile.toURI());
            }
            multiPartPost(getUrl(action), resource, params);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OpenPage<Homework> loadTeacherHomework(OpenPage<Homework> condition) {
        String action = "/homeWork/getHomeworksByTeacher";

        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(condition));
        params.put("teacherId", UserCache.getUserId());

        return postForObject(homeworkPage, getUrl(action), params);
    }

    @Override
    public void postHomework(String title) {
        String action = "/homeWork/postHomeWork";
        Homework homework = new Homework();
        homework.setHomeworkTitle(title);
        homework.setCreateBy(UserCache.getUserId());

        Map<String, String> params = new HashMap<String, String>();
        params.put("homeWork", JSON.toJSONString(homework));
        post(getUrl(action), params);
    }
}
