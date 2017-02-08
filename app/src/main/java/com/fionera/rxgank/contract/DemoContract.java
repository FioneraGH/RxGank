package com.fionera.rxgank.contract;

import com.fionera.base.mvp.BasePresenter;
import com.fionera.base.mvp.BaseView;

/**
 * DemoContract
 * Created by fionera on 17-2-8 in MVPPractice.
 */

public class DemoContract {

    public interface View
            extends BaseView<Presenter> {
    }

    public interface Presenter
            extends BasePresenter {
    }

    public interface Model {
    }

}