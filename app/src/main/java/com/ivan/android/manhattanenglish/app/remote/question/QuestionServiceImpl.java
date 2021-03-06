package com.ivan.android.manhattanenglish.app.remote.question;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-11
 * Time: PM9:02
 */
public class QuestionServiceImpl extends AbstractService implements QuestionService {

    Type questionPageType = new TypeReference<OpenPage<Question>>() {
    }.getType();

    public void askQuestion(Question question) {
        String action = "/question/askQuestion";
        Map<String, String> params = new HashMap<String, String>();
        params.put("question", JSON.toJSONString(question));
        post(getUrl(action), params);
    }

    @Override
    public void answerQuestion(Question question) {
        String action = "/question/answerQuestion";
        Map<String, String> params = new HashMap<String, String>();
        params.put("question", JSON.toJSONString(question));
        post(getUrl(action), params);
    }

    @Override
    public void deleteQuestion(String questionId) {
        String action = "/question/deleteQuestion";
        Map<String, String> params = new HashMap<String, String>();
        params.put("questionId", questionId);
        post(getUrl(action), params);
    }
    @Override
    public List<Question> loadMyQuestions() {
        String action = "/question/myQuestions";
        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(getConditionPage()));
        OpenPage<Question> page = postForObject(questionPageType, getUrl(action), params);
        return page.getRows();
    }

    public OpenPage<Question> getConditionPage() {
        OpenPage<Question> result = new OpenPage<Question>();
        result.setAutoPaging(false);
        return result;
    }

    @Override
    public List<Question> loadQuestionByType(String type) {
        String action = "/question/needAnswerList";
        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(getConditionPage()));
        params.put("type", type);
        OpenPage<Question> page = postForObject(questionPageType, getUrl(action), params);
        return page.getRows();
    }
}
