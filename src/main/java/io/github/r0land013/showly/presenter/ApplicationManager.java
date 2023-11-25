package io.github.r0land013.showly.presenter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Stack;
import java.lang.reflect.InvocationTargetException;
import io.github.r0land013.showly.presenter.Intent;

public class ApplicationManager {

    Stack<AbstractPresenter> presenterStack;
    Stage stage;


    public ApplicationManager(Stage stage, Class<? extends AbstractPresenter> initialPresenterClass) {
        this.stage = stage;
        presenterStack = new Stack<AbstractPresenter>();
        var initialPresenter = buildAbstractPresenter(initialPresenterClass, null);
        presenterStack.push(initialPresenter);
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

    public void openNewPresenter(Class<? extends AbstractPresenter> presenterClass, Intent intent) {
        
        var newPresenter = buildAbstractPresenter(presenterClass, intent);
        presenterStack.push(newPresenter);
        
        var newScene = newPresenter.getView().getViewScene();
        stage.setScene(newScene);
        
        newPresenter.onPresenterShown();
    }

    public void closeCurrentPresenter() {
        presenterStack.pop();

        if (areNoMorePresenters()) {
            closeApp();
        }

        var newScene = presenterStack.peek().getView().getViewScene();
        stage.setScene(newScene);
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
    }

}
