package islamkhsh.com.squarerepo.data;


import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.content.Context;

import islamkhsh.com.squarerepo.common.util.DeleteInternalStorageUtil;
import islamkhsh.com.squarerepo.data.remote.AppRemote;
import islamkhsh.com.squarerepo.data.remote.RemoteHelper;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class AppRepository implements RepositoryHelper {

    //for singleton
    private static final Object LOCK = new Object();
    private static volatile AppRepository sInstance;
    private RemoteHelper remote;

    private AppRepository(Context applicationContext) {
        this.remote = AppRemote.getInstance(applicationContext);
    }

    public static AppRepository getInstance(Context applicationContext) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new AppRepository(applicationContext);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void setupPagedList(Boolean refresh, Context context) {
        remote.setupPagedList(refresh, context);

    }

    @Override
    public LiveData<PagedList<Repo>> getRepoList(Boolean toRefresh, Context context) {
        return this.remote.getRepoList(toRefresh, context);
    }


    @Override
    public void refreshCache(Context context) {
        DeleteInternalStorageUtil.deleteCache(context);
        setupPagedList(true, context);
    }
}
