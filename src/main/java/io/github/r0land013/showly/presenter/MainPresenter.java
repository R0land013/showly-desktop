package io.github.r0land013.showly.presenter;

import io.github.r0land013.showly.presenter.AbstractPresenter;
import io.github.r0land013.showly.view.AbstractView;
import io.github.r0land013.showly.view.MainView;

public class MainPresenter extends AbstractPresenter{

    private MainView view;

    public MainPresenter(ApplicationManager appManager, Intent intent) {
        super(appManager, intent);
        view = new MainView(this);
    }

    public void onPresenterShown() {

    }

    public AbstractView getView() {
        return view;
    }

    public void openShowPresenter() {
        openNewPresenter(ShowPresenter.class);
    }
}
