package org.example.jfxhibernate.controller.usuario;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.jfxhibernate.HelloApplication;
import org.example.jfxhibernate.HibernateUtil;
import org.example.jfxhibernate.Session;
import org.example.jfxhibernate.dao.CopiaDao;
import org.example.jfxhibernate.models.Copia;
import org.example.jfxhibernate.models.Pelicula;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de copias de películas.
 */
public class CopiaPeliController implements Initializable {

    @javafx.fxml.FXML
    private TableColumn<Copia,String> clEstado;
    @javafx.fxml.FXML
    private Button brnEliminar;
    @javafx.fxml.FXML
    private TableColumn<Copia, String> clSoporte;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private TableColumn <Copia, String> clTitulo;
    @javafx.fxml.FXML
    private TableView<Copia> table;

    private CopiaDao copiaDao = new CopiaDao(HibernateUtil.getSessionFactory());
    @javafx.fxml.FXML
    private MenuItem menuCerrar;
    @javafx.fxml.FXML
    private MenuItem menuLogin;
    @javafx.fxml.FXML
    private Button btnDetalle;

    /**
     * Inicializa el controlador.
     *
     * @param url la URL utilizada para resolver rutas relativas.
     * @param resourceBundle el conjunto de recursos utilizado para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clTitulo.setCellValueFactory((row) -> {
            return new SimpleStringProperty(row.getValue().getPelicula().getTitulo().toString());
        });
        clSoporte.setCellValueFactory(cellData -> {
            String soporte = cellData.getValue().getSoporte() != null ?
                    cellData.getValue().getSoporte().toString() :
                    "Sin definir";
            return new SimpleStringProperty(soporte);
        });
        clEstado.setCellValueFactory(cellData -> {
            String estado = cellData.getValue().getEstado() != null ?
                    cellData.getValue().getEstado().toString() :
                    "Sin definir";
            return new SimpleStringProperty(estado);
        });

        table.setOnMouseClicked(event -> {
            Copia seleccionada = table.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {

                Session.currentPelicula = seleccionada.getPelicula();
                if (event.getClickCount() == 2) {

                    HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Modificar-view.fxml",
                            "Copia peli - " + seleccionada.getPelicula().getTitulo());
                }

            }
        });
        tableRefresh();

    }

    /**
     * Refresca la tabla de copias.
     */
    private void tableRefresh() {
        table.getItems().clear();
        new CopiaDao(HibernateUtil.getSessionFactory()).findByUser(Session.currentUser).forEach((g)->{
            table.getItems().add(g);
        });
    }

    /**
     * Elimina la copia seleccionada de la tabla.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void eliminar(ActionEvent actionEvent) {
        Copia seleccionada = table.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación:");
            alerta.setHeaderText("¿Estás seguro de que deseas eliminar la copia seleccionada?");
            alerta.setContentText("Copia a eliminar: " + seleccionada.getPelicula().getTitulo());
            Optional<ButtonType> result = alerta.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                copiaDao.delete(seleccionada);
                table.getItems().remove(seleccionada);
                tableRefresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ninguna copia seleccionada");
            alert.setHeaderText("No se ha seleccionado ninguna copia para eliminar");
            alert.setContentText("Por favor, selecciona una copia en la tabla y vuelve a intentarlo.");
            alert.showAndWait();
        }
    }

    /**
     * Navega a la vista para añadir una nueva copia.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/crear-copia-view.fxml","ir a crear copia - ");
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
     * Navega a la vista de login.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        Session.currentUser=null;
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/login-view.fxml","Login");
    }

    /**
     * Navega a la vista de detalles de la copia.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void detalle(ActionEvent actionEvent) {
        HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/Ver-Peliculas-view.fxml","Detalle de la copia");
    }
}
