package islamkhsh.com.squarerepo.data.remote.github;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import java.util.List;

import islamkhsh.com.squarerepo.common.Constants;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ESLAM on 12/26/2018.
 */

public class RepoDataSource extends PageKeyedDataSource<Integer, Repo> {

    private GithubService githubService;

    public RepoDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.githubService = retrofit.create(GithubService.class);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Repo> callback) {
        githubService.getAllRepos(0, params.requestedLoadSize).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
               callback.onResult(response.body(),null,Constants.ROWS_NUM+1);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Repo> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Repo> callback) {
        githubService.getAllRepos(params.key, params.requestedLoadSize).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                int nextKey = (params.key == response.body().size()) ? null : params.key+1;
                callback.onResult(response.body(),nextKey);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }
}
