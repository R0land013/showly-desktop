package io.github.r0land013.showly.desktop.view;

import java.io.IOException;
import java.net.URL;
import io.github.r0land013.showly.desktop.presenter.ShowPresenter;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ShowView implements AbstractView {
    
    private ShowPresenter presenter;
    Scene scene;

    @FXML
    private Label presentationNameLabel;

    @FXML
    private Text ipAddressLabel;

    @FXML
    private ImageView slideImageView;

    @FXML
    private Label slidePositionLabel;

    @FXML
    private Button nextSlideButton;

    @FXML
    private Button previousSlideButton;

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

        nextSlideButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presenter.nextSlide();
            }
        });
        
        previousSlideButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presenter.previousSlide();
            }
        });

    }

    @Override
    public Scene getViewScene() {
        return scene;
    }

    public void setPresentationName(String name) {
        presentationNameLabel.setText(name);
    }

    public void setIpAddressMessage(String ipAddressMessage) {
        ipAddressLabel.setText(ipAddressMessage);
    }

    public void setSlideImage(Image image) {
        slideImageView.setImage(image);
    }

    public void setSlidePosition(int slideTotal, int slidePosition) {
        slidePositionLabel.setText(String.format("%d / %d", slidePosition, slideTotal));
        
        if(slidePosition == slideTotal) {
            nextSlideButton.setDisable(true);
        } else {
            nextSlideButton.setDisable(false);
        }

        if(slidePosition == 1) {
            previousSlideButton.setDisable(true);
        } else {
            previousSlideButton.setDisable(false);
        }
    }

}
