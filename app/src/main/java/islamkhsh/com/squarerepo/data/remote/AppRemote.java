package islamkhsh.com.squarerepo.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import java.util.concurrent.Executors;

import islamkhsh.com.squarerepo.common.Constants;
import islamkhsh.com.squarerepo.data.remote.github.RepoDataSourceFactory;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class AppRemote implements RemoteHelper {

    public LiveData<PagedList<Repo>> repoList;


    //singleton pattern
    private static final Object LOCK = new Object();
    private static volatile AppRemote sInstance;

    public static AppRemote getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new AppRemote();
                }
            }
        }
        return sInstance;
    }

    private AppRemote() {
        RepoDataSourceFactory repoDataSourceFactory = new RepoDataSourceFactory();

        PagedList.Config config = (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                .setInitialLoadSizeHint(Constants.INISIAL_LOAD_SIZE)
                .setPageSize(Constants.ROWS_NUM).build();

        repoList = new LivePagedListBuilder<>(repoDataSourceFactory, config)
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build();
    }

    @Override
    public LiveData<PagedList<Repo>> getRepoList(){
        return repoList;
    }
}
