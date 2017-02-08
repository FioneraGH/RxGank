package com.fionera.rxgank.http;

import com.fionera.rxgank.model.GankDay;


import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * ApiService
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public interface ApiService {

    @Headers("Cache-Control：public, max-age=3600")
    @GET("day/{year}/{month}/{day}")
    Observable<GankDay> getGankDay(@Path("yesr") int year, @Path("mounth") int mounth,
                                   @Path("day") int day);
}
