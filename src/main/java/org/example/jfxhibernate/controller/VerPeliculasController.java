package org.example.jfxhibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.jfxhibernate.HelloApplication;
import org.example.jfxhibernate.HibernateUtil;
import org.example.jfxhibernate.dao.CopiaDao;
import org.hibernate.SessionFactory;
import org.example.jfxhibernate.Session;
import org.example.jfxhibernate.dao.PeliculaDao;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de ver películas.
 */
public class VerPeliculasController implements Initializable {
    @javafx.fxml.FXML
    private TextArea txtdescripcionver;
    @javafx.fxml.FXML
    private TextField textdirectorver;
    @javafx.fxml.FXML
    private Button btnAtrasUsuario;
    @javafx.fxml.FXML
    private TextField textgenerover;
    @javafx.fxml.FXML
    private ImageView imagenpeli;
    @javafx.fxml.FXML
    private TextField terañover;
    @javafx.fxml.FXML
    private MenuItem menuCerrar;
    @javafx.fxml.FXML
    private Button btnAtrasAdministrador;
    @javafx.fxml.FXML
    private TextField textTitulover;
    @javafx.fxml.FXML
    private MenuItem mnIraLogin;
    @javafx.fxml.FXML
    private Label verpeli;
    @javafx.fxml.FXML
    private MenuBar menu;
    @javafx.fxml.FXML
    private Label dev;
    @javafx.fxml.FXML
    private Label ttv;
    @javafx.fxml.FXML
    private Label ggv;
    @javafx.fxml.FXML
    private Label aav;
    @javafx.fxml.FXML
    private Label ddv;
    @javafx.fxml.FXML
    private Menu close;
    @javafx.fxml.FXML
    private Button btnguardaradministrador;

    /**
     * Inicializa el controlador.
     *
     * @param url la ubicación utilizada para resolver rutas relativas.
     * @param resourceBundle el conjunto de recursos utilizado para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textTitulover.setText(Session.currentPelicula.getTitulo());
        textdirectorver.setText(Session.currentPelicula.getDirector());
        textgenerover.setText(Session.currentPelicula.getGenero());
        terañover.setText(String.valueOf(Session.currentPelicula.getAnho()));
        txtdescripcionver.setText(Session.currentPelicula.getDescripcion());
        imagenpeli.setImage(new Image("file:src/main/resources/org.example.jfxhibernate/resources/media/portadas/" + Session.currentPelicula.getPortada()));
        if (Session.currentUser.isAdmin()) {
            btnAtrasUsuario.setVisible(false);
            btnAtrasAdministrador.setVisible(true);
            btnguardaradministrador.setVisible(true);

            textTitulover.setEditable(true);
            textdirectorver.setEditable(true);
            textgenerover.setEditable(true);
            terañover.setEditable(true);
            txtdescripcionver.setEditable(true);
        } else {
            btnAtrasUsuario.setVisible(true);
            btnAtrasAdministrador.setVisible(false);
            btnguardaradministrador.setVisible(false);

            textTitulover.setEditable(false);
            textdirectorver.setEditable(false);
            textgenerover.setEditable(false);
            terañover.setEditable(false);
            txtdescripcionver.setEditable(false);
            imagenpeli.setDisable(true);
        }
    }

    /**
     * Guarda los cambios realizados por el administrador.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void guardaradministrador(ActionEvent actionEvent) {
        Session.currentPelicula.setTitulo(textTitulover.getText());
        Session.currentPelicula.setDirector(textdirectorver.getText());
        Session.currentPelicula.setGenero(textgenerover.getText());
        Session.currentPelicula.setAnho(Integer.parseInt(terañover.getText()));
        Session.currentPelicula.setDescripcion(txtdescripcionver.getText());
        PeliculaDao peliculaDao = new PeliculaDao(HibernateUtil.getSessionFactory());
        try {
            peliculaDao.update(Session.currentPelicula);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Los cambios se han guardado correctamente.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/administrador/Peliculas-view.fxml", "Ver Peliculas");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Hubo un error al guardar los cambios.");
            alert.showAndWait();
        }
    }

    /**
     * Navega a la vista de copia de películas para el usuario.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void atrasusuario(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-Peli-view.fxml", "Ver Copia Peliculas");
    }

    /**
     * Navega a la vista de películas para el administrador.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void atrasadministrador(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/administrador/Peliculas-view.fxml", "Ver Peliculas");
    }

    /**
     * Cierra la aplicación.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Navega a la vista de login y cierra la sesión del usuario actual.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void irlogin(ActionEvent actionEvent) {
        Session.currentUser = null;
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/login-view.fxml", "Login");
    }
}



