package org.example.jfxhibernate.controller.usuario;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.jfxhibernate.HelloApplication;
import org.example.jfxhibernate.HibernateUtil;
import org.example.jfxhibernate.Session;
import org.example.jfxhibernate.dao.CopiaDao;
import org.example.jfxhibernate.dao.PeliculaDao;
import org.example.jfxhibernate.models.Copia;
import org.example.jfxhibernate.models.Pelicula;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de modificación de copias de películas.
 */
public class ModificarController implements Initializable {
    @javafx.fxml.FXML
    private ImageView imagemodificar;
    @javafx.fxml.FXML
    private Button btnGuardarModificar;
    @javafx.fxml.FXML
    private Button btnatrasModificar;
    @javafx.fxml.FXML
    private MenuItem mnCerrarModificar;
    @javafx.fxml.FXML
    private TextField txttitulomodificar;
    @javafx.fxml.FXML
    private MenuItem mnvercopias;
    @javafx.fxml.FXML
    private ComboBox ComboEstadomodi;
    @javafx.fxml.FXML
    private ComboBox ComboSoporteModi;
    @javafx.fxml.FXML
    private MenuItem mniralogin;
    @javafx.fxml.FXML
    private Label tt;
    @javafx.fxml.FXML
    private Label ss;
    @javafx.fxml.FXML
    private Label ee;
    @javafx.fxml.FXML
    private Label modcopia;
    @javafx.fxml.FXML
    private MenuBar menu;

    /**
     * Inicializa el controlador.
     *
     * @param url            la URL utilizada para resolver rutas relativas.
     * @param resourceBundle el conjunto de recursos utilizado para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txttitulomodificar.setEditable(false);
        txttitulomodificar.setText(Session.currentPelicula.getTitulo());
        imagemodificar.setImage(new Image("file:src/main/resources/org.example.jfxhibernate/resources/media/portadas/"+ Session.currentPelicula.getPortada()));

        ComboEstadomodi.getItems().addAll("Bueno", "Dañado");  // Opciones de estado
        ComboSoporteModi.getItems().addAll("DVD", "Blu-ray");  // Opciones de soporte

        if (!Session.currentPelicula.getCopias().isEmpty()) {
            Copia primeraCopia = Session.currentPelicula.getCopias().get(0);
            ComboEstadomodi.setValue(primeraCopia.getEstado().toString());
            ComboSoporteModi.setValue(primeraCopia.getSoporte().toString());
        } else {
            ComboEstadomodi.setValue("No disponible");
            ComboSoporteModi.setValue("No disponible");
        }
    }

    /**
     * Navega a la vista de copias de películas.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void atrasmodificar(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copias de " + Session.currentPelicula.getTitulo());
    }

    /**
     * Navega a la vista de copias de películas.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void verCopias(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copias de " + Session.currentPelicula.getTitulo());
    }

    /**
     * Cierra la aplicación.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void cerrarmodificar(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Guarda los cambios realizados en la copia de la película.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void guardarmodificar(ActionEvent actionEvent) {
        CopiaDao copiaDao = new CopiaDao(HibernateUtil.getSessionFactory());
        Copia copia = Session.currentPelicula.getCopias().get(0); // Asumiendo que se está modificando la primera copia

        copia.setEstado(ComboEstadomodi.getValue().toString());
        copia.setSoporte(ComboSoporteModi.getValue().toString());

        copiaDao.update(copia);

        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copias de " + Session.currentPelicula.getTitulo());
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