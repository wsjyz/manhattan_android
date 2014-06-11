package com.ivan.android.manhattanenglish.app.remote.purchase;

import com.ivan.android.manhattanenglish.app.utils.OpenPage;

/**
 * @author: Ivan Vigoss
 * Date: 14-6-11
 * Time: PM9:00
 */
public interface PurchaseService {
    /**
     * 获得用户的消费记录
     *
     * @param page
     * @return
     */
    OpenPage<PurchaseHistory> loadPurchaseHistory(OpenPage<PurchaseHistory> page);
}
