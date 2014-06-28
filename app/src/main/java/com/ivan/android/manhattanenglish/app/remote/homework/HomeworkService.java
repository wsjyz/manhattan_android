package com.ivan.android.manhattanenglish.app.remote.homework;

import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.io.File;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-21
 * Time: PM12:38
 */
public interface HomeworkService {

    /**
     * 获得用户的作业列表
     *
     * @param page
     * @return
     */
    OpenPage<Homework> loadUserHomework(OpenPage<Homework> page);

    /**
     * @param homeworkId
     * @param homeworkFile
     * @param content
     */
    void submitHomework(String homeworkId, File homeworkFile, String content);


    /**
     * 获得老师创建的作业
     *
     * @return
     */
    OpenPage<Homework> loadTeacherHomework(OpenPage<Homework> condition);

    /**
     * 提交一个
     *
     * @param title
     */
    void postHomework(String title);
}
