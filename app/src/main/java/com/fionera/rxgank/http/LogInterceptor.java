package com.fionera.rxgank.http;

import com.fionera.base.util.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * LogInterceptor
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class LogInterceptor
        implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Timber.d("Logging Request:%s", request.url().toString());
        Timber.d("Logging Request Header:%s", request.headers());

        Response response = chain.proceed(request);
        if (response != null && response.body() != null) {
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Timber.d("Logging Response:%s", content);
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        }
        return response;
    }
}
