package com.fionera.rxgank.http;

import com.fionera.base.util.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * LogInterceptor
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class LogInterceptor
        implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        L.d("Logging Request:" + request.url().toString());
        L.d("Logging Request Header:" + request.headers());

        Response response = chain.proceed(request);
        if (response != null && response.body() != null) {
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            L.d("Logging Response:" + content);
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        }
        return response;
    }
}
