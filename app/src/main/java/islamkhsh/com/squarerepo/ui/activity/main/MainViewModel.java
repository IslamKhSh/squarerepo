package islamkhsh.com.squarerepo.ui.activity.main;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PagedList;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import islamkhsh.com.squarerepo.common.Constants;
import islamkhsh.com.squarerepo.common.services.RefreshJobService;
import islamkhsh.com.squarerepo.data.remote.github.model.Repo;
import islamkhsh.com.squarerepo.ui.base.BaseViewModel;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class MainViewModel extends BaseViewModel implements Filterable {

    private PagedList<Repo> repoPagedList;
    private MutableLiveData<Boolean> inSearchMode;
    private MutableLiveData<List<Repo>> resultList;


    public MainViewModel(@NonNull Application application) {
        super(application);
        this.inSearchMode = new MutableLiveData<>();
        this.resultList = new MutableLiveData<>();
    }

    public LiveData<PagedList<Repo>> getRepoList(Context context) {
        periodicRefresh(context);
        return getRepositoryHelper().getRepoList(false, context);
    }

    public void refreshCache(Context context) {
        getRepositoryHelper().refreshCache(context);
    }

    public void periodicRefresh(Context context) {
        JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(context, RefreshJobService.class))
                .setPersisted(true)
                .setPeriodic(Constants.INTERVAL_HOUR)
                .build();

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Repo> filteredList = new ArrayList<>();

                if (charSequence.length() == 0)
                    filteredList.addAll(repoPagedList.snapshot());
                else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    filteredList.clear();

                    for (Repo repo : repoPagedList.snapshot()) {
                        if (repo.getName().toLowerCase().contains(filterPattern))
                            filteredList.add(repo);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                resultList.postValue((List<Repo>) filterResults.values);
            }
        };
    }

    public void setRepoPagedList(PagedList<Repo> repoPagedList) {
        this.repoPagedList = repoPagedList;
    }

    public MutableLiveData<Boolean> getInSearchMode() {
        return inSearchMode;
    }

    public void setInSearchMode(Boolean inSearchMode) {
        this.inSearchMode.postValue(inSearchMode);
    }

    public MutableLiveData<List<Repo>> getResultList() {
        return resultList;
    }
}
