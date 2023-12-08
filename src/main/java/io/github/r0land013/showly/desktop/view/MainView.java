package io.github.r0land013.showly.desktop.view;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import io.github.r0land013.showly.desktop.presenter.MainPresenter;

public class MainView implements AbstractView {
    
    Scene scene;
    MainPresenter presenter;
    String selectedFilePath;

    @FXML
    private ImageView helpImage;

    @FXML
    private Label loadingLabel;

    @FXML
    private TextField portTextField;

    @FXML
    private Label selectedFileLabel;
    
    @FXML
    private Button selectFileButton;
    
    @FXML
    private TextField presentationNameTextField;

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

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                var stage = (Stage) selectFileButton.getScene().getWindow();
                var fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(
                    new ExtensionFilter("Presentation Files", "*.pptx", "*.ppt"));
                    
                var selectedFile = fileChooser.showOpenDialog(stage);
                if(selectedFile != null) {
                    selectedFileLabel.setText(selectedFile.getName());
                    selectedFilePath = selectedFile.getAbsolutePath();
                }
            }
        });

        helpImage.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    presenter.openHelpPresenter();
                }
            }

        });
    }

    public Scene getViewScene() {
        return scene;
    }

    public boolean isValidUserInput() {
        return true;
    }

    public String getPort() {
        return portTextField.getText().trim();
    }

    public String getSelectedFilePath() {
        return selectedFilePath;
    }

    public String getPresentationName() {
        return presentationNameTextField.getText().trim();
    }

    public String getErrorMessageIfUserInputIsInvalid() {
        
        if(!isValidPort()){
            return "Invalid port. It must be an integer number\nbetween 1024 and 65535.";
        }

        if(!isValidPresentationFile()) {
            return "You must select a slide file.\nFile format must be .pptx or.ppt .";
        }
        
        return null;
    }

    private boolean isValidPort() {
        try{
            var port = getPort();
            var thePort = Integer.valueOf(port);
            
            if(thePort < 1024 || thePort > 65535) {
                return false;
            }

            return true;
        }
        catch(NumberFormatException ex) {
            return false;
        }
    }

    private boolean isValidPresentationFile() {
        if(selectedFilePath == null) {
            return false;
        }
        return true;
    }

    public void showErrorMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(text);

        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.showAndWait();
    }
}
