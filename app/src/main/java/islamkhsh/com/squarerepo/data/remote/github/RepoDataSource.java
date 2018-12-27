package islamkhsh.com.squarerepo.data.remote.github;

import android.arch.paging.PositionalDataSource;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import islamkhsh.com.squarerepo.common.Constants;
import islamkhsh.com.squarerepo.common.util.Okhttp3Interceptor;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ESLAM on 12/26/2018.
 */

public class RepoDataSource extends PositionalDataSource<Repo> {

    private GithubService githubService;

    public RepoDataSource(final Context context) {

        Cache cache = new Cache(context.getCacheDir(), Constants.CACHE_SIZE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(Okhttp3Interceptor.getOnlineInterceptor(context))
                .addInterceptor(Okhttp3Interceptor.getOfflineInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        this.githubService = retrofit.create(GithubService.class);
    }


    @Override
    public void loadInitial(@NonNull final LoadInitialParams params, @NonNull final LoadInitialCallback<Repo> callback) {
        githubService.getAllRepos(0, params.pageSize).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if (response.isSuccessful())
                    callback.onResult(response.body(), 0);
                else
                    callback.onResult(new ArrayList<Repo>(), 0);
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadRange(@NonNull final LoadRangeParams params, @NonNull final LoadRangeCallback<Repo> callback) {
        githubService.getAllRepos((params.startPosition / params.loadSize) + 1, params.loadSize).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                callback.onResult(response.body());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

    }
}
