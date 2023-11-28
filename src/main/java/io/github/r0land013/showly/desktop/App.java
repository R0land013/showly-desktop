package io.github.r0land013.showly.desktop;

import io.github.r0land013.showly.desktop.presenter.ApplicationManager;
import io.github.r0land013.showly.desktop.presenter.MainPresenter;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        var appManager = new ApplicationManager(stage, MainPresenter.class);
        appManager.exec();
    }

    public static void main(String[] args) {
        launch();
    }

}