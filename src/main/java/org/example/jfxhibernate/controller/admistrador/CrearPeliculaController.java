package org.example.jfxhibernate.controller.admistrador;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import org.example.jfxhibernate.HelloApplication;
import org.example.jfxhibernate.HibernateUtil;
import org.example.jfxhibernate.Session;

import org.example.jfxhibernate.dao.PeliculaDao;
import org.example.jfxhibernate.models.Pelicula;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

/**
 * Controlador para la creación de una nueva película.
 * Implementa la interfaz Initializable para inicializar los componentes de la interfaz gráfica.
 */
public class CrearPeliculaController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtTituloP;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerAño;
    @javafx.fxml.FXML
    private ImageView imagen;
    @javafx.fxml.FXML
    private TextField txtDirector;
    @javafx.fxml.FXML
    private TextArea txtDescripcion;
    @javafx.fxml.FXML
    private ComboBox<String> comboGenero;
    @javafx.fxml.FXML
    private MenuItem menuMisPeliculas;
    @javafx.fxml.FXML
    private MenuItem menuCerrar;

    private PeliculaDao peliculaDao = new PeliculaDao(HibernateUtil.getSessionFactory());
    @javafx.fxml.FXML
    private Button btnCancelarPeli;
    @javafx.fxml.FXML
    private MenuItem mnIrLogin;
    @javafx.fxml.FXML
    private Button btnfotodeescritorio;
    private String nombrenuevapeli;
    private File f;
    private MediaPlayer mp;
    @javafx.fxml.FXML
    private Label ttc;
    @javafx.fxml.FXML
    private Label ggc;
    @javafx.fxml.FXML
    private Label aac;
    @javafx.fxml.FXML
    private Label ddc;
    @javafx.fxml.FXML
    private Label dec;
    @javafx.fxml.FXML
    private Label crearpelicula;
    @javafx.fxml.FXML
    private MenuBar menucrearpeli;

    /**
     * Inicializa los componentes de la interfaz gráfica.
     *
     * @param url             la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle  el ResourceBundle utilizado para localizar el objeto raíz, o null si no se ha localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboGenero.getItems().addAll(peliculaDao.getDistinctGeneros());
        if (Session.currentPelicula == null) {
            txtTituloP.setText("Nueva Pelicula");
            spinnerAño.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1980, 2024, 2024, 1));
            return;
        }
        txtTituloP.setText(Session.currentPelicula.getTitulo());
        comboGenero.setValue(Session.currentPelicula.getGenero());
        spinnerAño.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1980, 2024, Session.currentPelicula.getAnho(), 1));
        txtDirector.setText(Session.currentPelicula.getDirector());
        txtDescripcion.setText(Session.currentPelicula.getDescripcion());
    }

    /**
     * Guarda la nueva película o actualiza la existente.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void guardar(ActionEvent actionEvent) {
        if (txtTituloP.getText().isBlank() || txtDirector.getText().isBlank() || txtDescripcion.getText().isBlank() || comboGenero.getValue() == null) {
            System.out.println("Todos los campos deben estar llenos");
            return;
        }

        if (Session.currentPelicula == null) {
            var pelicula = new Pelicula();
            pelicula.setTitulo(txtTituloP.getText());
            pelicula.setAnho(spinnerAño.getValue());
            pelicula.setDirector(txtDirector.getText());
            pelicula.setGenero(comboGenero.getValue());
            pelicula.setDescripcion(txtDescripcion.getText());
            if (f != null) {
                try (FileInputStream fis = new FileInputStream(f);
                     FileOutputStream fos = new FileOutputStream("src/main/resources/org.example.jfxhibernate/resources/media/portadas/" + f.getName());
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = fis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }

                    pelicula.setPortada(f.getName());

                } catch (IOException e) {
                    System.err.println("Error al copiar la imagen: " + e.getMessage());
                    return;
                }
            }
            peliculaDao.save(pelicula);

        } else {
            Session.currentPelicula.setTitulo(txtTituloP.getText());
            Session.currentPelicula.setAnho(spinnerAño.getValue());
            Session.currentPelicula.setDirector(txtDirector.getText());
            Session.currentPelicula.setGenero(comboGenero.getValue());
            Session.currentPelicula.setDescripcion(txtDescripcion.getText());
            if (f != null) {
                try (FileInputStream fis = new FileInputStream(f);
                     FileOutputStream fos = new FileOutputStream("src/main/resources/org.example.jfxhibernate/resources/media/portadas/" + f.getName());
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = fis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }

                    Session.currentPelicula.setPortada(f.getName());

                } catch (IOException e) {
                    System.err.println("Error al copiar la imagen: " + e.getMessage());
                    return;
                }
            }

            peliculaDao.update(Session.currentPelicula);
        }

        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/administrador/Peliculas-view.fxml", "Película - " + Session.currentUser.getNombreUsuario());
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
     * Navega a la vista de mis películas.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void mispeliculas(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/administrador/Peliculas-view.fxml", "Pelicula - " + Session.currentUser.getNombreUsuario());
    }

    /**
     * Cancela la creación o edición de la película y navega a la vista de mis películas.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void cancelarPeli(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/administrador/Peliculas-view.fxml", "Pelicula - " + Session.currentUser.getNombreUsuario());
    }

    /**
     * Navega a la vista de login.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void irlogin(ActionEvent actionEvent) {
        Session.currentUser = null;
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/login-view.fxml", "Login");
    }

    /**
     * Selecciona una imagen desde el escritorio para usar como portada de la película.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void selectfotodeescritorio(ActionEvent actionEvent) {
        var fc = new FileChooser();
        fc.setTitle("Selecionar portada");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        f = fc.showOpenDialog(null);
        nombrenuevapeli = f.getName();
        if (f != null) {
            imagen.setImage(new javafx.scene.image.Image(f.toURI().toString()));
            String relativePath = "file:src/main/resources/org.example.jfxhibernate/resources/media/portadas/" + nombrenuevapeli;

            if (Session.currentPelicula == null) {
                var pelicula = new Pelicula();
                pelicula.setPortada(relativePath);
                Session.currentPelicula = pelicula;
            } else {
                Session.currentPelicula.setPortada(relativePath);
            }
        }
    }
}