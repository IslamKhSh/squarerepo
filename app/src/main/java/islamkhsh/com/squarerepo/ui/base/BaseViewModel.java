package islamkhsh.com.squarerepo.ui.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import islamkhsh.com.squarerepo.data.AppRepository;
import islamkhsh.com.squarerepo.data.RepositoryHelper;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class BaseViewModel extends AndroidViewModel {

    private RepositoryHelper repositoryHelper;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.repositoryHelper = AppRepository.getInstance(application.getBaseContext());
    }

    public RepositoryHelper getRepositoryHelper() {
        return repositoryHelper;
    }
}
