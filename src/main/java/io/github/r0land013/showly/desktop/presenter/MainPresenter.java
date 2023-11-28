package io.github.r0land013.showly.desktop.presenter;

import java.io.IOException;

import io.github.r0land013.showly.Showly;
import io.github.r0land013.showly.ShowlyConfig;
import io.github.r0land013.showly.slides.exception.InvalidSlideFileException;
import javafx.application.Platform;
import io.github.r0land013.showly.desktop.view.AbstractView;
import io.github.r0land013.showly.desktop.view.MainView;
import static io.github.r0land013.showly.desktop.presenter.ShowPresenter.*;

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
        runShowlyAndOpenShowPresenter();
    }

    private void runShowlyAndOpenShowPresenter() {
        Platform.runLater(() -> {
            var port = Integer.valueOf(view.getPort());
            var slidePathFile = view.getSelectedFilePath();
            var presentationName = view.getPresentationName();
            
            var config = new ShowlyConfig(port, slidePathFile, presentationName);
            var showly = new Showly(config);
            
            var intent = new Intent();
            
            try {
                var slideList = showly.show();
                
                intent.addData(SHOWLY_INSTANCE_KEY, showly);
                intent.addData(SHOWLY_CONFIG_KEY, config);
                intent.addData(SLIDE_LIST_KEY, slideList);
                openNewPresenter(ShowPresenter.class, intent);

            } catch (IOException | InvalidSlideFileException e) {
                e.printStackTrace();
            }
        });
    }
}
