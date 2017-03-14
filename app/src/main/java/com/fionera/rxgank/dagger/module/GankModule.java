package com.fionera.rxgank.dagger.module;

import com.fionera.rxgank.contract.GankContract;
import com.fionera.rxgank.dagger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * GankModule
 * Created by fionera on 17-3-7 in MVPPractice.
 */

@Module(includes = {})
public class GankModule {
    private GankContract.View view;

    public GankModule(GankContract.View view) {
        this.view = view;
    }

    @Provides
    @FragmentScope
    GankContract.View provideView(){
        return view;
    }
}
