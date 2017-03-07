package com.fionera.rxgank.dagger.component;

import com.fionera.rxgank.dagger.AppComponent;
import com.fionera.rxgank.dagger.module.GankModule;
import com.fionera.rxgank.dagger.scope.FragmentScope;
import com.fionera.rxgank.fragment.GankFragment;

import dagger.Component;

/**
 * GankComponent
 * Created by fionera on 17-3-7 in MVPPractice.
 */
@FragmentScope
@Component(dependencies = {AppComponent.class}, modules = {GankModule.class})
public interface GankComponent {
    void inject(GankFragment gankFragment);
}
