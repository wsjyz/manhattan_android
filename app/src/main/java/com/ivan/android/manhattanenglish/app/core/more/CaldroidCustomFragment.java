package com.ivan.android.manhattanenglish.app.core.more;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

/**
 * @author: Ivan Vigoss
 * Date: 14-5-21
 * Time: PM8:44
 */
public class CaldroidCustomFragment extends CaldroidFragment {

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new CaldroidCustomAdapter(getActivity(), month, year, getCaldroidData(), extraData);
    }

}
