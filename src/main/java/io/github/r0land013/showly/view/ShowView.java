package io.github.r0land013.showly.view;

import java.io.IOException;
import java.net.URL;
import io.github.r0land013.showly.presenter.ShowPresenter;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ShowView implements AbstractView {
    
    private ShowPresenter presenter;
    Scene scene;

    @FXML
    private Button stopShowingButton;

    public ShowView(ShowPresenter presenter) {
        this.presenter = presenter;
        loadUI();
        wireUpUserActionsToEvents();
    }

    private void loadUI() {
        Parent root;
		try {
			URL url = MainView.class.getResource("/fxml/show.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			loader.setController(this);
			root = loader.load();
			scene = new Scene(root, VIEW_WIDTH, VIEW_HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
    }

    private void wireUpUserActionsToEvents() {
        stopShowingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presenter.closePresenter();
            }
        });
    }

    @Override
    public Scene getViewScene() {
        return scene;
    }

}
