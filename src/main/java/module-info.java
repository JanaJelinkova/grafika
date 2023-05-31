module gyarab.vyukagrafika {
    requires javafx.controls;


    opens gyarab.grafika to javafx.fxml;
    exports gyarab.grafika;
}
