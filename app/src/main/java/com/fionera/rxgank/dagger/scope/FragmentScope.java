package com.fionera.rxgank.dagger.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * FragmentScope
 * Created by fionera on 17-3-7 in MVPPractice.
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentScope {
}
