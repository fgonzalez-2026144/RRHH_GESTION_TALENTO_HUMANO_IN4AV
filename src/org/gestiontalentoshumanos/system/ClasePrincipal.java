package org.gestiontalentoshumanos.system;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicacion.
 *
 * Se encarga de:
 *  - Arrancar JavaFX.
 *  - Cargar la primera vista.
 *  - Ofrecer el metodo cambiarVista() que usan los controladores
 *    para navegar entre pantallas.
 */
public class ClasePrincipal extends Application {

    /** Carpeta donde estan los archivos .fxml dentro del classpath. */
    private static final String RUTA_VISTAS =
            "/org/gestiontalentoshumanos/system/view/";

    /** Ventana unica de la aplicacion. */
    private static Stage escenarioPrincipal;

    @Override
    public void start(Stage stage) {

        escenarioPrincipal = stage;

        escenarioPrincipal.setTitle(
                "RRHH - Gestion de Talento Humano");

        escenarioPrincipal.setWidth(1200);
        escenarioPrincipal.setHeight(720);

        // Vista con la que inicia el sistema
        cambiarVista("ColaboradoresView.fxml");

        escenarioPrincipal.show();
    }

    /**
     * Cambia el contenido de la ventana por la vista indicada.
     *
     * @param nombreFxml por ejemplo "NominaView.fxml"
     */
    public static void cambiarVista(String nombreFxml) {

        try {

            URL ubicacion =
                    ClasePrincipal.class.getResource(
                            RUTA_VISTAS + nombreFxml);

            if (ubicacion == null) {

                mostrarError("No se encontro la vista: "
                        + nombreFxml
                        + "\nVerifica que el archivo este en "
                        + RUTA_VISTAS);

                return;
            }

            FXMLLoader cargador = new FXMLLoader(ubicacion);

            Parent raiz = cargador.load();

            Scene escena = escenarioPrincipal.getScene();

            if (escena == null) {
                escenarioPrincipal.setScene(new Scene(raiz));
            } else {
                escena.setRoot(raiz);
            }

        } catch (IOException e) {

            e.printStackTrace();

            mostrarError("Error al cargar la vista "
                    + nombreFxml
                    + "\n" + e.getMessage());
        }
    }

    /** Devuelve la ventana principal (util para cerrar la app). */
    public static Stage getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    private static void mostrarError(String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
