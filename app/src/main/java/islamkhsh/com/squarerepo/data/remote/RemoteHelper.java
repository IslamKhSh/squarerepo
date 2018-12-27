package islamkhsh.com.squarerepo.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.content.Context;

import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/25/2018.
 */

public interface RemoteHelper {
    void setupPagedList(Boolean refresh, Context context);

    LiveData<PagedList<Repo>> getRepoList(Boolean toRefresh, Context context);
}
