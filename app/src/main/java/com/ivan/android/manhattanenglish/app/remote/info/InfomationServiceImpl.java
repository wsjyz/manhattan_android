package com.ivan.android.manhattanenglish.app.remote.info;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ivan.android.manhattanenglish.app.remote.AbstractService;
import com.ivan.android.manhattanenglish.app.utils.OpenPage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-4
 * Time: PM4:29
 */
public class InfomationServiceImpl extends AbstractService implements InfomationService {

    Type pageType = new TypeReference<OpenPage<Infomation>>() {
    }.getType();

    public OpenPage<Infomation> loadLatestInfomation(OpenPage<Infomation> page) {
        String action = "/info/getInformations";
        if (page == null) page = new OpenPage<Infomation>();

        Map<String, String> params = new HashMap<String, String>();
        params.put("openPage", JSON.toJSONString(page));
        return postForObject(pageType, getUrl(action), params);
    }
}
