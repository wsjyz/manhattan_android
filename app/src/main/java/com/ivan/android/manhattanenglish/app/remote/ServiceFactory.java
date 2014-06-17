package com.ivan.android.manhattanenglish.app.remote;

import com.ivan.android.manhattanenglish.app.remote.course.CourseService;
import com.ivan.android.manhattanenglish.app.remote.course.CourseServiceImpl;
import com.ivan.android.manhattanenglish.app.remote.info.InfomationService;
import com.ivan.android.manhattanenglish.app.remote.info.InfomationServiceImpl;
import com.ivan.android.manhattanenglish.app.remote.question.QuestionService;
import com.ivan.android.manhattanenglish.app.remote.question.QuestionServiceImpl;
import com.ivan.android.manhattanenglish.app.remote.user.LoginService;
import com.ivan.android.manhattanenglish.app.remote.user.LoginServiceImpl;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;
import com.ivan.android.manhattanenglish.app.remote.user.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-3-18
 * Time: AM10:02
 */
public class ServiceFactory {

    private static Map<Class, Object> serviceMap = new HashMap<Class, Object>();

    private ServiceFactory() {

    }

    static {
        register(LoginService.class, new LoginServiceImpl());
        register(InfomationService.class, new InfomationServiceImpl());
        register(QuestionService.class, new QuestionServiceImpl());
        register(UserService.class, new UserServiceImpl());
        register(CourseService.class, new CourseServiceImpl());
    }


    private static <T> void register(Class<T> clazz, T o) {
        serviceMap.put(clazz, o);
    }

    public static <T> T getService(Class<T> tClass) {
        return (T) serviceMap.get(tClass);
    }

}
