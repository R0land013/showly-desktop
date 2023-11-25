package io.github.r0land013.showly.view;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import io.github.r0land013.showly.presenter.MainPresenter;

public class MainView implements AbstractView {
    
    Scene scene;
    MainPresenter presenter;

    @FXML
    private Button showButton;

    public MainView(MainPresenter presenter) {
        this.presenter = presenter;
        loadUI();
        wireUpUserActionsToEvents();
    }

    private void loadUI() {
		try {
			URL url = MainView.class.getResource("/fxml/main.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			loader.setController(this);
			var root = (Parent) loader.load();
			scene = new Scene(root, VIEW_WIDTH, VIEW_HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
    }

    private void wireUpUserActionsToEvents() {
        showButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presenter.openShowPresenter();
            }
        });
    }

    public Scene getViewScene() {
        return scene;
    }
}
