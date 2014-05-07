package com.ivan.android.manhattanenglish.app.remote;

import com.ivan.android.manhattanenglish.app.remote.info.InfomationService;
import com.ivan.android.manhattanenglish.app.remote.info.InfomationServiceImpl;
import com.ivan.android.manhattanenglish.app.remote.login.LoginService;
import com.ivan.android.manhattanenglish.app.remote.login.LoginServiceImpl;

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
    }


    private static <T> void register(Class<T> clazz, T o) {
        serviceMap.put(clazz, o);
    }

    public static <T> T getService(Class<T> tClass) {
        return (T) serviceMap.get(tClass);
    }

}