package islamkhsh.com.squarerepo.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;

import java.util.concurrent.Executors;

import islamkhsh.com.squarerepo.common.Constants;
import islamkhsh.com.squarerepo.data.remote.github.RepoDataSourceFactory;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class AppRemote implements RemoteHelper {

    //singleton pattern
    private static final Object LOCK = new Object();
    private static volatile AppRemote sInstance;
    private LiveData<PagedList<Repo>> repoList;
    private RepoDataSourceFactory repoDataSourceFactory;

    private AppRemote(Context context) {
        repoDataSourceFactory = new RepoDataSourceFactory(context);
        setupPagedList(true, context);
    }

    public static AppRemote getInstance(Context applicationContext) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new AppRemote(applicationContext);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void setupPagedList(Boolean refresh, Context context) {
        if (repoList != null) ;
            //repoDataSourceFactory.repoDataSource.invalidate();
        else {

            PagedList.Config config = (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(Constants.INITIAL_LOAD_SIZE_HINT)
                    .setPageSize(Constants.ROWS_NUM)
                    .build();

            repoList = new LivePagedListBuilder<>(repoDataSourceFactory, config)
                    .setFetchExecutor(Executors.newFixedThreadPool(5))
                    .build();
        }
    }
    @Override
    public LiveData<PagedList<Repo>> getRepoList(Boolean toRefresh, Context context) {
        if (toRefresh)
            repoDataSourceFactory.repoDataSource.invalidate();

        return repoList;
    }
}
