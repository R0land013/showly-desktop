package io.github.r0land013.showly.desktop.view;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.fxml.FXML;
import io.github.r0land013.showly.desktop.presenter.HelpPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class HelpView implements AbstractView {

    private HelpPresenter presenter;
    private Scene scene;

    @FXML
    private Hyperlink r0land013Hyperlink;

    @FXML
    private Button backButton;

    public HelpView(HelpPresenter presenter) {
        this.presenter = presenter;
        loadUI();
        wireUpUserActionsToEvents();
    }

    private void loadUI() {
        try {
			URL url = HelpView.class.getResource("/fxml/help.fxml");
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
        backButton.setOnAction(event -> {
            presenter.returnToMainView();
        });
    }

    @Override
    public Scene getViewScene() {
        return scene;
    }
    
}
