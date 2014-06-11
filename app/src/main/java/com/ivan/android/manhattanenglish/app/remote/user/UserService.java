package com.ivan.android.manhattanenglish.app.remote.user;

import com.ivan.android.manhattanenglish.app.remote.course.TeacherDetail;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-11
 * Time: PM8:35
 */
public interface UserService {

    /**
     * 收藏
     *
     * @param teacherId
     */
    void collect(String teacherId);

    /**
     * 取消收藏
     *
     * @param teacherId
     */
    void cancelCollect(String teacherId);

    /**
     * 获得用户信息
     *
     * @param userId
     * @return
     */
    User loadUser(String userId);

    /**
     * 根据关键字搜索教师
     *
     * @param page
     * @param keyword
     * @return
     */
    OpenPage<User> search(OpenPage<User> page, String keyword);

    /**
     * 获得教师详情
     *
     * @param userId
     * @return
     */
    TeacherDetail loadTeacherDetail(String userId);
}
