package islamkhsh.com.squarerepo.data;


import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import islamkhsh.com.squarerepo.data.remote.AppRemote;
import islamkhsh.com.squarerepo.data.remote.RemoteHelper;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class AppRepository implements RepositoryHelper {

    private RemoteHelper remote;

    //for singleton
    private static final Object LOCK = new Object();
    private static volatile AppRepository sInstance;

    public static AppRepository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new AppRepository();
                }
            }
        }
        return sInstance;
    }

    private AppRepository() {
       this.remote = AppRemote.getInstance();
    }

    @Override
    public LiveData<PagedList<Repo>> getRepoList() {
        return this.remote.getRepoList();
    }
}
