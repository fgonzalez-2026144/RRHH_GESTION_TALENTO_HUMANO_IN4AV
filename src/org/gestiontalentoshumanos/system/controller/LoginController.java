/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.gestiontalentoshumanos.system.controller;

/**
 *
 * @author informatica
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class LoginController implements Initializable {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtPasswordVisible;
    @FXML private ToggleButton btnTogglePassword;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // El campo de texto visible siempre refleja lo que se escribe en el PasswordField
        txtPasswordVisible.textProperty().bindBidirectional(txtPassword.textProperty());
    }

    @FXML
    public void onTogglePasswordVisibility(ActionEvent event) {
        boolean mostrar = btnTogglePassword.isSelected();

        txtPassword.setVisible(!mostrar);
        txtPassword.setManaged(!mostrar);

        txtPasswordVisible.setVisible(mostrar);
        txtPasswordVisible.setManaged(mostrar);
    }

    @FXML
    public void onLoginClick(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            lblError.setText("Ingresa usuario y contraseña.");
            return;
        }

        lblError.setText("");

        // TODO: validar usuario/contraseña contra la tabla Usuarios (ConexionDB)
        // y navegar a la siguiente vista si la autenticación es correcta.
    }
}

