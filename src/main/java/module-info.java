module gyarab.vyukagrafika {
    requires javafx.controls;
    requires javafx.fxml;


    opens gyarab.grafika to javafx.fxml;
    exports gyarab.grafika;
}