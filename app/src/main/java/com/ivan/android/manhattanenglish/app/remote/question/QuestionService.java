package com.ivan.android.manhattanenglish.app.remote.question;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-11
 * Time: PM8:26
 */
public interface QuestionService {

    public static final String SEARCH_TYPE_ASSIGN = "ASSIGN";
    public static final String SEARCH_TYPE_ANSWER = "ANSWERED";
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
     * 获得我的问题
     *
     * @return
     */
    List<Question> loadMyQuestions();


    /**
     * @param type {@link QuestionService#SEARCH_TYPE_ASSIGN}
     * @return
     */
    List<Question> loadQuestionByType(String type);

}
