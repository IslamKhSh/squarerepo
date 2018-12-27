package islamkhsh.com.squarerepo.common.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import islamkhsh.com.squarerepo.data.AppRepository;

/**
 * Created by ESLAM on 12/27/2018.
 */

public class RefreshJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        AppRepository.getInstance(getApplicationContext()).refreshCache(getApplicationContext());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
