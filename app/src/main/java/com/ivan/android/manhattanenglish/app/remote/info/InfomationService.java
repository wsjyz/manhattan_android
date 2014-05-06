package com.ivan.android.manhattanenglish.app.remote.info;

import com.ivan.android.manhattanenglish.app.utils.OpenPage;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM4:03
 */
public interface InfomationService {

    OpenPage<Infomation> loadLatestInfomation(OpenPage<Infomation> page);
}
