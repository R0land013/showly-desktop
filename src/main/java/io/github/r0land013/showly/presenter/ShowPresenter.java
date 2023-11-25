package io.github.r0land013.showly.presenter;

import io.github.r0land013.showly.presenter.ApplicationManager;
import io.github.r0land013.showly.presenter.AbstractPresenter;
import io.github.r0land013.showly.presenter.Intent;
import io.github.r0land013.showly.view.AbstractView;
import io.github.r0land013.showly.view.ShowView;


public class ShowPresenter extends AbstractPresenter {
    
    private ShowView view;

    public ShowPresenter(ApplicationManager appManager, Intent intent) {
        super(appManager, intent);
        view = new ShowView(this);
    }

    @Override
    public AbstractView getView() {
        return view;
    }

    @Override
    public void onPresenterShown() {
        
    }

    public void closePresenter() {
        closeCurrentPresenter();
    }
}
