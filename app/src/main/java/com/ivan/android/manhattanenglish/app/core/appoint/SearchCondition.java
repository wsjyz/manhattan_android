package com.ivan.android.manhattanenglish.app.core.appoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-5
 * Time: PM2:27
 */
public class SearchCondition {
    public String text;
    public int icon;
    public String conditionText;

    public static List<SearchCondition> createFromArray(String[] textArray, int[] resArray, String defaultConditionText) {
        List<SearchCondition> result = new ArrayList<SearchCondition>();

        for (int i = 0, length = textArray.length; i < length; i++) {
            SearchCondition condition = new SearchCondition();
            condition.text = textArray[i];
            condition.icon = resArray[i];
            condition.conditionText = defaultConditionText;
            result.add(condition);
        }

        return result;
    }
}
