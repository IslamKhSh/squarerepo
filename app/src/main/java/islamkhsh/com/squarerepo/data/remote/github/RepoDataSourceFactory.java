package islamkhsh.com.squarerepo.data.remote.github;

import android.arch.paging.DataSource;
import android.content.Context;

import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/26/2018.
 */

public class RepoDataSourceFactory extends DataSource.Factory<Integer, Repo>  {

    public RepoDataSource repoDataSource;
    private Context context;

    public RepoDataSourceFactory(Context applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public DataSource<Integer, Repo> create() {
        if (repoDataSource == null)
            repoDataSource = new RepoDataSource(context);
        return repoDataSource;
    }

}
