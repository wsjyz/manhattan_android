package com.ivan.android.manhattanenglish.app.core.collect;

import android.content.Context;

import com.ivan.android.manhattanenglish.app.core.CommonDataLoader;
import com.ivan.android.manhattanenglish.app.remote.ServiceFactory;
import com.ivan.android.manhattanenglish.app.remote.user.TeacherDetail;
import com.ivan.android.manhattanenglish.app.remote.user.UserService;

import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-13
 * Time: PM11:34
 */
public class CollectTeacherListLoader extends CommonDataLoader<List<TeacherDetail>> {

    public CollectTeacherListLoader(Context context) {
        super(context);
    }

    @Override
    public List<TeacherDetail> loadInBackground() {
        UserService userService = ServiceFactory.getService(UserService.class);
        List<TeacherDetail> result = null;
        try {
            result = userService.loadCollectTeacherDetail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
