package org.example.jfxhibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 */
public class HelloApplication extends Application {
    private static Stage ventana;

    /**
     * Método de inicio de la aplicación.
     *
     * @param stage el escenario principal de la aplicación.
     * @throws IOException si ocurre un error al cargar la vista FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("/org.example.jfxhibernate/resources/views/login-view.fxml", "Prueba de Login");

        stage.show();
    }

    /**
     * Carga una vista FXML y la establece en el escenario principal.
     *
     * @param view  la ruta de la vista FXML.
     * @param title el título de la ventana.
     */
    public static void loadFXML(String view, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.setResizable(false);
    }

    /**
     * Método principal que lanza la aplicación.
     *
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}