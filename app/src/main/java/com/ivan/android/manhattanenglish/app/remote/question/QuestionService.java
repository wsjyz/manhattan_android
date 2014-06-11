package com.ivan.android.manhattanenglish.app.remote.question;

import com.ivan.android.manhattanenglish.app.utils.OpenPage;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-11
 * Time: PM8:26
 */
public interface QuestionService {

    public static final String SEARCH_TYPE_ASSIGN = "ASSIGN";
    public static final String SEARCH_TYPE_ANSWER = "ANSWER";
    public static final String SEARCH_TYPE_UNANSWER = "UNANSWER";

    /**
     * 提问
     *
     * @param question
     */
    void askQuestion(Question question);

    /**
     * 回答问题
     *
     * @param question
     */
    void answerQuestion(Question question);

    /**
     * 删除提问
     *
     * @param questionId
     */
    void deleteQuestion(String questionId);

    /**
     * 获得我的提问
     *
     * @return
     */
    OpenPage<Question> loadMyQuestions(OpenPage<Question> page);

    /**
     * @param page
     * @param type
     * @return
     */
    OpenPage<Question> loadQuestionsByType(OpenPage<Question> page, String type);

}
