package com.fionera.rxgank.http;

import com.fionera.rxgank.entity.GankDay;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * ApiService
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public interface ApiService {

    @Headers("Cache-Control: public, max-age=3600")
    @GET("day/{year}/{month}/{day}")
    Observable<GankDay> getGankDay(@Path("year") int year, @Path("month") int month,
                                   @Path("day") int day);
}
