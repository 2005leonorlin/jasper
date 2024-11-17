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

public class CrearCopiaController implements Initializable {
    @javafx.fxml.FXML
    private ImageView image;
    @javafx.fxml.FXML
    private ComboBox<String> comboEstado;
    @javafx.fxml.FXML
    private Button btnguardar;
    @javafx.fxml.FXML
    private ComboBox<String> comboSoporte;
    @javafx.fxml.FXML
    private ComboBox<String> comboTitulo;

    /**
     * DAO para gestionar las copias de películas.
     */
    private CopiaDao copiaDao = new CopiaDao(HibernateUtil.getSessionFactory());

    /**
     * DAO para gestionar las películas.
     */
    private PeliculaDao peliculaDao = new PeliculaDao(HibernateUtil.getSessionFactory());

    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private MenuItem menuMisCopias;
    @javafx.fxml.FXML
    private MenuItem menuCerrar;
    @javafx.fxml.FXML
    private MenuItem mniaLogin;
    @javafx.fxml.FXML
    private MenuBar menucopia;
    @javafx.fxml.FXML
    private Label ssso;
    @javafx.fxml.FXML
    private Label eeso;
    @javafx.fxml.FXML
    private Menu closecrear;
    @javafx.fxml.FXML
    private Label ttco;
    @javafx.fxml.FXML
    private Label crearco;

    /**
     * Inicializa el controlador.
     *
     * @param url            la URL utilizada para resolver rutas relativas.
     * @param resourceBundle el conjunto de recursos utilizado para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboEstado.getItems().addAll(copiaDao.getDistinctEstados());
        comboSoporte.getItems().addAll(copiaDao.getDistinctSoportes());
        comboTitulo.getItems().addAll(peliculaDao.getdistinctTitles());

        comboTitulo.setOnAction(event -> {
            Pelicula pelicula = peliculaDao.findByTitle((String) comboTitulo.getValue());
            if (pelicula != null) {
                image.setImage(new Image("file:src/main/resources/org.example.jfxhibernate/resources/media/portadas/" + pelicula.getPortada()));
            } else {
                System.out.println("Pelicula no encontrada");
            }
        });

        System.out.println("Usuario actual en CrearCopiaController: " + Session.currentUser);
    }

    /**
     * Guarda una nueva copia en la base de datos.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {
        if (Session.currentUser == null) {
            // Handle the case where the current user is not set
            System.out.println("Current user is not set.");
            return;
        }

        Copia ncopia = new Copia();
        ncopia.setEstado((String) comboEstado.getValue());
        ncopia.setSoporte((String) comboSoporte.getValue());
        ncopia.setIdUsuario(Session.currentUser);

        Pelicula pelicula = peliculaDao.findByTitle((String) comboTitulo.getValue());
        ncopia.setPelicula(pelicula);

        copiaDao.save(ncopia);
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copia peli - " + Session.currentUser.getNombreUsuario());
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
     * Navega a la vista de copias de películas.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     * @deprecated Este método está obsoleto y será eliminado en futuras versiones.
     */
    @Deprecated
    public void atras(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copia peli - " + Session.currentUser.getNombreUsuario());
    }

    /**
     * Cancela la operación actual y navega a la vista de copias de películas.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copia peli - " + Session.currentUser.getNombreUsuario());
    }

    /**
     * Navega a la vista de copias de películas del usuario actual.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void miscopias(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copia peli - " + Session.currentUser.getNombreUsuario());
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