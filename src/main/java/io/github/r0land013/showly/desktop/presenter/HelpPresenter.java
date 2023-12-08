package io.github.r0land013.showly.desktop.presenter;

import io.github.r0land013.showly.desktop.view.AbstractView;
import io.github.r0land013.showly.desktop.view.HelpView;

public class HelpPresenter extends AbstractPresenter {

    private HelpView view;
    
    public HelpPresenter(ApplicationManager appManager, Intent intent) {
        super(appManager, intent);
        view = new HelpView(this);
    }

    @Override
    public AbstractView getView() {
        return view;
    }

    public void returnToMainView() {
        closeCurrentPresenter();
    }

    @Override
    public String getWindowTitle() {
        return "Showly - Help";
    }

}
