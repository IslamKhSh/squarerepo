package islamkhsh.com.squarerepo.common.util;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ESLAM on 12/27/2018.
 */

public class Okhttp3Interceptor {

    public static Interceptor getOnlineInterceptor(final Context context) {

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                String headers = response.header("Cache-Control");
                if (NetworkUtils.isNetworkConnected(context) &&
                        (headers == null || headers.contains("no-store") || headers.contains("must-revalidate") || headers.contains("no-cache") || headers.contains("max-age=0"))) {

                    return response.newBuilder()
                            .header("Cache-Control", "public, max-age=100")
                            .build();
                } else
                    return response;

            }
        };

        return interceptor;
    }


    public static Interceptor getOfflineInterceptor(final Context context) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkConnected(context)) {
                    request = request.newBuilder()
                            .header("Cache-Control", "public, only-if-cached")
                            .build();
                }

                return chain.proceed(request);
            }
        };

        return interceptor;
    }
}
