package org.gestiontalentoshumanos.system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.gestiontalentoshumanos.system.ClasePrincipal;

import java.util.Optional;

public class ColaboradoresController {

    @FXML
    private TableView<Colaborador> tablaColaboradores;

    @FXML
    private TableColumn<Colaborador, String> colId;

    @FXML
    private TableColumn<Colaborador, String> colNombre;

    @FXML
    private TableColumn<Colaborador, String> colPuesto;

    @FXML
    private TableColumn<Colaborador, String> colDepartamento;

    @FXML
    private TableColumn<Colaborador, String> colFecha;

    @FXML
    private TableColumn<Colaborador, Double> colSalario;

    @FXML
    private TableColumn<Colaborador, Void> colAcciones;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Label lblTotalColaboradores;

    @FXML
    private Label lblActivos;

    @FXML
    private Label lblNuevos;

    @FXML
    private Label lblRegistros;


    private final ObservableList<Colaborador> lista =
            FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        configurarColumnas();

        cargarDatos();

        configurarBotones();

        actualizarEstadisticas();
    }


    // ==========================================
    // CONFIGURAR COLUMNAS
    // ==========================================

    private void configurarColumnas() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colPuesto.setCellValueFactory(
                new PropertyValueFactory<>("puesto")
        );

        colDepartamento.setCellValueFactory(
                new PropertyValueFactory<>("departamento")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );

        colSalario.setCellValueFactory(
                new PropertyValueFactory<>("salario")
        );
    }


    // ==========================================
    // DATOS DE PRUEBA
    // ==========================================

    private void cargarDatos() {

        lista.add(new Colaborador(
                "1001",
                "Ana López García",
                "Analista",
                "Finanzas",
                "12/01/2022",
                8000
        ));

        lista.add(new Colaborador(
                "1002",
                "Carlos Pérez",
                "Desarrollador",
                "TI",
                "15/03/2022",
                10000
        ));

        lista.add(new Colaborador(
                "1003",
                "Sofía Ramírez",
                "Diseñadora",
                "Marketing",
                "20/06/2022",
                9000
        ));

        lista.add(new Colaborador(
                "1004",
                "Luis Martínez",
                "Asistente",
                "RRHH",
                "10/09/2022",
                7500
        ));

        lista.add(new Colaborador(
                "1005",
                "Daniela Torres",
                "Analista",
                "Finanzas",
                "05/02/2023",
                8500
        ));

        lista.add(new Colaborador(
                "1006",
                "Jorge Castillo",
                "Desarrollador",
                "TI",
                "18/04/2023",
                11000
        ));

        tablaColaboradores.setItems(lista);
    }


    // ==========================================
    // BOTONES DE EDITAR Y ELIMINAR
    // ==========================================

    private void configurarBotones() {

        colAcciones.setCellFactory(param ->
                new TableCell<>() {

                    private final Button btnEditar =
                            new Button("Editar");

                    private final Button btnEliminar =
                            new Button("Eliminar");


                    {

                        btnEditar.setStyle(
                                "-fx-background-color: #2878d4;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 4;" +
                                "-fx-cursor: hand;"
                        );


                        btnEliminar.setStyle(
                                "-fx-background-color: #dc4b4b;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 4;" +
                                "-fx-cursor: hand;"
                        );


                        btnEditar.setOnAction(event -> {

                            Colaborador colaborador =
                                    getTableView()
                                            .getItems()
                                            .get(getIndex());

                            editarColaborador(colaborador);
                        });


                        btnEliminar.setOnAction(event -> {

                            Colaborador colaborador =
                                    getTableView()
                                            .getItems()
                                            .get(getIndex());

                            eliminarColaborador(colaborador);
                        });
                    }


                    @Override
                    protected void updateItem(
                            Void item,
                            boolean empty) {

                        super.updateItem(item, empty);

                        if (empty) {

                            setGraphic(null);

                        } else {

                            HBox botones =
                                    new HBox(6);

                            botones.getChildren().addAll(
                                    btnEditar,
                                    btnEliminar
                            );

                            setGraphic(botones);
                        }
                    }
                }
        );
    }


    // ==========================================
    // EDITAR COLABORADOR
    // ==========================================

    private void editarColaborador(
            Colaborador colaborador) {

        Dialog<ButtonType> dialog =
                new Dialog<>();

        dialog.setTitle("Editar colaborador");

        dialog.setHeaderText(
                "Editar información del colaborador"
        );


        ButtonType guardar =
                new ButtonType(
                        "Guardar",
                        ButtonBar.ButtonData.OK_DONE
                );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        guardar,
                        ButtonType.CANCEL
                );


        VBox formulario =
                new VBox(10);

        formulario.setPrefWidth(350);


        TextField txtId =
                new TextField(colaborador.getId());

        TextField txtNombre =
                new TextField(colaborador.getNombre());

        TextField txtPuesto =
                new TextField(colaborador.getPuesto());

        TextField txtDepartamento =
                new TextField(colaborador.getDepartamento());

        TextField txtFecha =
                new TextField(colaborador.getFecha());

        TextField txtSalario =
                new TextField(
                        String.valueOf(
                                colaborador.getSalario()
                        )
                );


        formulario.getChildren().addAll(

                new Label("ID del empleado"),
                txtId,

                new Label("Nombre completo"),
                txtNombre,

                new Label("Puesto"),
                txtPuesto,

                new Label("Departamento"),
                txtDepartamento,

                new Label("Fecha de contratación"),
                txtFecha,

                new Label("Salario base mensual"),
                txtSalario
        );


        dialog.getDialogPane()
                .setContent(formulario);


        dialog.setResultConverter(button -> {

            if (button == guardar) {

                try {

                    double salario =
                            Double.parseDouble(
                                    txtSalario.getText()
                            );


                    if (txtId.getText().isBlank()
                            || txtNombre.getText().isBlank()
                            || txtPuesto.getText().isBlank()
                            || txtDepartamento.getText().isBlank()
                            || txtFecha.getText().isBlank()
                            || salario <= 0) {

                        mostrarAlerta(
                                "Complete todos los campos correctamente."
                        );

                        return null;
                    }


                    colaborador.setId(
                            txtId.getText()
                    );

                    colaborador.setNombre(
                            txtNombre.getText()
                    );

                    colaborador.setPuesto(
                            txtPuesto.getText()
                    );

                    colaborador.setDepartamento(
                            txtDepartamento.getText()
                    );

                    colaborador.setFecha(
                            txtFecha.getText()
                    );

                    colaborador.setSalario(
                            salario
                    );


                    tablaColaboradores.refresh();

                    actualizarEstadisticas();

                } catch (NumberFormatException e) {

                    mostrarAlerta(
                            "El salario debe contener solamente números."
                    );
                }
            }

            return null;
        });


        dialog.showAndWait();
    }


    // ==========================================
    // NUEVO COLABORADOR
    // ==========================================

    @FXML
    private void nuevoColaborador() {

        Dialog<ButtonType> dialog =
                new Dialog<>();

        dialog.setTitle("Nuevo colaborador");

        dialog.setHeaderText(
                "Registrar nuevo colaborador"
        );


        ButtonType guardar =
                new ButtonType(
                        "Guardar",
                        ButtonBar.ButtonData.OK_DONE
                );


        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        guardar,
                        ButtonType.CANCEL
                );


        VBox formulario =
                new VBox(10);

        formulario.setPrefWidth(350);


        TextField txtId =
                new TextField();

        txtId.setPromptText("Ej. 1007");


        TextField txtNombre =
                new TextField();

        txtNombre.setPromptText(
                "Nombre completo"
        );


        TextField txtPuesto =
                new TextField();

        txtPuesto.setPromptText(
                "Puesto laboral"
        );


        TextField txtDepartamento =
                new TextField();

        txtDepartamento.setPromptText(
                "Departamento"
        );


        TextField txtFecha =
                new TextField();

        txtFecha.setPromptText(
                "dd/mm/aaaa"
        );


        TextField txtSalario =
                new TextField();

        txtSalario.setPromptText(
                "Ej. 8000"
        );


        formulario.getChildren().addAll(

                new Label("ID del empleado"),
                txtId,

                new Label("Nombre completo"),
                txtNombre,

                new Label("Puesto laboral"),
                txtPuesto,

                new Label("Departamento"),
                txtDepartamento,

                new Label("Fecha de contratación"),
                txtFecha,

                new Label("Salario base mensual"),
                txtSalario
        );


        dialog.getDialogPane()
                .setContent(formulario);


        Button botonGuardar =
                (Button) dialog.getDialogPane()
                        .lookupButton(guardar);


        botonGuardar.setOnAction(event -> {

            try {

                if (txtId.getText().isBlank()
                        || txtNombre.getText().isBlank()
                        || txtPuesto.getText().isBlank()
                        || txtDepartamento.getText().isBlank()
                        || txtFecha.getText().isBlank()
                        || txtSalario.getText().isBlank()) {

                    event.consume();

                    mostrarAlerta(
                            "Debe completar todos los campos."
                    );

                    return;
                }


                double salario =
                        Double.parseDouble(
                                txtSalario.getText()
                        );


                if (salario <= 0) {

                    event.consume();

                    mostrarAlerta(
                            "El salario debe ser mayor que 0."
                    );

                    return;
                }


                Colaborador nuevo =
                        new Colaborador(

                                txtId.getText(),

                                txtNombre.getText(),

                                txtPuesto.getText(),

                                txtDepartamento.getText(),

                                txtFecha.getText(),

                                salario
                        );


                lista.add(nuevo);

                actualizarEstadisticas();

            } catch (NumberFormatException e) {

                event.consume();

                mostrarAlerta(
                        "El salario debe ser un número válido."
                );
            }
        });


        dialog.showAndWait();
    }


    // ==========================================
    // ELIMINAR
    // ==========================================

    private void eliminarColaborador(
            Colaborador colaborador) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );


        alerta.setTitle(
                "Eliminar colaborador"
        );


        alerta.setHeaderText(
                "¿Desea eliminar este colaborador?"
        );


        alerta.setContentText(
                colaborador.getNombre()
                        + "\nID: "
                        + colaborador.getId()
        );


        Optional<ButtonType> resultado =
                alerta.showAndWait();


        if (resultado.isPresent()
                && resultado.get()
                == ButtonType.OK) {

            lista.remove(colaborador);

            actualizarEstadisticas();
        }
    }


    // ==========================================
    // BUSCAR
    // ==========================================

    @FXML
    private void buscarColaborador() {

        String texto =
                txtBuscar.getText()
                        .trim()
                        .toLowerCase();


        if (texto.isEmpty()) {

            tablaColaboradores.setItems(lista);

            actualizarEstadisticas();

            return;
        }


        ObservableList<Colaborador> resultados =
                FXCollections.observableArrayList();


        for (Colaborador c : lista) {

            if (
                    c.getId()
                            .toLowerCase()
                            .contains(texto)

                    ||

                    c.getNombre()
                            .toLowerCase()
                            .contains(texto)

                    ||

                    c.getPuesto()
                            .toLowerCase()
                            .contains(texto)

                    ||

                    c.getDepartamento()
                            .toLowerCase()
                            .contains(texto)
            ) {

                resultados.add(c);
            }
        }


        tablaColaboradores.setItems(
                resultados
        );


        lblRegistros.setText(
                "Mostrando "
                        + resultados.size()
                        + " colaboradores"
        );
    }


    // ==========================================
    // ACTUALIZAR
    // ==========================================

    @FXML
    private void actualizarTabla() {

        tablaColaboradores.refresh();

        actualizarEstadisticas();

        txtBuscar.clear();

        tablaColaboradores.setItems(lista);
    }


    // ==========================================
    // ESTADÍSTICAS
    // ==========================================

    private void actualizarEstadisticas() {

        int total = lista.size();

        lblTotalColaboradores.setText(
                String.valueOf(total)
        );

        lblActivos.setText(
                String.valueOf(total)
        );

        lblNuevos.setText(
                String.valueOf(
                        Math.min(total, 3)
                )
        );

        lblRegistros.setText(
                "Mostrando "
                        + total
                        + " colaboradores"
        );
    }


    // ==========================================
    // ALERTA
    // ==========================================

    private void mostrarAlerta(
            String mensaje) {

        Alert alerta =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alerta.setTitle("Aviso");

        alerta.setHeaderText(null);

        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }


    // ==========================================
    // NAVEGACIÓN
    // ==========================================

    @FXML
    private void irInicio() {

        ClasePrincipal.cambiarVista("ColaboradoresView.fxml");
    }


    @FXML
    private void irNomina() {

        ClasePrincipal.cambiarVista("NominaView.fxml");
    }


    @FXML
    private void irReportes() {

        Alert aviso = new Alert(Alert.AlertType.INFORMATION);

        aviso.setTitle("Reportes");
        aviso.setHeaderText(null);
        aviso.setContentText("La vista de Reportes aun no esta creada.");

        aviso.showAndWait();
    }


    @FXML
    private void cerrarSesion() {

        Stage stage =
                (Stage) tablaColaboradores
                        .getScene()
                        .getWindow();

        stage.close();
    }
}
