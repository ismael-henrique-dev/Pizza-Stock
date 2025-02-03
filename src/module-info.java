module Controllers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;

    opens Controllers to javafx.fxml;
    exports Controllers;
}
