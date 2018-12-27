package islamkhsh.com.squarerepo.common;

/**
 * Created by ESLAM on 12/25/2018.
 */

public abstract class Constants {
    public static final String BASE_URL = "https://api.github.com/";

    public static final int FIRST_ROW = 1;
    public static final int ROWS_NUM = 10;
    public static final int INITIAL_LOAD_SIZE_HINT = 15;

    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final long INTERVAL_HOUR = 100 * 60 * 60;
}
