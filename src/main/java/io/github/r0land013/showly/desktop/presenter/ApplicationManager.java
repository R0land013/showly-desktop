package io.github.r0land013.showly.desktop.presenter;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Stack;
import java.lang.reflect.InvocationTargetException;

public class ApplicationManager {

    Stack<AbstractPresenter> presenterStack;
    Stage stage;


    public ApplicationManager(Stage stage, Class<? extends AbstractPresenter> initialPresenterClass) {
        this.stage = stage;
        presenterStack = new Stack<AbstractPresenter>();
        var initialPresenter = buildAbstractPresenter(initialPresenterClass, null);
        presenterStack.push(initialPresenter);

        registerOnCloseStageEvent();
        setAppIcon();
    }
    
    private AbstractPresenter buildAbstractPresenter(Class<? extends AbstractPresenter> presenterClass, Intent intent) {
        try {
            var constrcutor = presenterClass.getConstructor(ApplicationManager.class, Intent.class);
            return (AbstractPresenter) constrcutor.newInstance(this, intent);
        
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void registerOnCloseStageEvent() {
        stage.setOnCloseRequest(event -> {
            callOnClosingWindowOnAllPresenters();
        });
    }

    private void callOnClosingWindowOnAllPresenters() {
        for (var presenter : presenterStack) {
            presenter.onClosingWindow();
        }
    }

    private void setAppIcon() {
        var image = new Image("images/icon.png");
        stage.getIcons().add(image);
    }

    public void openNewPresenter(Class<? extends AbstractPresenter> presenterClass, Intent intent) {
        
        var newPresenter = buildAbstractPresenter(presenterClass, intent);
        presenterStack.push(newPresenter);
        
        var newScene = newPresenter.getView().getViewScene();
        stage.setScene(newScene);
        stage.setTitle(newPresenter.getWindowTitle());
        
        newPresenter.onPresenterShown();
    }

    public void closeCurrentPresenter() {
        presenterStack.pop();

        if (areNoMorePresenters()) {
            closeApp();
        }

        var newScene = presenterStack.peek().getView().getViewScene();
        stage.setScene(newScene);
        stage.setTitle(presenterStack.peek().getWindowTitle());
    }

    private boolean areNoMorePresenters() {
        return presenterStack.size() == 0;
    }

    private void closeApp() {
        Platform.exit();
    }

    public void exec() {
        var initialPresenter = presenterStack.peek();
        stage.setScene(initialPresenter.getView().getViewScene());
        stage.show();
        stage.setTitle(initialPresenter.getWindowTitle());
    }

}
