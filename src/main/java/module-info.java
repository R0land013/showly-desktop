module io.github.r0land013.showly {
    requires javafx.controls;
    requires javafx.fxml;
    exports io.github.r0land013.showly;
    opens io.github.r0land013.showly.view to javafx.fxml;
}