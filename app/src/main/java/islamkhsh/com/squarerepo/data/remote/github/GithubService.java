package islamkhsh.com.squarerepo.data.remote.github;

import java.util.List;

import islamkhsh.com.squarerepo.data.remote.github.model.Repo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ESLAM on 12/25/2018.
 */

public interface GithubService {

    @GET("users/square/repos")
    Call<List<Repo>> getAllRepos(@Query("page") int pageNum, @Query("per_page") int rowsNum);

}
