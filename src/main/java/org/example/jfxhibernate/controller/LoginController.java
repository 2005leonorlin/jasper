package org.example.jfxhibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.jfxhibernate.HelloApplication;
import org.example.jfxhibernate.HibernateUtil;
import org.example.jfxhibernate.Session;
import org.example.jfxhibernate.dao.UserDao;
import org.example.jfxhibernate.models.Usuario;


import java.net.URL;
import java.util.ResourceBundle;





/**
 * Controlador para la vista de inicio de sesión.
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnEnter;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnExit;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label info;

    @FXML
    private ImageView bloc;
    @FXML
    private ImageView imglogo;
    @FXML
    private VBox log;
    @FXML
    private ImageView ini;
    @FXML
    private ImageView logo;

    /**
     * Inicializa el controlador.
     *
     * @param location  la ubicación utilizada para resolver rutas relativas.
     * @param resources el conjunto de recursos utilizado para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnEnter.setOnAction(event -> enter());
        btnExit.setOnAction(event -> exit());


    }

    /**
     * Cierra la aplicación.
     */
    @FXML
    public void exit() {
        System.exit(0);
    }

    /**
     * Maneja el evento de inicio de sesión.
     */
    @FXML
    public void enter() {
        Usuario user = new UserDao(HibernateUtil.getSessionFactory()).validarUsuario(txtEmail.getText(), txtPassword.getText());
        if (user == null) {
            info.setText("Usuario no encontrado");
        } else {
            info.setText("Usuario logueado");
            Session.currentUser = user;
        }

        if (user.isAdmin()) {
            HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/administrador/Peliculas-view.fxml", "Películas - " + user.getNombreUsuario());
        } else {
            HelloApplication.loadFXML("/org.example.jfxhibernate/resources/views/usuario/Copia-peli-view.fxml", "Copia peli - " + user.getNombreUsuario());
        }
    }
}
