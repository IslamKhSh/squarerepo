package islamkhsh.com.squarerepo.data.remote.github;

import android.arch.paging.DataSource;

import islamkhsh.com.squarerepo.data.remote.github.model.Repo;

/**
 * Created by ESLAM on 12/26/2018.
 */

public class RepoDataSourceFactory extends DataSource.Factory<Integer, Repo>  {

    @Override
    public DataSource<Integer, Repo> create() {
        return new RepoDataSource();
    }


}
