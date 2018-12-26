package islamkhsh.com.squarerepo.ui.base;

import android.arch.lifecycle.ViewModel;

import islamkhsh.com.squarerepo.data.AppRepository;
import islamkhsh.com.squarerepo.data.RepositoryHelper;

/**
 * Created by ESLAM on 12/25/2018.
 */

public class BaseViewModel extends ViewModel {

    private RepositoryHelper repositoryHelper;

    public BaseViewModel() {
        this.repositoryHelper = AppRepository.getInstance();
    }

    public RepositoryHelper getRepositoryHelper() {
        return repositoryHelper;
    }
}
