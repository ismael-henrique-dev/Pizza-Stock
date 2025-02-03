package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

import Services.AdminSession;

/**
 * JavaFX Appx
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/Images/icon.png")));

        if (AdminSession.isLoggedIn()) {
            scene = new Scene(loadFXML("home"), 1280, 640);
        } else {
            scene = new Scene(loadFXML("loginPage"), 600, 400); // Redireciona para login se não estiver logado
        }
        // scene = new Scene(loadFXML("home"), 1280, 640);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/View/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}