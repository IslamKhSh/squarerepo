package islamkhsh.com.squarerepo.ui.activity.main;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;


import islamkhsh.com.squarerepo.data.remote.github.model.Repo;
import islamkhsh.com.squarerepo.ui.base.BaseViewModel;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class MainViewModel extends BaseViewModel {

    public LiveData<PagedList<Repo>> getRepoList() {
        return getRepositoryHelper().getRepoList();
    }
}
