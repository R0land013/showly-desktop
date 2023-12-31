package io.github.r0land013.showly.desktop.presenter;

import io.github.r0land013.showly.desktop.view.AbstractView;
import javafx.application.Platform;

public abstract class AbstractPresenter {

    ApplicationManager appManager;
    Intent intent;

    public AbstractPresenter() {
        throw new RuntimeException("Do not call this method");
    }

    public AbstractPresenter(ApplicationManager appManager, Intent intent) {
        this.appManager = appManager;
        this.intent = intent;
    }

    public abstract AbstractView getView();

    public void onPresenterShown() {
        // Here will be code that will be executed when
        // the presenter is shown for first time.
    }

    protected void openNewPresenter(Class<? extends AbstractPresenter> presenterClass) {
        appManager.openNewPresenter(presenterClass, null);
    }
    
    protected void openNewPresenter(Class<? extends AbstractPresenter> presenterClass, Intent intent) {
        appManager.openNewPresenter(presenterClass, intent);
    }

    protected void closeCurrentPresenter() {
        appManager.closeCurrentPresenter();
    }

    public Intent getIntent() {
        return intent;
    }

    public void onClosingWindow() {
        Platform.exit();
    }

    public String getWindowTitle() {
        return "Not window title - override getWindowTitle method";
    }
}