package islamkhsh.com.squarerepo.data;

import android.content.Context;

import islamkhsh.com.squarerepo.data.remote.RemoteHelper;

/**
 * Created by ESLAM on 12/25/2018.
 */

public interface RepositoryHelper extends RemoteHelper {
    void refreshCache(Context context);
}
