package org.example.jfxhibernate.controller.admistrador;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;
import org.example.jfxhibernate.HelloApplication;
import org.example.jfxhibernate.HibernateUtil;
import org.example.jfxhibernate.Session;
import org.example.jfxhibernate.dao.CopiaDao;
import org.example.jfxhibernate.dao.PeliculaDao;
import org.example.jfxhibernate.models.Pelicula;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de películas.
 */
public class PeliculasController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> clGeneroP;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> clDirectorP;
    @javafx.fxml.FXML
    private TableView<Pelicula> tablePeli;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> clAñoP;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> clTituloP;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private TableColumn<Pelicula, String> clDescripcionP;
    @javafx.fxml.FXML
    private Label info;

    private PeliculaDao peliculaDao = new PeliculaDao(HibernateUtil.getSessionFactory());
    @javafx.fxml.FXML
    private MenuItem mnCerrarPeli;
    @javafx.fxml.FXML
    private MenuItem mnIrLoginPeliculas;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private MenuBar menu;
    @javafx.fxml.FXML
    private Label mispeli;
    @javafx.fxml.FXML
    private Button btnListadoCompleto;
    @javafx.fxml.FXML
    private Button btnpelimasdeunacopia;
    @javafx.fxml.FXML
    private Button btnCopiamalestado;

    /**
     * Inicializa el controlador de la vista de películas.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce.
     * @param resourceBundle el recurso utilizado para localizar el objeto raíz, o null si no se encuentra.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clTituloP.setCellValueFactory((row) -> {
            return new SimpleStringProperty(row.getValue().getTitulo().toString());
        });
        clDirectorP.setCellValueFactory((row) -> {
            return new SimpleStringProperty(row.getValue().getDirector().toString());
        });
        clGeneroP.setCellValueFactory((row) -> {
            return new SimpleStringProperty(row.getValue().getGenero().toString());
        });
        clAñoP.setCellValueFactory((row) -> {
            return new SimpleStringProperty(row.getValue().getAnho().toString());
        });
        clDescripcionP.setCellValueFactory((row) -> {
            return new SimpleStringProperty(row.getValue().getDescripcion().toString());
        });
        tablePeli.setOnMouseClicked(event -> {
            Pelicula seleccionada = tablePeli.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {

                Session.currentPelicula = seleccionada;

                if (event.getClickCount() == 2) {

                    HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/Ver-Peliculas-view.fxml",
                            "Copia peli - " + seleccionada.getTitulo());
                }

            }
        });
        tableRefresh();

    }

    /**
     * Refresca la tabla de películas.
     */
    private void tableRefresh() {
        tablePeli.getItems().clear();
        peliculaDao.findAll().forEach((p) -> {
            tablePeli.getItems().add(p);
        });

    }

    /**
     * Navega a la vista para añadir una nueva película.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/administrador/Crear-Pelicula-view.fxml", "Añadir Pelicula");
    }

    /**
     * Navega a la vista de login.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void irloginpeliculas(ActionEvent actionEvent) {
        Session.currentUser = null;
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/login-view.fxml", "Login");

    }

    /**
     * Cierra la aplicación.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void cerrarpeli(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Elimina la película seleccionada de la tabla.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void eliminapelicula(ActionEvent actionEvent) {
        Pelicula seleccionada = tablePeli.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación:");
            alerta.setHeaderText("¿Estás seguro de que deseas eliminar la película seleccionada?");
            alerta.setContentText("Pel��cula a eliminar: " + seleccionada.getTitulo());
            Optional<ButtonType> result = alerta.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                CopiaDao copiaDao = new CopiaDao(HibernateUtil.getSessionFactory());
                copiaDao.deleteByPelicula(seleccionada);

                peliculaDao.delete(seleccionada);
                tablePeli.getItems().remove(seleccionada);
                tableRefresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ninguna película seleccionada");
            alert.setHeaderText("No se ha seleccionado ninguna película para eliminar");
            alert.setContentText("Por favor, selecciona una película en la tabla y vuelve a intentarlo.");
            alert.showAndWait();
        }

    }

    /**
     * Genera un informe con el listado completo de películas.
     * @param actionEvent
     */

    @javafx.fxml.FXML
    public void listadocompletopeli(ActionEvent actionEvent) {
        try {
            Connection connection = HibernateUtil.getSessionFactory().openSession().doReturningWork(
                    connectionProvider -> connectionProvider
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport("ListPeli.jasper", null, connection);
           JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al generar el informe");
            alert.setHeaderText("No se pudo generar el informe de películas.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Genera un informe con las películas que tienen más de una copia.
     * @param actionEvent
     */

    @javafx.fxml.FXML
    public void pelimasdeunacopia(ActionEvent actionEvent) {
        try {
            Connection connection = HibernateUtil.getSessionFactory().openSession().doReturningWork(
                    connectionProvider -> connectionProvider
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport("PeliConMasDeUnaCopia" +
                    ".jasper", null, connection);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al generar el informe");
            alert.setHeaderText("No se pudo generar el informe de películas.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Genera un informe con las películas que tienen copias en mal estado.
     * @param actionEvent
     */

    @javafx.fxml.FXML
    public void copiamalestado(ActionEvent actionEvent) {
        try {
            Connection connection = HibernateUtil.getSessionFactory().openSession().doReturningWork(
                    connectionProvider -> connectionProvider
            );
            JasperPrint jasperPrint = JasperFillManager.fillReport("PeliMalEstado.jasper", null, connection);
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al generar el informe");
            alert.setHeaderText("No se pudo generar el informe de películas.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
